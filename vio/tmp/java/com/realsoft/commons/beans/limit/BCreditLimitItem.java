package com.realsoft.commons.beans.limit;

import java.io.Serializable;
import java.util.Date;

/**
 * Информация о минимальном авансе аккаунта.
 * 
 * @author temirbulatov
 * 
 */
public class BCreditLimitItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long contractId;

	private String contract;

	private long providerId;

	private Date openDate;

	private Date closeDate;

	private long limit;

	/**
	 * 
	 * @param contractId
	 *            Идентификатор контракта
	 * @param contract
	 *            Контракт
	 * @param openDate
	 *            Дата открытия
	 * @param closeDate
	 *            Дата закрытия
	 * @param limit
	 *            Минимальный аванс
	 * @param providerId
	 *            Идентификатор провайдера
	 */
	public BCreditLimitItem(long contractId, String contract, Date openDate,
			Date closeDate, long limit, long providerId) {
		super();
		this.contractId = contractId;
		this.contract = contract;
		this.providerId = providerId;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.limit = limit;

	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long id) {
		this.contractId = id;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

}
