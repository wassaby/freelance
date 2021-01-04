/*
 * Created on 23.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BInventoryImpl.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.inventory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

public class BInventoryImpl implements IBInventory {

	private static Logger log = Logger.getLogger(BInventoryImpl.class);

	private BasicDataSource dataSource = null;

	private IFormatter formatter = null;

	public BInventoryImpl() {
		super();
	}

	private static final String INVENTORY_CSTMT = "{? = call db.get_device_available_megaline( ?, ?, ?, ?) }";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.inventory.IBInventory#checkDeviceAvailable(java.lang.String)
	 */
	public String checkDeviceAvailable(String phone) throws BInventoryException {
		log.info("checking inventory for phone " + phone);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(INVENTORY_CSTMT);
			cstmt.setString(2, phone);
			cstmt.setNull(3, Types.NUMERIC);
			cstmt.setNull(4, Types.NUMERIC);
			cstmt.setNull(5, Types.NUMERIC);
			cstmt.registerOutParameter(1, Types.VARCHAR);

			cstmt.execute();

			return cstmt.getString(1);
		} catch (SQLException e) {
			log.error("Could not check account", e);
			throw new BInventoryException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.INVENTORY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not check inventory", e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static final String INVENTORY_WITH_DBLINK_CSTMT = "{? = call db.get_device_available_megaline@%s( ?, ?, ?, ?) }";

	public String checkDeviceAvailable(String phone, String dbLink)
			throws BInventoryException {
		log.info("checking inventory for phone " + phone);
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			String statement = formatter.format(INVENTORY_WITH_DBLINK_CSTMT,
					dbLink);
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall(statement);
			cstmt.setString(2, phone);
			cstmt.setNull(3, Types.NUMERIC);
			cstmt.setNull(4, Types.NUMERIC);
			cstmt.setNull(5, Types.NUMERIC);
			cstmt.registerOutParameter(1, Types.VARCHAR);

			cstmt.execute();

			return cstmt.getString(1);
		} catch (SQLException e) {
			log.error("Could not check device", e);
			throw new BInventoryException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.INVENTORY_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					"Could not check device", e);
		} catch (UtilsException e) {
			log.error("Could format inventory statement", e);
			throw new BInventoryException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.INVENTORY_ERROR,
					"Could not check device", e);
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

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	public IFormatter getFormatter() {
		return formatter;
	}

}
