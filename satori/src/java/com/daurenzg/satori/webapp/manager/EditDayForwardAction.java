package com.daurenzg.satori.webapp.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.BCommonItemInfo;
import com.daurenzg.commons.beans.satori.item.BPushUpItem;
import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class EditDayForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		EditDayForwardForm dayForm = (EditDayForwardForm) form;
		IComponentFactory manager = (IComponentFactory) session
				.getServletContext().getAttribute(SatoriConstants.MANAGER_ATTR);
		IBItem item = manager.getItem();

		BCommonItemInfo commonItemInfo = (BCommonItemInfo) session
				.getAttribute(SatoriConstants.COMMON_ITEM_INFO);

		if (dayForm.getItemId() != 0 && dayForm.getDayId() != 0
				&& dayForm.getLangId() != 0) {
			if (commonItemInfo.getDayId() != dayForm.getDayId()
					|| commonItemInfo.getItemId() != dayForm.getItemId()
					|| commonItemInfo.getLangId() != dayForm.getLangId()) {
				commonItemInfo.setItemId(dayForm.getItemId());
				commonItemInfo.setDayId(dayForm.getDayId());
				commonItemInfo.setLangId(dayForm.getLangId());
				session.setAttribute(SatoriConstants.COMMON_ITEM_INFO, commonItemInfo);
			}
		}

		List<BPushUpItem> dayNotificationList = item.getDayNotificationList(
				commonItemInfo.getItemId(), commonItemInfo.getDayId(), commonItemInfo.getLangId());
		session.setAttribute(SatoriConstants.DAY_PUSH_UPS, dayNotificationList);
		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}
}
