package com.daurenzg.betstars.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BTournamentItem implements Serializable{

	private static final long serialVersionUID = -8349226564721482421L;
	
	private long id;
	
	private String title = "Three-line item";
	private String text = "Secondary line text lorem ipsumdolor sit amet, consectetur adipiscing elit. Nam massa quam.";
	
	private String type;
	private long maxPlayers;
	private Date startAt;
	private long currentPlayers;
	private long numberOfRounds;
	private long entranceFee;
	private long rewardSecond;
	private long rewardThird;
	private long rewardFirst;
	private List<BAccountInfo> accountInfoList = new ArrayList<BAccountInfo>();
	
	public List<BAccountInfo> getAccountInfoList() {
		return accountInfoList;
	}
	public void setAccountInfoList(List<BAccountInfo> accountInfoList) {
		this.accountInfoList = accountInfoList;
	}
	public BTournamentItem() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(long maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	public long getCurrentPlayers() {
		return currentPlayers;
	}
	public void setCurrentPlayers(long currentPlayers) {
		this.currentPlayers = currentPlayers;
	}
	public long getNumberOfRounds() {
		return numberOfRounds;
	}
	public void setNumberOfRounds(long numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}
	public long getEntranceFee() {
		return entranceFee;
	}
	public void setEntranceFee(long entranceFee) {
		this.entranceFee = entranceFee;
	}
	public long getRewardFirst() {
		return rewardFirst;
	}
	public void setRewardFirst(long rewardFirst) {
		this.rewardFirst = rewardFirst;
	}
	public long getRewardSecond() {
		return rewardSecond;
	}
	public void setRewardSecond(long rewardSecond) {
		this.rewardSecond = rewardSecond;
	}
	public long getRewardThird() {
		return rewardThird;
	}
	public void setRewardThird(long rewardThird) {
		this.rewardThird = rewardThird;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public BTournamentItem(long id, String type, long maxPlayers, Date startAt,
			long currentPlayers, long numberOfRounds, long entranceFee,
			long rewardFirst, long rewardSecond, long rewardThird) {
		super();
		this.id = id;
		this.type = type;
		this.maxPlayers = maxPlayers;
		this.startAt = startAt;
		this.currentPlayers = currentPlayers;
		this.numberOfRounds = numberOfRounds;
		this.entranceFee = entranceFee;
		this.rewardFirst = rewardFirst;
		this.rewardSecond = rewardSecond;
		this.rewardThird = rewardThird;
	}
	public BTournamentItem(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}
	
}
