package com.daurenzg.satori.webapp.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.item.BCommonItemInfo;
import com.daurenzg.commons.beans.satori.item.BItemInfo;
import com.daurenzg.commons.beans.satori.item.IBItem;
import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class EditItemForwardAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			
			EditItemForwardForm itemForm = (EditItemForwardForm) form;
			
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBItem item = manager.getItem();
			
			if (itemForm.getItemId() == 0 && itemForm.getLangId() == 0){
				BCommonItemInfo commonItemInfoFromLangItem = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
				itemForm.setItemId(commonItemInfoFromLangItem.getItemId());
				itemForm.setLangId(commonItemInfoFromLangItem.getLangId());
			}
			
			List<BItemInfo> notificationList = item.getNotifications(
					itemForm.getItemId(), itemForm.getLangId());
			BItemInfo itemInfo = item.getItem(itemForm.getItemId(),
					itemForm.getLangId());
			BItemInfo parentInfo = item.getItem(itemInfo.getPid(),
					itemInfo.getLangId());
			List<BItemInfo> oneGroupItemList = item.getOneGroupItemList(itemForm.getItemId());
			BCommonItemInfo commonItemInfo = new BCommonItemInfo();
			commonItemInfo.setItemId(itemForm.getItemId());
			commonItemInfo.setLangId(itemForm.getLangId());
			commonItemInfo.setLanguage(itemInfo.getLangName());
			commonItemInfo.setItemName(itemInfo.getName());
			session.setAttribute(SatoriConstants.COMMON_ITEM_INFO,
					commonItemInfo);
			session.setAttribute(SatoriConstants.ITEM, itemInfo);
			session.setAttribute(SatoriConstants.PARENT_ITEM, parentInfo);
			session.setAttribute(SatoriConstants.PUSH_UPS, notificationList);
			session.setAttribute(SatoriConstants.ONE_GROUP_ITEM_LIST, oneGroupItemList);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
}
