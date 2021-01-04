<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<form action="test-upload.html" method="post" enctype="multipart/form-data">
	<input name="reportId" type="text"/>
	<input name="theFile" type="file">
	<button type="submit">Отправить</button>
</form>

<%=request.getServerName() %>
<%=request.getContextPath() %>
<%=request.getPathInfo() %>
<%=request.getRemoteAddr() %>
<%=request.getRemoteHost() %>

<a href="<%=request.getContextPath()%>/send-email.html"><button name="send_btn">Send email</button></a>

<a href="<%=request.getContextPath() %>/test-rotate-image.html?reportId=23&fileId=46"><img src="images/rotate.png" alt=""></a>

<img src="<%=request.getContextPath()%>/view-file.html?id=46">