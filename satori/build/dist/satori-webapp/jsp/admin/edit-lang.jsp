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
	BDictInfo dictInfo = (BDictInfo) session.getAttribute(SatoriConstants.LANGUAGE);
%>

<div class="row">

<h3>Edit language</h3>

	<form action="edit-lang.html" method="post">
		<table>
			<tr>
				<td>Language name:</td>
				<td>
					<input type="text" style="height: 30px" name="name" value="<%=dictInfo.getName() %>"/>
					<input type="hidden" name="id" value="<%=dictInfo.getId() %>"/>
				</td>
				
			</tr>
			<tr>
				<td>Language code:</td>
				<td>
					<input type="text" style="height: 30px" name="code" value="<%=dictInfo.getCode() %>"/>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/admin-main-page.html" class="btn btn-success">Back</a>
				</td>
				<td>
					<div class="control-group error">
						<div class="controls">
								<span class="help-inline"><html:errors property="error.empty-fields"/></span>
						</div>
					</div>
				</td>
			</tr>
		</table>

	</form>
</div>


