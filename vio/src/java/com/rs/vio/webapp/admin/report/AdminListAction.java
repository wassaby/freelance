package com.rs.vio.webapp.admin.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.BReportStatusItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.RequestObject;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class AdminListAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		AdminListForm page = (AdminListForm) form;
		long start = VioConstants.START_POSITION_REPORTS;
		long count = VioConstants.COUNT_REPORTS;
		long reportCount = 0;
		
		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();
			List<BReportItem> reportList = new ArrayList<BReportItem>();
			RequestObject requestObject = (RequestObject) session.getAttribute(VioConstants.GET_ADMIN_REPORTS_REQUEST_PARAMETERS);
			
			if (requestObject == null){
				requestObject = new RequestObject(0, 0);
			} else if (requestObject.getPage() != page.getPage()){
				requestObject.setPage(page.getPage());
			//} else if (requestObject.getPage() != page.getPage() &) {
				
			} else if (page.getReportStatusId() != 0 & requestObject.getReportStatusId() != page.getReportStatusId()){
				requestObject.setReportStatusId(page.getReportStatusId());
			} else {
				requestObject.setPage(0);
				requestObject.setReportStatusId(0);
			}
			
			if (Long.valueOf(page.getPage()) != 0){
				start = requestObject.getPage() * VioConstants.COUNT_REPORTS - VioConstants.COUNT_REPORTS;
			} else {
				start = requestObject.getPage();
			}
			
			if (requestObject.getReportStatusId() == 0){ 
        		reportList = report.getAdminReportListAll(start, count);
        	} else if (requestObject.getReportStatusId() != page.getReportStatusId()) {
        		reportList = report.getAdminReportListAllByStatus(start, count, page.getReportStatusId());
        	} else {
        		reportList = report.getAdminReportListAllByStatus(start, count, requestObject.getReportStatusId());
        	}
			
			/*List<BReportItem> reportItem = report.getAdminReportListAll(start, count);*/
			List<BReportStatusItem> statusList = report.getReportStatuses();
			
			reportCount = report.getAllReportCount();
        	session.setAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM, reportCount);
        	session.setAttribute(VioConstants.GET_ADMIN_REPORTS_REQUEST_PARAMETERS, requestObject);
			request.setAttribute(VioConstants.PAGE, requestObject.getPage());
			request.setAttribute(VioConstants.ADMIN_REPORT_LIST_ALL, reportList);
			request.setAttribute(VioConstants.REPORT_STATUSES, statusList);
			
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.ALL_ERRORS, e.getMessageKey());
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
