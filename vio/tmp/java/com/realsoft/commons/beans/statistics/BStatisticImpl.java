package com.realsoft.commons.beans.statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BStatisticImpl implements IBStatistic {

	private static Logger log = Logger.getLogger(BStatisticImpl.class);

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMM");

	private static SimpleDateFormat SDF_DATE = new SimpleDateFormat(
			"dd-MM-yyyy");

	private BasicDataSource dataSource = null;

	public BStatisticImpl() {
		super();
	}

	public List getDefaultStatistic(long radiusLoginId)
			throws BStatisticException {
		return getStatistic(radiusLoginId, Integer.valueOf(
				SDF.format(Calendar.getInstance().getTime())).intValue());
	}

//	private static final String STATISTICS_STMT = new StringBuffer()
//	.append("select t.service_date, s.id, s.name service_name, round(sum(t.service_count2), 2) service_count, ")
//	.append("u.short_name        unit_name, round(sum(t.debit), 2) amount from tr.service s, db.link_type lt, ")
//	.append("db.link l,tr.unit u, db.bill b, db.dbill d, db.account_usr au, db.tdr t, db.report_date rd ")
//	.append("where s.id = t.tariff_type_id and u.id = t.service_count_group_id and rd.id = t.report_date_id ")
//	.append("and lt.id = au.link_type_id and b.abonent_id = lt.abonent_id and b.id = d.bill_id and b.report_date_id = d.report_date_id ")
//	.append("and d.report_date_id = t.report_date_id and d.bill_id = t.bill_id and d.dbill_type_id = t.dbill_type_id ")
//	.append("and l.device_id = t.device_id and lt.id = l.link_type_id and b.report_date_id = ? and au.login = ? ")
//	.append("and (rd.from_date >= au.from_date and rd.from_date < nvl(au.to_date, rd.from_date + 1)) ")
//	.append("and (rd.from_date >= l.date_from and rd.from_date < nvl(l.date_to, rd.from_date + 1)) and au.account_usr_type_id = 0 ")
//	.append("and (t.service_count <> 0 or t.service_count1 <> 0 or t.service_count2 <> 0 or t.service_count3 <> 0) ")
//	.append("group by t.service_date, s.id, s.name, u.short_name order by s.name, t.service_date desc")
//	.toString();//old query (backup)
	
	private static final String STATISTICS_STMT = new StringBuffer()
			.append("select t.service_date, s.id, s.name service_name, round(sum(t.service_count2), 2) service_count, ")
			.append("u.short_name unit_name, round(sum(t.debit), 2) amount from tr.service s, tr.unit u, db.tdr t ")
			.append("where s.id = t.tariff_type_id and u.id = t.service_count_group_id and t.report_date_id = ? ")
			.append("and t.account_usr_id = ? and (t.service_count <> 0 or t.service_count1 <> 0 or ")
			.append("t.service_count2 <> 0 or t.service_count3 <> 0) group by t.service_date, s.id, s.name, u.short_name ")
			.append("order by s.name, t.service_date desc")
			.toString();

	public List getStatistic(long radiusLoginId, int currDate)
			throws BStatisticException {
		List<BStatisticItem> result = new ArrayList<BStatisticItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting statistiс list radiusLoginId = "
					+ radiusLoginId + "; currDate = " + currDate + " ...");
			conn = dataSource.getConnection();
			log.debug("STATISTICS_STMT = "+STATISTICS_STMT);
			pstmt = conn.prepareStatement(STATISTICS_STMT);
			pstmt.setInt(1, currDate);
			pstmt.setLong(2, radiusLoginId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BStatisticItem(rs.getTimestamp(1),
						rs.getLong(2), rs.getString(3), rs.getDouble(4), rs
								.getString(5), rs.getDouble(6)));
			}
			log.debug("Getting statistic list done size = " + result.size());
			return result;
		} catch (SQLException e) {
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String STATISTIC_PERIODS_STMT = "select id, name from db.view_report_date_web order by id desc";

	public List getStatisticPeriods() throws BStatisticException {
		List<BStatisticPeriodItem> result = new ArrayList<BStatisticPeriodItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting statistiс periods ...");
			conn = dataSource.getConnection();
			log.debug(STATISTIC_PERIODS_STMT);
			pstmt = conn.prepareStatement(STATISTIC_PERIODS_STMT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BStatisticPeriodItem(rs.getLong(1), rs
						.getString(2)));
			}
			log.debug("Getting statistiс periods done");
			return result;
		} catch (SQLException e) {
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic periods", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String STATISTIC_PERIODS_FROM_TO_STMT = "select * from  db.view_report_date_web t where t.to_date <= ? and t.from_date >= ? order by t.id desc";

	public List getStatisticPeriods(Date fromDate, Date toDate)
			throws BStatisticException {
		log.info("fromDate = " + SDF_DATE.format(fromDate) + "; toDate = "
				+ SDF_DATE.format(toDate));
		List<BStatisticPeriodItem> result = new ArrayList<BStatisticPeriodItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting statistiс periods ...");
			conn = dataSource.getConnection();
			log.debug(STATISTIC_PERIODS_FROM_TO_STMT);
			pstmt = conn.prepareStatement(STATISTIC_PERIODS_FROM_TO_STMT);
			Calendar toDateCalendar = Calendar.getInstance();
			toDateCalendar.setTime(toDate);
			toDateCalendar.set(Calendar.MONTH, toDateCalendar
					.get(Calendar.MONTH) + 1);
			toDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
			pstmt.setDate(1, new java.sql.Date(toDateCalendar.getTime()
					.getTime()));
			pstmt.setDate(2, new java.sql.Date(fromDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new BStatisticPeriodItem(rs.getLong(1), rs
						.getString(2)));
			}
			log.debug("Getting statistiс periods done");
			return result;
		} catch (SQLException e) {
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic periods", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String STATISTIC_STMT = new StringBuffer().append(
			"select d.device, ").append("c.name, ").append("dg.name, ").append(
			"db.pkg_abonent.get_address(l.address_id, 1, 1) address ").append(
			"from db.device       d, ").append("db.link_type    lt, ").append(
			"db.link         l, ").append("db.connect_type c, ").append(
			"db.device_group dg ").append("where d.id = ? ").append(
			"and lt.id = l.link_type_id ").append("and d.id = lt.device_id ")
			.append("and c.id = d.connect_type_id ").append(
					"and dg.id = d.device_group_id ").append(
					"and sysdate >= l.date_from ").append(
					"and sysdate < nvl(l.date_to, sysdate + 1) ").toString();

	public BStatisticItem getStatistic(long deviceId)
			throws BStatisticException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting statistic list");
			conn = dataSource.getConnection();
			log.debug(STATISTIC_STMT);
			pstmt = conn.prepareStatement(STATISTIC_STMT);
			pstmt.setLong(1, deviceId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new BStatisticItem(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4));
			}
			throw new BStatisticException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR,
					"Could not get statistic for device " + deviceId);
		} catch (SQLException e) {
			log.error("Could not get statistic for device " + deviceId, e);
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic for device " + deviceId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String TRAFFIC_FOR_PERIOD_STMT = new StringBuffer().append(
			"select sum(t.acctinputoctets), ").append(
			"sum(t.acctoutputoctets), ").append("t.name town, ").append(
			"pt.name ").append("from radius.radacct t, ").append(
			"db.account_usr ac, ").append("db.town        t, ").append(
			"db.address     a, ").append("db.street      s, ").append(
			"db.link        l, ").append("tr.packet_type pt ").append(
			"where trunc(t.acctstarttime, 'dd') between ? and ? ").append(
			"and l.link_type_id = ac.link_type_id ").append(
			"and l.packet_type_id = pt.id ").append("and l.address_id = a.id ")
			.append("and a.street_id = s.id ").append("and s.town_id = t.id ")
			.append("and t.account_usr_id = ac.id ").append(
					"and ? >= l.date_from ").append(
					"and ? < nvl(l.date_to, ? + 1) ").append(
					"group by t.name, pt.name").toString();

	public List getTrafficForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting traffic for period list from "
					+ SDF_DATE.format(dateFrom) + "; to "
					+ SDF_DATE.format(dateTo));
			conn = dataSource.getConnection();
			log.debug(TRAFFIC_FOR_PERIOD_STMT);
			pstmt = conn.prepareStatement(TRAFFIC_FOR_PERIOD_STMT);
			pstmt.setDate(1, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(3, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(4, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(5, new java.sql.Date(dateTo.getTime()));
			ResultSet rs = pstmt.executeQuery();
			List<BTrafficItem> list = new ArrayList<BTrafficItem>();
			while (rs.next()) {
				list.add(new BTrafficItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3), rs.getString(4)));
			}
			return list;
		} catch (SQLException e) {
			log.error("Could not get statistic about traffic for period "
					+ SDF_DATE.format(dateFrom) + "; to "
					+ SDF_DATE.format(dateTo), e);
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic about traffic for period "
							+ SDF_DATE.format(dateFrom) + "; to "
							+ SDF_DATE.format(dateTo), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CONNECTION_FOR_PERIOD_STMT = new StringBuffer()
			.append("select count(distinct username), ").append("pt.name, ")
			.append("t.name ").append("from radius.radacct t, ").append(
					"db.account_usr ac, ").append("db.link        l, ").append(
					"db.town        t, ").append("db.address     a, ").append(
					"db.street      s, ").append("tr.packet_type pt ").append(
					"where trunc(t.acctstarttime, 'dd') between ? and ? ")
			.append("and l.link_type_id = ac.link_type_id ").append(
					"and l.address_id = a.id ").append(
					"and a.street_id = s.id ").append("and s.town_id = t.id ")
			.append("and t.account_usr_id = ac.id ").append(
					"and pt.id = l.packet_type_id ").append(
					"and ? >= l.date_from ").append(
					"and ? < nvl(l.date_to, ? + 1) ").append(
					"group by t.name, ").append("pt.name").toString();

	public List getConnectionForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting connections for period list from "
					+ SDF_DATE.format(dateFrom) + "; to "
					+ SDF_DATE.format(dateTo));
			conn = dataSource.getConnection();
			log.debug(CONNECTION_FOR_PERIOD_STMT);
			pstmt = conn.prepareStatement(CONNECTION_FOR_PERIOD_STMT);
			pstmt.setDate(1, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(3, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(4, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(5, new java.sql.Date(dateTo.getTime()));
			ResultSet rs = pstmt.executeQuery();
			List<BCountItem> list = new ArrayList<BCountItem>();
			while (rs.next()) {
				list.add(new BCountItem(rs.getLong(1), rs.getString(2), rs
						.getString(3)));
			}
			return list;
		} catch (SQLException e) {
			log.error(
					"Could not get statistic about active connections for period "
							+ SDF_DATE.format(dateFrom) + "; to "
							+ SDF_DATE.format(dateTo), e);
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic about active connections for period "
							+ SDF_DATE.format(dateFrom) + "; to "
							+ SDF_DATE.format(dateTo), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String NEW_ACCOUNT_FOR_PERIOD_STMT = new StringBuffer()
			.append("select count(*), ").append("pt.name, ").append("t.name ")
			.append("from db.account_usr ac, ").append("db.link        l, ")
			.append("db.town        t, ").append("db.address     a, ").append(
					"db.street      s, ").append("tr.packet_type pt ").append(
					"where ac.system_date between ? and ? ").append(
					"and l.link_type_id = ac.link_type_id ").append(
					"and l.address_id = a.id ").append(
					"and a.street_id = s.id ").append("and s.town_id = t.id ")
			.append("and pt.id = l.packet_type_id ").append(
					"and ? >= l.date_from ").append(
					"and ? < nvl(l.date_to, ? + 1) ").append(
					"group by t.name, ").append("pt.name").toString();

	public List getNewAccountForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting new accounts for period list from "
					+ SDF_DATE.format(dateFrom) + "; to "
					+ SDF_DATE.format(dateTo));
			conn = dataSource.getConnection();
			log.debug(NEW_ACCOUNT_FOR_PERIOD_STMT);
			pstmt = conn.prepareStatement(NEW_ACCOUNT_FOR_PERIOD_STMT);
			pstmt.setDate(1, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(3, new java.sql.Date(dateFrom.getTime()));
			pstmt.setDate(4, new java.sql.Date(dateTo.getTime()));
			pstmt.setDate(5, new java.sql.Date(dateTo.getTime()));
			ResultSet rs = pstmt.executeQuery();
			List<BCountItem> list = new ArrayList<BCountItem>();
			while (rs.next()) {
				list.add(new BCountItem(rs.getLong(1), rs.getString(2), rs
						.getString(3)));
			}
			return list;
		} catch (SQLException e) {
			log.error("Could not get statistic about new accounts for period "
					+ SDF_DATE.format(dateFrom) + "; to "
					+ SDF_DATE.format(dateTo), e);
			throw new BStatisticException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.STATISTIC_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get statistic about new accounts for period "
							+ SDF_DATE.format(dateFrom) + "; to "
							+ SDF_DATE.format(dateTo), e);
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
