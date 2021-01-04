/*
 * Created on 25.11.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BLoginMD5Impl.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.login;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.control.IBModelPanel;

public class BLoginMD5Impl implements IBLogin {

	private static Logger log = Logger.getLogger(BLoginMD5Impl.class);

	protected static Map<String, BLoginInfo> cache = null;

	private static final String GROUP = "group";

	private static final String USER = "user";

	private static final String PASSWORD = "password";

	private static final String UID = "user-id";

	private String securityFile = "security.xml";

	protected static Map<String, SecurityItem> securityMap = null;

	public BLoginMD5Impl() {
		super();
		if (cache == null)
			cache = new LinkedHashMap<String, BLoginInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#login(java.lang.String,
	 *      java.lang.String, long)
	 */
	public BLoginInfo login(String userName, String password, long toDateSum)
			throws BLoginException {
		log.info("logging in ...");

		synchronized (cache) {
			if (cache.containsKey(userName)) {
				BLoginInfo loginInfo = (BLoginInfo) cache.get(userName);
				if (loginInfo.getPassword().equals(password))
					return loginInfo;
			}

			String note = null;
			log.debug("user = " + userName);
			long uid = -1;
			if (securityMap.containsKey(userName)) {
				SecurityItem item = (SecurityItem) securityMap.get(userName);
				log.debug("password = " + password);
				log.debug("hexPassword = " + item.getPassword());
				// if (DigestUtils.md5Hex(password).equals(item.getPassword()))
				if (password.equals(item.getPassword()))
					uid = new Long(item.getUid()).longValue();
			}
			log.debug("logining uid = " + uid);

			if (uid >= 0) {
				BLoginInfo loginInfo = new BLoginInfo(userName, password);
				try {
					loginInfo.setAccountId(uid);
				} catch (Exception e) {
					throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
							CommonsBeansConstants.LOGIN_ERROR,
							"Could not get session", e);
				}
				log.info("user with uid = " + uid
						+ " sucessfully connected to the system");
				cache.put(userName, loginInfo);
				log.info("logging in done");
				return loginInfo;
			}
		}
		throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR,
				"Authorisation Exception, not valid user or password");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#login(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public BLoginInfo login(String userName, String password, String serviceName)
			throws BLoginException {
		log.info("logging in userName = " + userName + "; serviceName = "
				+ serviceName + " ...");

		synchronized (cache) {
			if (cache.containsKey(userName)) {
				BLoginInfo loginInfo = (BLoginInfo) cache.get(userName);
				if (loginInfo.getPassword().equals(password))
					return loginInfo;
			}

			String note = null;
			log.debug("user = " + userName);
			long uid = -1;
			if (securityMap.containsKey(userName)) {
				SecurityItem item = (SecurityItem) securityMap.get(userName);
				// if (DigestUtils.md5Hex(password).equals(item.getPassword()))
				// {
				if (password.equals(item.getPassword())) {
					for (PermissionItem permissionItem : item.permissions) {
						if (permissionItem.serviceName.equals(serviceName)) {
							uid = new Long(item.getUid()).longValue();
							break;
						}
					}
				}
			}
			log.debug("logining uid = " + uid);

			if (uid >= 0) {
				BLoginInfo loginInfo = new BLoginInfo(userName, password);
				try {
					loginInfo.setAccountId(uid);
				} catch (Exception e) {
					throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
							CommonsBeansConstants.LOGIN_ERROR,
							"Could not get session", e);
				}
				log.info("user with uid = " + uid
						+ " sucessfully connected to the system");
				cache.put(userName, loginInfo);
				log.info("logging in done");
				return loginInfo;
			}
		}
		throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR,
				"Authorisation Exception, not valid user or password");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#getPassword(java.lang.String)
	 */
	public String getPassword(String userName) throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_ACCESS,
				CommonsBeansConstants.LOGIN_ERROR,
				"It's unpossible to get password from MD5 althoritm");
	}

	public void initialize() throws Exception {
		if (securityMap == null) {
			securityMap = new HashMap<String, SecurityItem>();
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				org.w3c.dom.Document doc = builder.parse(getClass()
						.getClassLoader().getResourceAsStream(securityFile));

				XPath xPath = XPathFactory.newInstance().newXPath();
				NodeList nodes = (NodeList) xPath.evaluate("//security-item",
						doc, XPathConstants.NODESET);
				for (int i = 0; i < nodes.getLength(); i++) {
					String user = null;
					String password = null;
					String userId = null;
					List<String> groups = new LinkedList<String>();
					NodeList childNodes = nodes.item(i).getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node node = childNodes.item(j);
						if (node.getNodeName().equals(USER)) {
							user = node.getTextContent();
						} else if (node.getNodeName().equals(PASSWORD)) {
							password = node.getTextContent();
						} else if (node.getNodeName().equals(UID)) {
							userId = node.getTextContent();
						} else if (node.getNodeName().equals(GROUP)) {
							groups.add(node.getTextContent());
						}
					}
					SecurityItem securityItem = new SecurityItem(groups, user,
							password, userId);
					for (String group : groups) {
						NodeList serviceNames = (NodeList) xPath.evaluate(
								"//permission-item[@group='" + group
										+ "']/service-name", doc,
								XPathConstants.NODESET);
						if (serviceNames.getLength() == 0)
							throw new BLoginException(
									CommonsBeansConstants.ERR_SYSTEM,
									CommonsBeansConstants.LOGIN_ERROR,
									"Security file inconsistent state: wrong group name "
											+ group);
						for (int k = 0; k < serviceNames.getLength(); k++) {
							Node serviceName = serviceNames.item(k);
							securityItem.addPermission(new PermissionItem(
									serviceName.getTextContent()));
						}
					}
					securityMap.put(user, securityItem);
				}

			} catch (Exception e) {
				throw new BLoginException(CommonsBeansConstants.ERR_SYSTEM,
						CommonsBeansConstants.LOGIN_ERROR,
						"Could not parse xml document " + securityFile, e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.realsoft.commons.beans.login.IBLogin#loginMonitoringClient(com.realsoft.commons.beans.control.IBModelPanel)
	 */

	public IBModelPanel loginMonitoringClient(IBModelPanel rootPanel)
			throws BLoginException {
		throw new BLoginException(CommonsBeansConstants.ERR_SYSTEM,
				"current.nethod.not.available",
				"Not available in this implementation");
	}

	private class SecurityItem {
		private List<String> group;

		private String user;

		private String password;

		private String uid;

		private List<PermissionItem> permissions = new LinkedList<PermissionItem>();

		public SecurityItem(List<String> group, String user, String password,
				String uid) {
			super();
			this.group = group;
			this.user = user;
			this.password = password;
			this.uid = uid;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public void addPermission(PermissionItem permissionItem) {
			permissions.add(permissionItem);
		}
	}

	private class PermissionItem {
		private String serviceName;

		public PermissionItem(String serviceName) {
			super();
			this.serviceName = serviceName;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

	}

	public String getSecurityFile() {
		return securityFile;
	}

	public void setSecurityFile(String securityFile) {
		this.securityFile = securityFile;
	}

}
