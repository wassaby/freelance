package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.BItemInfo;
import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class MoveItemToTrashForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MoveItemToTrashForwardForm moveForm = (MoveItemToTrashForwardForm) form;
		try{
			HttpSession session = request.getSession(true);	
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBItem item = manager.getItem();
			BItemInfo itemInfo = item.getItem(moveForm.getItemId(), moveForm.getLangId());
			session.setAttribute(SatoriConstants.ITEM, itemInfo);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e){
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
		
	}

}
