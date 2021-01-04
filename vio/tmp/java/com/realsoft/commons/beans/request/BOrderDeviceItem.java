/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BOrderDeviceItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;
import java.util.Calendar;

public class BOrderDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long orderDeviceId;

	private long actionId;

	private String actionName;

	private long abonentId;

	private long deviceGroupId;

	private String deviceGroupName;

	private long townId;

	private String oldDevice;

	private String newDevice;

	private long oldConnectTypeId;

	private long newConnectTypeId;

	private String newConnectTypeName;

	private long oldAddressId;

	private long newAddressId;

	private String oldConditions;

	private String newConditions;

	private Calendar toDate;

	private Calendar openDate;

	private Calendar closeDate;

	private Calendar beginSysdate;

	private Calendar endSysdate;

	private long priorityId;

	private long tariffTimingId;

	private long tariffTypeId;

	private double tariff;

	private double smeta;

	private double smetaRetax;

	private double amount;

	private long userId;

	private String userName;

	private String isDeleted;

	private String note;

	private long abonentGroupId;

	private long ndsTypeId;

	private long discountTypeId;

	private long servicePacketTypeId;

	private double discount;

	private double nds;

	private long billTypeId;

	private long newAbonentId;

	private long serviceCountGroupId;

	private String oldDepartment;

	private String newDepartment;

	private String keyWord;

	public BOrderDeviceItem() {
		super();
	}

	/**
	 * 
	 * @param orderDeviceId
	 * @param actionId
	 * @param abonentId
	 * @param deviceGroupId
	 * @param townId
	 * @param oldDevice
	 * @param newDevice
	 * @param oldConnectTypeId
	 * @param newConnectTypeId
	 * @param oldAddressId
	 * @param newAddressId
	 * @param oldConditions
	 * @param newConditions
	 * @param toDate
	 * @param openDate
	 * @param closeDate
	 * @param beginSysdate
	 * @param endSysdate
	 * @param priorityId
	 * @param tariffTimingId
	 * @param tariffTypeId
	 * @param tariff
	 * @param smeta
	 * @param smetaRetax
	 * @param amount
	 * @param userId
	 * @param isDeleted
	 * @param note
	 * @param abonentGroupId
	 * @param ndsTypeId
	 * @param discountTypeId
	 * @param servicePacketTypeId
	 * @param discount
	 * @param nds
	 * @param billTypeId
	 * @param newAbonentId
	 * @param serviceCountGroupId
	 * @param oldDepartment
	 * @param newDepartment
	 */
	public BOrderDeviceItem(long orderDeviceId, long actionId, long abonentId,
			long deviceGroupId, long townId, String oldDevice,
			String newDevice, long oldConnectTypeId, long newConnectTypeId,
			long oldAddressId, long newAddressId, String oldConditions,
			String newConditions, Calendar toDate, Calendar openDate,
			Calendar closeDate, Calendar beginSysdate, Calendar endSysdate,
			long priorityId, long tariffTimingId, long tariffTypeId,
			double tariff, double smeta, double smetaRetax, double amount,
			long userId, String isDeleted, String note, long abonentGroupId,
			long ndsTypeId, long discountTypeId, long servicePacketTypeId,
			double discount, double nds, long billTypeId, long newAbonentId,
			long serviceCountGroupId, String oldDepartment,
			String newDepartment, String actionName) {
		super();
		this.orderDeviceId = orderDeviceId;
		this.actionId = actionId;
		this.abonentId = abonentId;
		this.deviceGroupId = deviceGroupId;
		this.townId = townId;
		this.oldDevice = oldDevice;
		this.newDevice = newDevice;
		this.oldConnectTypeId = oldConnectTypeId;
		this.newConnectTypeId = newConnectTypeId;
		this.oldAddressId = oldAddressId;
		this.newAddressId = newAddressId;
		this.oldConditions = oldConditions;
		this.newConditions = newConditions;
		this.toDate = toDate;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.beginSysdate = beginSysdate;
		this.endSysdate = endSysdate;
		this.priorityId = priorityId;
		this.tariffTimingId = tariffTimingId;
		this.tariffTypeId = tariffTypeId;
		this.tariff = tariff;
		this.smeta = smeta;
		this.smetaRetax = smetaRetax;
		this.amount = amount;
		this.userId = userId;
		this.isDeleted = isDeleted;
		this.note = note;
		this.abonentGroupId = abonentGroupId;
		this.ndsTypeId = ndsTypeId;
		this.discountTypeId = discountTypeId;
		this.servicePacketTypeId = servicePacketTypeId;
		this.discount = discount;
		this.nds = nds;
		this.billTypeId = billTypeId;
		this.newAbonentId = newAbonentId;
		this.serviceCountGroupId = serviceCountGroupId;
		this.oldDepartment = oldDepartment;
		this.newDepartment = newDepartment;
		this.actionName = actionName;
	}

	public long getAbonentGroupId() {
		return abonentGroupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAbonentGroupId(long abonentGroupId) {
		this.abonentGroupId = abonentGroupId;
	}

	public long getAbonentId() {
		return abonentId;
	}

	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Calendar getBeginSysdate() {
		return beginSysdate;
	}

	public void setBeginSysdate(Calendar beginSysdate) {
		this.beginSysdate = beginSysdate;
	}

	public long getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(long billTypeId) {
		this.billTypeId = billTypeId;
	}

	public Calendar getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate) {
		this.closeDate = closeDate;
	}

	public long getDeviceGroupId() {
		return deviceGroupId;
	}

	public void setDeviceGroupId(long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public long getDiscountTypeId() {
		return discountTypeId;
	}

	public void setDiscountTypeId(long discountTypeId) {
		this.discountTypeId = discountTypeId;
	}

	public Calendar getEndSysdate() {
		return endSysdate;
	}

	public void setEndSysdate(Calendar endSysdate) {
		this.endSysdate = endSysdate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public double getNds() {
		return nds;
	}

	public void setNds(double nds) {
		this.nds = nds;
	}

	public long getNdsTypeId() {
		return ndsTypeId;
	}

	public void setNdsTypeId(long ndsTypeId) {
		this.ndsTypeId = ndsTypeId;
	}

	public long getNewAbonentId() {
		return newAbonentId;
	}

	public void setNewAbonentId(long newAbonentId) {
		this.newAbonentId = newAbonentId;
	}

	public long getNewAddressId() {
		return newAddressId;
	}

	public void setNewAddressId(long newAddressId) {
		this.newAddressId = newAddressId;
	}

	public String getNewConditions() {
		return newConditions;
	}

	public void setNewConditions(String newConditions) {
		this.newConditions = newConditions;
	}

	public long getNewConnectTypeId() {
		return newConnectTypeId;
	}

	public void setNewConnectTypeId(long newConnectTypeId) {
		this.newConnectTypeId = newConnectTypeId;
	}

	public String getNewDepartment() {
		return newDepartment;
	}

	public void setNewDepartment(String newDepartment) {
		this.newDepartment = newDepartment;
	}

	public String getNewDevice() {
		return newDevice;
	}

	public void setNewDevice(String newDevice) {
		this.newDevice = newDevice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getOldAddressId() {
		return oldAddressId;
	}

	public void setOldAddressId(long oldAddressId) {
		this.oldAddressId = oldAddressId;
	}

	public String getOldConditions() {
		return oldConditions;
	}

	public void setOldConditions(String oldConditions) {
		this.oldConditions = oldConditions;
	}

	public long getOldConnectTypeId() {
		return oldConnectTypeId;
	}

	public void setOldConnectTypeId(long oldConnectTypeId) {
		this.oldConnectTypeId = oldConnectTypeId;
	}

	public String getOldDepartment() {
		return oldDepartment;
	}

	public void setOldDepartment(String oldDepartment) {
		this.oldDepartment = oldDepartment;
	}

	public String getOldDevice() {
		return oldDevice;
	}

	public void setOldDevice(String oldDevice) {
		this.oldDevice = oldDevice;
	}

	public Calendar getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Calendar openDate) {
		this.openDate = openDate;
	}

	public long getOrderDeviceId() {
		return orderDeviceId;
	}

	public void setOrderDeviceId(long orderDeviceId) {
		this.orderDeviceId = orderDeviceId;
	}

	public long getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(long priorityId) {
		this.priorityId = priorityId;
	}

	public long getServiceCountGroupId() {
		return serviceCountGroupId;
	}

	public void setServiceCountGroupId(long serviceCountGroupId) {
		this.serviceCountGroupId = serviceCountGroupId;
	}

	public long getServicePacketTypeId() {
		return servicePacketTypeId;
	}

	public void setServicePacketTypeId(long servicePacketTypeId) {
		this.servicePacketTypeId = servicePacketTypeId;
	}

	public double getSmeta() {
		return smeta;
	}

	public void setSmeta(double smeta) {
		this.smeta = smeta;
	}

	public double getSmetaRetax() {
		return smetaRetax;
	}

	public void setSmetaRetax(double smetaRetax) {
		this.smetaRetax = smetaRetax;
	}

	public double getTariff() {
		return tariff;
	}

	public void setTariff(double tariff) {
		this.tariff = tariff;
	}

	public long getTariffTimingId() {
		return tariffTimingId;
	}

	public void setTariffTimingId(long tariffTimingId) {
		this.tariffTimingId = tariffTimingId;
	}

	public long getTariffTypeId() {
		return tariffTypeId;
	}

	public void setTariffTypeId(long tariffTypeId) {
		this.tariffTypeId = tariffTypeId;
	}

	public Calendar getToDate() {
		return toDate;
	}

	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("orderDeviceId = ").append(orderDeviceId).append(
				"; actionId = ").append(actionId).append("; abonentId = ")
				.append(abonentId).append("; deviceGroupId = ").append(
						deviceGroupId).append(": townId = ").append(townId)
				.append("; oldDevice = ").append(oldDevice).append(
						"; newDevice = ").append(newDevice).append(
						"; oldConnectTypeId = ").append(oldConnectTypeId)
				.append("; newConnectTypeId = ").append(newConnectTypeId)
				.append("; oldAddressId = ").append(oldAddressId).append(
						"; newAddressId = ").append(newAddressId).append(
						"; oldConditions = ").append(oldConditions).append(
						"; newConditions = ").append(newConditions).append(
						"; toDate = ").append(toDate).append("; openDate = ")
				.append(openDate).append("; closeDate = ").append(closeDate)
				.append("; beginSysdate = ").append(beginSysdate).append(
						"; endSysdate = ").append(endSysdate).append(
						"; priorityId = ").append(priorityId).append(
						"; tariffTimingId = ").append(tariffTimingId).append(
						"; tariffTypeId = ").append(tariffTypeId).append(
						"; tariff = ").append(tariff).append("; smeta = ")
				.append(smeta).append("; smetaRetax = ").append(smetaRetax)
				.append("; amount = ").append(amount).append("; userId = ")
				.append(userId).append("; isDeleted = ").append(isDeleted)
				.append("; note = ").append(note).append("; abonentGroupId = ")
				.append(abonentGroupId).append("; ndsTypeId = ").append(
						ndsTypeId).append("; discountTypeId = ").append(
						discountTypeId).append("; servicePacketTypeId = ")
				.append(servicePacketTypeId).append("; discount = ").append(
						discount).append("; nds = ").append(nds).append(
						"; billTypeId = ").append(billTypeId).append(
						"; newAbonentId = ").append(newAbonentId).append(
						"; serviceCountGroupId = ").append(serviceCountGroupId)
				.append("; oldDepartment = ").append(oldDepartment).append(
						"; newDepartment = ").append(newDepartment).append(
						"; keyWord = ").append(keyWord);
		return result.toString();
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDeviceGroupName() {
		return deviceGroupName;
	}

	public void setDeviceGroupName(String deviceGroupName) {
		this.deviceGroupName = deviceGroupName;
	}

	public String getNewConnectTypeName() {
		return newConnectTypeName;
	}

	public void setNewConnectTypeName(String newConnectTypeName) {
		this.newConnectTypeName = newConnectTypeName;
	}

}
