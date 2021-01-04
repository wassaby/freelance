/*
 * Created on 23.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ErrorsTag.java,v 1.2 2016/04/15 10:37:37 dauren_home Exp $
 */
package com.realsoft.struts.helper.tags;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import com.realsoft.struts.helper.StrutsHelperConstants;

public class ErrorsTag extends org.apache.struts.taglib.html.ErrorsTag {

	private static Logger log = Logger.getLogger(ErrorsTag.class);

	public ErrorsTag() {
		super();
	}

	public int doStartTag() throws JspException {

		// Were any error messages specified?
		ActionMessages errors = null;
		try {
			errors = TagUtils.getInstance()
					.getActionMessages(pageContext, name);
		} catch (JspException e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}

		if ((errors == null) || errors.isEmpty()) {
			return (EVAL_BODY_INCLUDE);
		}

		boolean headerPresent = TagUtils.getInstance().present(pageContext,
				bundle, locale, getHeader());

		boolean footerPresent = TagUtils.getInstance().present(pageContext,
				bundle, locale, getFooter());

		boolean prefixPresent = TagUtils.getInstance().present(pageContext,
				bundle, locale, getPrefix());

		boolean suffixPresent = TagUtils.getInstance().present(pageContext,
				bundle, locale, getSuffix());

		// Render the error messages appropriately
		StringBuffer results = new StringBuffer();
		boolean headerDone = false;
		String message = null;
		Iterator reports = (property == null) ? errors.get() : errors
				.get(property);

		while (reports.hasNext()) {
			ActionMessage report = (ActionMessage) reports.next();
			if (!headerDone) {
				if (headerPresent) {
					message = TagUtils.getInstance().message(pageContext,
							bundle, locale, getHeader());

					results.append(message);
				}
				headerDone = true;
			}

			if (prefixPresent) {
				message = TagUtils.getInstance().message(pageContext, bundle,
						locale, getPrefix());
				results.append(message);
			}

			message = TagUtils.getInstance().message(pageContext, bundle,
					locale, report.getKey(), report.getValues());

			if (message != null) {
				results.append(message);
			} else if (report.getKey() != null) {
				if (Boolean.valueOf(System.getProperty(
						StrutsHelperConstants.APPLICATION_DEBUG_MODE, "false"))) {
					log.debug("Running in debug mode, messageKey = "
							+ report.getKey());
					results.append(report.getKey());
				} else {
					String exceptionMessage = (String) pageContext
							.getRequest()
							.getAttribute(
									StrutsHelperConstants.EXCEPTION_MESSAGE_ATTR);
					;
					log.debug("Running in normal mode, exceptionMessage = "
							+ exceptionMessage);
					results.append(exceptionMessage == null ? report.getKey()
							: exceptionMessage);
				}

			}

			if (suffixPresent) {
				message = TagUtils.getInstance().message(pageContext, bundle,
						locale, getSuffix());
				results.append(message);
			}
		}

		if (headerDone && footerPresent) {
			message = TagUtils.getInstance().message(pageContext, bundle,
					locale, getFooter());
			results.append(message);
		}

		TagUtils.getInstance().write(pageContext, results.toString());

		return (EVAL_BODY_INCLUDE);

	}

}
