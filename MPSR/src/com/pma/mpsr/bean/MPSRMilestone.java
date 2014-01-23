package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRMilestone {

	private static final String SQL_SELECT_TABLE = "select MILESTONE, To_Char(PLAN_DATE, 'MM/DD/YYYY'), To_Char(END_DATE,'MM/DD/YYYY') from MPSR_MILESTONE WHERE MPSR_ID=?";

	private String milestone;
	private String plannedDate;
	private String finishDate;

	public static MPSRMilestone[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRMilestone[] beans = new MPSRMilestone[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRMilestone bean = new MPSRMilestone();
				bean.setMilestone(data[i++]);
				bean.setPlannedDate(data[i++]);
				bean.setFinishDate(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getPlannedDate() {
		return plannedDate != null ? plannedDate : "";
	}

	public void setPlannedDate(String plannedDate) {
		this.plannedDate = plannedDate;
	}

	public String getFinishDate() {
		return finishDate != null ? finishDate : "";
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

}
