package com.realsoft.commons.beans.control;

public interface IBTree extends IBControl {

	IBTreeNode getTreeNode() throws CommonsControlException;

	void setTreeNode(IBTreeNode treeNode) throws CommonsControlException;

}
