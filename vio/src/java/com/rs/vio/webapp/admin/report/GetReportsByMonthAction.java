package com.rs.vio.webapp.admin.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;

public class GetReportsByMonthAction extends AbstractAdminAction{
	
	private static Logger log = Logger.getLogger(GetReportsByMonthAction.class);
	
	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		//GetReportsByMonthForm fForm = (GetReportsByMonthForm) form;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBReport report = manager.getReport();
        	//List<BReportItem> reportList = report.getMonthReportList(fForm.getMonthNumber());
        	
        } catch (CommonsBeansException e) {
        }
		return null;
	}

}
