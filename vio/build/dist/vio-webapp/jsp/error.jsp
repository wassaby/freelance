<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@ page language="java"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@page import="org.json.simple.JSONObject"%>

<%
	String errorMessageKey = (String)request.getAttribute(VioConstants.ALL_ERRORS);
	JSONObject jsonResponse = new JSONObject();
	JSONObject error = new JSONObject();
	error.put("code", 1);
	error.put("message", errorMessageKey);
	jsonResponse.put("error", error);
	out.print(jsonResponse);
	out.flush();
%>
