package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRApprovedChanges {

	private static final String SQL_SELECT_TABLE = "select PO_NUM, VENDOR, PREV_AMT, CURR_AMT, CURR_DESC, TOTAL_AMT from MPSR_APPR_CO WHERE MPSR_ID=? order by PO_NUM";

	private String purchaseOrder;
	private String vendor;
	private double previousAmount;
	private double thisMonthAmount;
	private String thisMonthDescription;
	private double totalAmount;

	public static MPSRApprovedChanges[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRApprovedChanges[] beans = new MPSRApprovedChanges[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRApprovedChanges bean = new MPSRApprovedChanges();
				bean.setPurchaseOrder(data[i++]);
				bean.setVendor(data[i++]);
				bean.setPreviousAmount(data[i++]);
				bean.setThisMonthAmount(data[i++]);
				bean.setThisMonthDescription(data[i++]);
				bean.setTotalAmount(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getPurchaseOrder() {
		return null != purchaseOrder ? purchaseOrder : "";
	}

	public void setPurchaseOrder(String str) {
		this.purchaseOrder = str;
	}

	public String getVendor() {
		return null != vendor ? vendor : "";
	}

	public void setVendor(String str) {
		this.vendor = str;
	}

	public double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(String str) {
		this.previousAmount = null != str ? Double.parseDouble(str) : 0;
	}

	public double getThisMonthAmount() {
		return thisMonthAmount;
	}

	public void setThisMonthAmount(String str) {
		this.thisMonthAmount = null != str ? Double.parseDouble(str) : 0;
	}

	public String getThisMonthDescription() {
		return null != thisMonthDescription ? thisMonthDescription : "";
	}

	public void setThisMonthDescription(String str) {
		this.thisMonthDescription = str;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String str) {
		this.totalAmount = null != str ? Double.parseDouble(str) : 0;
	}

}
