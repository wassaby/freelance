/*
 * Created on 07.11.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: OracleJob.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.utils.scheduler.jobs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.realsoft.commons.utils14.RealsoftConstants;
import com.realsoft.commons.utils14.RealsoftException;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.formatter.FormatterImpl;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.string.StringUtils;

public class OracleJob implements IJob, StatefulJob {

	private static Logger log = Logger.getLogger(OracleJob.class);

	private BasicDataSource dataSource = null;

	private BasicDataSource dataSourceLog = null;

	private Connection conn = null;
	private Connection logConn = null;

	private String statement = null;

	private String sql = null;

	private String logQuery = null;

	private IFormatter formatter = null;

	private final Random random = new Random();

	private int procLength = 127;

	private int messLength = 255;

	public OracleJob() {
		super();
	}

	public void initialize() throws SQLException {
		try {
			formatter.register(Date.class, new FormatterImpl(
					"%td.%<tm.%<tY %<tH:%<tM:%<tS"));
		} catch (UtilsException e) {
			log.error("Could not initialize formatter", e);
		}
		try {
			log.info("curr date:\n"
					+ formatter.format(new Date(System.currentTimeMillis())));
		} catch (UtilsException e) {
			log.error("Could not get date formated", e);
		}
		statement = StringUtils.normalizeStringToTrimNull(statement);
		logQuery = StringUtils.normalizeStringToTrimNull(logQuery);
		sql = "begin\n\t" + (statement == null ? "null;" : statement)
				+ "\nend;";
		log.info("sql:\n" + sql);
		log.info("logQuery:\n" + logQuery);
	}

	public void destroy() {
		CommonOperations.closeConnection(conn);
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.debug("Executing job in not concurent fashion");
		try {
			execute();
		} catch (RealsoftException e) {
			log.error("Could not execute job in concurent fashion", e);
			throw new JobExecutionException(
					"Could not execute job in concurent fashion", e);
		}
	}

	public void execute() throws RealsoftException {
		long messageId = (long) (random.nextDouble() * 9999999999L);
		try {
			conn = dataSource.getConnection();
			CallableStatement cstmt = null;
			try {
				log.info("executing JOB "
						+ statement
						+ " at "
						+ formatter
								.format(new Date(System.currentTimeMillis()))
						+ " ...");
			} catch (UtilsException e) {
				log.error("Could not get date formated", e);
			}
			insertLog("executing job ...", 0, messageId);
			try {
				cstmt = conn.prepareCall(sql);
				cstmt.execute();
			} finally {
				CommonOperations.closeStatement(cstmt);
				cstmt = null;
				CommonOperations.closeConnection(conn);
				conn = null;
			}
			try {
				log.info("executing JOB "
						+ statement
						+ " DONE at "
						+ formatter
								.format(new Date(System.currentTimeMillis())));
			} catch (UtilsException e) {
				log.error("Could not get date formated", e);
			}
			insertLog("executing job done", 1, messageId);
		} catch (SQLException e) {
			try {
				log.fatal("Could NOT execute statement "
						+ statement
						+ " at "
						+ formatter
								.format(new Date(System.currentTimeMillis())),
						e);
			} catch (UtilsException e1) {
				log.error("Could not get date formated", e);
			}
			insertLog(e.getMessage(), -Math.abs(e.getErrorCode()), messageId);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"commons-utils.OracleJob.execute.error",
					new Object[] { statement }, "Could not execute job", e);
		}
	}

	private void insertLog(String message, int result, long messageId) {
		try {
			PreparedStatement stmt = null;
			logConn = dataSourceLog.getConnection();
			try {
				stmt = logConn.prepareStatement(logQuery);
				stmt.setTimestamp(1, new java.sql.Timestamp(System
						.currentTimeMillis()));
				stmt.setString(2, messageLength(fillZero(Long
						.toString(messageId), 10)
						+ "-" + statement, procLength));
				stmt.setInt(3, result);
				stmt.setString(4, messageLength(message, messLength));
				stmt.execute();
				CommonOperations.commitConnection(logConn);
			} finally {
				CommonOperations.closeStatement(stmt);
				CommonOperations.closeConnection(logConn);
			}
		} catch (Exception e) {
			log.error("Could not insert log", e);
		}
	}

	private String messageLength(String mess, int length) {
		String m = mess;
		if (m != null && (m = m.trim()).length() > 0 && m.length() > length) {
			m = m.substring(0, length);
		}
		return m;
	}

	private String fillZero(String mess, int len) {
		String m = mess;
		if (m != null && (m = m.trim()).length() >= 0) {
			while (m.length() < len) {
				m = "0" + m;
			}
		}
		return m;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public BasicDataSource getDataSourceLog() {
		return dataSourceLog;
	}

	public void setDataSourceLog(BasicDataSource dataSourceLog) {
		this.dataSourceLog = dataSourceLog;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getLogQuery() {
		return logQuery;
	}

	public void setLogQuery(String logQuery) {
		this.logQuery = logQuery;
	}

	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	public int getProcLength() {
		return procLength;
	}

	public void setProcLength(int procLength) {
		this.procLength = procLength;
	}

	public int getMessLength() {
		return messLength;
	}

	public void setMessLength(int messLength) {
		this.messLength = messLength;
	}

}
