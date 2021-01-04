package com.rs.vio.webapp.upload;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadReportFileForm extends ActionForm{
	
	private static final long serialVersionUID = -2578471504066228212L;

    private FormFile theFile;
    private long reportId;
    
	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		theFile = null;
		super.reset(arg0, arg1);
	}
	
	@Override
	public void reset(ActionMapping arg0, ServletRequest arg1) {
		theFile = null;
		super.reset(arg0, arg1);
	}
}
