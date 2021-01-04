<%@page import="com.daurenzg.commons.beans.satori.item.BCommonItemInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.item.BPushUpItem"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@page import="com.teremok.struts.helper.StrutsHelperConstants"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>

<%
	BCommonItemInfo commonItemInfo = (BCommonItemInfo) session.getAttribute(SatoriConstants.COMMON_ITEM_INFO);
%>

<div class="row">
	<form action="change-article-image.html" method="post" enctype="multipart/form-data">
		<table style="width: 50%" class="table table-bordered">
			<tr>
				<td colspan="2">
					<center><h3>Edit article image</h3></center>
				</td>			
			</tr>
			<tr>
				<td>Choose another file: </td>
				<td>
					<input type="file" name="file" required="required">
					<input name="articleId" type="hidden" value="<%=request.getParameter("articleId")%>" /> 
					<input name="fileId" type="hidden" value="<%=request.getParameter("fileId")%>" /> 
				</td>
			</tr>
			<tr>
				<td>
					<center><a href="<%=request.getContextPath()%>/go-edit-day.html" class="btn btn-danger">
						No
					</a></center>
				</td>
				<td>
					<button type="submit" class="btn btn-primary">Submit</button>
				</td>
			</tr>
		</table>
	</form>
</div>


<!--/row-->

