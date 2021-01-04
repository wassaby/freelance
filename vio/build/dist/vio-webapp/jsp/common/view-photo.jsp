<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>

<%@page import="com.rs.commons.beans.login.BLoginInfo"%>
<%@page import="com.teremok.commons.beans.CommonsBeansConstants"%>
<%@page import="com.teremok.struts.helper.StrutsHelperConstants"%>

<%@ page buffer="none"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	BReportItem reportItem = (BReportItem)session.getAttribute(SatoriConstants.VIEW_REPORT_ATTR);
int mainPhotoIndex = ((Integer)session.getAttribute(SatoriConstants.REPORT_MAIN_PHOTO_NUM_ATTR)).intValue();
BReportFileItem reportFileItem = (BReportFileItem) reportItem.getListReportFileId().get(mainPhotoIndex);
boolean isFirst = mainPhotoIndex < 1;
boolean hasNext = mainPhotoIndex < reportItem.getListReportFileId().size()-1;

String admin_flag = "admin-view-report.html";
BLoginInfo loginInfo = (BLoginInfo) session.getAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR);
	if (loginInfo == null || loginInfo.getAccountTypeId() != CommonsBeansConstants.ROLE_ADMIN){
		admin_flag = "view-report.html";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><bean:message key="application.broabcaster.title"/></title>
	<meta http-equiv="Content-Language" content="English" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="author" content="David Herreman (www.free-css-templates.com)" />
	<meta name="description" content="Free Css Template" />
	<meta name="keywords" content="free,css,template" />	
	<meta name="Robots" content="index,follow" />
	<meta name="Generator" content="sNews 1.5" />
	<meta http-equiv="cache-control" content="no-cache"/>
</head>

<body style="background-color: white;">
	<table width="100%" height="100%" border="0">
	<tr>
		<td align="center" valign="middle">
			<table border="0">
				<tr>
					<td>
						<img style="visibility: hidden;" src="<%= request.getContextPath() %>/images/close.gif"/>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<a href="<%= request.getContextPath() %>/<%=admin_flag %>?reportId=<%= String.valueOf(reportItem.getReportId()) %>">
							<img style="border: 0;" alt="<bean:message key='application.close-photo.message'/>" title="<bean:message key='application.close-photo.message'/>" src="<%= request.getContextPath() %>/img/close.gif"/>
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<% if (!isFirst) { %>
							<a href="<%= request.getContextPath() %>/view-photo.html?photo=<%= String.valueOf(mainPhotoIndex-1) %>&reportId=<%= String.valueOf(reportItem.getReportId()) %>">
								<img  style="border: 0;" alt="<bean:message key='application.previuos-photo.message'/>" title="<bean:message key='application.previuos-photo.message'/>" src="<%= request.getContextPath() %>/img/previous.gif"/>
							</a>
						<%} else {%>
							&nbsp;
						<%} %>
					</td>
					<td width="400" height="300" align="center" valign="middle" style="border: 1px; border-color: grey;border-style: solid;">
						<img style="border: 0;" src="<%= request.getContextPath() %>/view-file.html?id=<%= String.valueOf(reportFileItem.getFileId()) %>"/>
					</td>
					<td align="center">
						<% if (hasNext) { %>
							<a href="<%= request.getContextPath() %>/view-photo.html?photo=<%= String.valueOf(mainPhotoIndex+1) %>&reportId=<%= String.valueOf(reportItem.getReportId()) %>">
								<img style="border: 0;" alt="<bean:message key='application.next-photo.message'/>" title="<bean:message key='application.next-photo.message'/>" src="<%= request.getContextPath() %>/img/next.gif"/>
							</a>
						<%} else {%>
							&nbsp;
						<%} %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</body>
</html>