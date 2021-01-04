/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BAnswereComparator.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
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