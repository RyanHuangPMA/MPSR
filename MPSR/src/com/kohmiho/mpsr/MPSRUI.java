package com.kohmiho.mpsr;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.db.DBConfig;
import com.kohmiho.db.MyDBConfig;
import com.kohmiho.mpsr.export.PDFGenerator;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnHeaderMode;

@SuppressWarnings("serial")
@Theme("MPSR")
@Title("MPSR")
public class MPSRUI extends UI {

	private static final String FILE_STORE_PATH = "mpsr_upload";
	// private static final String FILE_STORE_PATH =
	// "/export/home/pseg_apps/P6EPPM_1/api/mpsr_upload";

	public static final String TREE_0 = "Cover";
	public static final String TREE_1 = "1 Executive Summary";
	public static final String TREE_1_1 = "1.1 General Description & Key Issues";
	public static final String TREE_1_1_1 = "1.1.1 General Project Description";
	public static final String TREE_1_1_2 = "1.1.2 Overall Project Status Summary";
	public static final String TREE_1_1_3 = "1.1.3 Key Issue Resolution";
	public static final String TREE_1_1_4 = "1.1.4 Project Outlook";
	public static final String TREE_1_2 = "1.2 Status Summary";
	public static final String TREE_1_2_1 = "1.2.1 Functional Area Performance Indicators";
	public static final String TREE_1_2_2 = "1.2.2 Project Cost Performance";
	public static final String TREE_1_2_3 = "1.2.3 Project Schedule Performance";
	public static final String TREE_1_3 = "1.3 Project Change Control";
	public static final String TREE_1_3_1 = "1.3.1 Project Change Requests for Reporting Month";
	public static final String TREE_1_3_2 = "1.3.2 Project Scope Change Requests for Reporting Month";
	public static final String TREE_2 = "2 Safety";
	public static final String TREE_3 = "3 Project Authorization";
	public static final String TREE_4 = "4 Project Budget";
	public static final String TREE_5 = "5 Project Schedule";
	public static final String TREE_5_1 = "5.1 Critical Path Analysis";
	public static final String TREE_5_2 = "5.2 Current and Anticipated Delaying Problems";
	public static final String TREE_5_3 = "5.3 Major Planned Work & Milestones for Next Period";
	public static final String TREE_5_4 = "5.4 Schedule Status";
	public static final String TREE_6 = "6 License & Permitting, and Environmental Status";
	public static final String TREE_6_1 = "6.1 L&P";
	public static final String TREE_6_2 = "6.2 Environmental";
	public static final String TREE_6_2_1 = "6.2.1 Issues";
	public static final String TREE_6_2_2 = "6.2.2 Environmental Compliance";
	public static final String TREE_7 = "7 Engineering Status";
	public static final String TREE_7_1 = "7.1 Inside Plant Design";
	public static final String TREE_7_2 = "7.2 Outside Plant Design";
	public static final String TREE_8 = "8 Procurement Status";
	public static final String TREE_8_1 = "8.1 Purchase Order Management";
	public static final String TREE_8_2 = "8.2 Change Management";
	public static final String TREE_8_2_1 = "8.2.1 Approved Changes (PO Increases)";
	public static final String TREE_8_2_2 = "8.2.2 Pending Change Orders";
	public static final String TREE_9 = "9 Construction Status";
	public static final String TREE_9_1 = "9.1 Inside Plant Construction";
	public static final String TREE_9_2 = "9.2 Outside Plant Construction";
	public static final String TREE_APPENDIX = "Appendix";
	public static final String TREE_ATTACHMENT = "Attachment";

	public static final ThemeResource ICON_ARROW_EVEN = new ThemeResource("img/Arrow_Even.png");
	public static final ThemeResource ICON_ARROW_DOWN = new ThemeResource("img/Arrow_Down.png");
	public static final ThemeResource ICON_ARROW_UP = new ThemeResource("img/Arrow_Up.png");
	public static final ThemeResource ICON_CIRCLE_GREEN = new ThemeResource("img/Circle_Green.png");
	public static final ThemeResource ICON_CIRCLE_YELLOW = new ThemeResource("img/Circle_Yellow.png");
	public static final ThemeResource ICON_CIRCLE_RED = new ThemeResource("img/Circle_Red.png");

	private static final Object[] VISIBLE_COLUMNS = { "MPSR_ID", "PROJ_DEF", "PROJ_TITLE" };
	private static final String[] COLUMN_HEADERS = { "No.", "Proj Def", "Title" };

