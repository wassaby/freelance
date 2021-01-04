/*
 * Created on 27.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAccountImpl.java,v 1.1 2014/07/01 11:58:22 dauren_work Exp $
 */
package com.realsoft.commons.beans.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.login.BLoginException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BAccountImpl implements IBAccount {

	private static Logger log = Logger.getLogger(BAccountImpl.class);

	private BasicDataSource dataSource = null;

	private BasicDataSource dataSourceAmdocs = null;

	public BAccountImpl() {
		super();
	}

	private static String ACCOUNT_LIST_STMT = new StringBuffer().append(
			"select val.id, ").append("val.login, ").append(
			"val.account_type, ").append("val.reg_date ").append(
			"from view_account_list val ").append("where val.lt_id = ?")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#getAccountList(long)
	 */
	public List<BAccountInfo> getAccountList(long linkType)
			throws BAccountException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting account list for linkType=" + linkType);
			conn = dataSource.getConnection();
			log.debug("stmt = " + ACCOUNT_LIST_STMT);
			pstmt = conn.prepareStatement(ACCOUNT_LIST_STMT);
			pstmt.setLong(1, linkType);
			ResultSet rs = pstmt.executeQuery();
			List<BAccountInfo> accountList = new ArrayList<BAccountInfo>();
			while (rs.next()) {
				accountList.add(new BAccountInfo(rs.getLong(1),
						rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
			}
			log
					.debug("Getting account list done: size = "
							+ accountList.size());
			return accountList;
		} catch (SQLException e) {
			log.error("Could not get account list for linkType " + linkType, e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_PERIOD_ACCOUNT_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get account list for linkType " + linkType, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CREATE_ACC_PSTMT = "{? = call un.pkg_account_usr.set_account_usr_all( ?, ?, ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#createAccount(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */

	public long createAccount(String pinCode, String loginRad,
			String passwordRad, String loginWeb, String passwordWeb,
			String question, String answer, String note)
			throws BAccountException {

		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("Creating account, pinCode = " + pinCode
					+ "; loginRad = " + loginRad + "; passwordRad = "
					+ passwordRad + "; loginWeb = " + loginWeb
					+ "; passwordWeb = " + passwordWeb + "; question = "
					+ question + "; answer = " + answer + "; note = " + note);
			log.debug("CREATE_ACC_PSTMT = "+CREATE_ACC_PSTMT);
			conn = dataSourceAmdocs.getConnection();
			cstmt = conn.prepareCall(CREATE_ACC_PSTMT);

			cstmt.setString(2, pinCode);
			cstmt.setString(3, loginRad);
			cstmt.setString(4, passwordRad);
			cstmt.setString(5, loginWeb);
			cstmt.setString(6, passwordWeb);
			cstmt.setString(7, question);
			cstmt.setString(8, answer);
			cstmt.setString(9, note);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.execute();
			log.debug("Creating account done by pinCode = " + pinCode);
			return cstmt.getLong(1);
		} catch (SQLException e) {
			log.error("Could not create account by pinCode = " + pinCode, e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_ACCOUNT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not create account", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CREATE_ACC_MACADDR_PSTMT = "{ call un.pkg_ivision.activate_subscriber( ?, ?, ?, ?, ?, ? ) }";

	public BAccountInfo createAccount(BAccountInfo accountInfo)
			throws BAccountException {
		try {
			log.debug("Creating account, CREATE_ACC_MACADDR_PSTMT = "
					+ CREATE_ACC_MACADDR_PSTMT);
			log.debug("pinCode = " + accountInfo.getCodeWord()
					+ "; macAddress = " + accountInfo.getMacAddress()
					+ "; login = " + accountInfo.getLogin() + "; password = "
					+ accountInfo.getPassword());
			Connection conn = null;
			CallableStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareCall(CREATE_ACC_MACADDR_PSTMT);

				cstmt.setString(1, accountInfo.getCodeWord());
				cstmt.registerOutParameter(2, Types.NUMERIC);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.setString(4, accountInfo.getMacAddress());
				cstmt.setString(5, accountInfo.getLogin());
				cstmt.setString(6, accountInfo.getPassword());
				cstmt.execute();

				accountInfo.setLinkTypeId(cstmt.getLong(2));
				accountInfo.setProductCode(cstmt.getString(3));
				log.debug("Creating account done");
				return accountInfo;
			} finally {
				CommonOperations.closeStatement(cstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not create account", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_ACCOUNT_ERROR + "."
							+ e.getErrorCode(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Could not create account", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.account.createAccount.error",
					e.getMessage(), e);
		}
	}

	private static final String LOAD_ACC_STMT = new StringBuffer().append(
			"select au.login, ").append("au.question, ").append("au.answer, ")
			.append("pt.provider_id, ").append("pt.name tariff_plan, ").append(
					"a.web_interface_type_id, ").append("au.link_type_id ")
			.append("from db.account_usr au, ").append("db.link_type lt, ")
			.append("tr.packet_type pt, ").append("db.abonent a, ").append(
					"db.link l ").append("where au.link_type_id = lt.id ")
			.append("and lt.abonent_id = a.id ").append(
					"and l.link_type_id = lt.id ").append(
					"and l.packet_type_id = pt.id ").append(
					"and sysdate >= l.date_from ").append(
					"and sysdate < nvl(l.date_to, sysdate+1) ").append(
					"and au.id = ?").toString();

	private static final String LOAD_RADIUS_STMT = new StringBuffer().append(
			"select au.id, au.login ").append("from db.account_usr au ")
			.append("where au.link_type_id = ? ").append(
					" and sysdate >= au.from_date ").append(
					"and sysdate < nvl(au.to_date, sysdate+1) ").append(
					"and au.account_usr_type_id = 0").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.components.editacc.ICEditAccount#loadAccountParameters()
	 */
	public BAccountInfo loadAccountParameters(long accountId)
			throws BAccountException {
		log.debug("accountId = " + accountId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("loadAccountParameters running...");
			conn = dataSource.getConnection();
			log.debug("LOAD_ACC_STMT = " + LOAD_ACC_STMT);
			log.debug("IN_PARAMETER (accountId) = " + accountId);
			pstmt = conn.prepareStatement(LOAD_ACC_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			BAccountInfo accountInfo = null;
			long linkType;
			if (rs.next()) {
				accountInfo = new BAccountInfo(rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getLong(4), rs
								.getString(5), rs.getLong(6));
				log.debug("answer = " + rs.getString(2) + "; question = "
						+ rs.getString(3));
				log.debug("answer = " + accountInfo.getAnswer()
						+ "; question = " + accountInfo.getQuestion());
				linkType = rs.getLong(7);
			} else {
				throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.EDIT_ACCOUNT_ERROR,
						"No such account with id " + accountId);
			}
			pstmt.close();
			log.debug("LOAD_RADIUS_STMT = " + LOAD_RADIUS_STMT);
			log.debug("IN_PARAMETER (linkType) = " + linkType);
			pstmt = conn.prepareStatement(LOAD_RADIUS_STMT);
			pstmt.setLong(1, linkType);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				accountInfo.setRadiusId(rs.getLong(1));
				accountInfo.setRadiusLogin(rs.getString(2));
			}// else throw new
			// BAccountException("login.error.no-active-radius-accounts","login.error.no-active-radius-accounts.mesage","There
			// are no active Radius Accounts");
			return accountInfo;
		} catch (SQLException e) {
			log.debug("Could not load account parameters", e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.EDIT_ACCOUNT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not load account parameters", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String UPDATE_ACC_PSTMT = "{ call un.pkg_account_usr.update_account_usr( ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.components.editacc.ICEditAccount#editAccount(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void editAccount(String login, long accountId, String password,
			String question, String answer, String note)
			throws BAccountException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.info("UPDATE_ACC_PSTMT = " + UPDATE_ACC_PSTMT);
			log.info("Editing account " + login + " ...");
			log.debug("password = " + password + "; question = " + question
					+ "; answer = " + answer + "; accountId = " + accountId);
			conn = dataSourceAmdocs.getConnection();
			cstmt = conn.prepareCall(UPDATE_ACC_PSTMT);
			cstmt.setString(1, password);
			cstmt.setString(2, question);
			cstmt.setString(3, answer);
			cstmt.setString(4, note);
			cstmt.setLong(5, accountId);
			cstmt.execute();
			log.debug("Editing account " + login + " done");
		} catch (SQLException e) {
			log.error("Could not edit account", e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.EDIT_ACCOUNT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not edit account", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String EDIT_ACC_PSTMT = "{ call %s.pkg_account_usr.set_account_pass( ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#editAccount(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void editAccount(long accountId, String password, String schema)
			throws BAccountException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.info("Editing account ...");
			log.debug("id = " + accountId);
			conn = dataSource.getConnection();
			Formatter formatter = new Formatter().format(Locale.getDefault(),
					EDIT_ACC_PSTMT, schema);
			String edirAccStmt = formatter.toString();
			formatter.close();
			log.debug("editAccStmt = " + edirAccStmt);
			cstmt = conn.prepareCall(edirAccStmt);
			cstmt.setLong(1, accountId);
			cstmt.setString(2, password);
			cstmt.execute();
			log.debug("Editing account done");
		} catch (SQLException e) {
			log.error("Could not edit account", e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.EDIT_ACCOUNT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not edit account", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String MAC_AADRESS_CSTMT = "{ ? = call db.pkg_warning.get_mac_count_for_acc_usr( ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#getLastMacAddresses(long)
	 */
	public long getLastMacAddresses(long accountId) throws BAccountException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.info("Getting mac addresses count ...");
			log.debug("id = " + accountId);
			conn = dataSource.getConnection();
			log.debug("MAC_AADRESS_CSTMT = " + MAC_AADRESS_CSTMT);
			cstmt = conn.prepareCall(MAC_AADRESS_CSTMT);
			cstmt.setLong(2, accountId);
			cstmt.registerOutParameter(1, Types.NUMERIC);
			cstmt.execute();
			log.debug("Getting mac addresses count done");
			return cstmt.getLong(1);
		} catch (SQLException e) {
			log.error("Could not get mac addresses count", e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAC_ADDRESS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get mac addresses count", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_ACCID_LOGIN = new StringBuffer().append(
			"select au.id ").append("from db.account_usr au ").append(
			"where au.login = ? and sysdate < nvl(au.to_date, sysdate+1) ")
			.append("and au.account_usr_type_id = 2").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#getAccountId(java.lang.String)
	 */
	public long getAccountId(String userName) throws BLoginException {
		Connection conn = null;
		log.debug("Getting accountId of user " + userName + " ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareStatement(GET_ACCID_LOGIN);
				cstmt.setString(1, userName);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get accountId of user " + userName, e);
			throw new BLoginException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.ACCOUNT_ABCENT_ERROR, "No such User "
						+ userName + " has been registered.");
	}

	private static String GET_LOGIN_ACCID = new StringBuffer().append(
			"select au.login ").append("from db.account_usr au ").append(
			"where au.id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#getLoginAccountId(long)
	 */
	public String getLoginAccountId(long accountId) throws BLoginException {
		Connection conn = null;
		log.debug("Getting user name of accountId " + accountId + " ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareStatement(GET_LOGIN_ACCID);
				cstmt.setLong(1, accountId);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get user name of accountId " + accountId, e);
			throw new BLoginException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR, "No such account "
						+ accountId + " has been registered.");
	}

	private static final String GET_MAC_ADDRESS_OF_ABONENT_STMT = "select id, login, from_date from db.account_usr where link_type_id = ? and account_usr_type_id = 7 and sysdate < nvl(to_date, sysdate+1)";

	public BAccountInfo getMacAddress(long linkTypeId) throws BAccountException {
		Connection conn = null;
		log.debug("Getting MAC-address of linkType = " + linkTypeId + " ...");
		log.debug("GET_MAC_ADDRESS_OF_ABONENT_STMT = "
				+ GET_MAC_ADDRESS_OF_ABONENT_STMT);
		BAccountInfo result = new BAccountInfo();
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareStatement(GET_MAC_ADDRESS_OF_ABONENT_STMT);
				cstmt.setLong(1, linkTypeId);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					result.setId(rs.getLong(1));
					result.setLogin(rs.getString(2));
					result.setMacAddressUndefined(false);
					result.setRegistrationDate(new Date(rs.getTimestamp(3)
							.getTime()));
					return result;
				}
				result.setLogin("");
				result.setMacAddressUndefined(true);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get MAC-address of linkType = " + linkTypeId,
					e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		return result;
	}

	private static final String SET_MAC_ADDRESS_OF_ABONENT_STMT = "{ ? = call db.pkg_device.set_account_usr( ?, ?, ?, ?, ?, safety.pkg_usr.get_user_id , ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) }";

	public void setMacAddress(Long accountId, long linkTypeId,
			String macAddress, boolean closeMacAddress)
			throws BAccountException {
		log.debug("Calendar.getInstance().getTime().getTime() = "
				+ Calendar.getInstance().getTime());
		if (accountId == null)
			log.debug("Creating mac-address...");
		else
			log.debug("Updating mac-address...");
		log.debug("SET_MAC_ADDRESS_OF_ABONENT_STMT = "
				+ SET_MAC_ADDRESS_OF_ABONENT_STMT);
		if (accountId != null)
			log.debug("accountId = " + accountId.longValue());
		else
			log.debug("accountId = null");
		log
				.debug("linkTypeId = " + linkTypeId + "; macAddress = "
						+ macAddress);
		Timestamp fromDate = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(SET_MAC_ADDRESS_OF_ABONENT_STMT);
			cstmt.registerOutParameter(1, Types.NUMERIC);
			if (accountId == null) {
				fromDate = new Timestamp(Calendar.getInstance().getTime()
						.getTime());
				cstmt.setNull(2, Types.NUMERIC);
			} else {
				try {
					log
							.debug("trying to get current account state, accountId = "
									+ accountId + "...");
					BAccountInfo accountInfo = getAccountInfo(accountId, conn);
					fromDate = new Timestamp(accountInfo.getFromDate()
							.getTime());
				} catch (BAccountException e) {
					log
							.error("could not get current account state, accountId = "
									+ accountId);
					log.debug("setting fromDate to system date...");
					fromDate = new Timestamp(Calendar.getInstance().getTime()
							.getTime());
				}
				cstmt.setLong(2, accountId.longValue());
			}
			cstmt.setLong(3, linkTypeId);
			cstmt.setString(4, macAddress);
			cstmt.setString(5, "1");// password
			cstmt.setLong(6, 0);// service_id
			cstmt.setLong(7, 7);// account_usr_type_id = 7 (mac-address)
			cstmt.setTimestamp(8, fromDate);
			if (closeMacAddress) {
				cstmt.setTimestamp(9, new Timestamp(Calendar.getInstance()
						.getTime().getTime()));
			} else {
				cstmt.setNull(9, Types.TIMESTAMP);
			}
			cstmt.setLong(10, 0);// crypt_type_id
			cstmt.setLong(11, 0);
			cstmt.setLong(12, 1);
			cstmt.setNull(13, Types.VARCHAR);
			cstmt.setNull(14, Types.VARCHAR);
			cstmt.setNull(15, Types.VARCHAR);
			cstmt.setNull(16, Types.NUMERIC);
			cstmt.execute();
			if (accountId == null)
				log.debug("Creating mac-address done");
			else
				log.debug("Updating mac-address done");
			log.debug("return value = " + cstmt.getLong(1));
			CommonOperations.commitConnection(conn);
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			String action;
			if (accountId == null) {
				log.error("Could not create mac-address", e);
				action = "create";
			} else {
				log.error("Could not update mac-address", e);
				action = "update";
			}
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_MAC_ADDRESS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not " + action + " mac-address", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}

	}

	private static final String GET_ACCOUNT_INFO_STATEMENT = new StringBuffer()
			.append("select au.id,au.login,aut.name,au.account_usr_type_id, ")
			.append("au.from_date,safety.pkg_usr.decrypt(au.password), ")
			.append(
					"au.question,au.answer from db.account_usr au, db.account_usr_type aut ")
			.append("where aut.id = au.account_usr_type_id and au.id = ? ")
			.toString();

	private BAccountInfo getAccountInfo(long accountId, Connection conn)
			throws BAccountException {
		log.debug("Getting account info, id = " + accountId);
		log.debug("GET_ACCOUNT_INFO_STATEMENT = " + GET_ACCOUNT_INFO_STATEMENT);
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = conn
					.prepareStatement(GET_ACCOUNT_INFO_STATEMENT);
			preparedStatement.setLong(1, accountId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				BAccountInfo accountInfo = new BAccountInfo();
				accountInfo.setId(rs.getLong(1));
				accountInfo.setLogin(rs.getString(2));
				accountInfo.setAccountType(rs.getString(3));
				accountInfo.setAccountTypeId(rs.getLong(4));
				accountInfo.setFromDate(new Date(rs.getTimestamp(5).getTime()));
				accountInfo.setPassword(rs.getString(6));
				accountInfo.setQuestion(rs.getString(7));
				accountInfo.setAnswer(rs.getString(8));
				return accountInfo;
			}
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_MAC_ADDRESS_ERROR,
					"Could not get account info, account with id = "
							+ accountId + " doesn't exists");
		} catch (SQLException e) {
			log.error("Could not get account info", e);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_MAC_ADDRESS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get account info", e);
		} finally {
			CommonOperations.closeResultSet(rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.account.IBAccount#getAccountInfo(long)
	 */
	public BAccountInfo getAccountInfo(long accountId) throws BAccountException {
		log.debug("Getting account info, id = " + accountId);
		log.debug("GET_ACCOUNT_INFO_STATEMENT = " + GET_ACCOUNT_INFO_STATEMENT);
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return getAccountInfo(accountId, conn);
		} catch (SQLException e) {
			log.error("Could not get account info", e);
			CommonOperations.rollbackConnection(conn);
			throw new BAccountException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREATE_MAC_ADDRESS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get account info", e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CHECK_IPTV_SERVICE_PSTMT = "{ ? = call un.pkg_ivision.check_service( ?, ?, ? ) }";

	public double checkIPTvService(BAccountInfo accountInfo,
			BServiceInfo serviceInfo) throws BAccountException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("checking IPTVService, CHECK_IPTV_SERVICE_PSTMT = "
					+ CHECK_IPTV_SERVICE_PSTMT);
			log.debug("serviceCode = " + serviceInfo.getServiceCode()
					+ "; linkType = " + accountInfo.getLinkTypeId()
					+ "; volume = " + serviceInfo.getVolume());
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(CHECK_IPTV_SERVICE_PSTMT);

			cstmt.setString(2, serviceInfo.getServiceCode());
			cstmt.setLong(3, accountInfo.getLinkTypeId());
			cstmt.setDouble(4, serviceInfo.getVolume());
			cstmt.registerOutParameter(1, Types.NUMERIC);
			cstmt.execute();
			log.debug("checking IPTVService done");
			return cstmt.getDouble(1);
		} catch (SQLException e) {
			log.error("Could not check IPTVService", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CHECH_SERVICE_ERROR + "."
							+ e.getErrorCode(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Could not check IPTVService", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.account.checkIPTvService.error", e
							.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String SET_IPTV_SERVICE_PSTMT = "{ call un.pkg_ivision.set_service( ?, ?, ? ) }";

	public void setIPTvService(BAccountInfo accountInfo,
			BServiceInfo serviceInfo) throws BAccountException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("checking IPTVService, SET_IPTV_SERVICE_PSTMT = "
					+ SET_IPTV_SERVICE_PSTMT);
			log.debug("serviceCode = " + serviceInfo.getServiceCode()
					+ "; linkTypeId = " + accountInfo.getLinkTypeId()
					+ "; volume = " + serviceInfo.getVolume());
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(SET_IPTV_SERVICE_PSTMT);

			cstmt.setString(1, serviceInfo.getServiceCode());
			cstmt.setLong(2, accountInfo.getLinkTypeId());
			cstmt.setDouble(3, serviceInfo.getVolume());
			cstmt.execute();
			log.debug("setting IPTVService done");
		} catch (SQLException e) {
			log.error("Could not set IPTVService", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SET_SERVICE_ERROR + "."
							+ e.getErrorCode(), e.getMessage(), e);
		} catch (Exception e) {
			log.error("Could not set IPTVService", e);
			throw new BAccountException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.account.setIPTvService.error", e
							.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public BasicDataSource getDataSourceAmdocs() {
		return dataSourceAmdocs;
	}

	public void setDataSourceAmdocs(BasicDataSource dataSourceAmdocs) {
		this.dataSourceAmdocs = dataSourceAmdocs;
	}

}