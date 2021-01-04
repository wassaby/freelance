package com.rs.vio.webapp.admin.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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

public class GetReportsAction extends AbstractAdminAction{
	
	private static Logger log = Logger.getLogger(GetReportsAction.class);
	
	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetReportsForm page = (GetReportsForm) form;
		long start = VioConstants.START_POSITION_REPORTS;
		long count = VioConstants.COUNT_REPORTS;
		long reportCount;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	
        	if (Long.valueOf(page.getPage()) != 0){
				start = page.getPage() * VioConstants.COUNT_REPORTS - VioConstants.COUNT_REPORTS;
			} else {
				start = page.getPage();
			}
        	List<BReportItem> reportList = report.getAdminReportList(start, count);
        	request.setAttribute(VioConstants.ADMIN_WAITING_REPORTS, reportList);
        	
        	reportCount = report.getAllWaitingReportCount();
        	session.setAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM, reportCount);
        	request.setAttribute(VioConstants.PAGE, page.getPage());
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
        } catch (CommonsBeansException e) {
            return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
	}

}
