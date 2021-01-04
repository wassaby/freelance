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
	BItemInfo itemInfo = (BItemInfo) session.getAttribute(SatoriConstants.ITEM);
%>

<div class="row">
	<form action="move-item-trash.html">
		<table style="width: 30%" class="table table-bordered">
			<tr>
				<td colspan="2">
					<center><h3>Move item <strong><%=itemInfo.getName() %></strong> to trash?</h3></center>
				</td>			
			</tr>
			<tr>
				<td>
					<center><input type="hidden" name="itemId"
						value="<%=itemInfo.getId()%>"> <input
						type="hidden" name="langId"
						value="<%=itemInfo.getLangId()%>">
					<button class="btn btn-primary" type="submit">Yes</button></center>
				</td>
				<td>
					<center><a href="<%=request.getContextPath()%>/item-list.html" class="btn btn-danger">
						No
					</a></center>
				</td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

