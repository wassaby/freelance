package com.rs.vio.webapp.webform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class MapAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(true);
		GetReportsTypeForm page = (GetReportsTypeForm) form;
		//long reportCount = 0;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdfMY = new SimpleDateFormat("MM-yyyy");
		String compareDate = new StringBuffer().append(
				sdfMY.format(calendar.getTime())).toString();
		try {
			RequestObject requestObject = (RequestObject) session.getAttribute(VioConstants.GET_REPORTS_REQUEST_PARAMETERS);
			List<BReportItem> reportList = new ArrayList<BReportItem>(); 
			if (requestObject == null){
				requestObject = new RequestObject("all");
			} else if (page.getMonth() != null & requestObject.getMonth() != page.getMonth()){
				requestObject.setMonth(page.getMonth());
			} else if (page.getReportType() != null & requestObject.getReportType() != page.getReportType()){
				requestObject.setReportType(page.getReportType());
			} else {
				requestObject.setMonth("all");
			}
			
			/*if (page.getMonth() == null) {
				page.setMonth(compareDate);
			}
			RequestObject requestObject = new RequestObject(page.getMonth());*/
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();
			
			if (requestObject.getMonth().equals("all")){
				reportList = report.getReportList();
			} else {
				String[] str = page.getMonth().split("-");
				String month = str[0];
				String year = str[1];
				reportList = report.getMonthReportList(month, year);
			};
			
			//reportCount = report.getReportCount();
			request.setAttribute(VioConstants.REPORT_LIST, reportList);
			//session.setAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM, reportCount);
			session.setAttribute(VioConstants.GET_REPORTS_REQUEST_PARAMETERS,
					requestObject);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
			
		} catch (CommonsBeansException e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
}
