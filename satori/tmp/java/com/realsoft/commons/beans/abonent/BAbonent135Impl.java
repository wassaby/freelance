/*
 * Created on 22.11.2007 18:46:03
 *
 * Realsoft Ltd.
 * <Last Name First Name>
 * $Id: BAbonent135Impl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

public class BAbonent135Impl implements IBabonent135 {

	private static final Logger log = Logger.getLogger(BAbonent135Impl.class);

	private BasicDataSource dataSource = null;

	private IFormatter formatter = null;

	private static final String GET_ABONENT_INFO135_BY_DEVICE = new StringBuffer()
			.append(
					"select distinct a.id, a.name, t.id, t.name, a.alien_abonent_id ")
			.append("alien_abonent_id, abt.id,")
			.append(
					"abt.name, case (select count(1) block_count from db.blocks blc where a.id = blc.abonent_id and ")
			.append(
					"lt.id = blc.link_type_id and sysdate >= blc.date_from and sysdate < nvl(blc.date_to, sysdate + 1 )) ")
			.append(
					"when 0 then 'false' else 'true' end blocked from db.abonent a, db.town t, db.link_type lt, db.device d, ")
			.append(
					"db.abonent_type abt where a.town_id = t.id and lt.abonent_id = a.id and lt.device_id = d.id ")
			.append(
					"and abt.id = a.abonent_type_id and t.unique_code = ? and d.device like ('%%%s%%')")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentInfo135(java.lang.String,
	 *      java.lang.String)
	 */
	public List<BAbonentInfo135> getAbonentInfo(String townCode, String device)
			throws BAbonentException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BAbonentInfo135> result = new ArrayList<BAbonentInfo135>();
		try {
			log.debug("Getting abonentInfo135 by townCode = " + townCode
					+ " & device = " + device + " ...");
			conn = dataSource.getConnection();
			String statement = formatter.format(GET_ABONENT_INFO135_BY_DEVICE,
					device);
			log.debug("GET_ABONENT_INFO135_BY_DEVICE = " + statement);
			pstmt = conn.prepareStatement(statement);
			pstmt.setString(1, townCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				log.debug("Getting abonentInfo135 by townCode = " + townCode
						+ " & device = " + device + " done");
				BAbonentInfo135 info135 = new BAbonentInfo135(
						rs.getLong(1),
						rs.getString(2),
						rs.getLong(3),
						rs.getString(4),
						rs.getLong(5) == CommonsBeansConstants.ALIEN_ABONENT_ID_NOT_DEFINED ? null
								: rs.getLong(5));
				info135.setAbonentTypeId(rs.getLong(6));
				info135.setAbonentTypeName(rs.getString(7));
				info135.setBlocked(rs.getString(8).equalsIgnoreCase("true"));
				info135.setDeviceList(getAbonentDevices135(info135
						.getAbonentId(), conn));
				result.add(info135);
			}
			if (result.size() == 0)
				throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.ABONENT_ERROR
								+ ".abonent-not-found",
						"No such abonent for townCode = " + townCode
								+ " & device = " + device);
			return result;
		} catch (SQLException e) {
			log.error("Could not get abonent info by townCode = " + townCode
					+ " & device = " + device, e);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR + "."
							+ e.getErrorCode(),
					"Could not get abonent info by townCode = " + townCode
							+ " & device = " + device, e);
		} catch (Exception e) {
			log.error("Could not get abonent info by townCode = " + townCode
					+ " & device = " + device, e);
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.abonent.getAbonentInfo.error",
					"Could not get abonent info by townCode = " + townCode
							+ " & device = " + device, e);
		} finally {
			log.debug("Closing statement...");
			CommonOperations.closeStatement(pstmt);
			log.debug("Closing connection...");
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ALL_ABONENT_DEVICES_135_STMT = new StringBuffer()
			.append(
					"select d.id, d.device, d.device_group_id, d.connect_type_id, d.town_id, dg.name from db.device d, db.link_type lt, db.link l, db.device_group dg ")
			.append(
					"where d.id = l.device_id and lt.id = l.link_type_id and dg.id = d.device_group_id and sysdate between l.date_from and nvl(l.date_to, sysdate + 1) ")
			.append("and lt.abonent_id = ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBAbonent#getAbonentDevices135(long)
	 */

	public List<BAbonentDeviceItem> getAbonentDevices(long abonentId)
			throws BAbonentException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return getAbonentDevices135(abonentId, conn);
		} catch (SQLException e) {
			log.error("Could not get abonent devices for 135", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	private List<BAbonentDeviceItem> getAbonentDevices135(long abonentId,
			Connection conn) throws BAbonentException, SQLException {
		log.debug("Trying to get all abonent devices, abonentId = " + abonentId
				+ "...");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			log.debug("GET_ALL_ABONENT_DEVICES_135_STMT = "
					+ GET_ALL_ABONENT_DEVICES_135_STMT);
			pstmt = conn.prepareStatement(GET_ALL_ABONENT_DEVICES_135_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			List<BAbonentDeviceItem> result = new ArrayList<BAbonentDeviceItem>();
			while (rs.next()) {
				BAbonentDeviceItem abonentDeviceItem = new BAbonentDeviceItem();
				abonentDeviceItem.setId(rs.getLong(1));
				abonentDeviceItem.setDevice(rs.getString(2));
				abonentDeviceItem.setDeviceGroupId(rs.getLong(3));
				abonentDeviceItem.setConnectTypeId(rs.getLong(4));
				abonentDeviceItem.setTownId(rs.getLong(5));
				abonentDeviceItem.setDeviceGroupName(rs.getString(6));
				result.add(abonentDeviceItem);
			}
			log.debug("getting abonent device list for 135 successfully done");
			return result;
		} finally {
			CommonOperations.closeStatement(pstmt);
		}
	}

	private static String GET_ABONENT_CHARGE_135_STMT = new StringBuffer()
			.append(
					"select nvl(sum(s.debit + s.change_debit + s.change_in_debit), 0) saldo ")
			.append("from db.saldo s, db.report_date rd ").append(
					"where rd.id = s.report_date_id ").append(
					"and trunc(sysdate, 'dd') >= rd.from_date ").append(
					"and trunc(sysdate, 'dd') <= rd.to_date ").append(
					"and s.abonent_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBabonent135#getAbonentCharge135(long)
	 */

	public double getAbonentCharge(long abonentId) throws BAbonentException {

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting abonent charge, abonentId= " + abonentId);
			conn = dataSource.getConnection();
			log.debug("GET_ABONENT_CHARGE_STMT = "
					+ GET_ABONENT_CHARGE_135_STMT);
			pstmt = conn.prepareStatement(GET_ABONENT_CHARGE_135_STMT);
			pstmt.setLong(1, abonentId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				log.debug("Getting abonent charge succesfuly done");
				return rs.getDouble(1);
			}
			throw new BAbonentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR, "Charge for abonent "
							+ abonentId + " not found");

		} catch (SQLException e) {
			log.error("Could not get charge for abonent", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get charge for abonent ", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ABONENT_STATUS_CSTMT = "{? = call un.pkg_sanatel.get_abonent_status(?, ?)}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.abonent.IBabonent135#isAbonent135Active(long,
	 *      long)
	 */
	public boolean isAbonentActive(long abonentId, long deviceId)
			throws BAbonentException {
		log.info("checking abonent status for phone " + deviceId
				+ "; abonentId = " + abonentId);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			log.info("GET_ABONENT_STATUS_CSTMT = " + GET_ABONENT_STATUS_CSTMT);
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(GET_ABONENT_STATUS_CSTMT);
			cstmt.setLong(2, abonentId);
			cstmt.setLong(3, deviceId);
			cstmt.registerOutParameter(1, Types.NUMERIC);
			cstmt.execute();
			log.info("checking abonent status for phone " + deviceId
					+ "; abonentId = " + abonentId);
			return cstmt.getLong(1) > 0;
		} catch (SQLException e) {
			log.error("Could not get abonent status", e);
			throw new BAbonentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get abonent status", e);
		} finally {
			CommonOperations.closeConnection(conn);
			CommonOperations.closeStatement(cstmt);
		}
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

}
