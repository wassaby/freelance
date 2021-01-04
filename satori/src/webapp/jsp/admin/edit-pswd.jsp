<%@page import="com.daurenzg.commons.beans.satori.login.BLoginInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.dict.BDictInfo"%>
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
	BDictInfo loginInfo = (BDictInfo) session.getAttribute(SatoriConstants.USER_MANAGER_PSWD_CHANGE);
	BLoginInfo info = loginInfo.getLoginInfo();
%>

<div class="row">

<h3>Edit content manager password</h3>

	<form action="edit-pswd.html" method="post">
		<table class="table table-bordered">
			<tr>
				<td>Manager's current login:</td>
				<td><%=info.getLogin() %></td>
			</tr>
			<tr>
				<td>Manager's current password:</td>
				<td><%=info.getPassword() %></td>
			</tr>
		</table>
		<table style="width: 50%">
			<tr>
				<td>Manager's new login:</td>
				<td>
					<input type="text" style="height: 30px" value="<%=info.getLogin() %>" name="login"/>
					<input type="hidden" name="userId" value="<%=info.getId() %>">
				</td>
				
			</tr>
			<tr>
				<td>Manager's new password:</td>
				<td>
					<input type="password" style="height: 30px" name="password" required="required"/>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn btn-success">Submit</button>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/admin-main-page.html" class="btn btn-primary">Back</a>
				</td>
			</tr>
		</table>

	</form>
</div>


