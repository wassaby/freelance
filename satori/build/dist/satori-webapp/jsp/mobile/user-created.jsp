<%@page import="com.daurenzg.commons.beans.satori.mobile.BMobileItem"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>
<%@page import="java.util.Map"%>
<%@page import="com.teremok.utils.formatter.IFormatter"%>
<%@page import="com.teremok.commons.beans.IComponentFactory"%>
<%@page import="com.teremok.commons.beans.CommonsBeansConstants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	String errorCode = (String) request.getAttribute(SatoriConstants.LOGIN_ERROR_CODE_ATTR);
	String errorMessage = (String) request.getAttribute(SatoriConstants.LOGIN_ERROR_MESSAGE_ATTR);
	BMobileItem userId = (BMobileItem)request.getAttribute(SatoriConstants.NEW_USER_ATTRS);
%>

<response>
	<error>
		<code>
			<%=errorCode==null?"0":errorCode%>
		</code>
		<message>
			<%=errorMessage==null?"SUCCESS":errorMessage%>
		</message>
	</error>
	<user>
		<user-item>
			<id><%= String.valueOf(userId.getId()) %></id>
			<userDeviceIdentifier><%= String.valueOf(userId.getUnique_identifier()) %></userDeviceIdentifier>
		</user-item>
	</user>
</response>