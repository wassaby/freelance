/*
 * Created on 11.10.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBPersist.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;
import java.util.Collection;

public interface IBPersist {

	String GENERATOR_TABLE = "com.realsoft.kiosk.dao.EpayInteractionGenerator"; // "hilo-generator";

	void deleteRow(String schemaName, String tableName, Long rowId)
			throws BPersistException;

	IBRow getRow(String schemaName, String tableName, Serializable rowId)
			throws BPersistException;

	IBRow getRow(String schemaName, String tableName, Serializable rowId,
			Object defaultValue) throws BPersistException;

	Collection<IBRow> getRowList(String schemaName, String tableName)
			throws BPersistException;

	IBRow getFirstRow(String schemaName, String tableName)
			throws BPersistException;

	IBRow getNextRow(String schemaName, String tableName, IBRow currentRow)
			throws BPersistException;

	void putRow(String schemaName, String tableName, IBRow value)
			throws BPersistException;

	void createSchema(String schemaName) throws BPersistException;

	void createTable(String schemaName, String tableName)
			throws BPersistException;

	Serializable getRowId(Object row) throws BPersistException;

}