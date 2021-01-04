package com.realsoft.commons.beans.control;

import java.util.LinkedList;
import java.util.List;

public class BLabelSetImpl extends AbstractControl implements IBLabelSet {

	private List<IBLabel> labels = new LinkedList<IBLabel>();

	public BLabelSetImpl(String name) {
		super(name);
	}

	public BLabelSetImpl(String name, IBRequest request) {
		super(name, request);
	}

	public BLabelSetImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
	}

	public List<IBLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<IBLabel> labels) {
		this.labels = labels;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof IBLabelSet) {
			IBLabelSet labelSet = (IBLabelSet) model;
			copyFrom(labelSet);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public void addLabel(IBLabel label) throws CommonsControlException {
		labels.add(label);
	}

	public void copyFrom(IBLabelSet labelSetImpl)
			throws CommonsControlException {
		super.copyFrom(labelSetImpl);
		setLabels(labelSetImpl.getLabels());
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nLABEL_SET[").append(name).append(",").append(value)
				.append("]\n\t").append("{");
		for (IBLabel label : labels) {
			buffer.append(label.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

}
