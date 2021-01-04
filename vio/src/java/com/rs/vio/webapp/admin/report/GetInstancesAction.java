package com.rs.vio.webapp.admin.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BInstanceItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetInstancesAction extends AbstractAdminAction{
	
	private static Logger log = Logger
			.getLogger(AbstractAdminAction.class);
	
	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		long methodId = Calendar.getInstance().getTimeInMillis();
		String logString = new StringBuffer().append(
				"executeBroadcasterAction called,  ").append(", methodId =")
				.append(methodId).toString();
		log.debug(logString);
		List<BInstanceItem> reportStList = new ArrayList<BInstanceItem>();
		try {
			IComponentFactory manager = (IComponentFactory) getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);

			IBReport report = manager.getReport();
			reportStList = report.getInstances();
			
			session.setAttribute(VioConstants.INSTANCES, reportStList);

			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.ALL_ERRORS, e.getMessageKey());
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
