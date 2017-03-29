package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRStatusSummary {

	private static final String SQL_SELECT_TABLE = "select "
			+ "BUDGET_STATUS, BUDGET_APPROVED, BUDGET_TO_DATE, BUDGET_AT_COMP, "
			+ "SCHEDULE_STATUS, SCHEDULE_APPROVED, SCHEDULE_TO_DATE, SCHEDULE_AT_COMP, "
			+ "IN_SERVICE_STATUS, To_Char(IN_SERVICE_APPROVED,'MM/DD/YYYY'), To_Char(IN_SERVICE_TO_DATE,'MM/DD/YYYY'), To_Char(IN_SERVICE_AT_COMP,'MM/DD/YYYY') "
			+ "from MPSR_STATUS_SUM WHERE MPSR_ID=?";

	private String budgetStatus;
	private String budgetApproved;
	private String budgetToDate;
	private String budgetAtCompletion;
	private String scheduleStatus;
	private String scheduleApproved;
	private String scheduleToDate;
	private String scheduleAtCompletion;
	private String inServiceDateStatus;
	private String inServiceDateApproved;
	private String inServiceDateToDate;
	private String inServiceDateAtCompletion;

	public static MPSRStatusSummary getInstance(String mpsrID) {

		MPSRStatusSummary bean = new MPSRStatusSummary();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setBudgetStatus(data[i++]);
			bean.setBudgetApproved(data[i++]);
			bean.setBudgetToDate(data[i++]);
			bean.setBudgetAtCompletion(data[i++]);
			bean.setScheduleStatus(data[i++]);
			bean.setScheduleApproved(data[i++]);
			bean.setScheduleToDate(data[i++]);
			bean.setScheduleAtCompletion(data[i++]);
			bean.setInServiceDateStatus(data[i++]);
			bean.setInServiceDateApproved(data[i++]);
			bean.setInServiceDateToDate(data[i++]);
			bean.setInServiceDateAtCompletion(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getBudgetApproved() {
		return budgetApproved != null ? budgetApproved : "";
	}

	public String getBudgetAtCompletion() {
		return budgetAtCompletion != null ? budgetAtCompletion : "";
	}

	public String getBudgetStatus() {
		return budgetStatus != null ? budgetStatus : "";
	}

	public String getBudgetToDate() {
		return budgetToDate != null ? budgetToDate : "";
	}

	public String getInServiceDateApproved() {
		return inServiceDateApproved != null ? inServiceDateApproved : "";
	}

	public String getInServiceDateAtCompletion() {
		return inServiceDateAtCompletion != null ? inServiceDateAtCompletion : "";
	}

	public String getInServiceDateStatus() {
		return inServiceDateStatus != null ? inServiceDateStatus : "";
	}

	public String getInServiceDateToDate() {
		return inServiceDateToDate != null ? inServiceDateToDate : "";
	}

	public String getScheduleApproved() {
		return scheduleApproved != null ? scheduleApproved : "";
	}

	public String getScheduleAtCompletion() {
		return scheduleAtCompletion != null ? scheduleAtCompletion : "";
	}

	public String getScheduleStatus() {
		return scheduleStatus != null ? scheduleStatus : "";
	}

	public String getScheduleToDate() {
		return scheduleToDate != null ? scheduleToDate : "";
	}

	public void setBudgetApproved(String BudgetApproved) {
		this.budgetApproved = BudgetApproved;
	}

	public void setBudgetAtCompletion(String BudgetAtCompletion) {
		this.budgetAtCompletion = BudgetAtCompletion;
	}

	public void setBudgetStatus(String BudgetStatus) {
		this.budgetStatus = BudgetStatus;
	}

	public void setBudgetToDate(String BudgetToDate) {
		this.budgetToDate = BudgetToDate;
	}

	public void setInServiceDateApproved(String InServiceDateApproved) {
		this.inServiceDateApproved = InServiceDateApproved;
	}

	public void setInServiceDateAtCompletion(String InServiceDateAtCompletion) {
		this.inServiceDateAtCompletion = InServiceDateAtCompletion;
	}

	public void setInServiceDateStatus(String InServiceDateStatus) {
		this.inServiceDateStatus = InServiceDateStatus;
	}

	public void setInServiceDateToDate(String InServiceDateToDate) {
		this.inServiceDateToDate = InServiceDateToDate;
	}

	public void setScheduleApproved(String ScheduleApproved) {
		this.scheduleApproved = ScheduleApproved;
	}

	public void setScheduleAtCompletion(String ScheduleAtCompletion) {
		this.scheduleAtCompletion = ScheduleAtCompletion;
	}

	public void setScheduleStatus(String ScheduleStatus) {
		this.scheduleStatus = ScheduleStatus;
	}

	public void setScheduleToDate(String ScheduleToDate) {
		this.scheduleToDate = ScheduleToDate;
	}

}
