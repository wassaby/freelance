package com.rs.vio.webapp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.daurenzg.commons.beans.satori.item.BItemInfo;

public class MySQLTest {

	public static void main(String args[]) {
		
		String dbURL = "jdbc:mysql://localhost:3306/satori?characterEncoding=UTF-8&amp;useUnicode=true";
		String username = "satori_dev";
		String password = "123";

		Connection dbCon = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "select * from account_usr";
		
		final String GET_NOTIFICATIONS = new StringBuffer()
		.append("select * from content_article")
		.toString();
		
		ArrayList<BItemInfo> notificationItemList = new ArrayList<BItemInfo>();
		
		try {
			// getting database connection to MySQL server
			dbCon = DriverManager.getConnection(dbURL, username, password);
			// getting PreparedStatment to execute query
			stmt = dbCon.prepareStatement(GET_NOTIFICATIONS);
			// Resultset returned by query
			rs = stmt.executeQuery(GET_NOTIFICATIONS);
			
			while (rs.next()) {
				BItemInfo notItem2 = new BItemInfo();
				notItem2.setId(rs.getLong(1));
				notItem2.setText(rs.getString(2));
				notItem2.setPid(rs.getLong(3));
				notItem2.setFileId(rs.getLong(4));
				notificationItemList.add(notItem2);
			}
			
			for (Iterator iter = notificationItemList.iterator(); iter.hasNext();){
				BItemInfo notItem = (BItemInfo) iter.next();
				System.out.println("\nID " + notItem.getId());
				System.out.println("Text " + notItem.getText());
				System.out.println("Notification ID " + notItem.getPid());
				System.out.println("File ID " + notItem.getFileId());
			}

		} catch (SQLException ex) {
			System.out.println("Oops! " + ex.getMessage());
		} finally {
			// close connection ,stmt and resultset here
		}

	}

}
