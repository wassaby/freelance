/*
 * Created on 25.03.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BReportImpl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.report.abonent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentBalanceInfo;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.FormatterFactory;
import com.realsoft.utils.string.StringUtils;

/**
 * @author dimad
 * 
 */
public class BReportImpl implements IBReport, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BReportImpl.class);

	private BeanFactory beanFactory = null;

	private BasicDataSource dataSource = null;

	private FormatterFactory formatter = null;

	private ConverterManager converter = null;

	public BReportImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getTotal(java.util.Date,
	 *      java.util.Date, java.lang.String)
	 */
	public List<BTotalItem> getTotal(Date dateFrom, Date dateTo,
			String netSource) throws BReportException, UtilsException {
		return getTotal(dateFrom, dateTo, netSource, null);
	}

	private static final String TOTALS_ALL_PSTMT = new StringBuffer().append(
			"select currency, ").append("sum(amount) credit, ").append(
			"0 debit ").append("from db.net_payments bl ").append(
			"where bl.net_source_id = ? and ").append(
			"bl.pay_date between ? and ? ").append("group by currency")
			.toString();

	private static final String TOTALS_PSTMT = new StringBuffer().append(
			"select currency, ").append("sum(amount) credit, ").append(
			"0 debit ").append("from db.net_payments bl ").append(
			"where bl.net_source_id = ? and ").append("bl.device = ? and ")
			.append("bl.pay_date between ? and ? ").append("group by currency")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getTotal(java.util.Date,
	 *      java.util.Date, java.lang.String, java.lang.String)
	 */
	public List<BTotalItem> getTotal(Date dateFrom, Date dateTo,
			String netSource, String phone) throws BReportException,
			UtilsException {
		log.info("report totals dateFrom = " + formatter.format(dateFrom)
				+ "; dateTo = " + formatter.format(dateTo) + "; netSource = "
				+ netSource + "; ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			java.sql.Date dateFromL = new java.sql.Date(dateFrom.getTime());
			java.sql.Date dateToL = new java.sql.Date(dateTo.getTime());
			if ((phone == null) || (phone.trim().equals(""))) {
				log.debug(TOTALS_ALL_PSTMT);
				pstmt = conn.prepareStatement(TOTALS_ALL_PSTMT);
				pstmt.setString(1, netSource);
				pstmt.setDate(2, dateFromL);
				pstmt.setDate(3, dateToL);
			} else {
				log.debug(TOTALS_PSTMT);
				pstmt = conn.prepareStatement(TOTALS_PSTMT);
				pstmt.setString(1, netSource);
				pstmt.setString(2, phone);
				pstmt.setDate(3, dateFromL);
				pstmt.setDate(4, dateToL);
			}

			ResultSet rs = pstmt.executeQuery();

			List<BTotalItem> list = new ArrayList<BTotalItem>();
			while (rs.next()) {
				list.add(new BTotalItem(rs.getDouble(2), rs.getString(1), rs
						.getDouble(3), CommonsBeansConstants.REPORT_TOTAL_OK,
						CommonsBeansConstants.OK));
			}
			log.debug("ResultSet size = " + list.size());
			log.info("report totals done");
			return list;
		} catch (SQLException e) {
			log.error("Could not get total report", e);
			throw new BReportException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REPORT_TOTAL_ERROR
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
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getPayment(java.util.Date,
	 *      java.util.Date, java.lang.String)
	 */
	public List<BPaymentItem> getPayment(Date dateFrom, Date dateTo,
			String netSource) throws BReportException {
		return getPayment(dateFrom, dateTo, netSource, null);
	}

	private static final String PAYMENTS_ALL_PSTMT = new StringBuffer().append(
			"select np.abonent_id, ").append("np.amount, ").append(
			"np.currency, ").append("np.pay_date, ")
			.append("np.device phone, ").append("np.trans_no ").append(
					"from db.net_payments np ").append(
					"where np.net_source_id = ? ").append(
					"and np.pay_date between ? and ?").toString();

	private static final String PAYMENTS_PSTMT = new StringBuffer().append(
			"select np.abonent_id, ").append("np.amount, ").append(
			"np.currency, ").append("np.pay_date, ")
			.append("np.device phone, ").append("np.trans_no ").append(
					"from db.net_payments np ").append(
					"where np.net_source_id = ? ").append("and np.device = ? ")
			.append("and np.pay_date between ? and ?").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getPayment(java.util.Date,
	 *      java.util.Date, java.lang.String, java.lang.String)
	 */
	public List<BPaymentItem> getPayment(Date dateFrom, Date dateTo,
			String netSource, String phone) throws BReportException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("==============");
			log.info("report totals dateFrom = " + formatter.format(dateFrom)
					+ "; dateTo = " + formatter.format(dateTo)
					+ "; netSource = " + netSource + "; phone = " + phone
					+ " ...");
			conn = dataSource.getConnection();
			java.sql.Date dateFromL = new java.sql.Date(dateFrom.getTime());
			java.sql.Date dateToL = new java.sql.Date(dateTo.getTime());
			if ((phone == null) || (phone.trim().equals(""))) {
				log.debug(PAYMENTS_ALL_PSTMT);
				pstmt = conn.prepareStatement(PAYMENTS_ALL_PSTMT);
				pstmt.setString(1, netSource);
				pstmt.setDate(2, dateFromL);
				pstmt.setDate(3, dateToL);
			} else {
				log.debug(PAYMENTS_PSTMT);
				pstmt = conn.prepareStatement(PAYMENTS_PSTMT);
				pstmt.setString(1, netSource);
				pstmt.setString(2, phone);
				pstmt.setDate(3, dateFromL);
				pstmt.setDate(4, dateToL);
			}

			ResultSet rs = pstmt.executeQuery();

			List<BPaymentItem> list = new ArrayList<BPaymentItem>();
			while (rs.next()) {
				Calendar date = Calendar.getInstance();
				date.setTime((Timestamp) converter.getObject(rs.getString(4),
						Timestamp.class));
				list.add(new BPaymentItem(rs.getLong(1), rs.getDouble(2), rs
						.getString(3), date, rs.getString(5),
						CommonsBeansConstants.REPORT_PAYMENT_OK,
						CommonsBeansConstants.OK, rs.getString(6)));
			}
			log.debug("ResultSet size = " + list.size());
			log.info("report payment done");
			return list;
		} catch (UtilsException e) {
			log.error("Could not get convert date", e);
			throw new BReportException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REPORT_PAYMENT_ERROR, e.getMessage(),
					e);
		} catch (SQLException e) {
			log.error("Could not get payments report", e);
			throw new BReportException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.REPORT_PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHARGE_STMT = new StringBuffer()
			.append("select t.device, ")
			.append("t.service_date, ")
			.append("t.detail_data, ")
			.append("t.detail, ")
			.append("t.detail_name, ")
			.append("t.service_count, ")
			.append("t.name sc_name, ")
			.append("t.debit, ")
			.append("r.name, ")
			.append("t.dbill_type_id, ")
			.append("dbt.name dbt_name, ")
			.append("t.service_pack_type_id ")
			.append("from db.view_tdr t, ")
			.append("db.bill b, ")
			.append("db.report_date r, ")
			.append("db.dbill_type dbt ")
			.append("where t.report_date_id = b.report_date_id ")
			.append("and r.id = t.report_date_id ")
			.append("and t.bill_id=b.id ")
			.append("and dbt.id = t.dbill_type_id ")
			.append("and b.abonent_id = ? ")
			.append("and b.bill_type_id = ? ")
			.append("and b.report_date_id = ? ")
			.append(
					"order by t.dbill_type_id, t.device, t.service_pack_type_id, t.service_date")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getDetailPayment(java.lang.String,
	 *      long, long, long)
	 */
	public List<BDetailChargeInfo> getDetailCharge(String phone,
			long abonentId, long billTypeId, long reportDateId)
			throws BReportException {
		log.debug("Getting detail payment ...");
		log.debug("Charge info for abonent " + abonentId + "; bill type = "
				+ billTypeId + "; report date = " + reportDateId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			log.debug("CHARGE_STMT = " + CHARGE_STMT);
			pstmt = conn.prepareStatement(CHARGE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, billTypeId);
			pstmt.setLong(3, reportDateId);
			rs = pstmt.executeQuery();
			List<BDetailChargeInfo> detailChargeList = new ArrayList<BDetailChargeInfo>();
			while (rs.next()) {
				detailChargeList.add(new BDetailChargeInfo(rs.getString(1), rs
						.getTimestamp(2), rs.getString(3), rs.getString(4), rs
						.getString(5), rs.getLong(6), rs.getString(7), rs
						.getDouble(8), rs.getString(9), rs.getLong(10), rs
						.getString(11), rs.getLong(12)));
			}
			log.debug("detail charge done");
			return detailChargeList;
		} catch (SQLException e) {
			log.error("Could not get charge info for abonent " + abonentId
					+ " bill type = " + billTypeId + " report date = "
					+ reportDateId, e);
			throw new BReportException(
					CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.ACCOUNT_DETAIL_CHARGE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String CHARGE_NO_BILLTYPE_STMT = new StringBuffer()
			.append("select t.device, ")
			.append("t.service_date, ")
			.append("t.detail_data, ")
			.append("t.detail, ")
			.append("t.detail_name, ")
			.append("t.service_count, ")
			.append("t.name sc_name, ")
			.append("t.debit, ")
			.append("r.name, ")
			.append("t.dbill_type_id, ")
			.append("dbt.name dbt_name, ")
			.append("t.service_pack_type_id ")
			.append("from db.view_tdr t, ")
			.append("db.bill b, ")
			.append("db.report_date r, ")
			.append("db.dbill_type dbt ")
			.append("where t.report_date_id = b.report_date_id ")
			.append("and r.id = t.report_date_id ")
			.append("and t.bill_id=b.id ")
			.append("and dbt.id = t.dbill_type_id ")
			.append("and b.abonent_id = ? ")
			.append("and b.report_date_id = ? ")
			.append(
					"order by t.dbill_type_id, t.device, t.service_pack_type_id, t.service_date")
			.toString();

	public List<BDetailChargeInfo> getDetailChargeNoBillType(String phone,
			long abonentId, long reportDateId) throws BReportException {
		log.debug("Getting detail payment ...");
		log.error("Charge info for abonent " + abonentId + "; report date = "
				+ reportDateId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			log.debug("CHARGE_NO_BILLTYPE_STMT = " + CHARGE_NO_BILLTYPE_STMT);
			pstmt = conn.prepareStatement(CHARGE_NO_BILLTYPE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, reportDateId);
			rs = pstmt.executeQuery();
			List<BDetailChargeInfo> detailChargeList = new ArrayList<BDetailChargeInfo>();
			while (rs.next()) {
				detailChargeList.add(new BDetailChargeInfo(rs.getString(1), rs
						.getTimestamp(2), rs.getString(3), rs.getString(4), rs
						.getString(5), rs.getLong(6), rs.getString(7), rs
						.getDouble(8), rs.getString(9), rs.getLong(10), rs
						.getString(11), rs.getLong(12)));
			}
			log.debug("detail charge done");
			return detailChargeList;
		} catch (SQLException e) {
			log.error("Could not get charge info for abonent " + abonentId
					+ " report date = " + reportDateId, e);
			throw new BReportException(
					CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.ACCOUNT_DETAIL_CHARGE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String PAYMENT_STMT = new StringBuffer().append(
			"select r.name, ").append("p.document_id check_num, ").append(
			"mt.name, ").append("min(p.system_Date) system_Date, ").append(
			"sum(p.money) summa, ").append("min(w.name) workplace ").append(
			"from db.payment p, ").append("db.money_type mt, ").append(
			"safety.xworkplaces w, ").append("db.report_date r ").append(
			"where mt.id = p.money_type_id ").append(
			"and w.id = p.user_place_id ").append(
			"and r.id = p.report_date_id ").append("and p.abonent_id = ? ")
			.append("and p.bill_type_id = ? ").append(
					"and p.report_date_id = ? ").append(
					"group by r.name, p.document_id, mt.name").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getDetailCharge(java.lang.String,
	 *      long, long, long)
	 */
	public List<BDetailPaymentInfo> getDetailPayment(String phone,
			long abonentId, long billTypeId, long reportDateId)
			throws BReportException {
		log.debug("Getting detail payment ...");
		log.error("payment info for abonent " + abonentId + "; bill type = "
				+ billTypeId + "; report date = " + reportDateId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			log.debug("PAYMENT_STMT = " + PAYMENT_STMT);
			pstmt = conn.prepareStatement(PAYMENT_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, billTypeId);
			pstmt.setLong(3, reportDateId);
			rs = pstmt.executeQuery();
			List<BDetailPaymentInfo> detailPaymentList = new ArrayList<BDetailPaymentInfo>();
			while (rs.next()) {
				Calendar systemDate = Calendar.getInstance();
				systemDate.setTime(rs.getTimestamp(4));
				detailPaymentList.add(new BDetailPaymentInfo(rs.getString(1),
						rs.getDouble(2), rs.getString(3), systemDate, rs
								.getDouble(5), rs.getString(6)));
			}
			log.debug("detail payment done size = " + detailPaymentList.size());
			return detailPaymentList;
		} catch (SQLException e) {
			log.error("Could not get payment info for abonent " + abonentId
					+ " bill type = " + billTypeId + " report date = "
					+ reportDateId, e);
			throw new BReportException(
					CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.ACCOUNT_DETAIL_PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage());
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String ACCOUNT_STATUS_STMT = new StringBuffer().append(
			"select r.name, ").append("bt.name, ").append("b.in_money, ")
			.append("b.debit+b.change_in_debit+b.change_debit debit, ").append(
					"b.credit+b.change_in_credit+b.change_credit credit, ")
			.append("b.out_money, ").append("r.id, ").append("bt.id ").append(
					"from db.saldo b, ").append("db.bill_type bt, ").append(
					"db.report_date r ")
			.append("where bt.id = b.bill_type_id ").append(
					"and r.id = b.report_date_id ").append(
					"and b.abonent_id = ? ").append("order by r.id").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getAccountStatus(java.lang.String,
	 *      long)
	 */
	public List<BAbonentStatusInfo> getAccountStatus(String phone,
			long abonentId) throws BReportException {
		log.debug("Getting account status ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			log.debug("ACCOUNT_STATUS_STMT = " + ACCOUNT_STATUS_STMT);
			pstmt = conn.prepareStatement(ACCOUNT_STATUS_STMT);
			pstmt.setLong(1, abonentId);
			rs = pstmt.executeQuery();
			List<BAbonentStatusInfo> accountStatusList = new ArrayList<BAbonentStatusInfo>();
			while (rs.next()) {
				accountStatusList.add(new BAbonentStatusInfo(rs.getString(1),
						rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs
								.getDouble(5), rs.getDouble(6), rs.getLong(7),
						rs.getLong(8)));
			}
			log.debug("Account status done");
			return accountStatusList;
		} catch (SQLException e) {
			log.error("Could not get account status info for abonent "
					+ abonentId, e);
			throw new BReportException(
					CommonsBeansConstants.ERR_ACCOUNT,
					CommonsBeansConstants.ACCOUNT_STATUS_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeResultSet(rs);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String ACCOUNTFOR_PERIOD_LIST_STMT = new StringBuffer()
			.append("select serv_date, ").append("afp.login, ").append(
					"afp.name, ").append("afp.tech_volume, ").append(
					"afp.volume, ").append("afp.unit, ").append("afp.debit, ")
			.append("afp.currency, ").append("afp.row_type ").append(
					"from view_account_for_period afp ").append(
					"where afp.acc_id = ? ").append("and afp.abonent_id = ? ")
			.append("and afp.report_date_id = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getAccountForPeriodList(long,
	 *      long, long)
	 */
	public List<BAbonentForPeriodItem> getAccountForPeriodList(long accountId,
			long abonentId, long reportDate) throws BReportException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting account for period list for accountId="
					+ accountId + " abonent=" + abonentId + "; reportDate="
					+ reportDate);
			conn = dataSource.getConnection();
			log.debug("stmt = " + ACCOUNTFOR_PERIOD_LIST_STMT);
			pstmt = conn.prepareStatement(ACCOUNTFOR_PERIOD_LIST_STMT);
			pstmt.setLong(1, accountId);
			pstmt.setLong(2, abonentId);
			pstmt.setLong(3, reportDate);
			long startTime = System.currentTimeMillis();
			log.debug("Staring execute query ");
			ResultSet rs = pstmt.executeQuery();
			log.debug("Query executed");
			List<BAbonentForPeriodItem> accountForPeriodList = new ArrayList<BAbonentForPeriodItem>();
			while (rs.next()) {
				accountForPeriodList.add(new BAbonentForPeriodItem(rs
						.getTimestamp(1), rs.getString(2), rs.getString(3), rs
						.getDouble(4), rs.getDouble(5), rs.getString(6), rs
						.getDouble(7), rs.getString(8), rs.getLong(9)));
			}
			log.debug("result set have got");
			log.debug("Getting account for period list done size = "
					+ accountForPeriodList.size());
			return accountForPeriodList;
		} catch (SQLException e) {
			log.error("Could not get account for period report for accountId="
					+ accountId + " abonent=" + abonentId, e);
			throw new BReportException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_PERIOD_ACCOUNT_LIST_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get account for period report for accountId="
							+ accountId + " abonent=" + abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getAbonentBalance135(long)
	 */
	public BAbonentBalanceInfo getAbonentBalance135(long abonentId)
			throws BAbonentException {
		IBAbonent abonent = (IBAbonent) beanFactory.getBean(
				ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
		return abonent.abonentBalance135(abonentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#abonentBalance13(java.lang.String,
	 *      long, java.util.Calendar)
	 */
	public double getAbonentBalance13(String phone, long abonentId)
			throws BReportException {
		try {
			IBAbonent abonent = (IBAbonent) beanFactory.getBean(
					ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
			return abonent.abonentBalance13(phone, abonentId);
		} catch (CommonsBeansException e) {
			log.error("Could not get abonent balance for abonent=" + abonentId,
					e);
			throw new BReportException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_BALANCE_ERROR,
					"Could not get abonent balance for abonent=" + abonentId, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getDetailPayment(java.lang.String,
	 *      long, long)
	 */
	public List<BDetailPaymentInfo> getDetailPayment(String guid,
			long billTypeId, long reportDateId) throws BReportException {
		String[] guids = guid.split("-");
		long abId = (Long) converter.getObject(guids[1], long.class);
		return getDetailPayment(null, abId, billTypeId, reportDateId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getAccountStatus(java.lang.String)
	 */
	public List<BAbonentStatusInfo> getAccountStatus(String guid)
			throws BReportException {
		String[] guids = guid.split("-");
		long abId = (Long) converter.getObject(guids[1], long.class);
		return getAccountStatus(null, abId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.report.abonent.IBReport#getDetailCharge(java.lang.String,
	 *      long, long)
	 */
	public List<BDetailChargeInfo> getDetailCharge(String guid,
			long billTypeId, long reportDateId) throws BReportException {
		String[] guids = guid.split("-");
		long abId = (Long) converter.getObject(guids[1], long.class);
		return getDetailCharge(null, abId, billTypeId, reportDateId);
	}

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

	public FormatterFactory getFormatter() {
		return formatter;
	}

	public void setFormatter(FormatterFactory formatter) {
		this.formatter = formatter;
	}

	public double getAbonentBalanceGuid13(String guids) throws BReportException {
		String[] guid = guids.split("-");
		long abonentId = (Long) converter.getObject(guid[1], long.class);
		try {
			IBAbonent abonent = (IBAbonent) beanFactory.getBean(
					ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
			return abonent.getAbonentBalance13(abonentId);
		} catch (CommonsBeansException e) {
			log.error("Could not get abonent balance for abonent=" + abonentId,
					e);
			throw new BReportException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ACCOUNT_BALANCE_ERROR,
					"Could not get abonent balance for abonent=" + abonentId, e);
		}
	}

}