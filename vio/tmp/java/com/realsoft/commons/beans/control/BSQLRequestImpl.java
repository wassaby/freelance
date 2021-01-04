package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class BSQLRequestImpl implements IBRequest, Serializable {

	private static final long serialVersionUID = 1L;

	private String request = null;

	private Map<String, Object> arguments = new LinkedHashMap<String, Object>();

	public BSQLRequestImpl(String request) {
		this.request = request;
	}

	public BSQLRequestImpl(String request, Map<String, Object> arguments) {
		this.request = request;
		this.arguments = arguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBRequest#getArguments()
	 */
	public Map<String, Object> getArguments() {
		return arguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBRequest#setArguments(java.util.Map)
	 */
	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

}
