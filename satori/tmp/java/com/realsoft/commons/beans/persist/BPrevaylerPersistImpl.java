/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPrevaylerPersistImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BPrevaylerPersistImpl implements IBPersist {

	private static Logger log = Logger.getLogger(BPrevaylerPersistImpl.class);

	private String persistDirectory = null;

	private String persistFileName = null;

	private Prevayler prevayler = null;

	private boolean initialized = false;

	private String regionId = null;

	public BPrevaylerPersistImpl() {
	}

	public void deleteRow(String schemaName, String tableName, Long rowId)
			throws BPersistException {
		DeleteRowTransaction transaction = new DeleteRowTransaction(schemaName,
				tableName, rowId);
		prevayler.execute(transaction);
	}

	public IBRow getRow(String schemaName, String tableName, Serializable rowId)
			throws BPersistException {
		BPrevalentDatabase database = (BPrevalentDatabase) prevayler
				.prevalentSystem();
		return database.getSchema(schemaName).getTable(tableName).getRow(rowId);
	}

	public IBRow getRow(String schemaName, String tableName,
			Serializable rowId, Object defaultValue) throws BPersistException {
		IBRow row = getRow(schemaName, tableName, rowId);
		if (row == null) {
			row = new BRow((long) 1, defaultValue);
			putRow(schemaName, tableName, row);
		}
		return row;
	}

	public Collection<IBRow> getRowList(String schemaName, String tableName)
			throws BPersistException {
		BPrevalentDatabase database = (BPrevalentDatabase) prevayler
				.prevalentSystem();
		return database.getSchema(schemaName).getTable(tableName).getAllRows();
	}

	public IBRow getFirstRow(String schemaName, String tableName)
			throws BPersistException {
		Iterator<IBRow> iterator = getRowList(schemaName, tableName).iterator();
		if (iterator.hasNext())
			return iterator.next();
		return null;
	}

	public IBRow getNextRow(String schemaName, String tableName,
			IBRow currentRow) throws BPersistException {
		Collection<IBRow> rowList = getRowList(schemaName, tableName);
		for (Iterator<IBRow> iterator = rowList.iterator(); iterator.hasNext();) {
			if (iterator.next().equals(currentRow)) {
				if (iterator.hasNext())
					return iterator.next();
				break;
			}
		}
		return null;
	}

	public void putRow(String schemaName, String tableName, IBRow value)
			throws BPersistException {
		try {
			PutRowTransaction transaction = new PutRowTransaction(schemaName,
					tableName, value, "");
			prevayler.execute(transaction);
		} catch (Exception e) {
			log.error("Could not execute transaction", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.store.executing-put-transaction.error",
					"Could not execute transaction");
		}
	}

	public void createSchema(String schemaName) throws BPersistException {
		CreateSchemaTransaction transaction = new CreateSchemaTransaction(
				schemaName, "");
		prevayler.execute(transaction);
	}

	public void createTable(String schemaName, String tableName)
			throws BPersistException {
		CreateTableTransaction transaction = new CreateTableTransaction(
				schemaName, tableName);
		prevayler.execute(transaction);
	}

	public Serializable getRowId(Object row) throws BPersistException {
		return null;
	}

	private static String PERSIST_DIRECTORY = "persist";

	public void initialize() throws BPersistException {
		try {
			prevayler = PrevaylerFactory.createPrevayler(
					new BPrevalentDatabase(persistFileName), persistDirectory
							+ File.separator + PERSIST_DIRECTORY);
			initialized = true;
		} catch (Exception e) {
			log.error("Could not initialize prevayler", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.store.initializing.error",
					"Could not initialize store", e);
		}
	}

	public void dispose() throws BPersistException {
		log.debug("disposing store ...");
		if (initialized) {
			try {
				prevayler.takeSnapshot();
				prevayler.close();
			} catch (Exception e) {
				log.error("Could not close prevayler", e);
				throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
						"commons-beans.store.closing.error",
						"Could not close prevayler", e);
			}
		}
		log.debug("disposing store done");
	}

	public String getPersistFileName() {
		return persistFileName;
	}

	public void setPersistFileName(String storeName) {
		this.persistFileName = storeName;
	}

	public String getPersistDirectory() {
		return persistDirectory;
	}

	public void setPersistDirectory(String persistDirectory) {
		this.persistDirectory = persistDirectory;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

}
