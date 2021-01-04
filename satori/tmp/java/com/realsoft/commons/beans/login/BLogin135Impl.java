/*
 * Created on 01.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BLogin135Impl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.control.IBModelPanel;
import com.realsoft.utils.db.CommonOperations;

public class BLogin135Impl implements IBLogin {

	private static Logger log = Logger.getLogger(BLogin135Impl.class);

	private BasicDataSource dataSource = null;

	public BLogin135Impl() {
		super();
	}

	private static final String LOGIN_INFO_STMT = new StringBuffer().append(
			"select a.id, ").append("a.name, ").append("t.name town, ").append(
			"pt.name tariff_plan, ").append("lt.id lt_id ").append(
			"from db.abonent a, ").append("db.link_type lt, ").append(
			"db.town t, ").append("db.link l, ").append("tr.packet_type pt, ")
			.append("db.account_usr au ").append("where au.id = ? ").append(
					"and au.link_type_id = lt.id ").append(
					"and lt.abonent_id = a.id ")
			.append("and t.id = a.town_id ").append(
					"and l.link_type_id = lt.id ").append(
					"and pt.id = l.packet_type_id ").append(
					"and sysdate >= l.date_from ").append(
					"and sysdate < nvl(l.date_to , sysdate +1)").toString();

	private static String LOGIN_STMT = new StringBuffer()
			.append(
					"select t.id, t.link_type_id, nvl(ptp.change_limit, 0) change_limit, nvl(ptp.change_packet_type, 0) change_packet_type ")
			.append(
					"from db.account_usr t, un.packet_type_params ptp where t.login = ? and t.account_usr_type_id = 2 ")
			.append(
					"and ptp.packet_type_id (+)  = un.pkg_order_account_packet.get_current_packet_type(t.id) ")
			.append(
					"and sysdate >= t.from_date and sysdate < nvl(t.to_date + %s, sysdate + 1) and decode(t.crypt_type_id, 0, ")
			.append(
					"safety.pkg_usr.encrypt(?), 1, safety.pkg_usr.md5(?)) = t.password")
			.toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#login(java.lang.String,
	 *      java.lang.String, long)
	 */
	public BLoginInfo login(String userName, String password, long toDateSum)
			throws BLoginException {
		Connection conn = null;
		log.debug("Logining userName = " + userName);
		try {
			PreparedStatement pstmt = null;
			try {
				log.debug("try to get connection...");
				conn = dataSource.getConnection();
				log.debug("connection getted successfully");
				Formatter formatter = new Formatter().format(LOGIN_STMT,
						toDateSum);
				log.debug("formater created successfully");
				String loginStmt = formatter.toString();
				formatter.close();
				log.debug("loginStmt = " + loginStmt);
				log.debug("User = " + userName);
				log.debug("Password = " + password);
				pstmt = conn.prepareStatement(loginStmt);
				pstmt.setString(1, userName);
				pstmt.setString(2, password);
				pstmt.setString(3, password);
				log.debug("trying to execute loginStmt = " + loginStmt);
				ResultSet resultSet = pstmt.executeQuery();
				log.debug("execute of " + loginStmt + " success");
				if (resultSet.next()) {
					log.debug("Logining done");
					BLoginInfo loginInfo = new BLoginInfo(userName, password);
					loginInfo.setAccountId(resultSet.getLong(1));
					loginInfo.setLinkTypeId(resultSet.getLong(2));
					loginInfo
							.setCanChangeCreditLimit(resultSet.getLong(3) == 0);
					loginInfo.setCanChangeTariffPlan(resultSet.getLong(4) == 0);
					return loginInfo;
				}
				log.debug("Account " + userName + " not found");
				throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.LOGIN_USERNAME_ERROR,
						"No such account found or there are no active Web Accounts "
								+ userName);
			} finally {
				CommonOperations.closeStatement(pstmt);
			}
		} catch (SQLException e) {
			log.error("Could not login user " + userName, e);
			if (e.getMessage().indexOf(
					"The Network Adapter could not establish the connection") != 0)
				throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
						CommonsBeansConstants.DATABASE_ERROR, e.getMessage(), e);
			throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
					CommonsBeansConstants.LOGIN_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#loginMonitoringClient(com.realsoft.commons.beans.control.IBModelPanel)
	 */

	public IBModelPanel loginMonitoringClient(IBModelPanel rootPanel)
			throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_SYSTEM,
				"current.nethod.not.available",
				"Not available in this implementation");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#login(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public BLoginInfo login(String userName, String password, String serviceName)
			throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_ACCOUNT,
				CommonsBeansConstants.LOGIN_ERROR, "Illegal method call");
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
