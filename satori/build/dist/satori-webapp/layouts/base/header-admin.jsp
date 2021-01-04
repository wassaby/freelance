<%@page import="com.daurenzg.commons.beans.satori.login.BLoginInfo"%>
<%@page import="com.teremok.struts.helper.StrutsHelperConstants"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	BLoginInfo loginInfo = (BLoginInfo) session.getAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR);
%>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand" href="<%=request.getContextPath() %>/item-list.html">Satori</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					Logged in as <a href="#" class="navbar-link"><strong><%=loginInfo.getAccountTypeName() %></strong>,</a>
					<a href="<%=request.getContextPath() %>/logout.html">Logout</a>
				</p>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>
