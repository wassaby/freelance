package com.realsoft.utils.thread;

public class ThreadRunner {

	public ThreadRunner(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
	}

}
