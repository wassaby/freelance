/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBDealer.java,v 1.2 2016/04/15 10:37:33 dauren_home Exp $
 */
package com.realsoft.commons.beans.apps;

import java.util.Date;

import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.account.BAccountException;
import com.realsoft.commons.beans.dealer.control.CControlException;
import com.realsoft.commons.beans.dealer.control.ICControlBox;
import com.realsoft.commons.beans.inventory.BInventoryException;
import com.realsoft.commons.beans.mail.BMailException;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.request.BRequestException;
import com.realsoft.commons.beans.urlresolver.BURLResolverException;
import com.realsoft.commons.beans.util.BUtilException;

public interface IBDealer {

	String ROLE = IBDealer.class.getName();

	ICControlBox getLoginPage() throws BDealerException, BURLResolverException;

	ICControlBox getAbonentInformationPage(Long townId, String phone,
			String abonentId, String name) throws BDealerException,
			BAbonentException, BRegistrationException, BAccountException,
			BInventoryException;

	ICControlBox getAbonentDebitPage(Long townId, String phone,
			String abonentId, String name) throws BDealerException,
			BRegistrationException, BAbonentException, BAccountException,
			BInventoryException, CControlException;

	ICControlBox getRequestPage(Long townId, Long abonentId,
			String deviceGroupMegalineName, long deviceGroup)
			throws BDealerException, BUtilException, BRequestException;

	ICControlBox getRequestTablePage(Long townId, Long userId, Long requestId,
			Long abonentId, Date beginOpenDate, Date endOpenDate,
			Date beginCloseDate, Date endCloseDate, String abonentName,
			String device, boolean showClosedRequests) throws BDealerException,
			BUtilException, BRequestException;

	ICControlBox getRequestStagesTablePage(Long townId, Long orderId)
			throws BDealerException, BRequestException, BUtilException,
			BAbonentException, BRegistrationException, BMailException;
}