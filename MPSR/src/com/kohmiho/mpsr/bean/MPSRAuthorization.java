package com.kohmiho.mpsr.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.ConnectDB;

public class MPSRAuthorization {

	private static final String SQL_SELECT_TABLE = "select DESC1, HEADER_YR0, HEADER_YR1, HEADER_YR2, HEADER_YR3, HEADER_YR4, HEADER_POST_YEAR, "
			+ "BASE_PRIOR_YEAR, BASE_YR0, BASE_YR1, BASE_YR2, BASE_YR3, BASE_YR4, BASE_POST_YEAR, "
			+ "RC_PRIOR_YEAR, RC_YR0, RC_YR1, RC_YR2, RC_YR3, RC_YR4, RC_POST_YEAR, To_Char(TOTAL_DATE,'MM/YY'), "
			+ "REQ0_PRIOR_YEAR, REQ0_YR0, REQ0_YR1, REQ0_YR2, REQ0_YR3, REQ0_YR4, REQ0_POST_YEAR, REQ0_AUTH, To_Char(REQ0_DATE,'MM/YY'), "
			+ "REQ1_PRIOR_YEAR, REQ1_YR0, REQ1_YR1, REQ1_YR2, REQ1_YR3, REQ1_YR4, REQ1_POST_YEAR, REQ1_AUTH, To_Char(REQ1_DATE,'MM/YY'), "
			+ "REQ2_PRIOR_YEAR, REQ2_YR0, REQ2_YR1, REQ2_YR2, REQ2_YR3, REQ2_YR4, REQ2_POST_YEAR, REQ2_AUTH, To_Char(REQ2_DATE,'MM/YY'), TARGETBUDGET "
			+ "from MPSR_AUTH WHERE MPSR_ID=?";

	private String desc1;
	private String headerYear0;
	private String headerYear1;
	private String headerYear2;
	private String headerYear3;
	private String headerYear4;
	private String headerPostYear;
	private double basePriorYear;
	private double baseYear0;
	private double baseYear1;
	private double baseYear2;
	private double baseYear3;
	private double baseYear4;
	private double basePostYear;
	private double rnCPriorYear;
	private double rnCYear0;
	private double rnCYear1;
	private double rnCYear2;
	private double rnCYear3;
	private double rnCYear4;
	private double rnCPostYear;
	private String totalProjectDate;
	private double req0PriorYear;
	private double req0Year0;
	private double req0Year1;
	private double req0Year2;
	private double req0Year3;
	private double req0Year4;
	private double req0PostYear;
	private double req0Authorization;
	private String req0RequestDate;
	private double req1PriorYear;
	private double req1Year0;
	private double req1Year1;
	private double req1Year2;
	private double req1Year3;
	private double req1Year4;
	private double req1PostYear;
	private double req1Authorization;
	private String req1RequestDate;
	private double req2PriorYear;
	private double req2Year0;
	private double req2Year1;
	private double req2Year2;
	private double req2Year3;
	private double req2Year4;
	private double req2PostYear;
	private double req2Authorization;
	private String req2RequestDate;
	private String targetBudget;

	private double baseTotal;
	private double rncTotal;
	private double priorTotal;
	private double year0Total;
	private double year1Total;
	private double year2Total;
	private double year3Total;
	private double year4Total;
	private double postTotal;
	private double totalTotal;
	private double req0Total;
	private double req1Total;
	private double req2Total;

