package com.realsoft.commons.beans.control;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.state.BStateImpl;
import com.realsoft.commons.beans.state.IBState;
import com.realsoft.commons.beans.xml.IBXMLHolder;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.converter.ConverterManager;
import com.realsoft.utils.reflection.ObjectMethodCaller;

public class BTreeModelImpl implements IBTreeModel {

	private static Logger log = Logger.getLogger(BTreeModelImpl.class);

	private IBXMLHolder xmlHolder = null;

	private IBTreeNode treeNode = null;

	private ConverterManager converter = null;

	public BTreeModelImpl() {
	}

	private void destroyAction(IBTreeNode treeNode)
			throws CommonsControlException {
		for (IBAction action : treeNode.getActionList()) {
			action.destroy();
		}
	}

	public void destroy() throws CommonsControlException {
		destroyAction(treeNode);
		for (IBTreeNode node : treeNode.getChildren()) {
			destroyAction(node);
		}
	}

	public IBTreeNode buildXMLTree() throws CommonsBeansException {
		if (treeNode == null) {
			refreshXMLTree();
		}
		return treeNode;
	}

	public IBTreeNode refreshXMLTree() throws CommonsBeansException {
		try {
			org.w3c.dom.Document doc = xmlHolder.getXMLStructure();
			Node rootElement = doc.getFirstChild();
			treeNode = buildNode(rootElement);// new BTreeNodeImpl(name);
			processTreeNodeList(treeNode, doc.getFirstChild().getChildNodes());
			return treeNode;
			// treeNode.setupTree();
		} catch (Exception e) {
			log.error("Could build tree textModel from XML document", e);
			throw new CommonsBeansException(RealsoftConstants.ERR_SYSTEM,
					"commons-control.jbtree.building-xml-tree-textModel.error",
					"Could build tree textModel from XML document", e);
		}
	}

	private IBTreeNode buildNode(Node node) throws DOMException,
			CommonsBeansException, IllegalArgumentException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UtilsException, InvocationTargetException,
			NoSuchMethodException {
		NamedNodeMap nodeMap = node.getAttributes();
		IBTreeNode treeNode = new BTreeNodeImpl(nodeMap.getNamedItem(NAME)
				.getNodeValue());
		String nodeId = nodeMap.getNamedItem(ID).getNodeValue();
		treeNode.setId(nodeId);
		IBState state = new BStateImpl(nodeMap.getNamedItem(MENU_STATE)
				.getNodeValue());
		state.setNodeId(nodeId);
		treeNode.setState(state);
		String imageName = nodeMap.getNamedItem(MENU_IMAGE).getNodeValue();
		treeNode.setBackgroundImage(imageName);
		treeNode.setType(nodeMap.getNamedItem(MENU_TYPE).getNodeValue());
		treeNode.setActionList(buildAction(node));
		log.debug("treeNode = " + treeNode);
		return treeNode;
	}

