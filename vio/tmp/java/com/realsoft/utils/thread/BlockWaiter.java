/*
 * Created on 26.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BlockWaiter.java,v 1.2 2016/04/15 10:37:49 dauren_home Exp $
 */

package com.realsoft.utils.thread;

import org.apache.log4j.Logger;

public class BlockWaiter {

	private static Logger log = Logger.getLogger(BlockWaiter.class);

	private static int SLEEP_TIME = 1000;

	private boolean condition = true;

	private Object monitor = new Object();

	public BlockWaiter() {
		super();
	}

	public void waiting() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					while (condition) {
						Thread.sleep(SLEEP_TIME);
					}
				} catch (InterruptedException e) {
					log.error("Sleep interrupted", e);
				}
				synchronized (monitor) {
					monitor.notify();
				}
			}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
		try {
			synchronized (monitor) {
				monitor.wait();
			}
		} catch (InterruptedException e) {
			log.error("Monitor interrupted", e);
		}
		log.debug("Block waiter terminated");
	}

	public void setCondition(boolean condition) {
		log.debug("Setting condition of block waiter to "+condition);
		this.condition = condition;
	}

}
