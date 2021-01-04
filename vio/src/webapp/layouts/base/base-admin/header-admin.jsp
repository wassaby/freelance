<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<!-- Хедер-->
<header id="header" class="fwidth">
	<a href="#" class="logo"><img src="images/logo.png" alt=""></a>
	<nav>
		<ul>
			<li class="wait"><a href="<%=request.getContextPath() %>/admin-main.html">Ожидающие рассмотрения</a></li>
			<li class="list"><a href="<%=request.getContextPath() %>/admin-list.html"">Сводка нарушений</a></li>
			<li class="exit"><a href="<%=request.getContextPath() %>/logout.html">Выход</a></li>
		</ul>
	</nav>
	<div class="clr"></div>
</header>
<!-- Хедер закончился-->
