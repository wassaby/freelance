/*
 * Created on 22.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BNDSItem.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.util;

import java.io.Serializable;

public class BNDSItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long ndsId;

	private String ndsName;

	private double nds;

	/**
	 * 
	 * @param ndsId
	 *            Идентификатор НДС
	 * @param ndsName
	 *            Наименование НДС
	 * @param nds
	 *            НДС
	 */
	public BNDSItem(long ndsId, String ndsName, double nds) {
		super();
		this.ndsId = ndsId;
		this.ndsName = ndsName;
		this.nds = nds;
	}

	public BNDSItem() {
		super();
	}

	public double getNds() {
		return nds;
	}

	public void setNds(double nds) {
		this.nds = nds;
	}

	public long getNdsId() {
		return ndsId;
	}

	public void setNdsId(long ndsId) {
		this.ndsId = ndsId;
	}

	public String getNdsName() {
		return ndsName;
	}

	public void setNdsName(String ndsName) {
		this.ndsName = ndsName;
	}

}
