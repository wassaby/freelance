/*
 * Created on 25.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBEpay.java,v 1.2 2016/04/15 10:37:34 dauren_home Exp $
 */
package com.realsoft.commons.beans.epay;

import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.keystore.BKeystoreException;
import com.realsoft.commons.beans.payment.BPaymentException;
import com.realsoft.commons.beans.persist.BPersistException;
import com.realsoft.commons.beans.registration.BRegistrationException;

public interface IBEpay {

	String CURRENCY_KZT = "398";

	String COMMAND_TYPE_COMPLETE = "complete";

	String COMMAND_TYPE_REVERSE = "reverse";

	String RU_LANG = "rus";

	String EN_LANG = "eng";

	String KZ_LANG = "kz";

	String buildEpayRequest(long abonentId, long orderId, String terminal,
			double amount, String currency, String phone)
			throws BEpayException, BKeystoreException, BPersistException;

	Object[] buildEpayRequest(long abonentId, String terminal, double amount,
			String currency, String phone) throws BEpayException,
			BKeystoreException, BPersistException;

	String buildEpayConfirmRequest(String commandType, String reference,
			String approvalCode, Long orderId, Double amount,
			String currencyCode) throws BKeystoreException, BPersistException,
			BEpayException;

	String buildEpaySuccessResponce(long abonentId, String customerName,
			String certId, String merchantName, long orderId, String terminal,
			double amount, String currency, String phone, long merchantId,
			String merchantSignAlthoritm, String customerSignType,
			long reference, long approvalCode, String responseCode,
			String bankSignType) throws BEpayException;

	String buildEpayFailureResponce(long orderId, String errorType,
			String code, String errorMessage, long sessionId)
			throws BEpayException;

	void acceptEpaySuccessResponce(Long orderId, String epayResponce,
			String subDivision) throws BEpayException, BPersistException,
			BPaymentException, BRegistrationException, BAbonentException;

	void acceptEpayConfirmResponce(String epayResponce) throws BEpayException,
			BPersistException, BPaymentException, BRegistrationException,
			BAbonentException, BKeystoreException;

	Long getEpayOrder() throws BPersistException;

	byte[] calculateBlockSign(String document, String nodeXPath,
			String signatureType, String keyAlias, String keyPassword,
			boolean inverse) throws BKeystoreException;

	boolean validateEpay(String epay, String originalNodeName,
			String signedNodeName) throws BEpayException, BKeystoreException;

}
