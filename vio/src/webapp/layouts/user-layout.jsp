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
    <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic,700italic,500,500italic&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
   
    <style type="text/css" media="all">
		@import url("layouts/base/css/style.css");
	</style>
</head>

<body>
	<div class="wrapper">
		<!-- Хедер-->
      	
      	<tiles:insert attribute="header" />
     	
     	<!-- Хедер закончился-->
     	
     	<tiles:insert attribute="body" />
     	
	</div>
	
	<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.formstyler.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.justifiedGallery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.fancybox.pack.js"></script>
    <script>
      $(function() {
        $("select").styler()
        $(".m-violation").each(function (i, el) {
          $(el).find(".images>a").attr("rel", 'gal' + i)
          $(el).find(".images .grid").justifiedGallery({
            rel: 'gal' + i,
            rowHeight : 125,
            lastRow : 'nojustify',
            margins : 5
          }).on('jg.complete', function () {
            $(this).find('a').fancybox()
          });
        });
      });
    </script>
    
</body>

</html:html>
