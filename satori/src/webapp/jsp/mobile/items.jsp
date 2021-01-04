<%@page import="java.io.StringWriter"%>
<%@page import="com.daurenzg.commons.beans.satori.mobile.BArticleItem"%>
<%@page import="com.daurenzg.commons.beans.satori.mobile.BPushItem"%>
<%@page import="com.daurenzg.commons.beans.satori.mobile.BDayItem"%>
<%@page import="com.daurenzg.commons.beans.satori.mobile.BMobileItem"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
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
	List itemList = (List) request.getAttribute(SatoriConstants.MOBILE_ITEMS);
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
			<day-content>
			<%
				for (Iterator iter2 = mobItem.getDayContent().iterator(); iter2.hasNext();){
					BDayItem dayItem = (BDayItem) iter2.next();
			%>
				<days>
					<number><%=dayItem.getDayNumber() %></number>
					<day-name><%=dayItem.getDayName() %></day-name>
					<notifications>
					<%
					if (dayItem.getNotifications() != null) {
						for (Iterator iter3 = dayItem.getNotifications().iterator(); iter3.hasNext();){
							BPushItem pushItem = (BPushItem) iter3.next();
					%>
						<push>
							<id><%=pushItem.getId() %> </id>
							<text><%=StringEscapeUtils.unescapeHtml(pushItem.getText()) %> </text>
							<language><%=pushItem.getLang() %> </language>
							<push-time><%=pushItem.getTime() %> </push-time>
							<content>
								<%
								if (pushItem.getContent() != null){
									for (Iterator iter4 = pushItem.getContent().iterator(); iter4.hasNext();){
										BArticleItem articleItem = (BArticleItem) iter4.next();
								%>
								<article>
									<id><%=articleItem.getId() %></id>
									<text><![CDATA[<%=StringEscapeUtils.unescapeHtml(articleItem.getText()) %>]]></text>
									<language><%=articleItem.getLang() %></language>
									<% if (articleItem.getFileId() == 0 || articleItem.getFileId() == -111){ %>
									<file>No file found.</file>
									<%} else { %>
									<file>http://<%=request.getServerName() %>:<%=request.getServerPort()%>/<%=request.getContextPath() %>/get-image.html?fileId=<%=articleItem.getFileId() %></file>
									<%} %>
								</article>
								<%
									};
								} else {
								%> No content found. <%
								}
								%>
							</content>
						</push>
					<%
						};
					} else {
				
					%>No notifications found.<%
						}
					%>
					</notifications>
				</days>
			<%
				}
			%>
			</day-content>
		</item>
	<%
		}
	%>
	</item-list>
</response>