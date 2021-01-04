/*
 * Created on 07.11.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IJob.java,v 1.2 2016/04/15 10:37:28 dauren_home Exp $
 */
package com.realsoft.utils.scheduler.jobs;

import com.realsoft.commons.utils14.RealsoftException;

public interface IJob {

	public void execute() throws RealsoftException;

}
