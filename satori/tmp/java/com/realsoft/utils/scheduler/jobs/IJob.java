/*
 * Created on 07.11.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IJob.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.utils.scheduler.jobs;

import com.realsoft.commons.utils14.RealsoftException;

public interface IJob {

	public void execute() throws RealsoftException;

}
