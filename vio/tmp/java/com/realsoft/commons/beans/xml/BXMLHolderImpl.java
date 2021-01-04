package com.realsoft.commons.beans.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.control.CommonsControlConstants;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class BXMLHolderImpl implements IBXMLHolder {

	private static Logger log = Logger.getLogger(BXMLHolderImpl.class);

	private String fileName = null;

	private String file = null;

	private Document doc = null;

	public BXMLHolderImpl() {
	}

	private String readFile(String fileName) throws FileNotFoundException,
			IOException {
		InputStream fis = new FileInputStream(fileName);
		StringBuffer result = new StringBuffer();
		int size = 1024;
		byte[] buffer = new byte[size];
		int readed;
		while ((readed = fis.read(buffer)) != -1) {
			result.append(new String(buffer, 0, readed));
		}
		return result.toString();
	}

	public void initialize() throws ParserConfigurationException, SAXException,
			IOException {
		InputStream in = new FileInputStream(new File(fileName));
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		doc = builder.parse(in);
		in.close();
		file = readFile(fileName);
	}

	public Document getXMLStructure() throws BXMLHolderException {
		if (doc == null)
			throw new BXMLHolderException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.xml-holder.getXMLStructure.error",
					"Component not initialized yet");
		return doc;
	}

	public void storeValue(String xpath, Object value)
			throws CommonsBeansException {
		if (doc == null)
			throw new BXMLHolderException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.xml-holder.getXMLStructure.error",
					"Component not initialized yet");
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(xpath, doc,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				nodes.item(i).setTextContent(value.toString());
			}
			OutputFormat outputFormat = new OutputFormat(doc);
			outputFormat.setEncoding("utf-8");
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(
					fileName), outputFormat);
			serializer.serialize(doc);

			// TransformerFactory tFactory = TransformerFactory.newInstance();
			// Transformer transformer = tFactory.newTransformer();
			// DOMSource source = new DOMSource(doc);
			// StreamResult result = new StreamResult(new
			// FileOutputStream(fileName));
			// transformer.transform(source, result);

		} catch (Exception e) {
			log.error("Could not store value", e);
			if (e instanceof CommonsBeansException) {
				CommonsBeansException ex = (CommonsBeansException) e;
				throw new BXMLHolderException(ex.getErrorCode(), ex
						.getMessageKey(), ex.getMessageText());
			}
			throw new BXMLHolderException(RealsoftConstants.ERR_SYSTEM,
					"commons-beans.control.storeValue", "Could not store value");
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getXmlDocument() throws BXMLHolderException {
		return file;
	}

}
