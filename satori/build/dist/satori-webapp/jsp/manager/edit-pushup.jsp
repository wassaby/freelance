<%@page import="com.daurenzg.commons.beans.satori.item.BItemInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.item.BCommonItemInfo"%>
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
	BItemInfo itemId = (BItemInfo) session
			.getAttribute(SatoriConstants.PUSH_UP_ITEM);
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session
			.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
%>

<div class="row">
<a href="<%=request.getContextPath()%>/go-edit-day.html?itemId=<%=commonItemInfo.getItemId()%>&dayId=<%=commonItemInfo.getDayId()%>&langId=<%=commonItemInfo.getLangId()%>" class="btn btn-inverse">Back</a>
	<h3>Edit notification text</h3>

	<table>
		<tr>
			<td>Day # <%=commonItemInfo.getDayId()%></td>
		</tr>
		<tr>
			<td>Language: <%=commonItemInfo.getLanguage()%>
			</td>
		</tr>
		<tr>
			<td>Item: <%=commonItemInfo.getItemName()%>
			</td>
		</tr>
	</table>

	<div style="height: 15px"></div>

	<form action="edit-pushup.html" method="post" >
		<table>
			<tr>
				<td>Notification text:</td>
				<td><textarea name="text" class="input-xxlarge"
						style="height: 30px" required="required"><%=itemId.getText()%></textarea>
					<input type="hidden" name="notificationId"
					value="<%=itemId.getId()%>" /> <input type="hidden" name="langId"
					value="<%=itemId.getLangId()%>" /></td>
			</tr>
			<tr>
				<td>Time (<strong>HH:MM</strong>):</td>
				<td><input value="<%=itemId.getTime()%>" name="time" pattern="([01]?[0-9]{1}|2[0-3]{1}):[0-5]{1}[0-9]{1}"
					type="text" class="input-mini" style="height: 30px"
					required="required" /></td>
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

