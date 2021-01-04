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


<div class="row">
	<form action="delete-pushup.html">
		<table style="width: 30%" class="table table-bordered">
			<tr>
				<td colspan="2">
					<center>
						<h3>Delete Push-Up?</h3>
					</center>
				</td>
			</tr>
			<tr>
				<td>
					<center>
						<input type="hidden" name="notificationId"
							value="<%=request.getParameter("notificationId")%>"> <input
							type="hidden" name="langId"
							value="<%=request.getParameter("langId")%>">
						<button class="btn btn-primary" type="submit">Yes</button>
					</center>
				</td>
				<td><center>
						<a href="<%=request.getContextPath()%>/go-edit-day.html"
							class="btn btn-danger">No</a>
					</center></td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

