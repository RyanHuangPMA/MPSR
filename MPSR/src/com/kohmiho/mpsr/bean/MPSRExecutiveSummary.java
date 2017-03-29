package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRExecutiveSummary {

	private static final String SQL_SELECT_TABLE = "select DESC_1_1_1, DESC_1_1_2, DESC_1_1_3, DESC_1_1_4, DESC_1_3_1, DESC_1_3_2 from MPSR_EXE_SUM WHERE MPSR_ID=?";

	private String desc_1_1_1;
	private String desc_1_1_2;
	private String desc_1_1_3;
	private String desc_1_1_4;
	private String desc_1_3_1;
	private String desc_1_3_2;

	public static MPSRExecutiveSummary getInstance(String mpsrID) {

		MPSRExecutiveSummary bean = new MPSRExecutiveSummary();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			if (rs.size() == 0)
				return bean;

			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc_1_1_1(data[i++]);
			bean.setDesc_1_1_2(data[i++]);
			bean.setDesc_1_1_3(data[i++]);
			bean.setDesc_1_1_4(data[i++]);
			bean.setDesc_1_3_1(data[i++]);
			bean.setDesc_1_3_2(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getDesc_1_1_1() {
		return desc_1_1_1 != null ? desc_1_1_1 : "";
	}

	public String getDesc_1_1_2() {
		return desc_1_1_2 != null ? desc_1_1_2 : "";
	}

	public String getDesc_1_1_3() {
		return desc_1_1_3 != null ? desc_1_1_3 : "";
	}

	public String getDesc_1_1_4() {
		return desc_1_1_4 != null ? desc_1_1_4 : "";
	}

	public String getDesc_1_3_1() {
		return desc_1_3_1 != null ? desc_1_3_1 : "";
	}

	public String getDesc_1_3_2() {
		return desc_1_3_2 != null ? desc_1_3_2 : "";
	}

	public void setDesc_1_1_1(String DESC_1_1_1) {
		this.desc_1_1_1 = DESC_1_1_1;
	}

	public void setDesc_1_1_2(String DESC_1_1_2) {
		this.desc_1_1_2 = DESC_1_1_2;
	}

	public void setDesc_1_1_3(String DESC_1_1_3) {
		this.desc_1_1_3 = DESC_1_1_3;
	}

	public void setDesc_1_1_4(String DESC_1_1_4) {
		this.desc_1_1_4 = DESC_1_1_4;
	}

	public void setDesc_1_3_1(String DESC_1_3_1) {
		this.desc_1_3_1 = DESC_1_3_1;
	}

	public void setDesc_1_3_2(String DESC_1_3_2) {
		this.desc_1_3_2 = DESC_1_3_2;
	}

}
