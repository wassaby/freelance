/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ViewPhotoForm.java,v 1.2 2014/08/05 18:35:45 dauren_home Exp $
 */
package com.daurenzg.satori.webapp.common;

import org.apache.struts.action.ActionForm;

import com.teremok.commons.beans.CommonsBeansConstants;

/**
 * @author dimad
 */
public class ViewPhotoForm extends ActionForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2578471504066228212L;

//	private static Logger log = Logger.getLogger(ViewLotForm.class);

	private long reportId = CommonsBeansConstants.ID_NOT_DEFINED;
	
	private long photo = CommonsBeansConstants.ID_NOT_DEFINED;

	public ViewPhotoForm() {
		super();
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public long getPhoto() {
		return photo;
	}

	public void setPhoto(long photo) {
		this.photo = photo;
	}

}
