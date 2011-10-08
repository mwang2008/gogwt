<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
  
<html>
 <head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Active Tracks </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
     
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    
<script language=javascript>
     
   var jq = jQuery.noConflict();
   var map;
   var gpolys = [];
   var gmarkers = [];
   var gicons = [];
   
   var side_bar_html = ""; 
   var infowindow = null;
   var bounds = null;
   var side_bar_arr = [];
   
   var timer; 
   var totalRuntime = 0;
   var totalCycle = 0;
   
   var mylocs = "";
   var lineIcon = null;
    
   var lastTrackNames =  []; //new Array();   
   var numLastTrackNames = 0;
   var hasTrackingChanged = false;
   var trafficLayer;   
   var polyOps = [];
   
   var NUM_AUTO_REFERSH = 3;

   jq(document).ready(function() {
     
      //hidden auto refersh button            
      document.getElementById('autoRefersh').style.visibility='hidden';
       
      startTimer();
      
      var latlng;
      var g = google.maps;
  
      latlng = new g.LatLng(34.03, -84.19);
      //latlng = new g.LatLng(41.30, 122.00);
                    
      var myOptions = {
           zoom: 6,
           center: latlng,
           mapTypeId: g.MapTypeId.ROADMAP
      };
       
      map = new g.Map(document.getElementById("map_canvas"), myOptions); 
      
      //add traffic
      trafficLayer = new g.TrafficLayer();
      
      bounds = new g.LatLngBounds();
      infowindow = new g.InfoWindow({size: new google.maps.Size(150,50)});
       
      var lineImg = '${env.contextPath}/images/square.png';
      lineIcon = new g.MarkerImage(lineImg,
                       new google.maps.Size(11, 11),
                       new google.maps.Point(0,0),                                  
                       new google.maps.Point(5, 5));    
                       
      initPolyOps();
      
      showMaps(map);
  
      /*------------------------------------------------------+
       | Actions when user click Auto Refresh Button          | 
       +------------------------------------------------------*/       
      jq('#autoRefersh').click(function(){           
           document.getElementById('autoRefersh').style.visibility='hidden';
           startTimer();
      }); 
      
      jq('#showTraffic').click(function(){                               
	  var btnVal = document.getElementById('showTraffic').value;		             
	  if (btnVal == 'Show Traffic') {
	      document.getElementById('showTraffic').value = 'Hide Traffic';
	      trafficLayer.setMap(map);
	  }
	  else {
	      document.getElementById('showTraffic').value = 'Show Traffic'; 
	      trafficLayer.setMap(null);
	  }
      }); 
      
      jq('#clearMap').click(function(){           
         clearMap();   
      });
      
      
      
      /*------------------------------------------------------+
       | Functions                                            | 
       +------------------------------------------------------*/ 
    
      function showMaps(map) {         
         
         jq.getJSON('${env.prefix}/displaycurrentlocation?groupId=gg1&days=5', function(data) {
             
             if (!data.dispLocations || data.dispLocations.length == 0) {  
                document.getElementById("side_bar").innerHTML = "No tracking yet";
                return;
             }
              
             var hasTrackingChanged = hasTrackChanged(data); 
             //alert(" ----------- showMaps hasTrackingChanged="+hasTrackingChanged + ",totalRuntime=" +totalRuntime );
             
	     if (hasTrackingChanged) {
 	        clearSideBar();
	        clearMap();
	        showLeftSidebar(data);	        
	     }
           
             showLines(map, data);   
             
         }); <%-- end of getJSON --%>         
      }
    
      
      function showLines(map, data) {
          
    	 jq.each(data.dispLocations, function(index, dispItem) {
	     var line = dispItem.line;
	     var locs = dispItem.locs;
	     var dispName = dispItem.dispName;
	    
	     color = line.color;
	     	       		
	     var html = line.html;
	     var label = line.label;
	     var length = 0;
	     
	     	       
	     mylocs = "data=" + data.dispLocations.length + ", locs="+locs.length + ", dispName="+dispName; 		
	     document.getElementById("mylocs").innerHTML = mylocs;
	     
	     var pts = [];
	     var lastPoint;
	     var lastLoc;
	     
	     var ops = polyOps[color];
	    
             if (typeof gpolys[index] == 'undefined') {
                 var polyOptions = {
                         strokeColor: color,
		         strokeOpacity: 1.0,
		      	 strokeWeight: 6	
                 }
                 
                 //var poly = new g.Polyline(polyOptions);
                 var poly = new g.Polyline(ops);
	         poly.setMap(map);
	         
	         gpolys[index] = poly;
             }
              
             var point = null;
             var hasNewLoc = false;
             
             var polyLocNum = gpolys[index].getPath().length;
             
             //alert("=*** length="+locs.length + ",index="+index +", dispName="+dispName + " ,polyLocNum="+polyLocNum +",totalRuntime="+totalRuntime);
             for (var i=polyLocNum; i<locs.length; i++) {
                 //alert(" --- ddd i="+i + ", lat="+locs[i].latitude/1.0e6 + ",lng=" + locs[i].longitude/1.0e6);
                 point = new g.LatLng(locs[i].latitude/1.0e6, locs[i].longitude/1.0e6);
                 bounds.extend(point);                
                 gpolys[index].getPath().push(point);                
                 hasNewLoc = true;
             }
              

             if (hasNewLoc) {             
	        if (point != null) {	
	            
	           if (gmarkers && gmarkers.length>0 && gmarkers[index]) {
	              gmarkers[index].setMap(null);
	           }
                 
	           var currentLocMarker = new google.maps.Marker({
	                position: point,
	                map: map                 
	           });    
	           gmarkers[index] = currentLocMarker;	            
	        }	     
 	     }
 	     
	     createClickablePolyline(html, label, point, length, index);
 	    
             
	 }); <%-- end of jq.each(data.dispLocations --%>
	 
	  
	 <%-- fitBounds --%>	 
	 if (totalCycle<10) {
	    map.fitBounds(bounds);
	    totalCycle++;
	 }
      }


      function createClickablePolyline(html, label, point, length, index) {              
         
         var poly = gpolys[index];
         var poly_num = gpolys.length - 1;
               
         if (!html) {html = "";}
         else { html += "<br>";}
               
         length = length * 0.000621371192; // convert meters to miles
               
         html += "length="+length.toFixed(2)+" miles";
               
                 
         var contentString = html;
         
         
         google.maps.event.addListener(poly,'mouseover', function(event) {
      	    infowindow.setContent(contentString);
      	          
      	    if (event) {
      	       point = event.latLng;
      	    }
      	            
      	    infowindow.setPosition(point);
      	            
      	    infowindow.open(map);
      	    map.openInfoWindowHtml(point,html); 
         }); 
                  
        
         //square.png
         google.maps.event.addListener(poly,'mousemove', function(event) {
      	    infowindow.setContent(contentString);
      	
      	    				
      	    if (event) {
      	        point = event.latLng;
      	    }
      	 	
      	    if (gicons) {
	       for (var i=0; i < gicons.length; i++) {
	           gicons[i].setMap(null);
	       }                     
	       gicons.length = 0;
            }
            
      	    var iconMarker = new google.maps.Marker({
	         position: point,
	         map: map, 	
	         draggable:true,
	         icon: lineIcon
            });
            
	    gicons.push(iconMarker);
	
      	    infowindow.setPosition(point);      	 	            
      	    infowindow.open(map);
      	    map.openInfoWindowHtml(point, html);
      	 	      
         }); 
                    
   }  <%-- end of createClickablePolyline --%>
                  
   function initPolyOps() {
        var ops = {
             strokeColor: '#FF0000',  
      	     strokeOpacity: 0.5,
      	     strokeWeight: 6	
        }        
        polyOps['#FF0000'] = ops;  //red
        
        ops = {
	      strokeColor: '#00FF00',  
	      strokeOpacity: 0.5,
	      strokeWeight: 6	
        }
        polyOps['#00FF00'] = ops;  //green
        
        ops = {
	      strokeColor: '#0000FF',  
	      strokeOpacity: 0.5,
	      strokeWeight: 6	
	}
        polyOps['#0000FF'] = ops;  //blue
        
        ops = {
	     strokeColor: '#FFFF00',  
	     strokeOpacity: 0.5,
	     strokeWeight: 6	
	}
        polyOps['#FFFF00'] = ops;  //yellow
        
        ops = {
	     strokeColor: '#00FFFF',  
	     strokeOpacity: 0.5,
	     strokeWeight: 6	
	}
	polyOps['#00FFFF'] = ops;  //red
	
        ops = {
	     strokeColor: '#FF00FF',  
	     strokeOpacity: 0.5,
	     strokeWeight: 6	
	}
	polyOps['#FF00FF'] = ops;  //purple
	
        ops = {
	     strokeColor: '#000000',  
	     strokeOpacity: 0.5,
	     strokeWeight: 6	
	}
	polyOps['#000000'] = ops;  //black

   }
   
   function hasTrackChanged(data) {
       	 
      var retVal = false;
      
      if (numLastTrackNames != data.dispLocations.length) {
         retVal = true;
      }
      
      if (retVal == true) {
         return true;      
      }
      
      
      jq.each(data.dispLocations, function(index, dispItem) {
      	 var dispName = dispItem.dispName;
         
      	 if (typeof lastTrackNames[dispName] == 'undefined') {
      	    retVal = true;     	    
      	    return false;
      	 } 
   	     
      }); <%-- end of jq.each(data.dispLocations --%>
 
      return retVal;
   }
   
   function showLeftSidebar(data) {
       
      numLastTrackNames = 0;
      
      jq.each(data.dispLocations, function(index, dispItem) {
         var line = dispItem.line;
         var locs = dispItem.locs;
         var dispName = dispItem.dispName;
   	 
         var glocation = locs[locs.length-1];
         createSideBar(index, line, glocation);
         
         lastTrackNames[dispName] = index;
         numLastTrackNames++;
      }); <%-- end of jq.each(data.dispLocations --%>
              
      showSideBar();
      //redrawSidebar = true;
   }
   
    
   function createSideBar(index, line, glocation) {
          //alert(" createSideBar ");
          var sidebar = "";
           
          var label = "<a href='javascript:google.maps.event.trigger(gpolys["+index+"],\"mouseover\");'>"+line.label +"</a>";
           
          sidebar = '<input type="checkbox" id="poly'+index+'" checked="checked" onclick="togglePoly('+index+');">' + label + '<br />';
          
          sidebar += '&nbsp; StartTime: ' + line.startTime +  '<br />';
          sidebar += '&nbsp; Speed: ' + glocation.speed + '<br />';
          sidebar += '&nbsp; '  + 'poly_num=' + index + '<br />';
          sidebar += '<hr>';
          
          //return sidebar;
          
          side_bar_arr[index] = sidebar;
   }
           
   function showSideBar() {
       
       var ret = '';
       if (side_bar_arr && side_bar_arr.length>0) {        
          for (var i=0; i < side_bar_arr.length; i++) {
             ret += side_bar_arr[i];
          }                                       
       }
       
       //alert(" showSideBar totalRuntime=" + totalRuntime + " ,ret="+ret);
       document.getElementById("side_bar").innerHTML = ret;
   }
       
    
   function clearSideBar() {
       side_bar_arr = [];
       document.getElementById("side_bar").innerHTML = "No tracking";
   }
   
   function resetMap(map) {
         side_bar_html = "";
         /*
         if (gpolys && gpolys.length>0) {        
            for (var i=0; i < gpolys.length; i++) {
               gpolys[i].setMap(null);
	    }                     
            gpolys.length = 0;
         }
        */ 
         if (gmarkers && gmarkers.length>0) {
           for (var i=0; i < gmarkers.length; i++) {
	      gmarkers[i].setMap(null);
	   }                     
           gmarkers.length = 0;
         }
    }
      
    function clearMap(map) {         
         if (gpolys && gpolys.length>0) {                    
            for (var i=0; i < gpolys.length; i++) {
              gpolys[i].setMap(null);
      	    }                     
            gpolys.length = 0;
         }
               
         if (gmarkers && gmarkers.length>0) {
             for (var i=0; i < gmarkers.length; i++) {
      	        gmarkers[i].setMap(null);
      	     }                     
             gmarkers.length = 0;
         }
    }
      
    function nextCycle() {         
         totalRuntime++;
         
         document.getElementById("xtimer").innerHTML = "totalRuntime=" + totalRuntime;
 
         showMaps(map);
         
         if (totalRuntime > NUM_AUTO_REFERSH) { 
             totalRuntime = 0;
	     document.getElementById('autoRefersh').style.visibility='visible';
             stopRotation(); 
         }
    }
      
    function stopRotation() {
         totalRuntime = 0;
      	 clearInterval(timer);
    }
      
    function startTimer() {
      	timer = setInterval(nextCycle, 3000);
    }
        
  });<%-- jq(document).ready --%>
   
   /**
    * show/hide polyline
    */
   function togglePoly(poly_num) {    
      //alert(" togglePoly poly_num="+poly_num + ", gpolys.length=" + gpolys.length);
      if (document.getElementById('poly'+poly_num)) {
         if (document.getElementById('poly'+poly_num).checked) {
            gpolys[poly_num].setMap(map);
         } else {
            gpolys[poly_num].setMap(null);
         }
      }
   }    
   

   
</script>
</head>

<body>

<%@ include file="/jsp/common/i_header.jspf"%>
  <c:set var="fromPage" value="Show Active Tracks"/>
<%@ include file="/jsp/common/i_menu.jspf"%>

 
<div id="container"> 
   <table border="1">
       <tr> 
          <td width="200" valign="top"> 
            <form name=xcv>
	        <input id="autoRefersh" type="button"  value="Start Auto Refresh">
	        <input id="showTraffic" type="button" value="Show Traffic">
	         
            </form>
            <div id="xtimer"> starting auto refresh </div><hr>
            <div id="mylocs">locations </div><hr>
            <div id="side_bar" style="height: 450px; overflow:auto"></div>
          </td>
          <td valign="top" width="760" align="left">
             <div id="container">  
	   
	       <div id="map_canvas" style="width:700px; height:450px"></div>
             </div> 
          </td>
       </tr>
   </table>
</div>
      
        
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/common/i_footer.jspf"%></div>
</div> 

<svg id="chart" width="400" height="240"><defs id="defs"><clipPath id="_ABSTRACT_RENDERER_ID_0"><rect x="75" y="46" width="250" height="148"></rect></clipPath></defs><g><rect x="0.5" y="0.5" width="399" height="239" stroke="none" stroke-width="0" fill="#ffffff"></rect><g><text x="75" y="27" font-family="Arial" font-size="11" font-weight="bold" stroke="none" stroke-width="0" fill="#000000">Investment Portfolio Stocks By Return</text></g><g><g><text x="351" y="55" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">Shares</text></g><rect x="336" y="46" width="11" height="11" stroke="none" stroke-width="0" fill="#3366cc"></rect></g><rect x="75" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#cccccc"></rect><rect x="138" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#cccccc"></rect><rect x="200" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#cccccc"></rect><rect x="263" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#cccccc"></rect><rect x="325" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#cccccc"></rect><rect x="75" y="46" width="1" height="148" stroke="none" stroke-width="0" fill="#333333"></rect><g clip-path="url(#_ABSTRACT_RENDERER_ID_0)"><rect x="75" y="46" width="250" height="148" stroke="none" stroke-width="0" fill-opacity="1" fill="none"></rect><g></g><g><rect x="76" y="50" width="17" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect><rect x="76" y="75" width="87" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect><rect x="76" y="100" width="93" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect><rect x="76" y="124" width="233" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect><rect x="76" y="149" width="171" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect><rect x="76" y="174" width="18" height="15" stroke="none" stroke-width="0" fill="#3366cc"></rect></g><g></g><g></g><g></g><g></g><g></g></g><g><text text-anchor="middle" x="75.25" y="210" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#444444">0</text></g><g><text text-anchor="middle" x="137.75" y="210" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#444444">100</text></g><g><text text-anchor="middle" x="200.25" y="210" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#444444">200</text></g><g><text text-anchor="middle" x="262.75" y="210" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#444444">300</text></g><g><text text-anchor="middle" x="325.25" y="210" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#444444">400</text></g><g><text text-anchor="end" x="64" y="62" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">AAPL</text></g><g><text text-anchor="end" x="64" y="87" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">ADC</text></g><g><text text-anchor="end" x="64" y="111" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">ETFC</text></g><g><text text-anchor="end" x="64" y="136" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">FIG</text></g><g><text text-anchor="end" x="64" y="161" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">SUI</text></g><g><text text-anchor="end" x="64" y="185" font-family="Arial" font-size="11" stroke="none" stroke-width="0" fill="#222222">TZOO</text></g></g><g></g></svg>

 
<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
 