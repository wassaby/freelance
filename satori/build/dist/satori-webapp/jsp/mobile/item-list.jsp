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
	List itemList = (List)request.getAttribute(SatoriConstants.MOBILE_ITEM_LIST);
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
	<item-list>
	<% 
		for (Iterator iter = itemList.iterator(); iter.hasNext();){
			BMobileItem mobItem = (BMobileItem) iter.next();		
	%>
		<item>
			<id><%=String.valueOf(mobItem.getId()) %></id>
			<lang-id><%=String.valueOf(mobItem.getLangId()) %></lang-id>
			<lang-name><%=mobItem.getLangName() %></lang-name>
			<lang-code><%=mobItem.getLangCode() %></lang-code>
			<name><%= mobItem.getItemName() %></name>
			<pid><%= String.valueOf(mobItem.getPid()) %></pid>
			<status-id><%= String.valueOf(mobItem.getItemStatusId()) %></status-id>
			<status-name><%= mobItem.getItemStatusName()%></status-name>
			<type-id><%= String.valueOf(mobItem.getItemTypeId()) %></type-id>
			<type-name><%= mobItem.getItemTypeName() %></type-name>
		</item>
	<%
		}
	%>
	</item-list>
</response>