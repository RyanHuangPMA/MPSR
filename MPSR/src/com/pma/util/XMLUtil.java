package com.pma.util;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLUtil {

	public static ByteArrayOutputStream loadXMLFromFile(String fileName) {

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = builder.parse(new FileInputStream(fileName));

			transfer(doc, outputStream);

			return outputStream;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String toString(Document doc) throws TransformerException {

		OutputStream result = new ByteArrayOutputStream();

		transfer(doc, result);

		return result.toString();
	}

	public static void transfer(Document doc, OutputStream result)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {

		transfer(doc, new StreamResult(result));
	}

	public static void transfer(Document doc, PrintWriter pw)
			throws TransformerException {

		transfer(doc, new StreamResult(pw));
	}

	private static void transfer(Document doc, Result streamResult)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {

		Source source = new DOMSource(doc);
		TransformerFactory xformFactory = TransformerFactory.newInstance();
		Transformer idTransform = xformFactory.newTransformer();
		idTransform.transform(source, streamResult);
	}
}
