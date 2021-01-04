package com.realsoft.commons.beans.state;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PaymentFailures implements Serializable {

	private List<String> fieldName = new LinkedList<String>();

	private List<Object> fieldValue = new LinkedList<Object>();
	
	public List<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(List<String> fieldName) {
		this.fieldName = fieldName;
	}

	public List<Object> getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(List<Object> fieldValue) {
		this.fieldValue = fieldValue;
	}

	public PaymentFailures(List<String> fieldName, List<Object> fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public PaymentFailures() {
		super();
		// TODO Auto-generated constructor stub
	}

}