	public void processTreeNodeList(IBTreeNode treeNode, NodeList nodeList)
			throws CommonsBeansException, DOMException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, UtilsException,
			IllegalArgumentException, SecurityException,
			InvocationTargetException, NoSuchMethodException {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			// log.debug("NodeName = " + node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& node.getNodeName().equals(MENU_ITEM)) {
				IBTreeNode childNode = buildNode(node);
				treeNode.addChild(childNode);
				processTreeNodeList(childNode, node.getChildNodes());
			}
		}
	}

	public List<IBAction> buildAction(Node node) throws DOMException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UtilsException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		List<IBAction> actionList = new LinkedList<IBAction>();

		Node actionsItem = findNode(node, ACTIONS);
		log.debug("actionsItem = " + actionsItem);

		if (actionsItem != null) {
			NodeList actionsNodeList = actionsItem.getChildNodes();
			log.debug("actionsNodeList.getLength() = "
					+ actionsNodeList.getLength());
			for (int i = 0; i < actionsNodeList.getLength(); i++) {
				Node nodeItem = actionsNodeList.item(i);
				if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap nodeMap = nodeItem.getAttributes();
					Class actionTypeClass = Class.forName(nodeMap.getNamedItem(
							ACTION_CLASS).getNodeValue());
					IBAction actionObject = (IBAction) actionTypeClass
							.getConstructor(new Class[] { String.class })
							.newInstance(
									new Object[] { nodeMap.getNamedItem(NAME)
											.getNodeValue() });
					if (nodeMap.getNamedItem(TYPE) != null) {
						actionObject.setType(nodeMap.getNamedItem(TYPE)
								.getNodeValue());
					}

					Node parametersItem = findNode(nodeItem, PARAMETERS);
					if (parametersItem != null) {
						NodeList parameterNodeList = parametersItem
								.getChildNodes();
						if (parameterNodeList != null) {
							for (int j = 0; j < parameterNodeList.getLength(); j++) {
								Node parameter = parameterNodeList.item(j);
								if (parameter.getNodeType() == Node.ELEMENT_NODE) {
									NamedNodeMap parameterAtts = parameter
											.getAttributes();
									String paramName = parameterAtts
											.getNamedItem(NAME).getNodeValue();
									Class parameterType = Class
											.forName(parameterAtts
													.getNamedItem(TYPE)
													.getNodeValue());
									if (parameter.getFirstChild() != null) {
										if (parameterType.equals(List.class)) {
											List list = new LinkedList();
											NodeList complexParameterList = parameter
													.getChildNodes();
											for (int k = 0; k < complexParameterList
													.getLength(); k++) {
												Node complexParameterItem = complexParameterList
														.item(k);
												if (complexParameterItem
														.getNodeType() == Node.ELEMENT_NODE) {
													NamedNodeMap complexParameterAttrs = complexParameterItem
															.getAttributes();
													Class type = Class
															.forName(complexParameterAttrs
																	.getNamedItem(
																			TYPE)
																	.getNodeValue());
													Object value = converter
															.getObject(
																	complexParameterItem
																			.getFirstChild()
																			.getNodeValue(),
																	type);
													list.add(value);
												}
											}
											ObjectMethodCaller
													.invokeSetterMethod(
															actionObject,
															paramName, list,
															List.class);
										} else if (parameterType
												.equals(Map.class)) {
											Map map = new LinkedHashMap();
											NodeList complexParameter = parameter
													.getChildNodes();
											for (int k = 0; k < complexParameter
													.getLength(); k++) {
												Node complexParameterItem = complexParameter
														.item(k);
												if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {
													NamedNodeMap complexParameterAttrs = complexParameterItem
															.getAttributes();
													String key = complexParameterAttrs
															.getNamedItem(NAME)
															.getNodeValue();
													Class type = Class
															.forName(complexParameterAttrs
																	.getNamedItem(
																			TYPE)
																	.getNodeValue());
													Object value = converter
															.getObject(
																	complexParameterItem
																			.getFirstChild()
																			.getNodeValue(),
																	type);
													map.put(key, value);
												}
											}
											ObjectMethodCaller
													.invokeSetterMethod(
															actionObject,
															paramName, map);
										} else {
											String paramValue = parameter
													.getFirstChild()
													.getNodeValue();
											Object paramObject = converter
													.getObject(paramValue,
															parameterType);
											ObjectMethodCaller
													.invokeSetterMethod(
															actionObject,
															paramName,
															paramObject);
										}
									}
								}
							}
						}
					}
					log.debug("actionObject = " + actionObject);
					actionObject.initialize();
					actionList.add(actionObject);
				}
			}
		}
		log.debug("actionList.size = " + actionList.size());
		return actionList;
	}

	private Node findNode(Node parentNode, String nodeName) {
		NodeList nodeList = parentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeItem = nodeList.item(i);
			if (nodeItem.getNodeType() == Node.ELEMENT_NODE
					&& nodeItem.getNodeName().equals(nodeName)) {
				return nodeItem;
			}
		}
		return null;
	}

	public void storeValue(String xpath, Object value)
			throws CommonsBeansException {
		xmlHolder.storeValue(xpath, value);
	}

	public IBXMLHolder getXmlHolder() {
		return xmlHolder;
	}

	public void setXmlHolder(IBXMLHolder xmlHolder) {
		this.xmlHolder = xmlHolder;
	}

	public ConverterManager getConverter() {
		return converter;
	}

	public void setConverter(ConverterManager converter) {
		this.converter = converter;
	}

}