/*
 * Created on 28.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: AbstractDealerAction.java,v 1.1 2014/07/01 11:58:20 dauren_work Exp $
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
public abstract class AbstractDealerAction extends AbstractAuthenticationAction {

	private static Logger log = Logger.getLogger(AbstractDealerAction.class);

	public AbstractDealerAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		rememberAction(mapping, request);
		if (!isAutorized(mapping, form, request)) {
			log.debug("Not authorized request");
			StrutsUtils.processErrors(request, "not-authorized-request",
					"not-authorized-request.message", mapping.getPath());
			return new ActionForward(StrutsHelperConstants.AUTHORIZE_FORWARD);
		}

		return executeAction(mapping, form, request, response);
	}

	protected abstract ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
