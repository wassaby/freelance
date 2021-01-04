package com.daurenzg.satori.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.login.BLoginInfo;
import com.teremok.commons.beans.CommonsBeansConstants;
import com.teremok.struts.helper.AbstractDealerAction;
import com.teremok.struts.helper.StrutsHelperConstants;
import com.teremok.struts.helper.StrutsUtils;

public abstract class AbstractManagerAction extends AbstractDealerAction{
	
	private static final Logger log = Logger.getLogger(AbstractManagerAction.class);
	private static final int ROLE_MANAGER = 2;
	
	@Override
	protected ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(true);
		BLoginInfo loginInfo = (BLoginInfo) session.getAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR);
		if (loginInfo==null||loginInfo.getAccountTypeId()!=ROLE_MANAGER){
			log.debug(new StringBuffer().append("WARNING! User tried to access to manager area, login = \"").append(loginInfo.getLogin()).append("\"").toString());
			StrutsUtils.processErrors(request, CommonsBeansConstants.OPERATION_NOT_PERMITTED_ERROR_CODE, CommonsBeansConstants.OPERATION_NOT_PERMITTED_ERROR_MESSAGE_KEY);
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		} 
				return executeManagerAction(mapping, form, request, response);
		
	}
	
	 protected abstract ActionForward executeManagerAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws Exception;
	
}
