/*
 * Created on Oct 24, 2007 10:02:23 AM
 *
 * Realsoft Ltd.
 * < --!-- JMan: Esanov Emil --!-- >
 */
package com.realsoft.commons.utils14.log4j;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

public class DateFileAppender extends RollingFileAppender {

	private static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			"yyyyMMdd");

	private String filenameWithPattern;

	private static Date currDate = new Date();

	private Date lastRecDate = new Date();

	private boolean append = true;

	public DateFileAppender() {
		super();
	}

	public DateFileAppender(Layout layout, String filename, boolean append)
			throws IOException {
		super(layout, normalizeFile(currDate, filename), append);
		this.filenameWithPattern = filename;
		this.append = append;
	}

	public DateFileAppender(Layout layout, String filename) throws IOException {
		super(layout, normalizeFile(currDate, filename));
		this.filenameWithPattern = filename;
	}

	public synchronized void setFile(String arg0, boolean arg1, boolean arg2,
			int arg3) throws IOException {
		super.setFile(normalizeFile(currDate, arg0), arg1, arg2, arg3);
		this.filenameWithPattern = arg0;
		this.append = arg1;
	}

	private static final String normalizeFile(Date date, String fileName) {
		return normalizeFile2(date, fileName);
	}

	private static final String normalizeFile2(Date date, String fileName) {
		String newFileName = "";
		int stPos = fileName.indexOf("%d", 0);
		if (stPos >= 0) {
			newFileName = newFileName + fileName.substring(0, stPos);
			if (fileName.charAt(stPos + 2) == '{') {
				int edPos = fileName.indexOf("}", stPos);
				String datePatern = fileName.substring(stPos + 3, edPos);
				newFileName = newFileName
						+ new SimpleDateFormat(datePatern).format(date);
				newFileName = newFileName + fileName.substring(edPos + 1);
			} else {
				newFileName = newFileName + defaultDateFormat.format(date);
				newFileName = newFileName + fileName.substring(stPos + 2);
			}
			normalizeFile2(date, newFileName);
		} else {
			newFileName = fileName;
			File file = new File(newFileName).getParentFile();
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		return newFileName;
	}

	protected void subAppend(LoggingEvent event) {
		currDate = new Date();
		int currDaysCount = (int) Math
				.floor((currDate.getTime() / (24 * 60 * 60 * 1000)));
		int lastRecDaysCount = (int) Math
				.floor((lastRecDate.getTime() / (24 * 60 * 60 * 1000)));
		if (currDaysCount != lastRecDaysCount) {
			try {
				closeFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				this.setFile(this.filenameWithPattern, append, bufferedIO,
						bufferSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			lastRecDate = currDate;
		}
		super.subAppend(event);
	}

}
