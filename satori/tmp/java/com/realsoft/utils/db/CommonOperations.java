/*
 * Created on 16.03.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CommonOperations.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.pool.ObjectPool;
import org.apache.log4j.Logger;

/**
 * @author dimad
 * 
 */
public class CommonOperations {

	private static Logger log = Logger.getLogger(CommonOperations.class);

	private CommonOperations() {
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void commitConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void rollbackConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void freePoolableObject(ObjectPool pool, Object object) {
		try {
			if ((object != null) && (pool != null))
				pool.returnObject(object);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void closeOutputStream(OutputStream out) {
		try {
			if (out != null)
				out.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	public static void closeInputStream(InputStream in) {
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Boolean value) {
		try {
			if (value != null)
				cstmt.setBoolean(position, value.booleanValue());
			else
				cstmt.setNull(position, Types.BOOLEAN);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Byte value) {
		try {
			if (value != null)
				cstmt.setByte(position, value.byteValue());
			else
				cstmt.setNull(position, Types.NUMERIC);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Date value) {
		try {
			if (value != null)
				cstmt.setDate(position, new java.sql.Date(value.getTime()));
			else
				cstmt.setNull(position, Types.DATE);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Double value) {
		try {
			if (value != null)
				cstmt.setDouble(position, value.longValue());
			else
				cstmt.setNull(position, Types.NUMERIC);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Float value) {
		try {
			if (value != null)
				cstmt.setFloat(position, value.floatValue());
			else
				cstmt.setNull(position, Types.NUMERIC);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Long value) {
		try {
			if (value != null)
				cstmt.setLong(position, value.longValue());
			else
				cstmt.setNull(position, Types.NUMERIC);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setValue(CallableStatement cstmt, int position,
			Short value) {
		try {
			if (value != null)
				cstmt.setShort(position, value.shortValue());
			else
				cstmt.setNull(position, Types.NUMERIC);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setTimeValue(CallableStatement cstmt, int position,
			Date value) {
		try {
			if (value != null)
				cstmt.setTime(position, new Time(value.getTime()));
			else
				cstmt.setNull(position, Types.TIME);
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void setTimestampValue(CallableStatement cstmt, int position,
			Date value) {
		try {
			if (value != null)
				cstmt.setTimestamp(position, new Timestamp(value.getTime()));
			else
				cstmt.setNull(position, Types.TIMESTAMP);
		} catch (SQLException e) {
			log.error(e);
		}
	}

}
