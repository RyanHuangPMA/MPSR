package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRBudget {

	private static final String SQL_SELECT_TABLE = "select DESC1, PM_BUDGET, PM_ACT, PM_COMMIT, PM_OTHER from MPSR_BUDGET WHERE MPSR_ID=?";

	private String desc1;
	private double pmBudget;
	private double pmActual;
	private double pmCommitment;
	private double pmAdditionalCost;

	public static MPSRBudget getInstance(String mpsrID) {

		MPSRBudget bean = new MPSRBudget();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc1(data[i++]);
			bean.setPMBudget(data[i++]);
			bean.setPMActual(data[i++]);
			bean.setPMCommitment(data[i++]);
			bean.setPMAdditionalCost(data[i++]);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bean;
	}

	public String getDesc1() {
		return null != desc1 ? desc1 : "";
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public double getPMBudget() {
		return pmBudget;
	}

	public void setPMBudget(String str) {
		this.pmBudget = null != str ? Double.parseDouble(str) : 0;
	}

	public double getPMActual() {
		return pmActual;
	}

	public void setPMActual(String str) {
		this.pmActual = null != str ? Double.parseDouble(str) : 0;
	}

	public double getPMCommitment() {
		return pmCommitment;
	}

	public void setPMCommitment(String str) {
		this.pmCommitment = null != str ? Double.parseDouble(str) : 0;
	}

	public double getPMAdditionalCost() {
		return pmAdditionalCost;
	}

	public void setPMAdditionalCost(String str) {
		this.pmAdditionalCost = null != str ? Double.parseDouble(str) : 0;
	}

}
