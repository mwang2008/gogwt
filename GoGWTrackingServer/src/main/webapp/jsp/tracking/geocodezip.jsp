
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

  <head>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title>Google Maps Browser (APIv3)</title>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 

 
  <link type="text/css" href="${env.contextPath}/css/jquery-ui-1.8rc3.custom.css" rel="stylesheet"/>
  <script type="text/javascript" src="/tracking/script/jquery-1.4.2.min.js"></script>

  <script type="text/javascript" src="/tracking/script/jquery-ui-1.8rc3.custom.min.js"></script>

<script type="text/javascript" src="/tracking/script/downloadxml.js"></script>

<script type="text/javascript" src="/tracking/script/gxml.js"></script>

<script type="text/javascript" src="/tracking/script/v3_epoly.js"></script>

<!-- QueryString= -->

<!-- FullDomainName=www.geocodezip.com -->

<!-- PathInfo=/v3_GenericMapBrowser.asp -->

<!-- ScriptName=/v3_GenericMapBrowser.asp -->



<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">

</script>

<script type="text/javascript">

_uacct = "UA-162157-1";

urchinTracker();

</script>

<style type="text/css">

html, body { height: 100%; } 

</style>

    <script type="text/javascript">

    //<![CDATA[





      // this variable will collect the html which will eventually be placed in the side_bar 

      var side_bar_html = ""; 

    

      // arrays to hold copies of the markers and html used by the sidebar

      // because the function closure trick doesnt work there

      var gmarkers = [];

      var gicons = [];

      var gpolys = [];

     // global "map" variable

      var map = null;

      var bounds = null;

      var infowindow = null;



// From Marc McClure's page at

// http://facstaff.unca.edu/mcmcclur/googlemaps/encodepolyline/decode.js

// This function is from Google's polyline utility.

function decodeLine (encoded) {

  var len = encoded.length;

  var index = 0;

  var array = [];

  var lat = 0;

  var lng = 0;



  while (index < len) {

    var b;

    var shift = 0;

    var result = 0;

    do {

      b = encoded.charCodeAt(index++) - 63;

      result |= (b & 0x1f) << shift;

      shift += 5;

    } while (b >= 0x20);

    var dlat = ((result & 1) ? ~(result >> 1) : (result >> 1));

    lat += dlat;



    shift = 0;

    result = 0;

    do {

      b = encoded.charCodeAt(index++) - 63;

      result |= (b & 0x1f) << shift;

      shift += 5;

    } while (b >= 0x20);

    var dlng = ((result & 1) ? ~(result >> 1) : (result >> 1));

    lng += dlng;



// LJR modified to return array of LatLngs 

    array.push(new google.maps.LatLng(lat * 1e-5, lng * 1e-5));

  }



  return array;

}



 function bindInfoWindow(marker, map, infowindow, contentString) {

      google.maps.event.addListener(marker, 'click', function() {

        if (!infowindow) { alert("no infowindow!") };

	infowindow.close();

        infowindow.setContent(contentString);

        infowindow.open(map, marker);

        $("#tabs").tabs();

      });

}

gicons["red"] = new google.maps.MarkerImage("http://labs.google.com/ridefinder/images/mm_20_red.png",

      // This marker is 12 pixels wide by 20 pixels tall.

      new google.maps.Size(12, 20),

      // The origin for this image is 0,0.

      new google.maps.Point(0,0),

      // The anchor for this image is at 6,20.

      new google.maps.Point(6, 20));

  // Marker sizes are expressed as a Size of X,Y

  // where the origin of the image (0,0) is located

  // in the top left of the image.

 

  // Origins, anchor positions and coordinates of the marker

  // increase in the X direction to the right and in

  // the Y direction down.



  var iconImage = new google.maps.MarkerImage('http://labs.google.com/ridefinder/images/mm_20_red.png',

      // This marker is 20 pixels wide by 32 pixels tall.

      new google.maps.Size(12, 20),

      // The origin for this image is 0,0.

      new google.maps.Point(0,0),

      // The anchor for this image is the base of the flagpole at 0,32.

      new google.maps.Point(6, 20));

  var iconShadow = new google.maps.MarkerImage('http://labs.google.com/ridefinder/images/mm_20_shadow.png',

      // The shadow image is larger in the horizontal dimension

      // while the position and offset are the same as for the main image.

      new google.maps.Size(22, 20),

      new google.maps.Point(0,0),

      new google.maps.Point(6, 20));

      // Shapes define the clickable region of the icon.

      // The type defines an HTML &lt;area&gt; element 'poly' which

      // traces out a polygon as a series of X,Y points. The final

      // coordinate closes the poly by connecting to the first

      // coordinate.

  var iconShape = {

      coord: [4,0,0,4,0,7,3,11,4,19,7,19,8,11,11,7,11,4,7,0],

      type: 'poly'

  };