	JDBCConnectionPool pool = createConnectionPool();
	SQLContainer project = createContainer("MPSR", "VERSION");
	SQLContainer executiveSummary = createContainer("MPSR_EXE_SUM", "VERSION");
	SQLContainer statusSummary = createContainer("MPSR_STATUS_SUM", "VERSION");
	SQLContainer functionalPerformance = createContainer("MPSR_FUNC_PERF", "VERSION");
	SQLContainer costPerformance = createContainer("MPSR_COST_PERF", "VERSION");
	SQLContainer schedulePerformance = createContainer("MPSR_SCHE_PERF", "VERSION");
	SQLContainer safety = createContainer("MPSR_SAFE", "VERSION");
	SQLContainer authorization = createContainer("MPSR_AUTH", "VERSION");
	SQLContainer budget = createContainer("MPSR_BUDGET", "VERSION");
	SQLContainer costRpt = createContainer("MPSR_COST_RPT", "VERSION");
	SQLContainer schedule = createContainer("MPSR_SCHEDULE", "VERSION");
	SQLContainer milestone = createContainer("MPSR_MILESTONE", "VERSION");
	SQLContainer lpe = createContainer("MPSR_LPE", "VERSION");
	SQLContainer ipd = createContainer("MPSR_ENG_IP", "VERSION");
	SQLContainer opd = createContainer("MPSR_ENG_OP", "VERSION");
	SQLContainer procure = createContainer("MPSR_PROCURE", "VERSION");
	SQLContainer approvedChanges = createContainer("MPSR_APPR_CO", "VERSION");
	SQLContainer pendingChanges = createContainer("MPSR_PEND_CO", "VERSION");
	SQLContainer ipc = createContainer("MPSR_CONST_IP", "VERSION");
	SQLContainer opc = createContainer("MPSR_CONST_OP", "VERSION");
	SQLContainer appendix = createContainer("MPSR_APDX", "VERSION");
	SQLContainer attach = createContainer("MPSR_ATCH", "VERSION");

	// TODO:
	SQLContainer[] containers = { executiveSummary, statusSummary, functionalPerformance, costPerformance,
			schedulePerformance, safety, authorization, budget, costRpt, schedule, milestone, lpe, ipd, opd, procure,
			approvedChanges, pendingChanges, ipc, opc, appendix, attach };

	HorizontalSplitPanel splitPanel1 = new HorizontalSplitPanel();
	HorizontalSplitPanel splitPanel2 = new HorizontalSplitPanel();

	Table mpsrTable = new Table();
	TextField searchField = new TextField();
	Button addNewMPSRButton = new Button("New");
	Button removeMPSRButton = new Button("Remove");
	Button saveMPSRButton = new Button("Save");
	Link pdflink = new Link("Export PDF", null);
	// Link wordLink = new Link("Export Word", null);
	Tree tree = new Tree();

