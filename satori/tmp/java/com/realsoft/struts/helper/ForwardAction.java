/*
 * Created on 04.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ForwardAction.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.struts.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ForwardAction extends Action {

	private static final Logger log = Logger.getLogger(ForwardAction.class);

	public ForwardAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Executing forward action");
		rememberAction(mapping, request);
		String path = (mapping.findForwards().length > 0) ? mapping
				.findForwards()[0] : StrutsHelperConstants.LOGIN_FORWARD;
		return mapping.findForward(path);
	}

	protected void rememberAction(ActionMapping mapping,
			HttpServletRequest request) {
		Boolean isFrame = new Boolean(request.getParameter("isFrame"));
		Boolean isPrint = new Boolean(request.getParameter("isPrint"));
		if (!isFrame && !isPrint) {
			HttpSession session = request.getSession(true);
			String previousAction = (String) session
					.getAttribute(StrutsHelperConstants.CURRENT_ACTION_ATTR);
			if (previousAction != null)
				session.setAttribute(
						StrutsHelperConstants.PREVIOUS_ACTION_ATTR,
						previousAction);
			else
				session.setAttribute(
						StrutsHelperConstants.PREVIOUS_ACTION_ATTR, "/"
								+ StrutsHelperConstants.INDEX_FORWARD);
			session.setAttribute(StrutsHelperConstants.CURRENT_ACTION_ATTR,
					mapping.getPath());
		}
	}

}
