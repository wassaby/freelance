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
	BItemInfo itemId = (BItemInfo) session
			.getAttribute(SatoriConstants.ITEM);
	List statusList = (List) session
			.getAttribute(SatoriConstants.STATUSES);
	List langList = (List) session
			.getAttribute(SatoriConstants.LANGUAGE_LIST);
	List parentList = (List) session
			.getAttribute(SatoriConstants.PARENT_LANG_ITEM);
	List itemList = (List) session
			.getAttribute(SatoriConstants.ITEM_LIST);
	List typeList = (List) session
			.getAttribute(SatoriConstants.ITEM_TYPES);
%>

<div class="row">
	<a href="<%=request.getContextPath() %>/go-edit-item.html?<%=itemId.getId() %>&langId=<%=itemId.getLangId() %>" class="btn btn-inverse">Back</a>

	<form action="edit-item-property.html" method="post">
		<table style="width: 50%">
			<tr>
				<td>Item name</td>
				<td>
					<input type="text" value="<%=itemId.getName()%>" name="itemName" style="height: 30px" required="required">
					<input name="itemId" type="hidden" value="<%=itemId.getId() %>">
				</td>
			</tr>
			<tr>
				<td>Language:</td>
				<td><%=itemId.getLangName()%> <input type="hidden" name="langId" value="<%=itemId.getLangId() %>"></td>
			</tr>
			<tr>
				<td>Change status:</td>
				<td><select name="statusId">
						<%
							for (Iterator iter = statusList.iterator(); iter.hasNext();) {
								BItemInfo statusItem = (BItemInfo) iter.next();
						%>
						<option value="<%=statusItem.getId()%>"
							<%if (statusItem.getId() == itemId.getStatusId()) {%> selected
							<%}%>>
							<%=statusItem.getName()%>
						</option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Change type:</td>
				<td><select name="typeId">
						<%
							for (Iterator iter2 = typeList.iterator(); iter2.hasNext();) {
								BItemInfo typeItem = (BItemInfo) iter2.next();
						%>
						<option value="<%=typeItem.getId()%>"
							<%if (typeItem.getId() == itemId.getTypeId()) {%> selected
							<%}%>>
							<%=typeItem.getName()%>
						</option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Change parent item:</td>
				<td><select name="parentId">
						<option value="-777">None</option>
						<%
							for (Iterator iter3 = parentList.iterator(); iter3.hasNext();) {
								BItemInfo parentItem = (BItemInfo) iter3.next();
						%>
						<option value="<%=parentItem.getId()%>"	<%if (parentItem.getId() == itemId.getId()){%> disabled="disabled"<%} %>>
							<%=parentItem.getName()%>
						</option>
						<%
							}
						%>
				</select></td>
			</tr>

			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Edit</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--/row-->

