/*
 * Created on 06.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: StrutsHelperConstants.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.struts.helper;

public class StrutsHelperConstants {

	protected StrutsHelperConstants() {
		super();
	}

	public static final String INDEX_FORWARD = "index";

	public static final String AUTHORIZE_FORWARD = "/authorize.do";

	public static final String LOGIN_FORWARD = "login";
	public static final String LOGIN_INFO_ATTR = "login-info";

	public static final String USERNAME_LIST_ATTR = "user-name-list";
	public static final String USERNAME_CURRENT_ATTR = "user-name-current";

	public static final String UNUNIQUE_USER_NAME_ERROR = "ununique-user-name.error.message";
	public static final String UNUNIQUE_SESSION_CLOSED = "unique-sesion-closed.message";

	public static final String ERROR_FORWARD = "error";
	public static final String FATAL_FORWARD = "fatal";

	public static final String CURRENT_ACTION_ATTR = "current-action";
	public static final String PREVIOUS_ACTION_ATTR = "previous-action";

	public static final String SUCCESS_TITLE_ATTR = "success-title";
	public static final String SUCCESS_MSG_ATTR = "success-msg";
	public static final String SUCCESS_MSG_OBJECTS_ATTR = "success-msg-objects";
	public static final String SUCCESS_FORWARD = "success";

	public static final String SESSION_LIST_ATTR = "http-session-list-attribute";

	public static final String NEXT_FORWARD = "next";

	public static final String USER_LIST_ATTR = "user-list";

	public static final String FORMATTER_FACTORY = "formatter-factory-attr";

	public static final String PRINT_PARAMETER_NAME = "print";

	public static final String APPLICATION_DEBUG_MODE = "debug";

	public static final String EXCEPTION_MESSAGE_ATTR = "application-exception-massage-attribute";
}
