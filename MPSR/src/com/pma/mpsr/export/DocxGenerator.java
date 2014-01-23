package com.pma.mpsr.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.javadocx.CreateDocx;
import com.pma.mpsr.MPSRUI;
import com.pma.mpsr.bean.MPSRAppendix;
import com.pma.mpsr.bean.MPSRApprovedChanges;
import com.pma.mpsr.bean.MPSRAttachment;
import com.pma.mpsr.bean.MPSRAuthorization;
import com.pma.mpsr.bean.MPSRBudget;
import com.pma.mpsr.bean.MPSRCostPerformance;
import com.pma.mpsr.bean.MPSRCostReport;
import com.pma.mpsr.bean.MPSRExecutiveSummary;
import com.pma.mpsr.bean.MPSRFunctionPerformance;
import com.pma.mpsr.bean.MPSRInsidePlantConstruction;
import com.pma.mpsr.bean.MPSRInsidePlantDesign;
import com.pma.mpsr.bean.MPSRLPE;
import com.pma.mpsr.bean.MPSRMilestone;
import com.pma.mpsr.bean.MPSROutsidePlantConstruction;
import com.pma.mpsr.bean.MPSROutsidePlantDesign;
import com.pma.mpsr.bean.MPSRPendingChanges;
import com.pma.mpsr.bean.MPSRProcure;
import com.pma.mpsr.bean.MPSRProjectInfo;
import com.pma.mpsr.bean.MPSRSafety;
import com.pma.mpsr.bean.MPSRSchedule;
import com.pma.mpsr.bean.MPSRSchedulePerformance;
import com.pma.mpsr.bean.MPSRStatusSummary;
import com.pma.vaadin.converter.NumberToBigDecimalConverter;

/**
 * Servlet implementation class DocxGenerator
 */
