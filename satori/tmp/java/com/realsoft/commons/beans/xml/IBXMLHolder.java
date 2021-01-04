package com.realsoft.commons.beans.xml;

import org.w3c.dom.Document;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBXMLHolder {

	Document getXMLStructure() throws BXMLHolderException;

	String getXmlDocument() throws BXMLHolderException;

	public void storeValue(String xpath, Object value)
			throws CommonsBeansException;

}
