<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>

<div class="well sidebar-nav">
	<ul class="nav nav-list">
		<li class="nav-header">Items</li>
		<li><a href="<%=request.getContextPath() %>/item-list.html">List of all items</a></li>
		<li class="nav-header">Items by status</li>
		<li><a href="<%=request.getContextPath() %>/item-list.html?item=active">List of active items</a></li>
		<li><a href="<%=request.getContextPath() %>/item-list.html?item=archive">List of archive items</a></li>
		<li class="nav-header">Items by types</li>
		<li><a href="<%=request.getContextPath() %>/item-list.html?item=positive">List of positive items</a></li>
		<li><a href="<%=request.getContextPath() %>/item-list.html?item=negative">List of negative items</a></li>
	</ul>
</div>

