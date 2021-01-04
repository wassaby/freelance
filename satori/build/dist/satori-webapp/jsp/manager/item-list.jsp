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

<script type="text/javascript">
	
</script>

<%
	List itemList = (List) session
			.getAttribute(SatoriConstants.ITEM_LIST);
%>

<div class="row">

	<table class="table table-bordered">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Languages</th>
				<th>Push-notifications</th>
				<th>Date</th>
				<th>Parent ID <a href="#" rel="popover" data-placement="bottom" title="" data-content="Parent ID is the ID of item which must be finished by user before taking this item." data-original-title="Help">
								<i class="icon-question-sign"></i>
							  </a>
				</th>
				<th>Content</th>
				<th>Files</th>
				<th>Status</th>
				<th colspan="2"></th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
					BItemInfo itemInfo = (BItemInfo) iterator.next();
					String trClass = (itemInfo.getTypeId() == 1) ? "success"
							: "error";
			%>
			<tr class="<%=trClass%>" style="cursor: pointer;"
				onclick="window.location = '<%=request.getContextPath()%>/go-edit-item.html?itemId=<%=itemInfo.getId()%>&langId=<%=itemInfo.getLangId()%>'">
				<td><%=itemInfo.getId()%></td>
				<td><%=itemInfo.getName()%></td>
				<td><%=itemInfo.getLanguages()%></td>
				<td><%=itemInfo.getPushUpsCount()%></td>
				<td><%=itemInfo.getInsertDate()%></td>
				<td><%=itemInfo.getPid()%></td>
				<td><%=itemInfo.getContentCount()%></td>
				<td><%=itemInfo.getFilesCount()%></td>
				<td><%=itemInfo.getStatusName()%></td>
				<td><a
					href="<%=request.getContextPath()%>/go-edit-item.html?itemId=<%=itemInfo.getId()%>&langId=<%=itemInfo.getLangId()%>"><button
							class="btn btn-success">Edit</button></a></td>
				<td><a
					href="<%=request.getContextPath()%>/go-move-item-trash.html?itemId=<%=itemInfo.getId()%>&langId=<%=itemInfo.getLangId()%>"><button
							class="btn btn-danger">Move to archive</button></a></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<table>
		<tr>
			<td><a href="<%=request.getContextPath()%>/new-item.html"><button
						class="btn btn-primary">Add</button></a></td>
		</tr>
	</table>
</div>


<!--/row-->

