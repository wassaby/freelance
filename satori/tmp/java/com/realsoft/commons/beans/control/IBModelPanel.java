package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.List;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBModelPanel extends IBModel, Serializable {

	int COLOR_WHITE = 1;

	int COLOR_BLACK = 2;

	int COLOR_RED = 3;

	int COLOR_GREEN = 5;

	IBModelPanel getModelPanel();

	IBModelPanel getModelPanel(String name) throws CommonsControlException;

	void addModelPanel(IBModelPanel modelPanel);

	List<IBModelPanel> getModels();

	void setModels(List<IBModelPanel> models);

	IBControl getControlModel(String name) throws CommonsControlException;

	void setControlModel(String name, IBModel model)
			throws CommonsControlException;

	void addControlModel(IBControl newControl);

	List<IBControl> getControls() throws CommonsControlException;

	void setControls(List<IBControl> controls);

	boolean isDurty() throws CommonsControlException;

	void setDurty(boolean isDurty) throws CommonsControlException;
	
	String getName();

	void setName(String name);

	void checkModel() throws CommonsControlException;

	void setEnabled(boolean isEnabled) throws CommonsControlException;

	List<String> getErrorMessages();

	void setErrorMessages(List<String> errorMessages);

	void addErrorMessage(String errorMessage);

	IBModelPanel getParentModel();

	void setParentModel(IBModelPanel modelPanel);

	IBModelPanel buildModel() throws CommonsBeansException;

	void setForeground(Integer color) throws CommonsControlException;

	Integer getForeground() throws CommonsControlException;

	void setBackground(Integer color) throws CommonsControlException;

	Integer getBackground() throws CommonsControlException;

	void setInitialBackground(Integer color) throws CommonsControlException;

	Integer getInitialBackground() throws CommonsControlException;

	void setInitialForeground(Integer color) throws CommonsControlException;

	Integer getInitialForeground() throws CommonsControlException;

	void setBackgroundImage(String image) throws CommonsControlException,
			CommonsBeansException;
	
	void clean() throws CommonsControlException;

}