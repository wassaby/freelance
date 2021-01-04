/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.mail.BMailException;
import com.realsoft.commons.beans.mail.IBMail;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.request.BComboBoxItem;
import com.realsoft.commons.beans.request.BRequestException;
import com.realsoft.commons.beans.request.BTariffTypeItem;
import com.realsoft.commons.beans.request.IBRequest;
import com.realsoft.commons.beans.util.BUtilException;
import com.realsoft.commons.beans.util.IBUtil;
import com.realsoft.commons.utils14.RealsoftException;
import com.realsoft.commons.utils14.hash.HashCalculator;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

/**
 * Реализация компоненты {@link IBAbonent}. Использует компоненту
 * {@link IBRegistration}
 * 
 * @author dimad
 */
public class BAbonentImpl implements IBAbonent, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BAbonentImpl.class);

	private BeanFactory beanFactory = null;

	private BasicDataSource dataSource = null;

	private IFormatter formatter = null;

	public BAbonentImpl() {
		super();
	}

	private static String ABONENT_INFO_BY_ID_STMT = new StringBuffer().append(
			"select a.id, ").append("a.name, ").append(
			"substr(db.get_adr(adr.id, 1, 1), 1, 80) adrname, ").append(
			"at.name atname, ").append("lpad(a.rnn, 12, '0') rnn, ").append(
			"a.passport, ").append("at.id atid, ").append("adr.id adrid, ")
			.append("ag.id, ").append("t.id town_id, ").append(
					"t.name town_name ").append("from db.abonent          a, ")
			.append("db.address          adr, ")
			.append("db.abonent_group ag, ").append("db.town             t, ")
			.append("db.abonent_type     at, ").append(
					"db.street           s, ")
			.append("db.street_type      st ").append("where a.id = ? ")
			.append("and adr.id = a.address_id ").append(
					"and s.id = adr.street_id ").append(
					"and st.id = s.type_id ").append("and t.id = s.town_id ")
			.append("and at.id = a.abonent_type_id ").append(
					"and ag.default_abonent_type_id = at.id").toString();

	private static final String ABONENT_NFO_BY_EXISTING_ORDER_STMT = new StringBuffer()
			.append(
					"select a.id, a.name, substr(db.get_adr(adr.id, 1, 1), 1, 80) adrname, ")
			.append(
					"at.name atname, lpad(a.rnn, 12, '0') rnn, a.passport, at.id atid, ")
			.append(
					"adr.id adrid, ag.id, t.id town_id, t.name town_name from db.abonent a, ")
			.append("db.address       adr, db.abonent_group ag, db.town t, ")
			.append(
					"db.abonent_type  at, db.street s, db.street_type   st, db.order_device od ")
			.append(
					"where od.abonent_id = a.id and ag.default_abonent_type_id = at.id and adr.id = a.address_id ")
			.append(
					"and s.id = adr.street_id and st.id = s.type_id and t.id = s.town_id ")
			.append("and at.id = a.abonent_type_id and od.id = ? ").toString();

	private static String GET_ALL_ABONENT_DEVICES_STMNT = new StringBuffer()
			.append("select d.*, l.link_type_id ").append(
					"from db.abonent ab, db.device d, db.link l ").append(
					"where l.abonent_id = ab.id ").append(
					"and l.device_id = d.id ").append("and l.closed = 0 ")
			.append("and d.connect_type_id in ( %s ) ")
			.append("and ab.id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentDevices(long)
	 */

	public List<BAbonentDeviceItem> getAbonentDevices(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		IBRequest requestComponent = (IBRequest) beanFactory
				.getBean(ComponentFactoryImpl.getBRequestName());
		try {
			conn = dataSource.getConnection();
			String deviceFilter = requestComponent
					.getConstValue("KIOSK_CONNECT_TYPE_PHONE");
			String getAllAbonentDevices = formatter.format(
					GET_ALL_ABONENT_DEVICES_STMNT, deviceFilter);
			log.debug("getAllAbonentDevices = " + getAllAbonentDevices);
			pstmt = conn.prepareStatement(getAllAbonentDevices);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			List<BAbonentDeviceItem> result = new ArrayList<BAbonentDeviceItem>();
			while (rs.next()) {
				result.add(new BAbonentDeviceItem(rs.getLong(1), rs
						.getString(2), rs.getLong(3), rs.getLong(4), rs
						.getLong(5), rs.getLong(6), -1));
			}
			return result;
		} catch (SQLException e) {
			log.error("Could not get abonent devices", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (BRequestException re) {
			log.error("Could not get abonent devices", re);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils
									.getOraErrorCodeDotend(re.getMessage())[0],
					re.getMessage(), re);
		} catch (UtilsException ue) {
			log.error("Could not get abonent devices", ue);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils
									.getOraErrorCodeDotend(ue.getMessage())[0],
					ue.getMessage(), ue);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public List<BAbonentDeviceItem> getAbonentDevices(long abonentId,
			String dbLinkName) throws BAbonentException, SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return getAbonentDevices(abonentId, dbLinkName, conn);
		} catch (SQLException e) {
			log.error("Could not get abonent devices", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ALL_ABONENT_DEVICES_STMT = new StringBuffer()
			.append(
					"select d.id, d.device, d.device_group_id, dg.name, d.connect_type_id, ct.name, d.town_id ")
			.append(
					"from db.device@%1$s d, db.device_group@%1$s dg, db.connect_type@%1$s ct, db.link@%1$s l where d.id = l.device_id ")
			.append(
					"and dg.id = d.device_group_id and ct.id = d.connect_type_id and l.flag = 0 and l.closed = 0 ")
			.append("and l.abonent_id = ? ").toString();

	private List<BAbonentDeviceItem> getAbonentDevices(long abonentId,
			String dbLinkName, Connection conn) throws BAbonentException,
			SQLException {
		log.debug("Trying to get all abonent devices, abonentId = " + abonentId
				+ "; dbLinkName = " + dbLinkName + " ...");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String statement = formatter.format(GET_ALL_ABONENT_DEVICES_STMT,
					new Object[] { dbLinkName });
			log.debug("GET_ALL_ABONENT_DEVICES_STMT = " + statement);
			pstmt = conn.prepareStatement(statement);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			List<BAbonentDeviceItem> result = new ArrayList<BAbonentDeviceItem>();
			while (rs.next()) {
				BAbonentDeviceItem abonentDeviceItem = new BAbonentDeviceItem();
				abonentDeviceItem.setId(rs.getLong(1));
				abonentDeviceItem.setDevice(rs.getString(2));
				abonentDeviceItem.setDeviceGroupId(rs.getLong(3));
				abonentDeviceItem.setDeviceGroupName(rs.getString(4));
				abonentDeviceItem.setConnectTypeId(rs.getLong(5));
				abonentDeviceItem.setConnectTypeName(rs.getString(6));
				abonentDeviceItem.setTownId(rs.getLong(7));
				result.add(abonentDeviceItem);
			}
			return result;
		} catch (SQLException e) {
			log.error("Could not get abonent devices", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could not format statement for all abonent devices", e);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentDevices(java.lang.String)
	 */

	public List<BAbonentDeviceItem> getAbonentDevices(String abonentGuid)
			throws BAbonentException {
		String[] guids = abonentGuid.split("-");
		long abonentId = Long.valueOf(guids[1]);
		return getAbonentDevices(abonentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo13ById(long,
	 *      java.lang.String, long)
	 */
	public BAbonentInfo13 getAbonentInfo13ById(long townId, String phone,
			long abonentId) throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		log.info("getting abonentinfo abonetId=" + abonentId + " phone = "
				+ phone + " ...");

		BRegistrationInfo13 registrationInfo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			IBRegistration registration = (IBRegistration) beanFactory
					.getBean(ComponentFactoryImpl.getBRegistrationName());
			registrationInfo = registration.register13(phone);

			log.debug("abonentId = " + registrationInfo.getAbonentId());
			if (registrationInfo != null
					&& registrationInfo.getAbonentId() != abonentId)
				throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.ABONENT_ABCENT_ERROR,
						"No such abonent found");

			conn = dataSource.getConnection();
			log.debug("ABONENT_INFO_BY_ID_STMT = " + ABONENT_INFO_BY_ID_STMT);
			pstmt = conn.prepareStatement(ABONENT_INFO_BY_ID_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String abonentName = rs.getString(2);
				log.debug("abonentName = " + abonentName);
				log.info("getting abonent info done");
				BAbonentInfo13 abonentInfo = new BAbonentInfo13();
				abonentInfo.setAbonentId(rs.getLong(1));
				abonentInfo.setAbonentName(rs.getString(2));
				abonentInfo.setAddressName(rs.getString(3));
				abonentInfo.setAbonentTypeName(rs.getString(4));
				abonentInfo.setRnn(rs.getString(5));
				abonentInfo.setPassword(rs.getString(6));
				abonentInfo.setAbonentTypeId(rs.getLong(7));
				abonentInfo.setAddressId(rs.getLong(8));
				abonentInfo.setAbonentGroupId(rs.getLong(9));
				abonentInfo.setTownId(rs.getLong(10));
				abonentInfo.setTownName(rs.getString(11));
				IBRequest request = (IBRequest) beanFactory
						.getBean(ComponentFactoryImpl.getBRequestName(),
								IBRequest.class);
				abonentInfo.setDevices(request.getDeviceList(townId, abonentId,
						1));
				IBMail mail = (IBMail) beanFactory.getBean(ComponentFactoryImpl
						.getBMailName(), IBMail.class);
				abonentInfo.setEmail(mail.getMailAddress13(abonentId));
				abonentInfo.setEmailAddresses(mail
						.getAllMailAddress13(abonentId));
				return abonentInfo;
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ABCENT_ERROR,
					"No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get abonent info", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo13ById(java.lang.String)
	 */

	public BAbonentInfo13 getAbonentInfo13ById(long abonentId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		log.debug("ABONENT_INFO_BY_ID_STMT = " + ABONENT_INFO_BY_ID_STMT);
		return getAbonentInfo(ABONENT_INFO_BY_ID_STMT, abonentId);
	}

	private static String ABONENT_NAME_BY_ID_STMT = new StringBuffer().append(
			"select a.id, a.name ").append("from db.abonent          a ")
			.append("where a.id = ? ").toString();

	public String getAbonentName13ById(String phone, long abonentId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ABONENT_NAME_BY_ID_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String abonentName = rs.getString(2);
				log.debug("abonentName = " + abonentName);
				log.info("getting abonent name done");
				return abonentName;
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ABCENT_ERROR,
					"No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get abonent info", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private BAbonentInfo13 getAbonentInfo(String statement, long id)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(statement);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String abonentName = rs.getString(2);
				log.debug("abonentName = " + abonentName);
				log.info("getting abonent info done");
				BAbonentInfo13 abonentInfo = new BAbonentInfo13();
				abonentInfo.setAbonentId(rs.getLong(1));
				abonentInfo.setAbonentName(rs.getString(2));
				abonentInfo.setAddressName(rs.getString(3));
				abonentInfo.setAbonentTypeName(rs.getString(4));
				abonentInfo.setRnn(rs.getString(5));
				abonentInfo.setPassword(rs.getString(6));
				abonentInfo.setAbonentTypeId(rs.getLong(7));
				abonentInfo.setAddressId(rs.getLong(8));
				abonentInfo.setAbonentGroupId(rs.getLong(9));
				abonentInfo.setTownId(rs.getLong(10));
				abonentInfo.setTownName(rs.getString(11));
				try {
					IBMail mail = (IBMail) beanFactory.getBean(
							ComponentFactoryImpl.getBMailName(), IBMail.class);
					abonentInfo.setEmail(mail.getMailAddress13(id));
					abonentInfo.setEmailAddresses(mail.getAllMailAddress13(id));
				} catch (CommonsBeansException e) {
					log.error("Could not get abonent e-mail", e);
				} catch (NoSuchBeanDefinitionException e) {
					log.error("Could not get abonent e-mail", e);
				}
				return abonentInfo;
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ABCENT_ERROR,
					"No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get abonent info", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ABONENT_DELIVERY_ADDRESS_STMNT = new StringBuffer()
			.append("select property_value ").append(
					"from db.abonent_properties ").append(
					"where property_id = (select const_value ").append(
					"from db.all_const where name = 'INV_ABN_FULL_ADDRESS') ")
			.append("and abonent_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getDeliveryAddress(long)
	 */

	public String getDeliveryAddress(long abonentId) throws BAbonentException {
		log.debug("getting abonent delivery address...");
		log.debug("abonentId = " + abonentId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("GET_ABONENT_DELIVERY_ADDRESS_STMNT = "
					+ GET_ABONENT_DELIVERY_ADDRESS_STMNT);
			pstmt = conn.prepareStatement(GET_ABONENT_DELIVERY_ADDRESS_STMNT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
			return null;
		} catch (SQLException e) {
			log.error("Could not get abonent delivery address", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo13ByName(long,
	 *      java.lang.String, java.lang.String)
	 */
	public BAbonentInfo13 getAbonentInfo13ByName(long townId, String phone,
			String name) throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		log.info("getting abonent info phone = " + phone + "; abonentName="
				+ name + "...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			IBRegistration registration = (IBRegistration) beanFactory
					.getBean(ComponentFactoryImpl.getBRegistrationName());
			BRegistrationInfo13 registrationInfo = registration
					.register13(phone);

			if (registrationInfo != null) {
				conn = dataSource.getConnection();
				log.debug("ABONENT_INFO_BY_ID_STMT = "
						+ ABONENT_INFO_BY_ID_STMT);
				pstmt = conn.prepareStatement(ABONENT_INFO_BY_ID_STMT);
				pstmt.setLong(1, registrationInfo.getAbonentId());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String abonentName = rs.getString(2).toLowerCase();
					log.debug("abonentName = " + abonentName);

					if (!abonentName.split(" ")[0].equalsIgnoreCase(name
							.split(" ")[0]))
						throw new BAbonentException(
								CommonsBeansConstants.ERR_SYSTEM,
								CommonsBeansConstants.ABONENT_ABCENT_ERROR,
								"No such abonent found");

					log.info("getting abonent info done");
					BAbonentInfo13 abonentInfo = new BAbonentInfo13();
					abonentInfo.setAbonentId(rs.getLong(1));
					abonentInfo.setAbonentName(rs.getString(2));
					abonentInfo.setAddressName(rs.getString(3));
					abonentInfo.setAbonentTypeName(rs.getString(4));
					abonentInfo.setRnn(rs.getString(5));
					abonentInfo.setPassword(rs.getString(6));
					abonentInfo.setAbonentTypeId(rs.getLong(7));
					abonentInfo.setAddressId(rs.getLong(8));
					abonentInfo.setAbonentGroupId(rs.getLong(9));
					abonentInfo.setTownId(rs.getLong(10));
					abonentInfo.setTownName(rs.getString(11));
					IBRequest request = (IBRequest) beanFactory.getBean(
							ComponentFactoryImpl.getBRequestName(),
							IBRequest.class);
					abonentInfo.setDevices(request.getDeviceList(townId,
							registrationInfo.getAbonentId(), 1));
					IBMail mail = (IBMail) beanFactory.getBean(
							ComponentFactoryImpl.getBMailName(), IBMail.class);
					abonentInfo.setEmail(mail.getMailAddress13(registrationInfo
							.getAbonentId()));
					return abonentInfo;
				}
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ABCENT_ERROR,
					"No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get abonent info", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}

	}

	private static String ALL_ABONENT_CONNECTIONS_STMT = new StringBuffer()
			.append("select lt.ID,").append("d.device NAME ").append(
					"from db.link_type lt,db.device d ").append(
					"where d.id = lt.device_id ").append(
					"and lt.abonent_id= ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAllAbonentConnections(long)
	 */
	public List<BAbonentItem> getAllAbonentConnections(long abonentId)
			throws BAbonentException {
		log.info("getting all abonen connections ...");
		log.debug("ALL_ABONENT_CONNECTIONS_STMT = "
				+ ALL_ABONENT_CONNECTIONS_STMT);
		log.debug("abonentId = " + abonentId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<BAbonentItem> retVal = new ArrayList<BAbonentItem>();

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ALL_ABONENT_CONNECTIONS_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new BAbonentItem(rs.getLong(1), 0, rs.getString(2)));
			}
			return retVal;
			// throw new BAbonentException(
			// SpringBeansConstants.ERR_SYSTEM,
			// SpringBeansConstants.ABONENT_ABCENT_ERROR,
			// "No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get all abonents", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String ABONENT_ADRESS_STMT = new StringBuffer().append(
			"select an.name ")
			.append("from db.abonent ab, db.address_name an ").append(
					" where an.id = ab.address_id ").append("and ab.id = ?")
			.toString();

	// private static String ABONENT_ADRESS_STMT = new StringBuffer().append(
	// "select db.pkg_abonent.get_address(t.address_id)").append(
	// " abonent_adress from db.abonent t where t.id= ?").toString();
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentAdress135(long)
	 */
	public String getAbonentAdress135(long abonentId) throws BAbonentException {
		log.info("getting abonen adress ...");
		log.debug("ABONENT_ADRESS_STMT = " + ABONENT_ADRESS_STMT);
		log.debug("abonentId = " + abonentId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ABONENT_ADRESS_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			// throw new BAbonentException(
			// SpringBeansConstants.ERR_SYSTEM,
			// SpringBeansConstants.ABONENT_ABCENT_ERROR,
			// "No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get abonent adress", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		return "-1";
	}

	private static String ADDRESS_STMT = new StringBuilder().append(
			"select an.name ").append("from db.address_name an, ").append(
			"db.abonent a ").append("where a.id = ? ").append(
			"and an.id = a.address_id").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAddress(long)
	 */
	public String getAddress(long abonentId) throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting address ... for abonent " + abonentId);
			conn = dataSource.getConnection();
			log.debug(ADDRESS_STMT);
			pstmt = conn.prepareStatement(ADDRESS_STMT);
			pstmt.setLong(1, abonentId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting address done");
				return rs.getString(1);
			}
		} catch (SQLException e) {
			log.error("Could not get address for abonent " + abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ADDRESS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get address for abonent " + abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.ADDRESS_ERROR,
				"Could not get address for abonent " + abonentId);
	}

	private static String ABONENT_NOTE_STMT = new StringBuffer()
			.append("select lt.id, l.note")
			.append(" from db.link_type lt, db.link l,")
			.append("db.device d where lt.id = l.link_type_id ")
			.append(
					"and lt.device_id = d.id and sysdate between l.date_from and nvl(l.date_to,sysdate) ")
			.append(" and lt.abonent_id= ? and d.device = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentNote(long,
	 *      java.lang.String)
	 */
	public String getAbonentNote(long abonentId, String connectName)
			throws BAbonentException {
		log.info("getting abonen note ...");
		log.debug("ABONENT_NOTE_STMT = " + ABONENT_NOTE_STMT);
		log.debug("abonentId = " + abonentId);
		log.debug("connectName = " + connectName);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ABONENT_NOTE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setString(2, connectName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(2);
			}
		} catch (SQLException e) {
			log.error("Could not get abonent note", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		return "-1";
	}

	private static String GET_LOGIN_STMT = "select t.loginname from safety.xusers t where t.id = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#changePwd13(java.lang.String,
	 *      long, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void changePwd13(String phone, long userId, String password,
			String question, String answer, String note)
			throws BAbonentException {
		log.debug("phone = " + phone + "; userId = " + userId + "; password = "
				+ password + "; question = " + question + "; answer = "
				+ answer + "; note = " + note);
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			log.debug(GET_LOGIN_STMT);
			pstmt = conn.prepareStatement(GET_LOGIN_STMT);
			pstmt.setLong(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				changePwd13(phone, rs.getString(1), password, question, answer,
						note);
			}
		} catch (SQLException e) {
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CHANGE_PASSWORD_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHANGE_PASSWORD_STMT = "{call safety.change_password104( ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#changePwd13(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void changePwd13(String phone, String login, String password,
			String question, String answer, String note)
			throws BAbonentException {
		log.debug("phone = " + phone + "; login = " + login + "; password = "
				+ password + "; question = " + question + "; answer = "
				+ answer + "; note = " + note);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			log.debug(CHANGE_PASSWORD_STMT);
			cstmt = conn.prepareCall(CHANGE_PASSWORD_STMT);
			cstmt.setString(1, password);
			cstmt.setString(2, login);
			cstmt.execute();
		} catch (SQLException e) {
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CHANGE_PASSWORD_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CHARGE_TO_ABONENT_FOR_PRINTING_STMT = "{call db.detail_print_tax(?, ?, ?, ?, ?, ?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#chargeForPrint(long)
	 */

	public double chargeForPrint(long abonentId, String device,
			long deviceGroupId, long townId, long pagesCount)
			throws BAbonentException {
		log.debug("charging to abonent for printing, abonentId = " + abonentId
				+ "; device = " + device + "; deviceGroupId = " + deviceGroupId
				+ "; townId = " + townId + "; pagesCount = " + pagesCount);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			log.debug("CHARGE_TO_ABONENT_FOR_PRINTING_STMT = "
					+ CHARGE_TO_ABONENT_FOR_PRINTING_STMT);
			cstmt = conn.prepareCall(CHARGE_TO_ABONENT_FOR_PRINTING_STMT);
			cstmt.setLong(1, abonentId);
			cstmt.setString(2, device);
			cstmt.setLong(3, deviceGroupId);
			cstmt.setLong(4, townId);
			cstmt.setLong(5, pagesCount);
			cstmt.registerOutParameter(6, Types.FLOAT);
			cstmt.execute();
			double chargedSumma = cstmt.getDouble(6);
			log.debug("charging successfully done, chargedSumma = "
					+ chargedSumma);
			return chargedSumma;
		} catch (SQLException e) {
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CHARGE_FOR_PRINT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.commitConnection(conn);
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#chandeDeliveryAddress(long,
	 *      java.lang.String, long)
	 */
	public void chandeDeliveryAddress(long abonentId, String newAddress,
			long operatorId) throws BAbonentException, BUtilException {
		return;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#changePwd13(long,
	 *      java.util.Calendar, java.util.Calendar, java.lang.String,
	 *      java.lang.String, long)
	 */
	public void changePwd13(long abonentId, Calendar dateFrom, Calendar dateTo,
			String password, long operatorId) throws BUtilException,
			BAbonentException {
		log.debug("abonentId = " + abonentId + "; operatorId = " + operatorId);
		try {
			String abonentPassword = getAbonentPassword(abonentId);
			log.debug("abonentPassword = " + abonentPassword);
			String hashPassword = HashCalculator.getMD5(password);
			IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
					.getBUtilName(), IBUtil.class);
			long propertyId = util.getDeviceGroupId("KIOSK_PASSWORD_PROP_ID");
			util.changeDeviceProperty(abonentId, propertyId, dateFrom, dateTo,
					hashPassword, 1, (Long) null, operatorId);
		} catch (RealsoftException e) {
			log.error("Could not calculate hash from password", e);
			throw new BAbonentException(e.getErrorCode(), e.getMessageKey(), e
					.getMessageText(), e);
		}
	}

	private static final String ACCOUNT_BALANCE_13_STMT = new StringBuffer()
			.append("select nvl(sum(out_money),0) ").append("from db.bill ")
			.append("where abonent_id = ? ").append(
					"and (ready=0 or out_money > 0)").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#abonentBalance13(java.lang.String,
	 *      long)
	 */
	public double abonentBalance13(String phone, long abonentId)
			throws BAbonentException {
		log.info("getting account balance phone=" + phone + "; accountId = "
				+ abonentId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug(ACCOUNT_BALANCE_13_STMT);
			pstmt = conn.prepareStatement(ACCOUNT_BALANCE_13_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			log.info("getting account balance done");
			if (rs.next())
				return rs.getDouble(1);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_ABCENT_ERROR,
					"No such account");
		} catch (SQLException e) {
			log.error("Could not get account balance", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_BALANCE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	// private static final String ACCOUNT_BALANCE_FOR_DATE_13_STMT = new
	// StringBuffer()
	// .append(
	// "select decode(sign(nvl(sum(out_money - credit), 0)),1,nvl(sum(out_money
	// - credit), 0),0) ")
	// .append(
	// "out_money from (select out_money, 0 credit from db.saldo where
	// abonent_id = ? ")
	// .append(
	// "and report_date_id = to_number(to_char(add_months(sysdate, -1), 'mm'))
	// and out_money > 0 ")
	// .append(
	// "union all select 0, (credit + change_credit + change_in_credit) from
	// db.saldo where ")
	// .append(
	// "abonent_id = ? and report_date_id = to_number(to_char(sysdate, 'mm')))")
	// .toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.components.abonent.ICAbonent#abonentBalance13(long,
	 *      java.util.Calendar)
	 */
	public double getAbonentBalance13(long abonentId) throws BAbonentException {
		log.info("getting balance for abonent  = " + abonentId);
		return abonentBalance13(null, abonentId);
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// try {
		// conn = dataSource.getConnection();
		// log.debug("ACCOUNT_BALANCE_FOR_DATE_13_STMT = "
		// + ACCOUNT_BALANCE_FOR_DATE_13_STMT);
		// pstmt = conn.prepareStatement(ACCOUNT_BALANCE_FOR_DATE_13_STMT);
		// pstmt.setLong(1, abonentId);
		// pstmt.setLong(2, abonentId);
		// rs = pstmt.executeQuery();
		// if (rs.next()) {
		// log.debug("balance = " + rs.getDouble(1));
		// log.info("getting abonent balance done");
		// return rs.getDouble(1);
		// }
		// throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
		// CommonsBeansConstants.ACCOUNT_ABCENT_ERROR,
		// "No such abonent");
		// } catch (SQLException e) {
		// log.error("Could not get abonent balance", e);
		// throw new BAbonentException(
		// CommonsBeansConstants.ERR_SYSTEM,
		// CommonsBeansConstants.ACCOUNT_BALANCE_ERROR
		// + StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
		// e.getMessage(), e);
		// } finally {
		// CommonOperations.closeStatement(pstmt);
		// CommonOperations.closeConnection(conn);
		// }
	}

	private static final String ACCOUNT_BALANCE_135_STMT = new StringBuffer()
			.append("{ ? = call un.pkg_from_bank.get_balance( ? ) }")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#abonentBalance135(long)
	 */
	public BAbonentBalanceInfo abonentBalance135(long abonentId)
			throws BAbonentException {
		log.info("getting account balance ...");
		log.debug("abonentId = " + abonentId);
		log.debug(ACCOUNT_BALANCE_135_STMT);
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(ACCOUNT_BALANCE_135_STMT);
			cstmt.setLong(2, abonentId);
			cstmt.registerOutParameter(1, Types.DOUBLE);
			cstmt.execute();
			log.info("getting account balance done");
			return new BAbonentBalanceInfo(cstmt.getDouble(1));
		} catch (SQLException e) {
			log.error("Could not get account balance", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_BALANCE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String ALL_ABONENTS_STMT = new StringBuffer().append(
			"select distinct t.id,").append("t.address_id, ").append(
			"substr(t.name,1,50) name ").append("from db.abonent t,").append(
			"safety.ua_link l,").append("safety.xusers  u ").append(
			"where l.abonent_id in (t.id, 0) ").append(
			"and l.user_id = u.id and t.id > 0 ").append("and u.loginname = ?")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAllAbonents(java.lang.String)
	 */
	public List<BAbonentItem> getAllAbonents(String operatorName)
			throws BAbonentException {
		log.info("getting all abonens operatorName = " + operatorName + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<BAbonentItem> retVal = new ArrayList<BAbonentItem>();

		try {
			conn = dataSource.getConnection();
			log.debug("ALL_ABONENTS_STMT = " + ALL_ABONENTS_STMT);
			pstmt = conn.prepareStatement(ALL_ABONENTS_STMT);
			pstmt.setString(1, operatorName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new BAbonentItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3)));
			}
			return retVal;
			// throw new BAbonentException(
			// SpringBeansConstants.ERR_SYSTEM,
			// SpringBeansConstants.ABONENT_ABCENT_ERROR,
			// "No such abonent found");
		} catch (SQLException e) {
			log.error("Could not get all abonents", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String ALL_STREETS_STMT = new StringBuffer().append(
			"select s.id, s.name||' '||st.name ").append(
			"from db.street s, db.street_type st ").append(
			"where s.type_id = st.id ").append(
			"and s.town_id = ? order by s.name").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAllStreetNames(long)
	 */

	public List<BComboBoxItem> getAllStreetNames(long townId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BComboBoxItem> result = new ArrayList<BComboBoxItem>();
		try {
			conn = dataSource.getConnection();
			log.debug("ALL_STREETS_STMT = " + ALL_STREETS_STMT);
			pstmt = conn.prepareStatement(ALL_STREETS_STMT);
			pstmt.setLong(1, townId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BComboBoxItem(rs.getLong(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			log.error("Could not get all abonents", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		return result;
	}

	private static String GET_CONSTANTLY_ABONENT_DADTA = new StringBuffer()
			.append("SELECT distinct AB.NAME AS ABNAME, ").append(
					"CP.NAME AS CPNAME, ").append("CP.PHONE AS CPPHONE,")
			.append("CP.EMAIL AS CPEMAIL,").append("CP.FAX AS FAX,")

			.append("nvl(T.ID, -101),").append("nvl(ST.ID,-101),").append(
					"decode(AD.HOUSE, null, -101, 1),").append(
					"decode(AD.SUB_HOUSE, null, -101, 1),").append(
					"decode(AD.FLAT, null, -101, 1),")
			.append("decode(AD.SUB_FLAT, null, -101, 1),")

			.append("T.NAME,")
			.append("ST.NAME,")
			.append("AD.HOUSE,")
			.append("AD.SUB_HOUSE,")
			.append("AD.FLAT,")
			.append("AD.SUB_FLAT ")

			// .append("AU.LOGIN,")
			// .append("AB.ID AS ABONENT_ID ")
			.append("FROM DB.ACCOUNT_USR    AU,").append(
					"DB.LINK_TYPE      LT,").append("DB.ABONENT        AB,")
			.append("DB.ADDRESS        AD,").append("DB.STREET         ST,")
			.append("DB.TOWN           T,").append("DB.CONTACT_PERSON CP ")
			.append("WHERE AB.ID = LT.ABONENT_ID ").append(
					"AND LT.ID = AU.LINK_TYPE_ID ").append(
					"AND AD.ID(+) = AB.ADDRESS_ID ").append(
					"AND ST.ID(+) = AD.STREET_ID ").append(
					"AND T.ID(+) = AB.TOWN_ID ").append(
					"AND CP.ABONENT_ID(+) = AB.ID ").append("and ab.id = ? ")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentConstantlyData(long)
	 */
	public List<BConstantlyDataItem> getAbonentConstantlyData(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		log.debug("Getting abonentId of abonent id = " + abonentId + " ...");
		log.debug("GET_CONSTANTLY_ABONENT_DADTA = "
				+ GET_CONSTANTLY_ABONENT_DADTA);
		List<BConstantlyDataItem> RetVal = new ArrayList<BConstantlyDataItem>();
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug("GET_CONSTANTLY_ABONENT_DADTA = "
						+ GET_CONSTANTLY_ABONENT_DADTA);
				cstmt = conn.prepareStatement(GET_CONSTANTLY_ABONENT_DADTA);
				cstmt.setLong(1, abonentId);
				ResultSet rs = cstmt.executeQuery();
				while (rs.next()) {
					RetVal.add(new BConstantlyDataItem(rs.getString(1), rs
							.getString(2), rs.getString(3), rs.getString(4), rs
							.getString(5), rs.getLong(6), rs.getLong(7), rs
							.getLong(8), rs.getLong(9), rs.getLong(10), rs
							.getLong(11), rs.getString(12), rs.getString(13),
							rs.getString(14), rs.getString(15), rs
									.getString(16), rs.getString(17)));
				}
				return RetVal;
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get constantly data of abonent id = "
					+ abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		}
	}

	private static String GET_ABONENT_ID_BY_LOGIN_STMNT = new StringBuffer()
			.append("select distinct ").append("v.abonent_id ").append(
					"from web.v_t2_hd_abonent_info v ").append(
					"where upper(v.login) = upper( ? )").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentIdByLogin(java.lang.String)
	 */
	public long getAbonentIdByLogin(String loginName) throws BAbonentException {
		Connection conn = null;
		log.debug("Getting abonentId of login " + loginName + " ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug(GET_ABONENT_ID_BY_LOGIN_STMNT);
				cstmt = conn.prepareStatement(GET_ABONENT_ID_BY_LOGIN_STMNT);
				cstmt.setString(1, loginName);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get abonentId of login " + loginName, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR,
				"No abonent corresponding to login " + loginName);
	}

	private static String GET_ABONID_LOGIN = new StringBuffer().append(
			"select lt.abonent_id ").append("from db.account_usr au, ").append(
			"db.link_type lt ").append("where au.login = ? ").append(
			"and au.account_usr_type_id = 2 ").append(
			"and lt.id = au.link_type_id").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentId(java.lang.String)
	 */
	public long getAbonentId(String userName) throws BAbonentException {
		Connection conn = null;
		log.debug("Getting abonentId of user " + userName + " ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug(GET_ABONID_LOGIN);
				cstmt = conn.prepareStatement(GET_ABONID_LOGIN);
				cstmt.setString(1, userName);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get abonentId of user " + userName, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR,
				"No abonent corresponding to User " + userName);
	}

	private static String GET_LOGIN_ABNID = new StringBuffer().append(
			"select au.login ").append("from db.account_usr au, ").append(
			"db.link_type lt ").append("where au.account_usr_type_id = 2 ")
			.append("and lt.id = au.link_type_id ").append(
					"and lt.abonent_id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getLoginAbonentId(long)
	 */
	public String getLoginAbonentId(long abonentId) throws BAbonentException {
		Connection conn = null;
		log.debug("Getting user name of abonentId " + abonentId + " ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug(GET_LOGIN_ABNID);
				cstmt = conn.prepareStatement(GET_LOGIN_ABNID);
				cstmt.setLong(1, abonentId);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR, "No such abonent "
							+ abonentId + " has been registered.");
		} catch (SQLException e) {
			log.error("Could not get user name of abonentId " + abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static String ABONENT_STMT = new StringBuilder().append(
			"select lt.abonent_id, ").append("a.name ").append(
			"from db.link_type lt, ").append("db.account_usr au, ").append(
			"db.abonent a ").append("where au.id = ? ").append(
			"and lt.id = au.link_type_id ").append("and a.id = lt.abonent_id")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentId(long)
	 */
	public Map<String, Object> getAbonentId(long accountId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting abonentId ...");
			conn = dataSource.getConnection();
			log.debug(ABONENT_STMT);
			pstmt = conn.prepareStatement(ABONENT_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting abonentId done");
				Map<String, Object> result = new HashMap<String, Object>();
				result.put(IBAbonent.ABONENT_ID, new Long(rs.getLong(1)));
				result.put(IBAbonent.ABONENT_NAME, rs.getString(2));
				return result;
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR,
					"No abonent id for account " + accountId);
		} catch (SQLException e) {
			log.error("Could not get abonent id for account " + accountId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonent id for account " + accountId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String ABONENT_INFO135_BYID_STMT = new StringBuffer()
			.append("select lt.abonent_id, ").append("a.name abonent_name, ")
			.append("a.town_id, ").append(
					"t.name town_name, nvl(a.alien_abonent_id, ").append(
					CommonsBeansConstants.ALIEN_ABONENT_ID_NOT_DEFINED).append(
					") from db.link_type lt, ").append("db.account_usr au, ")
			.append("db.abonent a, ").append("db.town t ").append(
					"where au.id = ? ").append("and lt.id = au.link_type_id ")
			.append("and a.id = lt.abonent_id ").append("and t.id = a.town_id")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo135(long)
	 */
	public BAbonentInfo135 getAbonentInfo135(long accountId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting abonentInfo135 ...");
			conn = dataSource.getConnection();
			log.debug(ABONENT_INFO135_BYID_STMT);
			pstmt = conn.prepareStatement(ABONENT_INFO135_BYID_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting abonentInfo135 done");
				return new BAbonentInfo135(
						rs.getLong(1),
						rs.getString(2),
						rs.getLong(3),
						rs.getString(4),
						rs.getLong(5) == CommonsBeansConstants.ALIEN_ABONENT_ID_NOT_DEFINED ? null
								: rs.getLong(5));
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR,
					"No abonent id for account " + accountId);
		} catch (SQLException e) {
			log.error("Could not get abonent id for account " + accountId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonent id for account " + accountId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String ABONENT_INFO135_BYNAME_STMT = new StringBuffer()
			.append("select lt.abonent_id, ").append("a.name abonent_name, ")
			.append("a.town_id, ").append(
					"t.name town_name, nvl(a.alien_abonent_id, ").append(
					CommonsBeansConstants.ALIEN_ABONENT_ID_NOT_DEFINED).append(
					") from db.link_type lt, ").append("db.account_usr au, ")
			.append("db.abonent a, ").append("db.town t ").append(
					"where au.login = ? ").append(
					"and lt.id = au.link_type_id ").append(
					"and a.id = lt.abonent_id ").append("and t.id = a.town_id")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo135(java.lang.String)
	 */
	public BAbonentInfo135 getAbonentInfo135(String accountName)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting abonentInfo135 ...");
			conn = dataSource.getConnection();
			log.debug(ABONENT_INFO135_BYNAME_STMT);
			pstmt = conn.prepareStatement(ABONENT_INFO135_BYNAME_STMT);
			pstmt.setString(1, accountName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting abonentInfo135 done");
				return new BAbonentInfo135(
						rs.getLong(1),
						rs.getString(2),
						rs.getLong(3),
						rs.getString(4),
						rs.getLong(5) == CommonsBeansConstants.ALIEN_ABONENT_ID_NOT_DEFINED ? null
								: rs.getLong(5));
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR,
					"No abonent id for account " + accountName);
		} catch (SQLException e) {
			log.error("Could not get abonent id for account " + accountName, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonent id for account " + accountName, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ALL_ABONENT_REQUESTS_STMNT = new StringBuffer()
			.append("select v.rid,").append("v.statename,").append(
					"v.insert_date,").append("v.close_date,")
			.append("v.cname,").append("v.servicedesc,").append("v.rmnote,")
			.append("v.smname,").append("v.rnote ").append(
					"from web.v_t2_hd_request_list v ").append(
					"where v.abonent_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentRequestsList(long)
	 */
	public List<BAbonentRequestsItem> getAbonentRequestsList(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BAbonentRequestsItem> retVal = new ArrayList<BAbonentRequestsItem>();
		try {
			log.debug("Getting abonents resquests ...");
			log.debug("input parameter (abonentId) = " + abonentId);
			conn = dataSource.getConnection();
			log.debug(GET_ALL_ABONENT_REQUESTS_STMNT);
			pstmt = conn.prepareStatement(GET_ALL_ABONENT_REQUESTS_STMNT);
			pstmt.setLong(1, abonentId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				log.debug("Getting abonent requests done");
				retVal.add(new BAbonentRequestsItem(rs.getLong(1), rs
						.getString(2), rs.getDate(3), rs.getDate(4), rs
						.getString(5), rs.getString(6), rs.getString(7), rs
						.getString(8), rs.getString(9)));
			}
			return retVal;
		} catch (SQLException e) {
			log.error("Could not get abonents requests for abonent id = "
					+ abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonents requests for abonent id = "
							+ abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ALL_ABONENT_SERVICES_STMNT = new StringBuffer()
			.append("select v.id,").append("v.service_type_id,").append(
					"v.saddress ").append("from web.v_t2_hd_service_list v ")
			.append("where v.abonent_id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentServices(long)
	 */
	public Map<Integer, String> getAbonentServices(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Map<Integer, String> retVal = new LinkedHashMap<Integer, String>();
		try {
			log.debug("Getting abonents services ...");
			log.debug("input parameter (abonentId) = " + abonentId);
			conn = dataSource.getConnection();
			log.debug(GET_ALL_ABONENT_SERVICES_STMNT);
			pstmt = conn.prepareStatement(GET_ALL_ABONENT_SERVICES_STMNT);
			pstmt.setLong(1, abonentId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				log.debug("Getting abonent requests done");
				retVal.put(rs.getInt(1), rs.getString(3));
			}
			return retVal;
		} catch (SQLException e) {
			log.error("Could not get abonents services for abonent id = "
					+ abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonents requests for abonent id = "
							+ abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}

	}

	private static final String REGISTER_HELP_DESC_REQUEST = "{ call web.pkg_t2_hd.insert_request( ?, ?, ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#registerHelpDeskRequest(long,
	 *      long, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void registerHelpDeskRequest(long abonentId, long serviceId,
			String rmNote, String cname, String cphone, String cmobile,
			String cemail, String cicq) throws BAbonentException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("registering help deask request id  ...");
			log.debug("abonentId = " + abonentId + ", serviceId = " + serviceId
					+ ", rmNote = " + rmNote + ", cname = " + cname
					+ ", cphone = " + cphone + ", cmobile = " + cmobile
					+ ", cemail = " + cemail + ", cicq = " + cicq);
			conn = dataSource.getConnection();
			log.debug(REGISTER_HELP_DESC_REQUEST);
			cstmt = conn.prepareCall(REGISTER_HELP_DESC_REQUEST);
			cstmt.setLong(1, abonentId);
			cstmt.setLong(2, serviceId);
			cstmt.setString(3, rmNote);
			cstmt.setString(4, cname);
			cstmt.setString(5, cphone);
			cstmt.setString(6, cmobile);
			cstmt.setString(7, cemail);
			cstmt.setString(8, cicq);
			cstmt.execute();
		} catch (SQLException e) {
			log.error("Could not register help desk request form abonent "
					+ abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.HELPDESK_REGISTRATION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not register help desk request form abonent "
							+ abonentId, e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_ABONENT_PASSWORD_STMT = new StringBuffer()
			.append("select property_value ").append(
					"from db.abonent_properties ").append(
					"where abonent_id = ? ").append("and property_id = ?")
			.toString();

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentPassword(java.lang.String)
	 */
	public String getAbonentPassword(long abonentId) throws BAbonentException,
			BUtilException {
		Connection conn = null;
		log.debug("Getting abonent password ...");
		try {
			IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
					.getBUtilName(), IBUtil.class);
			long deviceGroupId = util
					.getDeviceGroupId("KIOSK_PASSWORD_PROP_ID");
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				log.debug("GET_ABONENT_PASSWORD_STMT = "
						+ GET_ABONENT_PASSWORD_STMT);
				cstmt = conn.prepareStatement(GET_ABONENT_PASSWORD_STMT);
				cstmt.setLong(1, abonentId);
				cstmt.setLong(2, deviceGroupId);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get password of abonent " + abonentId, e);
			throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
					"abonent.getting-abonent-password.error", e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR, "Abonent " + abonentId
						+ " have no password");
	}

	private static String GET_OPERATOR_PASSWORD_STMT = new StringBuffer()
			.append("select safety.pkg_usr.decrypt(au.password) ").append(
					"from db.account_usr au ").append("where au.login = ? ")
			.append("and au.account_usr_type_id = 2").toString();

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getOperatorPassword(java.lang.String)
	 */
	public String getOperatorPassword(String operator) throws BAbonentException {
		Connection conn = null;
		log.debug("Getting password ...");
		try {
			PreparedStatement cstmt = null;
			try {
				conn = dataSource.getConnection();
				cstmt = conn.prepareStatement(GET_OPERATOR_PASSWORD_STMT);
				cstmt.setString(1, operator);
				ResultSet rs = cstmt.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not get password of operator " + operator, e);
			throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
					"abonent.getting-operator-password.error", e.getMessage(),
					e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		throw new BAbonentException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR, "Operator " + operator
						+ " has no password");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#setInfo(com.realsoft.commons.beans.registration.BAbonentPortalInfo)
	 */
	public void setInfo(BAbonentPortalInfo portal) throws BAbonentException {
		log.info("Branch = " + portal.getBranch());
		log.info("Email = " + portal.getEmail());
		log.info("EmailType = " + portal.getEmailType());
		log.info("Guids = " + portal.getGuids());
		log.info("MailAccount = " + portal.getMailAccount());
	}

	/**
	 * Возращает пул соединений инициализированный при старте компонентного
	 * менеджера
	 * 
	 * @return пул соединений с базой
	 */
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Инициализирует пул соединений, необходимый для работы компоненты.
	 * 
	 * @param dataSource
	 */
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Инициализирует переменную <code>beanFactory</code>, которая является
	 * ссылкой на компонентный менеджер
	 * 
	 * @param beanFactory
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	private static final String REGISTER_PORTAL = "{ call db.pkg_portal.register_portal(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

	public BAbonentPortalInfo systemRegistration(String guid, String codeWord)
			throws BRegistrationException {
		log.info("registering with guid " + guid);
		CallableStatement cstmt = null;
		Connection conn = null;

		String guids;
		String fullName;
		String account;
		String phones;
		String category;
		String identityCardNo;
		Calendar identityCardDate;
		String address;
		String login;
		String email;
		String emailSubscr;
		String emailType;
		String branch;
		String passwd;
		String rnn;

		BAbonentPortalInfo portalInfo = new BAbonentPortalInfo();
		// BRegistrationInfo13 registrationInfo = new BRegistrationInfo13();
		// registrationInfo.setName(phone);
		try {
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(REGISTER_PORTAL);
			cstmt.setString(1, guid);
			cstmt.setString(2, codeWord);
			cstmt.setInt(3, 1);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.registerOutParameter(11, Types.VARCHAR);
			cstmt.registerOutParameter(12, Types.VARCHAR);
			cstmt.registerOutParameter(13, Types.VARCHAR);

			cstmt.execute();

			guids = guid;
			fullName = cstmt.getString(4);
			account = cstmt.getString(5);
			phones = cstmt.getString(6);
			category = cstmt.getString(7);
			identityCardNo = cstmt.getString(8);
			// identityCardDate;
			address = cstmt.getString(9);
			// login;
			email = cstmt.getString(10);
			// emailSubscr;
			emailType = cstmt.getString(11);
			branch = cstmt.getString(12);
			// passwd;
			rnn = cstmt.getString(13);

			portalInfo.setAccount(account);
			portalInfo.setAddress(address);
			portalInfo.setBranch(branch);
			portalInfo.setCategory(category);
			portalInfo.setEmail(email);
			portalInfo.setEmailType(emailType);
			portalInfo.setFullName(fullName);
			portalInfo.setGuids(guids);
			portalInfo.setIdentityCardNo(identityCardNo);
			portalInfo.setPhones(phones);
			portalInfo.setRnn(rnn);

			log.debug("GUID = " + guid);
		} catch (SQLException e) {
			log.error("Could not check account", e);
			throw new BRegistrationException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REGISTRATION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not register for guid " + guid, e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
		return portalInfo;
	}

	private static final String ABONENT_SERVICES_STMT = new StringBuffer()
			.append(
					"select t.id, t.name t_name, sp.id packet_id, v.Service_Count_Group_Id ")
			.append(
					"from db.view_all_serv v, db.tariff_type t, db.service_packet_type sp ")
			.append("where t.id = v.Tariff_Type_Id ").append(
					"and sp.id = v.Service_Packet_Type_Id ").append(
					"and v.DEVICE_ID = ? ").append(
					"and v.Service_Packet_Type_Id in (select const_value ")
			.append("from db.all_const a where a.name = ? ) ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getServiceList(java.lang.String,
	 *      java.lang.String)
	 */

	public List<BTariffTypeItem> getServiceList(long deviceId, long townId,
			String servicePacketConstName) throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BTariffTypeItem> result = new ArrayList<BTariffTypeItem>();
		try {
			log.debug("getting service-list for device: " + deviceId);
			log.debug("ABONENT_SERVICES_STMT = " + ABONENT_SERVICES_STMT);
			log.debug("servicePacketConstName = " + servicePacketConstName);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ABONENT_SERVICES_STMT);
			pstmt.setLong(1, deviceId);
			pstmt.setString(2, servicePacketConstName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				log.debug("Getting address done");
				result.add(new BTariffTypeItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3), rs.getLong(4)));
			}
			return result;
		} catch (SQLException e) {
			log.error("Could not get service-list for abonent " + deviceId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get service-list for abonent " + deviceId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentId13(java.lang.String,
	 *      long, long)
	 */

	private static final String GET_ABONENT_ID_BY_DEVICE_STMT = "{ ? = call get_abonent_id( ? , ? , ? ) }";

	public long getAbonentId13(String device, long deviceGroupId, long townId)
			throws BAbonentException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("getting abonent id by device: device = " + device
					+ "; deviceGroupId = " + deviceGroupId + "; townId = "
					+ townId);
			log.debug("GET_ABONENT_ID_BY_DEVICE_STMT = "
					+ GET_ABONENT_ID_BY_DEVICE_STMT);
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(GET_ABONENT_ID_BY_DEVICE_STMT);
			cstmt.setString(2, device);
			cstmt.setLong(3, deviceGroupId);
			cstmt.setLong(4, townId);
			cstmt.registerOutParameter(1, Types.NUMERIC);
			cstmt.execute();
			return cstmt.getLong(1);
		} catch (SQLException e) {
			log.error("Could not get abonentId for device " + device, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonentId for device " + device, e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public List<BTariffTypeItem> getServiceList(String abonentGuid,
			long deviceId, long townId, String servicePacketConstName)
			throws BAbonentException {
		return getServiceList(deviceId, townId, servicePacketConstName);
	}

	private static final String GET_ABONENT_INFO_STMT = new StringBuffer()
			.append(
					"select distinct ab.id, ab.name, db.get_adr@%1$s(ab.address_id) address_name, ")
			.append(
					"abt.name, ab.rnn, ab.abonent_type_id, ab.address_id, ab.town_id, abn_t.name ")
			.append(
					"from db.device@%1$s d, db.town@%1$s t, db.town@%1$s abn_t, db.link@%1$s l, db.link_type@%1$s lt, ")
			.append(
					"db.abonent@%1$s ab, db.abonent_type@%1$s abt, db.address@%1$s adr where ab.id = l.abonent_id ")
			.append(
					"and abt.id = ab.abonent_type_id and adr.id = ab.address_id and d.id = l.device_id ")
			.append(
					"and t.id = d.town_id and abn_t.id = ab.town_id and lt.id = l.link_type_id ")
			.append(
					"and l.flag = 0 and l.closed = 0 and t.code = ? and d.device like '%%%2$s%%' ")
			.append(
					"and d.town_id in (select id from db.town@%1$s where code = ? )")

			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List<BAbonentInfo13> getAbonentInfo(String townCode, String device,
			String dbLinkName) throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BAbonentInfo13> result = new ArrayList<BAbonentInfo13>();
		try {
			log.debug("getting abonent info, townCode = " + townCode
					+ "; device = " + device + "; dbLinkName = " + dbLinkName);
			String statement = formatter.format(GET_ABONENT_INFO_STMT,
					new Object[] { dbLinkName, device });
			log.debug("GET_ABONENT_INFO_STMT = " + statement);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(statement);
			pstmt.setString(1, townCode);
			pstmt.setString(2, townCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BAbonentInfo13 info13 = new BAbonentInfo13();
				info13.setAbonentId(rs.getLong(1));
				info13.setAbonentName(rs.getString(2));
				info13.setAddressName(rs.getString(3));
				info13.setAbonentTypeName(rs.getString(4));
				info13.setRnn(rs.getString(5));
				info13.setAbonentTypeId(rs.getLong(6));
				info13.setAddressId(rs.getLong(7));
				info13.setTownId(rs.getLong(8));
				info13.setTownName(rs.getString(9));
				info13.setDevices(getAbonentDevices(rs.getLong(1), dbLinkName,
						conn));
				result.add(info13);
			}
			return result;
		} catch (SQLException e) {
			log.error("Could not get  abonent info", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get  abonent info", e);
		} catch (UtilsException e) {
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR,
					"Could not format statement for abonentInfo", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String ABONENT_DEBIT_LIST_STMT = new StringBuffer()
			.append("select r.id, r.name, bt.name bill_type_name, b.in_money, ")
			.append("b.debit + b.change_debit + b.change_in_debit debit, ")
			.append("b.credit + b.change_credit + b.change_in_credit credit, ")
			.append(
					"b.out_money, dt.name dbill_type_name, d.debit + d.change_debit, ")
			.append(
					"d.change_debit, b.id bill_id from db.report_date r, db.bill b, ")
			.append("db.dbill       d, db.bill_type   bt, db.dbill_type  dt ")
			.append("where r.id = b.report_date_id and d.bill_id = b.id ")
			.append(
					"and d.report_date_id = b.report_date_id and b.bill_type_id = bt.id ")
			.append(
					"and d.dbill_type_id = dt.id and (b.out_money > 0 or b.ready = 0) ")
			.append("and b.abonent_id = ? order by b.id desc").toString();

	public List<BAbonentDebitItem> getAbonentDebitList(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BAbonentDebitItem> result = new ArrayList<BAbonentDebitItem>();
		try {
			log.debug("getting debit-list for abonent: " + abonentId);
			log.debug("ABONENT_DEBIT_LIST_STMT = " + ABONENT_DEBIT_LIST_STMT);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(ABONENT_DEBIT_LIST_STMT);
			pstmt.setLong(1, abonentId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// log.debug("");
				result.add(new BAbonentDebitItem(rs.getLong(1),
						rs.getString(2), rs.getString(3), rs.getDouble(4), rs
								.getDouble(5), rs.getDouble(6),
						rs.getDouble(7), rs.getString(8), rs.getDouble(9), rs
								.getDouble(10), rs.getLong(11)));
			}
			return result;
		} catch (SQLException e) {
			log.error("Could not get debit-list for abonent: " + abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get debit-list for abonent: " + abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String ABONENT_DEBIT_LIST_WITH_DBLINK_STMT = new StringBuffer()

			.append("select bt.name, ")
			.append("s.in_money, ")
			.append("s.debit + s.change_debit + s.change_in_debit debit, ")
			.append("s.credit + s.change_credit + s.change_in_credit credit, ")
			.append("s.out_money ")
			.append(
					"from db.saldo@%1$s s, db.report_date@%2$s rd, db.bill_type@%3$s bt ")
			.append("where rd.id = s.report_date_id ").append(
					"and bt.id = s.bill_type_id ").append(
					"and sysdate >= rd.from_date ").append(
					"and sysdate < nvl(rd.to_date, sysdate + 1) ").append(
					"and s.abonent_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentDebitList(long,
	 *      java.lang.String)
	 */

	public List<BAbonentDebitItem> getAbonentDebitList(long abonentId,
			String dbLink) throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BAbonentDebitItem> result = new ArrayList<BAbonentDebitItem>();
		try {
			log.debug("getting debit-list for abonent: " + abonentId
					+ "; dbLink = " + dbLink);
			String statement = formatter.format(
					ABONENT_DEBIT_LIST_WITH_DBLINK_STMT, new Object[] { dbLink,
							dbLink, dbLink });
			log.debug("ABONENT_DEBIT_LIST_WITH_DBLINK_STMT = " + statement);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(statement);
			pstmt.setLong(1, abonentId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BAbonentDebitItem abonentDebitItem = new BAbonentDebitItem();
				abonentDebitItem.setBillTypeName(rs.getString(1));
				abonentDebitItem.setInMoney(rs.getDouble(2));
				abonentDebitItem.setDbillDebit(rs.getDouble(3));
				abonentDebitItem.setCredit(rs.getDouble(4));
				abonentDebitItem.setOutMoney(rs.getDouble(5));

				result.add(abonentDebitItem);
			}

			return result;
		} catch (SQLException e) {
			log.error("Could not get debit-list with dbLink for abonent: "
					+ abonentId, e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get debit-list for abonent: " + abonentId, e);
		} catch (UtilsException e) {
			log.error("Could not format abonent debit-list statement ", e);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR,
					"Could not format abonent debit-list statement", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BAbonentInfo13 getAbonentInfo13ByExistingOrder(long orderId)
			throws BAbonentException, BRegistrationException,
			BRequestException, BMailException {
		log.debug("ABONENT_NFO_BY_EXISTING_ORDER_STMT = "
				+ ABONENT_NFO_BY_EXISTING_ORDER_STMT);
		return getAbonentInfo(ABONENT_NFO_BY_EXISTING_ORDER_STMT, orderId);
	}

}