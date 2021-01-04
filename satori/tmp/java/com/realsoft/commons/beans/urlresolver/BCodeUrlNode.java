/*
 * Created on 08.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BCodeUrlNode.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.commons.beans.urlresolver;

import java.io.Serializable;

public class BCodeUrlNode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String townName;

	private long townId;

	private String uri;

	private String host;

//	private String code;
	
	private String min;
	
	private String max;

//	public BCodeUrlNode(String townName, long townId, String uri, String host,
//			String code) {
//		super();
//		this.townName = townName;
//		this.townId = townId;
//		this.uri = uri;
//		this.host = host;
//		this.code = code;
//	}


	public BCodeUrlNode(String townName, long townId, String uri, String host, String min, String max) {
		super();
		this.townName = townName;
		this.townId = townId;
		this.uri = uri;
		this.host = host;
		this.min = min;
		this.max = max;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

}
