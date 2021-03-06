package com.kohmiho.mpsr;

import com.kohmiho.vaadin.component.MPSRTable;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;

@SuppressWarnings("serial")
public class Form_1_2_3 extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private MPSRTable tableSchedulePerformance;
	@AutoGenerated
	private Label labelPrototype;
	SQLContainer schedulePerformance = null;

	FieldGroup fieldGroup = new FieldGroup();

	private static final Object[] VISIBLE_COLUMNS = { "WBS", "ORIGDUR", "REMDUR", "BL_PROJECT_START", "BL_PROJECT_FINISH", "START_DATE", "FINISH_DATE",
			"TOTAL_FLOAT", "STATUS" };
	private static final String[] COLUMN_HEADERS = { "WBS", "Orig. Dur.", "Rem. Dur.", "Baseline Start", "Baseline Finish", "Start", "Finish", "TF", "Status" };
	private static final String[] COLUMNS_ALIGN_CENTER = { "BL_PROJECT_START", "BL_PROJECT_FINISH", "START_DATE", "FINISH_DATE" };
	private static final String[] COLUMNS_ALIGN_RIGHT = { "ORIGDUR", "REMDUR", "TOTAL_FLOAT" };

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Form_1_2_3(SQLContainer schedulePerformance) {

		this.schedulePerformance = schedulePerformance;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		initFields();
	}

	private void initFields() {

		labelPrototype.setValue("Prototype");

		tableSchedulePerformance.setCaption(MPSRUI.TREE_1_2_3);
		tableSchedulePerformance.setContainerDataSource(schedulePerformance);
		tableSchedulePerformance.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		tableSchedulePerformance.setVisibleColumns(VISIBLE_COLUMNS);
		tableSchedulePerformance.setColumnHeaders(COLUMN_HEADERS);

		tableSchedulePerformance.setColumnWidth("WBS", 185);
		tableSchedulePerformance.setColumnWidth("ORIGDUR", 55);
		tableSchedulePerformance.setColumnWidth("REMDUR", 55);
		tableSchedulePerformance.setColumnWidth("BL_PROJECT_START", 65);
		tableSchedulePerformance.setColumnWidth("BL_PROJECT_FINISH", 65);
		tableSchedulePerformance.setColumnWidth("START_DATE", 65);
		tableSchedulePerformance.setColumnWidth("FINISH_DATE", 65);
		tableSchedulePerformance.setColumnWidth("Total Float", 55);
		tableSchedulePerformance.setColumnWidth("Status", 60);

		for (String column : COLUMNS_ALIGN_RIGHT) {
			tableSchedulePerformance.setColumnAlignment(column, Align.RIGHT);
		}

		for (String column : COLUMNS_ALIGN_CENTER) {
			tableSchedulePerformance.setColumnAlignment(column, Align.CENTER);
		}

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("770px");
		mainLayout.setHeight("540px");
		
		// top-level component properties
		setWidth("770px");
		setHeight("540px");
		
		// labelPrototype
		labelPrototype = new Label();
		labelPrototype.setStyleName("prototype2");
		labelPrototype.setImmediate(false);
		labelPrototype.setWidth("-1px");
		labelPrototype.setHeight("-1px");
		labelPrototype.setValue("Label");
		mainLayout.addComponent(labelPrototype, "top:0.0px;right:0.0px;");
		
		// tableSchedulePerformance
		tableSchedulePerformance = new MPSRTable();
		tableSchedulePerformance.setImmediate(false);
		tableSchedulePerformance.setWidth("770px");
		tableSchedulePerformance.setHeight("500px");
		mainLayout.addComponent(tableSchedulePerformance, "top:20.0px;left:0.0px;");
		
		return mainLayout;
	}

}
