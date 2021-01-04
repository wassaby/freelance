/*
 * Created on 10.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRequestDirectionItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;

public class BRequestDirectionItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID службы, обрабатывающей наряды.
	 */
	private long directId;

	/**
	 * ID пункта пункта назначения, внутри службы.
	 */

	private long directDetail;

	private long masterId;

	/**
	 * Наименовыние службы, обрабатывающей наряды.
	 */

	private String name = null;

	private String note = null;

	private int visible;

	private String fullName = null;

	public BRequestDirectionItem() {
		super();
	}

	public BRequestDirectionItem(long directId, long directDetail) {
		super();
		this.directId = directId;
		this.directDetail = directDetail;
	}

	public BRequestDirectionItem(long directId, long masterId, String name,
			String note, int visible, String fullName) {
		super();
		this.directId = directId;
		this.masterId = masterId;
		this.name = name;
		this.note = note;
		this.visible = visible;
		this.fullName = fullName;
	}

	public long getDirectId() {
		return directId;
	}

	public void setDirectId(long directId) {
		this.directId = directId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public long getDirectDetail() {
		return directDetail;
	}

	public void setDirectDetail(long directDetail) {
		this.directDetail = directDetail;
	}

}
