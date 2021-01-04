package com.daurenzg.betstars.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.daurenzg.betstars.utils.BMessageItem;

public class ChatItem implements Serializable {

	private static final long serialVersionUID = 4380474370282337990L;
	private String contactPhone;
	private String contactName;
	private int newMessagesCount;
	private List<BMessageItem> messagelList;
	public void addMessage(BMessageItem item){
		messagelList.add(item);
	}
	public List<BMessageItem> getMessageList() {
		Collections.sort(messagelList, new Comparator<BMessageItem>() {
			public int compare(BMessageItem lhs, BMessageItem rhs) {
				if (lhs.getId()>rhs.getId())
					return -1;
				else
					return 1;
			}
		});
		return messagelList;
	}
	public void setMessagelList(List<BMessageItem> messagelList) {
		this.messagelList = messagelList;
	}

	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public int getNewMessagesCount() {
		return newMessagesCount;
	}
	public void setNewMessagesCount(int newMessagesCount) {
		this.newMessagesCount = newMessagesCount;
	}
	public ChatItem(String contactPhone, String contactName,
			int newMessagesCount) {
		super();
		this.messagelList = new ArrayList<BMessageItem>();
		this.contactPhone = contactPhone;
		this.contactName = contactName;
		this.newMessagesCount = newMessagesCount;
	}
}
