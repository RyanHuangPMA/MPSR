package com.pma.mpsr;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class Form_7 extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextArea note;
	@AutoGenerated
	private ComboBox indicator;
	@AutoGenerated
	private TextField ifc;
	@AutoGenerated
	private TextField ifr;
	@AutoGenerated
	private TextField pdPackage;
	@AutoGenerated
	private Button removeButton;
	@AutoGenerated
	private Button addButton;
	@AutoGenerated
	private Label labelPrototype;
	@AutoGenerated
	private Table table;

	String caption;
	Table mpsrTable;
	SQLContainer sqlContainer;
	Field[] fields;
	FieldGroup fieldGroup = new FieldGroup();

	private static final Object[] VISIBLE_COLUMNS = { "PACKAGE", "IFR", "IFC", "INDICATOR", "NOTE" };
	private static final String[] COLUMN_HEADERS = { "Package", "IFR", "IRC", "Indicators", "Notes" };
	private static final int[] COLUMN_WIDTH = { 160, 100, 100, 80, 200 };

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Form_7(String caption, SQLContainer container, Table mpsrTable) {
		this.caption = caption;
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
		for (Field field : fields) {
			field.setVisible(visible);
		}
	}

	private void initFields() {

		// mainLayout.addStyleName("form-7-1");

		// textArea_1.setCaption(MPSRUI.TREE_7_1);

		labelPrototype.setValue("Prototype");

		fields = new Field[] { pdPackage, ifr, ifc, indicator, note };

		AbstractTextField[] textFields = { pdPackage, ifr, ifc, note };
		String[] propertyIds = { "PACKAGE", "IFR", "IFC", "NOTE" };
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setNullRepresentation("");
			fieldGroup.bind(textFields[i], propertyIds[i]);
		}

		fieldGroup.bind(indicator, "INDICATOR");
		String[] colors = { "Green", "Yellow", "Red" };
		Resource[] colorIcons = { MPSRUI.ICON_CIRCLE_GREEN, MPSRUI.ICON_CIRCLE_YELLOW, MPSRUI.ICON_CIRCLE_RED };
		for (int i = 0; i < colors.length; i++) {
			indicator.addItem(colors[i]);
			indicator.setItemIcon(colors[i], colorIcons[i]);
		}

		fieldGroup.setBuffered(false);

		table.setImmediate(true);
		table.setSelectable(true);
		table.setColumnReorderingAllowed(true);
		table.setCaption(caption);
		table.setContainerDataSource(sqlContainer);
		table.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		table.setVisibleColumns(VISIBLE_COLUMNS);
		table.setColumnHeaders(COLUMN_HEADERS);
		for (int i = 0; i < VISIBLE_COLUMNS.length; i++) {
			table.setColumnWidth(VISIBLE_COLUMNS[i], COLUMN_WIDTH[i]);
		}

		setFieldsVisible(null != table.getValue());

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
				table.getContainerProperty(id, "PACKAGE").setValue("New");

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

				fieldGroup.setItemDataSource(sqlContainer.getItem(table.getValue()));

				setFieldsVisible(null != table.getValue());
			}
		});
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(true);
		mainLayout.setWidth("760px");
		mainLayout.setHeight("500px");

		// top-level component properties
		setWidth("760px");
		setHeight("500px");

		// labelPrototype
		labelPrototype = new Label();
		labelPrototype.setStyleName("prototype2");
		labelPrototype.setImmediate(false);
		labelPrototype.setWidth("-1px");
		labelPrototype.setHeight("-1px");
		labelPrototype.setValue("Label");
		mainLayout.addComponent(labelPrototype, "top:0.0px;right:0.0px;");

		// tableIPD
		table = new Table();
		table.setImmediate(true);
		table.setWidth("720px");
		table.setHeight("200px");
		mainLayout.addComponent(table, "top:20.0px;left:20.0px;");

		// addIPDButton
		addButton = new Button();
		addButton.setCaption("New");
		addButton.setImmediate(true);
		addButton.setWidth("-1px");
		addButton.setHeight("-1px");
		mainLayout.addComponent(addButton, "top:240.0px;left:20.0px;");

		// removeIPDButton
		removeButton = new Button();
		removeButton.setCaption("Remove");
		removeButton.setImmediate(true);
		removeButton.setWidth("-1px");
		removeButton.setHeight("-1px");
		mainLayout.addComponent(removeButton, "top:240.0px;left:100.0px;");

		// pdPackage
		pdPackage = new TextField();
		pdPackage.setCaption("Package");
		pdPackage.setImmediate(true);
		pdPackage.setWidth("220px");
		pdPackage.setHeight("-1px");
		mainLayout.addComponent(pdPackage, "top:300.0px;left:20.0px;");

		// ifr
		ifr = new TextField();
		ifr.setCaption("IFR");
		ifr.setImmediate(true);
		ifr.setWidth("-1px");
		ifr.setHeight("-1px");
		mainLayout.addComponent(ifr, "top:300.0px;left:260.0px;");

		// ifc
		ifc = new TextField();
		ifc.setCaption("IFC");
		ifc.setImmediate(true);
		ifc.setWidth("-1px");
		ifc.setHeight("-1px");
		mainLayout.addComponent(ifc, "top:300.0px;left:420.0px;");

		// indicator
		indicator = new ComboBox();
		indicator.setCaption("Indicator");
		indicator.setImmediate(true);
		indicator.setWidth("-1px");
		indicator.setHeight("-1px");
		mainLayout.addComponent(indicator, "top:300.0px;left:580.0px;");

		// note
		note = new TextArea();
		note.setCaption("Note");
		note.setImmediate(true);
		note.setWidth("440px");
		note.setHeight("-1px");
		mainLayout.addComponent(note, "top:340.0px;left:260.0px;");

		return mainLayout;
	}
}