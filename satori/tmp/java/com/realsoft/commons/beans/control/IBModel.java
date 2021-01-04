package com.realsoft.commons.beans.control;

import java.io.Serializable;

import com.realsoft.commons.beans.CommonsBeansException;

public interface IBModel extends Serializable {

	void setModel(Object model) throws CommonsControlException, CommonsBeansException;
	
	public Object getModel() throws CommonsControlException;

}
