package com.kohmiho.db;

public abstract class DBConfig {

	public enum JDBCConnectionType {
		JDBC, DataSource
	}

	protected JDBCConnectionType type;

	protected String webServerHost;
	protected String webServerPort;
	protected String dataSourceName;

	protected String dbURL;
	protected String dbDriver;
	protected String dbUserName;
	protected String dbPassword;

	public String getURL() {
		return dbURL;
	}

	public String getDriver() {
		return dbDriver;
	}

	public String getUserName() {
		return dbUserName;
	}

	public String getPassword() {
		return dbPassword;
	}

	public String getWebServerHost() {
		return webServerHost;
	}

	public String getWebServerPort() {
		return webServerPort;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public JDBCConnectionType getType() {
		return type;
	}

}
