<%@page import="com.daurenzg.commons.beans.satori.dict.BDictInfo"%>
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
	BDictInfo dictInfo = (BDictInfo) session.getAttribute(SatoriConstants.LANGUAGE);
%>

<div class="row">
	<form action="delete-lang.html">
		<table style="width: 30%" class="table table-bordered">
			<tr>
				<td colspan="2">
					<center><h3>Delete language: <%=dictInfo.getName() %> ?</h3></center>
				</td>			
			</tr>
			<tr>
				<td>
					<center><input type="hidden" name="id" value="<%=request.getParameter("id")%>"> 
					<button class="btn btn-success" type="submit">Yes</button></center>
				</td>
				<td>
					<center><a href="<%=request.getContextPath()%>/admin-main-page.html" class="btn btn-danger">
						No
					</a></center>
				</td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

