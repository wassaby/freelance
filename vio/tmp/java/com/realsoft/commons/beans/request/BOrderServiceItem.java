/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BOrderServiceItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;
import java.util.Calendar;

public class BOrderServiceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long actionId;

	private String actionName;

	private String packetName;

	private String serviceName;

	private Calendar openDate;

	private Calendar closeDate;

	public BOrderServiceItem(long actionId, String actionName,
			String packetName, String serviceName, Calendar openDate,
			Calendar closeDate) {
		super();
		this.actionId = actionId;
		this.actionName = actionName;
		this.packetName = packetName;
		this.serviceName = serviceName;
		this.openDate = openDate;
		this.closeDate = closeDate;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Calendar getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate) {
		this.closeDate = closeDate;
	}

	public Calendar getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Calendar openDate) {
		this.openDate = openDate;
	}

	public String getPacketName() {
		return packetName;
	}

	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}