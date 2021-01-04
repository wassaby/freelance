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
	BCommonItemInfo item = (BCommonItemInfo) session
			.getAttribute(SatoriConstants.NEW_PUSH_UP);
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
%>

<div class="row">
	<a href="<%=request.getContextPath() %>/go-edit-day.html?itemId=<%=item.getItemId() %>&dayId=<%=item.getDayId()%>&langId=<%=item.getLangId() %>" class="btn btn-inverse">Back</a>
	
	<h3>Create new Push-notification</h3>
	
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

	<form action="add-new-push.html" method="post">
		<table style="width: 80%">
			<tr>
				<td>Input name of pushup</td>
				<td>
					<input name="content" type="text" class="input-xxlarge" style="height: 30px" required="required"/> 
					<input name="itemId" type="hidden" value="<%=item.getItemId()%>" /> 
					<input name="dayId" type="hidden" value="<%=item.getDayId()%>" />
					<input name="langId" type="hidden" value="<%=item.getLangId()%>" />
				</td>
			</tr>
			<tr>
				<td>
					Set time (<strong>HH:MM</strong>):
				</td>
				<td>
					<input name="time" type="text" pattern="([01]?[0-9]|2[0-3]):[0-5][0-9]" class="input-mini" style="height: 30px" required="required"/>
				</td>
			</tr>
		</table>
		<div style="height: 15px"></div>
		<table style="width: 20%">
			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

