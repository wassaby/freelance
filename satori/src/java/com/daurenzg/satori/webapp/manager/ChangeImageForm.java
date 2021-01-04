package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ChangeImageForm extends ActionForm{
	
	private long articleId;
	private long fileId;
	private FormFile file;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}


}
