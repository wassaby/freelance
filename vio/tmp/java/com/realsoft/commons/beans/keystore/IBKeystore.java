/*
 * Created on 03.05.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBKeystore.java,v 1.2 2016/04/15 10:37:46 dauren_home Exp $
 */
package com.realsoft.commons.beans.keystore;

import java.security.cert.Certificate;

public interface IBKeystore {

	String MD5withRSA = "MD5withRSA";

	String MD5withDSA = "MD5withDSA";

	String MD2withRSA = "MD2withRSA";

	String SHA1withRSA = "SHA1withRSA";

	String SHA1withDSA = "SHA1withDSA";

	void generateKeyPair(String alias, String name, String organizationUnit,
			String organization, String locality, String state,
			String countryCode) throws BKeystoreException;

	Certificate getCertificate(String alias) throws BKeystoreException;

	byte[] calculateSign(byte[] value, String althoritm, String keyAlias,
			char[] password) throws BKeystoreException;

	boolean validateSignedBytes(byte[] originalBytes, byte[] signedBytes,
			String althoritm, String certAlias) throws BKeystoreException;

}
