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
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetReportsTypeAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetReportsTypeForm fForm = (GetReportsTypeForm) form;
		long start = VioConstants.START_POSITION_REPORTS;
		long count = VioConstants.COUNT_REPORTS;
		long reportCount;
		
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	List<BReportItem> reportList = new ArrayList<BReportItem>();
        	if (Long.valueOf(fForm.getPage()) != 0){
				start = fForm.getPage() * VioConstants.COUNT_REPORTS - VioConstants.COUNT_REPORTS;
			} else {
				start = fForm.getPage();
			}
        	
        	reportCount = report.getReportCountByType(fForm.getReportType());
        	reportList = report.getReportList(fForm.getReportType(), start, count);
        	request.setAttribute(VioConstants.PAGE, fForm.getPage());
        	request.setAttribute(VioConstants.REPORT_LIST, reportList);
        	session.setAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM, reportCount);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
        } catch (CommonsBeansException e) {
        	return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
	}

}
