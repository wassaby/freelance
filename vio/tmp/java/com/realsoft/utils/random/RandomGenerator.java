/*
 * Created on 05.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: RandomGenerator.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
 */
package com.realsoft.utils.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author dimad
 * 
 */
public class RandomGenerator {

	private static String althoritm = "SHA1PRNG";

	private RandomGenerator() {
	}

	public static long getLong() throws NoSuchAlgorithmException {
		return SecureRandom.getInstance(althoritm).nextLong();
	}

	public static int getInt() throws NoSuchAlgorithmException {
		return SecureRandom.getInstance(althoritm).nextInt();
	}

	public static double getDouble() throws NoSuchAlgorithmException {
		return SecureRandom.getInstance(althoritm).nextDouble();
	}

	public static float getFloat() throws NoSuchAlgorithmException {
		return SecureRandom.getInstance(althoritm).nextFloat();
	}

}
