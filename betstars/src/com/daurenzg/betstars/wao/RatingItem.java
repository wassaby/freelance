package com.daurenzg.betstars.wao;

import java.io.Serializable;
import java.util.List;

public class RatingItem implements Serializable {
	
	private static final long serialVersionUID = 6478757735854528837L;
	private long id;
	private long countryId;
	private long cityId;
	private long userID;
	private long totalWins;
	private long totalGames;
	private long totalScore;
	private ProfileItem profileItem;
	private List<ProfileItem> profileItemList;
	
	public List<ProfileItem> getProfileItemList() {
		return profileItemList;
	}
	public void setProfileItemList(List<ProfileItem> profileItemList) {
		this.profileItemList = profileItemList;
	}
	public ProfileItem getProfileItem() {
		return profileItem;
	}
	public void setProfileItem(ProfileItem profileItem) {
		this.profileItem = profileItem;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(long totalWins) {
		this.totalWins = totalWins;
	}
	public long getTotalGames() {
		return totalGames;
	}
	public void setTotalGames(long totalGames) {
		this.totalGames = totalGames;
	}
	public long getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}
	
}
