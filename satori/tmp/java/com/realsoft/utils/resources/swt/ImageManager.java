/*
 * Created on 09.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga
 * $Id: ImageManager.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.utils.resources.swt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.UIPlugin;

/**
 * @author dimad
 */
public class ImageManager {

	private static final Logger log = Logger.getLogger(ImageManager.class);

	private static ImageManager instance = null;

	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	private Map<String, Object> images = new HashMap<String, Object>();

	public ImageManager() {
	}

	public Image getImage(Shell shell, String fullFileName) {
		if (images.containsKey(fullFileName)) {
			return (Image) images.get(fullFileName);
		}
		Image image = null;
		try {
			image = new Image(shell.getDisplay(), fullFileName);
			images.put(fullFileName, image);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not load imager " + fullFileName, e);
		}
		return image;
	}

	public Image getImage(Shell shell, Class clazz, String fileName) {
		String key = clazz.getPackage().getName() + "." + fileName;
		if (images.containsKey(key)) {
			return (Image) images.get(key);
		}
		Image image = null;
		try {
			image = new Image(shell.getDisplay(), clazz.getClassLoader()
					.getResourceAsStream(fileName));
			images.put(key, image);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not load imager " + fileName, e);
		}
		return image;
	}

	public Image getPackageImage(Shell shell, String fileName) {
		if (images.containsKey(fileName)) {
			return (Image) images.get(fileName);
		}
		Image image = null;
		try {
			image = new Image(shell.getDisplay(), getClass().getClassLoader()
					.getResourceAsStream(fileName));
			images.put(fileName, image);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not load imager " + fileName, e);
		}
		return image;
	}

	public Image getImage(Shell shell, String folder, String fileName) {
		String key = folder + "/" + fileName;
		if (images.containsKey(key)) {
			return (Image) images.get(key);
		}
		Image image = null;
		try {
			image = new Image(shell.getDisplay(), getClass().getClassLoader()
					.getResourceAsStream(key));
			images.put(key, image);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not load imager " + fileName, e);
		}
		return image;
	}

	public Image getImage(Shell shell, URL url) throws IOException {
		if (images.containsKey(url)) {
			return (Image) images.get(url);
		}
		InputStream in = url.openConnection().getInputStream();
		Image image = null;
		try {
			image = new Image(shell.getDisplay(), in);
			images.put(url.toString(), image);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not load imager " + url, e);
		}
		return image;
	}

	public Image getPluginImage(String pluginName, String imageName) {
		log.debug("pluginName = " + pluginName + "; imageName = " + imageName);
		if (images.containsKey(imageName)) {
			return (Image) images.get(imageName);
		}
		if (pluginName != null && imageName != null) {
			ImageDescriptor descriptor = UIPlugin.imageDescriptorFromPlugin(
					pluginName, imageName);
			if (descriptor != null) {
				Image image = descriptor.createImage();
				images.put(imageName, image);
				return image;
			}
			return null;
		}
		return null;
	}

}
