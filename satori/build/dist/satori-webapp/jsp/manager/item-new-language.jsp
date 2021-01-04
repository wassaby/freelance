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
	List statusList = (List) session.getAttribute(SatoriConstants.STATUSES);
	BItemInfo itemInfo = (BItemInfo) session.getAttribute(SatoriConstants.ITEM);
	List oneGroupItemList = (List) session.getAttribute(SatoriConstants.ONE_GROUP_ITEM_LIST);
%>
<div class="row">
	<a href="<%=request.getContextPath() %>/go-edit-item.html?go-add-item-language.html?itemId=<%=itemInfo.getId() %>&langId=<%=itemInfo.getLangId() %>&parentId=<%=itemInfo.getPid() %>" class="btn btn-inverse">Back</a>
	
	<h3>Create new Item</h3>

	<form action="new-language-item.html" method="post">
	
	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>Note!</h4>
		You are trying to rewrite item <strong><%=itemInfo.getName() %></strong> in other language. Choose it from dropdown below.<br>
	</div>
	
	<table style="width: 80%">
		<tr>
			<td>
				Choose language
			</td>
			<td>
				<select name="langId" style="width: 300px">
					<%
						for (Iterator iter = langList.iterator(); iter.hasNext();){
							BItemInfo langItem = (BItemInfo) iter.next();
							boolean disabled = false;
							for (Iterator iterIn = oneGroupItemList.iterator(); iterIn.hasNext();){
								BItemInfo innerItemInfo = (BItemInfo) iterIn.next();
								if (innerItemInfo.getLangId() == langItem.getId()) {
									disabled = true;
									continue;
								}
							}
							
					%>
						<option value="<%=langItem.getId() %>" <%if (disabled){ %>disabled="disabled" <%}; %>>
							<%=langItem.getLangName() %> | <%=langItem.getLangCode() %><%if (disabled){ %> Item on this language has already exist <%} %>
						</option>
					<%
						}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Choose status
			</td>
			<td>
				<select name="statusId">
					<%
						for (Iterator iter2 = statusList.iterator(); iter2.hasNext();){
						BItemInfo statusItem = (BItemInfo) iter2.next();	
					%>
						<option value="<%=statusItem.getId() %>">
							<%=statusItem.getName() %>
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
				<input name="itemName" type="text" class="input-xxlarge" style="height: 30px" required="required"/>
				<input name="parentId" type="hidden" value="<%=itemInfo.getPid() %>">
				<input name="itemId" type="hidden" value="<%=itemInfo.getId() %>">
				<input name="typeId" type="hidden" value="<%=itemInfo.getTypeId() %>">
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

