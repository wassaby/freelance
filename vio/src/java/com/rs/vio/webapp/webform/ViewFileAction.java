package com.rs.vio.webapp.webform;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.BFileItem;
import com.teremok.commons.beans.file.IBFile;
import com.teremok.struts.helper.StrutsHelperConstants;
import com.teremok.struts.helper.StrutsUtils;

public class ViewFileAction extends Action {

	Logger log = Logger.getLogger(ViewFileAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ViewFileForm viewFileForm = (ViewFileForm)form;
		HttpSession session = request.getSession(true);
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
            
        	IBFile file = manager.getFile();

        	/*if (fileItem.isAuthorizationRequired())
        		if (accountInfo==null)
        			throw new CommonsBeansException("application.operation-not-permitted", "application.operation-not-permitted.message", "Not authorized user tried to get file");*/
        	BFileItem fileItem = file.getFile(viewFileForm.getId());
        	response.setContentType(fileItem.getContentType());
        	if (viewFileForm.isDownload())
        		response.setHeader("Content-Disposition", new StringBuffer().append("attachment; filename=").append(fileItem.getName()).toString());
        	response.getOutputStream().write(fileItem.getData());

        } catch (CommonsBeansException e) {
            log.error("Could not get file", e);
            StrutsUtils.processErrors(request, e.getErrorCode(), e.getMessageKey());
            return mapping
                    .findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
        return null;
	}

}
