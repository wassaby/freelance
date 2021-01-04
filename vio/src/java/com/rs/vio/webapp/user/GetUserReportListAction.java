package com.rs.vio.webapp.user;

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
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class GetUserReportListAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GetUserReportListForm userReportListForm = (GetUserReportListForm) form;
		try {
			HttpSession session = request.getSession(true);
			IComponentFactory manager = (IComponentFactory) session
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();

			List<BReportItem> reportList = report.getUserReportList(
					userReportListForm.getUserId(),
					userReportListForm.getStartPos(),
					userReportListForm.getCount());
			request.setAttribute(VioConstants.MOBILE_USER_REPORTS, reportList);

			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (Exception e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
