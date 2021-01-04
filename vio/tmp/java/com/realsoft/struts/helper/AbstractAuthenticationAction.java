/*
 * Created on 28.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: AbstractAuthenticationAction.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.struts.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author dimad
 */
public class AbstractAuthenticationAction extends ForwardAction {

	private static Logger log = Logger
			.getLogger(AbstractAuthenticationAction.class);

	public AbstractAuthenticationAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		rememberAction(mapping, request);
		if (!isAutorized(mapping, form, request)) {
			log.debug("Not authorized request");
			String previousAction = (String) request.getSession(true)
					.getAttribute(StrutsHelperConstants.PREVIOUS_ACTION_ATTR);
			log.debug("previousAction = " + previousAction);
			String errorMessageKey = "error-read-access.message";
			StrutsUtils.processErrors(request, "error.message",
					errorMessageKey, mapping.getPath());
			return new ActionForward(previousAction + ".do");
		}
		return super.execute(mapping, form, request, response);
	}

	protected boolean isAutorized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		Object loginInfo = request.getSession(true).getAttribute(
				StrutsHelperConstants.LOGIN_INFO_ATTR);
		log.debug("loginInfo != null = " + (loginInfo != null));
		return loginInfo != null;
	}

}
