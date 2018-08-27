package com.ibm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.ibm.util.PropertyClass;

public class DBConnection {
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Properties prop = PropertyClass.getProperty();
			
			String driver = prop.getProperty("driver");
			String url =  prop.getProperty("url");
			String user =  prop.getProperty("user");
			String pass =  prop.getProperty("pass");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			//System.out.println("Connection successful");
			return conn;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return conn;
	}
}
