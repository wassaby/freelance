/*
 * Created on 14.11.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: IBDeviceStateListener.java,v 1.2 2016/04/15 10:37:46 dauren_home Exp $
 */
package com.realsoft.commons.beans.state;

import java.rmi.RemoteException;

public interface IBDeviceStateListener {

	void stateChanged(IBState state) throws RemoteException;

}
