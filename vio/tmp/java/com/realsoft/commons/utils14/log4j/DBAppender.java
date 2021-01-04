/*
 * Created on Oct 24, 2007 10:02:23 AM
 *
 * Realsoft Ltd.
 * < --!-- JMan: Esanov Emil --!-- >
 * $Id: DBAppender.java,v 1.2 2016/04/15 10:37:38 dauren_home Exp $
 */
package com.realsoft.commons.utils14.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;

public class DBAppender extends JDBCAppender {

	public DBAppender() {
		super();
	}

	public void append(LoggingEvent event) {

		System.out.println(event.getLevel());
		System.out.println(event.getLocationInformation());
		System.out.println(event.getLocationInformation().fullInfo);
		System.out.println(event.getLocationInformation().getClassName());
		System.out.println(event.getLocationInformation().getMethodName());
		System.out.println(event.getLocationInformation().getFileName());
		System.out.println(event.getLocationInformation().getLineNumber());
		System.out.println(event.getMDC("c"));
		System.out.println(event.getMDC("%c"));
		System.out.println(event.getLoggerName());
		System.out.println(event.getMessage());
		System.out.println(event.getNDC());
		System.out.println(event.getRenderedMessage());
		System.out.println(event.getThreadName());
		System.out.println(event.getStartTime());
		System.out.println(event.getMessage());
		System.out.println(event.fqnOfCategoryClass);
		Object mess = event.getMessage();
		if (mess instanceof String) {
			String sMess = (String) mess;
			sMess = sMess.replaceAll("\\r", "\\n");
			sMess = sMess.replaceAll("\\n", "\\\\n");
			LoggingEvent loggingEvent = new LoggingEvent(
					event.fqnOfCategoryClass, Logger
							.getLogger(event.fqnOfCategoryClass),
					event.timeStamp, event.getLevel(), sMess, (event
							.getThrowableInformation() == null ? null : event
							.getThrowableInformation().getThrowable()));
			System.out.println(loggingEvent.getMessage());
			super.append(loggingEvent);
		} else {
			super.append(event);
		}
	}

}
