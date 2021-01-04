package com.daurenzg.satori.webapp.manager;


import java.util.List;

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

public class AddItemNewLanguageForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			HttpSession session = request.getSession(true);
			AddItemNewLanguageForwardForm itemForm = (AddItemNewLanguageForwardForm) form;
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBItem item = manager.getItem();
			List<BItemInfo> oneGroupItemList = item.getOneGroupItemList(itemForm.getItemId());
			List<BItemInfo> langList = item.getLanguageList();
			List<BItemInfo> statusList = item.getStatuses();
			session.setAttribute(SatoriConstants.LANGUAGE_LIST, langList);
			session.setAttribute(SatoriConstants.STATUSES, statusList);
			session.setAttribute(SatoriConstants.ONE_GROUP_ITEM_LIST, oneGroupItemList);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
}
