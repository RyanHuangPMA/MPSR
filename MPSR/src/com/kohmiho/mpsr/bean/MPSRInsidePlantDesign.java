package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRInsidePlantDesign {

	private static final String SQL_SELECT_TABLE = "select PACKAGE, IFR, IFC, INDICATOR, NOTE from MPSR_ENG_IP WHERE MPSR_ID=? order by MPSR_ENG_IP_ID";

	private String designPackage;
	private String iFR;
	private String iFC;
	private String indicator;
	private String note;

	public static MPSRInsidePlantDesign[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRInsidePlantDesign[] beans = new MPSRInsidePlantDesign[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRInsidePlantDesign bean = new MPSRInsidePlantDesign();
				bean.setPackage(data[i++]);
				bean.setIFR(data[i++]);
				bean.setIFC(data[i++]);
				bean.setIndicator(data[i++]);
				bean.setNote(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getPackage() {
		return designPackage;
	}

	public void setPackage(String designPackage) {
		this.designPackage = designPackage;
	}

	public String getIFR() {
		return iFR != null ? iFR : "";
	}

	public void setIFR(String ifr) {
		this.iFR = ifr;
	}

	public String getIFC() {
		return iFC;
	}

	public void setIFC(String ifc) {
		this.iFC = ifc;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String str) {
		this.indicator = str;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String str) {
		note = str;
	}

}
