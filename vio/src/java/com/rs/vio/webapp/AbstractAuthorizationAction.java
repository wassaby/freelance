package com.rs.vio.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.beans.login.BLoginInfo;
import com.rs.vio.webapp.login.LoginAction;
import com.teremok.commons.beans.CommonsBeansConstants;
import com.teremok.struts.helper.ForwardAction;
import com.teremok.struts.helper.StrutsHelperConstants;
import com.teremok.struts.helper.StrutsUtils;

public abstract class AbstractAuthorizationAction extends ForwardAction{
	
	private static final Logger log = Logger.getLogger(LoginAction.class);
	
	public AbstractAuthorizationAction(){
		super();
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
			HttpSession session = request.getSession(true);
    		
			BLoginInfo loginInfo = (BLoginInfo) session.getAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR);
			if (loginInfo == null || loginInfo.getLogin().equals("") || loginInfo.getLogin().length() == 0)
			{
				StrutsUtils.processErrors(request, CommonsBeansConstants.OPERATION_NOT_PERMITTED_ERROR_CODE, CommonsBeansConstants.OPERATION_NOT_PERMITTED_ERROR_MESSAGE_KEY);
				return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
			}
			return executeAction(mapping, form, request, response);
	}
	
	protected abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

}
