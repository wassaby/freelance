package com.realsoft.commons.beans.bpel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BBpel implements IBBpel {
	
	private static Logger log = Logger.getLogger(BBpel.class);

	private BasicDataSource dataSource = null;
	
	public BBpel() {
		super();
	}
	
	private static String SEND_CSTMT = "aqapp.pkg_bpel.Send_order_to_destination_async ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public BBpelResponce sendOrderToDestination(BBpelRequest request) throws BBpelException {
		log.debug("sending order request = " + request.toString());

		log.debug("SEND_CSTMT = "+SEND_CSTMT);
		
		CallableStatement cstmt = null;
		Connection conn = null;
		BBpelResponce responce = new BBpelResponce();
		try {
			conn = dataSource.getConnection();
			try {
				cstmt = conn.prepareCall(SEND_CSTMT);
				cstmt.setLong(1, request.getServerId());
				cstmt.setLong(2, request.getOrderId());
				cstmt.setString(3, request.getNote());
				cstmt.setLong(4, request.getUserId());
				cstmt.setString(5, request.getDestination());
				cstmt.setString(6, request.getSource());
				cstmt.setString(7, request.getMessageId());
				cstmt.setString(8, request.getRelatesTo());
				cstmt.registerOutParameter(9, Types.NUMERIC);
				cstmt.registerOutParameter(10, Types.NUMERIC);
				cstmt.registerOutParameter(11, Types.NUMERIC);
				cstmt.registerOutParameter(12, Types.VARCHAR);
				cstmt.registerOutParameter(13, Types.VARCHAR);
				cstmt.execute();
								
				responce.setPackId(cstmt.getLong(9));
				responce.setServiceCenterTypeId(cstmt.getLong(10));
				responce.setServiceCenterId(cstmt.getLong(11));
				responce.setErrCode(cstmt.getString(12));
				responce.setMessage(cstmt.getString(13));
			} finally {
				CommonOperations.closeStatement(cstmt);
			}
		} catch (SQLException e) {
			log.error("Could not execute statement", e);
			throw new BBpelException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.PAYMENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}

		return responce;
	}


}
