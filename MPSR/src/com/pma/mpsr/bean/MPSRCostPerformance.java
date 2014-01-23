package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRCostPerformance {

	private static final String SQL_SELECT_TABLE = "select FILE_NAME from MPSR_COST_PERF WHERE MPSR_ID=?";

	private String fileName;

	public static MPSRCostPerformance getInstance(String mpsrID) {

		MPSRCostPerformance bean = new MPSRCostPerformance();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setFileName(data[i++]);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bean;
	}

	public String getFileName() {
		return null != fileName ? fileName : "";
	}

	public void setFileName(String path) {
		this.fileName = path;
	}

}
