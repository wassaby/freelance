/*
 * Created on 13.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ComponentFactoryImpl.java,v 1.2 2016/04/15 10:37:37 dauren_home Exp $
 */
package com.realsoft.commons.beans;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
public class ComponentFactoryImpl implements IComponentFactory {

	private static Logger log = Logger.getLogger(ComponentFactoryImpl.class);

	private BeanComponentManager manager = null;

	private static String mode = null;

	private static IComponentFactory instance = null;

	public static IComponentFactory getInstance(String beanResourceName,
			String mode) throws CommonsBeansException, URISyntaxException {
		if (instance == null) {
			ComponentFactoryImpl.mode = StringUtils.capitalize(mode);
			instance = new ComponentFactoryImpl(beanResourceName);
		}
		return instance;
	}

	public static IComponentFactory getInstance(URI beanResourceName,
			String mode) throws CommonsBeansException {
		if (instance == null) {
			ComponentFactoryImpl.mode = StringUtils.capitalize(mode);
			instance = new ComponentFactoryImpl(beanResourceName);
		}
		return instance;
	}

	public static IComponentFactory getInstance() throws CommonsBeansException {
		if (instance == null)
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MANAGER_INSTANTIATION_ERROR,
					"Instance had not been already initialized");
		return instance;
	}

	public ComponentFactoryImpl(String beanResourceName)
			throws CommonsBeansException, URISyntaxException {
		this(new URI(beanResourceName));
	}

	public ComponentFactoryImpl(URI beanResourceName)
			throws CommonsBeansException {
		if (instance == null) {
			manager = new BeanComponentManager(beanResourceName);
		} else {
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.MANAGER_INSTANTIATION_ERROR,
					"Instance has been already initialized");
		}
	}

	public static String getBAbonentName() {
		return ABONENT + ComponentFactoryImpl.mode;
	}

	public IBAbonent getAbonent() throws CommonsBeansException {
		log.debug("Abonent Bean Name = " + getBAbonentName());
		return (IBAbonent) manager.getBean(getBAbonentName());
	}

	public static String getBAbonent135Name() {
		return ABONENT_135 + ComponentFactoryImpl.mode;
	}

	public IBabonent135 getAbonent135() throws CommonsBeansException {
		log.debug("Abonent135 Bean Name = " + getBAbonent135Name());
		return (IBabonent135) manager.getBean(getBAbonent135Name());
	}

	public static String getBAccountName() {
		return ACCOUNT + ComponentFactoryImpl.mode;
	}

	public IBAccount getAccount() throws CommonsBeansException {
		return (IBAccount) manager.getBean(getBAccountName());
	}

	public static String getBAnketaName() {
		return ANKETA + ComponentFactoryImpl.mode;
	}

	public IBAnketa getAnketa() throws CommonsBeansException {
		return (IBAnketa) manager.getBean(getBAnketaName());
	}

	public static String getBAutopaymentName() {
		return AUTOPAYMENT + ComponentFactoryImpl.mode;
	}

	public IBAutopayment getAutopayment() throws CommonsBeansException {
		return (IBAutopayment) manager.getBean(getBAutopaymentName());
	}

	public static String getBConnectorName() {
		return CONNECTOR + ComponentFactoryImpl.mode;
	}

	public IBConnector getConnector() throws CommonsBeansException {
		return (IBConnector) manager.getBean(getBConnectorName());
	}

	public static String getBDealerName() {
		return DEALER + ComponentFactoryImpl.mode;
	}

	public IBDealer getDealer() throws CommonsBeansException {
		return (IBDealer) manager.getBean(getBDealerName());
	}

	public static String getBEpayName() {
		return EPAY;
	}

	public IBEpay getEpay() throws CommonsBeansException {
		return (IBEpay) manager.getBean(getBEpayName());
	}

	public static String getBInventoryName() {
		return INVENTORY + ComponentFactoryImpl.mode;
	}

	public IBInventory getInventory() throws CommonsBeansException {
		return (IBInventory) manager.getBean(getBInventoryName());
	}

	public static String getBIsAliveName() {
		return IS_ALIVE + ComponentFactoryImpl.mode;
	}

	public IBIsAlive getIsAlive() throws CommonsBeansException {
		return (IBIsAlive) manager.getBean(getBIsAliveName());
	}

	public static String getBCreditLimitName() {
		return CREDIT_LIMIT + ComponentFactoryImpl.mode;
	}

	public IBCreditLimit getCreditLimit() throws CommonsBeansException {
		return (IBCreditLimit) manager.getBean(getBCreditLimitName());
	}

	public static String getBKeystoreName() {
		return KEYSTORE + ComponentFactoryImpl.mode;
	}

	public IBKeystore getKeystore() throws CommonsBeansException {
		return (IBKeystore) manager.getBean(getBKeystoreName());
	}

	public static String getBHttpClientName() {
		return HTTP_CLIENT;
	}

	public IBHttpClient getBHttpClient() throws CommonsBeansException {
		return (IBHttpClient) manager.getBean(getBHttpClientName());
	}

	public static String getBLoginName(String mode) {
		return LOGIN + ComponentFactoryImpl.mode + mode;
	}

	public IBLogin getLogin(String mode) throws CommonsBeansException {
		return (IBLogin) manager.getBean(getBLoginName(mode));
	}

	public static String getBMailName() {
		return MAIL + ComponentFactoryImpl.mode;
	}

	public IBMail getMail() throws CommonsBeansException {
		return (IBMail) manager.getBean(getBMailName());
	}

	public static String getBReportMBDName() {
		return REPORT_MBD + ComponentFactoryImpl.mode;
	}

	public com.realsoft.commons.beans.report.mbd.IBReport getReportMBD()
			throws CommonsBeansException {
		return (com.realsoft.commons.beans.report.mbd.IBReport) manager
				.getBean(getBReportMBDName());
	}

	public static String getBReportAbonentName() {
		return REPORT_ABONENT + ComponentFactoryImpl.mode;
	}

	public IBReport getReportAbonent() throws CommonsBeansException {
		return (IBReport) manager.getBean(getBReportAbonentName());
	}

	public static String getBQueueIsOutName() {
		return QUEUE_IS_OUT + ComponentFactoryImpl.mode;
	}

	public IBQueueIsOut getQueueIsOut() throws CommonsBeansException {
		return (IBQueueIsOut) manager.getBean(getBQueueIsOutName());
	}

	public static String getBPaymentName() {
		return PAYMENT + ComponentFactoryImpl.mode;
	}

	public IBPayment getPayment() throws CommonsBeansException {
		return (IBPayment) manager.getBean(getBPaymentName());
	}

	public static String getBPersistName() {
		return PERSIST;
	}

	public IBPersist getPersist() throws CommonsBeansException {
		return (IBPersist) manager.getBean(getBPersistName());
	}

	public static String getBRegistrationName() {
		return REGISTRATION + ComponentFactoryImpl.mode;
	}

	public IBRegistration getRegistration() throws CommonsBeansException {
		return (IBRegistration) manager.getBean(getBRegistrationName());
	}

	public static String getBRequestName() {
		return REQUEST + ComponentFactoryImpl.mode;
	}

	public IBRequest getRequest() throws CommonsBeansException {
		return (IBRequest) manager.getBean(getBRequestName());
	}

	public static String getBStatisticName() {
		return STATISTIC + ComponentFactoryImpl.mode;
	}

	public IBStatistic getStatistic() throws CommonsBeansException {
		return (IBStatistic) manager.getBean(getBStatisticName());
	}

	public static String getBTrafficName() {
		return TARIFF + ComponentFactoryImpl.mode;
	}

	public IBTariff getTariff() throws CommonsBeansException {
		return (IBTariff) manager.getBean(getBTrafficName());
	}

	public static String getBUrlResolverName() {
		return URL_RESOLVER;
	}

	public IBURLResolver getUrlResolver() throws CommonsBeansException {
		return (IBURLResolver) manager.getBean(getBUrlResolverName());
	}

	public static String getBUtilName() {
		return UTIL + ComponentFactoryImpl.mode;
	}

	public IBUtil getUtil() throws CommonsBeansException {
		return (IBUtil) manager.getBean(getBUtilName());
	}

	/* Common components */
	public String getBundleManagerName() {
		return RESOURCE_BUNDLE;
	}

	public BundleManager getBundleManager() {
		return (BundleManager) manager.getBean(getBundleManagerName());
	}

	public String getFormatterName() {
		return FORMATTER_MANAGER;
	}

	public FormatterFactory getFormatter() {
		return (FormatterFactory) manager.getBean(getFormatterName());
	}

	public String getConverterName() {
		return CONVERTER_MANAGER;
	}

	public ConverterManager getConverterManager() {
		return (ConverterManager) manager.getBean(getConverterName());
	}

	public String getImageManagerName() {
		return IMAGE_MANAGER;
	}

	public ImageManager getImageManager() throws CommonsBeansException {
		ImageManager imageManager = (ImageManager) manager
				.getBean(getImageManagerName());
		return imageManager;
	}

	public static String getBServiceName() {
		return SERVICE;
	}

	public IBService getService() throws CommonsBeansException {
		return (IBService) manager.getBean(getBServiceName());
	}

	public static String getResourceSecurityName() {
		return RESOURCE_SECURITY + mode;
	}

	public IBSecurity getResourceSecurity() throws CommonsBeansException {
		IBSecurity security = (IBSecurity) manager
				.getBean(getResourceSecurityName());
		if (security == null)
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.NO_SUCH_COMPONENT_ERROR,
					"No such security mode " + ComponentFactoryImpl.mode);
		return security;
	}

	public static String getTaskSecurityName() {
		return TASK_SECURITY + mode;
	}

	public com.realsoft.commons.beans.security.task.IBSecurity getTaskSecurity()
			throws CommonsBeansException {
		com.realsoft.commons.beans.security.task.IBSecurity security = (com.realsoft.commons.beans.security.task.IBSecurity) manager
				.getBean(getTaskSecurityName());
		if (security == null)
			throw new CommonsBeansException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.NO_SUCH_COMPONENT_ERROR,
					"No such security mode " + ComponentFactoryImpl.mode);
		return security;
	}

	public static String getTreeModelName() {
		return TREE_MODEL;
	}

	public IBTreeModel getTreeModel() throws CommonsBeansException {
		return (IBTreeModel) manager.getBean(getTreeModelName());
	}

	public static String getXMLHolderName() {
		return XML_HOLDER;
	}

	public IBXMLHolder getXMLHolder() throws CommonsBeansException {
		return (IBXMLHolder) manager.getBean(getXMLHolderName());
	}

	public Object getBean(String beanName) {
		return manager.getBean(beanName);
	}

	public boolean containsBean(String beanName) {
		return manager.containsBean(beanName);
	}

	public void initialize() {
		manager.initialize();
	}

	public void dispose() {
		manager.dispose();
	}

	public void destroyBean(String beanName, Object bean) {
		manager.destroyBean(beanName, bean);
	}

	public void destroy() {
		manager.destroy();
	}

}