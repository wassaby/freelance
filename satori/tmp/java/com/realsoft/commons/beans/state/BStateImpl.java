package com.realsoft.commons.beans.state;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.realsoft.utils.array.ArrayUtils;

public class BStateImpl implements IBState, Cloneable {

	private static Logger log = Logger.getLogger(BStateImpl.class);

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> description = new LinkedList<String>();

	private List<PaymentFailures> paymentFailures = new LinkedList<PaymentFailures>();
	
	private String nodeId = "nod id not defined";

	public BStateImpl(String name) {
		super();
		this.name = name;
	}

	public BStateImpl(String name, List<String> description) {
		this(name);
		this.description = description;
	}

	public BStateImpl(String name, List<String> description, String nodeId) {
		this(name, description);
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public void copyOf(IBState state) {
		setDescription(state.getDescription());
		setName(state.getName());
		setNodeId(state.getNodeId());
	}

	public static String multiplyState(IBState firstState, IBState secondState) {
		String state = IBState.STATE_OK;
		if (firstState.getName().equals(IBState.STATE_ERROR)
				|| firstState.getName().equals(IBState.STATE_DOWN)) {
			state = IBState.STATE_ERROR;
		}
		if (secondState.getName().equals(IBState.STATE_ERROR)
				|| secondState.getName().equals(IBState.STATE_DOWN)) {
			state = IBState.STATE_ERROR;
		}
		return state;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			log.debug("state are equals each other");
			return true;
		}
		if (obj instanceof IBState) {
			IBState state = (IBState) obj;
			log.debug("state are equals each other = "
					+ state.getName().equals(name));
			return state.getName().equals(name)
					&& ArrayUtils.compareArrays(state.getDescription(),
							getDescription());
		}
		return false;
	}

	public String toString() {
		String result = name.toUpperCase() + "[" + nodeId + " : ";
		for (String string : description) {
			result += string + "\n";
		}
		result += "]";
		return result;
	}

	public Object clone() {
		IBState state = null;
		try {
			state = (IBState) super.clone();
			state.setDescription(description);
			state.setName(name);
			state.setNodeId(nodeId);
		} catch (CloneNotSupportedException e) {
			log.error("Could not clone IBState", e);
		}
		return state;
	}

	public List<PaymentFailures> getPaymentFailures() {
		return paymentFailures;
	}

	public void setPaymentFailures(List<PaymentFailures> paymentFailures) {
		this.paymentFailures = paymentFailures;
	}

}