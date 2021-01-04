package com.realsoft.utils.ant;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class MDXMLTask extends Task {

	private static final String MD5 = "MD5";

	private static final String SHA = "SHA";

	private String xpath = null;

	private String includesfile = null;

	private String outputfile = null;

	private String type = null;

	public MDXMLTask() {
		super();
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String value) {
		this.xpath = value;
	}

	public String getOutputfile() {
		return outputfile;
	}

	public void setOutputfile(String outputfile) {
		this.outputfile = outputfile;
	}

	public String getIncludesfile() {
		return includesfile;
	}

	public void setIncludesfile(String includesfile) {
		this.includesfile = includesfile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void execute() throws BuildException {
		try {
			log("calculating " + ((type == null) ? "MD5" : type) + " hash code");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(new File(includesfile));

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(xpath, doc,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				nodes.item(i).setTextContent(
						(type.equals(SHA)) ? DigestUtils.shaHex(nodes.item(i)
								.getTextContent()) : DigestUtils.md5Hex(nodes
								.item(i).getTextContent()));
			}

			OutputFormat outputFormat = new OutputFormat(doc);
			outputFormat.setEncoding("utf-8");
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(
					outputfile), outputFormat);
			serializer.serialize(doc);

			// TransformerFactory tFactory = TransformerFactory.newInstance();
			// Transformer transformer = tFactory.newTransformer();
			// DOMSource source = new DOMSource(doc);
			// StreamResult result = new StreamResult(new FileOutputStream(
			// outputfile));
			// transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException("Could not generate hash for password", e);
		}
	}

}
