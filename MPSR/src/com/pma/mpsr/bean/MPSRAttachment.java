package com.pma.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pma.db.ConnectDB;

public class MPSRAttachment extends MPSRAppendix {

	private static final String SQL_SELECT_TABLE = "select Num, Title, File_Name, DESC_1 from MPSR_ATCH WHERE MPSR_ID=? order by Num";

	public static MPSRAttachment[] getInstance(String mpsrID) {

		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			MPSRAttachment[] beans = new MPSRAttachment[rs.size()];

			for (int j = 0; j < rs.size(); j++) {
				String[] data = rs.get(j);
				int i = 0;

				MPSRAttachment bean = new MPSRAttachment();
				bean.setNumber(data[i++]);
				bean.setTitle(data[i++]);
				bean.setFileName(data[i++]);
				bean.setDescription(data[i++]);

				beans[j] = bean;
			}

			return beans;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
