package com.daurenzg.betstars.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BBetItem implements Serializable {

	private static final long serialVersionUID = -8072401044719485490L;
	private String key = null;
	private List<BOptionItem> optionsList = new ArrayList<BOptionItem>();
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<BOptionItem> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<BOptionItem> optionsList) {
		this.optionsList = optionsList;
	}
	public BBetItem(String ley, List<BOptionItem> optionsList) {
		super();
		this.key = ley;
		this.optionsList = optionsList;
	}

	public BBetItem() {
		super();
	}
}
