/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BQuestionComparator.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.util.Comparator;

public class BQuestionComparator implements Comparator<BQuestionItem> {

	public int compare(BQuestionItem item1, BQuestionItem item2) {
		if (item1.getId() > item2.getId())
			return 1;
		if (item1.getId() < item2.getId())
			return -1;
		return 0;
	}

}