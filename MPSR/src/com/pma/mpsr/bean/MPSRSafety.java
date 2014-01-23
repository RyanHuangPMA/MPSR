package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRSafety {

	private static final String SQL_SELECT_TABLE = "select DESC1, WH_CUR, WH_YTD, WH_PTD, OSHA_CUR, OSHA_YTD, OSHA_PTD, "
			+ "AID_1_CUR, AID_1_YTD, AID_1_PTD, MISS_CUR, MISS_YTD, MISS_PTD, AID_2_CUR, AID_2_YTD, AID_2_PTD, NOTE from MPSR_SAFE WHERE MPSR_ID=?";

	private String desc1;
	private double totalWorkHoursCurrent;
	private double totalWorkHoursYTD;
	private double totalWorkHoursPTD;
	private double oshaRecordableCurrent;
	private double oshaRecordableYTD;
	private double oshaRecordablePTD;
	private double firstAid1Current;
	private double firstAid1YTD;
	private double firstAid1PTD;
	private double nearMissCurrent;
	private double nearMissYTD;
	private double nearMissPTD;
	private double firstAid2Current;
	private double firstAid2YTD;
	private double firstAid2PTD;
	private String note;

	public static MPSRSafety getInstance(String mpsrID) {

		MPSRSafety bean = new MPSRSafety();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc1(data[i++]);
			bean.setTotalWorkHoursCurrent(data[i++]);
			bean.setTotalWorkHoursYTD(data[i++]);
			bean.setTotalWorkHoursPTD(data[i++]);
			bean.setOshaRecordableCurrent(data[i++]);
			bean.setOshaRecordableYTD(data[i++]);
			bean.setOshaRecordablePTD(data[i++]);
			bean.setFirstAid1Current(data[i++]);
			bean.setFirstAid1YTD(data[i++]);
			bean.setFirstAid1PTD(data[i++]);
			bean.setNearMissCurrent(data[i++]);
			bean.setNearMissYTD(data[i++]);
			bean.setNearMissPTD(data[i++]);
			bean.setFirstAid2Current(data[i++]);
			bean.setFirstAid2YTD(data[i++]);
			bean.setFirstAid2PTD(data[i++]);
			bean.setNote(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getDesc1() {
		return null != desc1 ? desc1 : "";
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public double getTotalWorkHoursCurrent() {
		return totalWorkHoursCurrent;
	}

	public void setTotalWorkHoursCurrent(String str) {
		this.totalWorkHoursCurrent = null != str ? Double.parseDouble(str) : 0;
	}

	public double getTotalWorkHoursYTD() {
		return totalWorkHoursYTD;
	}

	public void setTotalWorkHoursYTD(String str) {
		this.totalWorkHoursYTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getTotalWorkHoursPTD() {
		return totalWorkHoursPTD;
	}

	public void setTotalWorkHoursPTD(String str) {
		this.totalWorkHoursPTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getOshaRecordableCurrent() {
		return oshaRecordableCurrent;
	}

	public void setOshaRecordableCurrent(String str) {
		this.oshaRecordableCurrent = null != str ? Double.parseDouble(str) : 0;
	}

	public double getOshaRecordableYTD() {
		return oshaRecordableYTD;
	}

	public void setOshaRecordableYTD(String str) {
		this.oshaRecordableYTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getOshaRecordablePTD() {
		return oshaRecordablePTD;
	}

	public void setOshaRecordablePTD(String str) {
		this.oshaRecordablePTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid1Current() {
		return firstAid1Current;
	}

	public void setFirstAid1Current(String str) {
		this.firstAid1Current = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid1YTD() {
		return firstAid1YTD;
	}

	public void setFirstAid1YTD(String str) {
		this.firstAid1YTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid1PTD() {
		return firstAid1PTD;
	}

	public void setFirstAid1PTD(String str) {
		this.firstAid1PTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getNearMissCurrent() {
		return nearMissCurrent;
	}

	public void setNearMissCurrent(String str) {
		this.nearMissCurrent = null != str ? Double.parseDouble(str) : 0;
	}

	public double getNearMissYTD() {
		return nearMissYTD;
	}

	public void setNearMissYTD(String str) {
		this.nearMissYTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getNearMissPTD() {
		return nearMissPTD;
	}

	public void setNearMissPTD(String str) {
		this.nearMissPTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid2Current() {
		return firstAid2Current;
	}

	public void setFirstAid2Current(String str) {
		this.firstAid2Current = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid2YTD() {
		return firstAid2YTD;
	}

	public void setFirstAid2YTD(String str) {
		this.firstAid2YTD = null != str ? Double.parseDouble(str) : 0;
	}

	public double getFirstAid2PTD() {
		return firstAid2PTD;
	}

	public void setFirstAid2PTD(String str) {
		this.firstAid2PTD = null != str ? Double.parseDouble(str) : 0;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
