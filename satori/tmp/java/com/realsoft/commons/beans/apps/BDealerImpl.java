/*
 * Created on 15.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BDealerImpl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.apps;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.abonent.BAbonentDebitItem;
import com.realsoft.commons.beans.abonent.BAbonentException;
import com.realsoft.commons.beans.abonent.BAbonentInfo13;
import com.realsoft.commons.beans.abonent.IBAbonent;
import com.realsoft.commons.beans.account.BAccountException;
import com.realsoft.commons.beans.dealer.control.CComboboxImpl;
import com.realsoft.commons.beans.dealer.control.CControlBoxImpl;
import com.realsoft.commons.beans.dealer.control.CControlException;
import com.realsoft.commons.beans.dealer.control.CLabel;
import com.realsoft.commons.beans.dealer.control.CObject;
import com.realsoft.commons.beans.dealer.control.CRowItem;
import com.realsoft.commons.beans.dealer.control.CTable;
import com.realsoft.commons.beans.dealer.control.ICCombobox;
import com.realsoft.commons.beans.dealer.control.ICControlBox;
import com.realsoft.commons.beans.dealer.control.ICObject;
import com.realsoft.commons.beans.dealer.control.ICTable;
import com.realsoft.commons.beans.inventory.BInventoryException;
import com.realsoft.commons.beans.inventory.IBInventory;
import com.realsoft.commons.beans.mail.BMailException;
import com.realsoft.commons.beans.registration.BRegistrationException;
import com.realsoft.commons.beans.request.BConnectTypeItem;
import com.realsoft.commons.beans.request.BRequestException;
import com.realsoft.commons.beans.request.BRequestOrderStageItem;
import com.realsoft.commons.beans.request.BRequestTableItem;
import com.realsoft.commons.beans.request.IBRequest;
import com.realsoft.commons.beans.urlresolver.BTownItem;
import com.realsoft.commons.beans.urlresolver.BURLResolverException;
import com.realsoft.commons.beans.urlresolver.IBURLResolver;
import com.realsoft.commons.beans.util.BUtilException;
import com.realsoft.commons.beans.util.IBUtil;
import com.realsoft.utils.number.NumberUtils;

public class BDealerImpl implements IBDealer, BeanFactoryAware {

	private BeanFactory manager = null;

	private final static Logger log = Logger.getLogger(BDealerImpl.class);

	public BDealerImpl() {
		super();
	}

	public ICControlBox getLoginPage() throws BDealerException,
			BURLResolverException {
		IBURLResolver resolver = (IBURLResolver) manager
				.getBean(ComponentFactoryImpl.getBUrlResolverName(),
						IBURLResolver.class);
		ICControlBox controlBox = new CControlBoxImpl();
		List<BTownItem> townList = resolver.getTownList();
		ICCombobox combobox = new CComboboxImpl("townList");
		for (BTownItem item : townList) {
			combobox.addComboboxItem(item.getTownId(), item.getTownName());
		}

		controlBox.addControl(combobox);

		return controlBox;
	}

	public ICControlBox getAbonentInformationPage(Long townId, String phone,
			String abonentId, String name) throws BDealerException,
			BAbonentException, BRegistrationException, BAccountException,
			BInventoryException {
		IBAbonent abonent = (IBAbonent) manager.getBean(ComponentFactoryImpl
				.getBAbonentName());
		ICControlBox controlBox = new CControlBoxImpl();
		BAbonentInfo13 abonentInfo = null;
		try {
			if (phone != null && phone.length() != 0 && abonentId != null
					&& abonentId.length() != 0)
				abonentInfo = abonent.getAbonentInfo13ById(townId, phone,
						NumberUtils.getLong(abonentId));
			else if (phone != null && phone.length() != 0 && name != null
					&& name.length() != 0)
				abonentInfo = abonent.getAbonentInfo13ByName(townId, phone,
						name);
			else
				throw new BDealerException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.WEB_DEALER_EXCEPTION,
						"phone and abonent-name could not be null");
		} catch (BRequestException e) {
			throw new BDealerException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.WEB_DEALER_EXCEPTION,
					"Could not get abonent information page", e);
		} catch (BMailException e) {
			throw new BDealerException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.WEB_DEALER_EXCEPTION,
					"Could not get abonent information page", e);
		}

		ICObject object = new CObject("abonentInfo", abonentInfo);

		controlBox.addControl(object);

		IBInventory inventory = (IBInventory) manager
				.getBean(ComponentFactoryImpl.getBInventoryName());
		controlBox.addControl(new CLabel("deviceAvalaibleInfo", inventory
				.checkDeviceAvailable(phone)));

		return controlBox;
	}

	public ICControlBox getAbonentDebitPage(Long townId, String phone,
			String abonentId, String name) throws BDealerException,
			BRegistrationException, BAbonentException, BAccountException,
			BInventoryException, CControlException {
		log.debug("Getting abonent debit-page, townId = " + townId
				+ "; phone = " + phone + "; abonentId = " + abonentId
				+ "; name = " + name);
		IBAbonent abonent = (IBAbonent) manager.getBean(ComponentFactoryImpl
				.getBAbonentName());
		ICControlBox abonentDebitPage = getAbonentInformationPage(townId,
				phone, abonentId, name);
		ICObject object = (ICObject) abonentDebitPage.getControl("abonentInfo");
		BAbonentInfo13 abonentInfo13 = (BAbonentInfo13) object.getObject();
		List<BAbonentDebitItem> abonentDebitList = abonent
				.getAbonentDebitList(abonentInfo13.getAbonentId());
		ICObject abonentDebitObject = new CObject(
				CommonsBeansConstants.ABONENT_DEBIT_OBJECT_NAME,
				abonentDebitList);
		abonentDebitPage.addControl(abonentDebitObject);
		return abonentDebitPage;
	}

	public ICControlBox getRequestPage(Long townId, Long abonentId,
			String deviceGroupMegalineName, long deviceGroup)
			throws BDealerException, BUtilException, BRequestException {
		IBRequest request = (IBRequest) manager.getBean(ComponentFactoryImpl
				.getBRequestName());

		IBUtil util = (IBUtil) manager.getBean(ComponentFactoryImpl
				.getBUtilName());

		ICControlBox controlBox = new CControlBoxImpl();

		Long deviceGroupId = util.getDeviceGroupId(deviceGroupMegalineName);

		ICCombobox connectTypes = new CComboboxImpl("connectTypes");
		List<BConnectTypeItem> connectTypeList = request.getConnectTypes(
				townId, deviceGroupId);
		for (BConnectTypeItem item : connectTypeList) {
			connectTypes.addComboboxItem(item.getConnectTypeId(), item
					.getName());
		}
		controlBox.addControl(connectTypes);

		ICObject devices = new CObject("devices");
		devices
				.setObject(request
						.getDeviceList(townId, abonentId, deviceGroup));

		controlBox.addControl(devices);

		return controlBox;
	}

	public ICControlBox getRequestTablePage(Long townId, Long userId,
			Long requestId, Long abonentId, Date beginOpenDate,
			Date endOpenDate, Date beginCloseDate, Date endCloseDate,
			String abonentName, String device, boolean showClosedRequests)
			throws BDealerException, BUtilException, BRequestException {
		IBRequest request = (IBRequest) manager.getBean(ComponentFactoryImpl
				.getBRequestName());
		ICControlBox controlBox = new CControlBoxImpl();

		ICTable table = new CTable("requestTableInfo");
		List<BRequestTableItem> requestTableInfo = request.getRequestTableInfo(
				userId, requestId, abonentId, beginOpenDate, endOpenDate,
				beginCloseDate, endCloseDate, abonentName, device,
				showClosedRequests);
		log.debug("Returning request-table (after delegation) : ");
		for (BRequestTableItem item : requestTableInfo) {
			log.debug("request: id = " + item.getRequestId() + "; OPEN_DATE = "
					+ item.getOpenDate());
			table.addRowItem(new CRowItem(item.getRequestId(), item));
		}
		controlBox.addControl(table);

		return controlBox;
	}

	public ICControlBox getRequestStagesTablePage(Long townId, Long orderId)
			throws BDealerException, BRequestException, BUtilException,
			BAbonentException, BRegistrationException, BMailException {
		IBRequest request = (IBRequest) manager.getBean(ComponentFactoryImpl
				.getBRequestName());
		IBAbonent abonent = (IBAbonent) manager.getBean(ComponentFactoryImpl
				.getBAbonentName());
		ICControlBox controlBox = new CControlBoxImpl();

		BRequestTableItem requestTableItem = request.getRequestInfo(orderId);
		controlBox
				.addControl(new CObject("requestTableItem", requestTableItem));

		ICTable table = new CTable("requestStagesTableInfo");
		List<BRequestOrderStageItem> requestOrderStageList = request
				.getRequestOrderStagesList(orderId);

		BAbonentInfo13 abonentInfo13 = abonent
				.getAbonentInfo13ByExistingOrder(orderId);

		controlBox.addControl(new CObject("abonentInfo", abonentInfo13));

		long i = 0;
		for (BRequestOrderStageItem item : requestOrderStageList) {
			table.addRowItem(new CRowItem(++i, item));
		}
		controlBox.addControl(table);

		return controlBox;
	}

	/**
	 * Инициализирует переменную <code>beanFactory</code>, которая является
	 * ссылкой на компонентный менеджер
	 * 
	 * @param beanFactory
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.manager = beanFactory;
	}

}
