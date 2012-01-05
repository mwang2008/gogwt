<%--
   http://gogwtengine.appspot.com/show_kml_map.jsp?kml=cta.kml
--%>
<!DOCTYPE html>
 <%
     String kml = request.getParameter("kml");
	 String kmlUrl = "http://gogwtengine.appspot.com/sampledata/"+kml;
	 //System.out.println("kmlUrl="+kmlUrl);
	 
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
  // -122.364167,37.824787
  //var chicago = new google.maps.LatLng(41.875696,-87.624207);
  var chicago = new google.maps.LatLng(37.824787,-122.364167);
  var myOptions = {
    zoom: 11,
    center: chicago,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  }

  var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  var theKmlUrl = '<%=kmlUrl%>';
  //alert(theKmlUrl);
  var ctaLayer = new google.maps.KmlLayer(theKmlUrl);   
  ctaLayer.setMap(map);
}
</script>
</head>
<body onload="initialize()">
  4444
   <img src="/images/logo.jpg" atl="GoGWT Logo"><br>
  <div id="map_canvas" style="width:740px; height:350px"></div>
  
  <pre>
  -112.2549277039738,36.08117083492122,2357
          -112.2552505069063,36.08260761307279,2357
          -112.2564540158376,36.08395660588506,2357
          -112.2580238976449,36.08511401044813,2357
          -112.2595218489022,36.08584355239394,2357
          -112.2608216347552,36.08612634548589,2357
          -112.262073428656,36.08626019085147,2357
          -112.2633204928495,36.08621519860091,2357
          -112.2644963846444,36.08627897945274,2357
  </pre>   
</body>
</html>
