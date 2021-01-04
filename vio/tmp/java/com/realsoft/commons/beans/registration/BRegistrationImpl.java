/*
 * Created on 18.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRegistrationImpl.java,v 1.2 2016/04/15 10:37:50 dauren_home Exp $
 */
package com.realsoft.commons.beans.registration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.util.BUtilException;
import com.realsoft.commons.utils14.RealsoftException;
import com.realsoft.commons.utils14.hash.HashCalculator;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

/**
 * @author dimad
 */

public class BRegistrationImpl implements IBRegistration, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BRegistrationImpl.class);

	private BasicDataSource dataSource = null;

	private BeanFactory beanFactory = null;

	private ConverterManager converter = null;

	public BRegistrationImpl() throws CommonsBeansException {
	}

	private static final String GET_ABONENT_TOWN_ID_STMNT = new StringBuffer()
			.append("select town_id from db.abonent where id = ? ").toString();

	private long getTownId(long abonentId, Connection conn) throws SQLException {
		PreparedStatement statement = conn
				.prepareStatement(GET_ABONENT_TOWN_ID_STMNT);
		statement.setLong(1, abonentId);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next())
			return resultSet.getLong(1);
		return -1;
	}

	private static final String CALLABLE_ACCOUNT_STMT = "{call db.net_pay_check_account_odt( ?, ?, ?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#register13(java.lang.String)
	 */
	public BRegistrationInfo13 register13(String phone)
			throws BRegistrationException {
		log.info("registering with phone " + phone);
		CallableStatement cstmt = null;
		Connection conn = null;
		int recCount = 0;
		long abonentId = 0;
		BRegistrationInfo13 registrationInfo = new BRegistrationInfo13();
		registrationInfo.setName(phone);
		try {
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(CALLABLE_ACCOUNT_STMT);
			cstmt.setString(1, phone);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.INTEGER);

			cstmt.execute();

			recCount = cstmt.getInt(2);
			abonentId = cstmt.getLong(3);
			long townId = getTownId(abonentId, conn);
			registrationInfo.setTownId(townId);
			registrationInfo.setGuid(townId + "-" + abonentId + "-"
					+ "no information");
			log.debug("GUID = " + registrationInfo.getGuid());
		} catch (SQLException e) {
			log.error("Could not check account", e);
			throw new BRegistrationException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REGISTRATION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not register for phone " + phone, e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
		log.debug("recCount = " + recCount + "; accountId = " + abonentId);
		registrationInfo.setAbonentId(abonentId);
		try {
			if (recCount == -2) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_PHONE,
						CommonsBeansConstants.CHECK_PHONE_ERROR,
						"Wrong phone number");
			} else if (recCount == -1) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_PHONE,
						CommonsBeansConstants.PHONE_LENGTH_ERROR,
						"Wrong phone length");
			} else if (recCount == 0) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_PHONE,
						CommonsBeansConstants.PHONE_ABCENT_ERROR,
						"No such phone");
			} else if (recCount > 1) {
				throw new BRegistrationException(CommonsBeansConstants.ERROR,
						CommonsBeansConstants.PHONE_TOOMANY_ERROR,
						"Too many phones with such number");
			} else {
				IBAbonent abonent = (IBAbonent) beanFactory
						.getBean(ComponentFactoryImpl.getBAbonentName());
				registrationInfo.setResponce(CommonsBeansConstants.OK);
				registrationInfo.setNote(CommonsBeansConstants.PHONE_FOUND);
				registrationInfo.setBalance(abonent.abonentBalance13(phone,
						abonentId));
			}

			log.info("registration done");
			return registrationInfo;
		} catch (BRegistrationException e) {
			throw e;
		} catch (CommonsBeansException e) {
			log.error("Could not register abonent", e);
			throw new BRegistrationException(CommonsBeansConstants.ERROR,
					CommonsBeansConstants.ABONENT_ERROR,
					"Could not get register abonent");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#register13(java.util.List)
	 */
	public List register13(List phones) throws BRegistrationException {
		List<BRegistrationInfo13> registerList = new ArrayList<BRegistrationInfo13>();
		for (Iterator iter = phones.iterator(); iter.hasNext();) {
			registerList.add(register13((String) iter.next()));
		}
		return registerList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#register13(java.lang.String,
	 *      java.lang.String)
	 */
	public BRegistrationInfo13 register13(String phone, String password)
			throws BRegistrationException, BUtilException, BAbonentException {

		BRegistrationInfo13 registrationInfo = register13(phone);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			IBAbonent abonent = (IBAbonent) beanFactory
					.getBean(ComponentFactoryImpl.getBAbonentName());
			String abonentPassword = abonent
					.getAbonentPassword(registrationInfo.getAbonentId());
			log.debug("abonentPassword = " + abonentPassword + "; password = "
					+ password);
			String hashPAssword = HashCalculator.getMD5(password);
			log.debug("hashPAssword = " + hashPAssword);
			if (hashPAssword.equals(abonentPassword)) {
				registrationInfo.setPassword(abonentPassword);
				return registrationInfo;
			}
			throw new BRegistrationException(CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.REGISTRATION_ACCESS_ERROR,
					"Registration failed for phone " + phone);
		} catch (RealsoftException e) {
			log.error("Registration failed during calculate MD5 " + phone, e);
			throw new BRegistrationException(e.getErrorCode(), e
					.getMessageKey(), e.getMessageText());
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String OPERATOR_INFO_STMT = new StringBuffer().append(
			"select xwid from safety.uwp_link where xuid=? and rownum<2")
			.toString();

	private static final String OPERATOR_PSTMT_A = "{ call safety.connect_me_a( ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#registerOperator(java.lang.String,
	 *      java.lang.String)
	 */
	public BRegistrationInfo13 registerOperator(java.lang.String userName,
			java.lang.String password) throws BRegistrationException {
		Connection conn = null;
		log.debug("Logining userName = " + userName + "...");
		try {
			BRegistrationInfo13 registrationInfo = null;

			CallableStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug("OPERATOR_PSTMT_A = " + OPERATOR_PSTMT_A);
				cstmt = conn.prepareCall(OPERATOR_PSTMT_A);
				cstmt.setString(1, userName);
				cstmt.setString(2, password);
				cstmt.setLong(3, 5);
				cstmt.setString(4, "");
				cstmt.registerOutParameter(5, Types.INTEGER);
				cstmt.registerOutParameter(6, Types.INTEGER);
				cstmt.execute();
				CommonOperations.commitConnection(conn);
				registrationInfo = new BRegistrationInfo13();
				registrationInfo.setNote(cstmt.getString(5));
				registrationInfo.setName(userName);
				registrationInfo.setAbonentId(cstmt.getLong(6));
				registrationInfo.setPassword(HashCalculator.getMD5(password));
			} finally {
				CommonOperations.closeStatement(cstmt);
			}

			log.debug("abonent id = " + registrationInfo.getAbonentId());
			if (registrationInfo.getAbonentId() < 0) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_ERROR + ".operator."
								+ registrationInfo.getAbonentId(),
						"Could not login");
			}

			PreparedStatement pstmt = null;
			try {
				log.debug(OPERATOR_INFO_STMT);
				pstmt = conn.prepareStatement(OPERATOR_INFO_STMT);
				pstmt.setLong(1, registrationInfo.getAbonentId());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					registrationInfo.setWorkPlaceId(rs.getLong(1));
				}
			} finally {
				CommonOperations.closeStatement(pstmt);
			}
			log.debug("Logining done");
			return registrationInfo;
		} catch (SQLException e) {
			log.error("Could not register user " + userName, e);
			CommonOperations.rollbackConnection(conn);
			throw new BRegistrationException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.REGISTRATION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (RealsoftException e) {
			log.error("Could not register user " + userName, e);
			CommonOperations.rollbackConnection(conn);
			throw new BRegistrationException(e.getErrorCode(), e
					.getMessageKey(), e.getMessageText());
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String LOGIN_OPERATOR_STMNT = "{ call web.pkg_usr.logon( ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#loginOperator(java.lang.String,
	 *      java.lang.String)
	 */
	public BRegistrationInfo13 loginOperator(java.lang.String userName,
			java.lang.String password) throws BRegistrationException {
		Connection conn = null;
		log.debug("Logining userName = " + userName + "...");
		try {
			BRegistrationInfo13 registrationInfo = null;

			CallableStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug("LOGIN_OPERATOR_STMNT = " + LOGIN_OPERATOR_STMNT);
				cstmt = conn.prepareCall(LOGIN_OPERATOR_STMNT);
				cstmt.setString(1, userName);
				cstmt.setString(2, password);
				cstmt.registerOutParameter(3, Types.INTEGER);
				cstmt.execute();
				registrationInfo = new BRegistrationInfo13();
				registrationInfo.setAbonentId(cstmt.getLong(3));
				CommonOperations.commitConnection(conn);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}

			log.debug("abonent id = " + registrationInfo.getAbonentId());
			if (registrationInfo.getAbonentId() <= 0) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_ERROR + ".operator."
								+ registrationInfo.getAbonentId(),
						"Could not login");
			}
			log.debug("Logining done");
			return registrationInfo;
		} catch (SQLException e) {
			log.error("Could not register user " + userName, e);
			CommonOperations.rollbackConnection(conn);
			throw new BRegistrationException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.REGISTRATION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}

	}

	private static final String DISCONNECT_OPERATOR_PSTMT_A = "{ call safety.disconnect_me(?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#unRegisterOperator(long,
	 *      long)
	 */
	public void unRegisterOperator(long townId, long abonentId)
			throws BRegistrationException {
		unRegisterOperator(abonentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#unRegisterOperator(long)
	 */
	public void unRegisterOperator(long abonentId)
			throws BRegistrationException {
		log.debug("Disconnecting abonentId = " + abonentId + " ...");
		Connection conn = null;
		log.debug("Logining ...");
		try {

			CallableStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareCall(DISCONNECT_OPERATOR_PSTMT_A);
				cstmt.setLong(1, abonentId);
				log.debug("Preparing to disconnect");
				cstmt.execute();
				log.debug("Disconnecting done");
			} catch (SQLException e) {
				log.error("Could not register abonent " + abonentId, e);
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_ERROR
								+ StringUtils.getOraErrorCodeDotend(e
										.getMessage())[0], e.getMessage(), e);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String OPERATOR_PSTMT_B = "{ call safety.connect_me_b( ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.registration.IBRegistration#registerOperator(long,
	 *      java.lang.String, java.lang.String)
	 */
	public BRegistrationInfo13 registerOperator(long townId,
			java.lang.String userName, java.lang.String password)
			throws BRegistrationException {
		Connection conn = null;
		log.debug("Logining ...");
		try {
			BRegistrationInfo13 registrationInfo = null;

			CallableStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareCall(OPERATOR_PSTMT_B);
				cstmt.setString(1, userName);
				cstmt.setString(2, password);
				cstmt.setLong(3, 5);
				cstmt.setString(4, "");
				cstmt.registerOutParameter(5, Types.INTEGER);
				cstmt.registerOutParameter(6, Types.INTEGER);
				cstmt.execute();
				registrationInfo = new BRegistrationInfo13();
				registrationInfo.setNote(cstmt.getString(5));
				registrationInfo.setAbonentId(cstmt.getLong(6));
			} catch (SQLException e) {
				log.error("Could not register user " + userName, e);
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_ERROR
								+ StringUtils.getOraErrorCode(e.getMessage())[0],
						e.getMessage(), e);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}

			log.debug("abonent id = " + registrationInfo.getAbonentId());
			if (registrationInfo.getAbonentId() < 0) {
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_OPERATOR_ERROR,
						"Could not login");
			}

			PreparedStatement pstmt = null;
			try {
				log.debug(OPERATOR_INFO_STMT);
				pstmt = conn.prepareStatement(OPERATOR_INFO_STMT);
				pstmt.setLong(1, registrationInfo.getAbonentId());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					registrationInfo.setWorkPlaceId(rs.getLong(1));
				}
			} catch (SQLException e) {
				log.error("Could not register user " + userName, e);
				throw new BRegistrationException(
						CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.REGISTRATION_ERROR
								+ StringUtils.getOraErrorCodeDotend(e
										.getMessage())[0], e.getMessage(), e);
			} finally {
				CommonOperations.closeStatement(pstmt);
			}
			log.debug("Logining done");
			return registrationInfo;
		} finally {
			CommonOperations.closeConnection(conn);
		}

	}

	// private static final String REGISTER_PORTAL = "{ call
	// pkg_portal.register_portal(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	//
	// public BAbonentPortalInfo register13Guid(String guid, String codeWord)
	// throws BRegistrationException {
	//
	// log.info("registering with guid " + guid);
	// CallableStatement cstmt = null;
	// Connection conn = null;
	//
	// String guids;
	// String fullName;
	// String account;
	// String phones;
	// String category;
	// String identityCardNo;
	// Calendar identityCardDate;
	// String address;
	// String login;
	// String email;
	// String emailSubscr;
	// String emailType;
	// String branch;
	// String passwd;
	// String rnn;
	//
	// BAbonentPortalInfo portalInfo = new BAbonentPortalInfo();
	// // BRegistrationInfo13 registrationInfo = new BRegistrationInfo13();
	// // registrationInfo.setName(phone);
	// try {
	// conn = dataSource.getConnection();
	// cstmt = conn.prepareCall(REGISTER_PORTAL);
	// cstmt.setString(1, guid);
	// cstmt.setString(2, codeWord);
	// cstmt.setInt(3, 1);
	// cstmt.registerOutParameter(4, Types.VARCHAR);
	// cstmt.registerOutParameter(5, Types.VARCHAR);
	// cstmt.registerOutParameter(6, Types.VARCHAR);
	// cstmt.registerOutParameter(7, Types.VARCHAR);
	// cstmt.registerOutParameter(8, Types.VARCHAR);
	// cstmt.registerOutParameter(9, Types.VARCHAR);
	// cstmt.registerOutParameter(10, Types.VARCHAR);
	// cstmt.registerOutParameter(11, Types.VARCHAR);
	// cstmt.registerOutParameter(12, Types.VARCHAR);
	// cstmt.registerOutParameter(13, Types.VARCHAR);
	//
	// cstmt.execute();
	//
	// guids = guid;
	// fullName = cstmt.getString(4);
	// account = cstmt.getString(5);
	// phones = cstmt.getString(6);
	// category = cstmt.getString(7);
	// identityCardNo = cstmt.getString(8);
	// // identityCardDate;
	// address = cstmt.getString(9);
	// // login;
	// email = cstmt.getString(10);
	// // emailSubscr;
	// emailType = cstmt.getString(11);
	// branch = cstmt.getString(12);
	// // passwd;
	// rnn = cstmt.getString(13);
	//
	// portalInfo.setAccount(account);
	// portalInfo.setAddress(address);
	// portalInfo.setBranch(branch);
	// portalInfo.setCategory(category);
	// portalInfo.setEmail(email);
	// portalInfo.setEmailType(emailType);
	// portalInfo.setFullName(fullName);
	// portalInfo.setGuids(guids);
	// portalInfo.setIdentityCardNo(identityCardNo);
	// portalInfo.setPhones(phones);
	// portalInfo.setRnn(rnn);
	//			
	//			
	// log.debug("GUID = " + guid);
	// } catch (SQLException e) {
	// log.error("Could not check account", e);
	// throw new BRegistrationException(
	// SpringBeansConstants.ERR_SYSTEM,
	// SpringBeansConstants.REGISTRATION_ERROR
	// + StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
	// "Could not register for guid " + guid, e);
	// } finally {
	// CommonOperations.closeStatement(cstmt);
	// CommonOperations.closeConnection(conn);
	// }
	// return portalInfo;
	//
	// }

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public ConverterManager getConverter() {
		return converter;
	}

	public void setConverter(ConverterManager converter) {
		this.converter = converter;
	}

}