public class DocxGenerator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private static final String FILE_TIMESTAMP_FORMAT = "yyyyMMdd";
	private static final String FILE_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final int FILE_CACHE_DAYS = 1;
	private static final int BUFFER_LENGTH = 32 * 1024;

	private static final String CONTENT_TYPE_WORD_2007_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	private static final String[][] HEADER_1 = { { "val", "1" }, { "sz", "18" } };
	private static final String[][] HEADER_2 = { { "val", "2" }, { "sz", "16" } };
	private static final String[][] HEADER_3 = { { "val", "3" }, { "sz", "14" } };

	private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	private static final NumberFormat INTEGER_FORMATTER = NumberFormat.getIntegerInstance();
	private static final NumberFormat NUMBER_FORMATTER = NumberToBigDecimalConverter.nf;

	private static HashMap<String, String> paramMapHeader1;
	private static HashMap<String, String> paramMapHeader2;
	private static HashMap<String, String> paramMapHeader3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DocxGenerator() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		String mpsrID = request.getParameter("MPSR_ID");
		if (null == mpsrID)
			return;

		String[] texts = null;
		HashMap<String, String> paramMap = null;
		String[][] params = null;
		String[][] cells = null;
		String[][] tableParams = new String[][] { { "border", "single" }, { "jc", "center" } };

		deleteCachedDocx();

		MPSRProjectInfo basicInfo = MPSRProjectInfo.getInstance(mpsrID);
		MPSRExecutiveSummary exeSummary = MPSRExecutiveSummary.getInstance(mpsrID);
		MPSRStatusSummary statusSummary = MPSRStatusSummary.getInstance(mpsrID);
		MPSRFunctionPerformance funcPerform = MPSRFunctionPerformance.getInstance(mpsrID);
		MPSRCostPerformance costPerform = MPSRCostPerformance.getInstance(mpsrID);
		MPSRSchedulePerformance[] schedulePerform = MPSRSchedulePerformance.getInstance(mpsrID);
		MPSRSafety safety = MPSRSafety.getInstance(mpsrID);
		MPSRAuthorization auth = MPSRAuthorization.getInstance(mpsrID);
		MPSRBudget budget = MPSRBudget.getInstance(mpsrID);
		MPSRCostReport[] costRpt = MPSRCostReport.getInstance(mpsrID);
		MPSRSchedule schedule = MPSRSchedule.getInstance(mpsrID);
		MPSRMilestone[] milestones = MPSRMilestone.getInstance(mpsrID);
		MPSRLPE lpe = MPSRLPE.getInstance(mpsrID);
		MPSRInsidePlantDesign[] ipds = MPSRInsidePlantDesign.getInstance(mpsrID);
		MPSROutsidePlantDesign[] opds = MPSROutsidePlantDesign.getInstance(mpsrID);
		MPSRProcure procure = MPSRProcure.getInstance(mpsrID);
		MPSRApprovedChanges[] approvedChanges = MPSRApprovedChanges.getInstance(mpsrID);
		MPSRPendingChanges[] pendingChanges = MPSRPendingChanges.getInstance(mpsrID);
		MPSRInsidePlantConstruction[] ipcs = MPSRInsidePlantConstruction.getInstance(mpsrID);
		MPSROutsidePlantConstruction[] opcs = MPSROutsidePlantConstruction.getInstance(mpsrID);
		MPSRAppendix[] appendixs = MPSRAppendix.getInstance(mpsrID);
		MPSRAttachment[] attachments = MPSRAttachment.getInstance(mpsrID);

		// TODO:
		HashMap<String, String> paramsImage = new HashMap<String, String>();
		// paramsImage.put("scaling", "50");
		paramsImage.put("sizeX", "600");
		paramsImage.put("sizeY", "600");
		paramsImage.put("spacingTop", "0");
		paramsImage.put("spacingBottom", "0");
		paramsImage.put("spacingLeft", "0");
		paramsImage.put("spacingRight", "0");
		paramsImage.put("textWrap", "1");
		paramsImage.put("border", "1");
		paramsImage.put("borderDiscontinuous", "1");
		paramsImage.put("jc", "center");
		// paramsImage.put("dpi", "24");

		CreateDocx docx = new CreateDocx("docx");

		texts = new String[] { "PMA DELIVERY PROJECTS & CONSTRUCTION", "MONTHLY REPORT",
				basicInfo.getReportMonthYear(), };
		params = new String[][] { { "b", "on" }, { "sz", "16" }, { "jc", "center" } };
		for (String text : texts) {
			addContent(docx, text, params);
			docx.addBreak("line");
		}

		paramsImage.put("name", MPSRUI.getFilePath(mpsrID, basicInfo.getFileName()));
		docx.addImage(paramsImage);
		docx.addBreak("line");

		texts = new String[] { basicInfo.getProjectTitle(), "PROJECT No. " + basicInfo.getProjectDefinition() };
		for (String text : texts) {
			addContent(docx, text, params);
		}

		docx.addBreak("line");

		cells = new String[][] { { "Client", basicInfo.getProjectManager() },
				{ "Location", basicInfo.getProjectDirector() } };
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		docx.addBreak("page");

		addContent(docx, "Table of Contents", HEADER_1);
		docx.addTableContents();

		// *******************************************************************************************//

		docx.addBreak("page");

		addTitle(docx, MPSRUI.TREE_1, HEADER_1);

		addTitle(docx, MPSRUI.TREE_1_1, HEADER_2);

		addTitle(docx, MPSRUI.TREE_1_1_1, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_1_1());

		addTitle(docx, MPSRUI.TREE_1_1_2, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_1_2());

		addTitle(docx, MPSRUI.TREE_1_1_3, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_1_3());

		addTitle(docx, MPSRUI.TREE_1_1_4, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_1_4());

		// *******************************************************************************************//
		// 1.2 Status Summary
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_1_2, HEADER_2);
		addContent(docx, basicInfo.getProjectTitle());

		cells = new String[][] {
				{ "Project #: " + basicInfo.getProjectDefinition(), "Status", "Approved (Include RnC)", "To Date",
						"At Completion" },
				{
						"Budget ($M)",
						statusSummary.getBudgetStatus(),
						!"".equals(statusSummary.getBudgetApproved()) ? CURRENCY_FORMATTER.format(Double
								.parseDouble(statusSummary.getBudgetApproved())) : "",
						!"".equals(statusSummary.getBudgetToDate()) ? CURRENCY_FORMATTER.format(Double
								.parseDouble(statusSummary.getBudgetToDate())) : "",
						!"".equals(statusSummary.getBudgetAtCompletion()) ? CURRENCY_FORMATTER.format(Double
								.parseDouble(statusSummary.getBudgetAtCompletion())) : "" },
				{ "Schedule - Duration (months)", statusSummary.getScheduleStatus(),
						statusSummary.getScheduleApproved(), statusSummary.getScheduleToDate(),
						statusSummary.getScheduleAtCompletion() },
				{ "In-Service Date", statusSummary.getInServiceDateStatus(), statusSummary.getInServiceDateApproved(),
						statusSummary.getInServiceDateToDate(), statusSummary.getInServiceDateAtCompletion() } };
		addTable(docx, cells, tableParams);

		addContent(docx, "Legend:");

		cells = new String[][] { { "On Target", "At Risk", "Off Target" }, };
		params = new String[][] { { "border", "single" }, { "jc", "left" } };
		addTable(docx, cells, params);

		// *******************************************************************************************//
		// 1.2.1 Functional Area Performance Indicators
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_1_2_1, HEADER_3);

		cells = new String[][] {
				{ "Activities", "Current Status", "Trend", "Comments (main performance drives)" },
				{ "Project - " + basicInfo.getProjectTitle(), funcPerform.getProjectStatus(),
						funcPerform.getProjectTrend(), funcPerform.getProjectComment() },
				{ "  Target Budget", funcPerform.getTargetBudgetStatus(), funcPerform.getTargetBudgetTrend(),
						funcPerform.getTargetBudgetComment() },
				{ "  Safety", funcPerform.getSafetyStatus(), funcPerform.getSafetyTrend(),
						funcPerform.getSafetyComment() },
				{ "  Licensing & Permitting", funcPerform.getLicensingStatus(), funcPerform.getLicensingTrend(),
						funcPerform.getLicensingComment() },
				{ "  Corporate Properties", funcPerform.getCorporateStatus(), funcPerform.getCorporateTrend(),
						funcPerform.getCorporateComment() },
				{ "  Inside Plant Design & Engineering", funcPerform.getIPDEStatus(), funcPerform.getIPDETrend(),
						funcPerform.getIPDEComment() },
				{ "  Public Outreach", funcPerform.getOutreachStatus(), funcPerform.getOutreachTrend(),
						funcPerform.getOutreachComment() },
				{ "  Procurement", funcPerform.getProcureStatus(), funcPerform.getProcureTrend(),
						funcPerform.getProcureComment() },
				{ "  Construction", funcPerform.getConstructionStatus(), funcPerform.getConstructionTrend(),
						funcPerform.getConstructionComment() },
				{ "  Environmental - Resource Recovery", funcPerform.getEnviromentStatus(),
						funcPerform.getEnviromentTrend(), funcPerform.getEnviromentComment() },
				{ "  Salvage", funcPerform.getSalvageStatus(), funcPerform.getSalvageTrend(),
						funcPerform.getSalvageComment() },
				{ "  Withdrawal", funcPerform.getWithdrawalStatus(), funcPerform.getWithdrawalTrend(),
						funcPerform.getWithdrawalComment() } };
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//

		docx.addBreak("page");

		addTitle(docx, MPSRUI.TREE_1_2_2, HEADER_3);

		paramsImage.put("name", MPSRUI.getFilePath(mpsrID, costPerform.getFileName()));
		docx.addImage(paramsImage);

		// *******************************************************************************************//
		// 1.2.3 Schedule Performance
		// *******************************************************************************************//

		docx.addBreak("page");

		addTitle(docx, MPSRUI.TREE_1_2_3, HEADER_3);

		cells = new String[schedulePerform.length + 1][9];
		cells[0] = new String[] { "WBS", "Original Duration", "Remaining Duration", "Baseline Start Date",
				"Baseline Finish Date", "Start Date", "Finish Date", "Total Float", "Status" };
		for (int i = 0; i < schedulePerform.length; i++) {
			cells[i + 1][0] = schedulePerform[i].getWBS();
			cells[i + 1][1] = Double.toString(schedulePerform[i].getOriginalDuration());
			cells[i + 1][2] = Double.toString(schedulePerform[i].getRemainingDuration());
			cells[i + 1][3] = schedulePerform[i].getPlannedStartDate();
			cells[i + 1][4] = schedulePerform[i].getPlannedFinishDate();
			cells[i + 1][5] = schedulePerform[i].getStartDate();
			cells[i + 1][6] = schedulePerform[i].getFinishDate();
			cells[i + 1][7] = schedulePerform[i].getTotalFloat();
			cells[i + 1][8] = schedulePerform[i].getStatus();
		}
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 1.3 Project Change Control
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_1_3, HEADER_2);

		addTitle(docx, MPSRUI.TREE_1_3_1, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_3_1());

		addTitle(docx, MPSRUI.TREE_1_3_2, HEADER_3);
		addContent(docx, exeSummary.getDesc_1_3_2());

		// *******************************************************************************************//
		// 2 Safety
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_2, HEADER_1);

		addContent(docx, safety.getDesc1());

		cells = new String[][] {
				{ "", "Total Work Hours", "", "" },
				{ "", "Current Period", "Calendar YTD", "Project To Date" },
				{ "", INTEGER_FORMATTER.format(safety.getTotalWorkHoursCurrent()),
						INTEGER_FORMATTER.format(safety.getTotalWorkHoursYTD()),
						INTEGER_FORMATTER.format(safety.getTotalWorkHoursPTD()) },
				{ "Incidents", "", "", "" },
				{ "OSHA Recordable", INTEGER_FORMATTER.format(safety.getOshaRecordableCurrent()),
						INTEGER_FORMATTER.format(safety.getOshaRecordableYTD()),
						INTEGER_FORMATTER.format(safety.getOshaRecordablePTD()) },
				{ "First Aid", INTEGER_FORMATTER.format(safety.getFirstAid1Current()),
						INTEGER_FORMATTER.format(safety.getFirstAid1YTD()),
						INTEGER_FORMATTER.format(safety.getFirstAid1PTD()) },
				{ "Near Miss", INTEGER_FORMATTER.format(safety.getNearMissCurrent()),
						INTEGER_FORMATTER.format(safety.getNearMissYTD()),
						INTEGER_FORMATTER.format(safety.getNearMissPTD()) },
				{ "First Aid", INTEGER_FORMATTER.format(safety.getFirstAid2Current()),
						INTEGER_FORMATTER.format(safety.getFirstAid2YTD()),
						INTEGER_FORMATTER.format(safety.getFirstAid2PTD()) },
				{ "Note - " + safety.getNote(), "", "", "" } };
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 3 Project Authorization
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_3, HEADER_1);

		addContent(docx, auth.getDesc1());

		cells = new String[][] {
				{ "$ Millions", "Prior", auth.getHeaderYear0(), auth.getHeaderYear1(), auth.getHeaderYear2(),
						auth.getHeaderYear3(), auth.getHeaderYear4(), auth.getHeaderPostYear(), "Total", "Auth" },
				{ "Base", NUMBER_FORMATTER.format(auth.getBasePriorYear()),
						NUMBER_FORMATTER.format(auth.getBaseYear0()), NUMBER_FORMATTER.format(auth.getBaseYear1()),
						NUMBER_FORMATTER.format(auth.getBaseYear2()), NUMBER_FORMATTER.format(auth.getBaseYear3()),
						NUMBER_FORMATTER.format(auth.getBaseYear4()), NUMBER_FORMATTER.format(auth.getBasePostYear()),
						NUMBER_FORMATTER.format(auth.getBaseTotal()), "" },
				{ "Risk & Contingency", NUMBER_FORMATTER.format(auth.getRnCPriorYear()),
						NUMBER_FORMATTER.format(auth.getRnCYear0()), NUMBER_FORMATTER.format(auth.getRnCYear1()),
						NUMBER_FORMATTER.format(auth.getRnCYear2()), NUMBER_FORMATTER.format(auth.getRnCYear3()),
						NUMBER_FORMATTER.format(auth.getRnCYear4()), NUMBER_FORMATTER.format(auth.getRnCPostYear()),
						NUMBER_FORMATTER.format(auth.getRnCTotal()), "" },
				{ "Total Project " + auth.getTotalProjectDate(), NUMBER_FORMATTER.format(auth.getPriorTotal()),
						NUMBER_FORMATTER.format(auth.getYear0Total()), NUMBER_FORMATTER.format(auth.getYear1Total()),
						NUMBER_FORMATTER.format(auth.getYear2Total()), NUMBER_FORMATTER.format(auth.getYear3Total()),
						NUMBER_FORMATTER.format(auth.getYear4Total()), NUMBER_FORMATTER.format(auth.getPostTotal()),
						NUMBER_FORMATTER.format(auth.getTotalTotal()), "" },
				{ "Current Request " + auth.getReq0RequestDate(), NUMBER_FORMATTER.format(auth.getReq0PriorYear()),
						NUMBER_FORMATTER.format(auth.getReq0Year0()), NUMBER_FORMATTER.format(auth.getReq0Year1()),
						NUMBER_FORMATTER.format(auth.getReq0Year2()), NUMBER_FORMATTER.format(auth.getReq0Year3()),
						NUMBER_FORMATTER.format(auth.getReq0Year4()), NUMBER_FORMATTER.format(auth.getReq0PostYear()),
						NUMBER_FORMATTER.format(auth.getReq0Total()),
						NUMBER_FORMATTER.format(auth.getReq0Authorization()) },
				{ "Request Date " + auth.getReq1RequestDate(), NUMBER_FORMATTER.format(auth.getReq1PriorYear()),
						NUMBER_FORMATTER.format(auth.getReq1Year0()), NUMBER_FORMATTER.format(auth.getReq1Year1()),
						NUMBER_FORMATTER.format(auth.getReq1Year2()), NUMBER_FORMATTER.format(auth.getReq1Year3()),
						NUMBER_FORMATTER.format(auth.getReq1Year4()), NUMBER_FORMATTER.format(auth.getReq1PostYear()),
						NUMBER_FORMATTER.format(auth.getReq1Total()),
						NUMBER_FORMATTER.format(auth.getReq1Authorization()) },
				{ "Request Date " + auth.getReq2RequestDate(), NUMBER_FORMATTER.format(auth.getReq2PriorYear()),
						NUMBER_FORMATTER.format(auth.getReq2Year0()), NUMBER_FORMATTER.format(auth.getReq2Year1()),
						NUMBER_FORMATTER.format(auth.getReq2Year2()), NUMBER_FORMATTER.format(auth.getReq2Year3()),
						NUMBER_FORMATTER.format(auth.getReq2Year4()), NUMBER_FORMATTER.format(auth.getReq2PostYear()),
						NUMBER_FORMATTER.format(auth.getReq2Total()),
						NUMBER_FORMATTER.format(auth.getReq2Authorization()) },
				{ "Target Budget", "", "", "", "", "", "", "", auth.getTargetBudget(), "" } };
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 4 Project Budget
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_4, HEADER_1);

		addContent(docx, budget.getDesc1());

		cells = new String[][] {
				{ "Current Approved Budget (Base + RnC)", CURRENCY_FORMATTER.format(budget.getPMBudget()) + "M" },
				{ "Approved Phase Funding", CURRENCY_FORMATTER.format(budget.getPMActual()) + "M" },
				{ "Expended To Date", CURRENCY_FORMATTER.format(budget.getPMCommitment()) + "M" },
				{ "At Completion", CURRENCY_FORMATTER.format(budget.getPMAdditionalCost()) + "M" } };
		addTable(docx, cells, tableParams);

		// ----------------------------------------------------
		// table 4.1
		// ----------------------------------------------------

		docx.addBreak("line");

		cells = new String[costRpt.length + 1][5];
		cells[0] = new String[] { "Work Element", "Description", "Estimate", "Actual", "EAC" };
		for (int i = 0; i < costRpt.length; i++) {
			cells[i + 1][0] = costRpt[i].getWbs();
			cells[i + 1][1] = costRpt[i].getWbsDesc();
			cells[i + 1][2] = formatNumberString(costRpt[i].getEstimate());
			cells[i + 1][3] = formatNumberString(costRpt[i].getActual());
			cells[i + 1][4] = formatNumberString(costRpt[i].getEAC());
		}
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 5 Project Schedule
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_5, HEADER_1);

		addTitle(docx, MPSRUI.TREE_5_1, HEADER_2);
		addContent(docx, schedule.getDesc_5_1());

		addTitle(docx, MPSRUI.TREE_5_2, HEADER_2);
		addContent(docx, schedule.getDesc_5_2());

		addTitle(docx, MPSRUI.TREE_5_3, HEADER_2);
		addContent(docx, schedule.getDesc_5_3());

		addTitle(docx, MPSRUI.TREE_5_4, HEADER_2);
		addContent(docx, schedule.getDesc_5_4());

		// ----------------------------------------------------
		// table 5.1
		// ----------------------------------------------------

		cells = new String[milestones.length + 1][3];
		cells[0] = new String[] { "Milestone", "Planned Date", "Finish Date" };
		for (int i = 0; i < milestones.length; i++) {
			cells[i + 1][0] = milestones[i].getMilestone();
			cells[i + 1][1] = milestones[i].getPlannedDate();
			cells[i + 1][2] = milestones[i].getFinishDate();
		}
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 6 License & Permitting, and Environmental Status
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_6, HEADER_1);

		addTitle(docx, MPSRUI.TREE_6_1, HEADER_2);
		addContent(docx, lpe.getDesc_6_1());

		addTitle(docx, MPSRUI.TREE_6_2, HEADER_2);

		addTitle(docx, MPSRUI.TREE_6_2_1, HEADER_3);
		addContent(docx, lpe.getDesc_6_2_1());

		addTitle(docx, MPSRUI.TREE_6_2_2, HEADER_3);
		addContent(docx, lpe.getDesc_6_2_2());

		// *******************************************************************************************//
		// 7 Engineering Status
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_7, HEADER_1);

		addTitle(docx, MPSRUI.TREE_7_1, HEADER_2);

		cells = new String[ipds.length + 1][5];
		cells[0] = new String[] { "Package", "IFR", "IFC", "Indicator", "Note" };
		for (int i = 0; i < ipds.length; i++) {
			cells[i + 1][0] = ipds[i].getPackage();
			cells[i + 1][1] = ipds[i].getIFR();
			cells[i + 1][2] = ipds[i].getIFC();
			cells[i + 1][3] = ipds[i].getIndicator();
			cells[i + 1][4] = ipds[i].getNote();
		}
		addTable(docx, cells, tableParams);

		addTitle(docx, MPSRUI.TREE_7_2, HEADER_2);

		cells = new String[opds.length + 1][5];
		cells[0] = new String[] { "Package", "IFR", "IFC", "Indicator", "Note" };
		for (int i = 0; i < opds.length; i++) {
			cells[i + 1][0] = opds[i].getPackage();
			cells[i + 1][1] = opds[i].getIFR();
			cells[i + 1][2] = opds[i].getIFC();
			cells[i + 1][3] = opds[i].getIndicator();
			cells[i + 1][4] = opds[i].getNote();
		}
		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 8 Procurement Status
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_8, HEADER_1);

		addTitle(docx, MPSRUI.TREE_8_1, HEADER_2);

		addContent(docx, procure.getDesc_8_1_A());

		cells = new String[][] { { "Receipted to Date", CURRENCY_FORMATTER.format(procure.getReceiptedToDate()) },
				{ "Open Commitments", CURRENCY_FORMATTER.format(procure.getOpenCommitment()) },
				{ "Total", CURRENCY_FORMATTER.format(procure.getTotalPurchaseOrder()) } };
		addTable(docx, cells, tableParams);

		addContent(docx, procure.getDesc_8_1_B());

		addTitle(docx, MPSRUI.TREE_8_2, HEADER_2);

		// ----------------------------------------------------
		// section 8.2.1
		// ----------------------------------------------------

		addTitle(docx, MPSRUI.TREE_8_2_1, HEADER_3);
		addContent(docx, procure.getDesc_8_2_1());

		double totalToDate = 0;
		cells = new String[approvedChanges.length + 2][6];
		cells[0] = new String[] { "Purchase Order", "Vendor", "Previous Amount", "This Month Amount",
				"This Month Description", "Total Amount" };
		for (int i = 0; i < approvedChanges.length; i++) {
			cells[i + 1][0] = approvedChanges[i].getPurchaseOrder();
			cells[i + 1][1] = approvedChanges[i].getVendor();
			cells[i + 1][2] = CURRENCY_FORMATTER.format(approvedChanges[i].getPreviousAmount());
			cells[i + 1][3] = CURRENCY_FORMATTER.format(approvedChanges[i].getThisMonthAmount());
			cells[i + 1][4] = approvedChanges[i].getThisMonthDescription();
			cells[i + 1][5] = CURRENCY_FORMATTER.format(approvedChanges[i].getTotalAmount());

			totalToDate += approvedChanges[i].getTotalAmount();
		}
		cells[approvedChanges.length + 1][4] = "Total to Date";
		cells[approvedChanges.length + 1][5] = CURRENCY_FORMATTER.format(totalToDate);

		addTable(docx, cells, tableParams);

		// ----------------------------------------------------
		// section 8.2.2
		// ----------------------------------------------------

		addTitle(docx, MPSRUI.TREE_8_2_2, HEADER_3);
		addContent(docx, procure.getDesc_8_2_2());

		totalToDate = 0;
		cells = new String[pendingChanges.length + 2][6];
		cells[0] = new String[] { "Purchase Order", "Vendor", "Previous Amount", "This Month Amount",
				"This Month Description", "Total Amount" };
		for (int i = 0; i < pendingChanges.length; i++) {
			cells[i + 1][0] = pendingChanges[i].getPurchaseOrder();
			cells[i + 1][1] = pendingChanges[i].getVendor();
			cells[i + 1][2] = CURRENCY_FORMATTER.format(pendingChanges[i].getPreviousAmount());
			cells[i + 1][3] = CURRENCY_FORMATTER.format(pendingChanges[i].getThisMonthAmount());
			cells[i + 1][4] = pendingChanges[i].getThisMonthDescription();
			cells[i + 1][5] = CURRENCY_FORMATTER.format(pendingChanges[i].getTotalAmount());

			totalToDate += pendingChanges[i].getTotalAmount();
		}
		cells[pendingChanges.length + 1][4] = "Total to Date";
		cells[pendingChanges.length + 1][5] = CURRENCY_FORMATTER.format(totalToDate);

		addTable(docx, cells, tableParams);

		// *******************************************************************************************//
		// 9 Construction Status
		// *******************************************************************************************//

		addTitle(docx, MPSRUI.TREE_9, HEADER_1);

		addTitle(docx, MPSRUI.TREE_9_1, HEADER_2);
		params = new String[][] { { "u", "single" } };

		for (int i = 0; i < ipcs.length; i++) {
			addContent(docx, ipcs[i].getWork(), params);
			addContent(docx, ipcs[i].getDescription());

			paramsImage.put("name", MPSRUI.getFilePath(mpsrID, ipcs[i].getFileName()));
			docx.addImage(paramsImage);
			addContent(docx, "Figure " + ipcs[i].getFigure() + " " + ipcs[i].getTitle());
		}

		addTitle(docx, MPSRUI.TREE_9_2, HEADER_2);
		for (int i = 0; i < opcs.length; i++) {
			addContent(docx, opcs[i].getWork(), params);
			addContent(docx, opcs[i].getDescription());

			paramsImage.put("name", MPSRUI.getFilePath(mpsrID, opcs[i].getFileName()));
			docx.addImage(paramsImage);
			addContent(docx, "Figure " + opcs[i].getFigure() + " " + opcs[i].getTitle());
		}

		// *******************************************************************************************//
		// Appendix
		// *******************************************************************************************//

		for (int i = 0; i < appendixs.length; i++) {
			addTitle(docx, "Appendix " + appendixs[i].getNumber() + "  " + appendixs[i].getTitle(), HEADER_1);
		}

		// *******************************************************************************************//
		// Attachment
		// *******************************************************************************************//

		for (int i = 0; i < attachments.length; i++) {
			addTitle(docx, "Attachment " + attachments[i].getNumber() + "  " + attachments[i].getTitle(), HEADER_1);
		}

		// *******************************************************************************************//

		SimpleDateFormat formatter = new SimpleDateFormat(FILE_TIMESTAMP_FORMAT);
		String fileName = "MPSR_" + formatter.format(new Date());
		docx.createDocx(fileName);

		/*
		 * File is generated in C:\Dev_Env\Eclipse or
		 * C:\Dev_Env\wls1211_domain\PSEGWeb_Dev
		 */

		outputDocx(response, fileName);
	}

	private void addTitle(CreateDocx docx, String title, String[][] params) {
		docx.addTitle(toHTMLEntityName(title), toParamMap(params));
	}

	private void addContent(CreateDocx docx, String data) {
		addContent(docx, data, null);
	}

	private void addContent(CreateDocx docx, String data, String[][] params) {
		if (!"".equals(data)) {
			data = removeNonPrintingASCII(data);
			if (!data.startsWith("<")) {

				docx.addText(toHTMLEntityName(data), toParamMap(params));
			} else {

				parseHTMLToDocx(parseHTMLEntityName(data), docx);
			}
		}
	}

	private void addTable(CreateDocx docx, String[][] cells, String[][] params) {
		docx.addTable(toTableValues(cells), toParamMap(params));
	}

	private ArrayList<ArrayList> toTableValues(String[][] cells) {
		ArrayList<ArrayList> valuesTable = new ArrayList<ArrayList>();
		ArrayList<String> row;
		for (int i = 0; i < cells.length; i++) {
			row = new ArrayList<String>();
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = removeNonPrintingASCII(cells[i][j]);
				row.add(toHTMLEntityName(cells[i][j]));
			}
			valuesTable.add(row);
		}
		return valuesTable;
	}

	private HashMap<String, String> toParamMap(String[][] params) {
		if (HEADER_1 == params) {
			if (paramMapHeader1 == null)
				paramMapHeader1 = createParamMap(params);
			return paramMapHeader1;
		} else if (HEADER_2 == params) {
			if (paramMapHeader2 == null)
				paramMapHeader2 = createParamMap(params);
			return paramMapHeader2;
		} else if (HEADER_3 == params) {
			if (paramMapHeader3 == null)
				paramMapHeader3 = createParamMap(params);
			return paramMapHeader3;
		} else {
			return createParamMap(params);
		}
	}

	private HashMap<String, String> createParamMap(String[][] params) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		if (params == null)
			return paramMap;

		for (String[] param : params) {
			paramMap.put(param[0], param[1]);
		}
		return paramMap;
	}

	/**
	 * Delete cached Word files
	 */
	private void deleteCachedDocx() {

		File dir = new File(".");
		String[] list = dir.list();
		for (String fileName : list) {
			if (fileName.endsWith(".docx") && fileName.startsWith("MPSR_")) {

				String strDate = fileName.substring(5, 13);
				Date date = null;
				try {
					date = SIMPLE_DATE_FORMAT.parse(strDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -FILE_CACHE_DAYS);
				Date lastWeek = cal.getTime();

				if (date.before(lastWeek)) {
					System.out.println("delete file:" + fileName);
					new File(fileName).delete();
				}
			}
		}
	}

	private String formatNumberString(String number) {
		if (null != number) {
			return INTEGER_FORMATTER.format(Double.parseDouble(number));
		} else {
			return "";
		}
	}

	private void outputDocx(HttpServletResponse response, String fileName) throws FileNotFoundException, IOException {

		File file = new File(fileName + ".docx");

		response.setContentType(CONTENT_TYPE_WORD_2007_DOCX);
		response.setContentLength((int) file.length());

		FileInputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();

		byte[] bytes = new byte[BUFFER_LENGTH];
		int read = 0;
		while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
			output.write(bytes, 0, read);
			output.flush();
		}

		input.close();
		output.close();
	}

	private void parseHTMLToDocx(String htmlStr, CreateDocx docx) {

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader("<HTML>" + htmlStr + "</HTML>")));
			doc.getDocumentElement().normalize();

			Element element = doc.getDocumentElement();
			NodeList nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if ("P".equals(node.getNodeName())) {
					docx.addText(node.getTextContent());
				} else if ("UL".equals(node.getNodeName())) {
					NodeList nodeList2 = node.getChildNodes();
					ArrayList<String> list = new ArrayList<String>();
					for (int j = 0; j < nodeList2.getLength(); j++) {
						Node node2 = nodeList2.item(j);
						if ("LI".equals(node2.getNodeName()))
							list.add(node2.getTextContent());
					}
					HashMap paramsList = new HashMap();
					paramsList.put("val", "1");
					docx.addList(list, paramsList);
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String removeNonPrintingASCII(String str) {
		return null != str ? str.replaceAll("[\\x13]", "-").replaceAll("[\\x19]", "'")
				.replaceAll("[\\x00-\\x12\\x14-\\x18\\x1a-\\x1f]", "") : "";
	}

	private String toHTMLEntityName(String str) {
		return null != str ? str.replace("&", "&amp;") : "";
	}

	private String parseHTMLEntityName(String str) {
		return null != str ? str.replace("&nbsp;", " ") : "";
	}

	private void printASCII(char[] charArray) {
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] < 32 || charArray[i] > 126) {
				System.out.println(charArray[i] + " : " + (int) charArray[i] + " : "
						+ Integer.toHexString(charArray[i]) + " : " + Integer.toOctalString(charArray[i]));
			}
		}
	}
}
