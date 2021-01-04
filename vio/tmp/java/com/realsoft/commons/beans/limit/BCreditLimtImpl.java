package com.realsoft.commons.beans.limit;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BCreditLimtImpl implements IBCreditLimit {

	private static Logger log = Logger.getLogger(BCreditLimtImpl.class);

	private BasicDataSource dataSource = null;

	private static final String CREDIT_LIMIT_STMT = new StringBuffer().append(
			"select ct.id,").append("ct.contract,").append("ct.open_date,")
			.append("ct.close_date,").append("ct.min_debit,").append(
					"ct.provider_id ").append("from db.account_usr au, ")
			.append("db.link_type lt, ").append("db.contract ct ").append(
					"where au.id = ? ").append("and lt.id = au.link_type_id ")
			.append("and ct.abonent_id = lt.abonent_id").toString();

	public BCreditLimtImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.limit.IBCreditLimit#getCreditLimit(long)
	 */
	public BCreditLimitItem getCreditLimit(long accountId)
			throws BCreditLimitException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(CREDIT_LIMIT_STMT);
			pstmt.setLong(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new BCreditLimitItem(rs.getLong(1), rs.getString(2), rs
						.getTimestamp(3), rs.getTimestamp(4), (-1)
						* rs.getLong(5), rs.getLong(6));
			}
		} catch (SQLException e) {
			log.error("Could not get credit limit list", e);
			throw new BCreditLimitException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREDIT_LIMIT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get credit limit list", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BCreditLimitException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.CREDIT_LIMIT_ERROR,
				"No credit limits exist");
	}

	private static final String SET_CREDIT_LIMIT_CSTMT = "{ call db.pkg_abonent.set_contract( ?, ?, ?, ?, ?, ?, ?, ? ) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.limit.IBCreditLimit#putNewLimit(long,
	 *      long, long, java.lang.String, java.util.Date, java.util.Date,
	 *      double)
	 */
	public void putNewLimit(long contractId, long accountId, long providerId,
			String contract, Date openDate, Date closeDate, double newLimit)
			throws BCreditLimitException {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			log.debug("Setting new credit limit " + newLimit + " ...");
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(SET_CREDIT_LIMIT_CSTMT);
			cstmt.setLong(1, contractId);
			cstmt.setLong(2, accountId);
			cstmt.setLong(3, providerId);
			cstmt.setString(4, contract);
			cstmt.setDate(5, (openDate != null) ? new java.sql.Date(openDate
					.getTime()) : null);
			cstmt.setDate(6, (closeDate != null) ? new java.sql.Date(closeDate
					.getTime()) : null);
			cstmt.setDouble(7, -1 * newLimit);
			cstmt.setInt(8, 1);
			cstmt.execute();
			conn.commit();
			log.debug("Setting new credit limit done");
		} catch (SQLException e) {
			log.error("Could not set new credit limit", e);
			throw new BCreditLimitException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREDIT_LIMIT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not set new credit limit", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static String GET_ABONENT_CREDIT_LIMIT_STMT = new StringBuffer()
			.append("select ct.id,")
			.append("ct.contract,")
			.append("ct.open_date,")
			.append("ct.close_date,")
			.append("ct.min_debit,")
			.append("ct.provider_id ")
			.append("from db.contract ct ")
			.append(
					"where sysdate >= ct.open_date and sysdate < nvl(ct.close_date, sysdate+1) ")
			.append("and ct.provider_id = 1 ").append(
					"and ct.abonent_id = ?           ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.limit.IBCreditLimit#getCreditLimitByAbonentId(long)
	 */
	public BCreditLimitItem getCreditLimitByAbonentId(long abonentId)
			throws BCreditLimitException {

		Connection conn = null;

		PreparedStatement pstmt = null;
		try {
			log.debug("Getting abonent credit limit, abonentId= " + abonentId);
			conn = dataSource.getConnection();
			log.debug("GET_ABONENT_CREDIT_LIMIT_STMT = "
					+ GET_ABONENT_CREDIT_LIMIT_STMT);
			pstmt = conn.prepareStatement(GET_ABONENT_CREDIT_LIMIT_STMT);
			pstmt.setLong(1, abonentId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				log.debug("Getting abonent credir limit succesfuly done");
				return new BCreditLimitItem(rs.getLong(1), rs.getString(2), rs
						.getDate(3), rs.getDate(4), rs.getLong(5), rs
						.getLong(6));
			}
			throw new BCreditLimitException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREDIT_LIMIT_ERROR,

					"Credit limit for abonent " + abonentId + " not found");

		} catch (SQLException e) {
			log.error("Could not get credit limit for abonent", e);
			throw new BCreditLimitException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CREDIT_LIMIT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get credit limit for abonent ", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

}
