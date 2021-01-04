/*
 *
 * Created on 11.07.2006
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: IBAnketa.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.util.List;
import java.util.SortedMap;

/**
 * 
 * @author dimad
 */
public interface IBAnketa {
	/**
	 * 
	 * @return
	 * @throws BAnketaException
	 */
	SortedMap<BQuestionItem, SortedMap<Long, BAnswereItem>> getQuestionsAnsweres()
			throws BAnketaException;

	/**
	 * 
	 * @param accountId
	 * @return
	 * @throws BAnketaException
	 */
	boolean isAlreadyFilled(long accountId) throws BAnketaException;

	/**
	 * 
	 * @param list
	 * @throws BAnketaException
	 */
	void fillAnketa(List<BRowToInsertItem> list) throws BAnketaException;
}
