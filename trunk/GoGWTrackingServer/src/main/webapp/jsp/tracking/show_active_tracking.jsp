<%--
   http://localhost/tracking/en-us/addlocation
   
--%>
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
   
   <script type="text/javascript" src="http://www.google.com/jsapi"></script>
   
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    
<script language=javascript>
     
   var jq = jQuery.noConflict();
   var map;
   var chart = null;
   var gpolys = [];
   var gmarkers = [];
   var gicons = [];
   var mousemarker = null;
   
   var side_bar_html = ""; 
   var infowindow = null;
   var bounds = null;
   var side_bar_arr = [];
   var mousemarker = null;
   var timer; 
   var totalRuntime = 0;
   var totalCycle = 0;
   
   var mylocs = "";
   var lineIcon = null;
    
   var lastTrackNames =  []; //new Array();   
   var numLastTrackNames = 0;
   var hasTrackingChanged = false;
   var trafficLayer;   
   var currentIndex = 0;
   var changeChart = false;
   
   var chartData = null;
   var charsRepo = [];
   var matchMousemarker;
   
   var lasttimeWithData;
   var NUM_AUTO_REFERSH = 10;
   var IDLE_TIME_ALLOWED_IN_SEC = 300;
   var DEBUG = false;
   
   google.load("visualization", "1", {packages:["columnchart"]});

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
       
      //var lineImg = '${env.contextPath}/images/square.png';
      var lineImg = '${env.contextPath}/images/circle.png';
      lineIcon = new g.MarkerImage(lineImg,
                       new google.maps.Size(11, 11),
                       new google.maps.Point(0,0),                                  
                       new google.maps.Point(5, 5));    
       
      showMaps(map);
  
      google.setOnLoadCallback(drawChart);
      chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
      
	  <%--
      /*------------------------------------------------------+
       | Actions when user click Auto Refresh Button          | 
       +------------------------------------------------------*/     
      --%>	   
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
      
	  <%--
      /*------------------------------------------------------+
       | Functions                                            | 
	   | data is DisplayResponse 
	   | dispLocations 
       +------------------------------------------------------*/ 
      --%>
      function showMaps(map) {         
         
         jq.getJSON('${env.prefix}/displaycurrentlocation?groupId=gg1&days=5', function(data) {
             
             if (!data.dispLocations || data.dispLocations.length == 0) {  
                document.getElementById("side_bar").innerHTML = "No tracking yet";
                return;
             }
   	         if (hasTrackChanged(data)) {
 	            clearSideBar();
	            clearPolyline();
				clearMarker();
				clearChart();
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
	    
	       var color = line.color;
	     	       		
	       var html = line.html;
	       var label = line.label;
	       var length = 0;
	     
	     	       
	       mylocs = "data=" + data.dispLocations.length + ", locs="+locs.length + ", dispName="+dispName; 		
	       document.getElementById("mylocs").innerHTML = mylocs;
	     
	       var pts = [];
	       var lastPoint;
	       var lastLoc;
		     
           if (typeof gpolys[index] == 'undefined') {
              var polyOptions = {
                 strokeColor: color,
		         strokeOpacity: 1.0,
		      	 strokeWeight: 6	
              }
                 
              var poly = new g.Polyline(polyOptions);
              
	          poly.setMap(map);	         
	          gpolys[index] = poly;
           }
              
           var point = null;
           var hasNewLoc = false;
             
           var polyLocNum = gpolys[index].getPath().length;

		   if (changeChart == true && index == currentIndex) {
              replotHistoryChart(locs, polyLocNum, index, dispName, color);
			  changeChart = false;
		   }
	 	   
           //myLog("=*** length="+locs.length + ",index="+index +", dispName="+dispName + " ,polyLocNum="+polyLocNum + ",locs.length=" + locs.length + ",totalRuntime="+totalRuntime);
           for (var i=polyLocNum; i<locs.length; i++) {
                 point = new g.LatLng(locs[i].latitude/1.0e6, locs[i].longitude/1.0e6);
                 bounds.extend(point);                
                 
                 gpolys[index].getPath().push(point);                  
                 hasNewLoc = true;  			 
				 lasttimeWithData = new Date();	 				 
                 if (index == currentIndex) {
  					plotCurrentChart(i, index, dispName, color, locs[i].time, locs[i].speed, point)
                 }
           }
    
           if (hasNewLoc) {             
	           if (point != null) {		            
	             if (typeof gmarkers[index] == 'undefined') {
 	                 gmarkers[index] = new google.maps.Marker({
	                      position: point,
	                      map: map                 
	                 });  
	             }
	             else {
 	               gmarkers[index].setPosition(point);
	             } 	           	           
	          }	     
 	       }
 	     
	       createClickablePolyline(html, label, point, length, index);
  	    }); <%-- end of jq.each(data.dispLocations --%>
	 	  
	    <%-- fitBounds --%>	 
	    if (totalCycle<5) {
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
         google.maps.event.addListener(poly,'click', function(event) {     
           		 
      	    if (event) {
      	       point = event.latLng;
      	    }
      	    currentIndex = index;
      	    
      	    infowindow.setContent(contentString +" currentIndex: " + currentIndex);        
      	    infowindow.setPosition(point);
      	            
      	    infowindow.open(map);
      	    map.openInfoWindowHtml(point,html);                
         }); 
                  
        
         //square.png
         google.maps.event.addListener(poly,'mousemove', function(event) {     
            var locPoint;		 
      	    if (event) {
      	        locPoint = event.latLng;
      	    }
 	            
	        if (mousemarker == null) {
		       mousemarker = new google.maps.Marker({
	 	             position: locPoint,
		             map: map,
		             icon: lineIcon
		       });
	        } else {
	           mousemarker.setPosition(locPoint);
            }            
            
			var info = "Clicked Location: " + locPoint.lat().toFixed(6) + "," + locPoint.lng().toFixed(6);
	        info += " Speed " + html;
		    document.getElementById("locInfo").innerHTML = info;
		   /*
            infowindow.setContent(contentString);
      	    infowindow.setPosition(point);      	 	            
      	    infowindow.open(map);
      	    map.openInfoWindowHtml(point, html);
      	    */
      	 	      
         }); 
                    
   }  <%-- end of createClickablePolyline --%>
   
  
   
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
            <%-- skip jq.each --%>			
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
   	     var color = line.color;
   	 
         var glocation = locs[locs.length-1];
         createSideBar(index, color, line, glocation);
         
         lastTrackNames[dispName] = index;
         numLastTrackNames++;
      }); <%-- end of jq.each(data.dispLocations --%>
              
      showSideBar();
      //redrawSidebar = true;
   }
   
    
   function createSideBar(index, color, line, glocation) {
      //alert(" createSideBar ");
      var sidebar = "";
           
      var label = "<a href='javascript:google.maps.event.trigger(gpolys["+index+"],\"click\");'>"+line.label +"</a>";
      sidebar = '<input type="checkbox" id="poly'+index+'" checked="checked" onclick="togglePoly('+index+');">' + label + '<br />';
          
      sidebar += '&nbsp; StartTime: ' + line.startTime +  '<br />';
      sidebar += '&nbsp; Speed: ' + glocation.speed + '<br />';
	  
	  if (index == currentIndex) {
		  sidebar += '<input type="radio" name="radioPoly" id="radioPoly" checked onclick="selectRadioPoly('+index+');">' + '<span style="color:'+ color +'">Display Speed Chart</span>' + '<br />';
	  }
	  else {
		  sidebar += '<input type="radio" name="radioPoly" id="radioPoly" onclick="selectRadioPoly('+index+');">' + '<span style="color:'+ color +'">Display Speed Chart</span>' + '<br />';
	  }
	  sidebar += '<br>';
	  sidebar += '&nbsp; '  + 'poly_num=' + index + '<br />';
      sidebar += '<hr>';
          
      side_bar_arr[index] = sidebar;
   }
           
   function showSideBar() {     
       var ret = '';
       if (side_bar_arr && side_bar_arr.length>0) {        
          for (var i=0; i < side_bar_arr.length; i++) {
             ret += side_bar_arr[i];
          }                                       
       }
       document.getElementById("side_bar").innerHTML = ret;
   }
       
    
   function clearSideBar() {
       side_bar_arr = [];
       document.getElementById("side_bar").innerHTML = "No tracking";
   }
     
   function clearPolyline() {
        if (gpolys && gpolys.length>0) {                    
                for (var i=0; i < gpolys.length; i++) {
                  gpolys[i].setMap(null);
          	    }                     
                gpolys.length = 0;
         }
   }
    
   function clearChart() {
	    chartData = null;
   }
	
   function clearMarker() {
	   if (gmarkers && gmarkers.length>0) {
          for (var i=0; i < gmarkers.length; i++) {
      	     gmarkers[i].setMap(null);
      	  }                     
          gmarkers.length = 0;
       }
	}
	
    function clearMap() {  
       if (gpolys && gpolys.length>0) {                    
          for (var i=0; i < gpolys.length; i++) {
             gpolys[i].setMap(null);
      	  }                     
          gpolys.length = 0;
       }
    }
      
    function nextCycle() {         
         totalRuntime++;
         showMaps(map);
   
         var idleTime = new Date().getTime() - lasttimeWithData.getTime();
		 
		 document.getElementById("xtimer").innerHTML = "totalRuntime=" + totalRuntime + ", lasttimeWithData=" + lasttimeWithData.getTime() + ", idleTime="+idleTime;
         if (idleTime > IDLE_TIME_ALLOWED_IN_SEC*1000 ) {
            if (totalRuntime > NUM_AUTO_REFERSH) { 
               totalRuntime = 0;
	           document.getElementById('autoRefersh').style.visibility='visible';
               stopRotation(); 
            } 
         }	        
    }
      
    function stopRotation() {
         totalRuntime = 0;
      	 clearInterval(timer);
    }
      
    function startTimer() {
      	timer = setInterval(nextCycle, 3000);
    }
	
	function myLog(msg) {
	   if (DEBUG) {
	     document.getElementById("thelog").innerHTML = msg;
	   }
	}
  });<%-- jq(document).ready --%>
   
   <%--
   /**
    * show/hide polyline
    */
   --%>
   function togglePoly(poly_num) {    
      if (document.getElementById('poly'+poly_num)) {
         if (document.getElementById('poly'+poly_num).checked) {
            gpolys[poly_num].setMap(map);
         } else {
            gpolys[poly_num].setMap(null);
         }
      }
   }    
   
   function selectRadioPoly(poly_num) {    
  	  changeChart = true;
	  currentIndex = poly_num;
   }    
   
   function changePlot(newIndex) {
  	  changeChart = true;
	  currentIndex = newIndex;
   }
   
   <%--
     /*
	  *   do nothing, required by google.chart
	  */
   --%>   
   function drawChart() {
   }
 

   function plotCurrentChart(ii, index, dispName, color, time, speed, point) {       
      if (chartData == null) {
          chartData = new google.visualization.DataTable();
          chartData.addColumn('string', 'Sample');
	      chartData.addColumn('number', 'Speed');
          document.getElementById('chart_div').style.display = 'block'; 
       }
           
       charsRepo[ii] = point;
       
       chartData.addRow(['', speed]);
     
	   var options = {
         width: 740,
         height: 200,
         legend: 'none',
         title:  'Display Name: ' + dispName,
         titleY: 'Speed (mph)',
         titleX: 'Time',
         colors: [color,color],
         focusBorderColor: '#FFFFCC'
       };
	   
	   chart.draw(chartData, options);
	    
       google.visualization.events.addListener(chart, 'onmouseover', function(e) {
          if (matchMousemarker == null) {           
              matchMousemarker = new google.maps.Marker({
                position: charsRepo[e.row],
                map: map,
                icon: "http://maps.google.com/mapfiles/ms/icons/green-dot.png"
              });
           } else {
              matchMousemarker.setPosition(charsRepo[e.row]);
          }
      });
   }
  
    
   function replotHistoryChart(locs, polyLocNum, index, dispName, color) {
	  var locPoint;
	  chartData = null;
	  
	  for (var i=0; i<polyLocNum; i++) {
	     locPoint = new google.maps.LatLng(locs[i].latitude/1.0e6, locs[i].longitude/1.0e6);
		 bounds.extend(locPoint); 
	     plotCurrentChart(i, index, dispName, color, locs[i].time, locs[i].speed, locPoint);
      }
   }
   
   // Remove the green rollover marker when the mouse leaves the chart
   function clearMatchMousemarker() {
       if (matchMousemarker != null) {
         matchMousemarker.setMap(null);
         matchMousemarker = null;
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
            <div id="side_bar" style="height: 450px; overflow:auto;"></div>
          </td>
          <td valign="top" width="760" align="left">
		     <div id="container">  	   
			    <div id="thelog"/>	            
             </div> 
		     <div id="container">  	   
			    <div id="locInfo" style="width:740px; overflow:auto; background-color:lightgrey;"/>	            
             </div> 			 
             <div id="container">  	   			    
	            <div id="map_canvas" style="width:740px; height:350px"></div>
             </div> 
             <div id="container">  
	           <div id="chart_div" style="width:740px; height:200px" onmouseout="clearMatchMousemarker()"></div>
             </div>
          </td>
       </tr>
   </table>
</div>
      
        
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/common/i_footer.jspf"%></div>
</div> 

  
<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
 