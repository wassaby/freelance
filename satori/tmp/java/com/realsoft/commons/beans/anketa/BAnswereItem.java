/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BAnswereItem.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BAnswereItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public BAnswereItem(long id, long questiuonId, String name,
			String dispType, long parentId, BAnswereItem parent,
			List<BAnswereItem> children) {
		this.id = id;
		this.questionId = questiuonId;
		this.name = name;
		this.displayType = dispType;
		this.parentId = parentId;
		this.parent = parent;
		this.children = children;
	}

	private long id;

	private long parentId;

	private long questionId;

	private String name;

	private String displayType;

	private BAnswereItem parent;

	private List<BAnswereItem> children;

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String toString() {
		return this.name;
	}

	public List<BAnswereItem> getChildren() {
		return children;
	}

	public void addChildren(BAnswereItem child) {
		if (this.children == null)
			this.children = new ArrayList<BAnswereItem>();
		this.children.add(child);
	}

	public boolean hasChildren() {
		return children == null ? false : children.size() > 0;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public BAnswereItem getParent() {
		return parent;
	}

	public void setParent(BAnswereItem parent) {
		this.parent = parent;
	}

	public int getLevel() {
		int cnt = 0;
		BAnswereItem item = this;
		while (item.parent != null) {
			cnt++;
			item = item.parent;
		}
		return cnt;
	}

}
