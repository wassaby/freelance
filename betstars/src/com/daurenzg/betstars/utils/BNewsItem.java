package com.daurenzg.betstars.utils;

import java.io.Serializable;
import java.util.List;

public class BNewsItem implements Serializable{
	
	private static final long serialVersionUID = -4216344925290928651L;
	private int mainPicture;
	private String newsTitle;
	private String rubricName;
	private List<String> rubricTextParagraph;
	private String photoUrl;
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public List<String> getRubricTextParagraph() {
		return rubricTextParagraph;
	}
	public void setRubricTextParagraph(List<String> rubricTextParagraph) {
		this.rubricTextParagraph = rubricTextParagraph;
	}
	public int getMainPicture() {
		return mainPicture;
	}
	public void setMainPicture(int mainPicture) {
		this.mainPicture = mainPicture;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getRubricName() {
		return rubricName;
	}
	public void setRubricName(String rubricName) {
		this.rubricName = rubricName;
	}
	public BNewsItem(int mainPicture, String newsTitle, String rubricName,
			List<String> rubricTextParagraph) {
		super();
		this.mainPicture = mainPicture;
		this.newsTitle = newsTitle;
		this.rubricName = rubricName;
		this.rubricTextParagraph = rubricTextParagraph;
	}
	public BNewsItem(){
		
	};
}
