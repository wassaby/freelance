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

<h3>Add new language</h3>

	<form action="new-lang.html" method="post">
		<table>
			<tr>
				<td>Language name:</td>
				<td>
					<input type="text" style="height: 30px" name="name" required="required" />
				</td>
				
			</tr>
			<tr>
				<td>Language code:</td>
				<td>
					<input type="text" style="height: 30px" name="code" required="required"/>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/admin-main-page.html" class="btn btn-success">Back</a>
				</td>
			</tr>
		</table>

	</form>
</div>


