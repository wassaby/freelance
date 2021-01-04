/*
 * Created on 20.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRequestOrderStageItem.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.commons.beans.request;

import java.io.Serializable;
import java.util.Date;

public class BRequestOrderStageItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dtname;

	private String workPlaceName;

	private String ddnameTownName;

	private String ddnameNode;

	private String ddnameSrvName;

	private Long status;

	private Date datein;

	private Date there;

	private Date backagain;

	private String snote;

	private String rnote;

	private String sName;

	private String rName;

	private String keyWord;

	public BRequestOrderStageItem() {
		super();
	}

	/**
	 * 
	 * @param dtname
	 * @param ddnameTownName
	 * @param ddnameNode
	 * @param ddnameSrvName
	 * @param datein
	 * @param there
	 * @param backagain
	 * @param snote
	 * @param rnote
	 * @param sName
	 * @param rName
	 */
	public BRequestOrderStageItem(String dtname, String ddnameTownName,
			String ddnameNode, String ddnameSrvName, Date datein, Date there,
			Date backagain, String snote, String rnote, String sName,
			String rName, String workPlaceName) {
		super();

		this.dtname = dtname;
		this.ddnameTownName = ddnameTownName;
		this.ddnameNode = ddnameNode;
		this.ddnameSrvName = ddnameSrvName;
		this.datein = datein;
		this.there = there;
		this.backagain = backagain;
		this.snote = snote;
		this.rnote = rnote;
		this.sName = sName;
		this.rName = rName;
		this.workPlaceName = workPlaceName;
	}

	public Date getBackagain() {
		return backagain;
	}

	public void setBackagain(Date backagain) {
		this.backagain = backagain;
	}

	public Date getDatein() {
		return datein;
	}

	public void setDatein(Date datein) {
		this.datein = datein;
	}

	public String getDdnameNode() {
		return ddnameNode;
	}

	public void setDdnameNode(String ddnameNode) {
		this.ddnameNode = ddnameNode;
	}

	public String getDdnameSrvName() {
		return ddnameSrvName;
	}

	public void setDdnameSrvName(String ddnameSrvName) {
		this.ddnameSrvName = ddnameSrvName;
	}

	public String getDdnameTownName() {
		return ddnameTownName;
	}

	public void setDdnameTownName(String ddnameTownName) {
		this.ddnameTownName = ddnameTownName;
	}

	public String getDtname() {
		return dtname;
	}

	public void setDtname(String dtname) {
		this.dtname = dtname;
	}

	public String getRName() {
		return rName;
	}

	public void setRName(String name) {
		rName = name;
	}

	public String getRnote() {
		return rnote;
	}

	public void setRnote(String rnote) {
		this.rnote = rnote;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String name) {
		sName = name;
	}

	public String getSnote() {
		return snote;
	}

	public void setSnote(String snote) {
		this.snote = snote;
	}

	public Date getThere() {
		return there;
	}

	public void setThere(Date there) {
		this.there = there;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}
