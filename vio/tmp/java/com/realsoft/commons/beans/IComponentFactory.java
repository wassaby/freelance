/*
 * Created on 14.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IComponentFactory.java,v 1.2 2016/04/15 10:37:37 dauren_home Exp $
 */
package com.realsoft.commons.beans;

import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.abonent.IBabonent135;
import com.realsoft.commons.beans.account.IBAccount;
import com.realsoft.commons.beans.anketa.IBAnketa;
import com.realsoft.commons.beans.apps.IBDealer;
import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.connections.IBConnector;
import com.realsoft.commons.beans.control.IBService;
import com.realsoft.commons.beans.control.IBTreeModel;
import com.realsoft.commons.beans.epay.IBEpay;
import com.realsoft.commons.beans.httpclient.IBHttpClient;
import com.realsoft.commons.beans.inventory.IBInventory;
import com.realsoft.commons.beans.isalive.IBIsAlive;
import com.realsoft.commons.beans.keystore.IBKeystore;
import com.realsoft.commons.beans.limit.IBCreditLimit;
import com.realsoft.commons.beans.login.IBLogin;
import com.realsoft.commons.beans.mail.IBMail;
import com.realsoft.commons.beans.outqueue.IBQueueIsOut;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.persist.IBPersist;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.report.abonent.IBReport;
import com.realsoft.commons.beans.request.IBRequest;
import com.realsoft.commons.beans.security.resource.IBSecurity;
import com.realsoft.commons.beans.statistics.IBStatistic;
import com.realsoft.commons.beans.trplan.IBTariff;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;
import com.realsoft.commons.beans.util.IBUtil;
import com.realsoft.commons.beans.xml.IBXMLHolder;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.formatter.FormatterFactory;
import com.realsoft.utils.resources.BundleManager;
import com.realsoft.utils.resources.swt.ImageManager;

/**
 * @author dimad
 */
public interface IComponentFactory {

	String SERVICE_MODE = "serviceMode";

	String BILLING_MODE = "billing";

	String BILLING_135_MODE = "billing-135";

	String ROUTE_MODE = "router";

	String UNKNOW_MODE = "";

	String ABONENT = "abonent";

	String ABONENT_135 = "abonent135";

	IBAbonent getAbonent() throws CommonsBeansException;

	IBabonent135 getAbonent135() throws CommonsBeansException;

	String ACCOUNT = "account";

	IBAccount getAccount() throws CommonsBeansException;

	String ANKETA = "anketa";

	IBAnketa getAnketa() throws CommonsBeansException;

	String AUTOPAYMENT = "autopayment";

	IBAutopayment getAutopayment() throws CommonsBeansException;

	String CONNECTOR = "connetor";

	IBConnector getConnector() throws CommonsBeansException;

	String CREDIT_LIMIT = "creditLimit";

	IBCreditLimit getCreditLimit() throws CommonsBeansException;

	String DEALER = "dealer";

	IBDealer getDealer() throws CommonsBeansException;

	String EPAY = "epay";

	IBEpay getEpay() throws CommonsBeansException;

	String INVENTORY = "inventory";

	IBInventory getInventory() throws CommonsBeansException;

	String IS_ALIVE = "isAlive";

	IBIsAlive getIsAlive() throws CommonsBeansException;

	String KEYSTORE = "keystore";

	IBKeystore getKeystore() throws CommonsBeansException;

	String HTTP_CLIENT = "httpClient";

	IBHttpClient getBHttpClient() throws CommonsBeansException;

	String MODE_MD5 = "MD5";

	String MODE_135 = "135";

	String MODE_MONITORING = "Monitoring";

	String LOGIN = "login";

	IBLogin getLogin(String mode) throws CommonsBeansException;

	String MAIL = "mail";

	IBMail getMail() throws CommonsBeansException;

	String QUEUE_IS_OUT = "queueIsOut";

	IBQueueIsOut getQueueIsOut() throws CommonsBeansException;

	String PAYMENT = "payment";

	IBPayment getPayment() throws CommonsBeansException;

	String REGISTRATION = "registration";

	IBRegistration getRegistration() throws CommonsBeansException;

	String REPORT_ABONENT = "reportAbonent";

	IBReport getReportAbonent() throws CommonsBeansException;

	String REPORT_MBD = "reportMBD";

	com.realsoft.commons.beans.report.mbd.IBReport getReportMBD()
			throws CommonsBeansException;

	String REQUEST = "request";

	IBRequest getRequest() throws CommonsBeansException;

	String PERSIST = "persist";

	IBPersist getPersist() throws CommonsBeansException;

	String STATISTIC = "statistic";

	IBStatistic getStatistic() throws CommonsBeansException;

	String TARIFF = "tariff";

	IBTariff getTariff() throws CommonsBeansException;

	String TREE_MODEL = "treeModel";

	IBTreeModel getTreeModel() throws CommonsBeansException;

	String URL_RESOLVER = "urlResolver";

	IBURLResolver getUrlResolver() throws CommonsBeansException;

	String UTIL = "util";

	IBUtil getUtil() throws CommonsBeansException;

	String RESOURCE_BUNDLE = "resourceBundle";

	BundleManager getBundleManager();

	String FORMATTER_MANAGER = "formatter";

	FormatterFactory getFormatter();

	String CONVERTER_MANAGER = "converter";

	ConverterManager getConverterManager();

	String IMAGE_MANAGER = "imageManager";

	ImageManager getImageManager() throws CommonsBeansException;

	String SERVICE = "service";

	IBService getService() throws CommonsBeansException;

	String RESOURCE_SECURITY = "resourceSecurity";

	IBSecurity getResourceSecurity() throws CommonsBeansException;

	String TASK_SECURITY = "taskSecurity";

	com.realsoft.commons.beans.security.task.IBSecurity getTaskSecurity()
			throws CommonsBeansException;

	String XML_HOLDER = "xmlHolder";

	IBXMLHolder getXMLHolder() throws CommonsBeansException;

	void initialize();

	Object getBean(String beanName);

	boolean containsBean(String beanName);

	void dispose();

	void destroyBean(String beanName, Object bean);

	void destroy();

}