var icons = new Array();

icons["red"] = new google.maps.MarkerImage("http://labs.google.com/ridefinder/images/mm_20_red.png",

      // This marker is 20 pixels wide by 32 pixels tall.

      new google.maps.Size(12, 20),

      // The origin for this image is 0,0.

      new google.maps.Point(0,0),

      // The anchor for this image is the base of the flagpole at 0,32.

      new google.maps.Point(6, 20));



function getMarkerImage(iconColor) {

   if ((typeof(iconColor)=="undefined") || (iconColor==null)) { 

      iconColor = "red"; 

   }

   if (!gicons[iconColor]) {

      gicons[iconColor] = new google.maps.MarkerImage("http://labs.google.com/ridefinder/images/mm_20_"+ iconColor +".png",

      // This marker is 20 pixels wide by 34 pixels tall.

      new google.maps.Size(12, 20),

      // The origin for this image is 0,0.

      new google.maps.Point(0,0),

      // The anchor for this image is at 6,20.

      new google.maps.Point(6, 20));

   } 

   return gicons[iconColor];

}

// A function to create the marker and set up the event window function 



function createMarker(latlng,name,html,icon) {

    var contentString = html;

    var marker = new google.maps.Marker({

        position: latlng,

        icon: getMarkerImage(icon),

        shadow: iconShadow,

        map: map,

        title: name,

        zIndex: Math.round(latlng.lat()*-100000)<<5

        });

/*

    google.maps.event.addListener(marker, 'click', function() {

        infowindow.setContent(contentString); 

        infowindow.open(map,marker);

		$("#tabs").tabs();

        });

*/

    bindInfoWindow(marker, map, infowindow, contentString);

    // save the info we need to use later for the side_bar

    gmarkers.push(marker);

    // add a line to the side_bar html

    side_bar_html += '<a href="javascript:myclick(' + (gmarkers.length-1) + ')">' + name + '<\/a><br>';

}



// This function picks up the click and opens the corresponding info window

function myclick(i) {

  google.maps.event.trigger(gmarkers[i], "click");

}





      function togglePoly(poly_num) {

        if (document.getElementById('poly'+poly_num)) {

           if (document.getElementById('poly'+poly_num).checked) {

              gpolys[poly_num].setMap(map);

           } else {

              gpolys[poly_num].setMap(null);

           }

        }

      } 





      function createClickablePolyline(poly, html, label, point, length) {

        gpolys.push(poly);

        var poly_num = gpolys.length - 1;

        if (!html) {html = "";}

        else { html += "<br>";}

	length = length * 0.000621371192; // convert meters to miles

        html += "length="+length.toFixed(2)+" miles";

        // html += poly.getLength().toFixed(2)+" m; "+(poly.getLength()*3.2808399).toFixed(2)+" ft; ";

        // html += (poly.getLength()*0.000621371192).toFixed(2)+" miles";

        var contentString = html;

        google.maps.event.addListener(poly,'click', function(event) {

          infowindow.setContent(contentString);

          if (event) {

             point = event.latLng;

          }

          infowindow.setPosition(point);

          infowindow.open(map);

          // map.openInfoWindowHtml(point,html); 

        }); 

        if (!label) {

          label = "polyline #"+poly_num;

        }

        label = "<a href='javascript:google.maps.event.trigger(gpolys["+poly_num+"],\"click\");'>"+label+"</a>";

        // add a line to the sidebar html

        side_bar_html += '<input type="checkbox" id="poly'+poly_num+'" checked="checked" onclick="togglePoly('+poly_num+');">' + label + '<br />';



      }



function initialize() {

  // create the map

  var myOptions = {

    zoom: 8,

    center: new google.maps.LatLng(43.907787,-79.359741),

    mapTypeControl: true,

    mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},

    navigationControl: true,

    mapTypeId: google.maps.MapTypeId.ROADMAP

  }

  map = new google.maps.Map(document.getElementById("map_canvas"),

                                myOptions);

 

  infowindow = new google.maps.InfoWindow(

  { 

    size: new google.maps.Size(150,50)

  });

  google.maps.event.addListener(map, 'click', function() {

        infowindow.close();

        });

      var filename = "exampleTabbed.xml";



      document.MyForm.filename.value = "flights090414.xml";

      filename = "flights090414.xml";



      // Read the data from example.xml

      getXmlFile(filename);



      }

