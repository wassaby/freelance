package com.daurenzg.betstars.utils;

import java.io.Serializable;

public class BAccountInfo implements Serializable {

	private static final long serialVersionUID = 4270997263059571191L;
	private long id;
	private long countryId;
	private long cityId;
	private String email;
	private String name;
	private String photo;
	private long coins;
	private boolean isReady;
	private boolean isBlocked;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public long getCoins() {
		return coins;
	}
	public void setCoins(long coins) {
		this.coins = coins;
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
	public BAccountInfo(long id, long countryId, long cityId, String email,
			String name, String photo, long coins, boolean isReady,
			boolean isBlocked) {
		super();
		this.id = id;
		this.countryId = countryId;
		this.cityId = cityId;
		this.email = email;
		this.name = name;
		this.photo = photo;
		this.coins = coins;
		this.isReady = isReady;
		this.isBlocked = isBlocked;
	}
	
}
