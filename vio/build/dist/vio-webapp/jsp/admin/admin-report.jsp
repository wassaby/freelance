<%@page import="com.rs.commons.vio.report.BInstanceItem"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rs.commons.vio.report.BReportStatusItem"%>
<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="com.rs.commons.vio.report.BReportHistoryItem"%>
<%@page import="java.util.List"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>


<%
	BReportItem reportItem = (BReportItem) request
			.getAttribute(VioConstants.ADMIN_VIEW_REPORT_ATTR);
	List instances = (List) session
			.getAttribute(VioConstants.INSTANCES);
	List statuses = (List) session
			.getAttribute(VioConstants.REPORT_STATUSES);
	String address = (String) request.getAttribute(VioConstants.ADDRESS); 
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	String reportNum = reportItem.getReportNumber() == null ? " " : reportItem.getReportNumber();
%>


<div class="content fwidth">
	<div class="report-slider g-box">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<%
					for (Iterator iter = reportItem.getListReportFileId().iterator(); iter
							.hasNext();) {
						BReportFileItem file = (BReportFileItem) iter.next();
				%>
				<div class="swiper-slide"
					style="background-image: url(<%=request.getContextPath()%>/view-file.html?id=<%=file.getFileId()%>);">
					<a href="<%=request.getContextPath() %>/delete-image.html?reportId=<%=reportItem.getReportId() %>&fileId=<%=file.getFileId() %>" class="delete"><img src="images/delete-ico.png" alt=""></a>	
					<a href="<%=request.getContextPath() %>/rotate-image.html?reportId=<%=reportItem.getReportId() %>&fileId=<%=file.getFileId() %>"><img src="images/rotate.png" alt=""></a>
				</div>
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
				for (Iterator iter1 = reportItem.getListReportFileId().iterator(); iter1
						.hasNext();) {
					BReportFileItem file = (BReportFileItem) iter1.next();
			%>
			<span
				class="swiper-pagination-bullet active <%if (file.getDeleted() == 1) {%>deleted<%}%>"
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

	<form action="change-status.html" method="post" enctype="multipart/form-data">
		<input type="hidden" name="reportId"
			value="<%=reportItem.getReportId()%>">
		<div class="report-info editing g-box">
		
		
		
		<%
		for (Iterator iter3 = reportItem.getReportHistory().iterator(); iter3.hasNext();){
			BReportHistoryItem hist = (BReportHistoryItem) iter3.next();
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
			<%=hist.getNewStatus() %> <% if (hist.getNewInstance() != null) out.write(hist.getNewInstance());%> (Комментарий администраций: <%=hist.getComment() %>)
		</div>
		
		<%
			}
		%>
	
	
	
			<div class="text">
				<textarea name="comment"><%=reportItem.getComment()%></textarea>
			</div>
			<div class="history add">
				<label> <span>Установить статус заявки:</span> <select
					name="statusId">
						<%
							for (Iterator iter2 = statuses.iterator(); iter2.hasNext();) {
								BReportStatusItem statusItem = (BReportStatusItem) iter2.next();
						%>
						<option value="<%=statusItem.getId()%>"><%=statusItem.getName()%></option>
						<%
							}
						%>
				</select> <span class="clr"></span>
				</label>
				<div class="text">
					<textarea name="statusComment"></textarea>
				</div>
				 <label> <span>Название инстанции:</span> <select
					name="instanceId">
						<option><bean:message key="application.admin.report-form.no-instance"/></option>
						<%
							for (Iterator iter3 = instances.iterator(); iter3.hasNext();) {
								BInstanceItem instanceItem = (BInstanceItem) iter3.next();
						%>
						<option value="<%=instanceItem.getId()%>"><%=instanceItem.getName()%></option>
						<%
							}
						%>
				</select> <span class="clr"></span>
				</label> <label> <span>Номер заявки:</span> <input type="text"
					placeholder="Введите номер заявки" id="fileinp" name="reportNumber" value="<%=reportNum %>">
					<span class="clr"></span>
				</label>
			</div>
			<label class="attach-file"> <input type="file" name="theFile">
				<span>Обзор...</span> Прикрепить сканированную копию завяления
			</label>
		</div>
		
		<button class="refresh-order" name="update" type="submit" >Обновить заявку</button>
		<!--<button class="public-order">Опубликовать</button> -->
	</form>
	
	<form action="change-status.html" method="post">
		<input type="hidden" name="reportId" value="<%=reportItem.getReportId()%>">
		<button class="refuse-order" name="refuse" type="submit" >Отклонить</button>
		<div class="refuse-reason">
			<div class="ttl">Напишите причину отказа</div>
			<textarea name="reason" placeholder="Напишите сюда причину Вашего отказа"></textarea>
		</div>
	</form>
	
	<div class="clr"></div>
</div>

