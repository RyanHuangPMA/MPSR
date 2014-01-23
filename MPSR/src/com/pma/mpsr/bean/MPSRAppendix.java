package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRAppendix {

	private static final String SQL_SELECT_TABLE = "select Num, Title, File_Name, DESC_1 from MPSR_APDX WHERE MPSR_ID=? order by Num";

	private String number;
	private String title;
	private String fileName;
	private String description;

	public static MPSRAppendix[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRAppendix[] beans = new MPSRAppendix[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRAppendix bean = new MPSRAppendix();
				bean.setNumber(data[i++]);
				bean.setTitle(data[i++]);
				bean.setFileName(data[i++]);
				bean.setDescription(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String str) {
		this.number = str;
	}

	public String getTitle() {
		return title != null ? title : "";
	}

	public void setTitle(String str) {
		this.title = str;
	}

	public String getDescription() {
		return description != null ? description : "";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
