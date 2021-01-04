<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<div class="well sidebar-nav">
	<ul class="nav nav-list">
		<li class="nav-header">Users</li>
		<li><a href="#">Users of Satori (not done)</a></li>
		<li><a href="#">Google Analytics (not done)</a></li>
		<li class="nav-header">Settings</li>
		<li><a href="<%=request.getContextPath() %>/go-edit-pswd.html">Change password for content-manager</a></li>
		<li class="nav-header">Edit dictionaries</li>
		<li><a href="#langTable">Language dictionary</a></li>
		<li><a href="#statusTable">Item statuses dictionary</a></li>
		<li><a href="#typeTable">Item types dictionary</a></li>
	</ul>
</div>
