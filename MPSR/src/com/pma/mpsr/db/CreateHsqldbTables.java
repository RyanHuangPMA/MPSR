/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package com.pma.mpsr.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.pma.db.ConnectDB;

/**
 * Creates the hsqldatabase using SQL scripts containing CREATE statements with
 * the table definitions, and INSERT statements with the data.
 */
public class CreateHsqldbTables {

	/**
	 * Imports a number of SQL scripts into an HSQLDB database.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws SQLException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws SQLException, UnsupportedEncodingException, IOException {

		ConnectDB conn = new ConnectDB();
		conn.exeUpdate("SET IGNORECASE TRUE", null);

		runSQL(conn, "resources/scripts/MPSR_HSQLDB.sql");
		runSQL(conn, "resources/scripts/MPSR.sql");
		runSQL(conn, "resources/scripts/MPSR_HSQLDB_TRIGGER.sql");

		conn.close();
	}

	private static void runSQL(ConnectDB conn, String fileName) throws FileNotFoundException, IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader in;
		String line;
		in = new BufferedReader(new FileReader(fileName));
		while ((line = in.readLine()) != null) {

			sb.append(line);

			if (line.endsWith("!")) {
				String sql = sb.toString().substring(0, sb.length() - 1);
				System.out.println(sql);

				try {
					conn.exeUpdate(sql, null);
				} catch (Exception e) {
					System.err.println(sql);
					e.printStackTrace();
				}

				sb = new StringBuilder();
			}

		}
		in.close();
	}
}