	PageWelcome welcomePage = new PageWelcome();
	PageBlank blankPage = new PageBlank();
	PageUnderConstruction pageConstruction = new PageUnderConstruction();
	Form_0 form_0 = new Form_0(mpsrTable);
	Form_1 form_1_1_1 = new Form_1(TREE_1_1_1, "DESC_1_1_1");
	Form_1 form_1_1_2 = new Form_1(TREE_1_1_2, "DESC_1_1_2");
	Form_1 form_1_1_3 = new Form_1(TREE_1_1_3, "DESC_1_1_3");
	Form_1 form_1_1_4 = new Form_1(TREE_1_1_4, "DESC_1_1_4");
	Form_1_2_0 form_1_2 = new Form_1_2_0();
	Form_1_2_1 form_1_2_1 = new Form_1_2_1();
	Form_1_2_2 form_1_2_2 = new Form_1_2_2(mpsrTable);
	Form_1_2_3 form_1_2_3 = new Form_1_2_3(schedulePerformance);
	Form_1 form_1_3_1 = new Form_1(TREE_1_3_1, "DESC_1_3_1");
	Form_1 form_1_3_2 = new Form_1(TREE_1_3_2, "DESC_1_3_2");
	Form_2_0_0 form_2 = new Form_2_0_0();
	Form_3_0_0 form_3 = new Form_3_0_0();
	Form_4_0_0 form_4 = new Form_4_0_0();
	Form_1 form_5_1 = new Form_1(TREE_5_1, "DESC_5_1");
	Form_1 form_5_2 = new Form_1(TREE_5_2, "DESC_5_2");
	Form_1 form_5_3 = new Form_1(TREE_5_3, "DESC_5_3");
	Form_5_4_0 form_5_4 = new Form_5_4_0(milestone);
	Form_1 form_6_1 = new Form_1(TREE_6_1, "DESC_6_1");
	Form_1 form_6_2_1 = new Form_1(TREE_6_2_1, "DESC_6_2_1");
	Form_1 form_6_2_2 = new Form_1(TREE_6_2_2, "DESC_6_2_2");
	Form_7 form_7_1 = new Form_7(TREE_7_1, ipd, mpsrTable);
	Form_7 form_7_2 = new Form_7(TREE_7_2, opd, mpsrTable);
	Form_8_1_0 form_8_1 = new Form_8_1_0();
	Form_8_2 form_8_2_1 = new Form_8_2(TREE_8_2_1, "DESC_8_2_1", approvedChanges, mpsrTable);
	Form_8_2 form_8_2_2 = new Form_8_2(TREE_8_2_2, "DESC_8_2_2", pendingChanges, mpsrTable);
	Form_9 form_9_1 = new Form_9(TREE_9_1, ipc, mpsrTable);
	Form_9 form_9_2 = new Form_9(TREE_9_2, opc, mpsrTable);
	Form_A form_appendix = new Form_A(TREE_APPENDIX, appendix, mpsrTable);
	Form_A form_attach = new Form_A(TREE_ATTACHMENT, attach, mpsrTable);

	Form_Test form_test = new Form_Test();

	@Override
	protected void init(VaadinRequest request) {
		initLayout();
		initContainerRef();
		initTree();
		initMPSRTable();
		initSearch();
		initButton();
	}

	private void initContainerRef() {

		for (SQLContainer sqlContainer : containers) {
			project.addReference(sqlContainer, "MPSR_ID", "MPSR_ID");
		}
	}

	private void initLayout() {

		setContent(splitPanel1);

		VerticalLayout tableLayout = new VerticalLayout();
		splitPanel1.setFirstComponent(tableLayout);
		splitPanel1.setSecondComponent(splitPanel2);
		splitPanel1.setSplitPosition(30); // percentage

		VerticalLayout treeLayout = new VerticalLayout();
		splitPanel2.setFirstComponent(treeLayout);
		splitPanel2.setSecondComponent(welcomePage);
		splitPanel2.setSplitPosition(30);

		Label labelPrototype = new Label("Prototype");
		labelPrototype.setStyleName("prototype2");

		tree.setSizeFull();

		treeLayout.addComponent(labelPrototype);
		treeLayout.addComponent(tree);
		treeLayout.setSizeFull();
		treeLayout.setExpandRatio(tree, 1);

		// ***************************************************//

		HorizontalLayout tableLayoutTop = new HorizontalLayout();
		tableLayout.addComponent(tableLayoutTop);

		tableLayoutTop.addComponent(pdflink);
		// tableLayoutTop.addComponent(wordLink);
		tableLayoutTop.addComponent(saveMPSRButton);
		tableLayoutTop.addComponent(removeMPSRButton);

		pdflink.setTargetName("_blank");
		// wordLink.setTargetName("_blank");

		tableLayout.addComponent(mpsrTable);

		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		tableLayout.addComponent(bottomLeftLayout);

		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewMPSRButton);

