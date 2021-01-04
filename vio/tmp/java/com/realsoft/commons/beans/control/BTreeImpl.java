package com.realsoft.commons.beans.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BTreeImpl extends AbstractControl implements IBTree,
		BeanFactoryAware {

	private static Logger log = Logger.getLogger(BTreeImpl.class);

	private BeanFactory beanFactory = null;

	private IBTreeNode treeNode = null;

	public BTreeImpl(String name) {
		super(name);
		treeNode = new BTreeNodeImpl(name);
	}

	public BTreeImpl(String name, IBRequest request) {
		super(name, request);
		this.request = request;
		treeNode = new BTreeNodeImpl(name);
	}

	public BTreeImpl(String name, IBTreeNode treeNode) {
		super(name);
		this.treeNode = treeNode;
		treeNode = new BTreeNodeImpl(name);
	}

	public BTreeImpl(String name, IBRequest request, List<IBControl> dependOn) {
		super(name, request, dependOn);
		this.request = request;
		this.dependOn = dependOn;
		treeNode = new BTreeNodeImpl(name);
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof IBTree) {
			IBTree tree = (IBTree) model;
			setTreeNode(tree.getTreeNode());
		} else if (model instanceof IBTreeNode) {
			setTreeNode((IBTreeNode) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public IBTreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(IBTreeNode treeNode) {
		this.treeNode = treeNode;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTREE_MODEL[ name=").append(name).append(" ]{\n");
		buffer.append(getTreeNode().toString());
		buffer.append("}");
		return buffer.toString();
	}

}
