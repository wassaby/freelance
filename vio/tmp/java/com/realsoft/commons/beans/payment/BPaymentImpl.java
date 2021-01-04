/*
 * Created on 23.03.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPaymentImpl.java,v 1.2 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.realsoft.commons.beans.payment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.FormatterFactory;
import com.realsoft.utils.string.StringUtils;

/**
 * @author dimad
 * 
 */
public class BPaymentImpl implements IBPayment, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BPaymentImpl.class);

	// private static final String ZERO = "0";

	// private static SimpleDateFormat sdfDate = new SimpleDateFormat(
	// "yyyyMMddHHmmss");

	private BeanFactory beanFactory = null;

	private BasicDataSource dataSource = null;

	private FormatterFactory formatter = null;

	private ConverterManager converter = null;

	public BPaymentImpl() {
		// converter.initialize();
	}

	private static final String TRANS_STMT = "select np.our_document_id from db.net_payments np where np.trans_no=?";

	private static final String PAYMENT_STMT = "{call db.create_net_pay( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.billing.jws.com.payment.IPayment#payment(com.realsoft.billing.jws.com.payment.PaymentRequest)
	 */
	public BPaymentResponce payment(long abonentId, double amount,
			Calendar currDate, String currency, String documentNo,
			String netSource, String subDivision, String payType,
			String terminal, String transNo, String phone)
			throws BPaymentException, BRegistrationException, BAbonentException {
		log.info("paying ...");
		log.debug("payment with account = " + abonentId + ": amount = "
				+ amount + ": currency = " + currency + ": transNo = "
				+ transNo + ": netSource = " + netSource + ": subdivision = "
				+ subDivision + ": payType = " + payType + ": terminal = "
				+ terminal + ": transNo = " + transNo + ": phone = " + phone
				+ "; documentNo = " + documentNo);
		try {
			log.debug("currDate = " + formatter.format(currDate.getTime()));
		} catch (UtilsException e1) {
			e1.printStackTrace();
		}
		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		Connection conn = null;
		if (amount <= 0) {
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT, documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.PAYMENT_AMOUNT_ERROR,
					"Amount can not be less than zero");
		}
		log.debug(CommonsBeansConstants.CURRENCY + " = " + currency);
		if (!currency.equals(CommonsBeansConstants.CURRENCY)) {
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_CURRENCY,
					CommonsBeansConstants.CURRENCY_ERROR,
					"Such currency does not support");
		}
		BPaymentResponce responce = new BPaymentResponce();

		try {
			log.debug("currDate = " + formatter.format(currDate.getTime()));
			conn = dataSource.getConnection();
			try {
				pstmt = conn.prepareStatement(TRANS_STMT);
				pstmt.setString(1, transNo);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					log.debug("Such transaction " + transNo	+ " already exists");
					// }else{
					errorPayment(phone, currDate, currDate, currency,
							netSource, subDivision, terminal, transNo, amount,
							CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
					throw new BPaymentException(
							CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.TRANSACTION_ERROR,
							"Such transaction already exist");
				}
			} finally {
				CommonOperations.closeStatement(pstmt);
			}

			try {
				cstmt = conn.prepareCall(PAYMENT_STMT);
				cstmt.setString(1, netSource);
				cstmt.setString(2, subDivision);
				cstmt.setLong(3, abonentId);
				cstmt.setString(4, phone);
				cstmt.setDouble(5, amount);
				cstmt.setString(6, currency);
				cstmt.setString(7, payType);
				cstmt.setString(8, formatter.format(
						"%tY-%<tm-%<td %<tH:%<tM:%<tS", currDate.getTime()));
				cstmt.setString(9, documentNo);
				cstmt.setString(10, transNo);
				cstmt.setString(11, "Term:" + terminal);
				cstmt.registerOutParameter(12, Types.NUMERIC);
				cstmt.execute();
				responce.setResponce(CommonsBeansConstants.OK);
				responce.setNote(CommonsBeansConstants.PAYMENT_SUCCESS);
				responce.setReference(cstmt.getLong(12));
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (UtilsException e) {
			log.error("Could not format date", e);
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR, e.getMessage(), e);
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}

		IBAbonent abonent = (IBAbonent) beanFactory.getBean(
				ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
		responce.setBalance(abonent.abonentBalance13(phone, abonentId));
		log.info("payment done");
		return responce;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.payment.IBPayment#payment(double,
	 *      java.util.Calendar, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public BPaymentResponce payment(double amount, Calendar currDate,
			String currency, String documentNo, String netSource,
			String subDivision, String payType, String terminal,
			String transNo, String phone) throws BPaymentException,
			BRegistrationException, BAbonentException {
		log.info("paying ...");

		IBRegistration registration = (IBRegistration) beanFactory.getBean(
				ComponentFactoryImpl.getBRegistrationName(),
				IBRegistration.class);
		BRegistrationInfo13 registrationInfo = registration.register13(phone);

		log.debug("payment with phone " + phone + ": account = "
				+ registrationInfo.getAbonentId() + ": amount = " + amount
				+ ": currency = " + currency + ": payType = " + payType
				+ ": netSource = " + netSource + ": terminal = " + terminal
				+ ": transNo = " + transNo);
		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		Connection conn = null;
		if (amount <= 0) {
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.PAYMENT_AMOUNT_ERROR,
					"Account can not be less or equals zero");
		}
		log.debug(CommonsBeansConstants.CURRENCY + " = " + currency);
		if (!currency.equals(CommonsBeansConstants.CURRENCY)) {
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_CURRENCY,
					CommonsBeansConstants.CURRENCY_ERROR,
					"Such currency does not support");
		}
		BPaymentResponce responce = new BPaymentResponce();

		try {
			log.debug("currDate = " + formatter.format(currDate.getTime()));
			conn = dataSource.getConnection();
			try {
				pstmt = conn.prepareStatement(TRANS_STMT);
				pstmt.setString(1, transNo);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					log
							.debug("Such transaction " + transNo
									+ " already exists");
					errorPayment(phone, currDate, currDate, currency,
							netSource, subDivision, terminal, transNo, amount,
							CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
					throw new BPaymentException(
							CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.TRANSACTION_ERROR,
							"Such transaction already exist");
				}
			} finally {
				CommonOperations.closeStatement(pstmt);
			}

			try {
				cstmt = conn.prepareCall(PAYMENT_STMT);
				cstmt.setString(1, netSource);
				cstmt.setString(2, subDivision);
				cstmt.setLong(3, registrationInfo.getAbonentId());
				cstmt.setString(4, phone);
				cstmt.setDouble(5, amount);
				cstmt.setString(6, currency);
				cstmt.setString(7, payType);
				cstmt.setString(8, formatter.format(currDate.getTime()));
				cstmt.setString(9, documentNo);
				cstmt.setString(10, transNo);
				cstmt.setString(11, "Term:" + terminal);
				cstmt.registerOutParameter(12, Types.VARCHAR);
				cstmt.execute();
				responce.setResponce(CommonsBeansConstants.OK);
				responce.setNote(CommonsBeansConstants.PAYMENT_SUCCESS);
				responce.setReference(cstmt.getLong(12));
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (UtilsException e) {
			log.error("Could not format date", e);
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR, e.getMessage(), e);
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			errorPayment(phone, currDate, currDate, currency, netSource,
					subDivision, terminal, transNo, amount,
					CommonsBeansConstants.PAYMENT_FAILURES_WAIT,documentNo);
			throw new BPaymentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}

		IBAbonent abonent = (IBAbonent) beanFactory.getBean(
				ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
		responce.setBalance(abonent.abonentBalance13(phone, registrationInfo
				.getAbonentId()));

		log.info("payment done");
		return responce;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.payment.IBPayment#payment(java.util.List,
	 *      java.util.Calendar, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public List payment(List registrations, Calendar currDate, String currency,
			String documentNo, String netSource, String subDivision,
			String payType, String terminal, String transNo)
			throws BPaymentException, BRegistrationException, BAbonentException {
		List<BPaymentResponce> paymentResponces = new ArrayList<BPaymentResponce>();
		for (Iterator iter = registrations.iterator(); iter.hasNext();) {
			BRegistrationInfo13 info13 = (BRegistrationInfo13) iter.next();
			paymentResponces.add(payment(info13.getAbonentId(), info13
					.getBalance(), currDate, currency, documentNo, netSource,
					subDivision, payType, terminal, transNo, info13.getName()));
		}
		return paymentResponces;
	}

	private static final String PAYMENT135_STMT = "{call un.pkg_from_bank.set_from_bank( ?, ?, ?, ?, ?, ?, ?, ?, ? )}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.payment.IBPayment#payment135(long,
	 *      java.util.Calendar, double, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public BPaymentResponce payment135(long abonentId, Calendar orderDate,
			double money, String documentNo, String netSource, String payType,
			String terminal, String transNo, String device)
			throws BPaymentException, BRegistrationException, BAbonentException {
		log.info("paying ...");
		if (money <= 0) {
			throw new BPaymentException(CommonsBeansConstants.ERR_AMOUNT,
					CommonsBeansConstants.PAYMENT_AMOUNT_ERROR,
					"Amount can not be less than zero");
		}

		BPaymentResponce responce = new BPaymentResponce();

		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			log.debug("payment for abonent = " + abonentId + "; orderDate = "
					+ formatter.format(orderDate.getTime()) + "; money = "
					+ money + "; documentNo = " + documentNo + "; netSource = "
					+ netSource + "; payType = " + payType + "; terminal = "
					+ terminal + "; transNo = " + transNo + "; device = "
					+ device);
			conn = dataSource.getConnection();

			try {
				log.info(PAYMENT135_STMT);
				cstmt = conn.prepareCall(PAYMENT135_STMT);
				cstmt.setLong(1, abonentId);
				cstmt.setDate(2, new Date(orderDate.getTime().getTime()));
				cstmt.setDouble(3, money);
				cstmt.setString(4, documentNo);
				cstmt.setString(5, netSource);
				cstmt.setString(6, payType);
				cstmt.setString(7, terminal);
				cstmt.setString(8, transNo);
				cstmt.setString(9, device);
				cstmt.execute();
				CommonOperations.commitConnection(conn);
				responce.setResponce(CommonsBeansConstants.OK);
				responce.setNote(CommonsBeansConstants.PAYMENT_SUCCESS);
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (UtilsException e) {
			log.error("Could not format date", e);
			throw new BPaymentException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR, e.getMessage(), e);
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BPaymentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}

		IBAbonent abonent = (IBAbonent) beanFactory.getBean(
				ComponentFactoryImpl.getBAbonentName(), IBAbonent.class);
		responce.setBalance(abonent.abonentBalance135(abonentId).getBalance());

		log.info("payment done");
		return responce;
	}

	public BPaymentResponce payment(String guid, double amount,
			Calendar currDate, String currency, String documentNo,
			String netSource, String subDivision, String payType,
			String terminal, String transNo) throws BPaymentException,
			BRegistrationException, BAbonentException {
		String[] ids = guid.split("-");
		log.debug("GUID = " + guid);
		log
				.debug("ACCOUNT = "
						+ (Long) converter.getObject(ids[1], long.class));
		return payment((Long) converter.getObject(ids[1], long.class), amount,
				currDate, currency, documentNo, netSource, subDivision,
				payType, terminal, transNo, null);
	}

	/**
	 * Возращает экземпляр компоненты BasicDataSource
	 * 
	 * @return пул содинений с базой
	 */
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Инициализирует компоненту экземпляром компоненты BasicDataSource
	 * 
	 * @param dataSource
	 */
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public FormatterFactory getFormatter() {
		return formatter;
	}

	public void setFormatter(FormatterFactory formatter) {
		this.formatter = formatter;
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

	public void errorPayment(String device, Calendar payDate,
			Calendar systemDate, String currency, String netSource,
			String subDivision, String terminal, String transNo, double amount,
			String status, String documentNo) throws BPaymentException {
		log.info("Error payment started ...");
		log.debug("phone = "+device+"; payDate = "+payDate.toString()+"; currency = "+currency+"; netSource = "+netSource+"; subDivision = "+subDivision+
				"; terminal = "+terminal+"; transNo = "+transNo+"; amount = "+amount+"; status = "+status+"; documentNo = "+documentNo);
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		String PAYMENT_FAILURES_STMT = "insert into db.PAYMENT_FAILURES(DEVICE,PAY_DATE,SYSTEM_DATE,CURRENCY,NET_SOURCE,SUBDIVISION,TERMINAL,TRANS_NO,AMOUNT,STATUS, DOCUMENT_NO) values(?,?,SYSDATE,?,?,?,?,?,?,?,?)";
		try {
			java.sql.Date uPayDate = new java.sql.Date(payDate.getTime()
					.getTime());

			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(PAYMENT_FAILURES_STMT);
			pstmt.setString(1, device);
			pstmt.setDate(2, uPayDate);
			pstmt.setString(3, currency);
			pstmt.setString(4, netSource);
			pstmt.setString(5, subDivision);
			pstmt.setString(6, terminal);
			pstmt.setString(7, transNo);
			pstmt.setDouble(8, amount);
			pstmt.setString(9, status);
			pstmt.setString(9, documentNo);
			ResultSet rs = pstmt.executeQuery();
			log.info("Error payment done");

		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BPaymentException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}

	}

}