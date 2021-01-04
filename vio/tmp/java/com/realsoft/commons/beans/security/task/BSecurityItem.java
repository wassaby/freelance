/*
 * Created on 03.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BSecurityItem.java,v 1.2 2016/04/15 10:37:50 dauren_home Exp $
 */
package com.realsoft.commons.beans.security.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BSecurityItem implements Serializable {

	private static final long serialVersionUID = -769918974424252046L;

	private String taskName;

	private byte access;

	private List<FunctionSecurityItem> functionList = new ArrayList<FunctionSecurityItem>();

	public BSecurityItem(String taskName, byte access) {
		super();
		this.taskName = taskName;
		this.access = access;
	}

	public byte getAccess() {
		if (access > 0) {
			int isFunctionsAccessible = 0;
			for (FunctionSecurityItem item : functionList) {
				isFunctionsAccessible = isFunctionsAccessible
						+ item.getAccess();
			}
			access = (byte) isFunctionsAccessible;
		}
		return access;
	}

	public void setAccesible(byte access) {
		this.access = access;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void addFunctionSecurity(String functionName, byte access) {
		functionList.add(new FunctionSecurityItem(functionName, access));
	}

	public List<String> getFunctionNames() {
		List<String> functionNames = new ArrayList<String>();
		for (FunctionSecurityItem item : functionList) {
			functionNames.add(item.getFunctionName());
		}
		return functionNames;
	}

	public FunctionSecurityItem getFunction(String functionName)
			throws BSecurityException {
		for (FunctionSecurityItem item : functionList) {
			if (item.functionName.equals(functionName))
				return item;
		}
		throw new BSecurityException(
				CommonsBeansConstants.ERR_SYSTEM,
				"commons-beans.security-task.security-item.getting-function.error",
				"No such function error " + functionName);
	}

	public boolean isFunctionAccesible(String functionName) {
		for (FunctionSecurityItem item : functionList) {
			if (item.getFunctionName().equals(functionName))
				return item.getAccess() > 0;
		}
		return false;
	}

	public class FunctionSecurityItem implements Serializable {
		private String functionName;

		private byte access;

		public FunctionSecurityItem(String functionName, byte access) {
			super();

			this.functionName = functionName;
			this.access = access;
		}

		public String getFunctionName() {
			return functionName;
		}

		public void setFunctionName(String functionName) {
			this.functionName = functionName;
		}

		public byte getAccess() {
			return access;
		}

		public void setAccess(byte access) {
			this.access = access;
		}

	}

}
