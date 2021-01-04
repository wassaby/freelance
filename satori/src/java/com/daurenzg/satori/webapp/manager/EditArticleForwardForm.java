package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

public class EditArticleForwardForm extends ActionForm{
	
	private long langId;
	private long articleId;

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

}