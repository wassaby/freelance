/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BQuestionItem.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.io.Serializable;

public class BQuestionItem implements Comparable<BQuestionItem>, Serializable {

	private static final long serialVersionUID = 1L;

	public BQuestionItem(long id, String name) {
		this.id = id;
		this.name = name;
	}

	private long id;

	private String name;

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

	public int compareTo(BQuestionItem item) {
		if (this.id < item.getId())
			return -1;
		else if (this.id > item.getId())
			return 1;
		else
			return 0;
	}
}
