/*
 * Created on 23.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: HibernateManager.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.commons.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class HibernateManager {

	private Logger log = Logger.getLogger(HibernateManager.class);

	private SessionFactory sessionFactory = null;

	public HibernateManager() {
		super();
	}

	public Session openSession() throws CommonsBeansException {
		try {
			return sessionFactory.openSession();
		} catch (HibernateException e) {
			log.error("Could not openSession", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.session-open.error",
					"Could not open hibernate session");
		}
	}

	public void closeSession(Session session) throws CommonsBeansException {
		try {
			if (session != null) {
				session.close();
			}
		} catch (HibernateException e) {
			log.error("Could not closeSession", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.session-close.error",
					"Could not close hibernate session");
		}
	}

	public Object loadObject(Session session, Class clazz, Serializable id)
			throws CommonsBeansException {
		try {
			return session.load(clazz, id, LockMode.READ);
		} catch (HibernateException e) {
			log.error("Could not loadObject", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.load-object.error",
					"Could not load object of " + clazz + "[" + id + "]");
		}
	}

	public Object loadObject(Session session, Class clazz, Object id)
			throws CommonsBeansException {
		return loadObject(session, clazz, (Serializable) id);
	}

	public List loadObjectList(Session session, Class clazz)
			throws CommonsBeansException {
		try {
			return session.createCriteria(clazz).list();
		} catch (HibernateException e) {
			log.error("Could not loadObjectList", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.load-object-list.error",
					"Could not load object list of " + clazz);
		}
	}

	public Serializable saveObject(Session session, Object object)
			throws CommonsBeansException {
		Transaction tx = null;
		try {
			log.info("Object = " + object);
			tx = session.beginTransaction();
			Serializable id = session.save(object);
			tx.commit();
			return id;
		} catch (HibernateException e) {
			log.error("Could not saveObject", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					throw new CommonsBeansException(
							CommonsBeansConstants.ERR_SYSTEM,
							"spring-beans.hibernate-factory.save-object.error",
							"Could not save object " + object);
				}
			}
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.save-object.error",
					"Could not save DAOObject " + object
							+ ", it's already exists");
		}
	}

	public void updateObject(Session session, Object object)
			throws CommonsBeansException {
		Transaction tx = null;
		try {
			log.debug("Object = " + object);
			tx = session.beginTransaction();
			session.update(object);
			tx.commit();
		} catch (Exception e) {
			log.error("Could not updateObject", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					throw new CommonsBeansException(
							CommonsBeansConstants.ERR_SYSTEM,
							"spring-beans.hibernate-factory.update-object.error",
							"Could not update object " + object);
				}
			}
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.update-object.error",
					"Could not update object " + object);
		}
	}

	public void updateObject(Session session, Object object, Serializable id)
			throws CommonsBeansException {
		Transaction tx = null;
		try {
			log.debug("Object = " + object);
			tx = session.beginTransaction();
			session.update(object, id);
			tx.commit();
		} catch (Exception e) {
			log.error("Could not updateObject", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					throw new CommonsBeansException(
							CommonsBeansConstants.ERR_SYSTEM,
							"spring-beans.hibernate-factory.update-object.error",
							"Could not update object " + object);
				}
			}
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.update-object.error",
					"Could not update object " + object);
		}
	}

	public void deleteObject(Session session, Object object)
			throws CommonsBeansException {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
		} catch (Exception e) {
			log.error("Could not deleteObject", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					throw new CommonsBeansException(
							CommonsBeansConstants.ERR_SYSTEM,
							"spring-beans.hibernate-factory.delete-object.error",
							"Could not delete object " + object);
				}
			}
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.delete-object.error",
					"Could not delete object " + object);
		}
	}

	public void deleteObject(Session session, Class clazz, Object id)
			throws CommonsBeansException {
		Object object = loadObject(session, clazz, id);
		deleteObject(session, object);
	}

	public void deleteObjects(Session session, Class clazz)
			throws CommonsBeansException {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete("from " + clazz.getName());
			tx.commit();
		} catch (Exception e) {
			log.error("Could not deleteObjects", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					throw new CommonsBeansException(
							CommonsBeansConstants.ERR_SYSTEM,
							"spring-beans.hibernate-factory.delete-object.error",
							"Could not delete objects");
				}
			}
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.delete-object.error",
					"Could not delete objects");
		}
	}

	public List executeQuery(Session session, String query)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		try {
			List list = session.createQuery(query).setCacheable(false)
					.setFetchSize(3).list();
			log.info("Executing query done");
			return list;
		} catch (Exception e) {
			log.error("Could not executeQuery", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.execute-query.error",
					"Could not execute query " + query);
		}
	}

	public List executeQuery(Session session, String query,
			Map<String, Object> criteria) throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		for (String criteriaName : criteria.keySet()) {
			log.debug("criteriaName = " + criteriaName + "; criteriaValue = "
					+ criteria.get(criteriaName));
		}
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false)
					.setFetchSize(3);
			for (String criteriaName : criteria.keySet()) {
				if (query.contains(":" + criteriaName)) {
					hqlQuery.setParameter(criteriaName, criteria
							.get(criteriaName));
				}
			}
			List list = hqlQuery.list();
			log.info("Executing query done");
			return list;
		} catch (Exception e) {
			log.error("Could not executeQuery", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.execute-query.error",
					"Could not execute query " + query);
		}
	}

	public List executeQuery(Session session, String query,
			String criteriaName, Object criteriaValue)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false)
					.setFetchSize(3);
			hqlQuery.setParameter(criteriaName, criteriaValue);
			List list = hqlQuery.list();
			log.info("Executing query done");
			return list;
		} catch (Exception e) {
			log.error("Could not executeQuery", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.execute-query.error",
					"Could not execute query " + query);
		}
	}

	public List executeQuery(Session session, String query,
			String[] criteriaNames, Object[] criteriaValues)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		if (criteriaNames.length != criteriaValues.length) {
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.execute-query.error",
					"Criterial names and criteria values array must be the same length");
		}
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false)
					.setFetchSize(3);
			for (int i = 0; i < criteriaNames.length; i++) {
				hqlQuery.setParameter(criteriaNames[i], criteriaValues[i]);
			}
			List list = hqlQuery.list();
			log.info("Executing query done");
			return list;
		} catch (Exception e) {
			log.error("Could not executeQuery", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.execute-query.error",
					"Could not execute query " + query);
		}
	}

	public Integer getRowCount(Session session, String query)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false);
			Integer count = (Integer) hqlQuery.uniqueResult();
			log.info("Executing query done");
			return count;
		} catch (Exception e) {
			log.error("Could not getRowCount", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.row-count.error",
					"Could not get row count " + query);
		}
	}

	public Integer getRowCount(Session session, String query,
			String[] criteriaNames, Object[] criteriaValues)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		if (criteriaNames.length != criteriaValues.length) {
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.row-count.error",
					"Criterial names and criteria values array must be the same length");
		}
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false);
			for (int i = 0; i < criteriaNames.length; i++) {
				hqlQuery.setParameter(criteriaNames[i], criteriaValues[i]);
			}
			Integer count = (Integer) hqlQuery.uniqueResult();
			log.info("Executing query done");
			return count;
		} catch (Exception e) {
			log.error("Could not getRowCount", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.row-count.error",
					"Could not execute query of " + query);
		}
	}

	public Integer getRowCount(Session session, String query, Map criteria)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false);
			for (Iterator iter = criteria.keySet().iterator(); iter.hasNext();) {
				String criteriaName = (String) iter.next();
				hqlQuery.setEntity(criteriaName, criteria.get(criteriaName));
			}
			Integer count = (Integer) hqlQuery.uniqueResult();
			log.info("Executing query done");
			return count;
		} catch (Exception e) {
			log.error("Could not getRowCount", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.row-count.error",
					"Could not execute query of " + query);
		}
	}

	public Integer getRowCount(Session session, String query,
			String criteriaName, Object criteriaValue)
			throws CommonsBeansException {
		log.info("Executing query = " + query + " ...");
		try {
			Query hqlQuery = session.createQuery(query).setCacheable(false);
			hqlQuery.setEntity(criteriaName, criteriaValue);
			Integer count = (Integer) hqlQuery.uniqueResult();
			log.info("Executing query done");
			return count;
		} catch (Exception e) {
			log.error("Could not getRowCount", e);
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					"spring-beans.hibernate-factory.row-count.error",
					"Could not execute query of " + query);
		}
	}

	public boolean isObjectExists(Session session, Class clazz, Serializable id)
			throws CommonsBeansException {
		return loadObject(session, clazz, id) != null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
