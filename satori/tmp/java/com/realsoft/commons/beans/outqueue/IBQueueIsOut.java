/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBQueueIsOut.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.outqueue;

import java.util.List;

public interface IBQueueIsOut {

	List<BQueueIsOutInfo> isQueueOut() throws BQueueOutException;

	BQueueIsOutInfo isQueueOutByPhone(String phone) throws BQueueOutException;

	BQueueIsOutInfo isQueueOutByCode(String code) throws BQueueOutException;
}
