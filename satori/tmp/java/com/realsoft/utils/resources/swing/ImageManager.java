/*
 * Created on 21.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga.
 * $Id: ImageManager.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.utils.resources.swing;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * 
 */
public class ImageManager {

	private static ImageManager instance = null;

	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	private Map images = new HashMap();

	private ImageManager() {
	}

	public ImageIcon getImage(String fullFileName) {
		if (images.containsKey(fullFileName)) {
			return (ImageIcon) images.get(fullFileName);
		}
		ImageIcon image = new ImageIcon(fullFileName);
		images.put(fullFileName, image);
		return image;
	}

	public ImageIcon getImage(Class clazz, String fileName) {
		String key = clazz.getPackage().getName() + "." + fileName;
		if (images.containsKey(key)) {
			return (ImageIcon) images.get(key);
		}
		ImageIcon image = new ImageIcon(clazz.getClassLoader().getResource(
				fileName));
		images.put(key, image);
		return image;
	}

	public ImageIcon getImage(URL url) throws IOException {
		if (images.containsKey(url)) {
			return (ImageIcon) images.get(url);
		}
		ImageIcon image = new ImageIcon(url);
		images.put(url, image);
		return image;
	}

	public ImageIcon getImage(String folder, String fileName) {
		String key = folder + "/" + fileName;
		if (images.containsKey(key)) {
			return (ImageIcon) images.get(key);
		}
		ImageIcon image = new ImageIcon(getClass().getClassLoader()
				.getResource(key));
		images.put(key, image);
		return image;
	}

	public ImageIcon getPackageImage(Class clazz, String fileName) {
		if (images.containsKey(fileName)) {
			return (ImageIcon) images.get(fileName);
		}
		ImageIcon image = new ImageIcon(clazz.getClassLoader().getResource(
				fileName));
		images.put(fileName, image);
		return image;
	}

}
