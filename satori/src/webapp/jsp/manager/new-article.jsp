<%@page import="com.daurenzg.commons.beans.satori.item.BCommonItemInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.item.BPushUpItem"%>
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
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
%>

<div class="row">
	<a href="<%=request.getContextPath() %>/go-edit-day.html?itemId=<%=commonItemInfo.getItemId() %>&dayId=<%=commonItemInfo.getDayId()%>&langId=<%=commonItemInfo.getLangId() %>" class="btn btn-inverse">Back</a>
	
	<h3>Create new article</h3>

	<table>
		<tr>
			<td>Day # <%=commonItemInfo.getDayId() %></td>
		</tr>
		<tr>
			<td>Language: <%=commonItemInfo.getLanguage() %> </td>
		</tr>
		<tr>
			<td>Item: <%=commonItemInfo.getItemName() %> </td>
		</tr>
	</table>
	
	<div style="height: 15px"></div>
	
	<form action="add-new-article.html" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>Type text of article: </td>
				<td valign="bottom">
					<textarea name="text" id="input" rows="3" style="width: 500px" required="required"></textarea>
					<input type="file" name="file">
					<input name="notificationId" type="hidden" value="<%=request.getParameter("notificationId")%>" /> 
					<input name="langId" type="hidden" value="<%=request.getParameter("langId")%>" />
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

