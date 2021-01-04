/*
 * Created on 14.11.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: IBDeviceStateListener.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.commons.beans.state;

import java.rmi.RemoteException;

public interface IBDeviceStateListener {

	void stateChanged(IBState state) throws RemoteException;

}
