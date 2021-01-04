/*
 * Created on 07.02.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BDbPersistImpl.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.classic.Session;
import org.hibernate.metadata.ClassMetadata;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.HibernateManager;

public class BDbPersistImpl implements IBPersist {

	private static Logger log = Logger.getLogger(BDbPersistImpl.class);

	private HibernateManager hibernateManager = null;

	public BDbPersistImpl() {
	}

	public void createSchema(String schemaName) throws BPersistException {
	}

	public void createTable(String schemaName, String tableName)
			throws BPersistException {
	}

	public void deleteRow(String schemaName, String clazz, Long rowId)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				hibernateManager.deleteObject(session, Class.forName(clazz),
						rowId);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (ClassNotFoundException e) {
			log.error("Could not delete row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.persist.deleteRow.error",
					"Could not delete row", e);
		} catch (CommonsBeansException e) {
			log.error("Could not delete row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public IBRow getFirstRow(String schemaName, String clazz)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				String query = "select s from " + clazz
						+ " s fetch first row only";
				Object object = hibernateManager.executeQuery(session, query);
				ClassMetadata metadata = hibernateManager.getSessionFactory()
						.getClassMetadata(clazz);
				Serializable id = metadata.getIdentifier(object,
						EntityMode.POJO);
				return new BRow((Long) id, object);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (CommonsBeansException e) {
			log.error("Could not get first row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public IBRow getNextRow(String schemaName, String clazz, IBRow currentRow)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				String query = "select s from " + clazz + " s where s.id>"
						+ currentRow.getId() + " fetch first row only";
				Object object = hibernateManager.executeQuery(session, query);
				ClassMetadata metadata = hibernateManager.getSessionFactory()
						.getClassMetadata(clazz);
				Serializable id = metadata.getIdentifier(object,
						EntityMode.POJO);
				return new BRow((Long) id, object);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (CommonsBeansException e) {
			log.error("Could not get next row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public IBRow getRow(String schemaName, String clazz, Serializable rowId)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				Class objectClass = Class.forName(clazz);
				Object object = hibernateManager.loadObject(session,
						objectClass, rowId);
				ClassMetadata metadata = hibernateManager.getSessionFactory()
						.getClassMetadata(objectClass);
				Serializable id = metadata.getIdentifier(object,
						EntityMode.POJO);
				return new BRow((Long) id, object);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (ClassNotFoundException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.persist.getRow.error", "Could not get row",
					e);
		} catch (CommonsBeansException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public IBRow getRow(String schemaName, String clazz, Serializable rowId,
			Object defaultValue) throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				if (hibernateManager.isObjectExists(session, Class
						.forName(clazz), rowId)) {
					return getRow(schemaName, clazz, rowId);
				}
				return new BRow(rowId, defaultValue);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (ClassNotFoundException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.persist.getRow.error", "Could not get row",
					e);
		} catch (CommonsBeansException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public Collection<IBRow> getRowList(String schemaName, String clazz)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();

				ClassMetadata metadata = hibernateManager.getSessionFactory()
						.getClassMetadata(clazz);

				List list = hibernateManager.loadObjectList(session, Class
						.forName(clazz));
				Collection<IBRow> collection = new LinkedList<IBRow>();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Object object = iterator.next();
					Serializable id = metadata.getIdentifier(object,
							EntityMode.POJO);
					collection.add(new BRow(id, object));
				}
				return collection;
			} finally {
				if (session != null)
					session.close();
			}
		} catch (ClassNotFoundException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.persist.getRowList.error",
					"Could not get row list", e);
		} catch (CommonsBeansException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public void putRow(String schemaName, String clazz, IBRow value)
			throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				hibernateManager.updateObject(session, value.getObject());
			} finally {
				if (session != null)
					session.close();
			}
		} catch (CommonsBeansException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public Serializable getRowId(Object row) throws BPersistException {
		try {
			Session session = null;
			try {
				session = hibernateManager.openSession();
				return hibernateManager.saveObject(session, row);
			} finally {
				if (session != null)
					session.close();
			}
		} catch (CommonsBeansException e) {
			log.error("Could not get row", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM, e
					.getMessageKey(), e.getMessageText(), e);
		}
	}

	public HibernateManager getHibernateManager() {
		return hibernateManager;
	}

	public void setHibernateManager(HibernateManager hibernateManager) {
		this.hibernateManager = hibernateManager;
	}

}
