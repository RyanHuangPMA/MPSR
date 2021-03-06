package com.kohmiho.mpsr;

import java.io.File;

import com.kohmiho.vaadin.component.ImageUploader;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

@SuppressWarnings("serial")
public class Form_0 extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Embedded image;
	@AutoGenerated
	private Upload uploadPicture;
	@AutoGenerated
	private TextField fileName;
	@AutoGenerated
	private TextField textField_Dir;
	@AutoGenerated
	private TextField textField_PM;
	@AutoGenerated
	private TextField textField_RptMonthYear;
	@AutoGenerated
	private TextArea textField_ProjTitle;
	@AutoGenerated
	private TextField textField_ProjDef;
	@AutoGenerated
	private Label labelPrototype;

	Table mpsrTable;
	FieldGroup fieldGroup = new FieldGroup();

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Form_0(Table mpsrTable) {
		this.mpsrTable = mpsrTable;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		initFields();
	}

	private void initFields() {

		mainLayout.addStyleName("Cover");

		labelPrototype.setValue("Prototype");

		textField_ProjDef.setNullRepresentation("");
		textField_ProjTitle.setNullRepresentation("");
		textField_RptMonthYear.setNullRepresentation("");
		textField_PM.setNullRepresentation("");
		textField_Dir.setNullRepresentation("");

		TextField[] textFields = { textField_ProjDef, textField_RptMonthYear, textField_PM, textField_Dir, fileName };
		String[] textFieldProptertyIds = { "PROJ_DEF", "RPT_MONTH_YEAR", "PM", "DIR", "FILE_NAME" };
		for (int i = 0; i < textFields.length; i++) {
			fieldGroup.bind(textFields[i], textFieldProptertyIds[i]);
		}

		TextArea[] textAreas = { textField_ProjTitle };
		String[] textAreasProptertyIds = { "PROJ_TITLE" };
		for (int i = 0; i < textAreas.length; i++) {
			fieldGroup.bind(textAreas[i], textAreasProptertyIds[i]);
		}

		fieldGroup.setBuffered(false);

		image.setEnabled(true);
		image.setVisible(false);

		ImageUploader uploader = new ImageUploader(mpsrTable, fileName, image);
		uploadPicture.setReceiver(uploader);
		uploadPicture.addSucceededListener(uploader);

	}

	void showImage() {
		if (null != fileName.getValue()) {
			File sourceFile = new File(MPSRUI.getFilePath(mpsrTable.getValue().toString(), fileName.getValue()));
			if (sourceFile.exists()) {
				image.setSource(new FileResource(sourceFile));
				image.setVisible(true);
			} else {
				image.setVisible(false);
			}
		} else {
			image.setVisible(false);
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("640px");
		mainLayout.setHeight("700px");

		// top-level component properties
		setWidth("640px");
		setHeight("700px");

		// labelPrototype
		labelPrototype = new Label();
		labelPrototype.setStyleName("prototype2");
		labelPrototype.setImmediate(false);
		labelPrototype.setWidth("-1px");
		labelPrototype.setHeight("-1px");
		labelPrototype.setValue("Label");
		mainLayout.addComponent(labelPrototype, "top:0.0px;right:0.0px;");

		// textField_ProjDef
		textField_ProjDef = new TextField();
		textField_ProjDef.setCaption("Project Definition");
		textField_ProjDef.setImmediate(false);
		textField_ProjDef.setWidth("200px");
		textField_ProjDef.setHeight("-1px");
		mainLayout.addComponent(textField_ProjDef, "top:20.0px;left:20.0px;");

		// textField_ProjTitle
		textField_ProjTitle = new TextArea();
		textField_ProjTitle.setCaption("Project Description");
		textField_ProjTitle.setImmediate(false);
		textField_ProjTitle.setWidth("460px");
		textField_ProjTitle.setHeight("60px");
		mainLayout.addComponent(textField_ProjTitle, "top:60.0px;left:20.0px;");

		// textField_RptMonthYear
		textField_RptMonthYear = new TextField();
		textField_RptMonthYear.setCaption("Month Year");
		textField_RptMonthYear.setImmediate(false);
		textField_RptMonthYear.setWidth("200px");
		textField_RptMonthYear.setHeight("24px");
		mainLayout.addComponent(textField_RptMonthYear, "top:140.0px;left:20.0px;");

		// textField_PM
		textField_PM = new TextField();
		textField_PM.setCaption("Client");
		textField_PM.setImmediate(false);
		textField_PM.setWidth("280px");
		textField_PM.setHeight("24px");
		mainLayout.addComponent(textField_PM, "top:180.0px;left:20.0px;");

		// textField_Dir
		textField_Dir = new TextField();
		textField_Dir.setCaption("Location");
		textField_Dir.setImmediate(false);
		textField_Dir.setWidth("280px");
		textField_Dir.setHeight("24px");
		mainLayout.addComponent(textField_Dir, "top:220.0px;left:20.0px;");

		// fileName
		fileName = new TextField();
		fileName.setEnabled(false);
		fileName.setImmediate(true);
		fileName.setVisible(false);
		fileName.setWidth("-1px");
		fileName.setHeight("-1px");
		mainLayout.addComponent(fileName, "top:160.0px;left:540.0px;");

		// uploadPicture
		uploadPicture = new Upload();
		uploadPicture.setCaption("Upload picture here");
		uploadPicture.setImmediate(false);
		uploadPicture.setWidth("280px");
		uploadPicture.setHeight("-1px");
		mainLayout.addComponent(uploadPicture, "top:260.0px;left:20.0px;");

		// image
		image = new Embedded();
		image.setImmediate(false);
		image.setWidth("600px");
		image.setHeight("400px");
		image.setSource(new ThemeResource("img/component/embedded_icon.png"));
		image.setType(1);
		image.setMimeType("image/png");
		mainLayout.addComponent(image, "top:300.0px;left:20.0px;");

		return mainLayout;
	}
}
