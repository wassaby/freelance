/*
 * Created on 25.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BEpayImpl.java,v 1.2 2016/04/15 10:37:34 dauren_home Exp $
 */
package com.realsoft.commons.beans.epay;

import java.io.InputStream;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.keystore.BKeystoreException;
import com.realsoft.commons.beans.keystore.IBKeystore;
import com.realsoft.commons.beans.payment.BPaymentException;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.persist.BPersistException;
import com.realsoft.commons.beans.persist.IBPersist;
import com.realsoft.commons.beans.persist.IBRow;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.util.IBUtil;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.formatter.FormatterFactory;
import com.realsoft.utils.reflection.ObjectMethodCaller;
import com.realsoft.utils.resources.BundleManager;

public class BEpayImpl implements IBEpay, BeanFactoryAware {

	private static Logger log = Logger.getLogger(BEpayImpl.class);

	private static String REVERSE_STR = "reverse";

	private static String COMPLETE_STR = "complete";

	private boolean initialized = false;

	private String templateName = null;

	private String confirmTemplateName = null;

	private String merchantName = null;

	private String merchantId = null;

	private String certificate = null;

	private String althoritm = null;

	private String keyAlias = null;

	private String keyPassword = null;

	private String certAlias = null;

	private String failureTemplate = null;

	private String successTemplate = null;

	private String templateDocument = null;

	private String confirmTemplateDocument = null;

	private String successTemplateDocument = null;

	private String failureTemplateDocument = null;

	private IBKeystore keystore = null;

	private IBPersist persist = null;

	private BundleManager bundleManager = null;

	private FormatterFactory formatter = null;

	private ConverterManager converter = null;

	private IBPayment payment = null;

	private IBUtil util = null;

	private IBAbonent abonent = null;

	private BeanFactory beanFactory = null;

	public BEpayImpl() {
	}

