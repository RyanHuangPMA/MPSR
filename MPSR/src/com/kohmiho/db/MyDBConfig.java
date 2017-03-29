package com.kohmiho.db;

import java.util.ResourceBundle;


public class MyDBConfig extends DBConfig {

	public MyDBConfig() {
		ResourceBundle bundle = ResourceBundle.getBundle(DatabaseBundle.class.getName());
		type = JDBCConnectionType.JDBC;

		dbURL = bundle.getString("dbURL");
		dbDriver = bundle.getString("dbDriver");
		dbUserName = bundle.getString("dbUserName");
		dbPassword = bundle.getString("dbPassword");

	}
}
