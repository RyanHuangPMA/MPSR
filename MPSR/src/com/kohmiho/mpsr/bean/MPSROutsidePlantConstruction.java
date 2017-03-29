package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSROutsidePlantConstruction extends MPSRInsidePlantConstruction {

	private static final String SQL_SELECT_TABLE = "select FIGURE, TITLE, WORK, DESCRIPTION, FILE_NAME from MPSR_CONST_OP WHERE MPSR_ID=? order by MPSR_CONST_OP_ID";

	public static MPSROutsidePlantConstruction[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSROutsidePlantConstruction[] beans = new MPSROutsidePlantConstruction[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSROutsidePlantConstruction bean = new MPSROutsidePlantConstruction();
				bean.setFigure(data[i++]);
				bean.setTitle(data[i++]);
				bean.setWork(data[i++]);
				bean.setDescription(data[i++]);
				bean.setFileName(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
