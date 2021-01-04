/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: AbstractMobileAuthorizationAction.java,v 1.6 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.rs.vio.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.teremok.struts.helper.ForwardAction;

public abstract class AbstractMobileAuthorizationAction extends ForwardAction {

	public AbstractMobileAuthorizationAction() {
		super();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
			HttpSession session = request.getSession(true);
			
			String authString = request.getHeader(VioConstants.MOBILE_ACTION_AUTH_ATTR_NAME);
			
			if (authString==null||authString.length()==0||!authString.equals("is_mobile")){
				return mapping.findForward("not-authorized-mobile-request-error");
			}
			
			return executeAction(mapping, form, request, response);
	}

    protected abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
	
}