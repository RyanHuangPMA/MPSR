package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRSchedulePerformance {

	private static final String SQL_SELECT_TABLE = "select WBS, ORIGDUR, REMDUR, To_Char(BL_PROJECT_START, 'MM/DD/YYYY'), To_Char(BL_PROJECT_FINISH,'MM/DD/YYYY'), To_Char(START_DATE, 'MM/DD/YYYY'), To_Char(FINISH_DATE,'MM/DD/YYYY'), TOTAL_FLOAT, STATUS from MPSR_SCHE_PERF WHERE MPSR_ID=?";

	private String wbs;
	private double originalDuration;
	private double remainingDuration;
	private String plannedStartDate;
	private String plannedFinishDate;
	private String startDate;
	private String finishDate;
	private String totalFloat;
	private String status;

	public static MPSRSchedulePerformance[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRSchedulePerformance[] beans = new MPSRSchedulePerformance[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRSchedulePerformance bean = new MPSRSchedulePerformance();
				bean.setWBS(data[i++]);
				bean.setOriginalDuration(Double.parseDouble(data[i++]));
				bean.setRemainingDuration(Double.parseDouble(data[i++]));
				bean.setPlannedStartDate(data[i++]);
				bean.setPlannedFinishDate(data[i++]);
				bean.setStartDate(data[i++]);
				bean.setFinishDate(data[i++]);
				bean.setTotalFloat(data[i++]);
				bean.setStatus(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public String getWBS() {
		return wbs;
	}

	public void setWBS(String wbs) {
		this.wbs = wbs;
	}

	public String getPlannedStartDate() {
		return plannedStartDate != null ? plannedStartDate : "";
	}

	public void setPlannedStartDate(String date) {
		this.plannedStartDate = date;
	}

	public String getPlannedFinishDate() {
		return plannedFinishDate != null ? plannedFinishDate : "";
	}

	public void setPlannedFinishDate(String date) {
		this.plannedFinishDate = date;
	}

	public double getOriginalDuration() {
		return originalDuration;
	}

	public void setOriginalDuration(double originalDuration) {
		this.originalDuration = originalDuration;
	}

	public double getRemainingDuration() {
		return remainingDuration;
	}

	public void setRemainingDuration(double remainingDuration) {
		this.remainingDuration = remainingDuration;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String date) {
		this.startDate = date;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String date) {
		this.finishDate = date;
	}

	public String getTotalFloat() {
		return null != totalFloat ? totalFloat : "";
	}

	public void setTotalFloat(String totalFloat) {
		this.totalFloat = totalFloat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
