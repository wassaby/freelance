<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
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
	List reportList = (List) request
			.getAttribute(VioConstants.ADMIN_WAITING_REPORTS);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
%>

<div class="content fwidth">
	<div class="m-violation-list">
		<div class="num">Номер отчета</div>
		<div class="cat">Категория</div>
		<div class="date">Дата и время</div>
		<div class="watch">Просмотреть</div>
		<div class="clr"></div>
	</div>
	<%
		for (Iterator iter = reportList.iterator(); iter.hasNext();) {
			BReportItem report = (BReportItem) iter.next();
			String date = dateFormat.format(report.getDate());
	%>
	<div class="m-violation-list g-box">
		<div class="num"><%=report.getReportId()%></div>
		<div class="cat"><%=report.getReportTypeName()%></div>
		<div class="date"><%=date%></div>
		<div class="watch">
			<a
				href="<%=request.getContextPath()%>/admin-view-report.html?reportId=<%=report.getReportId()%>">Просмотреть
				заявку</a>
		</div>
		<div class="clr"></div>
	</div>
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
		
		<div class="nav g-box">
			<div class="pagenav">
				Страница
				<ul>
					<%
					for (int i = 1; i <= pages; i++){
				%>
					<li><a href="<%=request.getContextPath() %>/admin-main.html?page=<%=i %>" <%if (i == currentPage){ %>class="active"<%} %>><%=i %></a></li>
				<%
					}
				%>
				</ul>
			</div>
			<a href="<%=request.getContextPath() %>/admin-main.html?page=<%=currentPage + 1 %>" class="next_page">Следующая страница</a>
			<div class="clr"></div>
		</div>

	<div class="clr"></div>

</div>