package com.rs.vio.webapp.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.user.IBUser;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.mail.IBMail;
import com.teremok.struts.helper.StrutsHelperConstants;

public class UpdateReportAction extends /* AbstractMobileAuthorization */Action {

	@Override
	public ActionForward execute/* Action */(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UpdateReportForm updateReportForm = (UpdateReportForm) form;
		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBUser user = manager.getUser();
			IBMail mail = manager.getMail();
			
			if (updateReportForm.getLon() == null
					|| updateReportForm.getLat() == null
					|| updateReportForm.getLon().isEmpty()
					|| updateReportForm.getLat().isEmpty()
					|| updateReportForm.getLon().trim().length() == 0
					|| updateReportForm.getLat().trim().length() == 0)				
			user.backwardReportUpdate(updateReportForm.getUserId(),
					updateReportForm.getReportId(),
					updateReportForm.getComment(), VioConstants.REPORT_STATUS_NEW, updateReportForm.getReportType());
			else 
				user.backwardReportUpdate(updateReportForm.getUserId(),
						updateReportForm.getReportId(),
						updateReportForm.getComment(), VioConstants.REPORT_STATUS_NEW, updateReportForm.getLon(),
						updateReportForm.getLat(), updateReportForm.getReportType());
			mail.sendMail(updateReportForm.getReportId(), "Новая заявка", "В систему поступила новая заявка с ID ");
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.REPORT_INSERT_ERROR_CODE_ATTR,
					e.getErrorCode());
			request.setAttribute(VioConstants.REPORT_INSERT_ERROR_MESSAGE_ATTR,
					e.getMessage());
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		}
	}
}
