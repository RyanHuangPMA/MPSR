package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRLPE {

	private static final String SQL_SELECT_TABLE = "select DESC_6_1, DESC_6_2_1, DESC_6_2_2 from MPSR_LPE WHERE MPSR_ID=?";

	private String desc_6_1;
	private String desc_6_2_1;
	private String desc_6_2_2;

	public static MPSRLPE getInstance(String mpsrID) {

		MPSRLPE bean = new MPSRLPE();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc_6_1(data[i++]);
			bean.setDesc_6_2_1(data[i++]);
			bean.setDesc_6_2_2(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getDesc_6_1() {
		return desc_6_1 != null ? desc_6_1 : "";
	}

	public String getDesc_6_2_1() {
		return desc_6_2_1 != null ? desc_6_2_1 : "";
	}

	public String getDesc_6_2_2() {
		return desc_6_2_2 != null ? desc_6_2_2 : "";
	}

	public void setDesc_6_1(String str) {
		this.desc_6_1 = str;
	}

	public void setDesc_6_2_1(String str) {
		this.desc_6_2_1 = str;
	}

	public void setDesc_6_2_2(String str) {
		this.desc_6_2_2 = str;
	}

}
