<%@ page buffer="none"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">

<head>
	<title><bean:message key="application.name" /></title>
	
	<style type="text/css" media="all">
		@import url("layouts/base/css/bootstrap.css");
		@import url("layouts/base/css/jquery.cleditor.css");
	</style>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="Pragma" content="no-cache" />
	
</head>

<body>

	<tiles:insert attribute="header" />

	<div style="height: 60px"></div>
	
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span3">
					<tiles:insert attribute="menu" />
					<!--/.well -->
				</div>
				<!--/span-->
				<div class="span9">
					<tiles:insert attribute="body" />
				</div>
				<!--/span-->
			</div>
		</div>
		
		<div style="height: 60px"></div>
		<!--/.fluid-container-->
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/bootstrap-tooltip.js"></script>
		<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/bootstrap-popover.js"></script>
		
		<script type="text/javascript">
			 jQuery(document).ready(function() {
			 jQuery('[rel=popover]').popover();
			 });
		</script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cleditor.min.js"></script>
		
	    <script type="text/javascript">
	        $(document).ready(function () { $("#input").cleditor(); });
	    </script>
	    
	    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/jquery.msword_html_filter.js"></script>
		 <script type="text/javascript">
	        $("#input").msword_html_filter();
	    </script>
		
</body>

</html:html>
