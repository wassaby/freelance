package com.rs.vio.webapp.test.trash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLTest {

	public static void main(String args[]) {
		
		String dbURL = "jdbc:mysql://10.241.8.191:3306/vio?characterEncoding=UTF-8&amp;useUnicode=true";
		String username = "vio_dev";
		String password = "123";

		Connection dbCon = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "select * from account_usr";

		try {
			// getting database connection to MySQL server
			dbCon = DriverManager.getConnection(dbURL, username, password);

			// getting PreparedStatment to execute query
			stmt = dbCon.prepareStatement(query);

			// Resultset returned by query
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				String login = rs.getString(2);
				System.out.println("ID : " + id);
				System.out.println("Login : " + login);
			}

		} catch (SQLException ex) {
			System.out.println("Oops! " + ex.getMessage());
		} finally {
			// close connection ,stmt and resultset here
		}

	}

}
