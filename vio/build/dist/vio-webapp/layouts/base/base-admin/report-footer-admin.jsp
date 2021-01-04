<%@page import="com.rs.commons.vio.report.BReportItem"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	BReportItem reportItem = (BReportItem) request.getAttribute(VioConstants.ADMIN_VIEW_REPORT_ATTR);
	String lon = (reportItem.getLon() == null || reportItem.getLon().trim().length() == 0) ? "43.25654" : reportItem.getLon();
	String lat = (reportItem.getLat() == null || reportItem.getLat().trim().length() == 0) ? "76.92848" : reportItem.getLat();
	float ln = Float.valueOf(lon);
	float lt = Float.valueOf(lat);
%>

<!-- Футер-->
<footer id="footer">
	<div class="fwidth">
		<div class="partners">
			<a href="#"><img src="images/logo_almaty.png" alt=""></a>
		</div>
		<div class="data">
			<div class="mail">dz@posadiderevo.kz</div>
			<ul class="socials">
				<li><a href="#"><img src="images/soc-g-vk.png" alt=""></a></li>
				<li><a href="#"><img src="images/soc-g-fb.png" alt=""></a></li>
				<li><a href="#"><img src="images/soc-g-tw.png" alt=""></a></li>
				<li><a href="#"><img src="images/soc-g-pi.png" alt=""></a></li>
				<li><a href="#"><img src="images/soc-g-in.png" alt=""></a></li>
				<li><a href="#"><img src="images/soc-g-inst.png" alt=""></a></li>
			</ul>
			<div class="clr"></div>

			<div class="copy">Copyright &copy; solveit.kz</div>
			<div class="clr"></div>


			<div class="fax">8 (727) 328-25-34</div>
			<div class="copy">Все права защищены.</div>
			<div class="clr"></div>

			<div class="addr">ул. Макатаева 129 (между Байтурсынова и Шарипова)</div>
			<div class="cb">
				Сайт создан <a href="http://Websign.kz" target="_blank">Websign.kz</a>
			</div>
		</div>
	</div>
</footer>
<!-- Футер закончился-->
<script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="<%=request.getContextPath()%>/js/jquery.formstyler.min.js"></script>
<script src="<%=request.getContextPath()%>/js/swiper.min.js"></script>
<script>
	$(function() {
		// $("select").styler()

		var swiper1 = $(".report-slider").find(".swiper-container").swiper(
				{
					slidesPerView : 1,
					autoplay : 5000,
					noSwiping : true,
					loop : false,
					effect : 'slide',
					nextButton : '.swiper-button-next',
					prevButton : '.swiper-button-prev',
					spaceBetween : 100,
					onSlideChangeStart : function(swiper) {
						$('.swiper-pagination-bullet').removeClass('active')
						$('.swiper-pagination-bullet').eq(swiper.activeIndex)
								.addClass('active')
					}
				});

		$('.swiper-pagination-bullet').click(function() {
			swiper1.slideTo($(this).index());
			$('.swiper-pagination-bullet').removeClass('active');
			$(this).addClass('active')
		})

		initialize();

	});

	function initialize() {
		var mapOptions = {
			zoom : 15,
			center : new google.maps.LatLng(<%=lt %>,<%=ln %>),
			panControl : true,
			zoomControl : true,
			scaleControl : true,
			scrollwheel : false,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var image = 'images/beachflag.png';
		var beachMarker = new google.maps.Marker({
			position : new google.maps.LatLng(<%=lt %>,<%=ln %>),
			map : map,
			icon : image
		});
	}
</script>