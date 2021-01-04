/*
 * Created on 22.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAreaInfo.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;

/**
 * заявки для web dealer
 * 
 * @author temirbulatov
 * 
 */
public class BAreaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long areaId;

	private long areaIdS;

	private long areaIdR;

	private long areaIdT;

	/**
	 * 
	 * @param areaId
	 * @param areaIdS
	 * @param areaIdR
	 * @param areaIdT
	 */
	public BAreaInfo(long areaId, long areaIdS, long areaIdR, long areaIdT) {
		super();
		this.areaId = areaId;
		this.areaIdS = areaIdS;
		this.areaIdR = areaIdR;
		this.areaIdT = areaIdT;
	}

	public BAreaInfo() {
		super();
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public long getAreaIdR() {
		return areaIdR;
	}

	public void setAreaIdR(long areaIdR) {
		this.areaIdR = areaIdR;
	}

	public long getAreaIdS() {
		return areaIdS;
	}

	public void setAreaIdS(long areaIdS) {
		this.areaIdS = areaIdS;
	}

	public long getAreaIdT() {
		return areaIdT;
	}

	public void setAreaIdT(long areaIdT) {
		this.areaIdT = areaIdT;
	}

}