function getXmlFile(filename) {
      alert(" here filename="+filename);
      
      downloadUrl(filename, function(doc) {
         alert(" here filename="+filename);
        var g = google.maps;

        var xmlDoc = xmlParse(doc);

          // obtain the array of markers and loop through it

          var markers = xmlDoc.documentElement.getElementsByTagName("marker");

          // alert("processing "+markers.length+" markers");

        bounds = new google.maps.LatLngBounds();

        for (var i=0; i < gmarkers.length; i++) {

          gmarkers[i].setMap(null);

        }

        gmarkers = new Array();

        side_bar_html = ""; 

        for (var i = 0; i < markers.length; i++) {

         // obtain the attributes of each marker

         var marker_num = gmarkers.length;

            var lat = parseFloat(markers[i].getAttribute("lat"));

            var lng = parseFloat(markers[i].getAttribute("lng"));

            var label = markers[i].getAttribute("label");

         var icon = markers[i].getAttribute("icon");

         var contentStringTabs = "";

         var contentStringBody = "";

            // alert("point["+i+"] label="+label+":("+lat+", "+lng+")");

            if (isNaN(lat) || isNaN(lng)) {

               alert("bad point "+i);

               continue;

            }

          var point = new g.LatLng(lat,lng);



            // get the tab info

         var tabInfo = markers[i].getElementsByTagName("tab");

         var tabs = new Array();

         if ((tabInfo) && (tabInfo.length > 0)) {

            // alert("processing "+tabInfo.length+" tabs");

            for (var j = 0; j < tabInfo.length; j++) {

               var tabLabel = GXml.value(tabInfo[j].getElementsByTagName("label")[0]);

               var tabHtml = GXml.value(tabInfo[j].getElementsByTagName("contents")[0]);

               // alert("point["+i+"] tab["+j+"] label="+tabLabel+", contents="+tabHtml);

               if ((j==0) && (tabInfo.length > 2)){ //  adjust the width so that the info window is large enough for this many tabs

                   tabHtml = '<div style="width:'+tabInfo.length*88+'px">' + tabHtml + '</div>';

               }

               contentStringTabs += "<li><a href='#tab-"+j+"'><span>"+tabLabel+"</span></a></li>";

               contentStringBody += "<div id='tab-"+j+"'>"+tabHtml+"</div>";

            }

            // add directions tab

            var tabLabel = "Directions";

            var tabHtml = '<div style="width:'+(tabs.length+1)*88+'px">'+'<br>Directions: <a href="javascript:tohere('+marker_num+')">To here</a> - <a href="javascript:fromhere('+marker_num+')">From here</a></style>';

            contentStringTabs += "<li><a href='#tab-"+j+"'><span>Directions</span></a></li>";

            contentStringBody += "<div id='tab-"+j+"'>"+tabHtml+"</div>";

	    var contentString = "<div id='tabs'><ul>"+contentStringTabs+"</ul>"+contentStringBody;

         } else {

            // alert("no tabs point "+i);

            var tabLabel = label;

            var tabHtml = markers[i].getAttribute("html");

	    if (!tabHtml) { tabHtml = GXml.value(markers[i]); }

            if (!tabHtml) tabHtml = label;

            tabHtml += "<br />("+point.toUrlValue()+")";

            tabHtml += "<br /><a href='javascript:map.setCenter(gmarkers["+marker_num+"].getPosition());map.setZoom(17);'>Zoom In</a> - ";

            tabHtml += "<a href='javascript:map.fitBounds(bounds);'>Zoom Out</a>";

            tabHtml += ' - <a  href="javascript:map.setCenter(new google.maps.LatLng('+point.toUrlValue(6)+')); map.setZoom(parseInt(map.getZoom())+1);">[+]</a>';

            tabHtml += ' - <a  href="javascript:map.setCenter(new google.maps.LatLng('+point.toUrlValue(6)+')); map.setZoom(parseInt(map.getZoom())-1);">[-]</a>';            

	    var contentString = tabHtml;

         }

            // create the marker

         var marker = createMarker(point,label,contentString,icon);

         bounds.extend(point);



          }

      // ========= Now process the encoded polylines ===========

      var lines = xmlDoc.documentElement.getElementsByTagName("encodedline");

      // read each line

      for (var a = 0; a < lines.length; a++) {

        // get any line attributes

        var label = lines[a].getAttribute("label");

        var colour = lines[a].getAttribute("colour");

        var width  = parseInt(lines[a].getAttribute("width"));

        var opacity = parseFloat(lines[a].getAttribute("opacity"));

        var zoomFactor  = parseInt(lines[a].getAttribute("zoomFactor"));

        var numLevels  = parseInt(lines[a].getAttribute("numLevels"));

        var html = lines[a].getAttribute("html");

        var geodesic = lines[a].getAttribute("geodesic");

        if (!geodesic) { geodesic=false; }

        if (isNaN(opacity)) opacity = 0.45;

        if (isNaN(zoomFactor)) zoomFactor = 2;

        if (isNaN(numLevels)) numLevels = 18;

        if (isNaN(width)) width = 4;

        // read encoded points string

        var points = GXml.value(lines[a].getElementsByTagName("points")[0]);

        var levels = GXml.value(lines[a].getElementsByTagName("levels")[0]);



        var pts = decodeLine(points);

// alert("polyline has "+pts.length+" points");

        var poly = new g.Polyline({

                          map:map,

                          path:pts,

                          strokeColor:colour,

                          strokeOpacity:opacity,

                          strokeWeight:width,

                          geodesic: geodesic,

                          clickable: true

                          });

        var length = 0;

        var point = null;

        for (var i=0;i<pts.length;i++) {

           bounds.extend(pts[i]);

           if (i > 0) {

             length += pts[i-1].distanceFrom(pts[i]);

             // alert("length="+length+" segment="+distance(pts[i-1],pts[i]));

           }

           point = pts[parseInt(i/2)];

        }



        createClickablePolyline(poly, html, label, point, length);

      }

      // ========= Now process the polylines ===========

      var lines = xmlDoc.documentElement.getElementsByTagName("line");

      // read each line

      for (var a = 0; a < lines.length; a++) {

        // get any line attributes

        var label = lines[a].getAttribute("label");

        if (!label) { label = "polyline #"+a; }

        var geodesic = lines[a].getAttribute("geodesic");

        if (!geodesic) { geodesic=false; }

        var opacity = lines[a].getAttribute("opacity");

        if (!opacity) { opacity = 0.45; }

        var colour = lines[a].getAttribute("colour");

        var width  = parseFloat(lines[a].getAttribute("width"));

        var html = lines[a].getAttribute("html");

        // read each point on that line

        var points = lines[a].getElementsByTagName("point");

        var pts = [];

        var length = 0;

        var point = null;

        for (var i = 0; i < points.length; i++) {

           pts[i] = new g.LatLng(parseFloat(points[i].getAttribute("lat")),

                                parseFloat(points[i].getAttribute("lng")));

           if (i > 0) {

             length += pts[i-1].distanceFrom(pts[i]);

             if (isNaN(length)) { alert("["+i+"] length="+length+" segment="+pts[i-1].distanceFrom(pts[i])) };

           }

           bounds.extend(pts[i]);

           point = pts[parseInt(i/2)];

        }

        // length *= 0.000621371192; // miles/meter 

// alert("poly:"+label+" point="+point+" i="+i+" (i/2)%2="+parseInt(i/2)+" length="+length);

        var poly = new g.Polyline({

                          map:map,

                          path:pts,

                          strokeColor:colour,

                          strokeOpacity:opacity,

                          strokeWeight:width,

                          geodesic: geodesic,

                          clickable: true

                          });

        createClickablePolyline(poly, html, label, point, length);

        // map.addOverlay(poly);

      }



      // ================================================           

          // put the assembled sidebar_html contents into the sidebar div

        document.getElementById("side_bar").innerHTML = side_bar_html;

        map.fitBounds(bounds);

      });

   }









    // This Javascript is based on code provided by the

    // Blackpool Community Church Javascript Team

    // http://www.commchurch.freeserve.co.uk/   

    // http://www.econym.demon.co.uk/googlemaps/



    //]]>

    </script>

