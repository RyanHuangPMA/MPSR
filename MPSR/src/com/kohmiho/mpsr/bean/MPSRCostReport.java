package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRCostReport {

	private static final String SQL_SELECT_TABLE = "select WBS, WBS_DESC, EST, ACT, EAC from MPSR_COST_RPT WHERE MPSR_ID=?";

	private String wbs;
	private String wbsDesc;
	private String estimate;
	private String actual;
	private String EAC;

	public static MPSRCostReport[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRCostReport[] beans = new MPSRCostReport[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRCostReport bean = new MPSRCostReport();
				bean.setWbs(data[i++]);
				bean.setWbsDesc(data[i++]);
				bean.setEstimate(data[i++]);
				bean.setActual(data[i++]);
				bean.setEAC(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public String getWbsDesc() {
		return wbsDesc != null ? wbsDesc : "";
	}

	public void setWbsDesc(String wbsDesc) {
		this.wbsDesc = wbsDesc;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String str) {
		this.estimate = str;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String str) {
		this.actual = str;
	}

	public String getEAC() {
		return EAC;
	}

	public void setEAC(String str) {
		EAC = str;
	}

}
