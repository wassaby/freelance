<%@page import="com.rs.commons.vio.report.BReportFileItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rs.commons.vio.report.BReportItem"%>
<%@page import="com.rs.vio.webapp.VioConstants"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%
	List reportList = (List) request.getAttribute(VioConstants.REPORT_LIST);
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
    <script src="js/jquery.formstyler.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
      $(function() {
        $("select").styler()

        initialize();
      });
	
	
	
      marks = [
<%
for (Iterator iter = reportList.iterator(); iter.hasNext();){
	BReportItem report = (BReportItem) iter.next();
	List reportFiles = report.getListReportFileId();
	String flag = "images/beachflag2.png";
	String txt = "";
	if (reportFiles.isEmpty()){
		txt = new StringBuffer().append("<div class=\"map-wind\"></div>").toString();
	} else {
		BReportFileItem file1 = (BReportFileItem) reportFiles.get(0);
		txt = new StringBuffer().append("<div class=\"map-wind\"><img src=\"")
					.append(request.getContextPath()).append("/").append("view-file.html?id=")
					.append(file1.getFileId()).append("\"/>").append(report.getStatus()).append("</div>").toString();
	}
	switch ((int) report.getStatusId()){
		case 1:
			flag = "images/beachflag2.png"; break;
		case 2:
			flag = "images/beachflag2.png"; break;
		case 3: 
			flag = "images/beachflag2.png"; break;
		case 4: 
			flag = "images/beachflag2.png"; break;
		case 5: 
			flag = "images/beachflag2.png"; break;
		case 6:
			flag = "images/beachflag3.png"; break;
		default:
			flag = "images/beachflag2.png"; break;
	}
%>
        {
          text: '<%=txt %>',
          pos: new google.maps.LatLng(<%=report.getLat() %>, <%=report.getLon() %>),
          //beach: 'images/beachflag.png'
          beach: '<%=flag %>'
        },
<%		
	}
%>
      ]

      function initialize() {
        var markers = [],
            infos = [];
        var mapOptions = {
          zoom: 11,
          center: new google.maps.LatLng(43.238172, 76.924210),
          panControl: true,
          zoomControl: true,
          scaleControl: true,
          scrollwheel: false,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        //var bounds = new google.maps.LatLngBounds();
        var map = new google.maps.Map(document.getElementById('map-canvas'),
                                      mapOptions);

        for (var i = 0; i <= marks.length - 1; i++) {
          infos[i] = new google.maps.InfoWindow({
              content: marks[i].text
          });

          markers[i] = new google.maps.Marker({
              position: marks[i].pos,
              map: map,
              icon: marks[i].beach
          });
          google.maps.event.addListener(markers[i], 'click', function() {
            $(".map-wind").css("opacity","0")
            var _this = this
                inc = 0;;
            for (var j = infos.length - 1; j >= 0; j--) {
              infos[j].close();
            };
            for (var i = 0; i < markers.length; i++){
              if (markers[i].position == _this.position){
                inc = i
                break;
              }
            }
            infos[inc].open(map, _this);
            setTimeout(function() {
              var mw = $(".map-wind")
              mw.css("opacity", "0")
              mw.parent().parent().parent().parent().addClass("infowindowst")
              $(".infowindowst").children(":not(.gm-style-iw)").remove()
              mw.parents(".gm-style-iw").parent().css('top',parseFloat(mw.parents(".gm-style-iw").parent().css('top'))+mw.outerHeight())
              mw.parents(".gm-style-iw").parent().css('left',parseFloat(mw.parents(".gm-style-iw").parent().css('left'))+mw.parents(".gm-style-iw").outerWidth()/2)
              mw.animate({"opacity":1},300)
            },200)
          });


        };
      }
    </script>