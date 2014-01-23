package com.pma.db;

import java.util.ResourceBundle;


public class DB_PMA extends ADBConfig {

	public DB_PMA() {
		ResourceBundle bundle = ResourceBundle.getBundle(DatabaseBundle.class.getName());
		type = JDBCConnectionType.JDBC;

		dbURL = bundle.getString("dbURL");
		dbDriver = bundle.getString("dbDriver");
		dbUserName = bundle.getString("dbUserName");
		dbPassword = bundle.getString("dbPassword");

	}
}
