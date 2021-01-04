package com.daurenzg.satori.webapp.manager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.satori.webapp.AbstractManagerAction;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.IBFile;
import com.teremok.struts.helper.StrutsHelperConstants;

public class ChangeImageAction extends AbstractManagerAction {

	@Override
	protected ActionForward executeManagerAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			HttpSession session = request.getSession(true);
			ChangeImageForm articleForm = (ChangeImageForm) form;
			IComponentFactory manager = (IComponentFactory) session.getServletContext().getAttribute(SatoriConstants.MANAGER_ATTR);
			IBFile file = manager.getFile();
			long fileId = file.changeImage(articleForm.getFile().getFileName(),
					articleForm.getFile().getFileSize(), articleForm.getFile()
							.getFileData(), articleForm.getFile()
							.getContentType(), false, articleForm.getArticleId(), articleForm.getFileId());
			
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e){
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
}
