package com.realsoft.commons.beans.control;

import java.util.Map;

public interface IBRequest {

	public abstract Map<String, Object> getArguments();

	public abstract void setArguments(Map<String, Object> arguments);

}