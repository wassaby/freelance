package com.rs.vio.webapp.webform;

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

public class GetReportsAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetReportsForm fForm = (GetReportsForm) form;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	
        	List<BReportItem> reportList = report.getReportList(fForm.getStartPos(), fForm.getCount());
        	
        	session.setAttribute(VioConstants.ADMIN_REPORT_LIST_BY_ATTR, reportList);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
        } catch (CommonsBeansException e) {
            return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
        }
	}

}
