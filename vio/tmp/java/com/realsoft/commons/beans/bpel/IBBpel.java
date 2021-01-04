package com.realsoft.commons.beans.bpel;

public interface IBBpel {
	BBpelResponce sendOrderToDestination(BBpelRequest request) throws BBpelException;
}
