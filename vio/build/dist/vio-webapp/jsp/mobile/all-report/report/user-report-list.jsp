<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rs.commons.vio.report.BReportHistoryItem"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
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
	String errorCode = (String) request.getAttribute(VioConstants.LOGIN_ERROR_CODE_ATTR);
	String errorMessage = (String) request.getAttribute(VioConstants.LOGIN_ERROR_MESSAGE_ATTR);
	List reportList = (List) request.getAttribute(VioConstants.MOBILE_USER_REPORTS);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	JSONObject jsonResponse = new JSONObject();
	JSONObject error = new JSONObject();
	JSONArray jsonReportList = new JSONArray();
	
	
	error.put("code", errorCode==null?"0":errorCode);
	error.put("message", errorMessage==null?"OK":errorMessage);
	
	for (Iterator iterator = reportList.iterator(); iterator.hasNext();){
		JSONArray historyList = new JSONArray();
		JSONObject jsonReportItem = new JSONObject();
		JSONArray urlArray = new JSONArray();
		
		BReportItem reportItem = (BReportItem) iterator.next();
		jsonReportItem.put("id", reportItem.getReportId());
		jsonReportItem.put("reportHeader", reportItem.getHeader());
		jsonReportItem.put("reportComment", reportItem.getComment());
		jsonReportItem.put("reportTypeId", reportItem.getReportTypeId());
		jsonReportItem.put("reportTypeName", reportItem.getReportTypeName());
		jsonReportItem.put("reportStatus", reportItem.getStatus());
		jsonReportItem.put("reportUserId", reportItem.getUserId());
		jsonReportItem.put("reportDate", dateFormat.format(reportItem.getDate()));
		jsonReportItem.put("longitude", reportItem.getLon());
		jsonReportItem.put("latitude", reportItem.getLat());
		
		/*for (Iterator it = reportItem.getListReportFileId().iterator(); it.hasNext();){
			BReportFileItem reportFileItem = (BReportFileItem) it.next();
			String urlString = new StringBuffer().append("http://")
					.append(request.getServerName()).append(":")
					.append(request.getServerPort()).append(request.getContextPath())
					.append("view-file.html?id=").append(reportFileItem.getFileId()).toString();
			urlArray.add(urlString);
		}*/
		if (!reportItem.getListReportFileId().isEmpty()) {
			for (Iterator it = reportItem.getListReportFileId().iterator(); it.hasNext();){
				BReportFileItem reportFileItem = (BReportFileItem) it.next();
				String urlString = new StringBuffer()
						.append("view-file.html?id=").append(reportFileItem.getFileId()).toString();
				urlArray.add(urlString);
			}
		} else {
			String urlString = new StringBuffer()
					.append("images/no-image.png").toString();
			urlArray.add(urlString);
		}
		
		jsonReportItem.put("reportFilesURL", urlArray);
		
		for (Iterator iter = reportItem.getReportHistory().iterator(); iter.hasNext();){
			BReportHistoryItem historyItem = (BReportHistoryItem) iter.next();
			JSONObject jsonHistoryItem = new JSONObject();
			jsonHistoryItem.put("id", historyItem.getId());
			jsonHistoryItem.put("reportId", historyItem.getReportId());
			jsonHistoryItem.put("oldStatus", historyItem.getOldStatus());
			jsonHistoryItem.put("oldStatusId", historyItem.getOldStatusId());
			jsonHistoryItem.put("oldInstance", historyItem.getOldInstance());
			jsonHistoryItem.put("newStatus", historyItem.getNewStatus());
			jsonHistoryItem.put("newStatusId", historyItem.getNewStatusId());
			jsonHistoryItem.put("newInstance", historyItem.getNewInstance());
			jsonHistoryItem.put("date", dateFormat.format(historyItem.getDate()));
			jsonHistoryItem.put("comment", historyItem.getComment());
			historyList.add(jsonHistoryItem);
		}
		jsonReportItem.put("reportHistory", historyList);
		
		jsonReportList.add(jsonReportItem);
	}
	
	out.print(jsonReportList);
	out.flush();
	
%>