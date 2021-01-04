package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class DeleteArticleForm extends ActionForm{
	
	private long fileId;
	private long articleId;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

}