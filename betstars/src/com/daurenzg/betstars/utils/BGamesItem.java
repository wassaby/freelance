package com.daurenzg.betstars.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BGamesItem implements Serializable {
	private static final long serialVersionUID = -3661690371618478961L;
	private long id;
	private String name;
	private long championatshipId;
	private long sportId;
	private Date startAt;
	private List<BBetItem> betList = new ArrayList<BBetItem>();

	public long getChampionatshipId() {
		return championatshipId;
	}
	public void setChampionatshipId(long championatshipId) {
		this.championatshipId = championatshipId;
	}
	public long getSportId() {
		return sportId;
	}
	public void setSportId(long sportId) {
		this.sportId = sportId;
	}
	public List<BBetItem> getBetList() {
		return betList;
	}
	public void setBetList(List<BBetItem> betList) {
		this.betList = betList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	public BGamesItem(long id, String name, List<BBetItem> betList, long championatshipId, long sportId, Date startAt) {
		super();
		this.id = id;
		this.name = name;
		this.betList = betList;
		this.championatshipId = championatshipId;
		this.sportId = sportId;
		this.startAt = startAt;
	}

}
