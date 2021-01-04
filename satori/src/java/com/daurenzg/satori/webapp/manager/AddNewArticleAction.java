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
import com.teremok.commons.beans.file.IBFile;
import com.teremok.struts.helper.StrutsHelperConstants;

public class AddNewArticleAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		AddNewArticleForm articleForm = (AddNewArticleForm) form;
		IComponentFactory manager = (IComponentFactory) session
				.getServletContext().getAttribute(SatoriConstants.MANAGER_ATTR);
		IBItem item = manager.getItem();
		IBFile file = manager.getFile();
		long fileId = 0;
		
		if (articleForm.getFile().getFileName().length() != 0
				&& articleForm.getFile().getFileData().length != 0
				&& articleForm.getFile().getFileSize() != 0) {
			fileId = file.insertFile(articleForm.getFile().getFileName(),
					articleForm.getFile().getFileSize(), articleForm.getFile()
							.getFileData(), articleForm.getFile()
							.getContentType(), false);
		}

		item.addNewArticle(articleForm.getText(),
				articleForm.getNotificationId(), articleForm.getLangId(),
				fileId);

		return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