		tableLayout.setSizeFull();
		tableLayout.setExpandRatio(mpsrTable, 1);
		tableLayoutTop.setWidth("100%");
		mpsrTable.setSizeFull();
		bottomLeftLayout.setWidth("100%");
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

	}

	private void initMPSRTable() {

		mpsrTable.setContainerDataSource(project);
		mpsrTable.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		mpsrTable.setVisibleColumns(VISIBLE_COLUMNS);
		mpsrTable.setColumnHeaders(COLUMN_HEADERS);

		mpsrTable.setSelectable(true);
		mpsrTable.setImmediate(true);
		mpsrTable.setColumnReorderingAllowed(true);

		mpsrTable.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				if (null != mpsrTable.getValue()) {
					pdflink.setResource(new ExternalResource(PDFGenerator.class.getSimpleName() + "?MPSR_ID="
							+ mpsrTable.getValue().toString()));
					// wordLink.setResource(new
					// ExternalResource(DocxGenerator.class.getSimpleName() +
					// "?MPSR_ID="
					// + mpsrTable.getValue().toString()));
				} else {
					pdflink.setResource(null);
					// wordLink.setResource(null);
				}

				if (null == mpsrTable.getValue() || null == tree.getValue()) {
					splitPanel2.setSecondComponent(welcomePage);
					return;
				}

				setEditPage();

				mpsrTable.refreshRowCache();

				// TODO:
				form_7_1.setFieldsVisible(false);
				form_7_2.setFieldsVisible(false);
				form_8_2_1.setFieldsVisible(false);
				form_8_2_2.setFieldsVisible(false);
				form_9_1.setFieldsVisible(false);
				form_9_2.setFieldsVisible(false);
				form_appendix.setFieldsVisible(false);
				form_attach.setFieldsVisible(false);

				form_7_1.unSelectTable();
				form_7_2.unSelectTable();
				form_8_2_1.unSelectTable();
				form_8_2_2.unSelectTable();
				form_9_1.unSelectTable();
				form_9_2.unSelectTable();
				form_appendix.unSelectTable();
				form_attach.unSelectTable();
			}
		});

	}

	private void initTree() {

		tree.addItem(TREE_0);
		tree.setChildrenAllowed(TREE_0, false);

		tree.addItem(TREE_1);
		tree.addItem(TREE_1_1);
		tree.addItem(TREE_1_1_1);
		tree.addItem(TREE_1_1_2);
		tree.addItem(TREE_1_1_3);
		tree.addItem(TREE_1_1_4);
		tree.addItem(TREE_1_2);
		tree.addItem(TREE_1_2_1);
		tree.addItem(TREE_1_2_2);
		tree.addItem(TREE_1_2_3);
		tree.addItem(TREE_1_3);
		tree.addItem(TREE_1_3_1);
		tree.addItem(TREE_1_3_2);
		tree.setParent(TREE_1_1, TREE_1);
		tree.setParent(TREE_1_1_1, TREE_1_1);
		tree.setParent(TREE_1_1_2, TREE_1_1);
		tree.setParent(TREE_1_1_3, TREE_1_1);
		tree.setParent(TREE_1_1_4, TREE_1_1);
		tree.setParent(TREE_1_2, TREE_1);
		tree.setParent(TREE_1_2_1, TREE_1_2);
		tree.setParent(TREE_1_2_2, TREE_1_2);
		tree.setParent(TREE_1_2_3, TREE_1_2);
		tree.setParent(TREE_1_3, TREE_1);
		tree.setParent(TREE_1_3_1, TREE_1_3);
		tree.setParent(TREE_1_3_2, TREE_1_3);
		tree.setChildrenAllowed(TREE_1_1_1, false);
		tree.setChildrenAllowed(TREE_1_1_2, false);
		tree.setChildrenAllowed(TREE_1_1_3, false);
		tree.setChildrenAllowed(TREE_1_1_4, false);
		tree.setChildrenAllowed(TREE_1_2_1, false);
		tree.setChildrenAllowed(TREE_1_2_2, false);
		tree.setChildrenAllowed(TREE_1_2_3, false);
		tree.setChildrenAllowed(TREE_1_3_1, false);
		tree.setChildrenAllowed(TREE_1_3_2, false);

		tree.expandItem(TREE_1);

		tree.addItem(TREE_2);
		tree.setChildrenAllowed(TREE_2, false);

		tree.addItem(TREE_3);
		tree.setChildrenAllowed(TREE_3, false);

		tree.addItem(TREE_4);
		tree.setChildrenAllowed(TREE_4, false);

		tree.addItem(TREE_5);
		tree.addItem(TREE_5_1);
		tree.addItem(TREE_5_2);
		tree.addItem(TREE_5_3);
		tree.addItem(TREE_5_4);
		tree.setParent(TREE_5_1, TREE_5);
		tree.setParent(TREE_5_2, TREE_5);
		tree.setParent(TREE_5_3, TREE_5);
		tree.setParent(TREE_5_4, TREE_5);
		tree.setChildrenAllowed(TREE_5_1, false);
		tree.setChildrenAllowed(TREE_5_2, false);
		tree.setChildrenAllowed(TREE_5_3, false);
		tree.setChildrenAllowed(TREE_5_4, false);

		tree.addItem(TREE_6);
		tree.addItem(TREE_6_1);
		tree.addItem(TREE_6_2);
		tree.addItem(TREE_6_2_1);
		tree.addItem(TREE_6_2_2);
		tree.setParent(TREE_6_1, TREE_6);
		tree.setParent(TREE_6_2, TREE_6);
		tree.setParent(TREE_6_2_1, TREE_6_2);
		tree.setParent(TREE_6_2_2, TREE_6_2);
		tree.setChildrenAllowed(TREE_6_1, false);
		tree.setChildrenAllowed(TREE_6_2_1, false);
		tree.setChildrenAllowed(TREE_6_2_2, false);

		tree.addItem(TREE_7);
		tree.addItem(TREE_7_1);
		tree.addItem(TREE_7_2);
		tree.setParent(TREE_7_1, TREE_7);
		tree.setParent(TREE_7_2, TREE_7);
		tree.setChildrenAllowed(TREE_7_1, false);
		tree.setChildrenAllowed(TREE_7_2, false);

		tree.addItem(TREE_8);
		tree.addItem(TREE_8_1);
		tree.addItem(TREE_8_2);
		tree.addItem(TREE_8_2_1);
		tree.addItem(TREE_8_2_2);
		tree.setParent(TREE_8_1, TREE_8);
		tree.setParent(TREE_8_2, TREE_8);
		tree.setParent(TREE_8_2_1, TREE_8_2);
		tree.setParent(TREE_8_2_2, TREE_8_2);
		tree.setChildrenAllowed(TREE_8_1, false);
		tree.setChildrenAllowed(TREE_8_2_1, false);
		tree.setChildrenAllowed(TREE_8_2_2, false);

		tree.addItem(TREE_9);
		tree.addItem(TREE_9_1);
		tree.addItem(TREE_9_2);
		tree.setParent(TREE_9_1, TREE_9);
		tree.setParent(TREE_9_2, TREE_9);
		tree.setChildrenAllowed(TREE_9_1, false);
		tree.setChildrenAllowed(TREE_9_2, false);

		tree.addItem(TREE_APPENDIX);
		tree.setChildrenAllowed(TREE_APPENDIX, false);

		tree.addItem(TREE_ATTACHMENT);
		tree.setChildrenAllowed(TREE_ATTACHMENT, false);

		tree.setImmediate(true);

		tree.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				if (null == tree.getValue() || null == mpsrTable.getValue()) {
					splitPanel2.setSecondComponent(welcomePage);
					return;
				}

				setEditPage();
			}
		});
	}

	private void setEditPage() {

		String treeValue = tree.getValue().toString();
		Object tableValue = mpsrTable.getValue();
		Compare.Equal filter = new Compare.Equal("MPSR_ID", tableValue.toString());

		if (TREE_0.equals(treeValue)) {
			form_0.fieldGroup.setItemDataSource(project.getItem(tableValue));
			form_0.showImage();
			splitPanel2.setSecondComponent(form_0);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_1.equals(treeValue)) {
			tree.setValue(TREE_1_1_1);
			tree.expandItem(TREE_1);
			tree.expandItem(TREE_1_1);
			collapseTree(new String[] { TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_1_1.equals(treeValue)) {
			tree.setValue(TREE_1_1_1);
			tree.expandItem(TREE_1_1);
		} else if (TREE_1_1_1.equals(treeValue)) {
			form_1_1_1.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_1_1);
		} else if (TREE_1_1_2.equals(treeValue)) {
			form_1_1_2.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_1_2);
		} else if (TREE_1_1_3.equals(treeValue)) {
			form_1_1_3.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_1_3);
		} else if (TREE_1_1_4.equals(treeValue)) {
			form_1_1_4.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_1_4);
		} else if (TREE_1_2.equals(treeValue)) {
			form_1_2.fieldGroup.setItemDataSource(statusSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_2);
			collapseTree(new String[] { TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
			tree.expandItem(TREE_1_2);
		} else if (TREE_1_2_1.equals(treeValue)) {
			form_1_2_1.fieldGroup.setItemDataSource(functionalPerformance.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_2_1);
		} else if (TREE_1_2_2.equals(treeValue)) {
			form_1_2_2.fieldGroup.setItemDataSource(costPerformance.getItem(tableValue));
			form_1_2_2.showImage();
			splitPanel2.setSecondComponent(form_1_2_2);
		} else if (TREE_1_2_3.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_1_2_3);
			schedulePerformance.removeAllContainerFilters();
			schedulePerformance.addContainerFilter(filter);
		} else if (TREE_1_3.equals(treeValue)) {
			tree.setValue(TREE_1_3_1);
			collapseTree(new String[] { TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
			tree.expandItem(TREE_1_3);
		} else if (TREE_1_3_1.equals(treeValue)) {
			form_1_3_1.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_3_1);
		} else if (TREE_1_3_2.equals(treeValue)) {
			form_1_3_2.fieldGroup.setItemDataSource(executiveSummary.getItem(tableValue));
			splitPanel2.setSecondComponent(form_1_3_2);
		} else if (TREE_2.equals(treeValue)) {
			form_2.fieldGroup.setItemDataSource(safety.getItem(tableValue));
			splitPanel2.setSecondComponent(form_2);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_3.equals(treeValue)) {
			form_3.fieldGroup.setItemDataSource(authorization.getItem(tableValue));
			form_3.setReadOnly(true);
			splitPanel2.setSecondComponent(form_3);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_4.equals(treeValue)) {
			form_4.fieldGroup.setItemDataSource(budget.getItem(tableValue));
			splitPanel2.setSecondComponent(form_4);
			costRpt.removeAllContainerFilters();
			costRpt.addContainerFilter(filter);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_5.equals(treeValue)) {
			tree.setValue(TREE_5_1);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_6, TREE_7, TREE_8, TREE_9 });
			tree.expandItem(TREE_5);
		} else if (TREE_5_1.equals(treeValue)) {
			form_5_1.fieldGroup.setItemDataSource(schedule.getItem(tableValue));
			splitPanel2.setSecondComponent(form_5_1);
		} else if (TREE_5_2.equals(treeValue)) {
			form_5_2.fieldGroup.setItemDataSource(schedule.getItem(tableValue));
			splitPanel2.setSecondComponent(form_5_2);
		} else if (TREE_5_3.equals(treeValue)) {
			form_5_3.fieldGroup.setItemDataSource(schedule.getItem(tableValue));
			splitPanel2.setSecondComponent(form_5_3);
		} else if (TREE_5_4.equals(treeValue)) {
			form_5_4.fieldGroup.setItemDataSource(schedule.getItem(tableValue));
			splitPanel2.setSecondComponent(form_5_4);
			milestone.removeAllContainerFilters();
			milestone.addContainerFilter(filter);
		} else if (TREE_6.equals(treeValue)) {
			tree.setValue(TREE_6_1);
			tree.expandItem(TREE_6);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_6_1.equals(treeValue)) {
			form_6_1.fieldGroup.setItemDataSource(lpe.getItem(tableValue));
			splitPanel2.setSecondComponent(form_6_1);
		} else if (TREE_6_2.equals(treeValue)) {
			tree.setValue(TREE_6_2_1);
			tree.expandItem(TREE_6_2);
		} else if (TREE_6_2_1.equals(treeValue)) {
			form_6_2_1.fieldGroup.setItemDataSource(lpe.getItem(tableValue));
			splitPanel2.setSecondComponent(form_6_2_1);
		} else if (TREE_6_2_2.equals(treeValue)) {
			form_6_2_2.fieldGroup.setItemDataSource(lpe.getItem(tableValue));
			splitPanel2.setSecondComponent(form_6_2_2);
		} else if (TREE_7.equals(treeValue)) {
			tree.setValue(TREE_7_1);
			tree.expandItem(TREE_7);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_8, TREE_9 });
		} else if (TREE_7_1.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_7_1);
			ipd.removeAllContainerFilters();
			ipd.addContainerFilter(filter);
		} else if (TREE_7_2.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_7_2);
			opd.removeAllContainerFilters();
			opd.addContainerFilter(filter);
		} else if (TREE_8.equals(treeValue)) {
			tree.setValue(TREE_8_1);
			tree.expandItem(TREE_8);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_9 });
		} else if (TREE_8_1.equals(treeValue)) {
			form_8_1.fieldGroup.setItemDataSource(procure.getItem(tableValue));
			splitPanel2.setSecondComponent(form_8_1);
		} else if (TREE_8_2.equals(treeValue)) {
			tree.setValue(TREE_8_2_1);
			tree.expandItem(TREE_8_2);
		} else if (TREE_8_2_1.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_8_2_1);
			form_8_2_1.fieldGroup.setItemDataSource(procure.getItem(tableValue));
			approvedChanges.removeAllContainerFilters();
			approvedChanges.addContainerFilter(filter);
		} else if (TREE_8_2_2.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_8_2_2);
			form_8_2_2.fieldGroup.setItemDataSource(procure.getItem(tableValue));
			pendingChanges.removeAllContainerFilters();
			pendingChanges.addContainerFilter(filter);
		} else if (TREE_9.equals(treeValue)) {
			tree.setValue(TREE_9_1);
			tree.expandItem(TREE_9);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8 });
		} else if (TREE_9_1.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_9_1);
			ipc.removeAllContainerFilters();
			ipc.addContainerFilter(filter);
		} else if (TREE_9_2.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_9_2);
			opc.removeAllContainerFilters();
			opc.addContainerFilter(filter);
		} else if (TREE_APPENDIX.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_appendix);
			appendix.removeAllContainerFilters();
			appendix.addContainerFilter(filter);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else if (TREE_ATTACHMENT.equals(treeValue)) {
			splitPanel2.setSecondComponent(form_attach);
			attach.removeAllContainerFilters();
			attach.addContainerFilter(filter);
			collapseTree(new String[] { TREE_1_1, TREE_1_2, TREE_1_3, TREE_5, TREE_6, TREE_7, TREE_8, TREE_9 });
		} else {
			splitPanel2.setSecondComponent(pageConstruction);
		}

	}

	private void collapseTree(String[] items) {
		for (String item : items) {
			tree.collapseItemsRecursively(item);
		}
	}

	private void initSearch() {

		searchField.setInputPrompt("Search Report");
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		searchField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {

				project.removeAllContainerFilters();

				ArrayList<Like> likes = new ArrayList<Like>();
				for (int i = 1; i < VISIBLE_COLUMNS.length; i++) {
					likes.add(new Like((String) VISIBLE_COLUMNS[i], "%" + event.getText() + "%", false));
				}

				project.addContainerFilter(new Or(likes.toArray(new Like[VISIBLE_COLUMNS.length - 1])));
			}
		});
	}

	private void initButton() {
		addNewMPSRButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				project.removeAllContainerFilters();

				Object mpsrID = project.addItem();

				for (int i = 1; i < VISIBLE_COLUMNS.length; i++) {
					mpsrTable.getContainerProperty(mpsrID, VISIBLE_COLUMNS[i]).setValue("");
				}

				mpsrTable.select(mpsrID);
				tree.setValue(TREE_0);
			}
		});

		removeMPSRButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object mpsrID = mpsrTable.getValue();

				for (SQLContainer sqlContainer : containers) {
					sqlContainer.removeItem(mpsrID);
				}

				project.removeItem(mpsrID);

				tree.setValue(null);

				removeDirectory(new File(MPSRUI.FILE_STORE_PATH + mpsrID.toString()));
			}
		});

		saveMPSRButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				mpsrTable.commit();
				mpsrTable.refreshRowCache();

				// TODO:
				form_7_1.saveTable();
				form_7_2.saveTable();
				form_8_2_1.saveTable();
				form_8_2_2.saveTable();
				form_9_1.saveTable();
				form_9_2.saveTable();
				form_appendix.saveTable();
				form_attach.saveTable();
			}
		});
	}

	private SQLContainer createContainer(String table, String version) {
		try {
			// TableQuery tq = new TableQuery(table, pool, new
			// OracleGenerator());
			TableQuery tq = new TableQuery(table, pool);
			tq.setVersionColumn(version);
			SQLContainer container = new SQLContainer(tq);
			// TODO:
			container.setAutoCommit(true);
			return container;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private JDBCConnectionPool createConnectionPool() {
		DBConfig dbConfig = new MyDBConfig();
		try {
			return new SimpleJDBCConnectionPool(dbConfig.getDriver(), dbConfig.getURL(), dbConfig.getUserName(),
					dbConfig.getPassword(), 2, 5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	boolean removeDirectory(File directory) {

		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);

				// System.out.println("\tremoving entry " + entry);

				if (entry.isDirectory()) {
					if (!removeDirectory(entry))
						return false;
				} else {
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}

	public static String getFileFolder(String mpsrID) {
		return String.format("%s/%s", FILE_STORE_PATH, mpsrID);
	}

	public static String getFilePath(String mpsrID, String fileName) {
		return String.format("%s/%s", getFileFolder(mpsrID), fileName);
	}

}