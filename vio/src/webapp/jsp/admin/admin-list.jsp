<%@page import="org.springframework.beans.support.PagedListHolder"%>
<%@page import="com.rs.vio.webapp.RequestObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
<%@page import="com.rs.commons.vio.report.BReportStatusItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	List reportItemList = (List) request.getAttribute(VioConstants.ADMIN_REPORT_LIST_ALL);
	RequestObject requestObject = (RequestObject) session.getAttribute(VioConstants.GET_ADMIN_REPORTS_REQUEST_PARAMETERS);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	List statusFilter = (List) request.getAttribute(VioConstants.REPORT_STATUSES);
	String selected = "selected='selected'";
	if (requestObject.getReportStatusId() != 0){
		
	}
%>

<div class="content fwidth">
	<div class="filter_month">
		<div class="name">Показать только:</div>
		<select name="filterStatus" onChange="if(this.options[this.selectedIndex].value!=''){window.location=this.options[this.selectedIndex].value}else{this.options[selectedIndex=0];}">
			<option value="<%=request.getContextPath() %>/admin-list.html?reportStatusId=0">Все</option>
			<%
				for (Iterator iter2 = statusFilter.iterator(); iter2.hasNext();) {
					BReportStatusItem statusItem = (BReportStatusItem) iter2.next();
			%>
			<option <%if (requestObject.getReportStatusId() == statusItem.getId()){ %> selected="selected" <%} %> value="<%=request.getContextPath() %>/admin-list.html?reportStatusId=<%=statusItem.getId()%>"><%=statusItem.getName()%></option>
			<%
				}
			%>
		</select> 
		<a href="<%=request.getContextPath() %>/download-xls.html?reportStatusId=<%=requestObject.getReportStatusId() %>"><button name="download">Скачать в Excell</button></a>
	</div>
	<div class="g-box l-violation-list">
		<a class="l-violation-item title">
			<span class="num">Номер отчета</span>
			<span class="cat">Категория</span>
			<span class="date">Дата и время</span>
			<span class="stat">Статус</span>
			<span class="clr"></span>
		</a>
		<%
			for (Iterator iter = reportItemList.iterator(); iter.hasNext();){
				BReportItem report = (BReportItem) iter.next();
				String date = dateFormat.format(report.getDate());
				//String reportNum = (report.getReportNumber() == null || report.getReportNumber().trim().length() == 0) ? "&nbsp" : report.getReportNumber();
				long reportNum = report.getReportId();
				String color = "";
				switch ((int) report.getStatusId()){
					case 1:
						color = "orange"; break;
					case 2:
						color = "green"; break;
					case 3: 
						color = "orange"; break;
					case 4: 
						color = "green"; break;
					case 5: 
						color = "red"; break;
					case 6:
						color = "green"; break;
					default:
						color = "orange"; break;
				}
		%>
		<a href="<%=request.getContextPath() %>/admin-view-report.html?reportId=<%=report.getReportId()%>" class="l-violation-item">
			<span class="num"><%=reportNum %></span>
			<span class="cat"><%=report.getReportTypeName() %></span>
			<span class="date"><%=date %></span>
			<span class="stat <%=color %>"><%=report.getStatus() %></span>
			<span class="clr"></span>
		</a>
		<%
			}		
		%>
		
		<%
			Long reportCount = (Long) session.getAttribute(VioConstants.REPORT_COUNT_SHOW_WEBFORM);
			Long currentPageL = (Long) request.getAttribute(VioConstants.PAGE);
			int currentPage = currentPageL.intValue();
			if (currentPage == 0) currentPage++;
			long pages = reportCount.longValue() / VioConstants.COUNT_REPORTS;
			long mod = reportCount.longValue() % VioConstants.COUNT_REPORTS;
			if (mod != 0) pages++;
		%>
		<div class="nav">
			<div class="pagenav">
				Страница
				<ul>
				<%
					for (int i = 1; i <= pages; i++){
				%>
					<li><a href="<%=request.getContextPath() %>/admin-list.html?page=<%=i %>" <%if (i == currentPage){ %>class="active"<%} %>><%=i %></a></li>
				<%
					}
				%>
				</ul>
			</div>
			<a href="<%=request.getContextPath() %>/admin-list.html?page=<%=currentPage + 1 %>" class="next_page">Следующая страница</a>
			<div class="clr"></div>
		</div>

	</div>

	<div class="clr"></div>
</div>
