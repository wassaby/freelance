package com.daurenzg.satori.webapp.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.dict.BDictInfo;
import com.daurenzg.commons.beans.satori.dict.IBDict;
import com.daurenzg.commons.beans.satori.item.BItemInfo;
import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractAdminAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetInfoAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBDict dict = manager.getDict();
			IBItem item = manager.getItem();

			List<BDictInfo> dictUsers = dict.getUsers();
			List<BItemInfo> dictStatuses = item.getStatuses();
			List<BItemInfo> dictTypes = item.getItemTypes();
			List<BItemInfo> dictLangs = item.getLanguageList();

			session.setAttribute(SatoriConstants.LANGUAGE_LIST, dictLangs);
			session.setAttribute(SatoriConstants.ITEM_TYPES, dictTypes);
			session.setAttribute(SatoriConstants.STATUSES, dictStatuses);
			session.setAttribute(SatoriConstants.USER_LIST, dictUsers);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
