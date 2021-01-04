package com.realsoft.commons.beans.state;

import java.io.Serializable;
import java.util.List;

public interface IBState extends Serializable {

	final String STATE_OK = "up";

	final String STATE_DOWN = "down";

	final String STATE_ERROR = "error";

	final String STATE_WARNING = "warning";

	final String STATE_WAIT = "wait";

	final String STATE_DONE = "done";

	public void setName(String name);

	public String getName();

	public String getNodeId();

	public void setNodeId(String nodeId);

	public List<String> getDescription();

	public void setDescription(List<String> description);

	public List<PaymentFailures> getPaymentFailures();

	public void setPaymentFailures(List<PaymentFailures> paymentFailures);

	public void copyOf(IBState state);

	final IBState stateOk = new BStateImpl(STATE_OK);

	final IBState stateDown = new BStateImpl(STATE_DOWN);

	final IBState stateError = new BStateImpl(STATE_ERROR);

	final IBState stateWarning = new BStateImpl(STATE_WARNING);

	final IBState stateWait = new BStateImpl(STATE_WAIT);

	final IBState stateDone = new BStateImpl(STATE_DONE);

	Object clone();

}
