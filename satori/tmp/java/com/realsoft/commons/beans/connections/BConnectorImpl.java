package com.realsoft.commons.beans.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BConnectorImpl implements IBConnector {

	private static Logger log = Logger.getLogger(BConnectorImpl.class);

	private BasicDataSource dataSource = null;

	public BConnectorImpl() {
		super();
	}

	private static String PRIVATE_BILL_STATE_STMT = new StringBuilder()
			.append("select sum(s.out_money), ")
			.append(
					"sum(nvl(s.debit, 0) + nvl(change_in_debit, 0) + nvl(change_debit, 0)), ")
			.append(
					"sum(nvl(s.credit, 0) + nvl(change_in_credit, 0) + nvl(change_credit, 0)) ")
			.append("from db.saldo s, ").append("db.bill_type bt ").append(
					"where s.abonent_id = ? ").append(
					"and s.bill_type_id = bt.id ").append(
					"and bt.provider_id = ? ").append(
					"and s.report_date_id = (select rd.id ").append(
					"from db.report_date rd ").append(
					"where rd.from_date <= sysdate ").append(
					"and rd.to_date > sysdate)").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.connections.IBConnector#getBillState(long,
	 *      long)
	 */
	public Double[] getBillState(long abonentId, long providerId)
			throws BConnectorException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting bill state ... for abonent " + abonentId
					+ "; providerId = " + providerId);
			conn = dataSource.getConnection();
			log.debug(PRIVATE_BILL_STATE_STMT);
			pstmt = conn.prepareStatement(PRIVATE_BILL_STATE_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, providerId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting bill state done");
				return new Double[] { new Double(rs.getDouble(1)),
						new Double(rs.getDouble(2)),
						new Double(rs.getDouble(3)) };
			}
		} catch (SQLException e) {
			log.error("Could not get bill state for abonent " + abonentId, e);
			throw new BConnectorException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get bill state for abonent " + abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BConnectorException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.DATE_ERROR, "No bill state for abonent "
						+ abonentId);
	}

	private static String PREVIOUS_PAYMENT_STMT = new StringBuilder()
			.append(
					"select sum(nvl(s.credit, 0) + nvl(change_in_credit, 0) + nvl(change_credit, 0)) ")
			.append("from db.saldo s, ")
			.append("db.bill_type bt ")
			.append("where s.abonent_id = ? ")
			.append("and s.bill_type_id = bt.id ")
			.append("and bt.provider_id = ? ")
			.append("and s.report_date_id = (select rd1.id ")
			.append("from db.report_date rd, ")
			.append("db.report_date rd1 ")
			.append(
					"where rd.id = (select rd.id from db.report_date rd where rd.from_date <= sysdate and rd.to_date > sysdate) ")
			.append("and rd1.from_date <= rd.from_date - 15 ").append(
					"and rd1.to_date > rd.from_date - 15)").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.connections.IBConnector#getPreviousPayment(long,
	 *      long)
	 */
	public Double getPreviousPayment(long abonentId, long providerId)
			throws BConnectorException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting bill previous state ... for abonent "
					+ abonentId + "; providerId = " + providerId);
			conn = dataSource.getConnection();
			log.debug(PREVIOUS_PAYMENT_STMT);
			pstmt = conn.prepareStatement(PREVIOUS_PAYMENT_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, providerId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting previous state done");
				return new Double(rs.getDouble(1));
			}
		} catch (SQLException e) {
			log.error("Could not get previous state for abonent " + abonentId,
					e);
			throw new BConnectorException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get previous state for abonent " + abonentId, e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BConnectorException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.DATE_ERROR,
				"No previous state for abonent " + abonentId);
	}

	private static String CURRENCY_STMT = new StringBuilder().append(
			"select code from tr.currency_type where id = 0").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.connections.IBConnector#getCurrency()
	 */
	public String getCurrency() throws BConnectorException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			log.debug("Getting current currency");
			conn = dataSource.getConnection();
			log.debug(CURRENCY_STMT);
			pstmt = conn.prepareStatement(CURRENCY_STMT);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				log.debug("Getting previous state done");
				return rs.getString(1);
			}
		} catch (SQLException e) {
			log.error("Could not get current currency", e);
			throw new BConnectorException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get current currency", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		throw new BConnectorException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.DATE_ERROR, "No current currency");
	}

	private static String CONNECTIONS_STMT = new StringBuffer()
			.append("select distinct d.id, ")
			.append("d.device, ")
			.append("pt.name, ")
			.append("l.note ")
			.append("from db.link        l, ")
			.append("db.device      d, ")
			.append("tr.packet_type pt, ")
			.append("db.link_type   lt, ")
			.append("db.account_usr a, ")
			.append("db.abonent ab ")
			.append("where lt.abonent_id = ? ")
			.append("and pt.provider_id = ? ")
			.append("and l.link_type_id = lt.id ")
			.append("and d.id = lt.device_id ")
			.append("and pt.id = l.packet_type_id ")
			.append("and a.account_usr_type_id(+) = 2 ")
			.append("and decode(ab.web_interface_type_id, 0, 0, 1, ?, 2, ?) = ")
			.append("decode(ab.web_interface_type_id, 0, 0, 1, a.id, 2, a.id) ")
			.append("and a.link_type_id(+) = lt.id ").append(
					"and ab.id = lt.abonent_id ").append(
					"and sysdate >= l.date_from ").append(
					"and sysdate < nvl(l.date_to, sysdate + 1) ").append(
					"order by device ").toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.connections.IBConnector#getConnections(long,
	 *      long, long)
	 */
	public List<BConnectorItem> getConnections(long abonentId, long providerId,
			long accountId) throws BConnectorException {
		log
				.debug("Getting connection list abonentId = " + abonentId
						+ "; providerId = " + providerId + "; accountId = "
						+ accountId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.debug(CONNECTIONS_STMT);
			pstmt = conn.prepareStatement(CONNECTIONS_STMT);
			pstmt.setLong(1, abonentId);
			pstmt.setLong(2, providerId);
			pstmt.setLong(3, accountId);
			pstmt.setLong(4, accountId);
			ResultSet rs = pstmt.executeQuery();
			List<BConnectorItem> connectorList = new ArrayList<BConnectorItem>();
			while (rs.next()) {
				BConnectorItem item = new BConnectorItem(rs.getLong(1), rs
						.getString(2), rs.getString(3), rs.getString(4));
				connectorList.add(item);
			}
			return connectorList;
		} catch (SQLException e) {
			log.error("Could not get connector list", e);
			throw new BConnectorException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.DATE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not get connector list", e);
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