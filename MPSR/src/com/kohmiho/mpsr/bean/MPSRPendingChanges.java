package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRPendingChanges extends MPSRApprovedChanges {

	private static final String SQL_SELECT_TABLE = "select PO_NUM, VENDOR, PREV_AMT, CURR_AMT, CURR_DESC, TOTAL_AMT from MPSR_PEND_CO WHERE MPSR_ID=? order by PO_NUM";

	public static MPSRPendingChanges[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRPendingChanges[] beans = new MPSRPendingChanges[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRPendingChanges bean = new MPSRPendingChanges();
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
}
