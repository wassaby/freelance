<%@page import="java.util.Calendar"%>
<%@page import="com.rs.vio.webapp.RequestObject"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rs.commons.vio.report.BReportHistoryItem"%>
<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	List reportList = (List) request.getAttribute(VioConstants.REPORT_LIST);
	Long reportCount = (Long) session.getAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM);

	RequestObject requestObject = (RequestObject) session.getAttribute(VioConstants.GET_REPORTS_REQUEST_PARAMETERS);
	String monthF = "";
	String yearF = "";
	if (!requestObject.getMonth().equals("all")){
		String[] str = requestObject.getMonth().split("-");
		monthF = str[0];
		yearF = str[1];
	}
	
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MM");
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat sdfMY = new SimpleDateFormat("MM-yyyy");
	String month = new StringBuffer().append("month.").append(sdf.format(calendar.getTime())).toString();
	String monthS = new StringBuffer().append(sdf.format(calendar.getTime())).toString();
	String year = new StringBuffer().append(sdfYear.format(calendar.getTime())).toString();
	String selected = "selected='selected'";
%>

<div class="content fwidth">
	<div class="map_page g-box">
		<div class="info-n-filter">
			<div class="count light">
				<!-- клас light делает текст светлее -->
				Всего нарушений: 
				<%=reportCount.longValue()%>
			</div>
			<div class="filter_month">
				<div class="name">Показать заявки только за:</div>
					<select name="show_date" onChange="if(this.options[this.selectedIndex].value!=''){window.location=this.options[this.selectedIndex].value}else{this.options[selectedIndex=0];}">
						<option <%if (requestObject.getMonth().equals("all")){ %> selected="selected" <%} %> value="<%=request.getContextPath() %>/map.html?month=all" ><bean:message key="web.show.reports.for-all-time"/></option>
						<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/map.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=year %></option>
					<%
						for (int i = 1; i < 12; i++){
							calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
							month = new StringBuffer().append("month.").append(sdf.format(calendar.getTime())).toString();
							year = new StringBuffer().append(sdfYear.format(calendar.getTime())).toString();
							monthS = new StringBuffer().append(sdf.format(calendar.getTime())).toString();
					%>
						<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/map.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=sdfYear.format(calendar.getTime()) %></option>
					<%		
						}
					%>
					</select>
			</div>
		</div>
		<div class="map">
			<div id="map-canvas"></div>
		</div>
	</div>

	<div class="clr"></div>
</div>