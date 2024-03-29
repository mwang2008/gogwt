<%--
   http://localhost/tracking/jsp/tracking/show_kml_map.jsp?kml=cta.kml
--%>
<!DOCTYPE html>
 <%
     String kml = request.getParameter("kml");
	 String kmlUrl = "http://gogwtengine.appspot.com/sampledata/"+kml;
	 System.out.println("kmlUrl="+kmlUrl);
	 
 %>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Google Maps JavaScript API v3 Example: KmlLayer KML</title>
<link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false"></script>

<script type="text/javascript">
function initialize() {
  var chicago = new google.maps.LatLng(41.875696,-87.624207);
  var myOptions = {
    zoom: 11,
    center: chicago,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  }

  var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  var theKmlUrl = '<%=kmlUrl%>';
  alert(theKmlUrl);
  var ctaLayer = new google.maps.KmlLayer(theKmlUrl);
  alert("here");
  ctaLayer.setMap(map);
}
</script>
</head>
<body onload="initialize()">
 
  <div id="map_canvas" style="width:740px; height:350px"></div>
</body>
</html>
