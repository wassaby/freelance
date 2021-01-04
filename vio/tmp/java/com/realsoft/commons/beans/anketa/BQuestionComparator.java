/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BQuestionComparator.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
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