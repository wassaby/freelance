package com.rs.vio.webapp.webform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.ForwardAction;
import com.teremok.struts.helper.StrutsHelperConstants;
import com.teremok.struts.helper.StrutsUtils;

public class ViewPhotoAction extends ForwardAction {

	private static final Logger log = Logger.getLogger(ViewPhotoAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		ViewPhotoForm viewPhotoForm = (ViewPhotoForm) form;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	BReportItem reportItem = (BReportItem) session.getAttribute(VioConstants.VIEW_REPORT_ATTR);;
        	if (reportItem==null||reportItem.getReportId()!=viewPhotoForm.getReportId())
        		reportItem = report.getReport(viewPhotoForm.getReportId());
        	if (viewPhotoForm.getPhoto()==CommonsBeansConstants.ID_NOT_DEFINED)
        		session.setAttribute(VioConstants.REPORT_MAIN_PHOTO_NUM_ATTR, new Integer(0));
        	else if(reportItem.getListReportFileId() !=null && viewPhotoForm.getPhoto()<0)
        		session.setAttribute(VioConstants.REPORT_MAIN_PHOTO_NUM_ATTR, new Integer(0));
        	else if(reportItem.getListReportFileId()!=null&&viewPhotoForm.getPhoto()>reportItem.getListReportFileId().size()-1)
        		session.setAttribute(VioConstants.REPORT_MAIN_PHOTO_NUM_ATTR, new Integer(reportItem.getListReportFileId().size()-1));
        	else
        		session.setAttribute(VioConstants.REPORT_MAIN_PHOTO_NUM_ATTR, new Integer((int)viewPhotoForm.getPhoto()));
        	
        	session.setAttribute(VioConstants.VIEW_REPORT_ATTR, reportItem);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
        } catch (CommonsBeansException e) {
            log.error("Could not EditRubricAction", e);
            StrutsUtils.processErrors(request, e.getErrorCode(), e.getMessageKey());
            return mapping
                    .findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
	}
	
}
