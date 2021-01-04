/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BIsAliveImpl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.isalive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BIsAliveImpl implements IBIsAlive {

	private static Logger log = Logger.getLogger(BIsAliveImpl.class);

	private BasicDataSource dataSource = null;

	public BIsAliveImpl() {
		super();
	}

	private static String IS_ALIVE_STMT = "select 1 from dual";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.isalive.IBIsAlive#isAliveByPhone(java.lang.String)
	 */
	public BIsAliveInfo isAliveByPhone(String phone) {
		log.info("isAlive started ...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();

			pstmt = conn.prepareStatement(IS_ALIVE_STMT);

			ResultSet rs = pstmt.executeQuery();

			log.info("isAlive done");
			return new BIsAliveInfo(true,
					CommonsBeansConstants.IS_ALIVE_DATABASE_ALIVE, phone);
		} catch (SQLException e) {
			log.error("Database is not alive", e);
			return new BIsAliveInfo(
					false,
					CommonsBeansConstants.IS_ALIVE_DATABASE_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), phone);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.isalive.IBIsAlive#isAliveByCode(java.lang.String)
	 */
	public BIsAliveInfo isAliveByCode(String code) {
		return isAliveByPhone(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.isalive.IBIsAlive#isAlive()
	 */
	public BIsAliveInfo[] isAlive() {
		return new BIsAliveInfo[] { new BIsAliveInfo(true, "Test url is alive",
				"test url") };
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
