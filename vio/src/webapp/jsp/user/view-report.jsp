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
	BReportItem reportItem = (BReportItem) request.getAttribute(VioConstants.VIEW_REPORT_ATTR);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	String address = (String) request.getAttribute(VioConstants.ADDRESS); 
%>

<div class="content fwidth">
	<div class="report-slider g-box">
		<div class="swiper-container">
			<div class="swiper-wrapper">
			<%
				for (Iterator iter = reportItem.getListReportFileId().iterator(); iter.hasNext();){
					BReportFileItem file = (BReportFileItem) iter.next();
			%>
				<div class="swiper-slide"
					style="background-image: url(<%=request.getContextPath()%>/view-file.html?id=<%=file.getFileId()%>);"></div>
					
					<% 
						}
					%>
			</div>
		</div>
		<div class="swiper-button-prev">
			<img src="images/swiper-prev.png" alt="">
		</div>
		<div class="swiper-button-next">
			<img src="images/swiper-next.png" alt="">
		</div>
		<div class="swiper-pagination">
			<%
				for (Iterator iter = reportItem.getListReportFileId().iterator(); iter.hasNext();){
					BReportFileItem file = (BReportFileItem) iter.next();
			%>
			<span class="swiper-pagination-bullet active"
				style="background-image: url(<%=request.getContextPath()%>/view-file.html?id=<%=file.getFileId()%>);"></span>
					<% 
						}
					%>
		</div>
	</div>
	<div class="report-map g-box">
		<div id="map-canvas"></div>
		<div class="addr"><%=address %></div>
	</div>
	<div class="report-info g-box">
		<div class="text"><%=reportItem.getComment() %></div>
		<div class="history">
			<div class="ttl">История заявки</div>
			
			<%
				for (Iterator iter2 = reportItem.getReportHistory().iterator(); iter2.hasNext();){
					BReportHistoryItem hist = (BReportHistoryItem) iter2.next();
					String color2 = "";
					switch ((int) hist.getNewStatusId()){
						case 1:
							color2 = "orange"; break;
						case 2:
							color2 = "green"; break;
						case 3: 
							color2 = "orange"; break;
						case 4: 
							color2 = "green"; break;
						case 5: 
							color2 = "red"; break;
						case 6:
							color2 = "green"; break;
						default:
							color2 = "orange"; break;
					}
			%>
			
			<div class="item <%=color2 %>">
				<div class="date"><%=dateFormat.format(hist.getDate()) %></div>
				<%
					String admComment =  "";
					if (hist.getComment() != null) admComment = " (Комментарий администраций: " + hist.getComment() + ")";
				%>
				<%=hist.getNewStatus() %> <% if (hist.getNewInstance() != null) out.write(hist.getNewInstance());%> <%=admComment %>
			</div>
			
			<%
				}
			%>
			
		</div>
	</div>
	<div class="report-apps g-box">
		<div class="ttl">Данная заявка была отправлена с приложения
			Solve IT</div>
		<a href="https://itunes.apple.com/us/app/solve-it/id978630282?l=ru&ls=1&mt=8"><img src="images/app-store.png" alt=""></a> <a
			href="https://play.google.com/store/apps/details?id=kz.outlawstudio.solveit"><img src="images/google-play.png" alt=""></a>
	</div>
	<div class="clr"></div>
</div>
