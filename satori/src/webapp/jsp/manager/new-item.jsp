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
	List langList = (List) session.getAttribute(SatoriConstants.LANGUAGE_LIST);
	List typesList = (List) session.getAttribute(SatoriConstants.ITEM_TYPES);
%>
<div class="row">
	<a href="<%=request.getContextPath() %>/item-list.html" class="btn btn-inverse">Back</a>
	
	<h3>Create new Item</h3>

	<form action="add-new-item.html" method="post">
		<table style="width: 80%">
			<tr>
				<td>
					Select language of new item
				</td>
				<td>
					<select name="langId">
						<%
							for (Iterator iter = langList.iterator(); iter.hasNext();){
							BItemInfo langItem = (BItemInfo) iter.next();	
						%>
							<option value="<%=langItem.getId() %>">
								<%=langItem.getLangName() %> | <%=langItem.getLangCode() %>
							</option>
						<%
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Select type of new item
				</td>
				<td>
					<select name="typeId">
						<%
							for (Iterator iter2 = typesList.iterator(); iter2.hasNext();){
							BItemInfo typeItem = (BItemInfo) iter2.next();	
						%>
							<option value="<%=typeItem.getId() %>">
								<%=typeItem.getName() %>
							</option>
						<%
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					Input name of item
				</td>
				<td>
					<input name="item" type="text" class="input-xxlarge" style="height: 30px" required="required"/>
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

