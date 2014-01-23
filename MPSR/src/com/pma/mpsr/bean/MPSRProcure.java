package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRProcure {

	private static final String SQL_SELECT_TABLE = "select DESC_8_1_A, DESC_8_1_B, RECEIPT_TO_DATE, OPEN_COMMIT, DESC_8_2_1, DESC_8_2_2 from MPSR_PROCURE WHERE MPSR_ID=?";

	private String desc_8_1_A;
	private String desc_8_1_B;
	private String desc_8_2_1;
	private String desc_8_2_2;

	private double receiptedToDate;
	private double openCommitment;
	private double totalPurchaseOrder;

	public static MPSRProcure getInstance(String mpsrID) {

		MPSRProcure bean = new MPSRProcure();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc_8_1_A(data[i++]);
			bean.setDesc_8_1_B(data[i++]);
			bean.setReceiptedToDate(data[i++]);
			bean.setOpenCommitment(data[i++]);
			bean.setDesc_8_2_1(data[i++]);
			bean.setDesc_8_2_2(data[i++]);

			bean.setTotalPurchaseOrder(bean.getReceiptedToDate() + bean.getOpenCommitment());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bean;
	}

	public String getDesc_8_1_A() {
		return null != desc_8_1_A ? desc_8_1_A : "";
	}

	public void setDesc_8_1_A(String desc_8_1_A) {
		this.desc_8_1_A = desc_8_1_A;
	}

	public String getDesc_8_1_B() {
		return null != desc_8_1_B ? desc_8_1_B : "";
	}

	public void setDesc_8_1_B(String desc_8_1_B) {
		this.desc_8_1_B = desc_8_1_B;
	}

	public String getDesc_8_2_1() {
		return null != desc_8_2_1 ? desc_8_2_1 : "";
	}

	public void setDesc_8_2_1(String desc) {
		this.desc_8_2_1 = desc;
	}

	public String getDesc_8_2_2() {
		return null != desc_8_2_2 ? desc_8_2_2 : "";
	}

	public void setDesc_8_2_2(String desc) {
		this.desc_8_2_2 = desc;
	}

	public double getReceiptedToDate() {
		return receiptedToDate;
	}

	public void setReceiptedToDate(String str) {
		this.receiptedToDate = null != str ? Double.parseDouble(str) : 0;
	}

	public double getOpenCommitment() {
		return openCommitment;
	}

	public void setOpenCommitment(String str) {
		this.openCommitment = null != str ? Double.parseDouble(str) : 0;
	}

	public double getTotalPurchaseOrder() {
		return totalPurchaseOrder;
	}

	public void setTotalPurchaseOrder(double totalPurchaseOrder) {
		this.totalPurchaseOrder = totalPurchaseOrder;
	}

}
