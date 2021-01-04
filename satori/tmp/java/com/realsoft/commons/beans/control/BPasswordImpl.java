/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPasswordImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

public class BPasswordImpl extends AbstractControl implements IBPassword {

	private static final long serialVersionUID = 3536053024583351841L;

	public BPasswordImpl(String name) {
		super(name);
	}

	public BPasswordImpl(String name, Object value)
			throws CommonsControlException {
		super(name);
		setValue(value);
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BPasswordImpl) {
			BPasswordImpl passwordModel = (BPasswordImpl) model;
			setValue(passwordModel.getValue());
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public String toString() {
		return "\nPASSWORD[" + name + " = " + value.toString();
	}

	public void copyFrom(BPasswordImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
	}

}
