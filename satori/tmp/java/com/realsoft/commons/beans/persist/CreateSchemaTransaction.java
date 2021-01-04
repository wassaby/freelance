/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CreateSchemaTransaction.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.util.Date;

import org.apache.log4j.Logger;
import org.prevayler.Transaction;

public class CreateSchemaTransaction implements Transaction {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CreateSchemaTransaction.class);

	private String schemaName = null;

	private String idPostfix = null;

	public CreateSchemaTransaction(String schemaName, String idPostfix) {
		this.schemaName = schemaName;
		this.idPostfix = idPostfix;
	}

	public void executeOn(Object prevalentSystem, Date executionTime) {
		BPrevalentDatabase database = (BPrevalentDatabase) prevalentSystem;
		try {
			database.createSchema(schemaName);
			database.getSchema(schemaName).createTable(
					IBPersist.GENERATOR_TABLE);
			BPrevalentTable generatorTable = database.getSchema(schemaName)
					.getTable(IBPersist.GENERATOR_TABLE);
			if (generatorTable.getRow((long) 1) == null) {
				generatorTable.putRow(new BRow((long) 1, (long) 1));
			}
		} catch (BPersistException e) {
			log.error("Could not create schema " + schemaName, e);
		}
	}

}
