/*
 * Created on 20.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: GetSizedImageForm.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.rs.vio.webapp.webform;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
 * @author dimad
 */
public class GetSizedImageForm extends ActionForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2578471504066228212L;

	private static Logger log = Logger.getLogger(GetSizedImageForm.class);
	
	private int width;
	private int height;
	private long fileId;
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	
	
}
