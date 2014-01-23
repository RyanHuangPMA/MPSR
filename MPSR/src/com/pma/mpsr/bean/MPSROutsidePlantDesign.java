package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSROutsidePlantDesign extends MPSRInsidePlantDesign {

	private static final String SQL_SELECT_TABLE = "select PACKAGE, IFR, IFC, INDICATOR, NOTE from MPSR_ENG_OP WHERE MPSR_ID=? order by MPSR_ENG_OP_ID";

	public static MPSROutsidePlantDesign[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSROutsidePlantDesign[] beans = new MPSROutsidePlantDesign[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSROutsidePlantDesign bean = new MPSROutsidePlantDesign();
				bean.setPackage(data[i++]);
				bean.setIFR(data[i++]);
				bean.setIFC(data[i++]);
				bean.setIndicator(data[i++]);
				bean.setNote(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
