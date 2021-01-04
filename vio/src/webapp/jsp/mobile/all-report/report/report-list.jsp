<%@page import="com.rs.commons.vio.report.BReportHistoryItem"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>

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
	List reportList = (List) request.getAttribute(VioConstants.MOBILE_USER_REPORTS);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	JSONObject jsonResponse = new JSONObject();
	JSONArray jsonReportList = new JSONArray();
	
	for (Iterator iter = reportList.iterator(); iter.hasNext();){
		JSONObject jsonReportItem = new JSONObject();
		JSONArray urlArray = new JSONArray();
		JSONArray jsonFileArray = new JSONArray();
		JSONArray historyList = new JSONArray();
		
		BReportItem reportItem = (BReportItem) iter.next();
		
		jsonReportItem.put("id", reportItem.getReportId());
		jsonReportItem.put("reportHeader", reportItem.getHeader());
		jsonReportItem.put("reportComment", reportItem.getComment());
		jsonReportItem.put("reportTypeId", reportItem.getReportTypeId());
		jsonReportItem.put("reportTypeName", reportItem.getReportTypeName());
		jsonReportItem.put("reportStatus", reportItem.getStatus());
		jsonReportItem.put("reportStatusId", reportItem.getStatusId());
		jsonReportItem.put("reportUserId", reportItem.getUserId());
		jsonReportItem.put("reportDate", dateFormat.format(reportItem.getDate()));
		jsonReportItem.put("reportTypeCode", reportItem.getReportTypeCode());
		jsonReportItem.put("longitude", reportItem.getLon());
		jsonReportItem.put("latitude", reportItem.getLat());

		
		/*for (Iterator it = reportItem.getListReportFileId().iterator(); it.hasNext();){
			BReportFileItem fileItem = (BReportFileItem) it.next();
			JSONObject jsonFileItem = new JSONObject();
			
			String urlString = new StringBuffer().append("http://")
					.append(request.getServerName()).append(":")
					.append(request.getServerPort()).append(request.getContextPath())
					.append("/view-file.html?id=").append(fileItem.getFileId()).toString();
			
			jsonFileItem.put("fileId",fileItem.getFileId());
			jsonFileItem.put("fileUrl", urlString);
			jsonFileItem.put("scan", fileItem.getScan());
			jsonFileArray.add(jsonFileItem);
		}*/
		
		if (!reportItem.getListReportFileId().isEmpty()) {
			for (Iterator it = reportItem.getListReportFileId().iterator(); it.hasNext();){
				BReportFileItem fileItem = (BReportFileItem) it.next();
				JSONObject jsonFileItem = new JSONObject();
				
				String urlString = new StringBuffer()
						.append("/view-file.html?id=").append(fileItem.getFileId()).toString();
				
				jsonFileItem.put("fileId",fileItem.getFileId());
				jsonFileItem.put("fileUrl", urlString);
				jsonFileItem.put("scan", fileItem.getScan());
				jsonFileArray.add(jsonFileItem);
			}
		} else {
			JSONObject jsonFileItem = new JSONObject();
			String urlString = new StringBuffer().append("images/no-image.png").toString();
			jsonFileItem.put("fileId", 0);
			jsonFileItem.put("fileUrl", urlString);
			jsonFileItem.put("scan", 0);
			jsonFileArray.add(jsonFileItem);
		}
		
		jsonReportItem.put("reportFilesURL", jsonFileArray);
		jsonReportList.add(jsonReportItem);
		
		for (Iterator iter2 = reportItem.getReportHistory().iterator(); iter2.hasNext();){
			BReportHistoryItem historyItem = (BReportHistoryItem) iter2.next();
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
	}
	
	jsonResponse.put("reportList", jsonReportList);
	
	out.print(jsonResponse);
	out.flush();
%>
