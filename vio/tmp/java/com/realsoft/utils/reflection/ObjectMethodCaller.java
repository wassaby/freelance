package com.realsoft.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.realsoft.utils.UtilsException;

public class ObjectMethodCaller {

	private static final Logger log = Logger
			.getLogger(ObjectMethodCaller.class);

	private ObjectMethodCaller() {
	}

	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters) throws UtilsException {
		try {
			Class[] clazzes = new Class[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				clazzes[i] = parameters[i].getClass();
			}
			Method method = object.getClass().getMethod(methodName, clazzes);
			// log.debug("invoking getter method " + method);
			return method.invoke(object, parameters);
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke method", e);
		}
	}

	public static Object invokeGetterMethod(Object object, String field)
			throws UtilsException {
		try {
			// log.debug("invoking for field " + field);
			Method method = null;
			Field fieldObj = object.getClass().getDeclaredField(field);
			if (fieldObj.getType().equals(Boolean.class)
					|| fieldObj.getType().equals(boolean.class)) {
				method = object.getClass().getMethod(
						"is" + StringUtils.capitalize(field), new Class[] {});
			} else {
				method = object.getClass().getMethod(
						"get" + StringUtils.capitalize(field), new Class[] {});
			}
			// log.debug("invoking getter method " + method + " for field " +
			// field);
			return method.invoke(object, new Object[] {});
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke getter method", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object[] values) throws UtilsException {
		try {
			Class[] classes = new Class[values.length];
			for (int i = 0; i < values.length; i++) {
				classes[i] = values[i].getClass();
				// log.debug("class name = " + classes[i] + "; value = " +
				// values[i]);
			}

			String methodName = "set" + StringUtils.capitalize(field);
			// log.debug("Method name = " + methodName);
			Method method = object.getClass().getMethod(methodName, classes);
			method.invoke(object, values);
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke setter method", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object value) throws UtilsException {
		try {
			invokeSetterMethod(object, field, new Object[] { value });
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke setter method (" + field
					+ ")", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object value, Class clazz) throws UtilsException {
		try {
			Class[] classes = new Class[] { clazz };
			String methodName = "set" + StringUtils.capitalize(field);
			Method method = object.getClass().getMethod(methodName, classes);
			method.invoke(object, value);
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke setter method", e);
		}
	}

	public static void invokeSetterMethodNull(Object object, String field,
			Class clazz) throws UtilsException {
		try {
			String methodName = "set" + StringUtils.capitalize(field);
			Method method = object.getClass().getMethod(methodName,
					new Class[] { clazz });
			method.invoke(object, new Object[] { null });
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new UtilsException("Could not invoke setter method", e);
		}
	}

	public static Object invokeMethodByClass(Object object, String methodName,
			Map<String, Object> parameters) throws UtilsException {
		for (String parameterName : parameters.keySet()) {
			int cnt = 0;
			for (String param : parameters.keySet()) {
				if (param.equals(parameterName))
					cnt++;
			}
			if (cnt != 1)
				throw new UtilsException(
						"Parameter name is not unique or does not exists");
		}
		Method method = lookupMethod(object, methodName, parameters);
		Class parameterType = null;
		try {
			if (method.getParameterTypes().length > 0) {
				parameterType = method.getParameterTypes()[0];
				Object parameter = parameterType.newInstance();
				for (String parameterName : parameters.keySet()) {
					invokeSetterMethod(parameter, parameterName, parameters
							.get(parameterName));
				}
				return invokeMethod(object, methodName,
						new Object[] { parameter });
			} else {
				return invokeMethod(object, methodName, new Object[] {});
			}
		} catch (Exception e) {
			log.error("Could not create parameter object instance of "
					+ parameterType, e);
			throw new UtilsException(
					"Could not create parameter object instance of "
							+ parameterType);
		}
	}

	public static Object invokeMethodByClass(Object object, String methodName)
			throws UtilsException {
		try {
			return invokeMethod(object, methodName, new Object[] {});
		} catch (Exception e) {
			log.error("Could not invoke method " + methodName + " of "
					+ object.getClass(), e);
			throw new UtilsException("Could not invoke method " + methodName
					+ " of " + object.getClass());
		}
	}

	private static Method lookupMethod(Object object, String methodName,
			Map<String, Object> parameters) throws UtilsException {
		log.debug("lookupMethod: methodName = " + methodName);
		for (Method method : object.getClass().getMethods()) {
			if (method.getName().equals(methodName)) {
				Class[] parameterTypes = method.getParameterTypes();
				int count = 0;
				for (String parameterName : parameters.keySet()) {
					if (isFieldExists(parameterName, parameterTypes[0]))
						count++;
				}
				if (count == parameters.size())
					return method;
			}
		}
		throw new UtilsException("Method with name " + methodName
				+ " of object " + object.getClass() + " does not exists");
	}

	private static boolean isFieldExists(String fieldName, Class objectType) {
		for (Field field : objectType.getDeclaredFields()) {
			if (field.getName().equals(fieldName))
				return true;
		}
		return false;
	}

	public static Object getConstantValue(Object object, String constName)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		return object.getClass().getField(constName).get(object);
	}

}
