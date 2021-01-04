<%@page import="com.daurenzg.commons.beans.satori.item.BItemInfo"%>
<%@page import="com.daurenzg.commons.beans.satori.dict.BDictInfo"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@page import="java.util.Iterator"%>
<%@page import="javax.servlet.jsp.tagext.IterationTag"%>
<%@page import="java.util.List"%>
<%@page import="com.teremok.struts.helper.StrutsHelperConstants"%>
<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>

<%
	List users = (List) session.getAttribute(SatoriConstants.USER_LIST);
	List langs = (List) session.getAttribute(SatoriConstants.LANGUAGE_LIST);
	List statuses = (List) session.getAttribute(SatoriConstants.STATUSES);
	List types = (List) session.getAttribute(SatoriConstants.ITEM_TYPES);
%>

<div class="row">
	<h3>Languages</h3>
	<a name="langTable"></a>
	<table class="table table-bordered" >
		<thead>
			<tr>
				<th>
					ID
				</th>
				<th>
					Language	
				</th>
				<th>
					Code
				</th>
				<th>
				</th>
			</tr>
		</thead>
		<tbody>
		<% for (Iterator iter = langs.iterator(); iter.hasNext();){
				BItemInfo langItem = (BItemInfo) iter.next();
			%>
			<tr>
				<td>
					<%=langItem.getId() %>		
				</td>
				<td>
					<%=langItem.getLangName() %>
				</td>
				<td>
					<%=langItem.getLangCode() %>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/go-edit-lang.html?id=<%=langItem.getId() %>"><button class="btn btn-success">Edit</button></a>
					<a href="<%=request.getContextPath() %>/go-delete-lang.html?id=<%=langItem.getId() %>"><button class="btn btn-danger">Delete</button></a>
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<td colspan="4">
				<a href="<%=request.getContextPath() %>/go-new-lang.html"><button class="btn btn-primary">Add</button></a>
			</td>
		</tr>
		</tbody>
	</table>
	
	<h3>Item statuses</h3>
	<a name="statusTable"></a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>
					ID
				</th>
				<th>
					Name
				</th>
				<th>
				
				</th>
			</tr>
		</thead>
		<tbody>
		<% for (Iterator iter2 = statuses.iterator(); iter2.hasNext();){
				BItemInfo statusItem = (BItemInfo) iter2.next();
			%>
			<tr>
				<td>
					<%=statusItem.getId() %>		
				</td>
				<td>
					<%=statusItem.getName() %>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/go-edit-status.html?id=<%=statusItem.getId() %>/"><button disabled="disabled" class="btn btn-success">Edit</button></a>
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<td colspan="3">
				<a href="<%=request.getContextPath() %>/go-new-status.html"><button disabled="disabled" class="btn btn-primary">Add</button></a>
			</td>
		</tr>
		</tbody>
	</table>
	
	<h3>Item types</h3>
	<a name="typeTable"></a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>
					ID
				</th>
				<th>
					Name
				</th>
				<th>
				
				</th>
			</tr>
		</thead>
		<tbody>
		<% for (Iterator iter3 = statuses.iterator(); iter3.hasNext();){
				BItemInfo typesItem = (BItemInfo) iter3.next();
			%>
			<tr>
				<td>
					<%=typesItem.getId() %>		
				</td>
				<td>
					<%=typesItem.getName() %>
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/go-edit-type.html?id=<%=typesItem.getId() %>/"><button disabled="disabled" class="btn btn-success">Edit</button></a>
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<td colspan="3">
				<a href="<%=request.getContextPath() %>/go-new-type.html"><button disabled="disabled" class="btn btn-primary">Add</button></a>
			</td>
		</tr>
		</tbody>
	</table>
</div>


<!--/row-->

