package com.realsoft.struts.helper;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.realsoft.utils.number.NumberUtils;

public class StrutsUtils {

	private static Logger log = Logger.getLogger(StrutsUtils.class);

	private StrutsUtils() {
		super();
	}

	public static void resetForm(HttpSession session, String formName) {
		session.setAttribute(formName, null);
	}

	public static void processErrors(HttpServletRequest request, String key,
			String messageKey, Object value) {
		log.debug("key = " + key + "; messageKey = " + messageKey
				+ "; value = " + value);
		ActionErrors errors = (ActionErrors) request
				.getAttribute(Globals.ERROR_KEY);
		errors = (errors == null) ? new ActionErrors() : errors;
		errors.add(key, new ActionMessage(messageKey, value));
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	public static void processErrors(HttpServletRequest request, String key,
			String messageKey) {
		log.debug("message = " + new ActionMessage(messageKey));
		ActionErrors errors = (ActionErrors) request
				.getAttribute(Globals.ERROR_KEY);
		errors = (errors == null) ? new ActionErrors() : errors;
		errors.add(key, new ActionMessage(messageKey));
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	public static void processErrors(HttpServletRequest request, String key,
			String prefix, String[] messageKeys, Object[] values,
			String exceptionMessage) {
		log.debug("processing errors form " + key + "; messageKeys.length = "
				+ messageKeys.length + "; values.length = " + values.length);
		ActionErrors errors = (ActionErrors) request
				.getAttribute(Globals.ERROR_KEY);
		errors = (errors == null) ? new ActionErrors() : errors;
		for (int i = 0; i < messageKeys.length; i++) {
			if (NumberUtils.isNumber(messageKeys[i])
					&& Integer.parseInt(messageKeys[i]) > 20000) {
				log.debug("messageKeys[" + i + "] = " + messageKeys[i]);
				String messageKey = ((prefix != null) ? prefix : "")
						+ ((!prefix.endsWith(".")) ? "." : "") + messageKeys[i];
				log.debug(messageKey);
				errors.add(key, new ActionMessage(messageKey, values));
			}
		}
		request.setAttribute(StrutsHelperConstants.EXCEPTION_MESSAGE_ATTR,
				exceptionMessage);
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	public static void processErrors(HttpServletRequest request, String key,
			String prefix, String[] messageKeys) {
		ActionErrors errors = (ActionErrors) request
				.getAttribute(Globals.ERROR_KEY);
		errors = (errors == null) ? new ActionErrors() : errors;
		// errors.add(key, new ActionMessage(prefix));
		for (int i = 0; i < messageKeys.length; i++) {
			log.debug("messageKeys[" + i + "] = " + messageKeys[i]);
			String messageKey = ((prefix != null) ? prefix : "")
					+ ((!prefix.endsWith(".")) ? "." : "") + messageKeys[i];
			log.debug(messageKey);
			errors.add(key, new ActionMessage(messageKey));
		}
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	public static String getErrorMessages(PageContext pageContext, String key)
			throws StrutsHelperException {
		ActionMessages errors = null;
		try {
			errors = TagUtils.getInstance().getActionMessages(pageContext,
					Globals.ERROR_KEY);
		} catch (JspException e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw new StrutsHelperException("Could not get error messages", e);
		}

		if ((errors == null) || errors.isEmpty()) {
			return null;
		}

		StringBuffer results = new StringBuffer();
		Iterator reports = (key == null) ? errors.get() : errors.get(key);

		try {
			while (reports.hasNext()) {
				ActionMessage report = (ActionMessage) reports.next();
				String message = TagUtils.getInstance().message(pageContext,
						null, Globals.LOCALE_KEY, report.getKey(),
						report.getValues());

				if (message != null) {
					results.append(message);
				}
			}
			return results.toString();
		} catch (Exception e) {
			throw new StrutsHelperException("Could not get error messages", e);
		}
	}

	public static void showErrorMessages(HttpServletRequest request) {
		ActionErrors errors = (ActionErrors) request
				.getAttribute(Globals.ERROR_KEY);
		if (errors != null && !errors.isEmpty()) {
			for (Iterator iter = errors.get(); iter.hasNext();) {
				ActionMessage message = (ActionMessage) iter.next();
				log.info(message.getKey() + " : ");
				Object[] values = message.getValues();
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						log.info(values[i]);
					}
				}
			}
		}
	}

	public static void setupSuccessPage(MessageResources resources,
			HttpServletRequest request, String successMessage) {
		String message = resources.getMessage(successMessage);
		request.setAttribute(StrutsHelperConstants.SUCCESS_MSG_ATTR, message);
	}

	public static void setupSuccessPage(MessageResources resources,
			HttpServletRequest request, String successMessage, Object[] objects) {
		String message = (objects == null) ? resources
				.getMessage(successMessage) : resources.getMessage(
				successMessage, objects);
		request.setAttribute(StrutsHelperConstants.SUCCESS_MSG_ATTR, message);
	}

	/**
	 * Устанавливает атрибуту запроса по имнеи
	 * StrutsHelperConstants.SUCCESS_TITLE_ATTR значение полученное по ниже
	 * описанному. Из resources (первый параметр =
	 * ApplicationResources.properties) берется значение по ключу = successTitle
	 * (входной параметр)
	 * 
	 * Устанавливает атрибуту запроса по имнеи
	 * StrutsHelperConstants.SUCCESS_MSG_ATTR значение полученное по ниже
	 * описанному. Если входной параметр objects равен null, то из resources
	 * (входной параметр) берется значение по ключу successMessage (входной
	 * параметр) иначе из resources (входной параметр) берется строка по ключу
	 * successMessage (входной параметр) и вормеруется сообщение на основе
	 * строки и обьектов objects (входной параметр).
	 */

	public static void setupSuccessPage(MessageResources resources,
			HttpServletRequest request, String successTitle,
			String successMessage, Object[] objects) {
		String titleMessage = resources.getMessage(successTitle);
		request.setAttribute(StrutsHelperConstants.SUCCESS_TITLE_ATTR,
				titleMessage);
		String message = (objects == null) ? resources
				.getMessage(successMessage) : resources.getMessage(
				successMessage, objects);
		request.setAttribute(StrutsHelperConstants.SUCCESS_MSG_ATTR, message);
	}

	public static void setupSuccessPage(MessageResources resources,
			HttpServletRequest request, String successTitle,
			String successMessage) {
		setupSuccessPage(resources, request, successTitle, successMessage, null);
	}
}
