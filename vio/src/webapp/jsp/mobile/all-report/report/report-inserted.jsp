<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>

<%@page import="com.rs.commons.vio.user.BUserItem"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@page import="java.util.Map"%>
<%@page import="com.teremok.utils.formatter.IFormatter"%>
<%@page import="com.teremok.commons.beans.IComponentFactory"%>
<%@page import="com.teremok.commons.beans.CommonsBeansConstants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%
	String errorCode = (String) request.getAttribute(VioConstants.REPORT_INSERT_ERROR_CODE_ATTR);
	String errorMessage = (String) request.getAttribute(VioConstants.REPORT_INSERT_ERROR_MESSAGE_ATTR);
	long reportId = ((Long)request.getAttribute(VioConstants.INSERTED_REPORT_ID)).longValue();
	
	JSONObject jsonResponse = new JSONObject();
	JSONObject reportItem = new JSONObject();
	JSONObject error = new JSONObject();
	error.put("code", errorCode==null?"0":errorCode);
	error.put("message", errorMessage==null?"OK":errorMessage);
	reportItem.put("id", reportId);
	
	jsonResponse.put("error", error);
	jsonResponse.put("report", reportItem);
	out.print(jsonResponse);
	out.flush();
%>

