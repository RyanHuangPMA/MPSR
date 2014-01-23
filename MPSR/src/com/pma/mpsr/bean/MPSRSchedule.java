package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRSchedule {

	private static final String SQL_SELECT_TABLE = "select DESC_5_1, DESC_5_2, DESC_5_3, DESC_5_4 from MPSR_SCHEDULE WHERE MPSR_ID=?";

	private String desc_5_1;
	private String desc_5_2;
	private String desc_5_3;
	private String desc_5_4;

	public static MPSRSchedule getInstance(String mpsrID) {

		MPSRSchedule bean = new MPSRSchedule();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc_5_1(data[i++]);
			bean.setDesc_5_2(data[i++]);
			bean.setDesc_5_3(data[i++]);
			bean.setDesc_5_4(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getDesc_5_1() {
		return desc_5_1 != null ? desc_5_1 : "";
	}

	public String getDesc_5_2() {
		return desc_5_2 != null ? desc_5_2 : "";
	}

	public String getDesc_5_3() {
		return desc_5_3 != null ? desc_5_3 : "";
	}

	public String getDesc_5_4() {
		return desc_5_4 != null ? desc_5_4 : "";
	}

	public void setDesc_5_1(String str) {
		this.desc_5_1 = str;
	}

	public void setDesc_5_2(String str) {
		this.desc_5_2 = str;
	}

	public void setDesc_5_3(String str) {
		this.desc_5_3 = str;
	}

	public void setDesc_5_4(String str) {
		this.desc_5_4 = str;
	}

}
