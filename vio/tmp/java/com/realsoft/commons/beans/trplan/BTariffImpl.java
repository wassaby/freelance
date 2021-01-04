/*
 * Created on 01.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTariffImpl.java,v 1.2 2016/04/15 10:37:28 dauren_home Exp $
 */
package com.realsoft.commons.beans.trplan;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BTariffImpl implements IBTariff {

	private static Logger log = Logger.getLogger(BTariffImpl.class);

	private BasicDataSource dataSource = null;

	public BTariffImpl() {
		super();
	}

	private static final String TARIFF_LIST_STMT = new StringBuffer()
			.append(
					"select pt.id, pt.name from un.abonent_type_packet_type atpt, tr.packet_type pt ")
			.append("where atpt.packet_type_id = pt.id and pt.is_active = 1 ")
			.append(
					"and atpt.abonent_type_id = (select abonent_type_id from db.abonent a, ")
			.append(
					"db.link_type lt, db.account_usr ac where a.id = lt.abonent_id and lt.id = ")
			.append(
					"ac.link_type_id and ac.id = ?) and pt.id not in (select ptp.packet_type_id ")
			.append(
					"from un.packet_type_params ptp where ptp.change_packet_type = 1)")
			.toString();

	public List getTariffList(long accountId) throws BTariffException {
		List<BTariffItem> result = new ArrayList<BTariffItem>();

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting tariff plan list accountId = " + accountId
					+ " ...");
			conn = dataSource.getConnection();
			log.debug("TARIFF_LIST_STMT = " + TARIFF_LIST_STMT);
			pstmt = conn.prepareStatement(TARIFF_LIST_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BTariffItem(rs.getLong(1), rs.getString(2)));
			}
			log.debug("Getting tariff plan list done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get tariff plan list", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get tariff plan list", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String TARIFF_LIST_VIEW_STMT = new StringBuffer()
			.append("select t.id, ")
			.append("t.name, ")
			.append("t.tariff_date, ")
			.append("t.tariff ")
			.append("from db.view_tariff t ")
			.append("where tax_type_id  in (999,3,5) ")
			.append("and service_packet_type_id = ( ")
			.append("select distinct sp.service_packet_type_id from  ")
			.append(
					"db.action act, db.service_packet sp, db.service_packet_type spt ")
			.append(
					"where act.id = sp.action_id and sp.service_packet_type_id=spt.id ")
			.append("and spt.provider_id = ? and act.id = 5 ").append(
					"and act.is_tariffed = 'T' ").append(") ").append(
					"and abonent_group_id = ? ").append(
					"and area_id in ( ?, ?, ?, ?) ").append(
					"and device_group_id in ( ?, 0) ").append(
					"and connect_type_id in ( ?, 0) ").append("order by name")
			.toString();

	public List getTariffList(long providerId, long abonentGroupId,
			long areaId, long areaIdS, long areaIdR, long areaIdT,
			long deviceGroupId, long connectTypeId) throws BTariffException {
		List<BTariffItem> result = new ArrayList<BTariffItem>();

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting tariff plan list providerId = " + providerId
					+ ", abonentGroupId = " + abonentGroupId + ", areaId = "
					+ areaId + ", areaIdS = " + areaIdS + ", areaIdR = "
					+ areaIdR + ", areaIdT = " + areaIdT + ", deviceGroupId = "
					+ deviceGroupId + ", connectTypeId = " + connectTypeId
					+ " ...");
			conn = dataSource.getConnection();
			log.debug("TARIFF_LIST_VIEW_STMT = " + TARIFF_LIST_VIEW_STMT);
			pstmt = conn.prepareStatement(TARIFF_LIST_VIEW_STMT);
			pstmt.setLong(1, providerId);
			pstmt.setLong(2, abonentGroupId);
			pstmt.setLong(3, areaId);
			pstmt.setLong(4, areaIdS);
			pstmt.setLong(5, areaIdR);
			pstmt.setLong(6, areaIdT);
			pstmt.setLong(7, deviceGroupId);
			pstmt.setLong(8, connectTypeId);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BTariffItem(rs.getLong(1), rs.getString(2), rs
						.getTimestamp(3), rs.getDouble(4)));
			}
			log.debug("Getting tariff plan list view done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get tariff plan list view", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get tariff plan list view", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String TARIFF_ITEM_VIEW_STMT = new StringBuffer().append(
			"select t.id, ").append("t.name, ").append("t.tariff_date, ")
			.append("t.service_packet_type_id, ").append(
					"t.service_count_group_id, ").append("t.tariff ").append(
					"from db.view_tariff t ").append("where t.id = ? ").append(
					"order by name").toString();

	public BTariffItem getTariffById(long tariffId) throws BTariffException {
		log.debug("getting tariff by ID tariffId = " + tariffId);
		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.debug("TARIFF_ITEM_VIEW_STMT = " + TARIFF_ITEM_VIEW_STMT);
			pstmt = conn.prepareStatement(TARIFF_ITEM_VIEW_STMT);
			pstmt.setLong(1, tariffId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("getting tariff by ID done");
				return new BTariffItem(rs.getLong(1), rs.getString(2), rs
						.getTimestamp(3), rs.getLong(4), rs.getLong(5), rs
						.getDouble(6));
			}
			throw new BTariffException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR, "No such tariff found");
		} catch (SQLException e) {
			log.error("Getting tariff plan item done", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get tariff plan item", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CURRENT_TARIFF_STMT = new StringBuffer()
			.append("select pt.id, ").append("pt.name ").append(
					"from tr.packet_type pt, ").append("db.link        l, ")
			.append("db.account_usr ac ").append(
					"where l.packet_type_id = pt.id ").append(
					"and ac.link_type_id = l.link_type_id ").append(
					"and sysdate >= l.date_from ").append(
					"and sysdate < nvl(l.date_to, sysdate + 1) ").append(
					"and ac.id = ?").toString();

	public BTariffItem getCurrentTariff(long accountId) throws BTariffException {

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting current tariff plan ...");
			conn = dataSource.getConnection();
			log.debug("CURRENT_TARIFF_STMT = " + CURRENT_TARIFF_STMT);
			pstmt = conn.prepareStatement(CURRENT_TARIFF_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			BTariffItem result = null;
			if (rs.next()) {
				result = new BTariffItem(rs.getLong(1), rs.getString(2));
			}
			log.debug("Getting current tariff plan done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get current tariff plan", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get current tariff plan", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String UPDATE_TARIFF_PLAN_PSTMT = "{ call un.pkg_order_account_packet.set_order_account_packet( ? , ? , ? ) }";

	public void setCurrentTariff(long accountId, long tariffId, Calendar date)
			throws BTariffException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.info("Editing tarif ...");
			log.debug("accountId = " + accountId + "; tariffId = " + tariffId
					+ "; date = " + date);
			conn = dataSource.getConnection();

			log.debug("UPDATE_TARIFF_PLAN_PSTMT = " + UPDATE_TARIFF_PLAN_PSTMT);
			cstmt = conn.prepareCall(UPDATE_TARIFF_PLAN_PSTMT);
			cstmt.setLong(1, accountId);
			cstmt.setLong(2, tariffId);
			cstmt.setDate(3, new Date(date.getTimeInMillis()));
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			log.debug("Editing tariff done");
		} catch (SQLException e) {
			log.error("Could not edit tariff", e);
			CommonOperations.rollbackConnection(conn);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not edit tariff", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String CURRENT_NEW_TARIFF = new StringBuffer().append(
			"select pt.id, ").append("pt.name, ").append("oap.system_date ")
			.append("from un.order_account_packet oap, ").append(
					"tr.packet_type pt ").append("where oap.done = 0 ").append(
					"and pt.id = oap.packet_type_id ").append(
					"and oap.account_usr_id = ?").toString();

	public BTariffItem getCurrentNewTariff(long accountId)
			throws BTariffException {
		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting current new tariff plan accountId = "
					+ accountId + " ...");
			conn = dataSource.getConnection();
			log.debug("CURRENT_NEW_TARIFF = " + CURRENT_NEW_TARIFF);
			pstmt = conn.prepareStatement(CURRENT_NEW_TARIFF);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			BTariffItem result = null;
			if (rs.next()) {
				result = new BTariffItem(rs.getLong(1), rs.getString(2), rs
						.getTimestamp(3));
			}
			log.debug("Getting current new tariff plan done");
			return result;
		} catch (SQLException e) {
			log.error("Could not get current new tariff plan", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get current new tariff plan", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String GET_ABONENT_TARIFF_STMT = new StringBuffer().append(
			"select pt.id,").append("pt.name ").append(
			"from tr.packet_type pt,").append("db.link        l, ").append(
			"db.device      d , db.link_type lt ").append(
			"where l.packet_type_id = pt.id ").append(
			"and l.link_type_id = lt.id ").append("and l.device_id = d.id ")
			.append("and sysdate < nvl(l.date_to, sysdate + 1) ").append(
					"and sysdate >= l.date_from ").append(
					"and lt.abonent_id = ? ").append("and d.device = ? ")
			.toString();

	public BTariffItem getTariff(long abonentId, String device)
			throws BTariffException {

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting tariff plan, abonentId= " + abonentId
					+ "; device=" + device);
			conn = dataSource.getConnection();
			log.debug("GET_ABONENT_TARIFF_STMT = " + GET_ABONENT_TARIFF_STMT);
			pstmt = conn.prepareStatement(GET_ABONENT_TARIFF_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setString(2, device);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				log.debug("Getting tariff plan succesfuly done");
				return new BTariffItem(rs.getLong(1), rs.getString(2));
			}
			throw new BTariffException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR,

					"Tarif for abonent " + abonentId + " not found");

		} catch (SQLException e) {
			log.error("Could not get tariff plan", e);
			throw new BTariffException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.TARIFF_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get tariff ", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
