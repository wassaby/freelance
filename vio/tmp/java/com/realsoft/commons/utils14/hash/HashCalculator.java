/*
 * Created on 04.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: HashCalculator.java,v 1.2 2016/04/15 10:37:49 dauren_home Exp $
 */
package com.realsoft.commons.utils14.hash;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.realsoft.commons.utils14.RealsoftConstants;
import com.realsoft.commons.utils14.RealsoftException;

/**
 * @author dimad
 */
public class HashCalculator {

	private static Logger log = Logger.getLogger(HashCalculator.class);

	public static final String MD5 = "MD5";

	public static final String SHA = "SHA";

	private HashCalculator() {
	}

	public static String getHash(byte[] value, String althoritm)
			throws RealsoftException {
		try {
			MessageDigest md = MessageDigest.getInstance(althoritm);
			return new String(Hex.encodeHex(md.digest(value)));
		} catch (Exception e) {
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,RealsoftConstants.BUNDLE_NAME,"utils14.HashCalculator.getHash.error",null,"Could not calculate hash", e);
		}
	}

	public static String getMD5(byte[] value) throws RealsoftException {
		return getHash(value, MD5);
	}

	public static String getMD5(String value) throws RealsoftException {
		return getMD5(value.getBytes());
	}

	public static String getSHA(byte[] value) throws RealsoftException {
		return getHash(value, SHA);
	}

	public static String getSHA(String value) throws RealsoftException {
		return getSHA(value.getBytes());
	}

	public static void main(String[] args) {
		try {
			String password = "realsoft";
			System.out.println(password + ": SHA = "
					+ HashCalculator.getSHA(password));
			System.out.println(password + ": MD5 = "
					+ HashCalculator.getMD5(password));
		} catch (RealsoftException e) {
			e.printStackTrace();
		}
	}
}
