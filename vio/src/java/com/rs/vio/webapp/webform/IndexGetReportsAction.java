package com.rs.vio.webapp.webform;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.RequestObject;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class IndexGetReportsAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetReportsTypeForm page = (GetReportsTypeForm) form;
		long start = VioConstants.START_POSITION_REPORTS;
		long count = VioConstants.COUNT_REPORTS;
		long reportCount = 0;
		
		try {
			RequestObject requestObject = (RequestObject) session.getAttribute(VioConstants.GET_REPORTS_REQUEST_PARAMETERS);
			List<BReportItem> reportList = new ArrayList<BReportItem>();
			
			IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	
        	
			if (requestObject == null){
				requestObject = new RequestObject("all", "all", 0);
			} else if (requestObject.getPage() != page.getPage()){
				requestObject.setPage(page.getPage());
			} else if (page.getMonth() != null & requestObject.getMonth() != page.getMonth()){
				requestObject.setMonth(page.getMonth());
			} else if (page.getReportType() != null & requestObject.getReportType() != page.getReportType()){
				requestObject.setReportType(page.getReportType());
			} else {
				requestObject.setMonth("all");
				requestObject.setPage(0);
				requestObject.setReportType("all");
			}
			
        	if (Long.valueOf(page.getPage()) != 0){
				start = requestObject.getPage() * VioConstants.COUNT_REPORTS - VioConstants.COUNT_REPORTS;
			} else {
				start = requestObject.getPage();
			}
        	
        	if (requestObject.getReportType().equals("all") && requestObject.getMonth().equals("all")){ 
        		reportList = report.getReportList(start, count);
        		reportCount = report.getReportCount();
        	} else if (!requestObject.getReportType().equals("all") && requestObject.getMonth().equals("all")){ 
        		reportList = report.getReportList(requestObject.getReportType(), start, count);
        		reportCount = report.getReportCountByType(requestObject.getReportType());
        	} else if (requestObject.getReportType().equals("all") && !requestObject.getMonth().equals("all")){ 
        		String[] str = requestObject.getMonth().split("-");
        		String month = str[0];
        		String year = str[1];
        		reportList = report.getMonthReportList(month, year, start, count);
        		reportCount = report.getReportCountByMonth(month, year);
        	} else if (!requestObject.getReportType().equals("all") && !requestObject.getMonth().equals("all")){ 
        		String[] str = requestObject.getMonth().split("-");
        		String month = str[0];
        		String year = str[1];
        		reportList = report.getReportList(requestObject.getReportType(), month, year, start, count);
        		reportCount = report.getReportCountByMonthType(requestObject.getReportType(), month, year);
        	}
        	session.setAttribute(VioConstants.GET_REPORTS_REQUEST_PARAMETERS, requestObject);
        	request.setAttribute(VioConstants.PAGE, requestObject.getPage());
        	request.setAttribute(VioConstants.REPORT_LIST, reportList);
        	session.setAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM, reportCount);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
        } catch (CommonsBeansException e) {
            return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
	}

}
