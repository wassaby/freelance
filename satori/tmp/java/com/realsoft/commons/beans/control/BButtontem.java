/*
 * Created on 28.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BButtontem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BButtontem extends BLabelItem implements IBItem {

	private static final long serialVersionUID = 2042730293345133179L;

	private Object id = null;

	public BButtontem() {
		super();
	}

	public BButtontem(Object id) {
		super();
		this.id = id;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof BButtontem))
			return false;
		BButtontem castOther = (BButtontem) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

}
