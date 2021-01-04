/*
 * Created on 09.04.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ArrayUtils.java,v 1.2 2016/04/15 10:37:45 dauren_home Exp $
 */
package com.realsoft.utils.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @author dimad
 */
public class ArrayUtils {

	private static Logger log = Logger.getLogger(ArrayUtils.class);

	private ArrayUtils() {
	}

	public static <T> List<T> arrayToList(T[] array) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	public static <T, V extends T> boolean contains(T[] array, V value) {
		if (array == null || value == null)
			return false;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value))
				return true;
		}
		return false;
	}

	public static <T, V extends T> boolean containsFirst(T[] array, V value) {
		if (array == null || value == null)
			return false;
		if (array[0].equals(value))
			return true;
		return false;
	}

	public static <T> boolean contains(List<T> list, T object) {
		for (Iterator<T> iter = list.iterator(); iter.hasNext();) {
			if (iter.next().equals(object))
				return true;
		}
		return false;
	}

	public static <T> T[] toUniqueArray(T[] rowArray) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < rowArray.length; i++) {
			if (!contains(rowArray, rowArray[i]))
				list.add(rowArray[i]);
		}
		T[] result = ((T[]) new Object[list.size()]);
		return list.toArray(result);
	}

	public static <T, V extends T> T[] addItemToArray(T[] array, V item) {
		T[] objects = (T[]) new Object[array.length + 1];
		System.arraycopy(array, 0, objects, 0, array.length);
		objects[array.length] = item;
		return objects;
	}

	private static <T, V extends T> boolean isContain(T[] array, V object) {
		for (int j = 0; j < array.length; j++) {
			if (array[j].equals(object)) {
				object = null;
				return true;
			}
		}
		return false;
	}

	public static <V, T extends V> boolean compareArrays(T[] sourceObjects,
			V[] targetObjects) {
		if (sourceObjects.length != targetObjects.length)
			return false;
		boolean equals = true;
		for (int i = 0; i < sourceObjects.length; i++) {
			equals = equals && isContain(targetObjects, sourceObjects[i]);
		}
		return equals;
	}

	public static boolean compareArrays(byte[] sourceObjects,
			byte[] targetObjects) {
		if (sourceObjects.length != targetObjects.length)
			return false;
		boolean equals = true;
		for (int i = 0; i < sourceObjects.length; i++) {
			equals = equals && (targetObjects[i] == sourceObjects[i]);
		}
		return equals;
	}

	public static boolean compareArrays(int[] sourceObjects, int[] targetObjects) {
		if (sourceObjects.length != targetObjects.length)
			return false;
		boolean equals = true;
		for (int i = 0; i < sourceObjects.length; i++) {
			equals = equals && (targetObjects[i] == sourceObjects[i]);
		}
		return equals;
	}

	public static boolean compareArrays(long[] sourceObjects,
			long[] targetObjects) {
		if (sourceObjects.length != targetObjects.length)
			return false;
		boolean equals = true;
		for (int i = 0; i < sourceObjects.length; i++) {
			equals = equals && (targetObjects[i] == sourceObjects[i]);
		}
		return equals;
	}

	public static boolean compareArrays(List sourceObjects, List targetObject) {
		return compareArrays(sourceObjects.toArray(), targetObject.toArray());
	}

	public static boolean compareArrays(double[] sourceObjects,
			double[] targetObjects) {
		if (sourceObjects.length != targetObjects.length)
			return false;
		boolean equals = true;
		for (int i = 0; i < sourceObjects.length; i++) {
			equals = equals && (targetObjects[i] == sourceObjects[i]);
		}
		return equals;
	}

	public static <T> T[][] convertListToDoubleArray(List<T> list,
			int arrayWidth) {
		T[][] array = null;
		if (arrayWidth > list.size()) {
			array = (T[][]) new Object[list.size()][1];
			int i = 0;
			for (T item : list) {
				array[i++][1] = item;
			}
		} else {
			int rowCount = list.size() / arrayWidth + 1;
			array = (T[][]) new Object[arrayWidth][rowCount];
			Iterator<T> iterator = list.iterator();
			for (int j = 0; j < rowCount; j++) {
				for (int i = 0; i < arrayWidth; i++) {
					if (iterator.hasNext()) {
						array[i][j] = iterator.next();
					}
				}
			}
		}
		return array;
	}

	public static void main(String[] args) {
		String abonentName = "аубакирова калтай сатылхановна ";
		StringTokenizer sourceTokenizer = new StringTokenizer(abonentName
				.toLowerCase(), " ");
		String[] sourceArray = new String[sourceTokenizer.countTokens()];
		for (int i = 0; sourceTokenizer.hasMoreTokens(); i++) {
			String token = sourceTokenizer.nextToken().trim();
			sourceArray[i] = token;
		}
		String name = "аубакирова  калтай  сатылхановна ";
		StringTokenizer targetTokenizer = new StringTokenizer(name
				.toLowerCase(), " ");
		String[] targetArray = new String[targetTokenizer.countTokens()];
		for (int i = 0; targetTokenizer.hasMoreTokens(); i++) {
			String token = targetTokenizer.nextToken().trim();
			targetArray[i] = token;
		}

		boolean equals = ArrayUtils.compareArrays(sourceArray, targetArray);
		System.out.println("Arrays are equals = " + equals);
	}

}
