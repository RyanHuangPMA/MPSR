package com.kohmiho.db;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.kohmiho.db.DBConfig.JDBCConnectionType;

public class ConnectDB {

	private static DBConfig config = new MyDBConfig();

	private static DataSource ds = null;

	static {
		if (config.getType() == JDBCConnectionType.JDBC) {
			try {
				Class.forName(config.getDriver());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} else if (config.getType() == JDBCConnectionType.DataSource) {

			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			ht.put(Context.PROVIDER_URL, "t3://" + config.getWebServerHost() + ":" + config.getWebServerPort());

			Context ctx = null;
			try {
				ctx = new InitialContext(ht);
				ds = (javax.sql.DataSource) ctx.lookup(config.getDataSourceName());
			} catch (NamingException e) {
				e.printStackTrace();
			} finally {
				if (ctx != null) {
					try {
						ctx.close();
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private Connection conn = null;

	public ConnectDB() throws SQLException {

		if (config.getType() == JDBCConnectionType.JDBC) {

			conn = DriverManager.getConnection(config.getURL(), config.getUserName(), config.getPassword());

		} else if (config.getType() == JDBCConnectionType.DataSource) {

			conn = ds.getConnection();
		}
	}

	public void close() throws SQLException {

		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * execute SQL query and return result
	 * 
	 * @param sql
	 * @param params
	 * @return use String[] to get query result from the array list
	 */
	public ArrayList<String[]> exeQuery(String sql, String[] params) throws SQLException {

		ArrayList<String[]> returnResult = new ArrayList<String[]>();

		PreparedStatement preStmt = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				preStmt.setString(i + 1, params[i]);
			}
		}
		ResultSet rs = preStmt.executeQuery();
		preStmt.clearParameters();

		String[] result = null;
		while (rs.next()) {
			result = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < result.length; i++) {
				result[i] = rs.getString(i + 1);
			}
			returnResult.add(result);
		}

		rs.close();
		preStmt.close();

		return returnResult;
	}

	public void exeProcedure(String sql, String[] params) throws SQLException {

		CallableStatement callStmt = conn.prepareCall(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				callStmt.setString(i + 1, params[i]);
			}
		}
		callStmt.execute();
		callStmt.clearParameters();

		callStmt.close();
	}

	/**
	 * execute SQL insert and update
	 * 
	 * @param sqls
	 *            String for each array list element
	 * @param params
	 *            String[] for each array list element
	 * @throws SQLException
	 */
	public void exeUpdate(ArrayList<String> sqls, ArrayList<String[]> params) throws SQLException {

		conn.setAutoCommit(false);

		for (int i = 0; i < sqls.size(); i++) {
			update(sqls.get(i), params.get(i));
		}

		conn.commit();
	}

	/**
	 * batch execute SQL insert and update
	 * 
	 * @param sql
	 * @param param
	 * @throws SQLException
	 */
	public void exeUpdate(String sql, String[] param) throws SQLException {

		conn.setAutoCommit(false);

		update(sql, param);

		conn.commit();
	}

	/**
	 * batch execute SQL insert and update
	 * 
	 * @param sqls
	 * @param params
	 * @throws SQLException
	 */
	public void exeUpdate(String sqls[], String[][] params) throws SQLException {

		conn.setAutoCommit(false);

		for (int i = 0; i < sqls.length; i++) {
			update(sqls[i], params[i]);

			// System.out.println(sqls[i]);
			// for(String str: params[i]){
			// System.out.println(str);
			// }

		}

		conn.commit();
		conn.setAutoCommit(true);
	}

	private void update(String sql, String[] param) throws SQLException {

		PreparedStatement preStmt = conn.prepareStatement(sql);

		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				preStmt.setString(i + 1, param[i]);
			}
		}

		preStmt.executeUpdate();
		preStmt.clearParameters();
		preStmt.close();
	}

	// blob field code

	public byte[] runGetBLOB(String sql, String[] param) {
		byte[] allBytesInBlob = null;
		try { // Prepare a Statement:
			PreparedStatement stmnt = conn.prepareStatement(sql);

			// Execute
			ResultSet rs = stmnt.executeQuery();

			while (rs.next()) {
				try {
					// Get as a BLOB
					Blob aBlob = rs.getBlob(1);
					allBytesInBlob = aBlob.getBytes(1, (int) aBlob.length());
					return (allBytesInBlob);
				} catch (Exception ex) {
					// The driver could not handle this as a BLOB...
					// Fallback to default (and slower) byte[] handling
					allBytesInBlob = rs.getBytes(1);
				}
			}

			// Close resources
			rs.close();
			stmnt.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return allBytesInBlob;
	}
}