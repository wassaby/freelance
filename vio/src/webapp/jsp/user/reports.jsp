<%@page import="com.rs.vio.webapp.RequestObject"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
	
	if (reportList.isEmpty()) {
%>
<div class="content fwidth main">
	<div class="info-n-filter">
		<div class="count light">
			<!-- клас light делает текст светлее -->
			Всего нарушений: 
			<%=reportCount.longValue()%>
		</div>
		<div class="filter_month">
			<div class="name">Показать заявки только за:</div>
			<select name="show_date" onChange="if(this.options[this.selectedIndex].value!=''){window.location=this.options[this.selectedIndex].value}else{this.options[selectedIndex=0];}">
				<option <%if (requestObject.getMonth().equals("all")){ %> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=all" ><bean:message key="web.show.reports.for-all-time"/></option>
				<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=year %></option>
			<%
				for (int i = 1; i < 12; i++){
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
					month = new StringBuffer().append("month.").append(sdf.format(calendar.getTime())).toString();
					year = new StringBuffer().append(sdfYear.format(calendar.getTime())).toString();
					monthS = new StringBuffer().append(sdf.format(calendar.getTime())).toString();
			%>
				<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=sdfYear.format(calendar.getTime()) %></option>
			<%		
				}
			%>
			</select>
		</div>
	</div>
</div>
<%
	} else {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		List reportList1 = new ArrayList();
		List reportList2 = new ArrayList();
		for (int i = 0; i < reportList.size(); i++) {
			if (i % 2 == 0)
				reportList1.add(reportList.get(i));
			else
				reportList2.add(reportList.get(i));
		}
		
		
		
		
%>
<div class="content fwidth main">
	<div class="info-n-filter">
		<div class="count light">
			<!-- клас light делает текст светлее -->
			Всего нарушений: 
			<%=reportCount.longValue()%>
		</div>
		<div class="filter_month">
			<div class="name">Показать заявки только за:</div>
			<select name="show_date" onChange="if(this.options[this.selectedIndex].value!=''){window.location=this.options[this.selectedIndex].value}else{this.options[selectedIndex=0];}">
				<option <%if (requestObject.getMonth().equals("all")){ %> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=all" ><bean:message key="web.show.reports.for-all-time"/></option>
				<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=year %></option>
			<%
				for (int i = 1; i < 12; i++){
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
					month = new StringBuffer().append("month.").append(sdf.format(calendar.getTime())).toString();
					year = new StringBuffer().append(sdfYear.format(calendar.getTime())).toString();
					monthS = new StringBuffer().append(sdf.format(calendar.getTime())).toString();
			%>
				<option <%if (monthF.equals(monthS)) {%> selected="selected" <%} %> value="<%=request.getContextPath() %>/index.html?month=<%=sdfMY.format(calendar.getTime()) %>"><bean:message key="<%=month %>"/> <%=sdfYear.format(calendar.getTime()) %></option>
			<%		
				}
			%>
			</select>
		</div>
	</div>
	<div class="m-violations-list">
		<div class="col">
			<%
				for (Iterator i = reportList1.iterator(); i.hasNext();) {
						BReportItem report = (BReportItem) i.next();
						long reportId = report.getReportId();
						String reportTypeCode = report.getReportTypeCode();
						String reportTypeName = report.getReportTypeName();
						long reportTypeId = report.getReportTypeId();
						String reportStatus = report.getStatus();
						long reportStatusId = report.getStatusId();
						String reportHeader = report.getHeader();
						String reportComment = report.getComment();
						long reportUserId = report.getUserId();
						String reportDate = dateFormat.format(report.getDate());

						List reportFiles = report.getListReportFileId();
						
						if (reportFiles.isEmpty()) {
			%>
			<div class="m-violation">
				<div class="type <%=reportTypeCode%>"></div>
				<div class="images">
					<a href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img alt=""
						src="images/no-image.png"></a>
					<div class="grid"></div>
				</div>

				<div class="text"><%=reportComment%></div>
				<div class="ctrls">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"
						class="read_all">Смотреть отчет</a>
					<div class="date"><%=reportDate%></div>
					<div class="clr"></div>
				</div>
			</div>
			<%
				} else {
							BReportFileItem file1 = (BReportFileItem) reportFiles
									.get(0);
			%>
			<div class="m-violation">
				<div class="type <%=reportTypeCode%>"></div>
				<div class="images">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img
						alt=""
						src="<%=request.getContextPath()%>/view-file.html?id=<%=file1.getFileId()%>"></a>
					<div class="grid">
						<%
							for (Iterator iter = reportFiles.iterator(); iter
												.hasNext();) {
											BReportFileItem file = (BReportFileItem) iter
													.next();
											long fileId = file.getFileId();
											long isScan = file.getScan();
						%>
						<a
							href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img
							alt=""
							src="<%=request.getContextPath()%>/view-file.html?id=<%=file.getFileId()%>"></a>
						<%
							}
						%>
					</div>
				</div>

				<div class="text"><%=reportComment%></div>
				<div class="ctrls">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"
						class="read_all">Смотреть отчет</a>
					<div class="date"><%=reportDate%></div>
					<div class="clr"></div>
				</div>
			</div>
			<%
				}
					}
			%>
		</div>

		<div class="col">
			<%
				for (Iterator i = reportList2.iterator(); i.hasNext();) {
						BReportItem report = (BReportItem) i.next();
						long reportId = report.getReportId();
						String reportTypeCode = report.getReportTypeCode();
						String reportTypeName = report.getReportTypeName();
						long reportTypeId = report.getReportTypeId();
						String reportStatus = report.getStatus();
						long reportStatusId = report.getStatusId();
						String reportHeader = report.getHeader();
						String reportComment = report.getComment();
						long reportUserId = report.getUserId();
						String reportDate = dateFormat.format(report.getDate());
						List reportFiles = report.getListReportFileId();
						
						if (reportFiles.isEmpty()) {
			%>
			<div class="m-violation">
				<div class="type <%=reportTypeCode%>"></div>
				<div class="images">
					<a href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img alt=""
						src="images/no-image.png"></a>
					<div class="grid"></div>
				</div>

				<div class="text"><%=reportComment%></div>
				<div class="ctrls">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"
						class="read_all">Смотреть отчет</a>
					<div class="date"><%=reportDate%></div>
					<div class="clr"></div>
				</div>
			</div>
			<%
				} else {
						BReportFileItem file1 = (BReportFileItem) reportFiles
								.get(0);
			%>
			<div class="m-violation">
				<div class="type <%=reportTypeCode%>"></div>
				<div class="images">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img
						alt=""
						src="<%=request.getContextPath()%>/view-file.html?id=<%=file1.getFileId()%>"></a>
					<div class="grid">
						<%
							for (Iterator iter = reportFiles.iterator(); iter.hasNext();) {
										BReportFileItem file = (BReportFileItem) iter.next();
										long fileId = file.getFileId();
										long isScan = file.getScan();
						%>
						<a
							href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"><img
							alt=""
							src="<%=request.getContextPath()%>/view-file.html?id=<%=file.getFileId()%>"></a>
						<%
							}
						%>
					</div>
				</div>

				<div class="text"><%=reportComment%></div>
				<div class="ctrls">
					<a
						href="<%=request.getContextPath()%>/view-report.html?reportId=<%=reportId%>"
						class="read_all">Смотреть отчет</a>
					<div class="date"><%=reportDate%></div>
					<div class="clr"></div>
				</div>
			</div>
			<%
				}
				}
				}
			%>
		</div>
		<div class="clr"></div>
	</div>
	
	
	
	<%
	if (!reportList.isEmpty()) {
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
					<li><a href="<%=request.getContextPath() %>/index.html?page=<%=i %>" <%if (i == currentPage){ %>class="active"<%} %>><%=i %></a></li>
				<%
					}
				%>
				</ul>
			</div>
			<a href="<%=request.getContextPath() %>/index.html?page=<%=currentPage + 1 %>" class="next_page">Следующая страница</a>
			<div class="clr"></div>
		</div>
		<%} %>
		
	<div class="clr"></div>
</div>
