/*
 * Created on 15.08.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTreeNodeImpl.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.realsoft.commons.beans.state.BStateImpl;
import com.realsoft.commons.beans.state.IBState;

public class BTreeNodeImpl implements IBTreeNode {

	private static Logger log = Logger.getLogger(BTreeNodeImpl.class);

	private static final long serialVersionUID = 1L;

	private String name = null;

	private IBState state = IBState.stateOk;

	private List<IBTreeNode> children = new LinkedList<IBTreeNode>();

	private IBTreeNode parent = null;

	private boolean collapsed = true;

	private transient List<IBAction> actionList = new LinkedList<IBAction>();

	private transient List<IBControl> dependOn = new LinkedList<IBControl>();

	private IBRequest request = null;

	private Object id = null;

	private String type = null;

	private String backgroundImage = null;

	private Object value = null;

	private Boolean isDurty = null;

	private Boolean isEnabled = null;

	private Integer foreground = null;

	private Integer background = null;

	private Integer initialBackground = null;

	private Integer initialForeground = null;

	public BTreeNodeImpl(String name) {
		this.name = name;
	}

	public void addChild(IBTreeNode child) {
		for (IBTreeNode node : children) {
			if (node.equals(child)) {
				node = child;
				return;
			}
		}
		children.add(child);
	}

	public List<IBTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<IBTreeNode> children) {
		this.children = children;
	}

	public IBTreeNode getParent() {
		return parent;
	}

	public void setParent(IBTreeNode parent) {
		this.parent = parent;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public List<IBAction> getActionList() {
		return actionList;
	}

	public void setActionList(List<IBAction> action) {
		actionList = action;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BTreeNodeImpl) {
			copyFrom((BTreeNodeImpl) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public IBTreeNode getChild(String id) throws CommonsControlException {
		log.debug("gettingChild of id = " + id);
		if (id.indexOf("/", 1) != -1) {
			id = id.substring(id.indexOf("/", 1));
			String childName = id.indexOf("/", 1) != -1 ? id.substring(0, id
					.indexOf("/", 1)) : id;
			for (IBTreeNode childNode : children) {
				if (childNode.getId().equals(childName)) {
					return childNode.getChild(id);
				}
			}
		} else if (this.id.equals(id)) {
			return this;
		}
		return null;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTREE_NODE[ id=").append(id).append(", name=").append(
				name).append(", state=").append(state.toString()).append(
				"]\n\t").append("{");
		for (IBTreeNode node : children) {
			buffer.append(node.toString()).append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public void copyFrom(IBTreeNode impl) throws CommonsControlException {
		setBackgroundImage(impl.getBackgroundImage());
		setCollapsed(impl.isCollapsed());
		setId(impl.getId());
		setParent(impl.getParent());
		setState(impl.getState());
		setType(impl.getType());

		List<IBTreeNode> children = new LinkedList<IBTreeNode>();
		for (IBTreeNode node : impl.getChildren()) {
			IBTreeNode child = new BTreeNodeImpl(node.getName());
			child.copyFrom(node);
			children.add(child);
		}
		setChildren(children);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IBState getState() {
		return state;
	}

	public void setState(IBState state) throws CommonsControlException {
		this.state = state;
		// setStateOfParentNodes();
	}

	public void setStateRecursivelly(IBState state)
			throws CommonsControlException {
		// log.debug("curr node id = " + getId()+"; this.state.nodeId =
		// "+this.state.getNodeId()+"; state.nodeId = "+state.getNodeId());
		log.debug("curr node id = " + getId() + "; state.nodeId = "
				+ state.getNodeId() + "; state.name = " + state.getName());
		this.state.copyOf(state);
		setStateOfParentNodes();
	}

	private void setStateOfParentNodes() throws CommonsControlException {
		IBTreeNode parentNode = getParent();
		if (parentNode != null) {
			log.debug("parentNode.id = " + parentNode.getId()
					+ "; state.nodeId = " + parentNode.getState().getNodeId());
			IBState state = parentNode.calculateChildState();
			parentNode.getState().copyOf(state);
			parentNode.setStateRecursivelly(state);
		}
	}

	public IBState calculateChildState() throws CommonsControlException {
		log.debug("child id = " + getId() + "; NodeId = "
				+ getState().getNodeId());
		IBState state = getState();
		Iterator<IBTreeNode> childIterator = getChildren().iterator();
		if (childIterator.hasNext()) {
			state = new BStateImpl(IBState.STATE_OK);
			for (IBTreeNode child : getChildren()) {
				log.debug("child.nodeId = " + child.getState().getNodeId());
				IBState childState = child.calculateChildState();
				state.setName(BStateImpl.multiplyState(state, childState));
				state.getDescription().addAll(childState.getDescription());
			}
			state.setNodeId(getState().getNodeId());
		}
		return state;
	}

	public void setupTree(String nodeId) throws CommonsControlException {
		for (IBTreeNode childNode : getChildren()) {
			childNode.setParent(this);
			childNode.getState().setNodeId(
					nodeId + childNode.getId().toString());
			childNode.setupTree(nodeId + childNode.getId().toString());
		}
	}

	public void addActionList(List<IBAction> actions) {
		for (IBAction action : actions) {
			if (action.getType().equals(type)) {
				actionList.add(action);
			}
		}
		for (IBTreeNode node : children) {
			node.addActionList(actions);
		}
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getBackgroundImageName() {
		if (state != null) {
			return state.getName() + backgroundImage;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object obj) {
		try {
			if (super.equals(obj)) {
				return true;
			}
			if (obj instanceof IBTreeNode) {
				IBTreeNode node = (IBTreeNode) obj;
				if (node.getId() != null
						&& node.getId().equals(getId())
						&& (node.getParent() != null
								&& node.getParent().equals(getParent()) || node
								.getParent() == null
								&& getParent() == null)) {
					return true;
				}
			}
			return false;
		} catch (CommonsControlException e) {
			log.error("Could not check if this object equals to othor", e);
			return false;
		}
	}

	public void removeChilden() throws CommonsControlException {
		children = new LinkedList<IBTreeNode>();
	}

	public void setName(String name) {

	}

	public List<IBControl> getDependOn() throws CommonsControlException {
		return dependOn;
	}

	public void setDependOn(List<IBControl> dependOn)
			throws CommonsControlException {
		this.dependOn = dependOn;
	}

	public IBRequest getRequest() throws CommonsControlException {
		return request;
	}

	public void setRequest(IBRequest request) throws CommonsControlException {
		this.request = request;
	}

	public Object getValue() throws CommonsControlException {
		return value;
	}

	public void setValue(Object value) throws CommonsControlException {
		this.value = value;
	}

	public boolean isDurty() throws CommonsControlException {
		return isDurty();
	}

	public void setDurty(boolean isDurty) throws CommonsControlException {
		this.isDurty = isDurty;
	}

	public boolean isEnabled() throws CommonsControlException {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) throws CommonsControlException {
		this.isEnabled = isEnabled;
	}

	public void setForeground(Integer color) throws CommonsControlException {
		foreground = color;
	}

	public Integer getForeground() throws CommonsControlException {
		return foreground;
	}

	public void setBackground(Integer color) throws CommonsControlException {
		background = color;
	}

	public Integer getBackground() throws CommonsControlException {
		return background;
	}

	public void setInitialBackground(Integer color)
			throws CommonsControlException {
		initialBackground = color;
	}

	public Integer getInitialBackground() throws CommonsControlException {
		return initialBackground;
	}

	public void setInitialForeground(Integer color)
			throws CommonsControlException {
		initialForeground = color;
	}

	public Integer getInitialForeground() throws CommonsControlException {
		return initialForeground;
	}

	public void clean() throws CommonsControlException {
		setActionList(new LinkedList<IBAction>());
		setBackgroundImage(null);
		setChildren(new LinkedList<IBTreeNode>());
		setDurty(true);
		setEnabled(true);
		setForeground(null);
		setInitialBackground(initialBackground);
		setInitialForeground(initialForeground);
		setValue(null);
	}

	public void copyFrom(IBControl control) throws CommonsControlException {
	}

}