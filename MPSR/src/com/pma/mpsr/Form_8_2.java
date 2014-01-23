package com.pma.mpsr;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;

import com.pma.vaadin.converter.StringToBigDecimalConverter;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class Form_8_2 extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label totalToDate;
	@AutoGenerated
	private TextField totalAmount;
	@AutoGenerated
	private TextArea currentMonthDescription;
	@AutoGenerated
	private TextField currentMonthAmount;
	@AutoGenerated
	private TextField previousAmount;
	@AutoGenerated
	private TextField vendor;
	@AutoGenerated
	private TextField purchaseOrder;
	@AutoGenerated
	private Button removeButton;
	@AutoGenerated
	private Button addButton;
	@AutoGenerated
	private Table table;
	@AutoGenerated
	private TextArea sectionDescription;
	@AutoGenerated
	private Label labelPrototype;
	String caption;
	String descPropertyId;
	Table mpsrTable;
	SQLContainer sqlContainer;
	AbstractTextField[] tableFields;
	FieldGroup fieldGroup = new FieldGroup();
	FieldGroup tableFieldGroup = new FieldGroup();

	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();

	private static final Object[] VISIBLE_COLUMNS = { "PO_NUM", "VENDOR", "PREV_AMT", "CURR_AMT", "CURR_DESC",
			"TOTAL_AMT" };
	private static final String[] COLUMN_HEADERS = { "PO", "Vendor", "Prev. Amt", "Amount", "Description", "Total Amt" };
	private static final int[] COLUMN_WIDTH = { 80, 160, 80, 80, 160, 80 };

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Form_8_2(String caption, String descPropertyId, SQLContainer container, Table mpsrTable) {
		this.caption = caption;
		this.descPropertyId = descPropertyId;
		this.sqlContainer = container;
		this.mpsrTable = mpsrTable;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		initFields();
		addListener();
	}

	public void saveTable() {
		table.commit();
		table.refreshRowCache();
	}

	public void unSelectTable() {
		table.select(null);
	}

	public void setFieldsVisible(boolean visible) {
		for (AbstractTextField field : tableFields) {
			field.setVisible(visible);
		}
	}

	private void initFields() {

		// mainLayout.addStyleName("form-7-1");

		StringToBigDecimalConverter numberConverter = new StringToBigDecimalConverter();

		sectionDescription.setCaption(caption);

		labelPrototype.setValue("Prototype");

		sectionDescription.setNullRepresentation("");
		fieldGroup.bind(sectionDescription, descPropertyId);
		fieldGroup.setBuffered(false);

		tableFields = new AbstractTextField[] { purchaseOrder, vendor, previousAmount, currentMonthAmount,
				currentMonthDescription, totalAmount };

		TextField[] textFields = { previousAmount, currentMonthAmount, totalAmount };
		String[] numnberColumns = { "PREV_AMT", "CURR_AMT", "TOTAL_AMT" };
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setNullRepresentation("");
			textFields[i].setConverter(numberConverter);
			tableFieldGroup.bind(textFields[i], numnberColumns[i]);
		}

		textFields = new TextField[] { purchaseOrder, vendor };
		String[] textColumns = new String[] { "PO_NUM", "VENDOR" };
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setNullRepresentation("");
			tableFieldGroup.bind(textFields[i], textColumns[i]);
		}

		TextArea[] textAreas = { currentMonthDescription };
		String[] textAreaColumns = new String[] { "CURR_DESC" };
		for (int i = 0; i < textAreas.length; i++) {
			textAreas[i].setNullRepresentation("");
			tableFieldGroup.bind(textAreas[i], textAreaColumns[i]);
		}

		tableFieldGroup.setBuffered(false);

		table.setImmediate(true);
		table.setSelectable(true);
		table.setColumnReorderingAllowed(true);
		table.setContainerDataSource(sqlContainer);
		table.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		table.setVisibleColumns(VISIBLE_COLUMNS);
		table.setColumnHeaders(COLUMN_HEADERS);
		for (int i = 0; i < VISIBLE_COLUMNS.length; i++) {
			table.setColumnWidth(VISIBLE_COLUMNS[i], COLUMN_WIDTH[i]);
		}
		for (int i = 0; i < numnberColumns.length; i++) {
			table.setColumnAlignment(numnberColumns[i], Align.RIGHT);
		}

		setFieldsVisible(null != table.getValue());

		setTotalToDate();
	}

	private void addListener() {
		addButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				Object id = sqlContainer.addItem();

				sqlContainer.removeAllContainerFilters();

				// table.getContainerProperty(id, "MPSR_ID").setValue(new
				// BigDecimal(Integer.parseInt(mpsrTable.getValue().toString())));
				table.getContainerProperty(id, "MPSR_ID").setValue(Integer.parseInt(mpsrTable.getValue().toString()));
				table.getContainerProperty(id, "PO_NUM").setValue("New");

				Compare.Equal filter = new Compare.Equal("MPSR_ID", mpsrTable.getValue().toString());
				sqlContainer.addContainerFilter(filter);

				table.refreshRowCache();
				table.select(id);

			}
		});

		removeButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object id = table.getValue();
				sqlContainer.removeItem(id);
			}
		});

		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {

				table.refreshRowCache();

				tableFieldGroup.setItemDataSource(sqlContainer.getItem(table.getValue()));

				setFieldsVisible(null != table.getValue());

				setTotalToDate();
			}

		});
	}

	private void setTotalToDate() {
		double total = 0;
		for (int i = 0; i < sqlContainer.size(); i++) {
			BigDecimal value = (BigDecimal) sqlContainer.getItem(sqlContainer.getIdByIndex(i))
					.getItemProperty("TOTAL_AMT").getValue();
			total += null != value ? value.doubleValue() : 0;
		}
		totalToDate.setValue(CURRENCY_FORMATTER.format(total));
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("760px");
		mainLayout.setHeight("620px");

		// top-level component properties
		setWidth("760px");
		setHeight("620px");

		// labelPrototype
		labelPrototype = new Label();
		labelPrototype.setStyleName("prototype2");
		labelPrototype.setImmediate(false);
		labelPrototype.setWidth("-1px");
		labelPrototype.setHeight("-1px");
		labelPrototype.setValue("Label");
		mainLayout.addComponent(labelPrototype, "top:0.0px;right:0.0px;");

		// sectionDescription
		sectionDescription = new TextArea();
		sectionDescription.setImmediate(true);
		sectionDescription.setWidth("720px");
		sectionDescription.setHeight("100px");
		mainLayout.addComponent(sectionDescription, "top:21.0px;left:20.0px;");

		// table
		table = new Table();
		table.setImmediate(true);
		table.setWidth("720px");
		table.setHeight("200px");
		mainLayout.addComponent(table, "top:140.0px;left:20.0px;");

		// addButton
		addButton = new Button();
		addButton.setCaption("New");
		addButton.setImmediate(true);
		addButton.setWidth("-1px");
		addButton.setHeight("-1px");
		mainLayout.addComponent(addButton, "top:360.0px;left:20.0px;");

		// removeButton
		removeButton = new Button();
		removeButton.setCaption("Remove");
		removeButton.setImmediate(true);
		removeButton.setWidth("-1px");
		removeButton.setHeight("-1px");
		mainLayout.addComponent(removeButton, "top:360.0px;left:100.0px;");

		// purchaseOrder
		purchaseOrder = new TextField();
		purchaseOrder.setCaption("Purchase Order");
		purchaseOrder.setImmediate(true);
		purchaseOrder.setWidth("140px");
		purchaseOrder.setHeight("24px");
		mainLayout.addComponent(purchaseOrder, "top:420.0px;left:20.0px;");

		// vendor
		vendor = new TextField();
		vendor.setCaption("Vendor");
		vendor.setImmediate(true);
		vendor.setWidth("320px");
		vendor.setHeight("24px");
		mainLayout.addComponent(vendor, "top:420.0px;left:180.0px;");

		// previousAmount
		previousAmount = new TextField();
		previousAmount.setCaption("Previous Amount");
		previousAmount.setImmediate(true);
		previousAmount.setWidth("120px");
		previousAmount.setHeight("24px");
		mainLayout.addComponent(previousAmount, "top:480.0px;left:20.0px;");

		// currentMonthAmount
		currentMonthAmount = new TextField();
		currentMonthAmount.setCaption("This Month Amount");
		currentMonthAmount.setImmediate(true);
		currentMonthAmount.setWidth("120px");
		currentMonthAmount.setHeight("24px");
		mainLayout.addComponent(currentMonthAmount, "top:480.0px;left:160.0px;");

		// currentMonthDescription
		currentMonthDescription = new TextArea();
		currentMonthDescription.setCaption("This Month Description");
		currentMonthDescription.setImmediate(true);
		currentMonthDescription.setWidth("200px");
		currentMonthDescription.setHeight("48px");
		mainLayout.addComponent(currentMonthDescription, "top:480.0px;left:300.0px;");

		// totalAmount
		totalAmount = new TextField();
		totalAmount.setCaption("Total Amount");
		totalAmount.setImmediate(true);
		totalAmount.setWidth("120px");
		totalAmount.setHeight("24px");
		mainLayout.addComponent(totalAmount, "top:480.0px;left:520.0px;");

		// totalToDate
		totalToDate = new Label();
		totalToDate.setCaption("Total To Date");
		totalToDate.setImmediate(false);
		totalToDate.setWidth("100px");
		totalToDate.setHeight("-1px");
		totalToDate.setValue("Label");
		mainLayout.addComponent(totalToDate, "top:360.0px;left:640.0px;");

		return mainLayout;
	}
}
