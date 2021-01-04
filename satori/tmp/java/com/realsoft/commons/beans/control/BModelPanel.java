/*
 * Created on 09.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BModelPanel.java,v 1.1 2014/07/01 11:58:19 dauren_work Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansException;

public class BModelPanel implements IBModelPanel {

	private static Logger log = Logger.getLogger(BModelPanel.class);

	private static final long serialVersionUID = 4627904783683931235L;

	private IBModelPanel parent = null;

	private List<IBModelPanel> models = new ArrayList<IBModelPanel>();

	private List<IBControl> controls = new ArrayList<IBControl>();

	private String name = null;

	private List<String> errorMessages = new LinkedList<String>();

	protected Integer foreground = null;

	protected Integer background = null;

	protected Integer initialBackground = null;

	protected Integer initialForeground = null;

	protected String backgroundImage = null;

	public BModelPanel(IBModelPanel modelPanel, String name)
			throws CommonsBeansException {
		super();
		this.parent = modelPanel;
		this.name = name;
		if (this.parent != null)
			this.parent.addModelPanel(this);
	}

	public IBModelPanel getModelPanel(String name)
			throws CommonsControlException {
		for (IBModelPanel model : models) {
			if (model.getName().equals(name))
				return model;
		}
		throw new CommonsControlException(RealsoftConstants.ERR_SYSTEM,
				CommonsControlConstants.CONTROL_NOT_FOUND_EXCEPTION,
				"Could not find model '" + name + "'");
	}

	public void addModelPanel(IBModelPanel modelPanel) {
		models.add(modelPanel);
	}

	public List<IBModelPanel> getModels() {
		return models;
	}

	public void setModels(List<IBModelPanel> models) {
		this.models = models;
	}

	public IBControl getControlModel(String name)
			throws CommonsControlException {
		if (name.indexOf("/") != -1) {
			IBModelPanel panel = null;
			if (name.indexOf("/") == 0) {
				panel = ControlUtils.lookupRootPanel(this);
				name = name.substring(name.indexOf("/") + 1);
				panel = panel.getModelPanel(name
						.substring(0, name.indexOf("/")));
			} else {
				panel = getModelPanel(name.substring(0, name.indexOf("/")));
			}
			return panel.getControlModel(name.substring(name.indexOf("/") + 1));
		} else {
			for (IBControl control : controls) {
				if (control.getName().equals(name))
					return control;
			}
		}
		throw new CommonsControlException(RealsoftConstants.ERR_SYSTEM,
				CommonsControlConstants.CONTROL_NOT_FOUND_EXCEPTION,
				"Could not find control " + name);
	}

	public void setControlModel(String name, IBModel model)
			throws CommonsControlException {
		if (name.indexOf("/") != -1) {
			IBModelPanel panel = null;
			if (name.indexOf("/") == 0) {
				panel = ControlUtils.lookupRootPanel(this);
				name = name.substring(name.indexOf("/") + 1);
				panel = panel.getModelPanel(name
						.substring(0, name.indexOf("/")));
			} else {
				panel = getModelPanel(name.substring(0, name.indexOf("/")));
			}
			panel.setControlModel(name.substring(name.indexOf("/") + 1), model);
			return;
		} else {
			controls.remove(findControl(name));
			controls.add((IBControl) model);
			return;
		}
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof IBModelPanel) {
			IBModelPanel modelPanel = (IBModelPanel) model;
			setModels(modelPanel.getModels());
			setControls(modelPanel.getControls());
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	private IBControl findControl(String name) throws CommonsControlException {
		for (IBControl foundControl : controls) {
			if (foundControl.getName().equals(name)) {
				return foundControl;
			}
		}
		throw new CommonsControlException(RealsoftConstants.ERR_SYSTEM,
				CommonsControlConstants.CONTROL_NOT_FOUND_EXCEPTION,
				"Could not find control " + name);
	}

	public void addControlModel(IBControl newControl) {
		controls.add(newControl);
	}

	public List<IBControl> getControls() {
		return controls;
	}

	public void setControls(List<IBControl> controls) {
		this.controls = controls;
	}

	public boolean isDurty() throws CommonsControlException {
		for (IBControl control : controls) {
			if (control.isDurty())
				return true;
		}
		for (IBModelPanel model : models) {
			if (model.isDurty())
				return true;
		}
		return false;
	}

	public void setDurty(boolean isDurty) throws CommonsControlException {
		log.debug("setting durty ...");
		for (IBControl control : controls) {
			control.setDurty(isDurty);
		}
		for (IBModelPanel model : models) {
			model.setDurty(isDurty);
		}
		log.debug("setting durty done");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnabled(boolean isEnabled) throws CommonsControlException {
		log.debug("setting enabled ...");
		for (IBControl control : controls) {
			control.setEnabled(isEnabled);
		}
		for (IBModelPanel model : models) {
			model.setEnabled(isEnabled);
		}
		log.debug("setting enabled done");
	}

	public void checkModel() throws CommonsControlException {
		checkUnique();
		checkDependency();
	}

	private void checkUnique() throws CommonsControlException {
		for (IBModelPanel model : models) {
			for (IBControl control : model.getControls()) {
				if (!isControlUnique(model, control))
					throw new CommonsControlException(
							RealsoftConstants.ERR_SYSTEM,
							"commons-control.bmodel-panel.check-unique.error",
							"Control '" + control.getName() + "' is not unique");
			}
		}
	}

	private boolean isControlUnique(IBModelPanel model, IBControl control)
			throws CommonsControlException {
		int count = 0;
		if (control.getName().equals("payment-amount"))
			log.error("checking control " + control.getName());
		for (IBControl controlToCheck : model.getControls()) {
			if (controlToCheck.getName().equals(control.getName()))
				count++;
		}
		if (count != 1)
			log.error("control " + control.getName() + " is not unique");
		return count == 1;
	}

	private void checkDependency() throws CommonsControlException {
		for (IBControl control : getControls()) {
			if (control.getDependOn() != null) {
				if (control.getRequest() instanceof BSQLRequestImpl) {
					BSQLRequestImpl request = (BSQLRequestImpl) control
							.getRequest();
					if (control.getDependOn().size() == 0
							&& request.getRequest().contains(":"))
						throw new CommonsControlException(
								RealsoftConstants.ERR_SYSTEM,
								"commons-control.bmodel-panel.check-dependency.request.error",
								"No dependency of control '"
										+ control.getName()
										+ "' found while request has parameters");
//					for (IBControl dependControl : control.getDependOn()) {
//						checkControlExist(fullControlName);
//						if (!request.getRequest().contains(
//								":" + control.getName()))
//							throw new CommonsControlException(
//									RealsoftConstants.ERR_SYSTEM,
//									"commons-control.bmodel-panel.check-dependency.dependency.error",
//									"Wrong request '" + control.getRequest()
//											+ "' or dependency name '"
//											+ fullControlName + "'");
//					}
				} else if (control.getRequest() instanceof BComponentRequestImpl) {
					for (IBControl dependControl : control.getDependOn()) {
//						checkControlExist(fullControlName);
					}
				}
			}
		}
	}

	private void checkControlExist(String fullControlName)
			throws CommonsControlException {
		if (!fullControlName.contains("\\.")) {
			if (fullControlName.equals("autoType")
					|| fullControlName.equals("creditAmountCurrency"))
				log.debug("autoType/creditAmountCurrency");
			getControlModel(fullControlName);
		} else {
			String[] controlPath = fullControlName.split("\\.");
			IBModelPanel modelPanel = parent.getModelPanel(controlPath[0]);
			for (int i = 1; i < controlPath.length - 1; i++) {
				modelPanel = modelPanel.getModelPanel(controlPath[i]);
			}
			modelPanel.getControlModel(controlPath[controlPath.length - 1]);
		}
		// throw new CommonsControlException(
		// RealsoftConstants.ERR_SYSTEM,
		// "commons-control.bmodel-panel.check-control-exist.control-path.error",
		// "Control path must be full path to the control including parent model
		// name");
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nPANEL[ name=").append(name).append(" , class='")
				.append(getClass().getName()).append("' background = '")
				.append(background).append("' foreground = '").append(
						foreground).append("' backgroundImage = '").append(
						backgroundImage).append("' ]").append("\n\t").append(
						"{");
		for (IBModelPanel modelPanel : models) {
			buffer.append(modelPanel.toString());
		}
		for (IBControl control : controls) {
			buffer.append(control.toString());
		}
		return buffer.append("\n\t}//").append(name).toString();
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void addErrorMessage(String errorMessage) {
		errorMessages.add(errorMessage);
	}

	public IBModelPanel getModelPanel() {
		return this;
	}

	public IBModelPanel getParentModel() {
		return parent;
	}

	public void setParentModel(IBModelPanel modelPanel) {
		this.parent = modelPanel;
	}

	public IBModelPanel buildModel() throws CommonsBeansException {
		return null;
	}

	public Integer getBackground() throws CommonsControlException {
		return background;
	}

	public Integer getForeground() throws CommonsControlException {
		return foreground;
	}

	public Integer getInitialBackground() throws CommonsControlException {
		return initialBackground;
	}

	public Integer getInitialForeground() throws CommonsControlException {
		return initialForeground;
	}

	public void setBackground(Integer color) throws CommonsControlException {
		background = color;
	}

	public void setBackgroundImage(String image) throws CommonsControlException {
		this.backgroundImage = image;
	}

	public void setForeground(Integer color) throws CommonsControlException {
		foreground = color;
	}

	public void setInitialBackground(Integer color)
			throws CommonsControlException {
		background = color;
	}

	public void setInitialForeground(Integer color)
			throws CommonsControlException {
		initialForeground = color;
	}

	public void clean() throws CommonsControlException {
		setBackground(initialBackground);
		setBackgroundImage(null);
		setDurty(true);
		setEnabled(true);
		setErrorMessages(new LinkedList<String>());
		setForeground(initialForeground);
		for(IBControl control : controls){
			control.clean();
		}
		for(IBModelPanel modelPanel : models){
			modelPanel.clean();
		}
	}

}
