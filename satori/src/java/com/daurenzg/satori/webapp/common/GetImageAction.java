package com.daurenzg.satori.webapp.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.BFileItem;
import com.teremok.commons.beans.file.IBFile;

public class GetImageAction extends Action {

	Logger log = Logger.getLogger(GetImageAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetImageForm imgForm = (GetImageForm) form;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(SatoriConstants.MANAGER_ATTR);
        	IBFile file = manager.getFile();
        	
        	BFileItem fileItem = file.getFile(imgForm.getFileId());
        	response.getOutputStream().write(fileItem.getData());
			return null;
        } catch (Exception e) {
            return null;
        }
	}

}
