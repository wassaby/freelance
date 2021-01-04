<%@page import="com.daurenzg.commons.beans.satori.item.BCommonItemInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.item.BPushUpItem"%>
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

<script>
	function op(obj) {
		x=document.getElementById(obj);
		if (x.style.display == "none") 
			x.style.display = "inline";
		else 
			x.style.display = "none";
	}
</script>

<%
	List notificationList = (List) session
			.getAttribute(SatoriConstants.DAY_PUSH_UPS);
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
	long itemId = commonItemInfo.getItemId();
	long dayId = commonItemInfo.getDayId();
	long langId = commonItemInfo.getLangId();
%>

<div class="row">
	<a href="<%=request.getContextPath() %>/go-edit-item.html?<%=itemId %>&langId=<%=langId %>" class="btn btn-inverse">Back</a>
	<div style="height: 10px"></div>
	<table class="table table-bordered">
		<tr>
			<td>Day # <%=dayId %></td>
		</tr>
		<tr>
			<td>Language: <%=commonItemInfo.getLanguage() %> </td>
		</tr>
		<tr>
			<td>Item: <%=commonItemInfo.getItemName() %> </td>
		</tr>
	</table>
	
	<div style="height: 15px"></div>
	
	<h3>Notifications of day # <%=dayId %></h3>
	<table class="table table-bordered">
		<tr>
			<th>ID</th>
			<th>Text</th>
			<th>Post</th>
			<th>Popup time</th>
			<th>Create date</th>
			<th></th>
		</tr>
		<%
			for (Iterator iter = notificationList.iterator(); iter.hasNext();) {
				BPushUpItem pushInfo = (BPushUpItem) iter.next();
				BItemInfo itemInfo = pushInfo.getItemInfo();
				List articles = pushInfo.getListItemInfo();
		%>
		<tr>
			<td><%=itemInfo.getId()%></td>
			<td><%=itemInfo.getText()%></td>
			<td>
			<% if (articles.size() != 0) {%>
				<table style="border: 0px;">
					<% 
						int i = 0;
						for (Iterator iter2 = articles.iterator(); iter2.hasNext();){
							BItemInfo articleItem = (BItemInfo) iter2.next();
						i++;	
					%>
					<tr>
						<td style="width: 500px">
							<%if (articleItem.getText().length() > 25) {%>
								<%=articleItem.getText().substring(0, 25).trim() %><span id="span<%=i %>" style="display:none;"><%=articleItem.getText().substring(25) %></span>
								<a href="#" onClick="op('span<%=i %>'); return false;">Show / Hide</a>
							<%} else { %>
								<%=articleItem.getText() %>
							<%} %>
							<br>
							<a href="<%=request.getContextPath() %>/go-edit-article.html?articleId=<%=articleItem.getId() %>&langId=<%=langId %>">
								<button class="btn btn-success">Edit article</button>
							</a><br>
							<a href="<%=request.getContextPath() %>/go-delete-article.html?articleId=<%=articleItem.getId() %>&fileId=<%=articleItem.getFileId() %>">
								<button class="btn btn-danger">Delete article</button>
							</a>
						</td>
						<td><% if (articleItem.getFileId() == 0 || articleItem.getFileId() == -111) {%>
							<img src="<%=request.getContextPath()%>/img/no-image.png" class="img-polaroid"><br>
							<a href="<%=request.getContextPath() %>/go-change-article-image.html?articleId=<%=articleItem.getId() %>">
								<button class="btn btn-success">Add image</button>
							</a>
						<%} else {%>
							<img src="<%=request.getContextPath()%>/get-image.html?fileId=<%=articleItem.getFileId() %>" class="img-polaroid"><br>
							<a href="<%=request.getContextPath() %>/go-change-article-image.html?articleId=<%=articleItem.getId() %>&fileId=<%=articleItem.getFileId() %>">
								<button class="btn btn-success">Change image</button>
							</a><br>
							<a href="<%=request.getContextPath() %>/go-delete-article-image.html?articleId=<%=articleItem.getId() %>&fileId=<%=articleItem.getFileId() %>">
								<button class="btn btn-danger">Delete image</button>
							</a>
							<%}%>
						</td>
					</tr>
					<%
						} 
					%>
					<tr>
						<td colspan="2">
							<a href="<%=request.getContextPath() %>/go-add-article.html?notificationId=<%=itemInfo.getId() %>&langId=<%=langId %>">
								<button class="btn btn-primary">Add post</button>
							</a>
						</td>
					</tr>
				</table>
			<%} else { %>
				<a href="<%=request.getContextPath() %>/go-add-article.html?notificationId=<%=itemInfo.getId() %>&langId=<%=langId %>"><button class="btn btn-primary">Add post</button></a>
			<%} %>
			</td>
			<td><%=itemInfo.getTime() %> </td>
			<td><%=itemInfo.getInsertDate()%></td>
			<td>
				<a href="<%=request.getContextPath() %>/go-edit-pushup.html?notificationId=<%=itemInfo.getId() %>&langId=<%=langId %>">
					<button class="btn btn-success">Edit</button>
				</a>
				<a href="<%=request.getContextPath() %>/go-delete-pushup.html?notificationId=<%=itemInfo.getId() %>&langId=<%=langId %>">
					<button class="btn btn-danger">Delete</button>
				</a>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	
	<table style="width: 20%">
		<tr>
			<td>
				<a href="<%=request.getContextPath() %>/new-pushup.html?itemId=<%=itemId %>&dayId=<%=dayId %>&langId=<%=langId %>">
					<button class="btn btn-primary">Add</button>
				</a>
			</td>
		</tr>
	</table>
	
</div>


<!--/row-->

