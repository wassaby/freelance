package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBLabelSet extends IBControl {

	List<IBLabel> getLabels() throws CommonsControlException;

	void setLabels(List<IBLabel> labels) throws CommonsControlException;

	void addLabel(IBLabel label) throws CommonsControlException;

}
