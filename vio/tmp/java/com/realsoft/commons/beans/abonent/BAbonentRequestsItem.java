package com.realsoft.commons.beans.abonent;

import java.io.Serializable;
import java.util.Date;

/**
 * Информация по заявке абонента
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentRequestsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long rId;

	private String stateName;

	private Date insertDate;

	private Date closeDate;

	private String cName;

	private String serviceDesc;

	private String rmNote;

	private String smName;

	private String rNote;

	/**
	 * 
	 * @param id
	 *            идентификатор заявки
	 * @param stateName
	 * @param insertDate
	 * @param closeDate
	 *            дата закрытия заявки
	 * @param name
	 * @param serviceDesc
	 * @param rmNote
	 *            описание проблемы
	 * @param smName
	 * @param note
	 */
	public BAbonentRequestsItem(long id, String stateName, Date insertDate,
			Date closeDate, String name, String serviceDesc, String rmNote,
			String smName, String note) {
		super();
		// TODO Auto-generated constructor stub
		rId = id;
		this.stateName = stateName;
		this.insertDate = insertDate;
		this.closeDate = closeDate;
		cName = name;
		this.serviceDesc = serviceDesc;
		this.rmNote = rmNote;
		this.smName = smName;
		rNote = note;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCName() {
		return cName;
	}

	public void setCName(String name) {
		cName = name;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public long getRId() {
		return rId;
	}

	public void setRId(long id) {
		rId = id;
	}

	public String getRmNote() {
		return rmNote;
	}

	public void setRmNote(String rmNote) {
		this.rmNote = rmNote;
	}

	public String getRNote() {
		return rNote;
	}

	public void setRNote(String note) {
		rNote = note;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getSmName() {
		return smName;
	}

	public void setSmName(String smName) {
		this.smName = smName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
