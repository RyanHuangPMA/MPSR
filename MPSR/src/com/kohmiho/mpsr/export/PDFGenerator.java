package com.kohmiho.mpsr.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfPageLabels;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.kohmiho.mpsr.MPSRUI;
import com.kohmiho.mpsr.bean.MPSRAppendix;
import com.kohmiho.mpsr.bean.MPSRApprovedChanges;
import com.kohmiho.mpsr.bean.MPSRAttachment;
import com.kohmiho.mpsr.bean.MPSRAuthorization;
import com.kohmiho.mpsr.bean.MPSRBudget;
import com.kohmiho.mpsr.bean.MPSRCostPerformance;
import com.kohmiho.mpsr.bean.MPSRCostReport;
import com.kohmiho.mpsr.bean.MPSRExecutiveSummary;
import com.kohmiho.mpsr.bean.MPSRFunctionPerformance;
import com.kohmiho.mpsr.bean.MPSRInsidePlantConstruction;
import com.kohmiho.mpsr.bean.MPSRInsidePlantDesign;
import com.kohmiho.mpsr.bean.MPSRLPE;
import com.kohmiho.mpsr.bean.MPSRMilestone;
import com.kohmiho.mpsr.bean.MPSROutsidePlantConstruction;
import com.kohmiho.mpsr.bean.MPSROutsidePlantDesign;
import com.kohmiho.mpsr.bean.MPSRPendingChanges;
import com.kohmiho.mpsr.bean.MPSRProcure;
import com.kohmiho.mpsr.bean.MPSRProjectInfo;
import com.kohmiho.mpsr.bean.MPSRSafety;
import com.kohmiho.mpsr.bean.MPSRSchedule;
import com.kohmiho.mpsr.bean.MPSRSchedulePerformance;
import com.kohmiho.mpsr.bean.MPSRStatusSummary;
import com.kohmiho.vaadin.converter.NumberToBigDecimalConverter;

/**
 * Servlet implementation class PDFGenerator
 */
