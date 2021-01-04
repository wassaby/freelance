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

public class UploadReportAction extends /*AbstractMobileAuthorization*/Action {

	@Override
	public ActionForward execute/*Action*/(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UploadReportForm uploadReportForm = (UploadReportForm) form;
		long reportId;
		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBUser user = manager.getUser();
			IBMail mail = manager.getMail();
			
			if (uploadReportForm.getReportHeader() == null
					|| uploadReportForm.getReportHeader().isEmpty() || uploadReportForm.getReportHeader().trim().length() == 0)
				uploadReportForm.setReportHeader("empty");
			
			if (uploadReportForm.getLon() == null
					|| uploadReportForm.getLat() == null
					|| uploadReportForm.getLon().isEmpty()
					|| uploadReportForm.getLat().isEmpty()
					|| uploadReportForm.getLon().trim().length() == 0
					|| uploadReportForm.getLat().trim().length() == 0)

			reportId = user.newToReportTable(uploadReportForm.getUserId(),
					uploadReportForm.getReportHeader(),
					uploadReportForm.getComment(),
					VioConstants.REPORT_STATUS_NEW, uploadReportForm.getReportType());
			
			else reportId = user.newToReportTable(uploadReportForm.getUserId(),
					uploadReportForm.getReportHeader(),
					uploadReportForm.getComment(),
					VioConstants.REPORT_STATUS_NEW, uploadReportForm.getLon(), uploadReportForm.getLat(), uploadReportForm.getReportType(), VioConstants.FIRST_INSTANCE_ID);
			mail.sendMail(reportId, "Новая заявка", "В систему поступила новая заявка с ID ");
			request.setAttribute(VioConstants.INSERTED_REPORT_ID, new Long(
					reportId));
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
