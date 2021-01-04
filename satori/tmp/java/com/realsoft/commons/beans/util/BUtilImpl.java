/*
 * Created on 11.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BUtilImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.abonent.BAreaInfo;
import com.realsoft.commons.beans.connections.BConnectorException;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

public class BUtilImpl implements IBUtil {

	private static Logger log = Logger.getLogger(BUtilImpl.class);

	private BasicDataSource dataSource = null;

	private IFormatter formatter = null;

	public BUtilImpl() {
		super();
	}

	private static String DEVICE_GROUP_ID_STMT = "select const_value from db.all_const where name = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getDeviceGroupId(java.lang.String)
	 */
	public long getDeviceGroupId(String deviceGroupName) throws BUtilException {
		log.info("getting device group ID deviceGroupName = " + deviceGroupName
				+ " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("DEVICE_GROUP_ID_STMT = " + DEVICE_GROUP_ID_STMT);
			pstmt = conn.prepareStatement(DEVICE_GROUP_ID_STMT);
			pstmt.setString(1, deviceGroupName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				log.info("device group ID done");
				return rs.getLong(1);
			}
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_GROUP_ID_NOT_FOUD_ERROR,
					"No such device group " + deviceGroupName + " found");
		} catch (SQLException e) {
			log.error("Could not get device group id", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_GROUP_ID_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String DEVICE_GROUP_LIST_STMT = new StringBuffer().append(
			"select t.id, t.Name, t.provider_id from device_group t ")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getDeviceGroupList(long,
	 *      long)
	 */
	public List getDeviceGroupList(long townId, long abonentId)
			throws BUtilException {
		log.info("getting device groups ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(DEVICE_GROUP_LIST_STMT);
			rs = pstmt.executeQuery();
			List<BDeviceGroupItem> list = new ArrayList<BDeviceGroupItem>();
			while (rs.next()) {
				list.add(new BDeviceGroupItem(rs.getLong(1), rs.getString(2),
						rs.getLong(3)));
			}
			log.info("getting device groups done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get device groups list", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_GROUPS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String DEVICE_GROUP_ITEM_STMT = new StringBuffer()
			.append(
					"select t.id, t.Name, t.provider_id from device_group t where t.id=?")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getDeviceGroupById(long,
	 *      long)
	 */
	public BDeviceGroupItem getDeviceGroupById(long townId, long deviceGroupId)
			throws BUtilException {
		log.info("getting device group item townId = " + townId
				+ "; deviceGroupId = " + deviceGroupId + "...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			log.debug("DEVICE_GROUP_ITEM_STMT = " + DEVICE_GROUP_ITEM_STMT);
			pstmt = conn.prepareStatement(DEVICE_GROUP_ITEM_STMT);
			pstmt.setLong(1, deviceGroupId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				log.info("getting device groups done");
				return new BDeviceGroupItem(rs.getLong(1), rs.getString(2), rs
						.getLong(3));
			}
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_GROUPS_ERROR,
					"No such device group found " + deviceGroupId);
		} catch (SQLException e) {
			log.error("Could not get device groups list", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DEVICE_GROUPS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String NDS_LIST_STMT = new StringBuffer().append(
			"select id, name, nds from ").append("db.view_nds ").append(
			"where abonent_group_id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getNDSList(long)
	 */
	public List getNDSList(long abonentGroupId) throws BUtilException {
		Connection conn = null;

		List<BNDSItem> result = new ArrayList<BNDSItem>();

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting nds list abonentGroupId = " + abonentGroupId
					+ " ...");
			conn = dataSource.getConnection();
			log.debug("NDS_LIST_STMT = " + NDS_LIST_STMT);
			pstmt = conn.prepareStatement(NDS_LIST_STMT);
			pstmt.setLong(1, abonentGroupId);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BNDSItem(rs.getLong(1), rs.getString(2), rs
						.getDouble(3)));
			}
			log.debug("Getting nds list done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get nds list", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get nds list", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String NDS_ITEM_STMT = new StringBuffer().append(
			"select id, ").append("name, ").append("nds ").append(
			"from db.view_nds ").append("where id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getNDSItem(long)
	 */
	public BNDSItem getNDSItem(long ndsId) throws BUtilException {
		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting nds item ndsId = " + ndsId + " ...");
			conn = dataSource.getConnection();
			log.debug("NDS_ITEM_STMT = " + NDS_ITEM_STMT);
			pstmt = conn.prepareStatement(NDS_ITEM_STMT);
			pstmt.setLong(1, ndsId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting nds item done");
				return new BNDSItem(rs.getLong(1), rs.getString(2), rs
						.getDouble(3));
			}
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR, "No such nds item");
		} catch (SQLException e) {
			log.error("Could not get nds item", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get nds item", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_KEY_WORD = "{ ? = call db.get_keyword( ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getKeyWord(long)
	 */
	public String getKeyWord(long orderId) throws BUtilException {
		log.info("getting key word ...");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			log.info("Utils connection.hashCode= "
					+ String.valueOf(conn.hashCode()));

			CallableStatement cstmt = null;

			String keyWord = null;
			try {
				cstmt = conn.prepareCall(GET_KEY_WORD);
				cstmt.setLong(2, orderId);
				cstmt.registerOutParameter(1, Types.VARCHAR);
				cstmt.execute();
				keyWord = cstmt.getString(1);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
			log.info("getting key word done");
			return keyWord;
		} catch (SQLException e) {
			log.error("Could not get key word", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_DB_SYSTIME = "select sysdate from dual";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getDBSysDate()
	 */
	public Date getDBSysDate() throws BUtilException {
		log.info("getting sysdate ...");
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(GET_DB_SYSTIME);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					log.info("getting sysdate done, sysdate = "
							+ rs.getTimestamp(1));
					return rs.getTimestamp(1);
				}
				log.error("Could not get sysdate from database");
				throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.UTIL_ERROR,
						"Could not get sysdate from database");
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not get sysdate", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		}
	}

	private static String AREA_BY_TOWN = "{ call db.get_area_by_town( ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getAreaByTown(long, long)
	 */
	public BAreaInfo getAreaByTown(long townId, long providerId)
			throws BUtilException {
		log.info("getting area by town townId=" + townId + " provideId="
				+ providerId + " ...");
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = dataSource.getConnection();
			log.debug("AREA_BY_TOWN = " + AREA_BY_TOWN);
			cstmt = conn.prepareCall(AREA_BY_TOWN);
			cstmt.setLong(1, townId);
			cstmt.setLong(2, providerId);
			cstmt.registerOutParameter(3, Types.NUMERIC);
			cstmt.registerOutParameter(4, Types.NUMERIC);
			cstmt.registerOutParameter(5, Types.NUMERIC);
			cstmt.registerOutParameter(6, Types.NUMERIC);
			cstmt.execute();
			log.info("getting area by town done");
			return new BAreaInfo(cstmt.getLong(3), cstmt.getLong(4), cstmt
					.getLong(5), cstmt.getLong(6));
		} catch (SQLException e) {
			log.error("Could not get area info", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String LINK_TYPE_ID = new StringBuffer().append(
			"select au.link_type_id ").append("from db.account_usr au ")
			.append("where au.id = ?").toString();

	private static final String SERVICE_LIST_STMT = new StringBuffer().append(
			"select srv.id, srv.name ").append(
			"from db.view_service_real vsr, ").append("tr.service srv ")
			.append("where vsr.link_type_id = ? ").append(
					"and srv.id = vsr.service_id ").append("order by srv.name")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getServiceList(long)
	 */
	public List<BServiceListItem> getServiceList(long accountId)
			throws BUtilException {
		Connection conn = null;
		PreparedStatement cstmt = null;
		try {
			log.debug("Getting service list ...");
			conn = dataSource.getConnection();
			cstmt = conn.prepareStatement(LINK_TYPE_ID);
			cstmt.setLong(1, accountId);
			ResultSet rs = cstmt.executeQuery();
			long linkTypeId = 0;
			if (rs.next())
				linkTypeId = rs.getLong(1);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeStatement(cstmt);
			cstmt = conn.prepareStatement(SERVICE_LIST_STMT);
			cstmt.setLong(1, linkTypeId);
			rs = cstmt.executeQuery();
			List<BServiceListItem> serviceList = new ArrayList<BServiceListItem>();
			while (rs.next()) {
				serviceList.add(new BServiceListItem(rs.getLong(1), rs
						.getString(2)));
			}
			log.debug("Getting service list done");
			return serviceList;
		} catch (SQLException e) {
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SERVICE_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get service list in implementation", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String REPORT_DATE_STMT = new StringBuilder()
			.append("select rd.id, rd.name ")
			.append(
					"from db.report_date rd where rd.is_active_web='T' and rd.from_date <= sysdate order by id desc")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getReportDates()
	 */
	public List<BReportDateItem> getReportDates() throws BUtilException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting report date list");
			conn = dataSource.getConnection();
			log.debug(REPORT_DATE_STMT);
			pstmt = conn.prepareStatement(REPORT_DATE_STMT);
			ResultSet rs = pstmt.executeQuery();
			List<BReportDateItem> reportDateList = new ArrayList<BReportDateItem>();
			while (rs.next()) {
				reportDateList.add(new BReportDateItem(rs.getLong(1), rs
						.getString(2)));
			}
			return reportDateList;
		} catch (SQLException e) {
			log.error("Could not get report date list", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get report date list", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CURR_REPORT_DATE_STMT = new StringBuilder().append(
			"select rd.id ").append("from db.report_date rd ").append(
			"where rd.from_date <= sysdate ")
			.append("and rd.to_date > sysdate").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getCurrentReportDate()
	 */
	public Long getCurrentReportDate() throws BUtilException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting current report date");
			conn = dataSource.getConnection();
			log.debug(CURR_REPORT_DATE_STMT);
			pstmt = conn.prepareStatement(CURR_REPORT_DATE_STMT);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting current report date done");
				return new Long(rs.getLong(1));
			}
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REPORT_DATE_ERROR,
					"No current report date");
		} catch (SQLException e) {
			log.error("Could not get current report date", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get current report date", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String REGISTRATION_DATE_STMT = new StringBuilder()
			.append("select max(ct.open_date) ")
			.append("from db.contract ct ")
			.append("where ct.abonent_id = ? ")
			.append("and ct.provider_id = ? ")
			.append(
					"and sysdate between ct.open_date and nvl(ct.close_date, sysdate)")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getRegistrationDate(long,
	 *      long)
	 */
	public Date getRegistrationDate(long abonentId, long providerId)
			throws BConnectorException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting registration date ... for abonent = "
					+ abonentId + "; providerId = " + providerId);
			conn = dataSource.getConnection();
			log.debug(REGISTRATION_DATE_STMT);
			pstmt = conn.prepareStatement(REGISTRATION_DATE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, providerId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting registration date done");
				log.debug("Registration date = " + rs.getTimestamp(1));
				return rs.getTimestamp(1);
			}
		} catch (SQLException e) {
			log.error("Could not get registration date for abonent "
					+ abonentId, e);
			throw new BConnectorException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get registration date for abonent " + abonentId,
					e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BConnectorException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.DATE_ERROR,
				"No registration date for abonent " + abonentId);
	}

	private String GET_KASPERSKY_KEY_STMT = new StringBuffer().append(
			"{? = call un.pkg_kaspersky_key.get_key( ? ) }").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getKasperskyKey(long)
	 */
	public String getKasperskyKey(long radiusUserId) throws BUtilException {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			cstmt = conn.prepareCall(GET_KASPERSKY_KEY_STMT);

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setLong(2, radiusUserId);
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			return cstmt.getString(1);
		} catch (SQLException se) {
			log.error("Could not  get KasperskyKey ", se);
			CommonOperations.rollbackConnection(conn);
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					StringUtils.getOraErrorCode(se.getMessage())[0], se
							.getMessage(), se);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private String GET_JMAP_KEY_STMT = new StringBuffer().append(
			"{? = call un.pkg_jmap_key.get_key( ? ) }").toString();

	public String getJmapKey(long radiusUserId) throws BUtilException {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			log.debug("GET_JMAP_KEY_STMT = " + GET_JMAP_KEY_STMT);
			log.debug("radiusUserId = " + radiusUserId);

			cstmt = conn.prepareCall(GET_JMAP_KEY_STMT);

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setLong(2, radiusUserId);
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			return cstmt.getString(1);
		} catch (SQLException se) {
			log.error("Could not  get JmapKey ", se);
			CommonOperations.rollbackConnection(conn);
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					StringUtils.getOraErrorCode(se.getMessage())[0], se
							.getMessage(), se);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private String GET_DRWEB_KEY_STMT = new StringBuffer().append(
			"{? = call un.pkg_drweb.get_key( ? ) }").toString();

	public String getDrWebKey(long radiusUserId) throws BUtilException {
		log.debug("GET_DRWEB_KEY_STMT = " + GET_DRWEB_KEY_STMT);
		log.debug("radiusUserId = " + radiusUserId);

		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			cstmt = conn.prepareCall(GET_DRWEB_KEY_STMT);

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setLong(2, radiusUserId);
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			return cstmt.getString(1);
		} catch (SQLException se) {
			log.error("Could not  get DrWebKey ", se);
			CommonOperations.rollbackConnection(conn);
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					StringUtils.getOraErrorCode(se.getMessage())[0], se
							.getMessage(), se);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHANGE_DEVICE_PROPERTY_CSTMT = "{call db.change_device_properties( ?, ?, ?, ?, ?, ?, ?, ?)}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#changeDeviceProperty(long,
	 *      long, java.util.Calendar, java.util.Calendar, java.lang.String, int,
	 *      long, long)
	 */
	public void changeDeviceProperty(long deviceId, long propertyId,
			Calendar dateFrom, Calendar dateTo, String propertyValue,
			int abnDevice, Long valueId, long operatorId) throws BUtilException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.info("changing device property deviceId = " + deviceId
					+ "; dateFrom = " + formatter.format(dateFrom)
					+ "; dateTo = " + formatter.format(dateTo)
					+ "; propertyValue = " + propertyValue + "; abnDevice = "
					+ abnDevice + "; valueId = " + valueId + "; operatorId = "
					+ operatorId + " ...");
			conn = dataSource.getConnection();
			log.debug("CHANGE_DEVICE_PROPERTY_CSTMT = "
					+ CHANGE_DEVICE_PROPERTY_CSTMT);

			cstmt = conn.prepareCall(CHANGE_DEVICE_PROPERTY_CSTMT);
			cstmt.setLong(1, deviceId);
			cstmt.setLong(2, propertyId);
			if (dateFrom != null)
				cstmt.setTimestamp(3, new java.sql.Timestamp(dateFrom.getTime()
						.getTime()));
			else
				cstmt.setTimestamp(3, new java.sql.Timestamp(getDBSysDate()
						.getTime()));
			cstmt.setTimestamp(4, new java.sql.Timestamp(dateTo.getTime()
					.getTime()));
			cstmt.setString(5, propertyValue);
			cstmt.setInt(6, abnDevice);
			if (valueId == null)
				cstmt.setNull(7, Types.NUMERIC);
			else
				cstmt.setLong(7, valueId);
			cstmt.setLong(8, operatorId);
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			log.info("changing device property done");
		} catch (UtilsException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not changing device property", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not changing device property", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CONST_VALUE_STMT = "select ac.const_value from db.all_const ac where ac.name=?";

	public Object getConstValue(String constName) throws BUtilException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return getConstValue(constName, conn);
		} catch (SQLException e) {
			log.error("Could not changing device property", e);
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR + ".const-value",
					"Could not get constant value");
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private Object getConstValue(String constName, Connection conn)
			throws BUtilException {
		PreparedStatement pstmt = null;
		try {
			log.info("constValue = " + constName + " ...");
			log.debug("CONST_VALUE_STMT = " + CONST_VALUE_STMT);

			pstmt = conn.prepareStatement(CONST_VALUE_STMT);
			pstmt.setString(1, constName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			}
		} catch (SQLException e) {
			log.error("Could not changing device property", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
		throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.UTIL_ERROR + ".const-value",
				"Could not get constant value");

	}

	private static final String GET_CUT_COUNT_FROM_BEGIN_STMT = "select data_length "
			+ "from db.source_town where rowid = (select db.tax1.get_source_town( ? , 1) from dual)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getShortDevice(java.lang.String)
	 */

	public String getShortDevice(String longDevice) throws BUtilException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return getShortDevice(longDevice, conn);
		} catch (SQLException e) {
			log.error("Could not get payServicePacketTypeId", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					"request.error.get-service-packet-type-id"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private String getShortDevice(String longDevice, Connection conn)
			throws BUtilException {
		if (longDevice == null)
			return null;
		if (longDevice.length() < 10)
			return longDevice;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int beginCutCount = 0;
		try {
			log.debug("GET_CUT_COUNT_FROM_BEGIN_STMT = "
					+ GET_CUT_COUNT_FROM_BEGIN_STMT);
			pstmt = conn.prepareStatement(GET_CUT_COUNT_FROM_BEGIN_STMT);
			pstmt.setString(1, longDevice);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				beginCutCount = rs.getInt(1);
				log.debug("beginCutCount = " + beginCutCount);
			} else
				throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
						"request.error.get-cut-from-begin-characters.count",
						"Wrong town-code in device " + longDevice);
		} catch (SQLException e) {
			log.error("Could not get payServicePacketTypeId", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					"request.error.get-service-packet-type-id"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
		return longDevice.substring(beginCutCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getRegionId()
	 */

	private static final String GET_REGION_ID_STMT = "select ID from db.crm_server";

	public String getRegionId() throws BUtilException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int regionId = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(GET_REGION_ID_STMT);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				regionId = rs.getInt(1);
			} else
				throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
						"util.error.no-region-id",
						"RegionId is not defined in Bittl");
			if (rs.next())
				throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
						"util.error.too-much-region-id.found",
						"There are more than one regionId in Bittl");
		} catch (SQLException e) {
			log.error("Could not get regionId", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					"util.error.get-region-id"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
		log.debug("Getting regionId successfully done, returning regionId = "
				+ regionId);
		Formatter formatter = new Formatter().format("%03d", regionId);
		return formatter.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getConstValue(java.lang.String,
	 *      java.lang.String)
	 */
	public Object getConstValue(String code, String constName)
			throws BUtilException {
		return getConstValue(constName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#getConstValue(java.lang.Long,
	 *      java.lang.String)
	 */
	public Object getConstValue(Long townId, String constName)
			throws BUtilException {
		return getConstValue(constName);
	}

	private static final String GET_IS_AVAILABLE_SERVICE_PACKETS = new StringBuffer()
			.append("select count(*) amount from db.view_device_service vds ")
			.append("where service_packet_type_id in ( %s ) ")
			.append(
					"and vds.link_type_id in (select link_id from db.view_all_real ")
			.append(
					"where device = ? and dev_town_id = ? and device_group_id = ?)")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.util.IBUtil#isAvailableServicePackets(java.lang.String,
	 *      java.lang.String, long, long)
	 */
	public boolean isAvailableServices(String device,
			String servicePacketTypeIdsConstName, long deviceGroupId,
			long deviceTownId) throws BUtilException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String servicePacketTypeIds = getConstValue(
					servicePacketTypeIdsConstName, conn).toString();
			String stmt = formatter.format(GET_IS_AVAILABLE_SERVICE_PACKETS,
					servicePacketTypeIds);
			log.debug("GET_IS_AVAILABLE_SERVICE_PACKETS = " + stmt);
			String dev = getShortDevice(device, conn);
			pstmt = conn.prepareStatement(stmt);
			pstmt.setString(1, dev);
			pstmt.setLong(2, deviceTownId);
			pstmt.setLong(3, deviceGroupId);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getLong(1) > 0;
		} catch (SQLException e) {
			log.error("Could check is available services", e);
			throw new BUtilException(
					CommonsBeansConstants.ERR_SYSTEM,
					"utils.error.check-available-services"
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (UtilsException e) {
			log.error("Could check is available services", e);
			throw new BUtilException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.UTIL_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}