/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BQueueIsOutImpl.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.outqueue;

import java.sql.Connection;
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

public class BQueueIsOutImpl implements IBQueueIsOut {

	private static Logger log = Logger.getLogger(BQueueIsOutImpl.class);

	private BasicDataSource dataSource = null;

	private long maxQueue = 0;

	public BQueueIsOutImpl() {
		super();
	}

	private static String IS_QUEUE_OUT_STMT = new StringBuffer().append(
			"select count(*) ").append("from aqapp.outbound_queue t ")
			.toString();

	public BQueueIsOutInfo isQueueOutByPhone(String phone)
			throws BQueueOutException {
		log.info("queue out started ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();

			pstmt = conn.prepareStatement(IS_QUEUE_OUT_STMT);

			ResultSet rs = pstmt.executeQuery();
			log.info("queue out done");
			if (rs.next() && rs.getLong(1) > 0) {
				return new BQueueIsOutInfo(rs.getLong(1), Calendar
						.getInstance().getTime(),
						CommonsBeansConstants.QUEUE_OUT_ERROR);
			} else {
				return new BQueueIsOutInfo(0, Calendar.getInstance().getTime(),
						CommonsBeansConstants.QUEUE_OUT_OK);
			}
		} catch (SQLException e) {
			log.error("Could not count queue", e);
			throw new BQueueOutException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.QUEUE_OUT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not check max queue", e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BQueueIsOutInfo isQueueOutByCode(String code)
			throws BQueueOutException {
		return isQueueOutByPhone(code);
	}

	public List<BQueueIsOutInfo> isQueueOut() throws BQueueOutException {
		List<BQueueIsOutInfo> list = new ArrayList<BQueueIsOutInfo>();
		return list;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public long getMaxQueue() {
		return maxQueue;
	}

	public void setMaxQueue(long maxQueue) {
		this.maxQueue = maxQueue;
	}

}
