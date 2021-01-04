package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.BCommonItemInfo;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.struts.helper.StrutsHelperConstants;

public class NewPushUpForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		
		NewPushUpForwardForm pushForm = (NewPushUpForwardForm) form;
		BCommonItemInfo pushItem = new BCommonItemInfo();
		pushItem.setDayId(pushForm.getDayId());
		pushItem.setItemId(pushForm.getItemId());
		pushItem.setLangId(pushForm.getLangId());
		session.setAttribute(SatoriConstants.NEW_PUSH_UP, pushItem);
		
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
