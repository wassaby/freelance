/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BButtonImpl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public class BButtonImpl extends AbstractControl implements IBButton {

	private static final long serialVersionUID = -379870308888255718L;

	private String image = null;

	public BButtonImpl(String name) {
		super(name);
	}

	public BButtonImpl(String name, Object value)
			throws CommonsControlException {
		super(name);
		setValue(value);
	}

	public BButtonImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BButtonImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public String toString() {
		return "\nBUTTON[" + name + ";" + value + ";" + isEnabled + ";"
				+ backgroundImage + ";" + image + "]";
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BButtonImpl) {
			copyFrom((BButtonImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public Class getType() throws CommonsControlException {
		return null;
	}

	public void setType(Class type) throws CommonsControlException {
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void copyFrom(BButtonImpl impl) throws CommonsControlException {
		super.copyFrom(impl);
		setImage(impl.getImage());
	}

}