	public static MPSRAuthorization getInstance(String mpsrID) {

		MPSRAuthorization bean = new MPSRAuthorization();
		try {
			ConnectDB connectDB = new ConnectDB();
			ArrayList<String[]> rs = connectDB.exeQuery(SQL_SELECT_TABLE, new String[] { mpsrID });
			String[] data = rs.get(0);
			int i = 0;

			bean.setDesc1(data[i++]);
			bean.setHeaderYear0(data[i++]);
			bean.setHeaderYear1(data[i++]);
			bean.setHeaderYear2(data[i++]);
			bean.setHeaderYear3(data[i++]);
			bean.setHeaderYear4(data[i++]);
			bean.setHeaderPostYear(data[i++]);
			bean.setBasePriorYear(data[i++]);
			bean.setBaseYear0(data[i++]);
			bean.setBaseYear1(data[i++]);
			bean.setBaseYear2(data[i++]);
			bean.setBaseYear3(data[i++]);
			bean.setBaseYear4(data[i++]);
			bean.setBasePostYear(data[i++]);
			bean.setRnCPriorYear(data[i++]);
			bean.setRnCYear0(data[i++]);
			bean.setRnCYear1(data[i++]);
			bean.setRnCYear2(data[i++]);
			bean.setRnCYear3(data[i++]);
			bean.setRnCYear4(data[i++]);
			bean.setRnCPostYear(data[i++]);
			bean.setTotalProjectDate(data[i++]);
			bean.setReq0PriorYear(data[i++]);
			bean.setReq0Year0(data[i++]);
			bean.setReq0Year1(data[i++]);
			bean.setReq0Year2(data[i++]);
			bean.setReq0Year3(data[i++]);
			bean.setReq0Year4(data[i++]);
			bean.setReq0PostYear(data[i++]);
			bean.setReq0Authorization(data[i++]);
			bean.setReq0RequestDate(data[i++]);
			bean.setReq1PriorYear(data[i++]);
			bean.setReq1Year0(data[i++]);
			bean.setReq1Year1(data[i++]);
			bean.setReq1Year2(data[i++]);
			bean.setReq1Year3(data[i++]);
			bean.setReq1Year4(data[i++]);
			bean.setReq1PostYear(data[i++]);
			bean.setReq1Authorization(data[i++]);
			bean.setReq1RequestDate(data[i++]);
			bean.setReq2PriorYear(data[i++]);
			bean.setReq2Year0(data[i++]);
			bean.setReq2Year1(data[i++]);
			bean.setReq2Year2(data[i++]);
			bean.setReq2Year3(data[i++]);
			bean.setReq2Year4(data[i++]);
			bean.setReq2PostYear(data[i++]);
			bean.setReq2Authorization(data[i++]);
			bean.setReq2RequestDate(data[i++]);
			bean.setTargetBudget(data[i++]);

			// --------------------------------------------

			bean.setBaseTotal(bean.getBasePriorYear() + bean.getBaseYear0() + bean.getBaseYear1() + bean.getBaseYear2() + bean.getBaseYear3()
					+ bean.getBaseYear4() + bean.getBasePostYear());
			bean.setRnCTotal(bean.getRnCPriorYear() + bean.getRnCYear0() + bean.getRnCYear1() + bean.getRnCYear2() + bean.getRnCYear3() + bean.getRnCYear4()
					+ bean.getRnCPostYear());
			bean.setTotalTotal(bean.getBaseTotal() + bean.getRnCTotal());
			bean.setPriorTotal(bean.getBasePriorYear() + bean.getRnCPriorYear());
			bean.setYear0Total(bean.getBaseYear0() + bean.getRnCYear0());
			bean.setYear1Total(bean.getBaseYear1() + bean.getRnCYear1());
			bean.setYear2Total(bean.getBaseYear2() + bean.getRnCYear2());
			bean.setYear3Total(bean.getBaseYear3() + bean.getRnCYear3());
			bean.setYear4Total(bean.getBaseYear4() + bean.getRnCYear4());
			bean.setPostTotal(bean.getBasePostYear() + bean.getRnCPostYear());
			bean.setReq0Total(bean.getReq0PriorYear() + bean.getReq0Year0() + bean.getReq0Year1() + bean.getReq0Year2() + bean.getReq0Year3()
					+ bean.getReq0Year4() + bean.getReq0PostYear());
			bean.setReq1Total(bean.getReq1PriorYear() + bean.getReq1Year0() + bean.getReq1Year1() + bean.getReq1Year2() + bean.getReq1Year3()
					+ bean.getReq1Year4() + bean.getReq1PostYear());
			bean.setReq2Total(bean.getReq2PriorYear() + bean.getReq2Year0() + bean.getReq2Year1() + bean.getReq2Year2() + bean.getReq2Year3()
					+ bean.getReq2Year4() + bean.getReq2PostYear());

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

	public String getHeaderYear0() {
		return headerYear0;
	}

	public void setHeaderYear0(String headerYear0) {
		this.headerYear0 = headerYear0;
	}

	public String getHeaderYear1() {
		return headerYear1;
	}

	public void setHeaderYear1(String headerYear1) {
		this.headerYear1 = headerYear1;
	}

	public String getHeaderYear2() {
		return headerYear2;
	}

	public void setHeaderYear2(String headerYear2) {
		this.headerYear2 = headerYear2;
	}

	public String getHeaderYear3() {
		return headerYear3;
	}

	public void setHeaderYear3(String headerYear3) {
		this.headerYear3 = headerYear3;
	}

	public String getHeaderYear4() {
		return headerYear4;
	}

	public void setHeaderYear4(String headerYear4) {
		this.headerYear4 = headerYear4;
	}

	public String getHeaderPostYear() {
		return headerPostYear;
	}

	public void setHeaderPostYear(String headerPostYear) {
		this.headerPostYear = headerPostYear;
	}

	public double getBasePriorYear() {
		return basePriorYear;
	}

	public void setBasePriorYear(String str) {
		this.basePriorYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBaseYear0() {
		return baseYear0;
	}

	public void setBaseYear0(String str) {
		this.baseYear0 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBaseYear1() {
		return baseYear1;
	}

	public void setBaseYear1(String str) {
		this.baseYear1 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBaseYear2() {
		return baseYear2;
	}

	public void setBaseYear2(String str) {
		this.baseYear2 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBaseYear3() {
		return baseYear3;
	}

	public void setBaseYear3(String str) {
		this.baseYear3 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBaseYear4() {
		return baseYear4;
	}

	public void setBaseYear4(String str) {
		this.baseYear4 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getBasePostYear() {
		return basePostYear;
	}

	public void setBasePostYear(String str) {
		this.basePostYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCPriorYear() {
		return rnCPriorYear;
	}

	public void setRnCPriorYear(String str) {
		this.rnCPriorYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCYear0() {
		return rnCYear0;
	}

	public void setRnCYear0(String str) {
		this.rnCYear0 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCYear1() {
		return rnCYear1;
	}

	public void setRnCYear1(String str) {
		this.rnCYear1 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCYear2() {
		return rnCYear2;
	}

	public void setRnCYear2(String str) {
		this.rnCYear2 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCYear3() {
		return rnCYear3;
	}

	public void setRnCYear3(String str) {
		this.rnCYear3 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCYear4() {
		return rnCYear4;
	}

	public void setRnCYear4(String str) {
		this.rnCYear4 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getRnCPostYear() {
		return rnCPostYear;
	}

	public void setRnCPostYear(String str) {
		this.rnCPostYear = null != str ? Double.parseDouble(str) : 0;
	}

	public String getTotalProjectDate() {
		return null != totalProjectDate ? totalProjectDate : "";
	}

	public void setTotalProjectDate(String str) {
		this.totalProjectDate = str;
	}

	public double getReq0PriorYear() {
		return req0PriorYear;
	}

	public void setReq0PriorYear(String str) {
		this.req0PriorYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Year0() {
		return req0Year0;
	}

	public void setReq0Year0(String str) {
		this.req0Year0 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Year1() {
		return req0Year1;
	}

	public void setReq0Year1(String str) {
		this.req0Year1 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Year2() {
		return req0Year2;
	}

	public void setReq0Year2(String str) {
		this.req0Year2 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Year3() {
		return req0Year3;
	}

	public void setReq0Year3(String str) {
		this.req0Year3 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Year4() {
		return req0Year4;
	}

	public void setReq0Year4(String str) {
		this.req0Year4 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0PostYear() {
		return req0PostYear;
	}

	public void setReq0PostYear(String str) {
		this.req0PostYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq0Authorization() {
		return req0Authorization;
	}

	public void setReq0Authorization(String str) {
		this.req0Authorization = null != str ? Double.parseDouble(str) : 0;
	}

	public String getReq0RequestDate() {
		return null != req0RequestDate ? req0RequestDate : "";
	}

	public void setReq0RequestDate(String str) {
		this.req0RequestDate = str;
	}

	public double getReq1PriorYear() {
		return req1PriorYear;
	}

	public void setReq1PriorYear(String str) {
		this.req1PriorYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Year0() {
		return req1Year0;
	}

	public void setReq1Year0(String str) {
		this.req1Year0 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Year1() {
		return req1Year1;
	}

	public void setReq1Year1(String str) {
		this.req1Year1 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Year2() {
		return req1Year2;
	}

	public void setReq1Year2(String str) {
		this.req1Year2 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Year3() {
		return req1Year3;
	}

	public void setReq1Year3(String str) {
		this.req1Year3 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Year4() {
		return req1Year4;
	}

	public void setReq1Year4(String str) {
		this.req1Year4 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1PostYear() {
		return req1PostYear;
	}

	public void setReq1PostYear(String str) {
		this.req1PostYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq1Authorization() {
		return req1Authorization;
	}

	public void setReq1Authorization(String str) {
		this.req1Authorization = null != str ? Double.parseDouble(str) : 0;
	}

	public String getReq1RequestDate() {
		return null != req1RequestDate ? req1RequestDate : "";
	}

	public void setReq1RequestDate(String str) {
		this.req1RequestDate = str;
	}

	public double getReq2PriorYear() {
		return req2PriorYear;
	}

	public void setReq2PriorYear(String str) {
		this.req2PriorYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Year0() {
		return req2Year0;
	}

	public void setReq2Year0(String str) {
		this.req2Year0 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Year1() {
		return req2Year1;
	}

	public void setReq2Year1(String str) {
		this.req2Year1 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Year2() {
		return req2Year2;
	}

	public void setReq2Year2(String str) {
		this.req2Year2 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Year3() {
		return req2Year3;
	}

	public void setReq2Year3(String str) {
		this.req2Year3 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Year4() {
		return req2Year4;
	}

	public void setReq2Year4(String str) {
		this.req2Year4 = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2PostYear() {
		return req2PostYear;
	}

	public void setReq2PostYear(String str) {
		this.req2PostYear = null != str ? Double.parseDouble(str) : 0;
	}

	public double getReq2Authorization() {
		return req2Authorization;
	}

	public void setReq2Authorization(String str) {
		this.req2Authorization = null != str ? Double.parseDouble(str) : 0;
	}

	public String getReq2RequestDate() {
		return null != req2RequestDate ? req2RequestDate : "";
	}

	public void setReq2RequestDate(String str) {
		this.req2RequestDate = str;
	}

	public String getTargetBudget() {
		return targetBudget;
	}

	public void setTargetBudget(String targetBudget) {
		this.targetBudget = targetBudget;
	}

	public double getPriorTotal() {
		return priorTotal;
	}

	public void setPriorTotal(double totalPrior) {
		this.priorTotal = totalPrior;
	}

	public double getYear0Total() {
		return year0Total;
	}

	public void setYear0Total(double totalYear0) {
		this.year0Total = totalYear0;
	}

	public double getYear1Total() {
		return year1Total;
	}

	public void setYear1Total(double totalYear1) {
		this.year1Total = totalYear1;
	}

	public double getYear2Total() {
		return year2Total;
	}

	public void setYear2Total(double totalYear2) {
		this.year2Total = totalYear2;
	}

	public double getYear3Total() {
		return year3Total;
	}

	public void setYear3Total(double totalYear3) {
		this.year3Total = totalYear3;
	}

	public double getYear4Total() {
		return year4Total;
	}

	public void setYear4Total(double totalYear4) {
		this.year4Total = totalYear4;
	}

	public double getPostTotal() {
		return postTotal;
	}

	public void setPostTotal(double totalPost) {
		this.postTotal = totalPost;
	}

	public double getTotalTotal() {
		return totalTotal;
	}

	public void setTotalTotal(double totalTotal) {
		this.totalTotal = totalTotal;
	}

	public double getBaseTotal() {
		return baseTotal;
	}

	public void setBaseTotal(double baseTotal) {
		this.baseTotal = baseTotal;
	}

	public double getRnCTotal() {
		return rncTotal;
	}

	public void setRnCTotal(double rncTotal) {
		this.rncTotal = rncTotal;
	}

	public double getReq0Total() {
		return req0Total;
	}

	public void setReq0Total(double req0Total) {
		this.req0Total = req0Total;
	}

	public double getReq1Total() {
		return req1Total;
	}

	public void setReq1Total(double req1Total) {
		this.req1Total = req1Total;
	}

	public double getReq2Total() {
		return req2Total;
	}

	public void setReq2Total(double req2Total) {
		this.req2Total = req2Total;
	}

}
