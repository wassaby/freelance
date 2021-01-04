package com.realsoft.commons.beans.abonent;

import java.io.Serializable;

/**
 * Содержит некоторые данные об абоненте. Используется в больших списках.
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long adressId;

	private String name;

	/**
	 * 
	 * @param id
	 *            лицевой счет абонента
	 * @param adressid
	 *            идентификатор адреса
	 * @param name
	 *            имя абонента
	 */
	public BAbonentItem(long id, long adressid, String name) {
		this.id = id;
		this.adressId = adressid;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public long getAdressID() {
		return this.adressId;
	}

	public void setAdressId(long value) {
		this.adressId = value;
	}

	public String getName() {
		return this.name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
