package com.daurenzg.satori.webapp.manager;

import org.apache.struts.action.ActionForm;

/**
 * @author Dauren
 *
 */
public class EditArticleForm extends ActionForm{
	
	private long langId;
	private long articleId;
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

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