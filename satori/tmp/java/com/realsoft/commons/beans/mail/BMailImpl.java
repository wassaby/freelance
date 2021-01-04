/*
 * Created on 16.11.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BMailImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.mail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.PasswordAuthentication;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.report.abonent.BReportException;
import com.realsoft.commons.beans.report.abonent.IBReport;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BMailImpl implements IBMail, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BMailImpl.class);

	private BeanFactory beanFactory = null;

	private BasicDataSource dataSource = null;
	
	private BasicDataSource dataSourceAmdocs = null;

	private String smtpServer = null;

	private String smtpUser = null;

	private String smtpPassword = null;

	private String mailSourceAddress = null;

	private String mailSourceName = null;

	private String encoding = null;

	public BMailImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendAccountingStatusMail(java.lang.String,
	 *      java.lang.String, java.lang.String, long)
	 */
	public BMailInfo sendAccountingStatusMail(String phone,
			String subjectMessage, String titleMessage, long abonentId)
			throws BMailException, BReportException {
		log.info("sending accounting status phone = " + phone
				+ "; subjectMessage = " + subjectMessage + "; titleMessage = "
				+ titleMessage + "; abonentId = " + abonentId);
		IBReport report = (IBReport) beanFactory.getBean(ComponentFactoryImpl
				.getBReportAbonentName(), IBReport.class);
		return new BMailInfo(getMailAddress13(abonentId), report
				.getAccountStatus(phone, abonentId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendDetailChargeMail(java.lang.String,
	 *      java.lang.String, java.lang.String, long, long, long)
	 */
	public BMailInfo sendDetailChargeMail(String phone, String subjectMessage,
			String titleMessage, long abonentId, long billTyte, long reportDate)
			throws BMailException, BReportException {
		log.info("sending detail charge mail phone = " + phone
				+ "; subjectMessage = " + subjectMessage + "; abonentId = "
				+ abonentId + "; billTyte = " + billTyte + "; reportDate = "
				+ reportDate);
		IBReport report = (IBReport) beanFactory.getBean(ComponentFactoryImpl
				.getBReportAbonentName(), IBReport.class);
		return new BMailInfo(getMailAddress13(abonentId), report
				.getDetailPayment(phone, abonentId, billTyte, reportDate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendDetailPaymentMail(java.lang.String,
	 *      java.lang.String, java.lang.String, long, long, long)
	 */
	public BMailInfo sendDetailPaymentMail(String phone, String subjectMessage,
			String titleMessage, long abonentId, long billTyte, long reportDate)
			throws BMailException, BReportException {
		log.info("sending detail payment mail phone = " + phone
				+ ", subjectMessage = " + subjectMessage + ", titleMessage = "
				+ titleMessage + ", abonentId = " + abonentId + ", billTyte = "
				+ billTyte + ", reportDate = " + reportDate);
		IBReport report = (IBReport) beanFactory.getBean(ComponentFactoryImpl
				.getBReportAbonentName(), IBReport.class);
		return new BMailInfo(getMailAddress13(abonentId), report
				.getDetailCharge(phone, abonentId, billTyte, reportDate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendMail13(java.lang.String,
	 *      long, java.lang.String, java.lang.String)
	 */
	public BMailInfo sendMail13(String phone, long abonentId,
			String subjectMessage, String textMessage) throws BMailException {
		log.info("sending mail phone = " + phone + ", abonentId = " + abonentId
				+ ", subjectMessage = " + subjectMessage + ", textMessage = "
				+ textMessage);
		return new BMailInfo(getMailAddress13(abonentId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendMail135(long,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendMail135(long abonentId, String subjectMessage,
			String textMessage) throws BMailException {
		log.info("sending mail135 abonentId = " + abonentId
				+ ", subjectMessage = " + subjectMessage + ", textMessage"
				+ textMessage);
		sendMail(getMailAddress13(abonentId).getEmailName(), subjectMessage,
				textMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendMail135(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendMail135(String login, String subjectMessage,
			String textMessage) throws BMailException {
		log.info("sending mail135 login = " + login + ", subjectMessage = "
				+ subjectMessage + ", textMessage = " + textMessage);
		String emailAddress = getMailAddress135(login).getEmailName();
		if (emailAddress.equals(""))
			throw new BMailException(CommonsBeansConstants.ERR_ACCOUNT,
					CommonsBeansConstants.MAIL_ERROR, "Email address is null");
		sendMail(emailAddress, subjectMessage, textMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendAllMail135(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendAllMail135(String login, String subjectMessage,
			String textMessage) throws BMailException {
		log.info("sending all mail135 login = " + login + " subjectMessage = "
				+ subjectMessage + " textMessage = " + textMessage);
		List<BMailAddressItem> emailAddresses = getAllMailAddress135(login);
		for (BMailAddressItem item : emailAddresses) {
			sendMail(item.getEmailName(), subjectMessage, textMessage);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#sendMail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendMail(final String email, final String subjectMessage,
			final String textMessage) throws BMailException {
		log.info("email = " + email + " subjectMessage = " + subjectMessage
				+ " textMessage = " + textMessage);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					MultiPartEmail multiPartEmail = new MultiPartEmail();
					multiPartEmail.setHostName(smtpServer);
					multiPartEmail.addTo(email);
					multiPartEmail.setFrom(mailSourceAddress, mailSourceName);
					multiPartEmail.setSubject(subjectMessage);
					multiPartEmail.setMsg(textMessage);
					multiPartEmail.setAuthentication(smtpUser, smtpPassword);

					multiPartEmail.send();

				} catch (Exception e) {
					log.error("Could not send email", e);
				}
			}
		});
		thread.start();
	}

	private class Security extends javax.mail.Authenticator {
		private String user;

		private String password;

		public Security(String user, String password) {
			super();
			this.user = user;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}

	}

	private static final String EMAIL_ADDRESS_135_STMT = new StringBuffer()
			.append("select t.id, t.email ")
			.append("from db.contact_person t ").append(
					"where t.abonent_id = ? ").append(
					" and t.contact_person_typ_id = 0 ").append(
					"and rownum = 1").toString();

	public BMailAddressItem getMailAddress135(long abonentId)
			throws BMailException {
		log.info("getting mail address abonentId = " + abonentId);
		try {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				log.info("EMAIL_ADDRESS_135_STMT = " + EMAIL_ADDRESS_135_STMT);
				pstmt = conn.prepareStatement(EMAIL_ADDRESS_135_STMT);
				pstmt.setLong(1, abonentId);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next() && rs.getString(1) != null) {
					return new BMailAddressItem(rs.getLong(1), rs.getString(1));
				} else {
					throw new BMailException(CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.MAIL_ERROR,
							"No email address for this abonent " + abonentId);
				}
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get email address", e);
		}
	}

	private static String EMAIL_ADDRESS_LOGIN_135 = new StringBuffer().append(
			"select t.id, ").append("t.email  ").append(
			"from db.contact_person t,  ").append("db.account_usr au,  ")
			.append("db.link_type lt  ").append(
					"where au.link_type_id = lt.id  ").append(
					"and t.abonent_id = lt.abonent_id  ").append(
					"and t.contact_person_typ_id = 0 ").append(
					"and sysdate < nvl (au.to_date, sysdate + 1) ").append(
					"and au.login = ? ").append("and rownum=1").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#getMailAddress135(java.lang.String)
	 */
	public BMailAddressItem getMailAddress135(String login)
			throws BMailException {
		try {
			log.info("getting mail address login = " + login + " ...");
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				log
						.info("EMAIL_ADDRESS_LOGIN_135 = "
								+ EMAIL_ADDRESS_LOGIN_135);
				pstmt = conn.prepareStatement(EMAIL_ADDRESS_LOGIN_135);
				pstmt.setString(1, login);
				ResultSet rs = pstmt.executeQuery();
				BMailAddressItem addressItem = new BMailAddressItem();
				if (rs.next() && rs.getString(1) != null) {
					addressItem.setEmailId(rs.getLong(1));
					addressItem.setEmailName(rs.getString(2));
					return addressItem;
				}
				throw new BMailException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.MAIL_ERROR,
						"There are no any email's for login :\"" + login + "\"");
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not execute email statement", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not execute email statement", e);
		}
	}

	private static String EMAIL_ALL_ADDRESS_LOGIN_135 = new StringBuffer()
			.append("select t.id, t.email ").append(
					"from db.contact_person t, ").append("db.account_usr au, ")
			.append("db.link_type lt ")
			.append("where au.link_type_id = lt.id ").append(
					"and t.contact_person_typ_id = 0 ").append(
					"and t.abonent_id = lt.abonent_id ").append(
					"and au.login = ? ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#getAllMailAddress135(java.lang.String)
	 */
	public List<BMailAddressItem> getAllMailAddress135(String login)
			throws BMailException {
		log.info("getting all mail address login = " + login);
		try {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				log.info("EMAIL_ALL_ADDRESS_LOGIN_135 = "
						+ EMAIL_ALL_ADDRESS_LOGIN_135);
				pstmt = conn.prepareStatement(EMAIL_ALL_ADDRESS_LOGIN_135);
				pstmt.setString(1, login);
				ResultSet rs = pstmt.executeQuery();
				List<BMailAddressItem> mailAddresses = new ArrayList<BMailAddressItem>();
				while (rs.next() && rs.getString(1) != null) {
					mailAddresses.add(new BMailAddressItem(rs.getLong(1), rs
							.getString(2)));
				}
				if (mailAddresses.size() == 0)
					throw new BMailException(CommonsBeansConstants.ERR_SYSTEM,
							CommonsBeansConstants.MAIL_ERROR,
							"No mail found for " + login);
				return mailAddresses;
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not execute email statement", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not execute email statement", e);
		}
	}

	private static final String SET_EMAIL_PSTMT = "{ call db.pkg_abonent.set_contact_person( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#setEmail(long,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setEmail135(long abonentId, String login, String email,
			String note) throws BMailException {
		log.info("setting email for abonent login = " + login
				+ "; abonentId = " + abonentId + " ...");
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = dataSource.getConnection();

			long contactPersonId = getMailAddress135(login).getEmailId();

			log.info("contactPersonId = " + contactPersonId + "; login = "
					+ login + "; abonentId = " + abonentId + "; email = "
					+ email);
			log.info("SET_EMAIL_PSTMT = " + SET_EMAIL_PSTMT);
			cstmt = conn.prepareCall(SET_EMAIL_PSTMT);
			cstmt.setLong(1, contactPersonId);
			cstmt.setString(2, login);
			cstmt.setLong(3, 0);
			cstmt.setLong(4, abonentId);
			cstmt.setString(5, "");
			cstmt.setString(6, email);
			cstmt.setString(7, note);
			cstmt.setLong(8, 0);
			cstmt.setString(9, "");
			cstmt.setString(10, "");
			cstmt.setDate(11, new Date(Calendar.getInstance().getTime()
					.getTime()));
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			log.debug("Creating account done");
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not set email", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SET_EMAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not set email", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String SET_EMAIL_CSTMT = "{ call un.pkg_account_usr.set_email(?, ?)}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#setEmail135(long,
	 *      java.lang.String)
	 */
	public void setEmail135(long webAccountId, String email)
			throws BMailException {
		log.info("setting email for abonent webAccountId = " + webAccountId
				+ "; email = " + email + " ...");
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = dataSourceAmdocs.getConnection();

			log.info("SET_EMAIL_CSTMT = " + SET_EMAIL_CSTMT);
			cstmt = conn.prepareCall(SET_EMAIL_CSTMT);
			cstmt.setString(1, email);
			cstmt.setLong(2, webAccountId);
			cstmt.execute();
			CommonOperations.commitConnection(conn);
			log.debug("Seting email for abonent webAccountId = " + webAccountId
				+ "; email = " + email + " done");
		} catch (SQLException e) {
			CommonOperations.rollbackConnection(conn);
			log.error("Could not set email for abonent webAccountId = " + webAccountId
				+ "; email = " + email, e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SET_EMAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not set email", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String GET_ABONENT_ID_BY_LOGIN_STMT = new StringBuffer()
			.append("select lt.abonent_id ").append("from db.account_usr au, ")
			.append("db.link_type lt ")
			.append("where au.link_type_id = lt.id ").append(
					"and au.login = ? ").append("and rownum = 1").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.mail.IBMail#setEmail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void setEmail135(String login, String email, String note)
			throws BMailException {
		log.info("setting email for abonent " + login + " ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.info("SET_EMAIL_LOGIN_PSTMT = " + GET_ABONENT_ID_BY_LOGIN_STMT);
			pstmt = conn.prepareStatement(GET_ABONENT_ID_BY_LOGIN_STMT);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("setting email for login = " + login + "; abonent = "
						+ rs.getLong(1));
				setEmail135(rs.getLong(1), login, email, note);
				log.debug("Setting email by login done");
				return;
			}
			throw new BMailException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SET_EMAIL_ERROR,
					"No such abonent with login = " + login);
		} catch (SQLException e) {
			log.error("Could not set email", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.SET_EMAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not set email", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String EMAIL_ADDRESS_13_STMT = new StringBuffer()
			.append(
					"select v.DEVICE_ID, device, DEVICE_GROUP_ID, CONNECT_TYPE_ID, DEV_TOWN_ID ")
			.append("from db.view_all_real v ").append(
					"where v.ABONENT_ID = ? ").append(
					"and v.DEVICE_GROUP_ID = (select device_group_id ").append(
					"from db.bill_analyze_device_group ").append(
					"where device_handler_id = 1 ").append("and can_send = 1)")
			.toString();

	public BMailAddressItem getMailAddress13(long abonentId)
			throws BMailException {
		log.info("getting mail address13 abonentId = " + abonentId);
		try {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				log.info("EMAIL_ADDRESS_13_STMT = " + EMAIL_ADDRESS_13_STMT);
				pstmt = conn.prepareStatement(EMAIL_ADDRESS_13_STMT);
				pstmt.setLong(1, abonentId);
				ResultSet rs = pstmt.executeQuery();
				BMailAddressItem result = new BMailAddressItem();
				if (rs.next()) {
					result.setEmailId(rs.getLong(1));
					result.setEmailName(rs.getString(2));
				}
				return result;
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		}
	}

	public List<BMailAddressItem> getAllMailAddress13(long abonentId)
			throws BMailException {
		log.info("getting all mail addresses13 for abonentId = " + abonentId);
		try {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				log.info("EMAIL_ADDRESS_13_STMT = " + EMAIL_ADDRESS_13_STMT);
				pstmt = conn.prepareStatement(EMAIL_ADDRESS_13_STMT);
				pstmt.setLong(1, abonentId);
				ResultSet rs = pstmt.executeQuery();
				List<BMailAddressItem> result = new ArrayList<BMailAddressItem>();
				while (rs.next()) {
					result.add(new BMailAddressItem(rs.getLong(1), rs
							.getString(2)));
				}
				return result;
			} finally {
				CommonOperations.closeStatement(pstmt);
				CommonOperations.closeConnection(conn);
			}
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BMailException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MAIL_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		}
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getMailSourceAddress() {
		return mailSourceAddress;
	}

	public void setMailSourceAddress(String mailSourceAddress) {
		this.mailSourceAddress = mailSourceAddress;
	}

	public String getMailSourceName() {
		return mailSourceName;
	}

	public void setMailSourceName(String mailSourceName) {
		this.mailSourceName = mailSourceName;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public BasicDataSource getDataSourceAmdocs() {
		return dataSourceAmdocs;
	}

	public void setDataSourceAmdocs(BasicDataSource dataSourceAmdocs) {
		this.dataSourceAmdocs = dataSourceAmdocs;
	}

}
