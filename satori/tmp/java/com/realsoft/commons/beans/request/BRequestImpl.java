/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRequestImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.request;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentDeviceItem;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.BAbonentInfo13;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.mail.BMailException;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.urlresolver.BURLResolverException;
import com.realsoft.commons.beans.util.BUtilException;
import com.realsoft.commons.beans.util.IBUtil;
import com.realsoft.commons.utils14.RealsoftException;
import com.realsoft.commons.utils14.hash.HashCalculator;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

public class BRequestImpl implements IBRequest, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BRequestImpl.class);

	private BeanFactory beanFactory = null;

	private BasicDataSource dataSource = null;

	private IFormatter formatter = null;

	private IBRegistration registration = null;

	private IBAbonent abonent = null;

	private IBUtil util = null;

	private String orderDestination = null;

	private String actionIdConstName = null;

	private long payServicePacketTypeId;

	private int actionId;

	private long operatorId;

	private String operatorName = null;

	private String operatorPassword = null;

	public BRequestImpl() {
		super();
	}

	private static String GET_ORDER_DEVICES_135_STMT = new StringBuffer()
			.append(
					"select od.alien_abonent_id, od.alien_order_id, dg.name device_group_name, od.action_id, od.system_date, od.done, ")
			.append("case when od.done = -1 then ")
			.append("(select ex.exception from un.order_device_exception ex ")
			.append(" where ex.order_device_id = od.id) end exception ")
			.append(
					" from un.order_device od, db.connect_type ct, db.device_group dg ")
			.append(" where od.town_code = ? ").append(" and od.device = ? ")
			.append(" and  od.system_date >= ? ")
			// .append(" and od.system_date >= (TO_CHAR(SYSDATE - 180, 'DD MONTH
			// YYYY'))")
			.append(" and od.connect_type_id= ct.id ").append(
					" and ct.device_group_id = dg.id ").toString();

	public List<BOrderDeviceItem135> getOrderDeviceList135(String device,
			String townCode, Date countDay) throws BRequestException {
		log.info("geting order-device list for 135...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.info("device = " + device + "townCode = " + townCode
					+ "; countDay = " + new java.sql.Date(countDay.getTime()));
			log.info("GET_ORDER_DEVICES_135_STMT = "
					+ GET_ORDER_DEVICES_135_STMT);
			pstmt = conn.prepareStatement(GET_ORDER_DEVICES_135_STMT);
			pstmt.setString(1, townCode);
			pstmt.setString(2, device);
			pstmt.setDate(3, new java.sql.Date(countDay.getTime()));
			ResultSet rs = pstmt.executeQuery();
			List<BOrderDeviceItem135> list = new ArrayList<BOrderDeviceItem135>();
			while (rs.next()) {
				list.add(new BOrderDeviceItem135(rs.getInt(1), rs.getInt(2), rs
						.getString(3), rs.getInt(4), rs.getDate(5), rs
						.getInt(6), rs.getString(7)));
			}
			log.debug("ResultSet size = " + list.size());
			log.info("Getting order-device list for 135 successfully done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get order-device list for 135, device = "
					+ device + "; townCode = " + townCode, e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REPORT_TOTAL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String WORKPLACE_CSTMT = new StringBuffer()
			// .append("select sc.name, decode(t.status,0,'Наряд к
			// обработке',1,'Отправлен на выполнение',2,'Наряд
			// выполнен',-2,'Отказано',-3,'Отказано') status, ")
			.append("select sc.name, t.status, ")
			.append(
					"t.datein from tech.orderstages@%1$s t, tech.service_center@%2$s sc ")
			.append("where t.directdetail = sc.id ").append(
					" and t.orderid = ? ").toString();

	public List<BRequestOrderStageItem> getWorkPlace(long orderid, String dblink)
			throws BRequestException {
		log.info("geting workplace list for order-device, id = " + orderid);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();

			log.info("orderid = " + orderid + "; dblink = " + dblink);
			String statement = formatter.format(WORKPLACE_CSTMT,
					new Object[] { dblink, dblink }).toString();
			log.debug("formater created successfully");
			log.info("WORKPLACE_CSTMT = " + statement);
			pstmt = conn.prepareStatement(statement);
			pstmt.setLong(1, orderid);
			ResultSet rs = pstmt.executeQuery();
			List<BRequestOrderStageItem> list = new ArrayList<BRequestOrderStageItem>();
			while (rs.next()) {
				BRequestOrderStageItem stagesItem = new BRequestOrderStageItem();
				stagesItem.setWorkPlaceName(rs.getString(1));
				stagesItem.setStatus(rs.getLong(2));
				stagesItem.setDatein(rs.getDate(3));
				list.add(stagesItem);
			}
			log.debug("ResultSet size = " + list.size());
			log
					.info("geting workplace list for order-device successfully done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get workplace list for order-device", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could not format statement", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ORDER_DEVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_ORDERS_13_STMT = new StringBuffer()
			.append(
					"select od.id as orderid, db.get_keyword@%1$s(od.id) keyword, ct.name as connect_type, dg.name as device_group, ")
			.append(
					"od.new_device as device,a.name order_type, od.open_date, od.close_date, ")
			.append("(u.fname|| ' ' ||u.lname) user_id ")
			.append(
					" from db.order_device@%2$s od,  db.connect_type@%3$s ct, db.device_group@%4$s dg, ")
			.append("db.action@%5$s a, db.town@%6$s t, safety.xusers@%7$s u ")
			.append(" where od.new_connect_type_id = ct.id ").append(
					" and od.device_group_id = dg.id ").append(
					" and od.action_id = a.id ").append(
					" and od.town_id = t.id ")
			.append(" and od.user_id = u.id ").append(" and t.code = ? ")
			.append(" and od.open_date >= ? ")
			.append(" and od.abonent_id = ? ").append(
					" order by od.open_date desc ").toString();

	public List<BOrderDeviceItem> getOrdersDeviceList13(String townCode,
			String dblink, Date countDay, long abonentId)
			throws BRequestException {
		log.info("geting order-device list for 13...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.info("townCode = " + townCode + "; dblink = " + dblink
					+ "; countDay = " + new java.sql.Date(countDay.getTime()));
			String statement = formatter.format(
					GET_ORDERS_13_STMT,
					new Object[] { dblink, dblink, dblink, dblink, dblink,
							dblink, dblink }).toString();
			log.debug("formater created successfully");
			log.info("ORDERSBITTL_CSTMT = " + statement);
			pstmt = conn.prepareStatement(statement);
			pstmt.setString(1, townCode);
			pstmt.setDate(2, new java.sql.Date(countDay.getTime()));
			pstmt.setLong(3, abonentId);
			ResultSet rs = pstmt.executeQuery();
			List<BOrderDeviceItem> list = new ArrayList<BOrderDeviceItem>();

			while (rs.next()) {
				BOrderDeviceItem deviceItem = new BOrderDeviceItem();
				deviceItem.setOrderDeviceId(rs.getLong(1));
				deviceItem.setKeyWord(rs.getString(2));
				deviceItem.setNewConnectTypeName(rs.getString(3));
				deviceItem.setDeviceGroupName(rs.getString(4));
				deviceItem.setNewDevice(rs.getString(5));
				deviceItem.setActionName(rs.getString(6));
				Calendar calendar = new GregorianCalendar();
				if (rs.getDate(7) != null) {
					calendar.setTime(rs.getDate(7));
					deviceItem.setOpenDate(calendar);
				} else {
					deviceItem.setOpenDate(null);
				}
				calendar = new GregorianCalendar();
				if (rs.getDate(8) != null) {
					calendar.setTime(rs.getDate(8));
					deviceItem.setCloseDate(calendar);
				} else {
					deviceItem.setCloseDate(null);
				}
				deviceItem.setUserName(rs.getString(9));
				list.add(deviceItem);
			}
			log.debug("ResultSet size = " + list.size());
			log.info("geting order-device list for 13 successfully done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get order-device list for 13", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ORDER_DEVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could not format statement", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ORDER_DEVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String DEVICE_LIST_STMT_DEALER = new StringBuffer()
			.append(
					"select vr.DEVICE_ID, trim(t.code) || trim(vr.device) device, vr.DEVICE_GROUP_ID, vr.CONNECT_TYPE_ID, t.id town_id, vr.LINK_ID, vr.DEV_ADR_ID ")
			.append("from db.view_all_real vr, db.town t ").append(
					"where vr.dev_town_id = t.id ").append(
					"and vr.abonent_id = ? ").append(
					"and vr.DEVICE_GROUP_ID = ? ").toString();

	public List<BAbonentDeviceItem> getDeviceList(long townId, long abonentId,
			long deviceGroupId) throws BRequestException {
		log.debug("getting device list townId = " + townId + " abonentId = "
				+ abonentId + " deviceGroupId = " + deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("DEVICE_LIST_STMT_DEALER = " + DEVICE_LIST_STMT_DEALER);
			pstmt = conn.prepareStatement(DEVICE_LIST_STMT_DEALER);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, deviceGroupId);
			rs = pstmt.executeQuery();
			List<BAbonentDeviceItem> list = new ArrayList<BAbonentDeviceItem>();
			while (rs.next()) {
				list.add(new BAbonentDeviceItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3), rs.getLong(4), rs.getLong(5), rs
								.getLong(6), rs.getLong(7)));
			}
			log.debug("getting device list done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get device list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_LIST_ERROR
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
	 * @see com.realsoft.commons.beans.request.IBRequest#getDeviceInfo(long,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */

	private static final String GET_DEVICE_INFO_13_CSTMT = "{call un.pkg_sanatel.get_device_info( ?, ?, ?, ?, ?, ?, ?, ?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.inventory.IBInventory#checkDeviceAvailable(java.lang.String)
	 */
	public BAbonentDeviceItem getDeviceInfo(long deviceGroupId, String device,
			String townCode, String dbLink) throws BRequestException {
		log.info("Getting device info, deviceGroupId = " + deviceGroupId
				+ "; device = " + device + "; townCode = " + townCode
				+ "; dbLink = " + dbLink);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			String statement = formatter.format(GET_DEVICE_INFO_13_CSTMT,
					dbLink);
			log.info("GET_DEVICE_INFO_13_CSTMT = " + statement);
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(statement);
			cstmt.setLong(1, deviceGroupId);
			cstmt.setString(2, townCode);
			cstmt.setString(3, device);
			cstmt.setString(4, dbLink);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.DATE);

			cstmt.execute();

			BAbonentDeviceItem result = new BAbonentDeviceItem();
			result.setConnectTypeName(cstmt.getString(5));
			result.setDisconnected(cstmt.getString(6).equals("T"));
			result.setDamageTypeName(cstmt.getString(7));
			java.sql.Date date = cstmt.getDate(8);
			result.setDamageRegistrationDate(date != null ? new Date(date
					.getTime()) : null);

			return result;
		} catch (SQLException e) {
			log.error("Could get device info", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.INVENTORY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could get device info", e);
		} catch (UtilsException e) {
			log.error("Could get device info", e);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.INVENTORY_ERROR,
					"Could get device info", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CONST_VALUE_STMNT = new StringBuffer().append(
			"select ac.const_value ").append("from db.all_const ac ").append(
			"where ac.name = ?").toString();

	/**
	 * Возвращает значение поля const_value из db.all_const по уникальному полю
	 * db.all_const.name
	 * 
	 * @param constId
	 *            ID констнаты
	 * @return значение поля const_value из db.all_const
	 * @throws BRequestException
	 */

	public String getConstValue(String constId) throws BRequestException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmnt = null;
		try {
			conn = dataSource.getConnection();
			pstmnt = conn.prepareStatement(CONST_VALUE_STMNT);
			pstmnt.setString(1, constId);
			rs = pstmnt.executeQuery();
			if (rs.next())
				return rs.getString(1);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR,
					"No sutch constant : '" + constId + "'");
		} catch (SQLException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR,
					"No sutch constant : '" + constId + "'", e);
		} finally {
			CommonOperations.closeStatement(pstmnt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CONNECT_TYPES_STMT = new StringBuffer()
			.append("select ct.id, ")
			.append("ct.name, ")
			.append("ct.ias_connect_type_id, ")
			.append("ct.is_active, ")
			.append("ct.center_packet_type_id, ")
			.append("ct.center_connect_type_id ")
			.append("from db.connect_type ct ")
			.append(
					"where ct.device_group_id = ? and ct.id in ( %s ) and ct.is_active='T'")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getConnectTypes(long,
	 *      long)
	 */
	public List<BConnectTypeItem> getConnectTypes(long townId,
			String connectType, long deviceGroupId) throws BRequestException {
		log.info("getting connect types townId = " + townId
				+ " deviceGroupId = " + deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement cpstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			List<BConnectTypeItem> list = new ArrayList<BConnectTypeItem>();
			String connTypeValue = getConstValue(connectType);
			log.debug("connTypeValue = " + connTypeValue);
			String connectTypes = formatter.format(CONNECT_TYPES_STMT,
					connTypeValue);
			log.debug("CONNECT_TYPES_STMT = " + connectTypes);
			pstmt = conn.prepareStatement(connectTypes);
			pstmt.setLong(1, deviceGroupId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new BConnectTypeItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3), rs.getString(4), rs.getLong(5), rs
								.getLong(6)));
			}
			log.info("getting connect types done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get connect types list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could not get connect types list", e);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cpstmt);
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CONNECT_TYPES_STMT_DEALER = new StringBuffer()
			.append("select ct.id, ")
			.append("ct.name, ")
			.append("ct.ias_connect_type_id, ")
			.append("ct.is_active, ")
			.append("ct.center_packet_type_id, ")
			.append("ct.center_connect_type_id ")
			.append("from db.connect_type ct ")
			.append(
					"where ct.device_group_id = ? and ct.is_active='T' order by name desc")
			.toString();

	public List<BConnectTypeItem> getConnectTypes(long townId,
			long deviceGroupId) throws BRequestException {
		log.debug("getting connect types townId = " + townId
				+ " deviceGroupId = " + deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CONNECT_TYPES_STMT_DEALER = "
					+ CONNECT_TYPES_STMT_DEALER);
			pstmt = conn.prepareStatement(CONNECT_TYPES_STMT_DEALER);
			pstmt.setLong(1, deviceGroupId);
			rs = pstmt.executeQuery();
			List<BConnectTypeItem> list = new ArrayList<BConnectTypeItem>();
			while (rs.next()) {
				list.add(new BConnectTypeItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3), rs.getString(4), rs.getLong(5), rs
								.getLong(6)));
			}
			log.debug("getting connect types done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get connect types list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR
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
	 * @see com.realsoft.commons.beans.request.IBRequest#getConnectTypes(java.lang.String,
	 *      java.lang.String, long)
	 */

	public List<BConnectTypeItem> getConnectTypes(String abonentGiud,
			String connectType, String deviceGroup) throws BRequestException {
		try {
			String[] guids = abonentGiud.split("-");
			long townId = Long.valueOf(guids[0]);
			long deviceGroupId = util.getDeviceGroupId(deviceGroup);
			return getConnectTypes(townId, connectType, deviceGroupId);
		} catch (Exception e) {
			log.error("Could not get ConnectTypes", e);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					"request.error.get-connect-types", e.getMessage(), e);
		}
	}

	private static String TARIFF_TYPES_STMNT = new StringBuffer()
			.append(
					"select tp.id, tp.name, tp.service_packet_type_id, tp.Service_Count_Group_Id from db.view_tariff tp where abonent_group_id = 1 and service_packet_type_id in ( %s )")
			.toString();

	public List<BTariffTypeItem> getTariffTypes(String spTypeId)
			throws BRequestException {
		Connection conn = null;
		PreparedStatement cpstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CONNECT_TYPES_STMT = " + CONNECT_TYPES_STMT);
			List<BTariffTypeItem> result = new ArrayList<BTariffTypeItem>();
			String tariffTypeIds = getConstValue(spTypeId);
			log.debug("tariffTypeIds = " + tariffTypeIds);
			String tariffTypesStmnt = formatter.format(TARIFF_TYPES_STMNT,
					tariffTypeIds);
			log.debug("TARIFF_TYPES_STMNT = " + tariffTypesStmnt);
			pstmt = conn.prepareStatement(tariffTypesStmnt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BTariffTypeItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3), rs.getLong(4)));
			}
			log.info("getting tariff types done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get tariff types list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could not get tariff types list", e);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONNECT_TYPE_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cpstmt);
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getTariffTypes(java.lang.String,
	 *      java.lang.String)
	 */

	public List<BTariffTypeItem> getTariffTypes(String spTypeId,
			String abonentGiuds) throws BRequestException {
		return getTariffTypes(spTypeId);
	}

	private static String CHECK_IS_ORDER_BY_DEVICE_EXISTS = new StringBuffer()
			.append("select nvl(max(id),0) from db.order_device ").append(
					"where new_device = ? ").append("and device_group_id = ? ")
			.append("and close_date is null ").append("and action_id = 5")
			.toString();

	private boolean isOrderDeviceExists(String device, Long deviceGroupId)
			throws BRequestException {
		log.info("checking if order exists device = " + device
				+ " deviceGroupId = " + deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CHECK_IS_ORDER_BY_DEVICE_EXISTS = "
					+ CHECK_IS_ORDER_BY_DEVICE_EXISTS);
			log.debug("device = " + device + "; deviceGroupId = "
					+ deviceGroupId);
			pstmt = conn.prepareStatement(CHECK_IS_ORDER_BY_DEVICE_EXISTS);
			pstmt.setString(1, device);
			pstmt.setLong(2, deviceGroupId.longValue());
			rs = pstmt.executeQuery();
			return (rs.next() && rs.getLong(1) > 0);
		} catch (SQLException e) {
			log.error("Could not check if order existrs", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHECK_IS_ORDER_EXISTS = new StringBuffer().append(
			"select count(*) from db.order_device ").append("where id = ? ")
			.toString();

	private boolean isOrderDeviceExists(long orderId) throws BRequestException {
		log.info("checking if order exists orderId = " + orderId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CHECK_IS_ORDER_EXISTS = " + CHECK_IS_ORDER_EXISTS);
			pstmt = conn.prepareStatement(CHECK_IS_ORDER_EXISTS);
			pstmt.setLong(1, orderId);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getLong(1) > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			log.error("Could not check if order existrs", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHECK_IS_ORDER_BY_SERVICE_EXISTS = new StringBuffer()
			.append("select count(1) from db.order_service ").append(
					"where abonent_id = ? ").append(
					"and service_packet_type_id = ? ").append(
					"and tariff_type_id = ? ").append("and close_date is null")
			.toString();

	private boolean isOrderServiceExists(long abonentId,
			long servicePacketTypeId, long tariffTypeId)
			throws BRequestException {
		log.info("checking if order-services exists abonentId = " + abonentId
				+ "; servicePacketTypeId = " + servicePacketTypeId
				+ "; tariffTypeId = " + tariffTypeId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CHECK_IS_ORDER_BY_SERVICE_EXISTS = "
					+ CHECK_IS_ORDER_BY_SERVICE_EXISTS);
			pstmt = conn.prepareStatement(CHECK_IS_ORDER_BY_SERVICE_EXISTS);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, servicePacketTypeId);
			pstmt.setLong(3, tariffTypeId);
			rs = pstmt.executeQuery();
			return (rs.next() && rs.getLong(1) > 0);
		} catch (SQLException e) {
			log.error("Could not check if order existrs", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHECK_IS_DEVICE_EXISTS = new StringBuffer().append(
			"select abonent_id, ").append("order_date ").append(
			"from db.view_all_real ").append("where device=? ").append(
			"and dev_town_id=? ").append("and device_group_id=? ").toString();

	private boolean isDeviceExists(String device, Long townId,
			Long deviceGroupId) throws BRequestException {
		log.info("checking if device exists device " + device + " townId = "
				+ townId + " deviceGroupId = " + deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("CHECK_IS_DEVICE_EXISTS = " + CHECK_IS_DEVICE_EXISTS);
			log.debug("device = " + device + "; townId = " + townId
					+ "; deviceGroupId = " + deviceGroupId);
			pstmt = conn.prepareStatement(CHECK_IS_DEVICE_EXISTS);
			pstmt.setString(1, device);
			pstmt.setLong(2, townId.longValue());
			pstmt.setLong(3, deviceGroupId.longValue());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			log.error("Could not check if device existrs", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
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
	 * @see com.realsoft.commons.beans.request.IBRequest#createOrderDevice(java.lang.Long,
	 *      java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long,
	 *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long,
	 *      java.lang.Long, java.lang.Double, java.lang.String, java.lang.Long,
	 *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long,
	 *      java.lang.Double, boolean)
	 */
	public BOrderDeviceItem createOrderDevice(Long townId, String device,
			Long abonentId, Long abonentGroupId, Long deviceGroupId,
			Long abonentTownId, Long newConnectTypeId, Long addressId,
			Long userId, Long tariffTypeId, Double tariff, String note,
			Long ndsTypeId, Long discountTypeId, Long servicePacketTypeId,
			Long serviceCountGroupId, Double discount, Double nds,
			boolean isMegalineCall) throws BRequestException, BUtilException {
		log.info("creating order device ...");
		log.info("townId " + townId + ", device = " + device + ", abonentId = "
				+ abonentId + ", abonentGroupId = " + abonentGroupId
				+ ", deviceGroupId = " + deviceGroupId + ", abonentTownId = "
				+ abonentTownId + ", connectTypeId  = " + newConnectTypeId
				+ ", addressId = " + addressId + ", userId = " + userId
				+ ", tariffTypeId = " + tariffTypeId + ", tariff = " + tariff
				+ ", note = " + note + ", ndsTypeId = " + ndsTypeId
				+ ", discountTypeId = " + discountTypeId
				+ ", servicePacketTypeId = " + servicePacketTypeId
				+ ", serviceCountGroupId = " + serviceCountGroupId
				+ ", discount = " + discount + ", nds = " + nds);

		if (isOrderDeviceExists(device, deviceGroupId)) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ORDER_ALREADY_EXIST_ERROR,
					"Order is already exist");
		}

		if (isMegalineCall) {
			if (isDeviceExists(device, abonentTownId, deviceGroupId)) {
				throw new BRequestException(
						CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.REQUEST_DEVICE_ALREADY_EXIST_ERROR,
						"Device is already exist");
			}
		}

		Connection conn = null;
		long result = 0;
		try {
			conn = dataSource.getConnection();
			long orderDeviceid = getOrderDeviceId(conn);
			String keyWord = null;
			IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
					.getBUtilName(), IBUtil.class);
			Date serverDate = util.getDBSysDate();
			keyWord = util.getKeyWord(orderDeviceid); // to encode MD5
			// add to db.center_order_device
			if (isMegalineCall) {
				CenterConnectType centerConnectType = getCenterConnectType(
						conn, newConnectTypeId);
				createCenterOrderDevice(conn, orderDeviceid, centerConnectType
						.getCenterPacketTypeId(), centerConnectType
						.getCenterConnectTypeId(), keyWord);
			}
			result = originateOrderDevice(conn, orderDeviceid, 5, device,

			abonentId, abonentGroupId, deviceGroupId, abonentTownId, null,
					newConnectTypeId, addressId, userId, tariffTypeId, tariff,
					note, ndsTypeId, discountTypeId, servicePacketTypeId,
					serviceCountGroupId, discount, nds, serverDate, null, null);

			if (result != 0) {
				CommonOperations.rollbackConnection(conn);
				throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.REQUEST_ERROR,
						"creating orderder device with error result is not zero");
			}

			CommonOperations.commitConnection(conn);

			BOrderDeviceItem deviceItem = new BOrderDeviceItem();
			deviceItem.setOrderDeviceId(orderDeviceid);
			deviceItem.setKeyWord(keyWord);

			log.info("creating order device done");
			return deviceItem;
		} catch (RealsoftException e) {
			log.error("Could not create order device", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(e.getErrorCode(), e.getMessageKey(), e
					.getMessageText(), e);
		} catch (SQLException e) {
			log.error("Could not create order device", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CREATE_ORDER_SERVICE_PSTMNT = new StringBuffer()
			.append(
					"{ call db.create_order_service (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#makeOrderService(long,
	 *      java.lang.String, long, long)
	 */

	public double makeOrderService(long abonentId, long deviceGroupId,
			long connectTypeId, String device, long townId, long linkTypeId,
			long servicePacketTypeId, long tariffTypeId,
			long serviceCountGroupId, long operatorId,
			String serviceCenterType, long payServicePacketTypeId, String note)
			throws BRequestException, BUtilException {
		Connection conn = null;
		Date openDate = util.getDBSysDate();
		log.debug("CREATE_ORDER_SERVICE_PSTMNT = "
				+ CREATE_ORDER_SERVICE_PSTMNT);
		log.debug("abonentId = " + abonentId + ", " + "deviceGroupId = "
				+ deviceGroupId + ", " + "connectTypeId = " + connectTypeId
				+ "; openDate = " + openDate + "; beginSysDate = " + openDate);
		log.debug("device = " + device + ", townId = " + townId
				+ ", linkTypeId = " + linkTypeId + ", servicePacketTypeId = "
				+ servicePacketTypeId);
		log.debug("tariffTypeId = " + tariffTypeId + ", serviceCountGroupId = "
				+ serviceCountGroupId + ", operatorId = " + operatorId);
		log.debug("serviceCenterType = " + serviceCenterType + ", note = "
				+ note);
		if (isOrderServiceExists(abonentId, servicePacketTypeId, tariffTypeId)) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ORDER_ALREADY_EXIST_ERROR,
					"Order is already exist");
		}
		try {
			conn = dataSource.getConnection();
			long orderId = getOrderDeviceId(conn);
			CallableStatement cstmnt = conn
					.prepareCall(CREATE_ORDER_SERVICE_PSTMNT);
			cstmnt.setLong(1, orderId);
			cstmnt.setLong(2, 11);
			cstmnt.setLong(3, abonentId);
			cstmnt.setLong(4, deviceGroupId);
			cstmnt.setLong(5, connectTypeId);
			cstmnt.setString(6, device);
			cstmnt.setLong(7, townId);
			cstmnt.setLong(8, linkTypeId);
			cstmnt.setLong(9, servicePacketTypeId);
			cstmnt.setLong(10, tariffTypeId);
			cstmnt.setLong(11, 0);
			cstmnt.setNull(12, Types.INTEGER);
			cstmnt.setLong(13, 1);
			cstmnt.setNull(14, Types.INTEGER);
			cstmnt.setLong(15, 1);
			cstmnt.setNull(16, Types.INTEGER);
			cstmnt.setLong(17, 1);
			cstmnt.setNull(18, Types.INTEGER);
			cstmnt.setLong(19, 1);
			cstmnt.setNull(20, Types.INTEGER);
			cstmnt.setLong(21, serviceCountGroupId);
			cstmnt.setLong(22, 1);
			cstmnt.setLong(23, 1);
			cstmnt.setLong(24, 1);
			cstmnt.setLong(25, payServicePacketTypeId);
			cstmnt.setLong(26, serviceCountGroupId);
			cstmnt.setNull(27, Types.INTEGER);
			cstmnt.setNull(28, Types.INTEGER);
			cstmnt.setNull(29, Types.INTEGER);
			cstmnt.setNull(30, Types.DATE);
			cstmnt.setDate(31, new java.sql.Date(openDate.getTime()));
			cstmnt.setNull(32, Types.DATE);
			cstmnt.setDate(33, new java.sql.Date(openDate.getTime()));
			cstmnt.setNull(34, Types.DATE);
			cstmnt.setLong(35, 0);
			cstmnt.setLong(36, operatorId);
			cstmnt.setString(37, note);
			cstmnt.setLong(38, 0);
			cstmnt.registerOutParameter(39, Types.DOUBLE);
			cstmnt.execute();
			log.debug("db.create_order_service executed successfully");
			Long serviceCenterTypeId = Long.valueOf(util.getConstValue(
					serviceCenterType).toString());
			BRequestDirectionItem directionItem = getWorkplaceId(townId,
					serviceCenterTypeId, device, deviceGroupId);
			sendOrder(townId, orderId, 0, directionItem.getDirectId(),
					directionItem.getDirectDetail(), note, operatorId, null, 0,
					false, 0, 0);
			CommonOperations.commitConnection(conn);
			return cstmnt.getDouble(39);
		} catch (SQLException e) {
			log.error("Could not create order service", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#makeOrderService(java.lang.String,
	 *      long, long, java.lang.String, long, long, long, long, long, long,
	 *      java.lang.String, long, java.lang.String)
	 */

	public double makeOrderService(String abonentGiud, long deviceGroupId,
			long connectTypeId, String device, long townId, long linkTypeId,
			long servicePacketTypeId, long tariffTypeId,
			long serviceCountGroupId, String note) throws BRequestException,
			BUtilException {
		if (abonentGiud == null)
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_GUIDS_NULL_ERROR,
					"Abonent guid can't be null");
		String[] guids = abonentGiud.split("-");
		long abonentId = Long.valueOf(guids[1]);
		return makeOrderService(abonentId, deviceGroupId, connectTypeId,
				device, townId, linkTypeId, servicePacketTypeId, tariffTypeId,
				serviceCountGroupId, operatorId, orderDestination,
				payServicePacketTypeId, note);
	}

	private class CenterConnectType {
		long centerPacketTypeId;

		long centerConnectTypeId;

		public CenterConnectType(long centerPacketTypeId,
				long centerConnectTypeId) {
			super();
			this.centerPacketTypeId = centerPacketTypeId;
			this.centerConnectTypeId = centerConnectTypeId;
		}

		public long getCenterConnectTypeId() {
			return centerConnectTypeId;
		}

		public void setCenterConnectTypeId(long centerConnectTypeId) {
			this.centerConnectTypeId = centerConnectTypeId;
		}

		public long getCenterPacketTypeId() {
			return centerPacketTypeId;
		}

		public void setCenterPacketTypeId(long centerPacketTypeId) {
			this.centerPacketTypeId = centerPacketTypeId;
		}
	}

	private static String GET_CENTER_CONNECT_TYPE_STMT = new StringBuffer()
			.append("select ct.name, ").append("ct.center_packet_type_id, ")
			.append("ct.center_connect_type_id ").append(
					"from db.connect_type ct ").append(
					"where ct.is_active='T' ").append("and ct.id = ?")
			.toString();

	private CenterConnectType getCenterConnectType(Connection conn,
			Long connectTypeId) throws SQLException, BRequestException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			log.debug("GET_CENTER_CONNECT_TYPE_STMT = "
					+ GET_CENTER_CONNECT_TYPE_STMT);
			log.debug("connectTypeId = " + connectTypeId);
			pstmt = conn.prepareStatement(GET_CENTER_CONNECT_TYPE_STMT);
			pstmt.setLong(1, connectTypeId.longValue());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(2) == null || rs.getString(3) == null) {
					throw new BRequestException(
							CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.CONNECT_TYPE_CREATED_ERROR,
							"Connection type created with errors");
				}
				return new CenterConnectType(rs.getLong(2), rs.getLong(3));
			} else
				throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.CONNECT_TYPE_CREATED_ERROR,
						"Connection type created with errors");
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
	}

	private static String ORDER_DEVICE_ID_STMT = "select db.seq_order.nextval id from dual";

	private long getOrderDeviceId(Connection conn) throws SQLException,
			BRequestException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			log.debug("ORDER_DEVICE_ID_STMT = " + ORDER_DEVICE_ID_STMT);
			pstmt = conn.prepareStatement(ORDER_DEVICE_ID_STMT);
			rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getLong(1);
			else
				throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.ABONENT_ERROR,
						"Could not get next value of sequence");
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
	}

	private static String CREATE_ORDER_DEVICE_PSTMT = "{ call db.create_order_device ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) }";

	private long originateOrderDevice(Connection conn, long orderDeviceid,
			long actionId, String device, Long abonentId, Long abonentGroupId,
			Long deviceGroupId, Long abonentTownId, Long oldConnectTypeId,
			Long newConnectTypeId, Long addressId, Long userId,
			Long tariffTypeId, Double tariff, String note, Long ndsTypeId,
			Long discountTypeId, Long servicePacketTypeId,
			Long serviceCountGroupId, Double discount, Double nds,
			Date openDate, Date closeDate, Date endSysDate) throws SQLException {
		CallableStatement cstmt = null;
		try {
			log.debug("CREATE_ORDER_DEVICE_PSTMT = "
					+ CREATE_ORDER_DEVICE_PSTMT);
			cstmt = conn.prepareCall(CREATE_ORDER_DEVICE_PSTMT);
			cstmt.setLong(1, orderDeviceid);
			cstmt.setLong(2, actionId);
			cstmt.setLong(3, abonentId);
			cstmt.setLong(4, abonentId);
			cstmt.setLong(5, abonentGroupId);
			cstmt.setLong(6, deviceGroupId);
			cstmt.setLong(7, abonentTownId);
			cstmt.setString(8, device);
			cstmt.setString(9, device);
			if (oldConnectTypeId == null)
				cstmt.setNull(10, Types.NUMERIC);
			else
				cstmt.setLong(10, oldConnectTypeId);
			cstmt.setLong(11, newConnectTypeId);
			if (addressId != null) {
				cstmt.setLong(12, addressId);
				cstmt.setLong(13, addressId);
			} else {
				cstmt.setNull(12, Types.NUMERIC);
				cstmt.setNull(13, Types.NUMERIC);
			}
			cstmt.setNull(14, Types.NUMERIC);
			cstmt.setNull(15, Types.NUMERIC);
			cstmt.setNull(16, Types.DATE);
			// cstmt.setDate(17, new java.sql.Date(serverDate));
			cstmt.setTimestamp(17, new Timestamp(openDate.getTime()));
			if (closeDate != null)
				cstmt.setTimestamp(18, new Timestamp(closeDate.getTime()));
			else
				cstmt.setNull(18, Types.TIMESTAMP);
			// cstmt.setDate(19, new java.sql.Date(serverDate));
			cstmt.setTimestamp(19, new Timestamp(openDate.getTime()));
			if (endSysDate == null)
				cstmt.setNull(20, Types.DATE);
			else
				cstmt.setDate(20, new java.sql.Date(endSysDate.getTime()));
			cstmt.setLong(21, 0);
			cstmt.setNull(22, Types.NUMERIC);
			if (tariffTypeId != null)
				cstmt.setLong(23, tariffTypeId);
			else
				cstmt.setNull(23, Types.NUMERIC);
			if (tariff != null)
				cstmt.setDouble(24, tariff);
			else
				cstmt.setNull(24, Types.NUMERIC);
			cstmt.setNull(25, Types.NUMERIC);
			cstmt.setNull(26, Types.NUMERIC);
			cstmt.setLong(27, 0);
			if (userId != null)
				cstmt.setLong(28, userId);
			else
				cstmt.setNull(28, Types.NUMERIC);
			cstmt.setNull(29, Types.VARCHAR);
			cstmt.setString(30, note);
			cstmt.setNull(31, Types.VARCHAR);
			cstmt.setNull(32, Types.VARCHAR);
			if (ndsTypeId != null)
				cstmt.setLong(33, ndsTypeId);
			else
				cstmt.setNull(33, Types.NUMERIC);
			if (discountTypeId != null)
				cstmt.setLong(34, discountTypeId);
			else
				cstmt.setNull(34, Types.NUMERIC);
			if (servicePacketTypeId != null)
				cstmt.setLong(35, servicePacketTypeId);
			else
				cstmt.setNull(35, Types.NUMERIC);
			if (serviceCountGroupId != null)
				cstmt.setLong(36, serviceCountGroupId);
			else
				cstmt.setNull(36, Types.NUMERIC);
			if (discount != null)
				cstmt.setDouble(37, discount);
			else
				cstmt.setNull(37, Types.NUMERIC);
			if (nds != null)
				cstmt.setDouble(38, nds);
			else
				cstmt.setNull(38, Types.NUMERIC);
			cstmt.registerOutParameter(39, Types.NUMERIC);
			cstmt.execute();
			return cstmt.getLong(39);
		} finally {
			CommonOperations.closeStatement(cstmt);
		}

	}

	private static String INSERT_CENTER_ORDER_DEVICE = new StringBuffer()
			.append("insert into db.center_order_device ").append(
					"(order_device_id, ").append("center_packet_type_id, ")
			.append("center_connect_type_id, ").append("center_nds_type_id, ")
			.append("note) ").append("values ").append("( ? , ")
			.append(" ? , ").append(" ? , ").append(" ? , ").append(" ? )")
			.toString();

	private void createCenterOrderDevice(Connection conn, long orderDeviceid,
			long centerPacketTypeId, long centerConnectTypeId, String keyWord)
			throws SQLException, RealsoftException {
		PreparedStatement pstmt = null;
		try {
			log.debug("INSERT_CENTER_ORDER_DEVICE = "
					+ INSERT_CENTER_ORDER_DEVICE);
			log.debug("orderDeviceid = " + orderDeviceid
					+ " centerPacketTypeId = " + centerPacketTypeId
					+ " centerConnectTypeId = " + centerConnectTypeId
					+ " keyWord = " + keyWord);
			pstmt = conn.prepareStatement(INSERT_CENTER_ORDER_DEVICE);
			pstmt.setLong(1, orderDeviceid);
			pstmt.setLong(2, centerPacketTypeId);
			pstmt.setLong(3, centerConnectTypeId);
			pstmt.setLong(4, 1);
			pstmt.setString(5, HashCalculator.getMD5(keyWord));
			pstmt.execute();
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#makeOrderDevice(java.lang.Long,
	 *      java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long,
	 *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long,
	 *      java.lang.Double, java.lang.String, java.lang.Long, java.lang.Long,
	 *      java.lang.Long, java.lang.Long, java.lang.Double, java.lang.Double,
	 *      java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public BOrderDeviceItem makeOrderDevice(Long townId, String device,
			Long abonentId, Long abonentGroupId, Long abonentTownId,
			Long connectTypeId, Long addressId, Long userId, Long tariffTypeId,
			Double tariff, String note, Long ndsTypeId, Long discountTypeId,
			Long servicePacketTypeId, Long serviceCountGroupId,
			Double discount, Double nds, String devideGroupName,
			String serviceCenterType, String snote, boolean isMegalineCall)
			throws BRequestException, BUtilException {
		util = (IBUtil) beanFactory.getBean(
				ComponentFactoryImpl.getBUtilName(), IBUtil.class);
		Long deviceGroupId = Long.valueOf(util
				.getDeviceGroupId(devideGroupName));
		Long serviceCenterTypeId = Long.valueOf((String) util
				.getConstValue(serviceCenterType));
		String shortDevice = util.getShortDevice(device);
		BRequestDirectionItem directionItem = getWorkplaceId(abonentTownId,
				serviceCenterTypeId, shortDevice, deviceGroupId);
		BOrderDeviceItem deviceItem = createOrderDevice(abonentTownId,
				shortDevice, abonentId, abonentGroupId, deviceGroupId,
				abonentTownId, connectTypeId, addressId, userId, tariffTypeId,
				tariff, note, ndsTypeId, discountTypeId, servicePacketTypeId,
				serviceCountGroupId, discount, nds, isMegalineCall);
		sendOrder(abonentTownId, deviceItem.getOrderDeviceId(), 0,
				directionItem.getDirectId(), directionItem.getDirectDetail(),
				snote, userId, null, 0, false, 0, 0);
		return deviceItem;
	}

	public BOrderDeviceItem makeOrderDevice(Long townId, String device,
			Long abonentId, Long connectTypeId, Long tariffTypeId,
			Double tariff, String note, Long ndsTypeId, Long discountTypeId,
			Long servicePacketTypeId, Long serviceCountGroupId,
			String deviceGroup, Double discount, Double nds, String snote,
			boolean isMegalineCall) throws BRequestException, BUtilException,
			BURLResolverException {
		try {
			BAbonentInfo13 abonentInfo = abonent
					.getAbonentInfo13ById(abonentId);
			return makeOrderDevice(townId, device, abonentId, abonentInfo
					.getAbonentGroupId(), abonentInfo.getTownId(),
					connectTypeId, abonentInfo.getAddressId(), operatorId,
					null, null, " ", null, (long) 1, null, null, (double) 1,
					null, deviceGroup, orderDestination, "", false);
		} catch (BAbonentException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR, e.getMessage(), e);
		} catch (BRegistrationException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR, e.getMessage(), e);
		} catch (BMailException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR, e.getMessage(), e);
		}
	}

	private static String GET_ORDER_DEVICE = new StringBuffer().append(
			"select od.id, ").append("od.action_id, ")
			.append("od.abonent_id, ").append("od.device_group_id, ").append(
					"od.town_id, ").append("od.old_device, ").append(
					"od.new_device, ").append("od.old_connect_type_id, ")
			.append("od.new_connect_type_id, ").append("od.old_address_id, ")
			.append("od.new_address_id, ").append("od.old_conditions, ")
			.append("od.new_conditions, ").append("od.to_date, ").append(
					"od.open_date, ").append("od.close_date, ").append(
					"od.begin_sysdate, ").append("od.end_sysdate, ").append(
					"od.priority_id, ").append("od.tariff_timing_id, ").append(
					"od.tariff_type_id, ").append("od.tariff, ").append(
					"od.smeta, ").append("od.smeta_retax, ").append(
					"od.amount, ").append("od.user_id, ").append(
					"od.is_deleted, ").append("od.note, ").append(
					"od.abonent_group_id, ").append("od.nds_type_id, ").append(
					"od.discount_type_id, ").append(
					"od.service_packet_type_id, ").append("od.discount, ")
			.append("od.nds, ").append("od.bill_type_id, ").append(
					"od.new_abonent_id, ")
			.append("od.service_count_group_id, ")
			.append("od.old_department, ").append("od.new_department ").append(
					"from order_device od ").append("where od.id = ?")
			.toString();

	private BOrderDeviceItem getOrderDeviceItem(long orderId)
			throws BRequestException {
		log.info("getting order device orderId = " + orderId);
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(GET_ORDER_DEVICE);
				pstmt.setLong(1, orderId);
				ResultSet rs = pstmt.executeQuery();
				BOrderDeviceItem result = null;
				if (rs.next()) {
					result = new BOrderDeviceItem(rs.getLong(1), rs.getLong(2),
							rs.getLong(3), rs.getLong(4), rs.getLong(5), rs
									.getString(6), rs.getString(7), rs
									.getLong(8), rs.getLong(9), rs.getLong(10),
							rs.getLong(11), rs.getString(12), rs.getString(13),
							null, null, null, null, null, rs.getLong(19), rs
									.getLong(20), rs.getLong(21), rs
									.getDouble(22), rs.getDouble(23), rs
									.getDouble(24), rs.getDouble(25), rs
									.getLong(26), rs.getString(27), rs
									.getString(28), rs.getLong(29), rs
									.getLong(30), rs.getLong(31), rs
									.getLong(32), rs.getDouble(33), rs
									.getDouble(34), rs.getLong(35), rs
									.getLong(36), rs.getLong(37), rs
									.getString(38), rs.getString(39), null);
					if (rs.getTimestamp(14) != null) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getTimestamp(14));
						result.setToDate(calendar);
					}
					if (rs.getTimestamp(15) != null) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getTimestamp(15));
						result.setOpenDate(calendar);
					}
					if (rs.getTimestamp(16) != null) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getTimestamp(16));
						result.setCloseDate(calendar);
					}
					if (rs.getTimestamp(17) != null) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getTimestamp(17));
						result.setBeginSysdate(calendar);
					}
					if (rs.getTimestamp(18) != null) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getTimestamp(18));
						result.setEndSysdate(calendar);
					}
					return result;
				}
				throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.REQUEST_ORDER_DONOT_EXIST_ERROR,
						"No such order " + orderId + " found");
			} finally {
				CommonOperations.closeStatement(pstmt);
			}
		} catch (SQLException e) {
			log.error("Could not create order device", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static String DO_ORDER_DEVICE = new StringBuffer().append(
			"{ call db.do_order_device ( ?, ?, ?) }").toString();

	public long doOrderDevice(Connection conn, long orderId)
			throws BRequestException {
		CallableStatement cstmt = null;
		try {
			log.info("DO_ORDER_DEVICE = " + DO_ORDER_DEVICE);
			cstmt = conn.prepareCall(DO_ORDER_DEVICE);
			cstmt.setLong(1, orderId);
			cstmt.setLong(3, 1);
			cstmt.registerOutParameter(2, Types.NUMERIC);
			cstmt.execute();
			return cstmt.getLong(2);
		} catch (SQLException e) {
			log.error("Could not do order ", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#closeOrderDevice(long,
	 *      long, java.util.Date, java.lang.String)
	 */
	public void closeOrderDevice(long townId, long orderId, Date closeDate,
			long userId, String note) throws BRequestException, BUtilException {
		log.info("creating order device ...");
		log.info("townId " + townId + ", orderId = " + orderId
				+ ", closeDate = " + closeDate + ", note = " + note);

		if (!isOrderDeviceExists(orderId)) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ORDER_DONOT_EXIST_ERROR,
					"Order does not exist");
		}

		BOrderDeviceItem orderDeviceItem = getOrderDeviceItem(orderId);

		Connection conn = null;
		long result = 0;
		try {
			conn = dataSource.getConnection();

			CallableStatement cstmt = null;

			Date serverDate;
			try {
				IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
						.getBUtilName(), IBUtil.class);
				serverDate = util.getDBSysDate();
				log.debug("CREATE_ORDER_DEVICE_PSTMT = "
						+ CREATE_ORDER_DEVICE_PSTMT);

				originateOrderDevice(conn, orderId, (long) 6, orderDeviceItem
						.getNewDevice(), orderDeviceItem.getAbonentId(),
						orderDeviceItem.getAbonentGroupId(), orderDeviceItem
								.getDeviceGroupId(), orderDeviceItem
								.getTownId(), null, orderDeviceItem
								.getNewConnectTypeId(), orderDeviceItem
								.getNewAddressId(), userId, orderDeviceItem
								.getTariffTypeId(),
						orderDeviceItem.getTariff(), note, orderDeviceItem
								.getNdsTypeId(), orderDeviceItem
								.getDiscountTypeId(), orderDeviceItem
								.getServicePacketTypeId(), orderDeviceItem
								.getServiceCountGroupId(), orderDeviceItem
								.getDiscount(), orderDeviceItem.getNds(),
						serverDate, serverDate, serverDate);

				result = cstmt.getLong(39);
				log.debug("result = " + result);

				if (result != 0) {
					conn.rollback();
					throw new BRequestException(
							CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.REQUEST_ERROR,
							"creating orderder device with error result is not zero");
				}

			} finally {
				CommonOperations.closeStatement(cstmt);
			}

			if (doOrderDevice(conn, orderId) != 0) {
				CommonOperations.rollbackConnection(conn);
				throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.REQUEST_ERROR,
						"could not do order");
			}

			CommonOperations.commitConnection(conn);
			log.info("creating order device done");
		} catch (SQLException e) {
			log.error("Could not create order device", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	// public CRequestFormInfo getRequestFormInfo(long townId, long abonentId)
	// throws BRequestException, BUtilException {
	// IBUtil util = null;
	// try {
	// ServiceSelector selector = null;
	// try {
	// selector = (ServiceSelector) manager.lookup(IBUtil.ROLE
	// + IComponentFactory.SELECTOR);
	// util = (IBUtil) selector.select(jdbcName);
	// manager.release(selector);
	//
	// List deviceList = getDeviceList(townId, abonentId);
	// List connectTypes = getConnectTypes(townId, util
	// .getDeviceGroupId("BILL_CLIENT_ADSL"));
	// return new CRequestFormInfo(deviceList, connectTypes);
	// } finally {
	// manager.release(util);
	// }
	// } catch (ServiceException e) {
	// log.error("Could not create order device", e);
	// throw new BRequestException(SpringBeansConstants.ERR_ACCOUNT,
	// SpringBeansConstants.REQUEST_ERROR, e.getMessage(), e);
	// }
	// }
	//
	private static String REQUEST_TABLE_INFO_STMT = new StringBuffer().append(
			"select od.id, ").append("od.abonent_id,  ").append("a.name, ")
			.append("tn.name town_name, ").append(
					"db.get_adr(od.abonent_id,1,1), ")
			.append("od.new_device, ").append("ct.name connect_type, ").append(
					"od.open_date, ").append("od.close_date, ").append(
					"od.note, od.device_group_id").append(
					"from db.order_device od, ").append("db.abonent a, ")
			.append("db.town tn, ").append("db.connect_type ct ").append(
					"where a.id = od.abonent_id ").append(
					"and od.town_id = tn.id ").append(
					"and od.new_connect_type_id = ct.id ").append(
					"and od.user_id = ? ").append("order by od.open_date desc")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestTableInfo(long)
	 */
	public List<BRequestTableItem> getRequestTableInfo(long userId)
			throws BRequestException {
		log.info("getting request table info userId = " + userId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.info("REQUEST_TABLE_INFO_STMT = " + REQUEST_TABLE_INFO_STMT);
			pstmt = conn.prepareStatement(REQUEST_TABLE_INFO_STMT);
			pstmt.setLong(1, userId);
			rs = pstmt.executeQuery();
			List<BRequestTableItem> list = new ArrayList<BRequestTableItem>();
			while (rs.next()) {
				list.add(new BRequestTableItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getTimestamp(8), rs
						.getTimestamp(9), rs.getString(10), rs.getLong(11), rs
						.getLong(12)));
			}
			log.info("getting request table info done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get request table info list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR
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
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestTableInfo(long,
	 *      java.lang.Long, java.lang.Long, java.util.Date, java.util.Date,
	 *      java.util.Date, java.util.Date, boolean)
	 */
	public List<BRequestTableItem> getRequestTableInfo(long userId,
			Long requestId, Long abonentId, Date beginOpenDate,
			Date endOpenDate, Date beginCloseDate, Date endCloseDate,
			String abonentName, String device, boolean showClosedRequests)
			throws BRequestException {
		log.info("getting request table info userId = " + userId + " ...");
		log.info("requestId = " + requestId + ", abonentId = " + abonentId
				+ ", beginOpenDate = " + beginOpenDate + ", endOpenDate = "
				+ endOpenDate + ", beginCloseDate = " + beginCloseDate
				+ ", endCloseDate = " + endCloseDate);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuffer requestTableInfo = new StringBuffer().append(
					"select od.id, ").append("od.abonent_id,  ").append(
					"a.name, ").append("tn.name town_name, ").append(
					"db.get_adr(od.new_address_id,1,1), ").append(
					"od.new_device, ").append("ct.name connect_type, ").append(
					"od.begin_sysdate, ").append("od.end_sysdate, ").append(
					"od.note, od.device_group_id, od.town_id ").append(
					"from db.order_device od, ").append("db.abonent a, ")
					.append("db.town tn, ").append("db.connect_type ct ")
					.append("where a.id = od.abonent_id ").append(
							"and od.town_id = tn.id ").append(
							"and od.new_connect_type_id = ct.id ").append(
							"and od.user_id = ? ");

			if (requestId != null) {
				requestTableInfo.append("and od.id = ? ");
			}

			if (abonentId != null) {
				requestTableInfo.append("and od.abonent_id = ? ");
			}

			if (beginOpenDate != null && endOpenDate != null) {
				requestTableInfo
						.append("and trunc(od.begin_sysdate, 'DD') >= trunc(?,'DD') and trunc(od.begin_sysdate,'DD') <= trunc(?,'DD') ");
			} else if (beginOpenDate != null && endOpenDate == null) {
				requestTableInfo.append("and od.begin_sysdate >= ? ");
			} else if (beginOpenDate == null && endOpenDate != null) {
				requestTableInfo.append("and od.begin_sysdate <= ? ");
			}

			if (beginCloseDate != null && endCloseDate != null) {
				requestTableInfo
						.append("and trunc(od.end_sysdate,'DD') >= ? and trunc(od.end_sysdate,'DD') <= trunc(?, 'DD') ");
			} else if (beginCloseDate != null && endCloseDate == null) {
				requestTableInfo.append("and od.end_sysdate >= ? ");
			} else if (beginCloseDate == null && endCloseDate != null) {
				requestTableInfo.append("and od.end_sysdate <= ? ");
			}
			if (abonentName != null && abonentName.length() != 0)
				requestTableInfo.append("and lower(a.name) like '%"
						+ abonentName.toLowerCase().trim() + "%' ");
			if (device != null && device.length() != 0)
				requestTableInfo.append("and od.new_device = ? ");
			if (!showClosedRequests)
				requestTableInfo.append("and od.end_sysdate is null ");

			requestTableInfo.append("order by od.id ");

			log
					.info("REQUEST_TABLE_INFO_STMT = "
							+ requestTableInfo.toString());
			pstmt = conn.prepareStatement(requestTableInfo.toString());
			pstmt.setLong(1, userId);

			int counter = 1;
			if (requestId != null) {
				pstmt.setLong(++counter, requestId.longValue());
			}

			if (abonentId != null) {
				pstmt.setLong(++counter, abonentId.longValue());
			}

			if (beginOpenDate != null && endOpenDate != null) {
				pstmt.setDate(++counter, new java.sql.Date(beginOpenDate
						.getTime()));
				pstmt.setDate(++counter, new java.sql.Date(endOpenDate
						.getTime()));
			} else if (beginOpenDate != null && endOpenDate == null) {
				pstmt.setDate(++counter, new java.sql.Date(beginOpenDate
						.getTime()));
			} else if (beginOpenDate == null && endOpenDate != null) {
				pstmt.setDate(++counter, new java.sql.Date(endOpenDate
						.getTime()));
			}

			if (beginCloseDate != null && endCloseDate != null) {
				pstmt.setDate(++counter, new java.sql.Date(beginCloseDate
						.getTime()));
				pstmt.setDate(++counter, new java.sql.Date(endCloseDate
						.getTime()));
			} else if (beginCloseDate != null && endCloseDate == null) {
				pstmt.setDate(++counter, new java.sql.Date(beginCloseDate
						.getTime()));
			} else if (beginCloseDate == null && endCloseDate != null) {
				pstmt.setDate(++counter, new java.sql.Date(endCloseDate
						.getTime()));
			}

			if (device != null && device.length() != 0)
				pstmt.setString(++counter, device.trim());

			rs = pstmt.executeQuery();
			List<BRequestTableItem> list = new ArrayList<BRequestTableItem>();
			while (rs.next()) {
				log.debug("Order device: id = " + rs.getLong(1)
						+ "; open_date = " + rs.getTimestamp(8)
						+ " ................... ");
				list.add(new BRequestTableItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getTimestamp(8), rs
						.getTimestamp(9), rs.getString(10), rs.getLong(11), rs
						.getLong(12)));
			}
			log.info("getting request table info done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get request table info list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String REQUEST_INFO_STMT = new StringBuffer().append(
			"select od.id, ").append("od.abonent_id,  ").append("a.name, ")
			.append("tn.name town_name, ").append(
					"db.get_adr(od.new_address_id,1,1), ").append(
					"od.new_device, ").append("ct.name connect_type, ").append(
					"od.open_date, ").append("od.close_date, ").append(
					"od.note, od.device_group_id, od.town_id ").append(
					"from db.order_device od, ").append("db.abonent a, ")
			.append("db.town tn, ").append("db.connect_type ct ").append(
					"where a.id = od.abonent_id ").append(
					"and od.town_id = tn.id ").append(
					"and od.new_connect_type_id = ct.id ").append(
					"and od.id = ? ").append("order by od.open_date desc")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestInfo(long)
	 */
	public BRequestTableItem getRequestInfo(long orderId)
			throws BRequestException {
		log.info("getting request table info orderId = " + orderId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.info("REQUEST_INFO_STMT = " + REQUEST_INFO_STMT);
			pstmt = conn.prepareStatement(REQUEST_INFO_STMT);
			pstmt.setLong(1, orderId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				log.info("getting request table info done");
				return new BRequestTableItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getTimestamp(8), rs
						.getTimestamp(9), rs.getString(10), rs.getLong(11), rs
						.getLong(12));
			}
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR,
					"Could not find such order device " + orderId);
		} catch (SQLException e) {
			log.error("Could not get request table info list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String REQUEST_ORDER_STAGES_STMT = new StringBuffer()
			.append(
					"select (select substr(full_name,1, 250) from tech.vservice_center_type where id = os.directid) dtname, ")
			.append(
					"(select v2.node_town_name from tech.vservice_center v2 where id = os.directdetail) ddname_town_name, ")
			.append(
					"(select v2.node from tech.vservice_center v2 where id = os.directdetail) ddname_node, ")
			.append(
					"(select v2.name from tech.vservice_center v2 where id = os.directdetail) ddname_srv_name, ")
			.append("os.datein, ")
			.append("os.there, ")
			.append("os.backagain, ")
			.append("os.snote, ")
			.append("os.rnote, ")
			.append("xu.fname||' '||xu.lname s_name, ")
			.append(
					"xu1.fname||' '||xu1.lname r_name, ( select nvl(node_town_name,'\u0412\u0441\u0435')||'\\'||node||'\\'||name from tech.vservice_center v2 where id = os.directdetail) work_place_name ")
			.append("from tech.orderstages os, ").append("safety.xusers xu, ")
			.append("safety.xusers xu1 ").append("where os.orderid = ? ")
			.append("and os.suserid=xu.id(+) ").append(
					"and os.ruserid=xu1.id(+) ").append("order by datein")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestOrderStagesList(long)
	 */
	public List<BRequestOrderStageItem> getRequestOrderStagesList(long orderId)
			throws BRequestException, BUtilException {
		log.info("getting request order stages list orderId = " + orderId
				+ " ...");
		int iterations = 0;
		Connection conn = null;
		try {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			IBUtil util = null;
			try {
				conn = dataSource.getConnection();
				log.info("Requests connection.hashCode= "
						+ String.valueOf(conn.hashCode()));
				log.debug("REQUEST_ORDER_STAGES_STMT = "
						+ REQUEST_ORDER_STAGES_STMT);
				pstmt = conn.prepareStatement(REQUEST_ORDER_STAGES_STMT);
				pstmt.setLong(1, orderId);
				rs = pstmt.executeQuery();
				util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
						.getBUtilName());
				List<BRequestOrderStageItem> list = new ArrayList<BRequestOrderStageItem>();
				while (rs.next()) {
					iterations++;
					BRequestOrderStageItem item = new BRequestOrderStageItem(rs
							.getString(1), rs.getString(2), rs.getString(3), rs
							.getString(4), rs.getTimestamp(5), rs
							.getTimestamp(6), rs.getTimestamp(7), rs
							.getString(8), rs.getString(9), rs.getString(10),
							rs.getString(11), rs.getString(12));
					item.setKeyWord(util.getKeyWord(orderId));
					list.add(item);
				}
				log.info("iterations = " + iterations);
				log.info("getting request table info done");
				return list;
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not get request table info list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (Exception w) {
			log.info(w.getMessage());
			throw new BRequestException(CommonsBeansConstants.ERR_ACCOUNT,
					CommonsBeansConstants.REQUEST_TABLE_INFO_ERROR, w
							.getMessage());
		}
	}

	private static String REQUEST_DIRECTION_LIST_STMT = new StringBuffer()
			.append("select id, ").append("master_id, ").append("name, ")
			.append("note, ").append("visible, ").append(
					"trim(substr(full_name, 1, 50)) full_name ").append(
					"from tech.vservice_center_type ").append(
					"order by full_name").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestDirectionList()
	 */
	public List<BRequestDirectionItem> getRequestDirectionList()
			throws BRequestException {
		log.info("getting request direction list ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("REQUEST_DIRECTION_LIST_STMT = "
					+ REQUEST_DIRECTION_LIST_STMT);
			pstmt = conn.prepareStatement(REQUEST_DIRECTION_LIST_STMT);
			rs = pstmt.executeQuery();
			List<BRequestDirectionItem> list = new ArrayList<BRequestDirectionItem>();
			while (rs.next()) {
				list.add(new BRequestDirectionItem(rs.getLong(1),
						rs.getLong(2), rs.getString(3), rs.getString(4), rs
								.getInt(5), rs.getString(6)));
			}
			log.info("getting request direction list done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get request direction list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_REQUEST_DIRECTION_STMT = new StringBuffer()
			.append(
					"select nvl(tech.get_device_service_center_id(?, ?, ?, ? ), -100) from dual")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRequestDirection(java.lang.Long,
	 *      java.lang.String)
	 */
	public BRequestDirectionItem getRequestDirection(Long townIdOrder,
			Long townIdRoute, Long serviceCenterTypeId, String device,
			Long deviceGroupId) throws BRequestException {
		log.info("getting request serviceCenterTypeId = " + serviceCenterTypeId
				+ "; townIdOrder = " + townIdOrder + "; townIdRoute = "
				+ townIdRoute + "; device = " + device + "; deviceGroupId = "
				+ deviceGroupId + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("GET_REQUEST_DIRECTION_STMT = "
					+ GET_REQUEST_DIRECTION_STMT);
			// Пытаемся определить направление наряда путем вызова
			// tech.get_device_service_center_id
			PreparedStatement preparedStatement = conn
					.prepareStatement(GET_REQUEST_DIRECTION_STMT);
			preparedStatement.setLong(1, serviceCenterTypeId);
			if (device == null)
				preparedStatement.setNull(2, Types.VARCHAR);
			else
				preparedStatement.setString(2, device);
			if (townIdOrder == null)
				preparedStatement.setNull(3, Types.NUMERIC);
			else
				preparedStatement.setLong(3, townIdOrder);
			if (deviceGroupId == null)
				preparedStatement.setNull(4, Types.NUMERIC);
			else
				preparedStatement.setLong(4, deviceGroupId);
			rs = preparedStatement.executeQuery();
			rs.next();
			if (rs.getInt(1) != -100) {
				log.info("getting request direction info done");
				return new BRequestDirectionItem(serviceCenterTypeId, rs
						.getInt(1));
			}
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_NOEXISTS_ERROR,
					"Could not get request direction");
		} catch (SQLException e) {
			log.error("Could not get request direction", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_ERROR
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
	 * @see com.realsoft.commons.beans.request.IBRequest#getWorkplaceId(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.Long)
	 */

	public BRequestDirectionItem getWorkplaceId(Long townId,
			Long serviceCenterTypeId, String device, Long deviceGroupId)
			throws BRequestException {
		log.debug("Trying to find out the request direction...");
		BRequestDirectionItem directionItem = null;
		Long primaryDeviceGroup = new Long(
				CommonsBeansConstants.PRIMARY_DEVICE_GROUP_ID_DEFAULT_VALUE);
		try {
			primaryDeviceGroup = Long
					.valueOf((String) util
							.getConstValue(CommonsBeansConstants.PRIMARY_DEVICE_GROUP_ID_CONST_NAME));
		} catch (BUtilException e) {
			log.error("Could not get primaryDeviceGroup", e);
			log
					.debug("Setting default primaryDeviceGroup: "
							+ CommonsBeansConstants.PRIMARY_DEVICE_GROUP_ID_DEFAULT_VALUE);
		}
		try {
			log
					.debug("Step 1 : getting request direction by device group in request...");
			directionItem = getRequestDirection(townId, null,
					serviceCenterTypeId, device, deviceGroupId);
			log.debug("request direction sucessfully found on step 1");
		} catch (BRequestException e) {
			log.error("Step 1 failed", e);
			try {
				log
						.debug("Step 2 : Trying to get request direction by primaryDeviceGroup = "
								+ primaryDeviceGroup.longValue());
				directionItem = getRequestDirection(townId, null,
						serviceCenterTypeId, device, primaryDeviceGroup);
				log.debug("request direction sucessfully found on step 2");
			} catch (BRequestException re) {
				log.error("Step 2 failed", e);
				try {
					log
							.debug("Step 3 : Trying to get empty workplace of service with id = "
									+ serviceCenterTypeId + " ...");
					directionItem = getEmptyWorkPlace(serviceCenterTypeId);
					log.debug("request direction sucessfully found on step 3");
				} catch (BRequestException er) {
					log.error("Step 3 failed", e);
					log
							.debug("Step 4 : Trying to get random workplace of service with id = "
									+ serviceCenterTypeId + " ...");
					directionItem = getRandomWorkPlace(serviceCenterTypeId);
				}
			}
		}
		return directionItem;
	}

	public BRequestDirectionItem getWorkplaceId(Long townIdForRoute,
			Long townId, Long serviceCenterTypeId, String device,
			Long deviceGroupId) throws BRequestException {
		return getWorkplaceId(townId, serviceCenterTypeId, device,
				deviceGroupId);
	}

	private static final String GET_RANDOM_WORKPLACE_ID_STMT = new StringBuffer()
			.append(
					"select c.id from tech.service_center_type ct, tech.service_center c ")
			.append(
					"where c.service_center_type_id = ct.id  and ct.id = ? order by c.id desc")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getRandomWorkPlace(java.lang.Long)
	 */

	public BRequestDirectionItem getRandomWorkPlace(Long serviceCenterTypeId)
			throws BRequestException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			log.debug("Getting random workplace in service with ID = "
					+ serviceCenterTypeId);
			conn = dataSource.getConnection();
			log.debug("GET_RANDOM_WORKPLACE_ID_STMT = "
					+ GET_RANDOM_WORKPLACE_ID_STMT);
			PreparedStatement preparedStatement = conn
					.prepareStatement(GET_RANDOM_WORKPLACE_ID_STMT);
			preparedStatement.setLong(1, serviceCenterTypeId);
			rs = preparedStatement.executeQuery();
			if (rs.next())
				return new BRequestDirectionItem(serviceCenterTypeId, rs
						.getInt(1));
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_NOEXISTS_ERROR,
					"Service (service_center_type) with id = "
							+ serviceCenterTypeId + " have no any workplaces");
		} catch (SQLException e) {
			log.error("Could not get random workplace of service with id = "
					+ serviceCenterTypeId, e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_EMPTY_WORKPLACE_ID_STMT = new StringBuffer()
			.append(
					"select ct.id, c.id from tech.service_center_type ct, tech.service_center c where c.service_center_type_id = ct.id ")
			.append(
					"and not exists(select * from TECH.vservice_center_device vscd where vscd.service_center_id = c.id) and ct.id = ? ")
			.append("order by c.id ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getEmptyWorkPlace(java.lang.Long)
	 */
	public BRequestDirectionItem getEmptyWorkPlace(Long serviceCenterTypeId)
			throws BRequestException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			log.debug("Getting empty workplace in service with ID = "
					+ serviceCenterTypeId);
			log.debug("GET_EMPTY_WORKPLACE_ID_STMT = "
					+ GET_EMPTY_WORKPLACE_ID_STMT);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(GET_EMPTY_WORKPLACE_ID_STMT);
			pstmt.setLong(1, serviceCenterTypeId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				log.info("getting empty workplace id done, serviceId = "
						+ rs.getLong(1) + "; workplace id = " + rs.getLong(2));
				return new BRequestDirectionItem(rs.getLong(1), rs.getLong(2));
			}
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_NOEXISTS_ERROR,
					"Service (service_center_type) with id = "
							+ serviceCenterTypeId + " have no empty workplaces");
		} catch (SQLException e) {
			log.error("Could not get empty workplace of service with id = "
					+ serviceCenterTypeId, e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_DIRECTION_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String SEND_ORDER_PSTMT = "{ call db.sendorder_fromabon ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#sendOrder(long, long,
	 *      long, long, long, java.lang.String, java.lang.Long, java.lang.Long,
	 *      double, boolean, long, double)
	 */
	public long sendOrder(long townId, long orderId, long packId,
			long directId, long directDetail, String snote, Long suserId,
			Long sourceGroupId, double debit, boolean isOld, long oldDirectId,
			double sysdateTax) throws BRequestException {
		log.info("sendind order ...");
		log.info("orderId = " + orderId + "; packId = " + packId
				+ "; directId = " + directId + "; directDetail = "
				+ directDetail + "; snote = " + snote + "; suserId = "
				+ suserId + "; sourceGroupId = " + sourceGroupId + "; debit = "
				+ debit + "; isOld = " + isOld + "; oldDirectId = "
				+ oldDirectId + "; sysdateTax = " + sysdateTax);

		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			CallableStatement cstmt = null;
			try {
				log.info("SEND_ORDER_PSTMT = " + SEND_ORDER_PSTMT);
				cstmt = conn.prepareCall(SEND_ORDER_PSTMT);
				cstmt.setLong(1, orderId);
				cstmt.setLong(2, packId);
				cstmt.setLong(3, directId);
				cstmt.setLong(4, directDetail);
				cstmt.setString(5, snote);
				CommonOperations.setValue(cstmt, 6, suserId);
				CommonOperations.setValue(cstmt, 7, sourceGroupId);
				cstmt.setDouble(8, debit);
				cstmt.setInt(9, (isOld ? 0 : 1));
				cstmt.setLong(10, oldDirectId);
				cstmt.setDouble(11, sysdateTax);
				cstmt.registerOutParameter(2, Types.NUMERIC);
				cstmt.execute();
				CommonOperations.commitConnection(conn);
				log.info("sending order done");
				return cstmt.getLong(2);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not sending order device", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ORDER_SEND_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	public long updateEmail(String email, Long abonentId, Long abonentGroupId,
			Long deviceGroupId, Long townId, Long connectTypeId,
			Long addressId, Long userId, String note) throws BRequestException,
			BUtilException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			long orderId = getOrderDeviceId(conn);
			IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
					.getBUtilName(), IBUtil.class);
			Date serverDate = util.getDBSysDate();
			originateOrderDevice(conn, orderId, 5, email, abonentId,
					abonentGroupId, deviceGroupId, townId, null, connectTypeId,
					addressId, userId, null, null, note, null, null, null,
					null, null, null, serverDate, serverDate, serverDate);
			doOrderDevice(conn, orderId);
			CommonOperations.commitConnection(conn);
			return orderId;
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not sending order device", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					"request.error.update-mail"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	public void deleteEmail(long mailId, String mailMame, long abonentId,
			long abonentGroupId, long deviceGroupId, long townId,
			long newConnectTypeId, long addressId, long userId)
			throws BRequestException, BUtilException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			long orderId = getOrderDeviceId(conn);
			IBUtil util = (IBUtil) beanFactory.getBean(ComponentFactoryImpl
					.getBUtilName(), IBUtil.class);
			Date serverDate = util.getDBSysDate();
			originateOrderDevice(conn, orderId, 6, mailMame, abonentId,
					abonentGroupId, deviceGroupId, townId, null,
					newConnectTypeId, addressId, userId, null, null,
					"delete e-mail", null, null, null, null, null, null,
					serverDate, serverDate, serverDate);
			doOrderDevice(conn, orderId);
			CommonOperations.commitConnection(conn);
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not delete e-mail", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					"request.error.delete-mail"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getPayServicePacketTypeId(int)
	 */

	private static String GET_PAY_SERVICE_PACKET_TYPE_ID_STMT = "select t.service_packet_type_id from db.service_packet t where action_id = ? group by t.service_packet_type_id";

	public int getPayServicePacketTypeId(int actionId) throws BRequestException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(GET_PAY_SERVICE_PACKET_TYPE_ID_STMT);
			pstmt.setInt(1, actionId);
			rs = pstmt.executeQuery();
			int iter = 0;
			while (rs.next()) {
				if (++iter > 1) {
					throw new BRequestException(
							CommonsBeansConstants.ERR_SYSTEM,
							"request.error.pay-service-packet-more-than-one-found",
							"More than one payServicePacketTypeId found");
				}
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("Could not get payServicePacketTypeId", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					"request.error.get-service-packet-type-id"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#createOrderChangeData(long,
	 *      long, long, java.lang.String, long, long, long)
	 */

	public long createOrderChangeData(long abonentId, long deviceGroupId,
			long townId, String device, long oldConnectTypeId,
			long newConnectTypeId, Long operatorId) throws BRequestException {
		Connection conn = null;
		long result = 0;
		try {
			conn = dataSource.getConnection();
			long orderDeviceid = getOrderDeviceId(conn);
			BAbonentInfo13 abonentInfo = abonent
					.getAbonentInfo13ById(abonentId);
			Date orderDate = Calendar.getInstance().getTime();
			long userId = 0;
			if (operatorId == null) {
				userId = this.operatorId;
			} else
				userId = operatorId;
			result = originateOrderDevice(conn, orderDeviceid, (long) 9,
					device, abonentId, abonentInfo.getAbonentGroupId(),
					deviceGroupId, abonentInfo.getTownId(), oldConnectTypeId,
					newConnectTypeId, abonentInfo.getAddressId(), userId,
					new Long(-1), null, null, null, null, null, null,
					new Double(1), null, orderDate, null, null);
		} catch (SQLException e) {
			log.error("Could not create order change data", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (BAbonentException e) {
			log.error("Could not create order change data", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (BRegistrationException e) {
			log.error("Could not create order change data", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (BMailException e) {
			log.error("Could not create order change data", e);
			CommonOperations.rollbackConnection(conn);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		return result;
	}

	public long createOrderChangeData(String abonentGuid, long deviceGroupId,
			long townId, String device, long oldConnectTypeId,
			long newConnectTypeId, Long operatorId) throws BRequestException {
		long abonentId = -1;
		try {
			String[] guid = abonentGuid.split("-");
			abonentId = Long.valueOf(guid[1]);
			return createOrderChangeData(abonentId, deviceGroupId, townId,
					device, oldConnectTypeId, newConnectTypeId, operatorId);
		} catch (NumberFormatException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR,
					"both part of guid must be numeric", e);
		}
	}

	private static final String GET_ORDER_DEVICE_BY_ABONENT = new StringBuffer()
			.append(
					"select od.id, od.action_id, od.abonent_id, od.device_group_id, od.town_id, od.old_device, od.new_device, od.old_connect_type_id, od.new_connect_type_id, od.old_address_id, od.new_address_id, od.old_conditions, od.new_conditions, ")
			.append(
					"od.to_date, od.open_date, od.close_date, od.begin_sysdate, od.end_sysdate, od.priority_id, od.tariff_timing_id, od.tariff_type_id, od.tariff, od.smeta, od.smeta_retax, od.amount, od.user_id, od.is_deleted, od.note, ")
			.append(
					"od.abonent_group_id, od.nds_type_id, od.discount_type_id, od.service_packet_type_id, od.discount, od.nds, od.bill_type_id, od.new_abonent_id, od.service_count_group_id, od.old_department, od.new_department, a.name ")
			.append(
					"from db.order_device od, db.action a where od.action_id = a.id and abonent_id = ? ")
			.toString();

	public List<BOrderDeviceItem> getOrderDeviceList(long abonentId)
			throws BRequestException {
		log.info("getting order device list for abonent: " + abonentId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("GET_ORDER_DEVICE_BY_ABONENT = "
					+ GET_ORDER_DEVICE_BY_ABONENT);
			pstmt = conn.prepareStatement(GET_ORDER_DEVICE_BY_ABONENT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			List<BOrderDeviceItem> list = new ArrayList<BOrderDeviceItem>();
			while (rs.next()) {
				BOrderDeviceItem orderDeviceItem = new BOrderDeviceItem();
				orderDeviceItem.setOrderDeviceId(rs.getLong(1));
				orderDeviceItem.setActionId(rs.getLong(2));
				orderDeviceItem.setAbonentId(rs.getLong(3));
				orderDeviceItem.setDeviceGroupId(rs.getLong(4));
				orderDeviceItem.setTownId(rs.getLong(5));
				orderDeviceItem.setOldDevice(rs.getString(6));
				orderDeviceItem.setNewDevice(rs.getString(7));
				orderDeviceItem.setOldConnectTypeId(rs.getLong(8));
				orderDeviceItem.setNewConnectTypeId(rs.getLong(9));
				orderDeviceItem.setOldAddressId(rs.getLong(10));
				orderDeviceItem.setNewAddressId(rs.getLong(11));
				orderDeviceItem.setOldConditions(rs.getString(12));
				orderDeviceItem.setNewConditions(rs.getString(13));

				if (rs.getTimestamp(14) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(14));
					orderDeviceItem.setToDate(calendar);
				}
				if (rs.getTimestamp(15) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(15));
					orderDeviceItem.setOpenDate(calendar);
				}
				if (rs.getTimestamp(16) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(16));
					orderDeviceItem.setCloseDate(calendar);
				}
				if (rs.getTimestamp(17) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(17));
					orderDeviceItem.setBeginSysdate(calendar);
				}
				if (rs.getTimestamp(18) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(18));
					orderDeviceItem.setEndSysdate(calendar);
				}
				orderDeviceItem.setPriorityId(rs.getLong(19));
				orderDeviceItem.setTariffTimingId(rs.getLong(20));
				orderDeviceItem.setTariffTypeId(rs.getLong(21));
				orderDeviceItem.setTariff(rs.getLong(22));
				orderDeviceItem.setSmeta(rs.getDouble(23));
				orderDeviceItem.setSmetaRetax(rs.getDouble(24));
				orderDeviceItem.setAmount(rs.getDouble(25));
				orderDeviceItem.setUserId(rs.getLong(26));
				orderDeviceItem.setIsDeleted(rs.getString(27));
				orderDeviceItem.setNote(rs.getString(28));
				orderDeviceItem.setAbonentGroupId(rs.getLong(29));
				orderDeviceItem.setNdsTypeId(rs.getLong(30));
				orderDeviceItem.setDiscountTypeId(rs.getLong(31));
				orderDeviceItem.setServicePacketTypeId(rs.getLong(32));
				orderDeviceItem.setDiscount(rs.getDouble(33));
				orderDeviceItem.setNds(rs.getDouble(34));
				orderDeviceItem.setBillTypeId(rs.getLong(35));
				orderDeviceItem.setNewAbonentId(rs.getLong(36));
				orderDeviceItem.setServiceCountGroupId(rs.getLong(37));
				orderDeviceItem.setOldDepartment(rs.getString(38));
				orderDeviceItem.setNewDepartment(rs.getString(39));
				orderDeviceItem.setActionName(rs.getString(40));
				list.add(orderDeviceItem);
			}
			log.info("getting order device list done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get order device list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ORDER_DEVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public List<BOrderDeviceItem> getOrderDeviceList(String abonentGuid)
			throws BRequestException {
		long abonentId = -1;
		try {
			String[] guid = abonentGuid.split("-");
			abonentId = Long.valueOf(guid[1]);
			return getOrderDeviceList(abonentId);
		} catch (NumberFormatException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR,
					"both part of guid must be numeric", e);
		}
	}

	private static final String GET_ORDER_SERVICE_FOR_ABONENT_STMT = new StringBuffer()
			.append(
					"select a.id, a.name, sp.name packet, tt.name service, s.open_date, s.close_date ")
			.append(
					"from db.order_service s, db.action a, db.service_packet_type sp, db.tariff_type tt ")
			.append(
					"where a.id = s.action_id and sp.id = s.service_packet_type_id and tt.id = s.tariff_type_id ")
			.append(
					"and link_type_id in (select v.link_id from db.view_all_real v where v.ABONENT_ID = ? ")
			.append(
					"and device = ?) and not exists (select orderid from tech.orderstages os where os.orderid = s.id ")
			.append(
					"and os.directid in (select service_center_type_id from tech.service_center_type_back_comp ")
			.append("where direct_type_id = 4)) order by s.open_date ")
			.toString();

	public List<BOrderServiceItem> getOrderServiceList(long abonentId,
			String device) throws BRequestException {
		log.info("getting order service list for abonent: " + abonentId
				+ "; device = " + device);
		List<BOrderServiceItem> resut = new ArrayList<BOrderServiceItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("GET_ORDER_SERVICE_FOR_ABONENT_STMT = "
					+ GET_ORDER_SERVICE_FOR_ABONENT_STMT);
			pstmt = conn.prepareStatement(GET_ORDER_SERVICE_FOR_ABONENT_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setString(2, device);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BOrderServiceItem orderServiceItem = new BOrderServiceItem(rs
						.getLong(1), rs.getString(2), rs.getString(3), rs
						.getString(4), null, null);
				if (rs.getTimestamp(5) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(5));
					orderServiceItem.setOpenDate(calendar);
				}
				if (rs.getTimestamp(6) != null) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getTimestamp(6));
					orderServiceItem.setCloseDate(calendar);
				}
				resut.add(orderServiceItem);
			}
			return resut;
		} catch (SQLException e) {
			log.error("Could not get order service list", e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ORDER_SERVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public List<BOrderServiceItem> getOrderServiceList(String abonentGuid,
			String device) throws BRequestException {
		long abonentId = -1;
		try {
			String[] guid = abonentGuid.split("-");
			abonentId = Long.valueOf(guid[1]);
			return getOrderServiceList(abonentId, device);
		} catch (NumberFormatException e) {
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR,
					"both part of guid must be numeric", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.request.IBRequest#getDeviceGroupId(java.lang.String)
	 */

	private static final String GET_GEVICE_GROUP_ID_GY_DBLINK_STMT = new StringBuffer()
			.append(
					"select t.device_group_id from un.dblink_device_group t where lower(t.db_link) = ?")
			.toString();

	public long getMegalineDeviceGroupId(String dbLinkName)
			throws BRequestException {
		log.info("Geting device group id, dbLinkName = " + dbLinkName + "...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			dbLinkName = dbLinkName.toLowerCase();
			conn = dataSource.getConnection();
			log.info("GET_GEVICE_GROUP_ID_GY_DBLINK_STMT = "
					+ GET_GEVICE_GROUP_ID_GY_DBLINK_STMT);
			pstmt = conn.prepareStatement(GET_GEVICE_GROUP_ID_GY_DBLINK_STMT);
			pstmt.setString(1, dbLinkName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getLong(1);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ ".device-group-not-defined-for-dblink",
					"There are no geviceGroupId defined for dbLink = "
							+ dbLinkName);
		} catch (SQLException e) {
			log.error("Could not get device group id, dbLinkName = "
					+ dbLinkName, e);
			throw new BRequestException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (NullPointerException npe) {
			log.error("Could not get device group id, dbLinkName = "
					+ dbLinkName, npe);
			throw new BRequestException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REQUEST_ERROR,
					"dbLinkName could not be null");
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void initialize() throws Exception {
		actionId = Integer.valueOf(getConstValue(actionIdConstName));
		payServicePacketTypeId = getPayServicePacketTypeId(actionId);
		BRegistrationInfo13 operatorInfo13 = registration.registerOperator(
				operatorName, operatorPassword);
		operatorId = operatorInfo13.getAbonentId();
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorPassword() {
		return operatorPassword;
	}

	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}

	public String getOrderDestination() {
		return orderDestination;
	}

	public void setOrderDestination(String orderDestination) {
		this.orderDestination = orderDestination;
	}

	public IBRegistration getRegistration() {
		return registration;
	}

	public void setRegistration(IBRegistration registration) {
		this.registration = registration;
	}

	public String getActionIdConstName() {
		return actionIdConstName;
	}

	public void setActionIdConstName(String actionIdConstName) {
		this.actionIdConstName = actionIdConstName;
	}

	public IBAbonent getAbonent() {
		return abonent;
	}

	public void setAbonent(IBAbonent abonent) {
		this.abonent = abonent;
	}

	public IBUtil getUtil() {
		return util;
	}

	public void setUtil(IBUtil util) {
		this.util = util;
	}

}