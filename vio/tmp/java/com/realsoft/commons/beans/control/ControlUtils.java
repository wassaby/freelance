package com.realsoft.commons.beans.control;

public class ControlUtils {

	private ControlUtils() {
	}

	public static IBModelPanel lookupRootPanel(IBModelPanel panel) {
		IBModelPanel parentModelPanel = panel.getParentModel();
		if (parentModelPanel != null)
			return lookupRootPanel(parentModelPanel);
		return panel;
	}

	public static IBControl lookupControl(IBModelPanel modelPanel,
			String controlName) throws CommonsControlException {
		if (controlName.indexOf("/") != -1) {
			IBModelPanel startPanel = null;
			String fullControlName = controlName;
			if (controlName.startsWith("/")) {
				startPanel = ControlUtils.lookupRootPanel(modelPanel);
				fullControlName = controlName.substring(1);
			} else {
				startPanel = modelPanel;
			}
			return findControl(startPanel, fullControlName);
		} else {
			return modelPanel.getControlModel(controlName);
		}
	}

	public static IBControl findControl(IBModelPanel panel, String controlName)
			throws CommonsControlException {
		if (controlName.contains("/")) {
			String panelName = controlName.substring(0, controlName
					.indexOf("/"));
			controlName = controlName.substring(controlName.indexOf("/") + 1,
					controlName.length());
			return findControl(panel.getModelPanel(panelName), controlName);
		}
		return panel.getControlModel(controlName);
	}

}
