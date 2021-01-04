package com.rs.vio.webapp.admin.report;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class DeleteImageAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		DeleteImageForm fForm = (DeleteImageForm) form;
		
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	report.deleteImage(fForm.getReportId(), fForm.getFileId());
        	BReportItem reportItem = report.getReport(fForm.getReportId());
        	session.setAttribute(VioConstants.ADMIN_VIEW_REPORT_ATTR, reportItem);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e){
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
