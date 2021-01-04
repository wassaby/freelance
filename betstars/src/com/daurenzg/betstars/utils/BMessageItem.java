package com.daurenzg.betstars.utils;

import java.io.Serializable;
import java.util.Date;

import com.daurenzg.betstars.wao.ProfileItem;

public class BMessageItem implements Serializable {

	private static final long serialVersionUID = -1000757294445036500L;
	private long id;
	private long userId;
	private long tournamentId;
	private String message;
	private Date createdAt;
	private ProfileItem profileItem;
	private boolean isRead = false;
	
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public ProfileItem getProfileItem() {
		return profileItem;
	}
	public void setProfileItem(ProfileItem profileItem) {
		this.profileItem = profileItem;
	}
	public BMessageItem(long id, long userId, long tournamentId,
			String message, Date createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.tournamentId = tournamentId;
		this.message = message;
		this.createdAt = createdAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
