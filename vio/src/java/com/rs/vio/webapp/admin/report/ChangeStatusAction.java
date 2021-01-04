package com.rs.vio.webapp.admin.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class ChangeStatusAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		ChangeStatusForm fForm = (ChangeStatusForm) form;

		if (request.getParameter("refuse") != null) {
			try {
				IComponentFactory manager = (IComponentFactory) getServlet()
						.getServletContext().getAttribute(
								VioConstants.MANAGER_ATTR);
				IBReport report = manager.getReport();

				report.rejectReport(fForm.getReportId(), VioConstants.REJECT_STATUS_ID, fForm.getReason());

				return mapping
						.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
			} catch (CommonsBeansException e) {
				return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
			}
		} else if (request.getParameter("update") != null) {
			try {
				IComponentFactory manager = (IComponentFactory) getServlet()
						.getServletContext().getAttribute(
								VioConstants.MANAGER_ATTR);
				IBReport report = manager.getReport();

				if (fForm.getTheFile().getFileName() == null
						|| fForm.getTheFile().getFileName().trim().length() == 0)

					report.changeReportStatus(fForm.getReportId(),
							fForm.getStatusId(), fForm.getInstanceId(),
							fForm.getReportNumber(), fForm.getComment(), fForm.getStatusComment());

				else
					report.changeReportStatus(fForm.getReportId(), fForm
							.getStatusId(), fForm.getInstanceId(), fForm
							.getReportNumber(), fForm.getTheFile()
							.getFileName(), fForm.getTheFile().getFileSize(),
							fForm.getTheFile().getFileData(), fForm
									.getTheFile().getContentType(), false, fForm.getComment(), fForm.getStatusComment());

				return mapping
						.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
			} catch (CommonsBeansException e) {
				return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
			}
		} else {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}

	}

}
