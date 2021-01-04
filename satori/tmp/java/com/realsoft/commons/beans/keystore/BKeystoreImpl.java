/*
 * Created on 03.05.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BKeystoreImpl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

import org.apache.log4j.Logger;

import sun.security.tools.KeyTool;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BKeystoreImpl implements IBKeystore {

	private static Logger log = Logger.getLogger(BKeystoreImpl.class);

	private KeyStore keyStore = null;

	private String keystoreFile = null;

	private String keyStorePassword = null;

	public BKeystoreImpl() {
		super();
	}

	public void generateKeyPair(String alias, String name,
			String organizationUnit, String organization, String locality,
			String state, String countryCode) throws BKeystoreException {
		String dname = new StringBuffer().append("CN=").append(name).append(
				", OU=").append(organizationUnit).append(", O=").append(
				organization).append(", L=").append(locality).append(", ST=")
				.append(state).append(", C=").append(countryCode).toString();
		String[] parameters = new String[] { "-genkey", "-alias", alias,
				"-dname", dname, "-keystore", keystoreFile, "-storepass",
				keyStorePassword, "-keypass", keyStorePassword };
		try {
			KeyTool.main(parameters);
			loadKeystore();
		} catch (Exception e) {
			log.error("Could not generate keypair", e);
			throw new BKeystoreException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.generating-keypair.error",
					"Could not generate keypair for alias " + alias);
		}
	}

	public Certificate getCertificate(String alias) throws BKeystoreException {
		try {
			if (keyStore.containsAlias(alias))
				return keyStore.getCertificate(alias);
			throw new BKeystoreException(
					CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.getting-certificate.no-certificate.error",
					"No such certificate with alias " + alias);
		} catch (KeyStoreException e) {
			log.error("Could not get certificate for " + alias, e);
			throw new BKeystoreException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.getting-certificate.error",
					"Could not get certificate with alias " + alias);
		}
	}

	public PublicKey getPublicKey(String alias) throws BKeystoreException {
		return getCertificate(alias).getPublicKey();
	}

	public byte[] calculateSign(byte[] value, String althoritm,
			String keyAlias, char[] password) throws BKeystoreException {
		if (!althoritm.equals(MD5withDSA) && !althoritm.equals(MD5withRSA)
				&& !althoritm.equals(MD2withRSA)
				&& !althoritm.equals(SHA1withDSA)
				&& !althoritm.equals(SHA1withRSA))
			throw new BKeystoreException(
					CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.calculating-sign.not-supporter-althoritms",
					"Could not calculate sign, cased not supporter althorim "
							+ althoritm);
		try {
			log.debug("keyAlias = " + keyAlias + "; althoritm = " + althoritm
					+ "; value = " + new String(value));
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
					password);
			Signature signature = Signature.getInstance(althoritm);
			signature.initSign(privateKey);
			signature.update(value);
			return signature.sign();
		} catch (Exception e) {
			log.error("Could not calculate sign", e);
			throw new BKeystoreException(
					CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.calculating-sign.sign-calculate-error",
					"Could not calculate sign", e);
		}
	}

	public boolean validateSignedBytes(byte[] originalBytes,
			byte[] signedBytes, String althoritm, String certAlias)
			throws BKeystoreException {
		try {
			Certificate certificate = keyStore.getCertificate(certAlias);
			Signature signature = Signature.getInstance(althoritm);
			signature.initVerify(certificate);
			signature.update(originalBytes);
			return signature.verify(signedBytes);
		} catch (Exception e) {
			log.error("Could not validate sign", e);
			throw new BKeystoreException(
					CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.keystore.validate-sign.sign-validation-error",
					"Could not validate sign", e);
		}
	}

	private void loadKeystore() throws BKeystoreException {
		try {
			keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream in = new FileInputStream(new File(getClass()
					.getClassLoader().getResource(keystoreFile).toURI()));
			try {
				keyStore.load(in, keyStorePassword.toCharArray());
			} finally {
				in.close();
			}
		} catch (Exception e) {
			log.fatal("Could not load keystore", e);
			throw new BKeystoreException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.KEYSTORE_LOAD_ERROR,
					"Could not load keystore from " + keystoreFile, e);
		}
	}

	public void initialize() throws Exception {
		try {
			loadKeystore();
		} catch (Exception e) {
			log.error("Could not load keystore", e);
		}
	}

	private void saveKeyStore() {
		OutputStream out;
		try {
			out = new FileOutputStream(new File(getClass().getClassLoader()
					.getResource(keystoreFile).toURI()));
			try {
				keyStore.store(out, keyStorePassword.toCharArray());
			} finally {
				out.close();
			}
		} catch (Exception e) {
			log.fatal("Could not save keyStore to file " + keystoreFile, e);
		}
	}

	public String getKeystoreFile() {
		return keystoreFile;
	}

	public void setKeystoreFile(String keystoreFile) {
		this.keystoreFile = keystoreFile;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

}
