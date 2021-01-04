package com.realsoft.commons.beans.statistics;

import java.io.Serializable;

public class BStatisticPeriodItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long reportDateId;

	private String name;

	public BStatisticPeriodItem(long reportDateId, String name) {
		super();
		this.reportDateId = reportDateId;
		this.name = name;
	}

	public long getReportDateId() {
		return reportDateId;
	}

	public void setReportDateId(long id) {
		this.reportDateId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