<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">

</script>

<script type="text/javascript">

_uacct = "UA-162157-1";

urchinTracker();

</script>

  </head>

<body style="margin:0px; padding:0px;" onload="initialize()"> 

<h3>Google Maps Browser (APIv3)</h3>



    <!-- you can use tables or divs for the overall layout -->

    <table border="1">

      <tr>

        <td>

           <div id="map_canvas" style="width: 550px; height: 450px"></div> 

        </td>

        <td valign="top" style="width:200px; text-decoration: underline; color: #4444ff;"> 

           <div id="side_bar" style="height: 450px; overflow:auto"></div>

        </td>

      </tr>

    </table>

    <a href="http://www.econym.demon.co.uk/googlemaps/">Based off of information in Mike Williams' v2 Tutorial</a>

<br />Uses the xml file <a href="http://www.geocodezip.com/exampleTabbed.xml">exampleTabbed.xml</a> to create the markers.

<br />

<form name="MyForm" id="MyForm" onsubmit="return false;" action=""> 

<input name="filename" type="text" id="filename" value="exampleTabbed.xml" /> 

<input type="button" value="Click here!" onclick="getXmlFile(document.MyForm.filename.value);" />

</form> 



<div id="w3valid">

    <a href="http://validator.w3.org/check?uri=referer"><img

        src="http://www.w3.org/Icons/valid-xhtml10"

        alt="Valid XHTML 1.0 Transitional" height="31" width="88" /></a>

</div>







    <noscript><b>JavaScript must be enabled in order for you to use Google Maps.</b> 

      However, it seems JavaScript is either disabled or not supported by your browser. 

      To view Google Maps, enable JavaScript by changing your browser options, and then 

      try again.

    </noscript>





  </body>



</html>








