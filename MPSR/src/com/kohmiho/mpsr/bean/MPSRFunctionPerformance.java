package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRFunctionPerformance {

	private static final String SQL_SELECT_TABLE = "select PROJ_STATUS, PROJ_TREND, PROJ_COMMENT, TARGET_BUDGET_STATUS, TARGET_BUDGET_TREND, TARGET_BUDGET_COMMENT, "
			+ "SAFETY_STATUS, SAFETY_TREND, SAFETY_COMMENT, LICEN_STATUS, LICEN_TREND, LICEN_COMMENT, "
			+ "CORP_STATUS, CORP_TREND, CORP_COMMENT, IPDE_STATUS, IPDE_TREND, IPDE_COMMENT, "
			+ "OUTREACH_STATUS, OUTREACH_TREND, OUTREACH_COMMENT, PROCU_STATUS, PROCU_TREND, PROCU_COMMENT, "
			+ "CONST_STATUS, CONST_TREND, CONST_COMMENT, ENV_STATUS, ENV_TREND, ENV_COMMENT, "
			+ "SALV_STATUS, SALV_TREND, SALV_COMMENT, WITHDRAW_STATUS, WITHDRAW_TREND, WITHDRAW_COMMENT " + "from MPSR_FUNC_PERF WHERE MPSR_ID=?";

	private String projectStatus;
	private String projectTrend;
	private String projectComment;
	private String targetBudgetStatus;
	private String targetBudgetTrend;
	private String targetBudgetComment;
	private String safetyStatus;
	private String safetyTrend;
	private String safetyComment;
	private String licensingStatus;
	private String licensingTrend;
	private String licensingComment;
	private String corporateStatus;
	private String corporateTrend;
	private String corporateComment;
	private String ipdeStatus;
	private String ipdeTrend;
	private String ipdeComment;
	private String outreachStatus;
	private String outreachTrend;
	private String outreachComment;
	private String procureStatus;
	private String procureTrend;
	private String procureComment;
	private String constructionStatus;
	private String constructionTrend;
	private String constructionComment;
	private String enviromentStatus;
	private String enviromentTrend;
	private String enviromentComment;
	private String salvageStatus;
	private String salvageTrend;
	private String salvageComment;
	private String withdrawalStatus;
	private String withdrawalTrend;
	private String withdrawalComment;

	public static MPSRFunctionPerformance getInstance(String mpsrID) {

		MPSRFunctionPerformance bean = new MPSRFunctionPerformance();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setProjectStatus(data[i++]);
			bean.setProjectTrend(data[i++]);
			bean.setProjectComment(data[i++]);
			bean.setTargetBudgetStatus(data[i++]);
			bean.setTargetBudgetTrend(data[i++]);
			bean.setTargetBudgetComment(data[i++]);
			bean.setSafetyStatus(data[i++]);
			bean.setSafetyTrend(data[i++]);
			bean.setSafetyComment(data[i++]);
			bean.setLicensingStatus(data[i++]);
			bean.setLicensingTrend(data[i++]);
			bean.setLicensingComment(data[i++]);
			bean.setCorporateStatus(data[i++]);
			bean.setCorporateTrend(data[i++]);
			bean.setCorporateComment(data[i++]);
			bean.setIPDEStatus(data[i++]);
			bean.setIPDETrend(data[i++]);
			bean.setIPDEComment(data[i++]);
			bean.setOutreachStatus(data[i++]);
			bean.setOutreachTrend(data[i++]);
			bean.setOutreachComment(data[i++]);
			bean.setProcureStatus(data[i++]);
			bean.setProcureTrend(data[i++]);
			bean.setProcureComment(data[i++]);
			bean.setConstructionStatus(data[i++]);
			bean.setConstructionTrend(data[i++]);
			bean.setConstructionComment(data[i++]);
			bean.setEnviromentStatus(data[i++]);
			bean.setEnviromentTrend(data[i++]);
			bean.setEnviromentComment(data[i++]);
			bean.setSalvageStatus(data[i++]);
			bean.setSalvageTrend(data[i++]);
			bean.setSalvageComment(data[i++]);
			bean.setWithdrawalStatus(data[i++]);
			bean.setWithdrawalTrend(data[i++]);
			bean.setWithdrawalComment(data[i++]);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return bean;
	}

	public String getConstructionComment() {
		return constructionComment != null ? constructionComment : "";
	}

	public String getConstructionStatus() {
		return constructionStatus != null ? constructionStatus : "";
	}

	public String getConstructionTrend() {
		return constructionTrend != null ? constructionTrend : "";
	}

	public String getCorporateComment() {
		return corporateComment != null ? corporateComment : "";
	}

	public String getCorporateStatus() {
		return corporateStatus != null ? corporateStatus : "";
	}

	public String getCorporateTrend() {
		return corporateTrend != null ? corporateTrend : "";
	}

	public String getEnviromentComment() {
		return enviromentComment != null ? enviromentComment : "";
	}

	public String getEnviromentStatus() {
		return enviromentStatus != null ? enviromentStatus : "";
	}

	public String getEnviromentTrend() {
		return enviromentTrend != null ? enviromentTrend : "";
	}

	public String getIPDEComment() {
		return ipdeComment != null ? ipdeComment : "";
	}

	public String getIPDEStatus() {
		return ipdeStatus != null ? ipdeStatus : "";
	}

	public String getIPDETrend() {
		return ipdeTrend != null ? ipdeTrend : "";
	}

	public String getLicensingComment() {
		return licensingComment != null ? licensingComment : "";
	}

	public String getLicensingStatus() {
		return licensingStatus != null ? licensingStatus : "";
	}

	public String getLicensingTrend() {
		return licensingTrend != null ? licensingTrend : "";
	}

	public String getOutreachComment() {
		return outreachComment != null ? outreachComment : "";
	}

	public String getOutreachStatus() {
		return outreachStatus != null ? outreachStatus : "";
	}

	public String getOutreachTrend() {
		return outreachTrend != null ? outreachTrend : "";
	}

	public String getProcureComment() {
		return procureComment != null ? procureComment : "";
	}

	public String getProcureStatus() {
		return procureStatus != null ? procureStatus : "";
	}

	public String getProcureTrend() {
		return procureTrend != null ? procureTrend : "";
	}

	public String getProjectComment() {
		return projectComment != null ? projectComment : "";
	}

	public String getProjectStatus() {
		return projectStatus != null ? projectStatus : "";
	}

	public String getProjectTrend() {
		return projectTrend != null ? projectTrend : "";
	}

	public String getTargetBudgetComment() {
		return targetBudgetComment != null ? targetBudgetComment : "";
	}

	public String getTargetBudgetStatus() {
		return targetBudgetStatus != null ? targetBudgetStatus : "";
	}

	public String getTargetBudgetTrend() {
		return targetBudgetTrend != null ? targetBudgetTrend : "";
	}

	public String getSafetyComment() {
		return safetyComment != null ? safetyComment : "";
	}

	public String getSafetyStatus() {
		return safetyStatus != null ? safetyStatus : "";
	}

	public String getSafetyTrend() {
		return safetyTrend != null ? safetyTrend : "";
	}

	public String getSalvageComment() {
		return salvageComment != null ? salvageComment : "";
	}

	public String getSalvageStatus() {
		return salvageStatus != null ? salvageStatus : "";
	}

	public String getSalvageTrend() {
		return salvageTrend != null ? salvageTrend : "";
	}

	public String getWithdrawalComment() {
		return withdrawalComment != null ? withdrawalComment : "";
	}

	public String getWithdrawalStatus() {
		return withdrawalStatus != null ? withdrawalStatus : "";
	}

	public String getWithdrawalTrend() {
		return withdrawalTrend != null ? withdrawalTrend : "";
	}

	public void setConstructionComment(String comment) {
		this.constructionComment = comment;
	}

	public void setConstructionStatus(String status) {
		this.constructionStatus = status;
	}

	public void setConstructionTrend(String trend) {
		this.constructionTrend = trend;
	}

	public void setCorporateComment(String comment) {
		this.corporateComment = comment;
	}

	public void setCorporateStatus(String status) {
		this.corporateStatus = status;
	}

	public void setCorporateTrend(String trend) {
		this.corporateTrend = trend;
	}

	public void setEnviromentComment(String comment) {
		this.enviromentComment = comment;
	}

	public void setEnviromentStatus(String status) {
		this.enviromentStatus = status;
	}

	public void setEnviromentTrend(String trend) {
		this.enviromentTrend = trend;
	}

	public void setIPDEComment(String comment) {
		this.ipdeComment = comment;
	}

	public void setIPDEStatus(String status) {
		this.ipdeStatus = status;
	}

	public void setIPDETrend(String trend) {
		this.ipdeTrend = trend;
	}

	public void setLicensingComment(String comment) {
		this.licensingComment = comment;
	}

	public void setLicensingStatus(String status) {
		this.licensingStatus = status;
	}

	public void setLicensingTrend(String trend) {
		this.licensingTrend = trend;
	}

	public void setOutreachComment(String comment) {
		this.outreachComment = comment;
	}

	public void setOutreachStatus(String status) {
		this.outreachStatus = status;
	}

	public void setOutreachTrend(String trend) {
		this.outreachTrend = trend;
	}

	public void setProcureComment(String comment) {
		this.procureComment = comment;
	}

	public void setProcureStatus(String status) {
		this.procureStatus = status;
	}

	public void setProcureTrend(String trend) {
		this.procureTrend = trend;
	}

	public void setProjectComment(String comment) {
		this.projectComment = comment;
	}

	public void setProjectStatus(String status) {
		this.projectStatus = status;
	}

	public void setProjectTrend(String trend) {
		this.projectTrend = trend;
	}

	public void setTargetBudgetComment(String comment) {
		this.targetBudgetComment = comment;
	}

	public void setTargetBudgetStatus(String status) {
		this.targetBudgetStatus = status;
	}

	public void setTargetBudgetTrend(String trend) {
		this.targetBudgetTrend = trend;
	}

	public void setSafetyComment(String comment) {
		this.safetyComment = comment;
	}

	public void setSafetyStatus(String status) {
		this.safetyStatus = status;
	}

	public void setSafetyTrend(String trend) {
		this.safetyTrend = trend;
	}

	public void setSalvageComment(String comment) {
		this.salvageComment = comment;
	}

	public void setSalvageStatus(String status) {
		this.salvageStatus = status;
	}

	public void setSalvageTrend(String trend) {
		this.salvageTrend = trend;
	}

	public void setWithdrawalComment(String comment) {
		this.withdrawalComment = comment;
	}

	public void setWithdrawalStatus(String status) {
		this.withdrawalStatus = status;
	}

	public void setWithdrawalTrend(String trend) {
		this.withdrawalTrend = trend;
	}

}
