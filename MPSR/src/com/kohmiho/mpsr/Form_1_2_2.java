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
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

@SuppressWarnings("serial")
public class Form_1_2_2 extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Embedded image;

	@AutoGenerated
	private TextField fileName;

	@AutoGenerated
	private Upload uploadPicture;

	@AutoGenerated
	private Label labelPrototype;

	@AutoGenerated
	private Label label_1;

	Table mpsrTable;
	FieldGroup fieldGroup = new FieldGroup();

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Form_1_2_2(Table mpsrTable) {
		this.mpsrTable = mpsrTable;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		initFields();
	}

	private void initFields() {

		label_1.setCaption(MPSRUI.TREE_1_2_2);
		label_1.setValue("");

		labelPrototype.setValue("Prototype");

		fieldGroup.bind(fileName, "FILE_NAME");
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
		mainLayout.setHeight("520px");

		// top-level component properties
		setWidth("640px");
		setHeight("520px");

		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Label");
		mainLayout.addComponent(label_1, "top:20.0px;left:20.0px;");

		// labelPrototype
		labelPrototype = new Label();
		labelPrototype.setStyleName("prototype2");
		labelPrototype.setImmediate(false);
		labelPrototype.setWidth("-1px");
		labelPrototype.setHeight("-1px");
		labelPrototype.setValue("Label");
		mainLayout.addComponent(labelPrototype, "top:0.0px;right:0.0px;");

		// uploadPicture
		uploadPicture = new Upload();
		uploadPicture.setCaption("Upload graph here");
		uploadPicture.setImmediate(false);
		uploadPicture.setWidth("280px");
		uploadPicture.setHeight("-1px");
		mainLayout.addComponent(uploadPicture, "top:60.0px;left:20.0px;");

		// fileName
		fileName = new TextField();
		fileName.setEnabled(false);
		fileName.setImmediate(true);
		fileName.setVisible(false);
		fileName.setWidth("-1px");
		fileName.setHeight("-1px");
		mainLayout.addComponent(fileName, "top:60.0px;left:340.0px;");

		// image
		image = new Embedded();
		image.setImmediate(false);
		image.setWidth("600px");
		image.setHeight("400px");
		image.setSource(new ThemeResource("img/component/embedded_icon.png"));
		image.setType(1);
		image.setMimeType("image/png");
		mainLayout.addComponent(image, "top:100.0px;left:20.0px;");

		return mainLayout;
	}

}
