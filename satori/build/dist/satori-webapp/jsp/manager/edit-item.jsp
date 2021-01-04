<%@page import="com.daurenzg.commons.beans.satori.item.BItemInfo"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@page import="java.util.Iterator"%>
<%@page import="javax.servlet.jsp.tagext.IterationTag"%>
<%@page import="java.util.List"%>
<%@page import="com.teremok.struts.helper.StrutsHelperConstants"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>

<%
	List notificationList = (List) session
			.getAttribute(SatoriConstants.PUSH_UPS);
	BItemInfo itemId = (BItemInfo) session
			.getAttribute(SatoriConstants.ITEM);
	BItemInfo parent = (BItemInfo) session.getAttribute(SatoriConstants.PARENT_ITEM);
	List oneGroupItemList = (List) session.getAttribute(SatoriConstants.ONE_GROUP_ITEM_LIST);
%>

<div class="row">

	<a href="<%=request.getContextPath()%>/item-list.html?item=all" class="btn btn-inverse">Back</a>
					
	<h3>Properties</h3>
	
	<table class="table table-bordered">
		<tr>
			<td>Item name</td>
			<td><%=itemId.getName()%></td>
		</tr>
		<tr>
			<td>Language: <%=itemId.getLangName()%></td>
			<td>
				<a href="<%=request.getContextPath() %>/go-add-item-language.html?itemId=<%=itemId.getId() %>&langId=<%=itemId.getLangId() %>&parentId=<%=itemId.getPid() %>">
					<button class="btn btn-success">Create this item in other language</button>
				</a>
			</td>
		</tr>
		<tr>
			<td>View this item in other available languages:</td>
			<td>
				<ul class="nav nav-pills">
					<%
						String active = "";
						for (Iterator iterLangs = oneGroupItemList.iterator(); iterLangs.hasNext();){ 
							BItemInfo itemLangInfo = (BItemInfo) iterLangs.next(); 
							if (itemLangInfo.getLangId() == itemId.getLangId()){
					%>
								<li class="active">
									<a href="<%=request.getContextPath() %>/go-edit-item.html?itemId=<%=itemLangInfo.getId() %>&langId=<%=itemLangInfo.getLangId() %>"><%=itemLangInfo.getLangName() %></a>
								</li>
						<%
							} else {
						%>
								<li>
									<a href="<%=request.getContextPath() %>/go-edit-item.html?itemId=<%=itemLangInfo.getId() %>&langId=<%=itemLangInfo.getLangId() %>"><%=itemLangInfo.getLangName() %></a>
								</li>
					<%
							};}
					%>
				</ul>
			</td>
		</tr>
		<tr>
			<td>Status: </td>
			<td><% if (itemId.getStatusId() == 1) {%> <span class="label label-success"><%=itemId.getStatusName() %></span><%} else { %><span class="label label-important"><%=itemId.getStatusName() %></span><%} %></td>
		</tr>
		<tr>
			<td>Type: </td>
			<td><% if (itemId.getTypeId() == 1) {%> <span class="label label-success"><%=itemId.getTypeName() %></span><%} else { %><span class="label label-important"><%=itemId.getTypeName() %></span><%} %></td>
		</tr>
		<tr>
			<td>Parent item: </td>
			<td><% if (parent.getName() == null) { %><span class="label label-info"> None </span><%} else { %><%=parent.getName() %><%} %> </td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="<%=request.getContextPath() %>/go-edit-item-property.html?itemId=<%=itemId.getId() %>&langId=<%=itemId.getLangId() %>">
					<button class="btn btn-success">Edit</button>
				</a>
			</td>
		</tr>
	</table>
	
	<div style="height: 15px"></div>
	
	<h3>Notifications by days</h3>
	<table class="table table-bordered">
		<tr>
			<th>Days</th>
			<th>Push-notification count</th>
			<th>Articles</th>
			<th>Action</th>
		</tr>
		<%
			for (Iterator iter = notificationList.iterator(); iter.hasNext();) {
				BItemInfo itemInfo = (BItemInfo) iter.next();
		%>
		<tr>
			<td><%=itemInfo.getDayName()%></td>
			<td><%=itemInfo.getPushUpsCount()%></td>
			<td><%=itemInfo.getContentCount()%></td>
			<td>
				<a href="<%=request.getContextPath() %>/go-edit-day.html?itemId=<%=itemId.getId() %>&dayId=<%=itemInfo.getDayId()%>&langId=<%=itemId.getLangId() %>">
					<button class="btn btn-success">Edit</button>
				</a>
			</td>
		</tr>
		<%
			}
		%>
	</table>
</div>


<!--/row-->

