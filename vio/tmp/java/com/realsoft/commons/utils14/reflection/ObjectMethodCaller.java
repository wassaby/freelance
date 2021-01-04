package com.realsoft.commons.utils14.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ObjectMethodCaller {

	private static final Logger log = Logger
			.getLogger(ObjectMethodCaller.class);

	private ObjectMethodCaller() {
	}

	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters) throws Exception {
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
			throw new Exception("Could not invoke method", e);
		}
	}

	public static Object invokeGetterMethod(Object object, String field)
			throws Exception {
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
			throw new Exception("Could not invoke getter method", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object[] values) throws Exception {
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
			throw new Exception("Could not invoke setter method", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object value) throws Exception {
		try {
			invokeSetterMethod(object, field, new Object[] { value });
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new Exception("Could not invoke setter method (" + field
					+ ")", e);
		}
	}

	public static void invokeSetterMethod(Object object, String field,
			Object value, Class clazz) throws Exception {
		try {
			Class[] classes = new Class[] { clazz };
			String methodName = "set" + StringUtils.capitalize(field);
			Method method = object.getClass().getMethod(methodName, classes);
			method.invoke(object, new Object[] { value });
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new Exception("Could not invoke setter method", e);
		}
	}

	public static void invokeSetterMethodNull(Object object, String field,
			Class clazz) throws Exception {
		try {
			String methodName = "set" + StringUtils.capitalize(field);
			Method method = object.getClass().getMethod(methodName,
					new Class[] { clazz });
			method.invoke(object, new Object[] { null });
		} catch (Exception e) {
			log.error("Could not invoke method", e);
			throw new Exception("Could not invoke setter method", e);
		}
	}

	public static Object invokeMethodByClass(Object object, String methodName,
			Map parameters) throws Exception {
		String[] keys = (String[]) parameters.keySet().toArray(
				new String[parameters.keySet().size()]);
		for (int i = 0; i < keys.length; i++) {
			String parameterName = keys[i];
			int cnt = 0;
			for (int j = 0; j < keys.length; j++) {
				String param = keys[i];
				if (param.equals(parameterName))
					cnt++;
			}
			if (cnt != 1)
				throw new Exception(
						"Parameter name is not unique or does not exists");
		}
		Method method = lookupMethod(object, methodName, parameters);
		Class parameterType = null;
		try {
			if (method.getParameterTypes().length > 0) {
				parameterType = method.getParameterTypes()[0];
				Object parameter = parameterType.newInstance();
				for (int i = 0; i < keys.length; i++) {
					String parameterName = keys[i];
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
			throw new Exception(
					"Could not create parameter object instance of "
							+ parameterType);
		}
	}

	public static Object invokeMethodByClass(Object object, String methodName)
			throws Exception {
		try {
			return invokeMethod(object, methodName, new Object[] {});
		} catch (Exception e) {
			log.error("Could not invoke method " + methodName + " of "
					+ object.getClass(), e);
			throw new Exception("Could not invoke method " + methodName
					+ " of " + object.getClass());
		}
	}

	private static Method lookupMethod(Object object, String methodName,
			Map parameters) throws Exception {
		log.debug("lookupMethod: methodName = " + methodName);
		for (int i = 0; i < object.getClass().getMethods().length; i++) {
			Method method = object.getClass().getMethods()[i];
			if (method.getName().equals(methodName)) {
				Class[] parameterTypes = method.getParameterTypes();
				int count = 0;
				String[] keys = (String[]) parameters.keySet().toArray(
						new String[parameters.keySet().size()]);
				for (int j = 0; j < keys.length; j++) {
					String parameterName = keys[j];
					if (isFieldExists(parameterName, parameterTypes[0]))
						count++;
				}
				if (count == parameters.size())
					return method;
			}
		}
		throw new Exception("Method with name " + methodName + " of object "
				+ object.getClass() + " does not exists");
	}

	private static boolean isFieldExists(String fieldName, Class objectType) {
		for (int j = 0; j < objectType.getClass().getDeclaredFields().length; j++) {
			Field field = objectType.getClass().getDeclaredFields()[j];
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
