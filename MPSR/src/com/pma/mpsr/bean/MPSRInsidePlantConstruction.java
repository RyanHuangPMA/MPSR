package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRInsidePlantConstruction {

	private static final String SQL_SELECT_TABLE = "select FIGURE, TITLE, WORK, DESCRIPTION, FILE_NAME from MPSR_CONST_IP WHERE MPSR_ID=? order by MPSR_CONST_IP_ID";

	private String figure;
	private String title;
	private String work;
	private String description;
	private String fileName;

	public static MPSRInsidePlantConstruction[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRInsidePlantConstruction[] beans = new MPSRInsidePlantConstruction[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRInsidePlantConstruction bean = new MPSRInsidePlantConstruction();
				bean.setFigure(data[i++]);
				bean.setTitle(data[i++]);
				bean.setWork(data[i++]);
				bean.setDescription(data[i++]);
				bean.setFileName(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getFigure() {
		return null != figure ? figure : "";
	}

	public void setFigure(String str) {
		this.figure = str;
	}

	public String getTitle() {
		return null != title ? title : "";
	}

	public void setTitle(String str) {
		this.title = str;
	}

	public String getWork() {
		return null != work ? work : "";
	}

	public void setWork(String str) {
		this.work = str;
	}

	public String getDescription() {
		return null != description ? description : "";
	}

	public void setDescription(String str) {
		this.description = str;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String str) {
		this.fileName = str;
	}

}
