/*
 * Created on 14.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBTreeNode.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.List;

import com.realsoft.commons.beans.state.IBState;

public interface IBTreeNode extends IBModel, Serializable, IBControl {

	String getName();

	List<IBTreeNode> getChildren() throws CommonsControlException;

	IBTreeNode getChild(String id) throws CommonsControlException;

	void setChildren(List<IBTreeNode> parent) throws CommonsControlException;

	void addChild(IBTreeNode child) throws CommonsControlException;

	void removeChilden() throws CommonsControlException;

	boolean isCollapsed() throws CommonsControlException;

	void setCollapsed(boolean collapsed) throws CommonsControlException;

	IBTreeNode getParent() throws CommonsControlException;

	void setParent(IBTreeNode parent) throws CommonsControlException;

	List<IBAction> getActionList() throws CommonsControlException;

	void setActionList(List<IBAction> action) throws CommonsControlException;

	Object getId();

	void setId(Object id);

	String getType() throws CommonsControlException;

	void setType(String type) throws CommonsControlException;

	void setState(IBState state) throws CommonsControlException;

	void setStateRecursivelly(IBState state) throws CommonsControlException;

	IBState calculateChildState() throws CommonsControlException;

	void setupTree(String nodeId) throws CommonsControlException;

	IBState getState() throws CommonsControlException;

	String getBackgroundImage();

	void setBackgroundImage(String backgroundImage);

	String getBackgroundImageName();

	void copyFrom(IBTreeNode impl) throws CommonsControlException;

	void addActionList(List<IBAction> actions);

}