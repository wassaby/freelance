package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BComponentRequestImpl implements IBRequest, Serializable {

	private static final long serialVersionUID = 2460120934979310847L;

	private String compomentName = null;

	private String componentMethod = null;

	private Map<String, Object> arguments = null;

	public BComponentRequestImpl(String compomentName, String componentMethod) {
		this.compomentName = compomentName;
		this.componentMethod = componentMethod;
		this.arguments = new HashMap<String, Object>();
	}

	public BComponentRequestImpl(String compomentName, String componentMethod,
			Map<String, Object> arguments) {
		this.compomentName = compomentName;
		this.componentMethod = componentMethod;
		this.arguments = arguments;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public String getCompomentName() {
		return compomentName;
	}

	public void setCompomentName(String compomentName) {
		this.compomentName = compomentName;
	}

	public String getComponentMethod() {
		return componentMethod;
	}

	public void setComponentMethod(String componentMethod) {
		this.componentMethod = componentMethod;
	}

}
