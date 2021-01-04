package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class NewPushUpAddAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		IComponentFactory manager = (IComponentFactory) session
				.getServletContext().getAttribute(
						SatoriConstants.MANAGER_ATTR);
		IBItem item = manager.getItem();
		NewPushUpAddForm newPushForm = (NewPushUpAddForm) form;
		
		item.addNewPushUp(newPushForm.getContent(), newPushForm.getItemId(), newPushForm.getDayId(), newPushForm.getLangId(), newPushForm.getTime());
		
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
