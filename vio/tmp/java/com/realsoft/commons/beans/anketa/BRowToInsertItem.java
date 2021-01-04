/*
 * Created on 13.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BRowToInsertItem.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.io.Serializable;

public class BRowToInsertItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long questionId;

	private long answereId;

	private long accountUsrId;

	private String note;

	public BRowToInsertItem(long questionId, long answId, long AccID,
			String note) {
		super();
		this.questionId = questionId;
		this.answereId = answId;
		this.accountUsrId = AccID;
		this.note = note;
	}

	public long getAccountUsrId() {
		return accountUsrId;
	}

	public void setAccountUsrId(long accountUsrId) {
		this.accountUsrId = accountUsrId;
	}

	public long getAnswereId() {
		return answereId;
	}

	public void setAnswereId(long answereId) {
		this.answereId = answereId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String toString() {
		return new StringBuffer().append("QuestionID = ").append(questionId)
				.append(" AnswereID = ").append(answereId).append(
						" Account USR id = ").append(accountUsrId).append(
						" Note = " + note).toString();
	}

}
