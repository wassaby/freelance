/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ViewFileForm.java,v 1.2 2016/04/15 10:37:30 dauren_home Exp $
 */
package com.rs.vio.webapp.webform;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.teremok.commons.beans.CommonsBeansConstants;

/**
 * @author dimad
 */
public class ViewFileForm extends ActionForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2578471504066228212L;

	private static Logger log = Logger.getLogger(ViewFileForm.class);

	private long id = CommonsBeansConstants.ID_NOT_DEFINED;

	private boolean download = false;
	
    public ViewFileForm() {
    	super();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

}
