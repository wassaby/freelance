package com.realsoft.commons.beans.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.utils.UtilsException;

public interface IBTreeModel {

	String MENU_ITEM = "menu-item";

	String ID = "id";

	String NAME = "name";

	String TYPE = "type";

	String MENU_IMAGE = "image";

	String MENU_TYPE = "type";

	String MENU_STATE = "state";

	String ACTIONS = "actions";

	String ACTION_CLASS = "class";

	String PARAMETERS = "parameters";

	IBTreeNode buildXMLTree() throws CommonsBeansException;

	IBTreeNode refreshXMLTree() throws CommonsBeansException;

	void processTreeNodeList(IBTreeNode treeNode, NodeList nodeList)
			throws CommonsBeansException, DOMException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, UtilsException,
			IllegalArgumentException, SecurityException,
			InvocationTargetException, NoSuchMethodException;

	public List<IBAction> buildAction(Node node) throws DOMException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UtilsException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException;

	void storeValue(String xpath, Object value) throws CommonsBeansException;

}
