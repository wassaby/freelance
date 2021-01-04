/*
 * Created on 05.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAutopaymentImpl.java,v 1.2 2016/04/15 10:37:50 dauren_home Exp $
 */
package com.realsoft.commons.beans.autopayment;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.payment.BPaymentException;
import com.realsoft.commons.beans.payment.BPaymentResponce;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.registration.BRegistrationInfo13;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.random.RandomGenerator;

/**
 * @author dimad
 * 
 */
public class BAutopaymentImpl implements IBAutopayment, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BAutopaymentImpl.class);

	private BeanFactory beanFactory = null;

	private String terminal = "AUTOPAYMENT_TRM";

	private ConverterManager converter = null;

	public ConverterManager getConverter() {
		return converter;
	}

	public void setConverter(ConverterManager converter) {
		this.converter = converter;
	}

	public BAutopaymentImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.components.autopayment.ICAutopayment#registration(java.util.List)
	 */
	public List<BRegistrationInfo13> registration(List<String> phones)
			throws BAutopaymentException, BAbonentException {
		log.info("registering for autopayment ...");

		List<BRegistrationInfo13> list = new ArrayList<BRegistrationInfo13>();
		for (Iterator<String> iter = phones.iterator(); iter.hasNext();) {
			BRegistrationInfo13 registrationResponce = new BRegistrationInfo13();
			String phone = iter.next();
			registrationResponce.setName(phone);
			try {
				IBRegistration registration = (IBRegistration) beanFactory
						.getBean(ComponentFactoryImpl.getBRegistrationName(),
								IBRegistration.class);
				registrationResponce = registration.register13(phone);
				log.debug("Registering phone " + phone);
			} catch (BRegistrationException e) {
				log.error("Could not create register during autopayment", e);
				registrationResponce.setNote(e.getMessageKey());
			}
			list.add(registrationResponce);
		}
		log.info("registering for autopayment done");
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.components.autopayment.ICAutopayment#autopayment(java.lang.String,
	 *      java.lang.String, java.util.List)
	 */
	public List<BAutopaymentItem> autopayment(String netSource,
			String subDivision, String payType,
			List<BRegistrationInfo13> registrationInfo)
			throws BAutopaymentException, BRegistrationException,
			BAbonentException {
		log.info("autopayment ...");
		Calendar currDate = Calendar.getInstance();
		List<BAutopaymentItem> autopaymentList = new ArrayList<BAutopaymentItem>();

		for (Iterator<BRegistrationInfo13> iterator = registrationInfo
				.iterator(); iterator.hasNext();) {
			log.debug("Creating new CAutopaymentItem");
			BRegistrationInfo13 info = iterator.next();
			BAutopaymentItem autopaymentItem = new BAutopaymentItem(info
					.getAbonentId(), info.getBalance(), "IPayment passed", info
					.getName());
			if (info.getBalance() <= 0) {
				autopaymentItem
						.setNote(CommonsBeansConstants.AUTOPAYMENT_AMOUNT_ERROR);
			} else {
				try {
					String rrn = String.valueOf(RandomGenerator.getLong());
					IBPayment payment = (IBPayment) beanFactory.getBean(
							ComponentFactoryImpl.getBPaymentName(),
							IBPayment.class);
					BPaymentResponce paymentResponce = payment
							.payment(info.getAbonentId(), info.getBalance(),
									currDate, CommonsBeansConstants.CURRENCY,
									rrn, netSource, subDivision, payType, info
											.getName(), terminal, rrn);
					log.debug("Balance = " + paymentResponce.getBalance());
					autopaymentItem.setBalance(paymentResponce.getBalance());
					autopaymentItem.setNote(paymentResponce.getNote());
				} catch (BPaymentException e) {
					log.error("Could not create new autopayment", e);
					autopaymentItem.setNote(e.getMessageText());
				} catch (NoSuchAlgorithmException e) {
					log.error("Could not create new autopayment", e);
					autopaymentItem.setNote(e.getMessage());
				}
			}
			autopaymentList.add(autopaymentItem);
		}
		log.info("autopayment done");
		return autopaymentList;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public List<BRegistrationInfo13> registrationGuid(List<String> guids)
			throws BAutopaymentException, BAbonentException {
		log.info("registering for autopayment ...");

		List<BRegistrationInfo13> list = new ArrayList<BRegistrationInfo13>();
		for (Iterator<String> iter = guids.iterator(); iter.hasNext();) {

			BRegistrationInfo13 registrationResponce = new BRegistrationInfo13();
			String guid = iter.next();
			String[] gds = guid.split("-");
			Long abonentId = (Long) converter.getObject(gds[1], long.class);
			Long townId = (Long) converter.getObject(gds[0], long.class);
			try {
				IBAbonent abonent = (IBAbonent) beanFactory
						.getBean(ComponentFactoryImpl.getBAbonentName(),
								IBAbonent.class);
				registrationResponce.setBalance(abonent
						.getAbonentBalance13(abonentId));
				registrationResponce.setGuid(guid);
				registrationResponce.setAbonentId(abonentId);
				registrationResponce.setTownId(townId);
				registrationResponce.setResponce("OK");
				log.debug("Registering guid " + guid);
			} catch (BAbonentException e) {
				log.error("Could not create register during autopayment", e);
				registrationResponce.setNote(e.getMessageKey());
			}
			list.add(registrationResponce);
		}
		log.info("registering for autopayment done");
		return list;
	}

}
