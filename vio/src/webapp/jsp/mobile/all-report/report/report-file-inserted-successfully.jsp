<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>

<%@page import="java.util.Map"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@page import="com.teremok.utils.formatter.IFormatter"%>
<%@page import="com.teremok.commons.beans.IComponentFactory"%>
<%@page import="com.teremok.commons.beans.CommonsBeansConstants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%
	String errorCode = (String) request.getAttribute(VioConstants.LOGIN_ERROR_CODE_ATTR);
	String errorMessage = (String) request.getAttribute(VioConstants.LOGIN_ERROR_MESSAGE_ATTR);
	long fileId = ((Long)request.getAttribute(VioConstants.INSERTED_FILE_ID)).longValue();
	
	JSONObject jsonResponse = new JSONObject();
	JSONObject error = new JSONObject();
	error.put("code", errorCode==null?"0":errorCode);
	error.put("message", errorMessage==null?"OK":errorMessage);
	jsonResponse.put("error", error);
	jsonResponse.put("fileId", fileId);
	out.print(jsonResponse);
	out.flush();
%>