	public String buildEpayRequest(long abonentId, long orderId,
			String terminal, double amount, String currency, String phone)
			throws BEpayException, BKeystoreException, BPersistException {
		if (initialized) {
			log.debug("templateDocument = " + templateDocument);
			String template = setElementValue(templateDocument, "@order_id@",
					orderId);
			template = setElementValue(template, "@amount@", amount);
			template = setElementValue(template, "@abonent_id@", abonentId);
			template = setElementValue(template, "@amount@", amount);
			template = setElementValue(template, "@terminal@", terminal);
			template = setElementValue(template, "@phone@", phone);
			template = setElementValue(template, "@currency@", currency);
			log.debug("template = " + template);

			byte[] tempMerchant = calculateBlockSign(template,
					"/document/merchant", IBKeystore.SHA1withRSA, keyAlias,
					keyPassword, true);

			template = setElementValue(template, "@merchant_sign@",
					tempMerchant);

			log.debug("epay = " + template);
			IBRow row = persist.getRow("db",
					"com.realsoft.kiosk.dao.EpayInteraction", orderId);
			Object epayInteraction = row.getObject();
			try {
				ObjectMethodCaller.invokeSetterMethod(epayInteraction,
						"request", template);
			} catch (UtilsException e) {
				log.error("Could not build epay request", e);
				throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
						"commons-beans.epay.buildEpayRequest.error",
						"Could not build epay request", e);
			}
			persist.putRow("db", "com.realsoft.kiosk.dao.EpayInteraction", row);
			return new String(Base64.encodeBase64(template.getBytes()));
		}
		throw new BEpayException(
				CommonsBeansConstants.ERR_SYSTEM,
				"commons-beans.epay.building-epay-request.component-not-initialized",
				"Component epay is not initialized");
	}

	public Object[] buildEpayRequest(long abonentId, String terminal,
			double amount, String currency, String phone)
			throws BEpayException, BKeystoreException, BPersistException {
		Long orderId = getEpayOrder();
		// log.debug("buildEpayRequest: orderId = " + orderId);
		String epayRequest = buildEpayRequest(abonentId, orderId, terminal,
				amount, currency, phone);
		return new Object[] { orderId, epayRequest };
	}

	public String buildEpayConfirmRequest(String commandType, String reference,
			String approvalCode, Long orderId, Double amount,
			String currencyCode) throws BKeystoreException, BPersistException,
			BEpayException {
		if (initialized) {
			String confirmTemplate = setElementValue(confirmTemplateDocument,
					"@command_type@", commandType);
			confirmTemplate = setElementValue(confirmTemplate, "@reference@",
					reference);
			confirmTemplate = setElementValue(confirmTemplate,
					"@approval_code@", approvalCode);
			confirmTemplate = setElementValue(confirmTemplate, "@order_id@",
					orderId);
			confirmTemplate = setElementValue(confirmTemplate, "@amount@",
					amount);
			confirmTemplate = setElementValue(confirmTemplate, "@currency@",
					currencyCode);
			String reason = (commandType.equals(REVERSE_STR)) ? "reverse"
					: (commandType.equals(COMPLETE_STR) ? "complete"
							: "Wrong parameter commandType");
			confirmTemplate = setElementValue(confirmTemplate, "@reason@",
					reason);
			log.debug("templateDocument = " + confirmTemplate);

			Node block;
			try {
				byte[] signedBytes = calculateBlockSign(confirmTemplate,
						"/document/merchant", IBKeystore.SHA1withRSA, keyAlias,
						keyPassword, true);

				log.debug("signedBytes = " + new String(signedBytes));
				// keystore.calculateSign(byteBlock,
				// IBKeystore.SHA1withRSA, keyAlias, keyPassword
				// .toCharArray());

				confirmTemplate = setElementValue(confirmTemplate,
						"@merchant_sign@", signedBytes);
				log.debug("confirm document = \n" + confirmTemplate);
				log.debug("confirm document length = "
						+ confirmTemplate.length());

				IBRow row = persist.getRow("db",
						"com.realsoft.kiosk.dao.EpayInteraction", orderId);
				Object epayInteraction = row.getObject();
				ObjectMethodCaller.invokeSetterMethod(epayInteraction,
						"confirmRequest", confirmTemplate);
				persist.putRow("db", "com.realsoft.kiosk.dao.EpayInteraction",
						row);
				return confirmTemplate;
			} catch (UtilsException e) {
				log.error("Could not build epay request", e);
				throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
						"commons-beans.epay.buildEpayRequest.error",
						"Could not build epay request", e);
			}
		}
		throw new BEpayException(
				CommonsBeansConstants.ERR_SYSTEM,
				"commons-beans.epay.building-epay-request.component-not-initialized",
				"Component epay is not initialized");
	}

	public void acceptEpaySuccessResponce(Long orderId, String epayResponce,
			String subDivision) throws BEpayException, BPersistException,
			BPaymentException, BRegistrationException, BAbonentException {
		try {
			// Document document = DocumentHelper.parseText(epayResponce);

			IBRow row = persist.getRow("db",
					"com.realsoft.kiosk.dao.EpayInteraction", orderId);
			Object epayInteraction = row.getObject();
			ObjectMethodCaller.invokeSetterMethod(epayInteraction, "response",
					epayResponce);
			persist.putRow("db", "com.realsoft.kiosk.dao.EpayInteraction", row);
			// } catch (DocumentException e) {
			// log.error("Could not accept document", e);
			// throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
			// "commons-beans.epay.accepting-epay-success-responce.error",
			// "Could not accept epay success responce", e);
		} catch (UtilsException e) {
			log.error("Could not accept document", e);
			throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.accepting-epay-success-responce.error",
					"Could not accept epay success responce", e);
		}
	}

	public void acceptEpayConfirmResponce(String epayResponce)
			throws BEpayException, BPersistException, BPaymentException,
			BRegistrationException, BAbonentException, BKeystoreException {
		try {
			epayResponce = epayResponce.trim();
			Document document = DocumentHelper.parseText(epayResponce);
			String originalText = document.selectSingleNode("/document/bank")
					.asXML();
			String signedText = document
					.selectSingleNode("/document/bank_sign").getText();

			Long orderId = (Long) converter.getObject(document
					.selectSingleNode(
							"/document/bank/merchant/payment/@orderid")
					.getText(), Long.class);
			String type = document.selectSingleNode(
					"/document/bank/merchant/command/@type").getText();
			String reference = document.selectSingleNode(
					"/document/bank/merchant/payment/@reference").getText();
			String approvalCode = document.selectSingleNode(
					"/document/bank/merchant/payment/@approval_code").getText();
			Double amount = (Double) converter.getObject(
					document.selectSingleNode(
							"/document/bank/merchant/payment/@amount")
							.getText(), Double.class);
			String currencyCode = document.selectSingleNode(
					"/document/bank/merchant/payment/@currency_code").getText();
			IBRow row = persist.getRow("db",
					"com.realsoft.kiosk.dao.EpayInteraction", orderId);
			Object epayInteraction = row.getObject();

			ObjectMethodCaller.invokeSetterMethod(epayInteraction, "reference",
					reference);
			ObjectMethodCaller.invokeSetterMethod(epayInteraction,
					"approvalCode", approvalCode);
			ObjectMethodCaller.invokeSetterMethod(epayInteraction,
					"confirmResponse", epayResponce);

			boolean isValid = validateEpay(epayResponce, "/document/bank",
					"/document/bank_sign");
			// boolean isValid = keystore.validateSignedBytes(originalText
			// .getBytes(), signedText.getBytes(), (althoritm
			// .equals("RSA") ? IBKeystore.SHA1withRSA
			// : IBKeystore.SHA1withDSA), certAlias);
			if (isValid) {
				ObjectMethodCaller.invokeSetterMethod(epayInteraction,
						"status", "T");
				persist.putRow("db", "com.realsoft.kiosk.dao.EpayInteraction",
						row);
			} else {
				persist.putRow("db", "com.realsoft.kiosk.dao.EpayInteraction",
						row);
				log.error("Confirm Responce document is not valid");
				throw new BEpayException(
						CommonsBeansConstants.ERR_SYSTEM,
						"commons-beans.epay.accepting-epay-success-responce.error",
						"Confirm Responce document is not valid");
			}

		} catch (DocumentException e) {
			log.error("Could not accept document", e);
			throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.accepting-epay-confirm-responce.error",
					"Could not accept epay confirm responce", e);
		} catch (UtilsException e) {
			log.error("Could not accept document", e);
			throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.accepting-epay-confirm-responce.error",
					"Could not accept epay confirm responce", e);
		}
	}

	public Long getEpayOrder() throws BPersistException {
		Long orderId;
		try {
			Object object = Class.forName(
					"com.realsoft.kiosk.dao.EpayInteraction").newInstance();
			ObjectMethodCaller.invokeSetterMethod(object, "systemDate",
					Calendar.getInstance().getTime());
			ObjectMethodCaller.invokeSetterMethod(object, "status", "F");
			orderId = (Long) persist.getRowId(object);
			log.debug("orderId = " + orderId);
			return orderId;
		} catch (Exception e) {
			log.error("Could not get epay order", e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.getEpayOrder.error",
					"Could not get epay order", e);
		}
	}

	public String buildEpayFailureResponce(long orderId, String errorType,
			String code, String errorMessage, long sessionId)
			throws BEpayException {
		log.debug("failureTemplate = " + failureTemplate);
		try {
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@order_id@", orderId);
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@type@", errorType);
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@time@", Calendar.getInstance());
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@code@", code);
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@error-message@", errorMessage);
			failureTemplateDocument = setElementValue(failureTemplateDocument,
					"@session_id@", sessionId);
			log.debug("templateDocument = " + failureTemplateDocument);
			return new String(Base64.encodeBase64(failureTemplateDocument
					.getBytes()));
		} catch (Exception e) {
			log.error("Could not build epay failure request", e);
		}
		throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
				"commons-beans.epay.building-epay-failure-request.error",
				"Could not build epay failure request");
	}

	public String buildEpaySuccessResponce(long abonentId, String customerName,
			String certId, String merchantName, long orderId, String terminal,
			double amount, String currency, String phone, long merchantId,
			String merchantSignAlthoritm, String customerSignType,
			long reference, long approvalCode, String responseCode,
			String bankSignType) throws BEpayException {
		try {
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@customer_name@", customerName);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@cert_id@", certId);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@merchant_name@", merchantName);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@order_id@", orderId);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@amount@", amount);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@currency@", currency);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@abonent_id@", abonentId);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@merchant_id@", merchantId);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@terminal@", terminal);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@phone@", phone);

			byte[] tempMerchant = calculateBlockSign(successTemplateDocument,
					"/document/bank/customer/merchant", (merchantSignAlthoritm
							.equals("RSA")) ? IBKeystore.SHA1withRSA
							: IBKeystore.SHA1withDSA, keyAlias, keyPassword,
					true);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@merchant_sign@", tempMerchant);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@althoritm@", merchantSignAlthoritm);

			successTemplateDocument = setElementValue(successTemplateDocument,
					"@customer_sign_type@", customerSignType);
			if (customerSignType.equals("SSL")) {
				successTemplateDocument = setElementValue(
						successTemplateDocument, "@customer_sign@", String
								.valueOf(certId));
			} else if (customerSignType.equals("RSA")) {
				byte[] tempCustomer = calculateBlockSign(
						successTemplateDocument,
						"/document/bank/customer",
						(customerSignType.equals("RSA")) ? IBKeystore.SHA1withRSA
								: IBKeystore.SHA1withDSA, keyAlias,
						keyPassword, true);
				successTemplateDocument = setElementValue(
						successTemplateDocument, "@customer_sign@",
						tempCustomer);
			} else if (customerSignType.equals("none")) {
			}

			successTemplateDocument = setElementValue(successTemplateDocument,
					"@timestamp@", Calendar.getInstance());
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@reference@", reference);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@approval_code@", approvalCode);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@response_code@", responseCode);

			successTemplateDocument = setElementValue(successTemplateDocument,
					"@bank_sign_type@", bankSignType);

			byte[] tempBank = calculateBlockSign(successTemplateDocument,
					"/document/bank",
					(bankSignType.equals("RSA") ? IBKeystore.SHA1withRSA
							: IBKeystore.SHA1withDSA), keyAlias, keyPassword,
					true);
			successTemplateDocument = setElementValue(successTemplateDocument,
					"@bank_sign@", tempBank);

			log.debug("templateDocument = " + successTemplateDocument);
			return new String(Base64.encodeBase64(successTemplateDocument
					.getBytes()));
		} catch (Exception e) {
			log.error("Could not build epay success responce", e);
			throw new BEpayException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.building-epay-success-responce.error",
					"Could not build epay success responce");
		}
	}

	public boolean validateEpay(String epay, String originalNodeName,
			String signedNodeName) throws BEpayException, BKeystoreException {
		try {
			String epayDocument = epay;
			log.debug("epayDocument = " + epayDocument + ";\nsignedNodeName = "
					+ signedNodeName + "\noriginalNodeName = "
					+ originalNodeName);
			Document confXml = DocumentHelper.parseText(epayDocument);
			Element rootElement = confXml.getRootElement();
			Element signedElement = (Element) rootElement
					.selectSingleNode(signedNodeName);
			String signedText = signedElement.getText();
			log.debug("signedText    = " + signedText);

			Element originalElement = (Element) rootElement
					.selectSingleNode(originalNodeName);
			String originalText = originalElement.asXML();
			log.debug("originalText    = " + originalText);

			byte[] signedBytes = Base64.decodeBase64(signedText.getBytes());
			ArrayUtils.reverse(signedBytes);
			return keystore.validateSignedBytes(originalText.getBytes(),
					signedBytes,
					(althoritm.equals("RSA") ? IBKeystore.SHA1withRSA
							: IBKeystore.SHA1withDSA), certAlias);
		} catch (DocumentException e) {
			log.debug("validation of " + signedNodeName + " failed", e);
			throw new BEpayException(
					CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.validating-epay-request.wrong-xml-document",
					"Request is wrong XML document");
		}
	}

	public byte[] calculateBlockSign(String document, String nodeXPath,
			String signatureType, String keyAlias, String keyPassword,
			boolean inverse) throws BKeystoreException {
		Node block;
		try {
			block = DocumentHelper.parseText(document).getRootElement()
					.selectSingleNode(nodeXPath);
			log.debug("block = " + block.asXML());
			return calculateBlockSign(block.asXML().getBytes(), signatureType,
					keyAlias, keyPassword, inverse);
		} catch (DocumentException e) {
			log.error("Could not calculate block sign", e);
			throw new BKeystoreException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.epay.calculateBlockSign.error",
					"Could not calculate block sign", e);
		}
	}

	public byte[] calculateBlockSign(byte[] block, String signatureType,
			String keyAlias, String keyPassword, boolean inverse)
			throws BKeystoreException {
		byte[] signedBytes = keystore.calculateSign(block, signatureType,
				keyAlias, keyPassword.toCharArray());
		if (inverse) {
			ArrayUtils.reverse(signedBytes);
		}
		return Base64.encodeBase64(signedBytes);
	}

	// public boolean checkDocumentIntegrity(String templateText, String
	// documentText){
	// Document template = DocumentHelper.parseText(templateText);
	// Document document = DocumentHelper.parseText(documentText);
	//		
	// Element templateElement = template.getRootElement();
	// }
	//
	public void initialize() {
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					templateName);
			templateDocument = IOUtils.toString(in);
			templateDocument = setElementValue(templateDocument, "@cert_id@",
					certificate);
			templateDocument = setElementValue(templateDocument,
					"@merchant_name@", merchantName);
			templateDocument = setElementValue(templateDocument,
					"@merchant_id@", merchantId);
			templateDocument = setElementValue(templateDocument, "@althoritm@",
					althoritm);

			InputStream confirmIn = getClass().getClassLoader()
					.getResourceAsStream(confirmTemplateName);
			confirmTemplateDocument = IOUtils.toString(confirmIn);
			confirmTemplateDocument = setElementValue(confirmTemplateDocument,
					"@merchant_id@", merchantId);
			confirmTemplateDocument = setElementValue(confirmTemplateDocument,
					"@althoritm@", "RSA");
			confirmTemplateDocument = setElementValue(confirmTemplateDocument,
					"@cert_id@", certificate);

			InputStream successIn = getClass().getClassLoader()
					.getResourceAsStream(successTemplate);
			successTemplateDocument = IOUtils.toString(successIn);

			InputStream failureIn = getClass().getClassLoader()
					.getResourceAsStream(failureTemplate);
			failureTemplateDocument = IOUtils.toString(failureIn);

			initialized = true;
		} catch (Exception e) {
			log.error("Could not initialize component BEpayImpl", e);
		}
	}

	private String setElementValue(String document, String name, String value) {
		// log.debug("document = " + document + "; name = " + name + "; value =
		// " + value);
		return document.replaceAll(name, value);
	}

	private String setElementValue(String document, String name, long value) {
		return setElementValue(document, name, String.valueOf(value));
	}

	private String setElementValue(String document, String name, double value) {
		return setElementValue(document, name, String.valueOf(value));
	}

	private String setElementValue(String document, String name, byte[] value) {
		return setElementValue(document, name, new String(value));
	}

	private String setElementValue(String document, String name, Calendar value)
			throws UtilsException {
		return setElementValue(document, name, formatter.format(value));
	}

	public IBKeystore getKeystore() {
		return keystore;
	}

	public void setKeystore(IBKeystore keystore) {
		this.keystore = keystore;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public String getAlthoritm() {
		return althoritm;
	}

	public void setAlthoritm(String encoding) {
		this.althoritm = encoding;
	}

	public IBPersist getPersist() {
		return persist;
	}

	public void setPersist(IBPersist persist) {
		this.persist = persist;
	}

	public String getFailureTemplate() {
		return failureTemplate;
	}

	public void setFailureTemplate(String failureTemplate) {
		this.failureTemplate = failureTemplate;
	}

	public FormatterFactory getFormatter() {
		return formatter;
	}

	public void setFormatter(FormatterFactory formatter) {
		this.formatter = formatter;
	}

	public String getSuccessTemplate() {
		return successTemplate;
	}

	public void setSuccessTemplate(String successTemplate) {
		this.successTemplate = successTemplate;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificateId) {
		this.certificate = certificateId;
	}

	public String getCertAlias() {
		return certAlias;
	}

	public void setCertAlias(String certAlias) {
		this.certAlias = certAlias;
	}

	public BundleManager getBundleManager() {
		return bundleManager;
	}

	public void setBundleManager(BundleManager bundleManager) {
		this.bundleManager = bundleManager;
	}

	public ConverterManager getConverter() {
		return converter;
	}

	public void setConverter(ConverterManager converter) {
		this.converter = converter;
	}

	public IBPayment getPayment() {
		return payment;
	}

	public void setPayment(IBPayment payment) {
		this.payment = payment;
	}

	public IBUtil getUtil() {
		return util;
	}

	public void setUtil(IBUtil util) {
		this.util = util;
	}

	public IBAbonent getAbonent() {
		return abonent;
	}

	public void setAbonent(IBAbonent abonent) {
		this.abonent = abonent;
	}

	public void acceptEpaySuccessResponce(String epayResponce)
			throws BEpayException {
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	}

	public String getConfirmTemplateName() {
		return confirmTemplateName;
	}

	public void setConfirmTemplateName(String confirmTemplateName) {
		this.confirmTemplateName = confirmTemplateName;
	}

	public String getConfirmTemplateDocument() {
		return confirmTemplateDocument;
	}

	public void setConfirmTemplateDocument(String confirmTemplateDocument) {
		this.confirmTemplateDocument = confirmTemplateDocument;
	}

}