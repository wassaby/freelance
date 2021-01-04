/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CControlBoxImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.dealer.control;

import java.util.ArrayList;
import java.util.List;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class CControlBoxImpl implements ICControlBox {

	private List<ICControl> controlCollection = new ArrayList<ICControl>();

	public CControlBoxImpl() {
		super();
	}

	public List<ICControl> getControlCollection() {
		return controlCollection;
	}

	public void addControl(ICControl control) {
		controlCollection.add(control);
	}

	public ICControl getControl(String name) throws CControlException {
		if (controlCollection == null)
			throw new CControlException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.CONTROL_NOT_FOUND_EXCEPTION,
					"No such control " + name + " found");
		for (ICControl control : controlCollection) {
			if (control != null && control.getName().equals(name))
				return control;
		}
		throw new CControlException(CommonsBeansConstants.ERR_SYSTEM,
				CommonsBeansConstants.CONTROL_NOT_FOUND_EXCEPTION,
				"No such control " + name + " found");
	}

}
