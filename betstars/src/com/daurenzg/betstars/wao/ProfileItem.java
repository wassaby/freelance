package com.daurenzg.betstars.wao;

import java.io.Serializable;

public class ProfileItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1931710724285679800L;
	private int id;
	private String name;
	private long countryId;
	private long cityId;
	private String email;
	private String photoUrl;
	private boolean isReady;
	private boolean isBlocked;
	private long coins;
	
	public ProfileItem(String name, long countryId, long cityId, int id,
			String email, String photoUrl, boolean isReady, boolean isBlocked,
			long coins) {
		super();
		this.name = name;
		this.countryId = countryId;
		this.cityId = cityId;
		this.id = id;
		this.email = email;
		this.photoUrl = photoUrl;
		this.isReady = isReady;
		this.isBlocked = isBlocked;
		this.coins = coins;
	}

	public ProfileItem() {
		super();
	}
	public long getCoins() {
		return coins;
	}
	public void setCoins(long coins) {
		this.coins = coins;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryCode) {
		this.countryId = countryCode;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityCode) {
		this.cityId = cityCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isReady() {
		return isReady;
	}
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
}
