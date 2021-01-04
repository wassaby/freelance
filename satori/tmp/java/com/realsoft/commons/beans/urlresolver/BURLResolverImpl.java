package com.realsoft.commons.beans.urlresolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BURLResolverImpl implements IBURLResolver {

	private static Logger log = Logger.getLogger(BURLResolverImpl.class);

	protected static List<BCodeUrlNode> codeUrlList = null;

	public BURLResolverImpl() {
		super();
	}

	private String getCode(String phone) throws BURLResolverException {
		String code = phone;
		while (code != null && code.length() != 0) {
			if (isCodeExists(code))
				return code;
			code = code.substring(0, code.length() - 1);
		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No url association with phone " + phone);
	}

	private boolean isCodeExists(String code) {
		for (Iterator<BCodeUrlNode> iterator = codeUrlList.iterator(); iterator
				.hasNext();) {
			BCodeUrlNode node = iterator.next();
			if (inRange(node.getMin(), node.getMax(), getPhoneByCode(code))){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getUriByPhone(java.lang.String)
	 */
	public String getUriByPhone(String phone) throws BURLResolverException {
		for (Iterator<BCodeUrlNode> iterator = codeUrlList.iterator(); iterator
				.hasNext();) {
			BCodeUrlNode node = iterator.next();
//			if (node.getCode().equals(getCode(phone))) {
//				return node.getUri();
//			}
			if (inRange(node.getMin(), node.getMax(), phone)){
				return node.getUri();
			}
		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No url association with phone " + phone);
	}
	
	private boolean inRange(String min, String max, String phone){
		Long minValue = Long.parseLong(min);
		Long maxValue = Long.parseLong(max);
		Long currentValue = Long.parseLong(phone);
		if ((minValue<=currentValue)&&(maxValue>=currentValue)){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getHostByPhone(java.lang.String)
	 */
	public String getHostByPhone(String phone) throws BURLResolverException {
		for (Iterator<BCodeUrlNode> iterator = codeUrlList.iterator(); iterator
				.hasNext();) {
			BCodeUrlNode node = iterator.next();
			if (inRange(node.getMin(), node.getMax(), phone)){
				return node.getUri();
			}
		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No host association with phone " + phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getUriByCode(java.lang.String)
	 */
	public String getUriByCode(String code) throws BURLResolverException {
		for (Iterator<BCodeUrlNode> iterator = codeUrlList.iterator(); iterator
				.hasNext();) {
			BCodeUrlNode node = iterator.next();
			if (inRange(node.getMin(), node.getMax(), getPhoneByCode(code))){
				return node.getUri();
			}

		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No url association with code " + code);
	}
	private String getPhoneByCode(String code){
		String zero = "";
		for (int i=0;i<10-code.length();i++){
			zero = zero+"0";
		}
		return code+zero;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getUriByTownId(long)
	 */
	public String getUriByTownId(long townId) throws BURLResolverException {
		for (Iterator<BCodeUrlNode> iterator = codeUrlList.iterator(); iterator
				.hasNext();) {
			BCodeUrlNode node = iterator.next();
			if (node.getTownId() == townId)
				return node.getUri();
		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No url association with town id " + townId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getHostByTownId(long)
	 */
	public String getHostByTownId(long townId) throws BURLResolverException {
		for (Iterator iterator = codeUrlList.iterator(); iterator.hasNext();) {
			BCodeUrlNode node = (BCodeUrlNode) iterator.next();
			if (node.getTownId() == townId)
				return node.getHost();
		}
		throw new BURLResolverException(CommonsBeansConstants.ERR_PHONE,
				CommonsBeansConstants.URL_RESOLVER_ERROR,
				"No host association with town id " + townId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getTownList()
	 */
	public List<BTownItem> getTownList() throws BURLResolverException {
		List<BTownItem> list = new ArrayList<BTownItem>();
		for (Iterator<BCodeUrlNode> iter = codeUrlList.iterator(); iter
				.hasNext();) {
			BCodeUrlNode node = iter.next();
			if (!isTownExist(list, node.getTownName()))
				list.add(new BTownItem(node.getTownId(), node.getTownName()));
		}
		return list;
	}

	private boolean isTownExist(List<BTownItem> townList, String town) {
		for (Iterator<BTownItem> iter = townList.iterator(); iter.hasNext();) {
			BTownItem item = iter.next();
			if (item.getTownName().equals(town))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getAllUri()
	 */
	public List<String> getAllUri() throws BURLResolverException {
		List<String> list = new ArrayList<String>();
		for (Iterator<BCodeUrlNode> iter = codeUrlList.iterator(); iter
				.hasNext();) {
			BCodeUrlNode node = iter.next();
			if (!list.contains(node.getUri()))
				list.add(node.getUri());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.urlresolver.IBURLResolver#getAllUri(java.util.List)
	 */
	public Map<String, Object> getAllUri(List<String> phones)
			throws BURLResolverException {
		Map<String, Object> uriMap = new HashMap<String, Object>();
		for (Iterator iter = phones.iterator(); iter.hasNext();) {
			String phone = (String) iter.next();
			String uri = (String) getUriByPhone(phone);
			log.debug("phone = " + phone + "; uri = " + uri);
			if (uriMap.containsKey(uri)) {
				List<String> phoneList = (List<String>) uriMap.get(uri);
				phoneList.add(phone);
			} else {
				List<String> phoneList = new ArrayList<String>();
				phoneList.add(phone);
				uriMap.put(uri, phoneList);
			}
		}
		for (Iterator iter = uriMap.keySet().iterator(); iter.hasNext();) {
			String uri = (String) iter.next();
			List<String> phoneList = (List<String>) uriMap.get(uri);
			String[] phoneArray = new String[phoneList.size()];
			phoneArray = phoneList.toArray(phoneArray);
			uriMap.put(uri, phoneArray);
		}
		return uriMap;
	}

	private String phoneFile = "code-url-mapping.xml";

	private static final String MAP_ITEM_CODE = "code";

	private static final String MAP_ITEM_URI_ATT = "uri";

	private static final String MAP_ITEM_HOST_ATT = "host";
	/**
	 * Минимальное значение диапазона телефонных номеров
	 */
	private static final String MAP_ITEM_MIN_ATT = "min";
	/**
	 * Максимальное значение диапазона телефонных номеров
	 */
	private static final String MAP_ITEM_MAX_ATT = "max";

	private static final String MAP_ITEM_TOWN_NAME_ATT = "town-name";

	private static final String MAP_ITEM_TOWN_ID_ATT = "town-id";

	public void initialize() throws Exception {
		if (codeUrlList == null) {
			log.debug("initializing ...");
			codeUrlList = new ArrayList<BCodeUrlNode>();
			String mappingFile = null;
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				Document doc = builder.parse(getClass().getClassLoader()
						.getResourceAsStream(phoneFile));

				XPath xPath = XPathFactory.newInstance().newXPath();
				NodeList nodes = (NodeList) xPath.evaluate("//map-item", doc,
						XPathConstants.NODESET);
				for (int i = 0; i < nodes.getLength(); i++) {
					String townName = null;
					long townId = 0;
					String uri = null;
					String host = null;
					NamedNodeMap attributes = nodes.item(i).getAttributes();
					for (int j = 0; j < attributes.getLength(); j++) {
						Node node = attributes.item(j);
						if (node.getNodeName().equals(MAP_ITEM_URI_ATT)) {
							uri = node.getTextContent();
						} else if (node.getNodeName().equals(MAP_ITEM_HOST_ATT)) {
							host = node.getTextContent();
						} else if (node.getNodeName().equals(
								MAP_ITEM_TOWN_NAME_ATT)) {
							townName = node.getTextContent();
						} else if (node.getNodeName().equals(
								MAP_ITEM_TOWN_ID_ATT)) {
							townId = Long.parseLong(node.getTextContent());
						}
					}
					NodeList ranges = nodes.item(i).getChildNodes();
					for (int j = 0; j < ranges.getLength(); j++) {
						String min = null;
						String max = null;
						NamedNodeMap attrs = ranges.item(j).getAttributes();
						if (attrs!=null){
							for (int k=0;k<attrs.getLength();k++){
								if (attrs.item(k).getNodeName().equals(MAP_ITEM_MIN_ATT)){
									min = attrs.item(k).getTextContent();
								}
								if (attrs.item(k).getNodeName().equals(MAP_ITEM_MAX_ATT)){
									max = attrs.item(k).getTextContent();
								}
							}
							codeUrlList.add(new BCodeUrlNode(townName, townId,
									uri, host, min,max));
						}
					}
				}
			} catch (Exception e) {
				throw new BURLResolverException(
						CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.URL_RESOLVER_ERROR,
						"Could not parse xml document " + mappingFile, e);
			}
		}
	}

	public String getPhoneFile() {
		return phoneFile;
	}

	public void setPhoneFile(String phoneFile) {
		this.phoneFile = phoneFile;
	}

}