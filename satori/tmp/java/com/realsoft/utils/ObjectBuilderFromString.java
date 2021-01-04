package com.realsoft.utils;

import org.apache.log4j.Logger;

import com.realsoft.utils.reflection.ObjectMethodCaller;

public class ObjectBuilderFromString {

	private static Logger log = Logger.getLogger(ObjectBuilderFromString.class);

	private ObjectBuilderFromString() {
	}

	public static Object buildObjectFromString(Class clazz, String string)
			throws UtilsException {
		if (string == null)
			return null;
		try {
			Object object = clazz.newInstance();
			log.debug("object string = " + string);
			String stringTokens = string.substring(string.indexOf('[') + 1,
					string.indexOf(']'));
			// stringTokens = stringTokens.substring(1,string.indexOf(']')-1);
			String[] tokens = stringTokens.split(",");
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i] != null && tokens[i] != "") {
					String[] nameValue = tokens[i].split("=");
					ObjectMethodCaller.invokeSetterMethod(object, nameValue[0],
							nameValue[1]);
				}
			}
			return object;
		} catch (Exception e) {
			log.error("Could not build object", e);
			throw new UtilsException("Could not build object", e);
		}
	}

	public static Object buildObjectFromString(String string)
			throws UtilsException {
		if (string.indexOf('@') == -1)
			throw new UtilsException("No '@' character found in the string "
					+ string);
		Class clazz = null;
		String[] classString = string.split("@");
		try {
			return buildObjectFromString(Class.forName(classString[0]),
					classString[1]);
		} catch (ClassNotFoundException e) {
			throw new UtilsException("Could not build object from string", e);
		}
	}

}
