/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BAnswereComparator.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.util.Comparator;

public class BAnswereComparator implements Comparator<Long> {

	public int compare(Long item1, Long item2) {
		if (item1.longValue() > item2.longValue())
			return 1;
		if (item1.longValue() < item2.longValue())
			return -1;
		return 0;
	}

}