/*
 * Created on 05.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBQueueIsOut.java,v 1.2 2016/04/15 10:37:34 dauren_home Exp $
 */
package com.realsoft.commons.beans.outqueue;

import java.util.List;

public interface IBQueueIsOut {

	List<BQueueIsOutInfo> isQueueOut() throws BQueueOutException;

	BQueueIsOutInfo isQueueOutByPhone(String phone) throws BQueueOutException;

	BQueueIsOutInfo isQueueOutByCode(String code) throws BQueueOutException;
}