public class PDFGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Rectangle DEFAULT_PAGE_SIZE = PageSize.LETTER;
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	private static final NumberFormat INTEGER_FORMATTER = NumberFormat.getIntegerInstance();
	private static final NumberFormat NUMBER_FORMATTER = NumberToBigDecimalConverter.nf;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PDFGenerator() {

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

		// response.setContentType("application/pdf");
		// response.setHeader("Content-Disposition",
		// "attachment; filename=\"test.pdf\"");

		Font fontNormal = null;
		Font fontCoverTitle = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
		Font fontCoverTableHeader = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
		Font fontCoverTableContent = new Font(FontFamily.HELVETICA, 16);
		Font fontChapterHeader = new Font(FontFamily.HELVETICA, 18, Font.BOLD);
		Font fontSection1Header = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
		Font fontSection2Header = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
		Font fontWork = new Font(FontFamily.HELVETICA, 12, Font.UNDERLINE);

		Font fontTableCaption = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font fontTableColumnHeader = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		Font fontTableColumnHeaderYellow = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		fontTableColumnHeaderYellow.setColor(BaseColor.YELLOW);
		Font fontTableRowTitle = new Font(FontFamily.HELVETICA, 12, Font.ITALIC);
		Font fontTableCellBold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		Font fontTableNote = new Font(FontFamily.HELVETICA, 10, Font.ITALIC);
		Font fontImageCaption = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD);

		Font fontPOTableHeader = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
		fontPOTableHeader.setColor(BaseColor.YELLOW);
		Font fontPOTable = new Font(FontFamily.HELVETICA, 9);
		Font fontPOTableSummary = new Font(FontFamily.HELVETICA, 9, Font.BOLD);

		Font fontPageHeader = new Font(FontFamily.HELVETICA, 8);
		Font fontPageFooter = new Font(FontFamily.HELVETICA, 10);

		Phrase[] headerTexts = new Phrase[] { new Phrase("ELECTRIC DELIVERY", fontPageHeader),
				new Phrase("DELIVERY PROJECTS & CONSTRUCTION (DP&C)", fontPageHeader),
				new Phrase("PROJECT MONTHLY REPORT", fontPageHeader) };

		Paragraph paragraph = null;
		PdfPTable table = null;
		PdfPCell cell = null;

		Document document = new Document(DEFAULT_PAGE_SIZE, 36, 36, 9 * 10, 36);
		ByteArrayOutputStream baosOrig = new ByteArrayOutputStream();
		XMLWorkerHelper xmlWorker = XMLWorkerHelper.getInstance();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, baosOrig);
			writer.setStrictImageSequence(true);
			writer.setLinearPageMode();
			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines | PdfWriter.FitWindow);

			Image icon = getEmbeddedImage(getServletContext(), "/resource/image/company_logo.png");

			document.open();

			PdfOutline root = writer.getRootOutline();

			// *******************************************************************************************//

			// addOutline(root, writer, MPSRUI.TREE_0);

			for (int i = 0; i < 2; i++) {
				document.add(new Paragraph(" "));
			}

			String[] titles = { " DELIVERY PROJECTS & CONSTRUCTION", "MONTHLY REPORT",
					basicInfo.getReportMonthYear(), basicInfo.getProjectTitle(),
					"PROJECT No. " + basicInfo.getProjectDefinition() };
			int[] spaceingAfters = { 48, 48, 48, 24, 48 };
			for (int i = 0; i < titles.length; i++) {
				paragraph = new Paragraph();
				paragraph.setSpacingAfter(spaceingAfters[i]);
				paragraph.setFont(fontCoverTitle);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.add(new Chunk(titles[i]));
				document.add(paragraph);
			}

			addCoverImage(document, MPSRUI.getFilePath(mpsrID, basicInfo.getFileName()));

			document.add(new Paragraph(" "));

			table = new PdfPTable(2);
			table.addCell(new Phrase("Client", fontCoverTableHeader));
			table.addCell(new Phrase(basicInfo.getProjectManager(), fontCoverTableContent));
			table.addCell(new Phrase("Location", fontCoverTableHeader));
			table.addCell(new Phrase(basicInfo.getProjectDirector(), fontCoverTableContent));
			document.add(table);

			// *******************************************************************************************//

			document.resetPageCount();
			document.add(Chunk.NEXTPAGE);
			writer.setPageEvent(new MPSRPageEventHelper(icon, headerTexts, fontPageFooter));

			// *******************************************************************************************//

			PdfOutline outline1 = addOutline(root, writer, MPSRUI.TREE_1);
			addSectionTitle(document, MPSRUI.TREE_1, fontChapterHeader, 0, 0, 12);

			PdfOutline outline2 = addOutline(outline1, writer, "1.1");

			addOutline(outline2, writer, MPSRUI.TREE_1_1_1);
			addSectionTitle(document, MPSRUI.TREE_1_1_1, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_1_1(), fontNormal, 12, 0, 12);

			addOutline(outline2, writer, MPSRUI.TREE_1_1_2);
			addSectionTitle(document, MPSRUI.TREE_1_1_2, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_1_2(), fontNormal, 12, 0, 12);

			addOutline(outline2, writer, MPSRUI.TREE_1_1_3);
			addSectionTitle(document, MPSRUI.TREE_1_1_3, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_1_3(), fontNormal, 12, 0, 12);

			addOutline(outline2, writer, MPSRUI.TREE_1_1_4);
			addSectionTitle(document, MPSRUI.TREE_1_1_4, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_1_4(), fontNormal, 12, 0, 12);

			// *******************************************************************************************//
			// 1.2 Status Summary
			// *******************************************************************************************//

			outline2 = addOutline(outline1, writer, MPSRUI.TREE_1_2);
			addSectionTitle(document, MPSRUI.TREE_1_2, fontSection1Header, 6, 12, 12);

			paragraph = new Paragraph();
			paragraph.add(basicInfo.getProjectTitle());
			paragraph.setSpacingAfter(4);
			document.add(paragraph);

			table = new PdfPTable(5);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 3, 1, 1, 1, 1 });

			cell = new PdfPCell(new Phrase("Project #: " + basicInfo.getProjectDefinition(), fontTableColumnHeader));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			String[] headers = { "Status", "Approved (Include RnC)", "To Date", "At Completion" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeader));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			String[][] cellValues = {
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
					{ "In-Service Date", statusSummary.getInServiceDateStatus(),
							statusSummary.getInServiceDateApproved(), statusSummary.getInServiceDateToDate(),
							statusSummary.getInServiceDateAtCompletion() } };
			for (int i = 0; i < cellValues.length; i++) {
				table.addCell(new Phrase(cellValues[i][0]));
				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setBackgroundColor(getBackgroundColor(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				for (int j = 2; j < 5; j++) {
					cell = new PdfPCell(new Phrase(cellValues[i][j]));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
			}

			document.add(table);

			// -------------------------------------------------------------------//

			paragraph = new Paragraph("Legend:");
			paragraph.setSpacingAfter(4);
			paragraph.setIndentationLeft(25);
			document.add(paragraph);

			table = new PdfPTable(3);
			table.setTotalWidth(240);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] legends = { "On Target", "At Risk", "Off Target" };
			for (String legend : legends) {
				cell = new PdfPCell(new Phrase(legend));
				cell.setBackgroundColor(getBackgroundColor(legend));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setMinimumHeight(18);
				cell.setPadding(0);
				table.addCell(cell);
			}

			paragraph = new Paragraph();
			paragraph.add(table);
			paragraph.setIndentationLeft(25);
			document.add(paragraph);

			// *******************************************************************************************//
			// 1.2.1 Functional Area Performance Indicators
			// *******************************************************************************************//

			addOutline(outline2, writer, MPSRUI.TREE_1_2_1);
			addSectionTitle(document, MPSRUI.TREE_1_2_1, fontSection2Header, 12, 12, 12);

			table = new PdfPTable(4);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 4, 1, 1, 4 });

			headers = new String[] { "Activities", "Current Status", "Trend", "Comments (main performance drives)" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeader));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			Image imgArrowUp = getEmbeddedImage(getServletContext(), "/VAADIN/themes/MPSR/img/Arrow_Up.png");
			Image imgArrowDown = getEmbeddedImage(getServletContext(), "/VAADIN/themes/MPSR/img/Arrow_Down.png");
			Image imgArrowEven = getEmbeddedImage(getServletContext(), "/VAADIN/themes/MPSR/img/Arrow_Even.png");
			Image[] images = { imgArrowUp, imgArrowDown, imgArrowEven };
			for (Image image : images) {
				image.scaleToFit(12, 12);
			}

			cellValues = new String[][] {
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
			for (int i = 0; i < cellValues.length; i++) {
				cell = new PdfPCell(new Phrase(cellValues[i][0]));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setBackgroundColor(getBackgroundColor(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if ("Up".equals(cellValues[i][2])) {
					cell = new PdfPCell(imgArrowUp);
				} else if ("Down".equals(cellValues[i][2])) {
					cell = new PdfPCell(imgArrowDown);
				} else if ("Even".equals(cellValues[i][2])) {
					cell = new PdfPCell(imgArrowEven);
				} else {
					cell = new PdfPCell();
				}

				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][3]));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}

			document.add(table);

			// *******************************************************************************************//

			document.setPageSize(DEFAULT_PAGE_SIZE.rotate());
			document.add(Chunk.NEXTPAGE);

			addOutline(outline2, writer, MPSRUI.TREE_1_2_2);
			addSectionTitle(document, MPSRUI.TREE_1_2_2, fontSection2Header, 12, 12, 12);
			addImage(document, MPSRUI.getFilePath(mpsrID, costPerform.getFileName()));

			// *******************************************************************************************//
			// 1.2.3 Schedule Performance
			// *******************************************************************************************//

			document.add(Chunk.NEXTPAGE);

			addOutline(outline2, writer, MPSRUI.TREE_1_2_3);
			addSectionTitle(document, MPSRUI.TREE_1_2_3, fontSection2Header, 12, 12, 12);

			table = new PdfPTable(9);
			table.setTotalWidth(750);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 3, 1, 1, 1, 1, 1, 1, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "WBS", "Original Duration", "Remaining Duration", "Baseline Start Date",
					"Baseline Finish Date", "Start Date", "Finish Date", "Total Float", "Status" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeaderYellow));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			for (int i = 0; i < schedulePerform.length; i++) {
				cell = new PdfPCell(new Phrase(schedulePerform[i].getWBS()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(Double.toString(schedulePerform[i].getOriginalDuration())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(Double.toString(schedulePerform[i].getRemainingDuration())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getPlannedStartDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getPlannedFinishDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getStartDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getFinishDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getTotalFloat()));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(schedulePerform[i].getStatus()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			table.setHeaderRows(1);

			document.add(table);

			// *******************************************************************************************//
			// 1.3 Project Change Control
			// *******************************************************************************************//

			document.setPageSize(DEFAULT_PAGE_SIZE);
			document.add(Chunk.NEXTPAGE);

			outline2 = addOutline(outline1, writer, MPSRUI.TREE_1_3);
			addSectionTitle(document, MPSRUI.TREE_1_3, fontSection1Header, 6, 12, 12);

			addOutline(outline2, writer, MPSRUI.TREE_1_3_1);
			addSectionTitle(document, MPSRUI.TREE_1_3_1, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_3_1(), fontNormal, 12, 0, 12);

			addOutline(outline2, writer, MPSRUI.TREE_1_3_2);
			addSectionTitle(document, MPSRUI.TREE_1_3_2, fontSection2Header, 12, 12, 0);
			addParagraph(document, writer, xmlWorker, exeSummary.getDesc_1_3_2(), fontNormal, 12, 0, 12);

			// *******************************************************************************************//
			// 2 Safety
			// *******************************************************************************************//

			addOutline(root, writer, MPSRUI.TREE_2);
			addSectionTitle(document, MPSRUI.TREE_2, fontChapterHeader, 0, 24, 12);
			addParagraph(document, writer, xmlWorker, safety.getDesc1(), fontNormal, 12, 0, 12);

			paragraph = new Paragraph("Table 2.1 Project Safety Summary", fontTableCaption);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingAfter(6);
			document.add(paragraph);

			table = new PdfPTable(3);
			table.setTotalWidth(300);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 1, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_RIGHT);

			cell = new PdfPCell(new Phrase("Total Work Hours", fontTableColumnHeaderYellow));
			cell.setColspan(3);
			cell.setBackgroundColor(BaseColor.BLUE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			headers = new String[] { "Current Period", "Calendar YTD", "Project To Date" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeader));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			cellValues = new String[][] { { INTEGER_FORMATTER.format(safety.getTotalWorkHoursCurrent()),
					INTEGER_FORMATTER.format(safety.getTotalWorkHoursYTD()),
					INTEGER_FORMATTER.format(safety.getTotalWorkHoursPTD()) } };
			for (int i = 0; i < cellValues.length; i++) {
				cell = new PdfPCell(new Phrase(cellValues[i][0]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][2], fontTableCellBold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			document.add(table);

			// -----------------------------------

			table = new PdfPTable(4);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 2, 1, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_RIGHT);

			cell = new PdfPCell(new Phrase("Incidents", fontTableColumnHeaderYellow));
			cell.setColspan(4);
			cell.setBackgroundColor(BaseColor.BLUE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cellValues = new String[][] {
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
							INTEGER_FORMATTER.format(safety.getFirstAid2PTD()) } };
			for (int i = 0; i < cellValues.length; i++) {
				cell = new PdfPCell(new Phrase(cellValues[i][0], fontTableRowTitle));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][2]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][3], fontTableCellBold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			cell = new PdfPCell(new Phrase("*Note - " + safety.getNote(), fontTableNote));
			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table.addCell(cell);

			document.add(table);

			// *******************************************************************************************//
			// 3 Project Authorization
			// *******************************************************************************************//

			addOutline(root, writer, MPSRUI.TREE_3);
			addSectionTitle(document, MPSRUI.TREE_3, fontChapterHeader, 0, 24, 12);
			addParagraph(document, writer, xmlWorker, auth.getDesc1(), fontNormal, 12, 0, 12);

			table = new PdfPTable(10);
			table.setSpacingBefore(6);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 3, 1, 1, 1, 1, 1, 1, 2, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "$ Millions", "Prior", auth.getHeaderYear0(), auth.getHeaderYear1(),
					auth.getHeaderYear2(), auth.getHeaderYear3(), auth.getHeaderYear4(), auth.getHeaderPostYear(),
					"Total", "Auth" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeader));
				cell.setBackgroundColor(BaseColor.YELLOW);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			cellValues = new String[][] {
					{ "Base", NUMBER_FORMATTER.format(auth.getBasePriorYear()),
							NUMBER_FORMATTER.format(auth.getBaseYear0()), NUMBER_FORMATTER.format(auth.getBaseYear1()),
							NUMBER_FORMATTER.format(auth.getBaseYear2()), NUMBER_FORMATTER.format(auth.getBaseYear3()),
							NUMBER_FORMATTER.format(auth.getBaseYear4()),
							NUMBER_FORMATTER.format(auth.getBasePostYear()),
							NUMBER_FORMATTER.format(auth.getBaseTotal()), "" },
					{ "Risk & Contingency", NUMBER_FORMATTER.format(auth.getRnCPriorYear()),
							NUMBER_FORMATTER.format(auth.getRnCYear0()), NUMBER_FORMATTER.format(auth.getRnCYear1()),
							NUMBER_FORMATTER.format(auth.getRnCYear2()), NUMBER_FORMATTER.format(auth.getRnCYear3()),
							NUMBER_FORMATTER.format(auth.getRnCYear4()),
							NUMBER_FORMATTER.format(auth.getRnCPostYear()),
							NUMBER_FORMATTER.format(auth.getRnCTotal()), "" },
					{ "Total Project " + auth.getTotalProjectDate(), NUMBER_FORMATTER.format(auth.getPriorTotal()),
							NUMBER_FORMATTER.format(auth.getYear0Total()),
							NUMBER_FORMATTER.format(auth.getYear1Total()),
							NUMBER_FORMATTER.format(auth.getYear2Total()),
							NUMBER_FORMATTER.format(auth.getYear3Total()),
							NUMBER_FORMATTER.format(auth.getYear4Total()),
							NUMBER_FORMATTER.format(auth.getPostTotal()),
							NUMBER_FORMATTER.format(auth.getTotalTotal()), "" },
					{ "Current Request " + auth.getReq0RequestDate(), NUMBER_FORMATTER.format(auth.getReq0PriorYear()),
							NUMBER_FORMATTER.format(auth.getReq0Year0()), NUMBER_FORMATTER.format(auth.getReq0Year1()),
							NUMBER_FORMATTER.format(auth.getReq0Year2()), NUMBER_FORMATTER.format(auth.getReq0Year3()),
							NUMBER_FORMATTER.format(auth.getReq0Year4()),
							NUMBER_FORMATTER.format(auth.getReq0PostYear()),
							NUMBER_FORMATTER.format(auth.getReq0Total()),
							NUMBER_FORMATTER.format(auth.getReq0Authorization()) },
					{ "Request Date " + auth.getReq1RequestDate(), NUMBER_FORMATTER.format(auth.getReq1PriorYear()),
							NUMBER_FORMATTER.format(auth.getReq1Year0()), NUMBER_FORMATTER.format(auth.getReq1Year1()),
							NUMBER_FORMATTER.format(auth.getReq1Year2()), NUMBER_FORMATTER.format(auth.getReq1Year3()),
							NUMBER_FORMATTER.format(auth.getReq1Year4()),
							NUMBER_FORMATTER.format(auth.getReq1PostYear()),
							NUMBER_FORMATTER.format(auth.getReq1Total()),
							NUMBER_FORMATTER.format(auth.getReq1Authorization()) },
					{ "Request Date " + auth.getReq2RequestDate(), NUMBER_FORMATTER.format(auth.getReq2PriorYear()),
							NUMBER_FORMATTER.format(auth.getReq2Year0()), NUMBER_FORMATTER.format(auth.getReq2Year1()),
							NUMBER_FORMATTER.format(auth.getReq2Year2()), NUMBER_FORMATTER.format(auth.getReq2Year3()),
							NUMBER_FORMATTER.format(auth.getReq2Year4()),
							NUMBER_FORMATTER.format(auth.getReq2PostYear()),
							NUMBER_FORMATTER.format(auth.getReq2Total()),
							NUMBER_FORMATTER.format(auth.getReq2Authorization()) },
					{ "Target Budget", "", "", "", "", "", "", "", auth.getTargetBudget(), "" } };
			for (int i = 0; i < cellValues.length; i++) {

				cell = new PdfPCell(new Phrase(cellValues[i][0]));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				for (int j = 1; j < cellValues[i].length; j++) {
					cell = new PdfPCell(new Phrase(cellValues[i][j]));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
			}

			document.add(table);

			// *******************************************************************************************//
			// 4 Project Budget
			// *******************************************************************************************//

			addOutline(root, writer, MPSRUI.TREE_4);
			addSectionTitle(document, MPSRUI.TREE_4, fontChapterHeader, 0, 24, 12);
			addParagraph(document, writer, xmlWorker, budget.getDesc1(), fontNormal, 12, 0, 12);

			table = new PdfPTable(2);
			table.setSpacingBefore(6);
			table.setTotalWidth(350);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 5, 2 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			cellValues = new String[][] {
					{ "Current Approved Budget (Base + RnC)", CURRENCY_FORMATTER.format(budget.getPMBudget()) + "M" },
					{ "Approved Phase Funding", CURRENCY_FORMATTER.format(budget.getPMActual()) + "M" },
					{ "Expended To Date", CURRENCY_FORMATTER.format(budget.getPMCommitment()) + "M" },
					{ "At Completion", CURRENCY_FORMATTER.format(budget.getPMAdditionalCost()) + "M" } };
			for (int i = 0; i < cellValues.length; i++) {
				cell = new PdfPCell(new Phrase(cellValues[i][0]));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			document.add(table);

			// ----------------------------------------------------
			// table 4.1
			// ----------------------------------------------------

			paragraph = new Paragraph("Table 4.1 Cost Report Summary", fontTableCaption);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingAfter(6);
			document.add(paragraph);

			table = new PdfPTable(5);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 1, 1, 1, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Work Element", "Description", "Estimate", "Actual", "EAC" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeaderYellow));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			for (int i = 0; i < costRpt.length; i++) {
				cell = new PdfPCell(new Phrase(costRpt[i].getWbs()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(costRpt[i].getWbsDesc()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(formatNumberString(costRpt[i].getEstimate())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(formatNumberString(costRpt[i].getActual())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(formatNumberString(costRpt[i].getEAC())));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			document.add(table);

			// *******************************************************************************************//
			// 5 Project Schedule
			// *******************************************************************************************//

			outline1 = addOutline(root, writer, MPSRUI.TREE_5);
			addSectionTitle(document, MPSRUI.TREE_5, fontChapterHeader, 0, 24, 12);

			addOutline(outline1, writer, MPSRUI.TREE_5_1);
			addSectionTitle(document, MPSRUI.TREE_5_1, fontSection1Header, 6, 12, 12);
			addParagraph(document, writer, xmlWorker, schedule.getDesc_5_1(), fontNormal, 12, 0, 12);

			addOutline(outline1, writer, MPSRUI.TREE_5_2);
			addSectionTitle(document, MPSRUI.TREE_5_2, fontSection1Header, 6, 12, 12);
			addParagraph(document, writer, xmlWorker, schedule.getDesc_5_2(), fontNormal, 12, 0, 12);

			addOutline(outline1, writer, MPSRUI.TREE_5_3);
			addSectionTitle(document, MPSRUI.TREE_5_3, fontSection1Header, 6, 12, 12);
			addParagraph(document, writer, xmlWorker, schedule.getDesc_5_3(), fontNormal, 12, 0, 12);

			addOutline(outline1, writer, MPSRUI.TREE_5_4);
			addSectionTitle(document, MPSRUI.TREE_5_4, fontSection1Header, 6, 12, 12);
			addParagraph(document, writer, xmlWorker, schedule.getDesc_5_4(), fontNormal, 12, 0, 12);

			// ----------------------------------------------------
			// table 5.1
			// ----------------------------------------------------

			paragraph = new Paragraph("Table 5.1 Planned Scorecard Milestones", fontTableCaption);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingAfter(6);
			document.add(paragraph);

			table = new PdfPTable(3);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 3, 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Milestone", "Planned Date", "Finish Date" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeaderYellow));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			for (int i = 0; i < milestones.length; i++) {
				cell = new PdfPCell(new Phrase(milestones[i].getMilestone()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(milestones[i].getPlannedDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(milestones[i].getFinishDate()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			document.add(table);

			// *******************************************************************************************//
			// 6 License & Permitting, and Environmental Status
			// *******************************************************************************************//

			outline1 = addOutline(root, writer, MPSRUI.TREE_6);
			addSectionTitle(document, MPSRUI.TREE_6, fontChapterHeader, 0, 24, 12);

			addOutline(outline1, writer, MPSRUI.TREE_6_1);
			addSectionTitle(document, MPSRUI.TREE_6_1, fontSection1Header, 6, 12, 12);
			addParagraph(document, writer, xmlWorker, lpe.getDesc_6_1(), fontNormal, 12, 0, 12);

			outline2 = addOutline(outline1, writer, MPSRUI.TREE_6_2);
			addSectionTitle(document, MPSRUI.TREE_6_2, fontSection1Header, 6, 12, 12);

			addOutline(outline2, writer, MPSRUI.TREE_6_2_1);
			addSectionTitle(document, MPSRUI.TREE_6_2_1, fontSection2Header, 12, 12, 12);
			addParagraph(document, writer, xmlWorker, lpe.getDesc_6_2_1(), fontNormal, 12, 0, 12);

			addOutline(outline2, writer, MPSRUI.TREE_6_2_2);
			addSectionTitle(document, MPSRUI.TREE_6_2_2, fontSection2Header, 12, 12, 12);
			addParagraph(document, writer, xmlWorker, lpe.getDesc_6_2_2(), fontNormal, 12, 0, 12);

			// *******************************************************************************************//
			// 7 Engineering Status
			// *******************************************************************************************//

			outline1 = addOutline(root, writer, MPSRUI.TREE_7);
			addSectionTitle(document, MPSRUI.TREE_7, fontChapterHeader, 0, 24, 12);

			addOutline(outline1, writer, MPSRUI.TREE_7_1);
			addSectionTitle(document, MPSRUI.TREE_7_1, fontSection1Header, 6, 12, 12);

			table = new PdfPTable(5);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 2, 1, 1, 1, 3 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Package", "IFR", "IFC", "Indicator", "Note" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeaderYellow));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			for (int i = 0; i < ipds.length; i++) {
				cell = new PdfPCell(new Phrase(ipds[i].getPackage()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(ipds[i].getIFR()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(ipds[i].getIFC()));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(ipds[i].getIndicator()));
				cell.setBackgroundColor(getBackgroundColor(ipds[i].getIndicator()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(ipds[i].getNote()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);
			}

			document.add(table);

			// -----------------------------------------------------------------------------------//

			addOutline(outline1, writer, MPSRUI.TREE_7_2);
			addSectionTitle(document, MPSRUI.TREE_7_2, fontSection1Header, 6, 12, 12);

			table = new PdfPTable(5);
			table.setTotalWidth(500);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 2, 1, 1, 1, 3 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Package", "IFR", "IFC", "Indicator", "Note" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontTableColumnHeaderYellow));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			for (int i = 0; i < opds.length; i++) {
				cell = new PdfPCell(new Phrase(opds[i].getPackage()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(opds[i].getIFR()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(opds[i].getIFC()));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(opds[i].getIndicator()));
				cell.setBackgroundColor(getBackgroundColor(opds[i].getIndicator()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(opds[i].getNote()));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);
			}

			document.add(table);

			// *******************************************************************************************//
			// 8 Procurement Status
			// *******************************************************************************************//

			outline1 = addOutline(root, writer, MPSRUI.TREE_8);
			addSectionTitle(document, MPSRUI.TREE_8, fontChapterHeader, 0, 24, 12);

			outline2 = addOutline(outline1, writer, MPSRUI.TREE_8_1);
			addSectionTitle(document, MPSRUI.TREE_8_1, fontSection1Header, 6, 12, 12);

			table = new PdfPTable(2);
			table.setTotalWidth(300);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 1, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			cellValues = new String[][] {
					{ "Receipted to Date", CURRENCY_FORMATTER.format(procure.getReceiptedToDate()) },
					{ "Open Commitments", CURRENCY_FORMATTER.format(procure.getOpenCommitment()) },
					{ "Total", CURRENCY_FORMATTER.format(procure.getTotalPurchaseOrder()) } };
			for (int i = 0; i < cellValues.length; i++) {
				cell = new PdfPCell(new Phrase(cellValues[i][0]));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cellValues[i][1]));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			document.add(table);

			addParagraph(document, writer, xmlWorker, procure.getDesc_8_1_B(), fontNormal, 12, 12, 12);

			outline2 = addOutline(outline1, writer, MPSRUI.TREE_8_2);
			addSectionTitle(document, MPSRUI.TREE_8_2, fontSection1Header, 6, 12, 12);

			// ----------------------------------------------------
			// section 8.2.1
			// ----------------------------------------------------

			addOutline(outline2, writer, MPSRUI.TREE_8_2_1);
			addSectionTitle(document, MPSRUI.TREE_8_2_1, fontSection2Header, 12, 12, 12);
			addParagraph(document, writer, xmlWorker, procure.getDesc_8_2_1(), fontNormal, 12, 0, 12);

			table = new PdfPTable(6);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 1, 2, 1, 1, 2, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Purchase Order", "Vendor", "Previous Amount", "This Month Amount",
					"This Month Description", "Total Amount" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontPOTableHeader));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			double totalToDate = 0;

			for (int i = 0; i < approvedChanges.length; i++) {
				cell = new PdfPCell(new Phrase(approvedChanges[i].getPurchaseOrder(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(approvedChanges[i].getVendor(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(approvedChanges[i].getPreviousAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(approvedChanges[i].getThisMonthAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(approvedChanges[i].getThisMonthDescription(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(approvedChanges[i].getTotalAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				totalToDate += approvedChanges[i].getTotalAmount();
			}

			document.add(table);

			table = new PdfPTable(2);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 7, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell = new PdfPCell(new Phrase("Total To Date", fontPOTableSummary));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(totalToDate), fontPOTableSummary));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table.addCell(cell);

			document.add(table);

			// ----------------------------------------------------
			// section 8.2.2
			// ----------------------------------------------------

			addOutline(outline2, writer, MPSRUI.TREE_8_2_2);
			addSectionTitle(document, MPSRUI.TREE_8_2_2, fontSection2Header, 12, 12, 12);
			addParagraph(document, writer, xmlWorker, procure.getDesc_8_2_2(), fontNormal, 12, 0, 12);

			table = new PdfPTable(6);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 1, 2, 1, 1, 2, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			headers = new String[] { "Purchase Order", "Vendor", "Previous Amount", "This Month Amount",
					"This Month Description", "Total Amount" };
			for (String header : headers) {
				cell = new PdfPCell(new Phrase(header, fontPOTableHeader));
				cell.setBackgroundColor(BaseColor.BLUE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

			totalToDate = 0;

			for (int i = 0; i < pendingChanges.length; i++) {
				cell = new PdfPCell(new Phrase(pendingChanges[i].getPurchaseOrder(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(pendingChanges[i].getVendor(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(pendingChanges[i].getPreviousAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(pendingChanges[i].getThisMonthAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(pendingChanges[i].getThisMonthDescription(), fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(pendingChanges[i].getTotalAmount()),
						fontPOTable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				table.addCell(cell);

				totalToDate += pendingChanges[i].getTotalAmount();
			}

			document.add(table);

			table = new PdfPTable(2);
			table.setTotalWidth(550);
			table.setLockedWidth(true);
			table.setWidths(new int[] { 7, 1 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell = new PdfPCell(new Phrase("Total To Date", fontPOTableSummary));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(CURRENCY_FORMATTER.format(totalToDate), fontPOTableSummary));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table.addCell(cell);

			document.add(table);

			// *******************************************************************************************//
			// 9 Construction Status
			// *******************************************************************************************//

			outline1 = addOutline(root, writer, MPSRUI.TREE_9);
			addSectionTitle(document, MPSRUI.TREE_9, fontChapterHeader, 0, 24, 12);

			addOutline(outline1, writer, MPSRUI.TREE_9_1);
			addSectionTitle(document, MPSRUI.TREE_9_1, fontSection1Header, 6, 12, 12);

			for (int i = 0; i < ipcs.length; i++) {
				addParagraph(document, writer, xmlWorker, ipcs[i].getWork(), fontWork, 12, 12, 0);
				addParagraph(document, writer, xmlWorker, ipcs[i].getDescription(), fontNormal, 12, 0, 0);
				addImage(document, MPSRUI.getFilePath(mpsrID, ipcs[i].getFileName()), "Figure " + ipcs[i].getFigure()
						+ " " + ipcs[i].getTitle(), fontImageCaption, 12, 3, 12);
			}

			// -----------------------------------------------------------------------------------------//

			addOutline(outline1, writer, MPSRUI.TREE_9_2);
			addSectionTitle(document, MPSRUI.TREE_9_2, fontSection1Header, 6, 12, 12);

			for (int i = 0; i < opcs.length; i++) {
				addParagraph(document, writer, xmlWorker, opcs[i].getWork(), fontWork, 12, 12, 0);
				addParagraph(document, writer, xmlWorker, opcs[i].getDescription(), fontNormal, 12, 0, 0);
				addImage(document, MPSRUI.getFilePath(mpsrID, opcs[i].getFileName()), "Figure " + opcs[i].getFigure()
						+ " " + opcs[i].getTitle(), fontImageCaption, 12, 3, 12);
			}

			// *******************************************************************************************//
			// Appendix
			// *******************************************************************************************//

			if (appendixs.length > 0) {

				document.add(Chunk.NEXTPAGE);
				outline1 = addOutline(root, writer, MPSRUI.TREE_APPENDIX);

				for (int i = 0; i < appendixs.length; i++) {
					addOutline(outline1, writer,
							"Appendix " + appendixs[i].getNumber() + "  " + appendixs[i].getTitle());
					addSectionTitle(document, "Appendix " + appendixs[i].getNumber() + "  " + appendixs[i].getTitle(),
							fontChapterHeader, 0, 12, 12);
					addParagraph(document, writer, xmlWorker, appendixs[i].getDescription(), fontNormal, 12, 0, 0);

					importPages(document, writer, mpsrID, appendixs[i].getFileName());
				}
			}

			// *******************************************************************************************//
			// Attachment
			// *******************************************************************************************//

			if (attachments.length > 0) {

				document.setPageSize(DEFAULT_PAGE_SIZE);
				document.add(Chunk.NEXTPAGE);
				outline1 = addOutline(root, writer, MPSRUI.TREE_ATTACHMENT);

				for (int i = 0; i < attachments.length; i++) {
					addOutline(outline1, writer,
							"Attachment " + attachments[i].getNumber() + "  " + attachments[i].getTitle());
					addSectionTitle(document,
							"Attachment " + attachments[i].getNumber() + "  " + attachments[i].getTitle(),
							fontChapterHeader, 0, 12, 12);
					addParagraph(document, writer, xmlWorker, attachments[i].getDescription(), fontNormal, 12, 0, 0);

					importPages(document, writer, mpsrID, attachments[i].getFileName());
				}
			}

			document.close();

			// *******************************************************************************************//

			// retrieve outline
			PdfReader readerOrig = new PdfReader(baosOrig.toByteArray());
			// int origNumberOfPages = readerOrig.getNumberOfPages();
			ColumnText ct = new ColumnText(null);
			ct.addElement(new Paragraph("Table of Contents", fontCoverTitle));
			List<HashMap<String, Object>> bookmarkList = SimpleBookmark.getBookmark(readerOrig);
			fillColumnText(bookmarkList, ct, 18, 0, fontSection2Header);

			// generate TOC after cover page
			ByteArrayOutputStream baosTOC = new ByteArrayOutputStream();
			PdfStamper stamperTOC = new PdfStamper(readerOrig, baosTOC);
			int tocPageEnd = 1;
			while (true) {
				stamperTOC.insertPage(++tocPageEnd, readerOrig.getPageSize(1));

				PdfContentByte underContent = stamperTOC.getUnderContent(tocPageEnd);

				generatePageHeader(document, underContent, icon, headerTexts);

				ct.setCanvas(stamperTOC.getOverContent(tocPageEnd));
				ct.setSimpleColumn(36, 36, 558, 693);
				if (!ColumnText.hasMoreText(ct.go()))
					break;
			}
			stamperTOC.close();

			// change page labels
			PdfPageLabels labels = new PdfPageLabels();
			labels.addPageLabel(1, PdfPageLabels.LOWERCASE_ROMAN_NUMERALS);
			labels.addPageLabel(tocPageEnd + 1, PdfPageLabels.DECIMAL_ARABIC_NUMERALS);

			// add labels
			ByteArrayOutputStream baosFinal = new ByteArrayOutputStream();
			PdfReader readerTOC = new PdfReader(baosTOC.toByteArray());
			// String selectPages = String.format("1-%s,%s-%s", tocPageEnd,
			// tocPageEnd + 1, origNumberOfPages + (tocPageEnd - 1));
			// readerTOC.selectPages(selectPages);
			PdfStamper stamperFinal = new PdfStamper(readerTOC, baosFinal);
			stamperFinal.getWriter().setPageLabels(labels);
			stamperFinal.close();

			// *******************************************************************************************//

			// send final PDF to browser
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setContentLength(baosFinal.size());

			OutputStream os = response.getOutputStream();
			os.write(baosFinal.toByteArray());
			os.flush();
			os.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	protected void importPages(Document document, PdfWriter writer, String mpsrID, String fileName)
			throws DocumentException {
		if (null != fileName) {
			String filePath = MPSRUI.getFilePath(mpsrID, fileName);

			PdfReader reader = null;
			try {
				reader = new PdfReader(filePath);
			} catch (IOException e) {
				document.add(new Paragraph(String.format("*** Can not load file %s ***", filePath)));
				// e.printStackTrace();
			}

			if (null != reader) {
				for (int pageNum = 1; pageNum <= reader.getNumberOfPages(); pageNum++) {

					document.setPageSize(reader.getPageSize(pageNum));
					document.newPage();

					PdfImportedPage page = writer.getImportedPage(reader, pageNum);

					// addPageByImage(writer, page);
					addPageByTemplate(writer, page);

					document.setPageSize(DEFAULT_PAGE_SIZE);
					document.newPage();
				}
			}
		}
	}

	protected void addPageByImage(PdfWriter writer, PdfImportedPage page) throws BadElementException, DocumentException {
		Image image = Image.getInstance(page);
		image.setAbsolutePosition(36, 36);
		image.scalePercent(90);
		writer.getDirectContentUnder().addImage(image);
		writer.setPageEmpty(false);
	}

	protected void addPageByTemplate(PdfWriter writer, PdfImportedPage page) {
		PdfContentByte directContentUnder = writer.getDirectContentUnder();
		// directContentUnder.addTemplate(page, 0.9f, 0, 0, 0.9f, 0, 0);
		directContentUnder.addTemplate(page, 0, 0);
	}

	@SuppressWarnings("unchecked")
	protected void fillColumnText(List<HashMap<String, Object>> bookmarkList, ColumnText ct, float leftIndent,
			int depth, Font font) {
		if (null != bookmarkList) {
			for (int i = 0; i < bookmarkList.size(); i++) {
				HashMap<String, Object> bookmark = bookmarkList.get(i);

				String title = (String) bookmark.get("Title");
				String pageNum = ((String) bookmark.get("Page")).split(" ")[0];
				Paragraph paragraph = null != font ? new Paragraph(title, font) : new Paragraph(title);
				// paragraph.add(new Chunk(new VerticalPositionMark()));
				paragraph.add(new Chunk(new DottedLineSeparator()));
				paragraph.add(Integer.toString((Integer.parseInt(pageNum) - 1)));
				paragraph.setIndentationLeft(leftIndent * depth);
				paragraph.setSpacingBefore(0 == depth ? 9 : 0);
				ct.addElement(paragraph);

				fillColumnText(((List<HashMap<String, Object>>) bookmark.get("Kids")), ct, leftIndent, depth + 1, null);
			}
		}
	}

	private PdfOutline addOutline(PdfOutline parent, PdfWriter writer, String title) {

		return new PdfOutline(parent, new PdfDestination(PdfDestination.XYZ, 0, writer.getVerticalPosition(true), 0),
				title, true);
	}

	private void addSectionTitle(Document document, String sectionTitle, Font fontChapterHeader, int indentation,
			int spacingBefore, int spacingAfter) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		paragraph.setSpacingBefore(spacingBefore);
		paragraph.setSpacingAfter(spacingAfter);
		paragraph.setIndentationLeft(indentation);
		paragraph.setFont(fontChapterHeader);
		paragraph.add(new Phrase(sectionTitle));
		document.add(paragraph);
	}

	private void addParagraph(Document document, PdfWriter writer, XMLWorkerHelper xmlWorker, String data, Font font,
			int indentationLeft, int spacingBefore, int spacingAfter) throws DocumentException, IOException {

		if (!data.startsWith("<")) {
			Paragraph paragraph = new Paragraph();
			paragraph.setIndentationLeft(indentationLeft);
			paragraph.setSpacingBefore(spacingBefore);
			paragraph.setSpacingAfter(spacingAfter);
			if (null != font)
				paragraph.setFont(font);

			paragraph.add(data);
			document.add(paragraph);
		} else {
			xmlWorker.parseXHtml(writer, document, new StringReader(data));
		}
	}

	private void addImage(Document document, String filename, String caption, Font font, int indentationLeft,
			int spacingBefore, int spacingAfter) throws BadElementException, MalformedURLException, IOException,
			DocumentException {

		if (null == filename) {
			addParagraph(document, null, null, String.format("[*** No Picture for %s ***]", caption), font,
					indentationLeft, 0, 0);
			return;
		}

		try {
			Paragraph paragraph = new Paragraph();
			paragraph.setIndentationLeft(indentationLeft);
			paragraph.setSpacingBefore(spacingBefore);
			paragraph.setSpacingAfter(spacingAfter);
			if (null != font)
				paragraph.setFont(font);
			paragraph.setKeepTogether(true);
			paragraph.setAlignment(Element.ALIGN_CENTER);

			Image image = Image.getInstance(filename);
			image.scaleToFit(400, 400);

			paragraph.add(new Chunk(image, 0, 0, true));
			paragraph.add(new Chunk("\n"));
			paragraph.add(caption);
			document.add(paragraph);
		} catch (Exception e) {
			addParagraph(document, null, null, String.format("[*** Unable to render picture for %s ***]", caption),
					font, indentationLeft, 0, 0);
		}
	}

	private void addImage(Document document, String filename) throws BadElementException, MalformedURLException,
			IOException, DocumentException {

		if (null == filename) {
			addParagraph(document, null, null, String.format("[*** No Picture for %s ***]", ""), null, 0, 0, 0);
			return;
		}

		try {
			Image image = Image.getInstance(filename);
			image.scaleToFit(700, 500);
			image.setAlignment(Element.ALIGN_CENTER);
			// image.setRotationDegrees(90);
			document.add(image);

		} catch (Exception e) {
			addParagraph(document, null, null, String.format("[*** Unable to render picture for %s ***]", ""), null, 0,
					0, 0);
		}
	}

	private void addCoverImage(Document document, String filename) throws BadElementException, MalformedURLException,
			IOException, DocumentException {

		if (null != filename) {
			try {
				Image image = Image.getInstance(filename);
				image.scaleToFit(300, 300);
				image.setAlignment(Element.ALIGN_CENTER);
				document.add(image);

			} catch (Exception e) {
				System.err.println("Can not load image " + filename);
				// e.printStackTrace();
			}
		}
	}

	private Image getEmbeddedImage(ServletContext servletContext, String path) throws BadElementException,
			MalformedURLException, IOException {
		// return Image.getInstance(servletContext.getRealPath(path));
		return Image.getInstance(IOUtils.toByteArray(servletContext.getResourceAsStream(path)));
	}

	private String formatNumberString(String number) {
		if (null != number) {
			return INTEGER_FORMATTER.format(Double.parseDouble(number));
		} else {
			return "";
		}
	}

	private BaseColor getBackgroundColor(String str) {
		if ("Green".equals(str) || "On Target".equals(str)) {
			return BaseColor.GREEN;
		} else if ("Yellow".equals(str) || "At Risk".equals(str)) {
			return BaseColor.YELLOW;
		} else if ("Red".equals(str) || "Off Target".equals(str)) {
			return BaseColor.RED;
		}
		return null;
	}

	private void generatePageHeader(Document document, PdfContentByte directContentUnder, Image image,
			Phrase[] headerTexts) {

		float left = document.getPageSize().getLeft(36);
		float top = document.getPageSize().getTop(36);
		float right = document.getPageSize().getRight(36);

		try {
			image.setAbsolutePosition(right - 150, top - 20);
			// image.setAlignment(Element.ALIGN_RIGHT);
			image.scalePercent(25);
			directContentUnder.addImage(image);
		} catch (DocumentException e) {
		}

		int i = 0;
		for (Phrase headerText : headerTexts) {
			ColumnText.showTextAligned(directContentUnder, Element.ALIGN_LEFT, headerText, left + 36, top - 10 * (i++),
					0);
		}
	}

	private class MPSRPageEventHelper extends PdfPageEventHelper {

		private Image image;
		private Font fontPageFooter;
		private Phrase[] headerTexts;

		public MPSRPageEventHelper(Image image, Phrase[] headerTexts, Font fontPageFooter) {
			this.image = image;
			this.fontPageFooter = fontPageFooter;
			this.headerTexts = headerTexts;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {

			PdfContentByte directContentUnder = writer.getDirectContentUnder();

			generatePageHeader(document, directContentUnder, image, headerTexts);

			// add page number
			ColumnText.showTextAligned(directContentUnder, Element.ALIGN_CENTER,
					new Phrase("Page " + document.getPageNumber(), fontPageFooter),
					(document.getPageSize().getLeft() + document.getPageSize().getRight()) / 2, 18, 0);
		}

	}

}
