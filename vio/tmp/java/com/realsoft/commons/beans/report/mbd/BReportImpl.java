package com.realsoft.commons.beans.report.mbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.report.abonent.BReportException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BReportImpl implements IBReport {

	private static Logger log = Logger.getLogger(BReportImpl.class);

	private BasicDataSource dataSource = null;

	private static String MONTH_BY_SERVICES_STMT = new StringBuffer().append(
			"select mbs.name, ").append("mbs.tech_summ, ").append("mbs.summ, ")
			.append("mbs.unit, ").append("mbs.debit, ").append("mbs.currency ")
			.append("from view_month_by_services mbs ").append(
					"where mbs.abonent_id = ? ").append(
					"and mbs.device_id = ? ").append(
					"and mbs.report_date_id = ?").toString();

	public BReportImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.mbd.IBReport#getMonthByServices(long,
	 *      long, long)
	 */
	public List getMonthByServices(long abonentId, long deviceId,
			long reportDate) throws BMonthByDayException {
		log.debug("Month by day started abonentId = " + abonentId
				+ "; deviceId = " + deviceId + "; reportDate = " + reportDate
				+ "...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting report month by services for abonent="
					+ abonentId + "; device=" + deviceId + "; reportDate="
					+ reportDate);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(MONTH_BY_SERVICES_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, deviceId);
			pstmt.setLong(3, reportDate);
			ResultSet rs = pstmt.executeQuery();
			List<BReportItem> monthByServicesList = new ArrayList<BReportItem>();
			while (rs.next()) {
				monthByServicesList.add(new BReportItem(rs.getString(1), rs
						.getDouble(2), rs.getDouble(3), rs.getString(4), rs
						.getDouble(5), rs.getString(6)));
			}
			log.debug("Getting report month by services done. size = "
					+ monthByServicesList.size());
			return monthByServicesList;
		} catch (SQLException e) {
			log.error("Could not get report month by services for abonent "
					+ abonentId + "; device " + deviceId, e);
			throw new BMonthByDayException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MONTH_BY_DAY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get report month by services for abonent "
							+ abonentId + "; device " + deviceId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String REPORT_ABONENT_FOR_DEVICE_PERIOD_STMT = new StringBuffer()
			.append("select afp.service_id, afp.service_date, ").append(
					"afp.name, ").append("afp.tech_volume, ").append(
					"afp.unit, ").append("afp.summ, ").append("afp.debit, ")
			.append(" afp.DETAIL_TYPE_NAME, afp.currency ").append(
					"from view_accounting_for_period afp ").append(
					"where afp.abonent_id = ? ")
			.append("and afp.device_id = ?").append(
					"and afp.report_date_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.mbd.IBReport#getReportForPeriod(long,
	 *      long, long)
	 */
	public List getReportForPeriod(long abonentId, long deviceId,
			long reportDate) throws BMonthByDayException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting report for period list form abonent="
					+ abonentId + "; device=" + deviceId + "; reportDate="
					+ reportDate);
			conn = dataSource.getConnection();
			log.info("REPORT_ABONENT_FOR_DEVICE_PERIOD_STMT = "
					+ REPORT_ABONENT_FOR_DEVICE_PERIOD_STMT);
			pstmt = conn
					.prepareStatement(REPORT_ABONENT_FOR_DEVICE_PERIOD_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, deviceId);
			pstmt.setLong(3, reportDate);
			ResultSet rs = pstmt.executeQuery();
			List<BMonthByDayItem> reportForPeriodList = new ArrayList<BMonthByDayItem>();
			while (rs.next()) {
				reportForPeriodList.add(new BMonthByDayItem(rs.getLong(1), rs
						.getTimestamp(2), rs.getString(3), rs.getDouble(4), rs
						.getString(5), rs.getDouble(6), rs.getDouble(7), rs
						.getString(8), rs.getString(9)));
			}
			log.debug("Getting report for periodlist done. size = "
					+ reportForPeriodList.size());
			return reportForPeriodList;
		} catch (SQLException e) {
			log.error("Could not get report for period for abonent "
					+ abonentId + "; device " + deviceId, e);
			throw new BMonthByDayException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MONTH_BY_DAY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get report for period for abonent " + abonentId
							+ "; device " + deviceId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String REPORT_FOR_PERIOD_STMT = new StringBuffer()
			.append("select t.service_date service_date, s.name service_name,")
			.append("p.name tariff_plan_name, s.code code_name,")
			.append(
					"sum(decode(u.base_unit_id,2,(t.service_count3/u.mul_value),")
			.append(
					"ceil(t.service_count3/u.mul_value))) volume, d.device connection_name, v.note client_note, u.name unit_name ")
			.append("from db.tdr t, db.connect_type ct, tr.service s,")
			.append("tr.packet_type p, db.device d, db.view_link v,")
			.append("db.dbill b, db.bill, db.report_date rd, tr.unit u,")
			.append("db.account_usr usr ")
			.append("where t.report_date_id = ? ")
			.append("and bill.abonent_id = ? ")
			.append(
					"and t.service_count_group_id = u.id and b.report_date_id = t.report_date_id ")
			.append(
					"and t.dbill_type_id = b.dbill_type_id and t.bill_id = b.bill_id ")
			.append(
					"and bill.id = b.bill_id and bill.report_date_id = b.report_date_id ")
			.append(
					"and t.abonent_id = bill.abonent_id and t.abonent_id = v.abonent_id ")
			.append("and v.device_id = d.id and rd.id = t.report_date_id ")
			.append(
					"and t.service_date >= v.date_from and t.service_date < nvl(v.date_to, t.service_date + 1) ")
			.append("and t.abonent_group_id = p.id and t.device = d.device ")
			.append(
					"and v.packet_type_id = p.id and t.device_group_id = d.device_group_id ")
			.append("and t.town_id = d.town_id and d.connect_type_id = ct.id ")
			.append(
					"and t.tariff_type_id = s.id and t.account_usr_id = usr.id ")
			.append(
					"group by s.name, p.name, s.code, d.device, v.note, rd.from_date, ")
			.append(
					"usr.login, u.name, t.service_date order by d.device, min(v.date_from)")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.mbd.IBReport#getReportForPeriod(long,
	 *      long)
	 */
	public List getReportForPeriod(long reportDate, long abonentId)
			throws BReportException {
		log.info("getting report for period reportDate = " + reportDate
				+ ", abonentId = " + abonentId + "...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<BReportForPeriodItem> retVal = new ArrayList<BReportForPeriodItem>();

		try {
			conn = dataSource.getConnection();
			log.debug("REPORT_FOR_PERIOD_STMT = " + REPORT_FOR_PERIOD_STMT);
			pstmt = conn.prepareStatement(REPORT_FOR_PERIOD_STMT);
			pstmt.setLong(1, reportDate);
			pstmt.setLong(2, abonentId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new BReportForPeriodItem(rs.getTimestamp(1), rs
						.getString(2), rs.getString(3), rs.getString(4), rs
						.getLong(5), rs.getString(6), rs.getString(7), rs
						.getString(8)));
			}
			return retVal;
		} catch (SQLException e) {
			log.error("Could not get device group id", e);
			return null;
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String MONTH_BY_DATE_STMT = new StringBuffer().append(
			"select id, serv_date, ").append("name, ").append("tech_volume, ")
			.append("unit, ").append("summ, ").append(
					"debit, detail_type_name, ").append("currency ").append(
					"from view_month_by_day mbd ").append(
					"where mbd.abonent_id = ? ").append(
					"and mbd.device_id = ? ").append(
					"and mbd.report_date_id = ? ").append(
					"order by serv_date desc").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.mbd.IBReport#getMonthByDay(long,
	 *      long, long)
	 */
	public List getMonthByDay(long abonentId, long deviceId, long reportDate)
			throws BMonthByDayException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting month by day list form abonent=" + abonentId
					+ "; device=" + deviceId + "; reportDate=" + reportDate);
			conn = dataSource.getConnection();
			log.debug("stmt = " + MONTH_BY_DATE_STMT);
			pstmt = conn.prepareStatement(MONTH_BY_DATE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, deviceId);
			pstmt.setLong(3, reportDate);
			ResultSet rs = pstmt.executeQuery();
			List<BMonthByDayItem> monthByDayList = new ArrayList<BMonthByDayItem>();
			while (rs.next()) {
				monthByDayList.add(new BMonthByDayItem(rs.getLong(1), rs
						.getTimestamp(2), rs.getString(3), rs.getDouble(4), rs
						.getString(5), rs.getDouble(6), rs.getDouble(7), rs
						.getString(8), rs.getString(9)));
			}
			log.debug("Getting month by day list done size = "
					+ monthByDayList.size());
			return monthByDayList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Could not get month-for-day report for abonent "
					+ abonentId + "; device " + deviceId, e);
			throw new BMonthByDayException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MONTH_BY_DAY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get month-for-day report for abonent "
							+ abonentId + "; device " + deviceId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String REPORT_BY_CONNECTION_STMT = new StringBuffer()
			.append("select t.service_date service_date, s.name service_name, ")
			.append("p.name tariff_plan_name, s.code code_name, ")
			.append("decode(u.base_unit_id,2,(t.service_count3/u.mul_value),")
			.append("ceil(t.service_count3/u.mul_value)) volume, u.name ")
			.append(" unit_name, usr.login ACCOUNT_NAME ")

			.append("from db.tdr t, db.connect_type ct, tr.service s, ")
			.append("tr.packet_type p, db.device d, db.view_link v, ")
			.append("db.dbill b, db.bill, db.report_date rd, tr.unit u, ")
			.append("db.account_usr usr ")

			.append("where t.report_date_id = ? ")
			.append("and bill.abonent_id = ? and t.device = ? ")
			.append(
					"and t.service_count_group_id = u.id and b.report_date_id = t.report_date_id ")
			.append(
					"and t.dbill_type_id = b.dbill_type_id and t.bill_id = b.bill_id ")
			.append(
					"and bill.id = b.bill_id and bill.report_date_id = b.report_date_id ")
			.append(
					"and t.abonent_id = bill.abonent_id and t.abonent_id = v.abonent_id ")
			.append("and v.device_id = d.id and rd.id = t.report_date_id ")
			.append(
					"and t.service_date >= v.date_from and t.service_date < nvl(v.date_to, t.service_date + 1) ")
			.append("and t.abonent_group_id = p.id and t.device = d.device ")
			.append("and t.device_group_id = d.device_group_id ")
			.append("and t.town_id = d.town_id and d.connect_type_id = ct.id ")
			.append(
					"and t.tariff_type_id = s.id and t.account_usr_id = usr.id ")
			.append(" order by d.device, t.service_date").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.mbd.IBReport#getReportByConnection(java.lang.String,
	 *      long, java.lang.String)
	 */
	public List getReportByConnection(String reportDate, long abonentId,
			String connect) throws BReportException {
		log.info("getting report by connection ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<BReportByConnectionItem> retVal = new ArrayList<BReportByConnectionItem>();

		try {
			conn = dataSource.getConnection();
			log.debug("REPORT_BY_CONNECTION_STMT = "
					+ REPORT_BY_CONNECTION_STMT);
			pstmt = conn.prepareStatement(REPORT_BY_CONNECTION_STMT);
			pstmt.setString(1, reportDate);
			pstmt.setLong(2, abonentId);
			pstmt.setString(3, connect);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new BReportByConnectionItem(rs.getString(1), rs
						.getString(2), rs.getString(3), rs.getString(4), rs
						.getDouble(5), rs.getString(6), rs.getString(7)));
			}
			return retVal;
		} catch (SQLException e) {
			log.error("Could not get device group id", e);
			return null;
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
