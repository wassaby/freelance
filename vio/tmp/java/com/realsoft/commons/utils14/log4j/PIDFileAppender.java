/*
 * Created on Oct 24, 2007 10:02:23 AM
 *
 * Realsoft Ltd.
 * < --!-- JMan: Esanov Emil --!-- >
 * $Id: PIDFileAppender.java,v 1.2 2016/04/15 10:37:38 dauren_home Exp $
 */
package com.realsoft.commons.utils14.log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;

public class PIDFileAppender extends RollingFileAppender {

	public static final String PID = getPid();

	public PIDFileAppender() {
		super();
	}

	public PIDFileAppender(Layout layout, String filename, boolean append)
			throws IOException {
		super(layout, normalizeFile(filename), append);
	}

	public PIDFileAppender(Layout layout, String filename) throws IOException {
		super(layout, normalizeFile(filename));
	}

	public synchronized void setFile(String arg0, boolean arg1, boolean arg2,
			int arg3) throws IOException {
		super.setFile(normalizeFile(arg0), arg1, arg2, arg3);
	}

	private static final String normalizeFile(String fileName) {
		String newFileName = null;
		newFileName = fileName.replaceAll("%pid", PID);
		return newFileName;
	}

	private static final String getPid() {

		int pid = -1;

		String[] cmd = { "/bin/bash", "-c", "echo $PPID" };
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (process != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			try {
				pid = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			process.destroy();
		}

		String sPid = pid >= 0 ? Integer.toString(pid) : null;
		if (sPid == null || (sPid = sPid.trim()).length() == 0) {
			sPid = "";
		}

		return sPid;

	}

}
