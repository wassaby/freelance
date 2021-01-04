package com.rs.vio.webapp.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.IBFile;
import com.teremok.struts.helper.StrutsHelperConstants;

public class UploadReportFileAction extends /*AbstractMobileAuthorization*/Action {

	@Override
	public ActionForward execute/*Action*/(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UploadReportFileForm uploadReportForm = (UploadReportFileForm) form;
		HttpSession session = request.getSession(true);
		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBFile file = manager.getFile();
			long fileId = 0;

			fileId = file.insertFile(uploadReportForm.getTheFile()
					.getFileName(), uploadReportForm.getTheFile()
					.getFileSize(), uploadReportForm.getTheFile()
					.getFileData(), uploadReportForm.getTheFile()
					.getContentType(), false);

			long report_file_id = file.addFileToReport(fileId,
					uploadReportForm.getReportId(), VioConstants.NOT_SCAN, VioConstants.NOT_DELETED);
			request.setAttribute(VioConstants.INSERTED_FILE_ID, new Long(
					report_file_id));
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);

		} catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.LOGIN_ERROR_CODE_ATTR,
					e.getErrorCode());
			request.setAttribute(VioConstants.LOGIN_ERROR_MESSAGE_ATTR,
					e.getMessage());
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		}
	}
}
