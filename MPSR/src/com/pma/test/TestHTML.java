package com.pma.test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestHTML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String html = "<P>Install a new 230kV gas insulated substation (GIS) with short-circuit current rating of 80kA to replace the existing 230kV air insulated substation (AIS). The new 230kV GIS will consist of nine (9) breaker-and-half bays with four (4) bus section breakers and 18 bus positions.</P>"
				+ "<P>This project is critical to the completion of the following separate but interrelated projects authorized by the URB/CRC:</P>"
				+ "<UL>"
				+ "<LI>Bergen SW 230-138kV Autotransformer</LI>"
				+ "<LI>Bergen 132-1 Transformer Bank Life Cycle Replacement</LI>"
				+ "<LI>Bergen 132-2 Transformer Bank Life Cycle Replacement</LI>"
				+ "<LI>Bergen 132-3 Transformer Bank Life Cycle Replacement</LI></UL>";

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader("<HTML>" + html + "</HTML>")));
			doc.getDocumentElement().normalize();

			Element element = doc.getDocumentElement();

			NodeList nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if ("P".equals(node.getNodeName())) {

				} else if ("UL".equals(node.getNodeName())) {

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
}
