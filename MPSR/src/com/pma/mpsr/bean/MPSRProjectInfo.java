package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRProjectInfo {

	private static final String SQL_SELECT_TABLE = "select PROJ_DEF, PROJ_TITLE, RPT_MONTH_YEAR, PM, DIR, FILE_NAME from MPSR WHERE MPSR_ID=?";

	private String projectDefinition;
	private String projectTitle;
	private String reportMonthYear;
	private String projectManager;
	private String projectDirector;
	private String fileName;

	public static MPSRProjectInfo getInstance(String mpsrID) {

		MPSRProjectInfo bean = new MPSRProjectInfo();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setProjectDefinition(data[i++]);
			bean.setProjectTitle(data[i++]);
			bean.setReportMonthYear(data[i++]);
			bean.setProjectManager(data[i++]);
			bean.setProjectDirector(data[i++]);
			bean.setFileName(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getProjectDefinition() {
		return projectDefinition != null ? projectDefinition : "";
	}

	public String getProjectDirector() {
		return projectDirector != null ? projectDirector : "";

	}

	public String getProjectManager() {
		return projectManager != null ? projectManager : "";
	}

	public String getProjectTitle() {
		return projectTitle != null ? projectTitle : "";
	}

	public String getFileName() {
		return null != fileName ? fileName : "";
	}

	public String getReportMonthYear() {
		return reportMonthYear != null ? reportMonthYear : "";
	}

	public void setProjectDefinition(String projDef) {
		this.projectDefinition = projDef;
	}

	public void setProjectDirector(String projectDirector) {
		this.projectDirector = projectDirector;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public void setProjectTitle(String title) {
		this.projectTitle = title;
	}

	public void setReportMonthYear(String reportMonthYear) {
		this.reportMonthYear = reportMonthYear;
	}

	public void setFileName(String path) {
		this.fileName = path;
	}

}
