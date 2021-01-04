/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BObjectImpl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public class BObjectImpl extends AbstractControl implements IBObject {

	private static final long serialVersionUID = -3071551949017082950L;

	private IBModelPanel modelPanel = null;

	public BObjectImpl(String name) {
		super(name);
	}

	public BObjectImpl(String name, Object value)
			throws CommonsControlException {
		super(name);
		setValue(value);
	}

	public BObjectImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BObjectImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BObjectImpl) {
			BObjectImpl objectModel = (BObjectImpl) model;
			setValue(objectModel.getValue());
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		return "\nOBJECT[" + name + " = " + value.toString();
	}

}
