/*
 * Created on 22.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: AbstractControl.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

public abstract class AbstractControl implements IBControl {

	protected String name = null;

	protected Object value = null;

	protected List<IBControl> dependOn = null;

	protected IBRequest request = null;

	protected boolean isDurty = true;

	protected boolean isEnabled = true;

	protected Integer foreground = null;

	protected Integer background = 1;

	protected Integer initialBackground = 1;

	protected Integer initialForeground = null;

	protected String backgroundImage = null;

	public AbstractControl(String name) {
		this.name = name;
	}

	public AbstractControl(String name, IBRequest request) {
		this.name = name;
		this.request = request;
	}

	public AbstractControl(String name, IBRequest request,
			List<IBControl> dependOn) {
		this.name = name;
		this.request = request;
		this.dependOn = dependOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() throws CommonsControlException {
		return value;
	}

	public void setValue(Object value) throws CommonsControlException {
		this.value = value;
	}

	public boolean isDurty() throws CommonsControlException {
		return isDurty;
	}

	public void setDurty(boolean isDurty) throws CommonsControlException {
		this.isDurty = isDurty;
	}

	public List<IBControl> getDependOn() throws CommonsControlException {
		return dependOn;
	}

	public void setDependOn(List<IBControl> dependOn)
			throws CommonsControlException {
		this.dependOn = dependOn;
	}

	public IBRequest getRequest() throws CommonsControlException {
		return request;
	}

	public void setRequest(IBRequest request) throws CommonsControlException {
		this.request = request;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getForeground() {
		return foreground;
	}

	public void setForeground(Integer foreground) {
		this.foreground = foreground;
	}

	public Integer getBackground() {
		return background;
	}

	public void setBackground(Integer background) {
		this.background = background;
	}

	public void setInitialBackground(Integer color) {
		initialBackground = color;
	}

	public Integer getInitialBackground() {
		return initialBackground;
	}

	public void setInitialForeground(Integer color) {
		initialForeground = color;
	}

	public Integer getInitialForeground() {
		return initialForeground;
	}

	public void setBackgroundImage(String backgroundImage)
			throws CommonsControlException {
		this.backgroundImage = backgroundImage;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.control.IBControl#copyFrom(com.realsoft.commons.beans.control.IBControl)
	 */
	public void copyFrom(IBControl control) throws CommonsControlException {
		setBackground(control.getBackground());
		setBackgroundImage(control.getBackgroundImage());
		setEnabled(control.isEnabled());
		setForeground(control.getForeground());
		setValue(control.getValue());
	}

	public void clean() throws CommonsControlException {
		setBackground(initialBackground);
		setBackgroundImage(null);
		setDurty(true);
		setEnabled(true);
		setForeground(initialForeground);
		setValue(null);
	}

}
