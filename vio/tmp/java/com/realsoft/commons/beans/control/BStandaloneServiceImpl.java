/*
 * Created on 09.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BStandaloneServiceImpl.java,v 1.2 2016/04/15 10:37:07 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.HibernateManager;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.clazz.ClazzUtil;
import com.realsoft.utils.reflection.ObjectMethodCaller;

public class BStandaloneServiceImpl implements IBService, BeanFactoryAware {

	private Logger log = Logger.getLogger(BStandaloneServiceImpl.class);

	private BeanFactory beanFactory = null;

	private HibernateManager hibernateManager = null;

	public BStandaloneServiceImpl() {
		super();
	}

	public IBModelPanel buildModelPanel(IBModelPanel modelPanel)
			throws CommonsBeansException {
		log.debug("modelPanel.name = " + modelPanel.getName());
		if (modelPanel.getName().equals("amountValueRange")) {
			log.debug("modelPanel.name = " + modelPanel.getName());
		}
		Session session = null;
		if (hibernateManager != null) {
			session = hibernateManager.openSession();
		}
		for (IBModelPanel childModelPanel : modelPanel.getModels()) {
			childModelPanel = buildModelPanel(childModelPanel);
			for (IBControl control : childModelPanel.getControls()) {
				processControl(session, childModelPanel, control);
			}
		}
		for (IBControl control : modelPanel.getControls()) {
			processControl(session, modelPanel, control);
		}
		if (hibernateManager != null) {
			hibernateManager.closeSession(session);
		}
		return modelPanel;
	}

	private Map<String, Object> processControl(Session session,
			IBModelPanel modelPanel, IBControl control)
			throws CommonsControlException {
		log.debug("processControl: modelPanel = " + modelPanel.getName()
				+ " control = " + control.getName());
		if (control.getName().equals("initial-amount-min")) {
			log.debug("processControl: control = initial-amount-min");
		}
		try {
			Map<String, Object> controlValues = new HashMap<String, Object>();
			if (control.getDependOn() != null) {
				for (IBControl dependControl : control.getDependOn()) {
					// IBControl dependControl =
					// ControlUtils.lookupControl(modelPanel,
					// dependControlName);
					controlValues.putAll(processControl(session, modelPanel,
							dependControl));
				}
			}
			if (control.getRequest() instanceof BComponentRequestImpl) {
				BComponentRequestImpl request = (BComponentRequestImpl) control
						.getRequest();
				Object component = beanFactory.getBean(request
						.getCompomentName());
				controlValues.putAll(request.getArguments());
				if (request.getComponentMethod().equals("getMaxUrgentCredit")) {
					log.debug("getMaxUrgentCredit");
				}
				Object result = ObjectMethodCaller.invokeMethodByClass(
						component, request.getComponentMethod(), controlValues);
				if (result instanceof IBModel) {
					control.setModel(result);
				} else {
					control.setValue(result);
					// throw new CommonsControlException(
					// RealsoftConstants.ERR_SYSTEM,
					// "commons-control.standalone-service.processing-control.component-result.error",
					// "Component " + component.getClass() + " of method "
					// + request.getComponentMethod()
					// + " return result of "+result.getClass()+" type instead
					// of
					// "+IBModel.class);
				}
			}
			if (control.getRequest() instanceof BSQLRequestImpl
					&& ((BSQLRequestImpl) control.getRequest()).getRequest() != null
					&& control.isDurty()) {
				if (control.getName().equals("depositType")) {
					log.debug("depositType");
				}
				BSQLRequestImpl request = (BSQLRequestImpl) control
						.getRequest();
				controlValues.putAll(request.getArguments());
				List requestResult = hibernateManager.executeQuery(session,
						request.getRequest(), controlValues);
				log
						.debug("processControl: list size = "
								+ requestResult.size());
				control.setDurty(false);
				List<IBItem> items = new ArrayList<IBItem>();
				long itemNum = 1;
				for (Iterator iterator = requestResult.iterator(); iterator
						.hasNext();) {
					Object object = iterator.next();
					if (object != null) {
						ClassMetadata metadata = hibernateManager
								.getSessionFactory().getClassMetadata(
										ClazzUtil.getClass(object.getClass()));
						if (control instanceof IBCheck) {
							items.add(processCheck(metadata, object));
						} else if (control instanceof IBComboBox) {
							items.add(processComboBox(metadata, object));
						} else if (control instanceof IBTable) {
							items.add(processTable((IBTable) control, metadata,
									object, itemNum++));
						} else if (control instanceof IBLabel) {
							items.add(processLabel(metadata, object));
						} else if (control instanceof IBText) {
							items.add(processText(metadata, object));
						} else if (control instanceof IBTextBox) {
							items.add(processText(metadata, object));
						} else if (control instanceof IBPassword) {
							items.add(processText(metadata, object));
						} else if (control instanceof IBHidden) {
							items.add(processHidden(metadata, object));
						} else if (control instanceof IBListBox) {
							items.add(processListBox(items.size(), metadata,
									object));
						} else if (control instanceof IBRadio) {
							items.add(processRadio(metadata, object));
						} else if (control instanceof IBCheckBox) {
							items.add(processCheckBox(metadata, object));
						}
					}
				}
				if (control instanceof IBListControl) {
					((IBListControl) control).setItemList(items);
					((IBListControl) control).setCurrentValue();
				}
			}
			// if (control instanceof IBListControl)
			// ((IBListControl) control).setCurrentValue();
			log.debug("processControl: control.value = " + control.getValue());
			controlValues.put(control.getName(),
					control.getValue() instanceof IBItem ? ((IBItem) control
							.getValue()).getId() : control.getValue());
			log
					.debug("processControl: processing control done controlValues.size = "
							+ controlValues.size());
			return controlValues;
		} catch (Exception e) {
			log.error("processControl: Could not process control", e);
			throw new CommonsControlException(
					RealsoftConstants.ERR_SYSTEM,
					"standalone-service.process-control.error",
					"Could not process control " + control.getName(), e);
		}
	}

	private BCheckBoxItem processCheckBox(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BCheckBoxItem(metadata
				.getIdentifier(object, EntityMode.POJO), object.toString());
	}

	private BRadioItem processRadio(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BRadioItem(metadata.getIdentifier(object, EntityMode.POJO),
				object.toString());
	}

	private BListBoxItem processListBox(int index, ClassMetadata metadata,
			Object object) throws HibernateException {
		return new BListBoxItem(index, metadata.getIdentifier(object,
				EntityMode.POJO), object.toString());
	}

	private BHiddenItem processHidden(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BHiddenItem(object);
	}

	private BLabelItem processLabel(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BLabelItem(object);
	}

	private BTextItem processText(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BTextItem(object);
	}

	private BCheckItem processCheck(ClassMetadata metadata, Object object)
			throws HibernateException {
		return new BCheckItem(metadata.getIdentifier(object, EntityMode.POJO));
	}

	private BComboBoxItem processComboBox(ClassMetadata metadata, Object object)
			throws HibernateException {
		if (object instanceof BComboBoxItem) {
			return (BComboBoxItem) object;
		}
		if (metadata == null && object instanceof IBItem) {
			return new BComboBoxItem((IBItem) object);
		}
		return new BComboBoxItem(metadata
				.getIdentifier(object, EntityMode.POJO), object.toString());
	}

	private BRowItem processTable(IBTable table, ClassMetadata metadata,
			Object object, long itemNum) throws HibernateException,
			CommonsControlException, UtilsException {
		table.setClazz(object.getClass());
		List<String> columNames = table.getColumnNames();
		Map<String, Object> row = new LinkedHashMap<String, Object>();
		for (String columnName : columNames) {
			row.put(columnName, getPropertyValue(metadata, object, columnName));
		}
		if (metadata == null) {
			Object id = ObjectMethodCaller.invokeGetterMethod(object, "id");
			return new BRowItem(itemNum, id, row);
		} else {
			return new BRowItem(itemNum, metadata.getIdentifier(object,
					EntityMode.POJO), row);
		}
	}

	private Object getPropertyValue(ClassMetadata metadata, Object object,
			String propertyName) throws HibernateException,
			CommonsControlException, UtilsException {
		if (propertyName.contains(".")) {
			String columnName = propertyName.substring(0, propertyName
					.indexOf("."));
			if (metadata.getPropertyType(columnName).isEntityType()) {
				Object propertyValue = metadata.getPropertyValue(object,
						columnName, EntityMode.POJO);
				ClassMetadata propertyMetadata = hibernateManager
						.getSessionFactory().getClassMetadata(
								metadata.getPropertyType(columnName)
										.getReturnedClass());
				return getPropertyValue(propertyMetadata, propertyValue,
						propertyName.substring(propertyName.indexOf(".") + 1));
			} else {
				throw new CommonsControlException(
						RealsoftConstants.ERR_SYSTEM,
						"standalone-service.process-control.illegal-property.error",
						columnName + " property is not entity type");
			}
		} else {
			if (metadata == null) {
				return ObjectMethodCaller.invokeGetterMethod(object,
						propertyName);
			} else if (metadata.getIdentifierPropertyName()
					.equals(propertyName)) {
				return metadata.getIdentifier(object, EntityMode.POJO);
			}
			return metadata != null ? metadata.getPropertyValue(object,
					propertyName, EntityMode.POJO) : null;
		}
	}

	public void delete(Class clazz, Object object) throws CommonsBeansException {
		log.debug("delete: deleting " + object);
		Session session;
		session = hibernateManager.openSession();
		log.debug("delete: session opened");
		Object objectDelete = null;
		if (object instanceof IBItem) {
			log.debug("delete: loading object");
			objectDelete = hibernateManager.loadObject(session, clazz,
					(Serializable) ((IBItem) object).getId());
			log.debug("delete: loading object done");
		}
		hibernateManager.deleteObject(session, objectDelete);
		log.debug("delete: deleting object done");
		hibernateManager.closeSession(session);
		log.debug("delete: done");
	}

	public HibernateManager getHibernateManager() {
		return hibernateManager;
	}

	public void setHibernateManager(HibernateManager hibernateManager) {
		this.hibernateManager = hibernateManager;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
