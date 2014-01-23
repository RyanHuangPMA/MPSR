package com.pma.vaadin.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.pma.mpsr.MPSRUI;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
public class ImageUploader implements Receiver, SucceededListener {

	private Table mpsrTable;
	private TextField fileName;
	private File file;
	private Embedded image;

	public ImageUploader(Table mpsrTable, TextField fileName, Embedded image) {
		this.mpsrTable = mpsrTable;
		this.fileName = fileName;
		this.image = image;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {

		// Create upload stream
		FileOutputStream fos = null; // Stream to write to

		try {
			new File(MPSRUI.getFileFolder(mpsrTable.getValue().toString())).mkdir();
			file = new File(MPSRUI.getFilePath(mpsrTable.getValue().toString(), filename));
			fos = new FileOutputStream(file);
		} catch (final java.io.FileNotFoundException e) {
			new Notification("Could not open file", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
			return null;
		}

		// fileName.setValue(strFile);
		fileName.setValue(filename);

		return fos; // Return the output stream to write to
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		image.setVisible(true);
		image.setSource(new FileResource(file));
	}

}
