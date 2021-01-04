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
			.getAttribute(SatoriConstants.ARTICLE_ITEM);
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
%>

<div class="row">
	
	<a href="<%=request.getContextPath() %>/go-edit-day.html?itemId=<%=commonItemInfo.getItemId() %>&dayId=<%=commonItemInfo.getDayId()%>&langId=<%=commonItemInfo.getLangId() %>" class="btn btn-inverse">Back</a>
	
	<h3>Edit article</h3>

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

	<form action="edit-article.html" method="post">
		<table>
			<tr>
				<td>Article text:</td>
				<td><textarea name="text" id="input" class="input-xxlarge"
						style="height: 30px" required="required"><%=itemId.getText()%></textarea>
				<input type="hidden" name="articleId" value="<%=itemId.getId() %>"/>
				<input type="hidden" name="langId" value="<%=itemId.getLangId() %>"/>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/go-edit-day.html?itemId=<%=commonItemInfo.getItemId() %>&dayId=<%=commonItemInfo.getDayId()%>&langId=<%=commonItemInfo.getLangId() %>" class="btn btn-inverse">Back</a>
				</td>
			</tr>
		</table>

	</form>
</div>


<!--/row-->

