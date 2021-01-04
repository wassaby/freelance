<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<header id="header" class="fwidth">
	<a href="<%=request.getContextPath() %>/index.html" class="logo"><img src="images/logo.png" alt=""></a>
	<nav>
		<ul>
			<li class="all active"><a href="<%=request.getContextPath() %>/index.html">Все нарушения</a></li>
			<li class="eco"><a href="<%=request.getContextPath() %>/index.html?reportType=eco">ЭКО-нарушения</a></li>
			<li class="auto"><a href="<%=request.getContextPath() %>/index.html?reportType=auto">АВТО-нарушения</a></li>
			<li class="zoo"><a href="<%=request.getContextPath() %>/index.html?reportType=zoo">ЗОО-нарушения</a></li>
			<li class="map"><a href="<%=request.getContextPath()%>/map.html">Карта</a></li>
		</ul>
	</nav>
	<div class="clr">
	
	</div>
</header>
