/*
  http://localhost/tracking/jsp/tracking/trackingscript.jsp
*/
 
     
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
   
   var lasttimeWithData = null;
   var NUM_AUTO_REFERSH = 10;
   var IDLE_TIME_ALLOWED_IN_SEC = 300;
   var DEBUG = false;
   
   /*GDispItem*/
   var lastDispLocations = null;
   
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
       
 	  var lineImg = '/tracking/images/circle.png';
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
	  
	  
      jq('#clearDebugPanel').click(function(){           
         clearLog();   
      });
	  
      jq('#clearMap').click(function(){           
         clearMap();   
      });
      
	  <%--
      /*------------------------------------------------------+
       | Functions                                            | 
	   | data is DisplayResponse                              |
	   | lastDispLocations kept the last data                 |  
	   | dispLocations                                        |
       +------------------------------------------------------*/ 
      --%>
      function showMaps(map) {  
    
         //jq.getJSON('${env.prefix}/displaycurrentlocation?groupId=${env.customerProfile.groupId}&days=5', function(data) {
		 //jq.getJSON('http://www.gogwt.com/tracking/en-us/displaycurrentlocation?groupId=g5&days=5', function(data) {		 
		 var url = ajaxUrl;
	     jq.getJSON(url, function(data) {    
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
           
			 if (DEBUG) {
	           document.getElementById('clearDebugPanel').style.visibility='visible';
	         }
	         else {
	           document.getElementById('clearDebugPanel').style.visibility='hidden';			
             }
	  
             showLines(map, data);   
             lastDispLocations = data.dispLocations;
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
	        
	     	       
	       //mylocs = "data=" + data.dispLocations.length + ", locs="+locs.length + ", dispName="+dispName; 		
		   mylocs = "Display Name: "+dispName; 		
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
 	                gmarkers[index] = new google.maps.Marker({position: point, map: map});  
	             }
	             else {
 	                gmarkers[index].setPosition(point);
	             } 	           	           
	          }	     
 	       }
 	     
	       addClickable(html, color, label, point, length, index);
  	    }); <%-- end of jq.each(data.dispLocations --%>
	 	  
	    <%-- fitBounds --%>	 
	    if (totalCycle<5) {
	      map.fitBounds(bounds);
	      totalCycle++;
	    }
      }

      /**
	   *  Google Map V3 polyline click and mousemove events only display static data.
	   *  It is not like marker
	   */
      function addClickable(content, color, label, point, length, index) {              
         
         var poly = gpolys[index];         
         var html = content;
          
         var contentString = content;
         google.maps.event.addListener(poly,'click', function(event) {     
           		 
      	    if (event) {
      	       point = event.latLng;
      	    }

      	    infowindow.setContent(contentString +" currentIndex: " + index);        
      	    infowindow.setPosition(point);
      	            
      	    infowindow.open(map);
      	    //map.openInfoWindowHtml(point,html);                
         }); 
         
         google.maps.event.addListener(poly,'mousemove', function(event) {          
		    document.getElementById("locInfo").innerHTML = "";
		 });
        
         //square.png
         google.maps.event.addListener(poly,'mousemove', function(event) {     
            var locPoint;		 
      	    if (event) {
      	        locPoint = event.latLng;
      	    }
 	            
	        if (mousemarker == null) {
		       mousemarker = new google.maps.Marker({position: locPoint, map: map, icon: lineIcon});
	        } else {
	           mousemarker.setPosition(locPoint);
            }            
    		
            <%-- show path mouse over information: path location, speed, display name --%>
			var info = "Path Location: " + locPoint.lat().toFixed(6) + "," + locPoint.lng().toFixed(6);
	         
			var speedInfo = getSpeedInfo(index, locPoint.lat()*1.0e6, locPoint.lng()*1.0e6);
			if (speedInfo != null) {
			   info += speedInfo;
			}			 
			info += "<br>Display Name: " + '<span style="color:'+ color +'">' + html + '</span>' ;
			
			document.getElementById("locInfo").innerHTML = info;
         }); 
                    
   }  <%-- end of createClickablePolyline --%>
   
   <%--
       s1 ---- s --- s2
       speed s = (s1*dist(s2-s) + s2*dist(s-s1))/(dist(s2-s)+dist(s-s1))
   --%>
   function getSpeedInfo(index, lat, lng) {      
      if (lastDispLocations != null) {	      
	      var selectedLat = lat | 0;
		  var selectedLng = lng | 0;
		  var locs = null;
		  
		  var selectedTrackList = lastDispLocations[index].locs;
		  var startLocIndex=selectedTrackList.length;
		  var endLocIndex=0;		  
		  var hasSameLoc = false;		  
		  for (var i=1; i<selectedTrackList.length; i++) {
		      if (isSameLocation(selectedTrackList[i-1], selectedTrackList[i])) {
			      if (!hasSameLoc) {
			         startLocIndex = i-1;
			      }
				  endLocIndex = i;
				  hasSameLoc = true;
				  continue;
			  }
			  if (hasSameLoc) {
			     hasSameLoc = false;
			     if (isBetween(selectedLat, selectedLng, selectedTrackList[startLocIndex], selectedTrackList[endLocIndex])) {
			         break;
			     }			    
			  }
			  else {
			     if (isBetween(selectedLat, selectedLng, selectedTrackList[i-1], selectedTrackList[i])) {
				    startLocIndex = i-1;
					endLocIndex = i;
			        break;
			     }
			  }
		  }
		  
		  var insertedSpeed = "";
		  
		  if (endLocIndex == 0) {		     
		     return null;			 
		  }
		  
		  //insertedSpeed += "startLoc=" + startLocIndex + ",speed="+selectedTrackList[startLocIndex].speed +",endLocIndex=" + endLocIndex + ",speed="+selectedTrackList[endLocIndex].speed +" -- "  ;
		   
		  insertedSpeed += (selectedTrackList[startLocIndex].speed + selectedTrackList[endLocIndex].speed)/2.00;
		  
		  var speedTime = "<br>Date Time: " + formatDateFromTime((selectedTrackList[startLocIndex].time + selectedTrackList[endLocIndex].time)/2.00);
		  speedTime += "<br>Speed: " + meterToFeet(insertedSpeed).toFixed(2) + " (feet/s), " + meterToMPH(insertedSpeed).toFixed(2) + " (mph)";
		  return speedTime;
	      
	  }
   }
    
   function isSameLocation(latlng1, latlng2) {
      if (latlng1.latitude == latlng2.latitude && latlng1.longitude == latlng2.longitude) {
	     return true;
	  }
	  return false;
   }
   function isBetween(selectedLat, selectedLng, latlng1, latlng2) {
      var radio = 10000;
	  
	  if (latlng1.latitude == latlng2.latitude) {
	     if (latlng1.longitude > latlng2.longitude) {
		    return (latlng1.longitude>selectedLng && selectedLng>latlng2.longitude);
		 }
		 else {
		    return (latlng2.longitude>selectedLng && selectedLng>latlng1.longitude);
		 }
	  }
      
	  if (latlng1.longitude == latlng2.longitude) {
	      if (latlng1.latitude > latlng2.latitude) {
		     return (latlng1.latitude>selectedLat && selectedLat>latlng2.latitude);
		  }
		  else {
		     return (latlng2.latitude>selectedLat && selectedLat>latlng1.latitude);
		  }
	  }
      
	  var inBetween = true;
	  
      if (latlng1.latitude > latlng2.latitude) {
	     inBetween = !(selectedLat > latlng1.latitude || selectedLat < latlng2.latitude);
	  }
	  else {
	     inBetween = !(selectedLat > latlng2.latitude || selectedLat < latlng1.latitude);
	  }
	  
	  if (inBetween == false) {
	     return false;
	  }
	  
	  if (latlng1.longitude > latlng2.longitude) {
	      inBetween = !(selectedLng > latlng1.longitude || selectedLat < latlng2.longitude);
	  }
	  else {
	      inBetween = !(selectedLng > latlng2.longitude || selectedLat < latlng1.longitude);
	  }
	  
	  return inBetween;	  
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
   }
   
    
   function createSideBar(index, color, line, glocation) {
      var sidebar = "";
           
      var label = "<a href='javascript:google.maps.event.trigger(gpolys["+index+"],\"click\");'>"+line.label +"</a>";
      sidebar = '<input type="checkbox" id="poly'+index+'" checked="checked" onclick="togglePoly('+index+');">' + label + '<br />';
          
      sidebar += '&nbsp; Start Time: ' + formatDateFromTime(line.startTime) +  '<br />';
      sidebar += '&nbsp; Current Speed: ' + glocation.speed + '<br />';
	  
	  if (index == currentIndex) {
		  sidebar += '<input type="radio" name="radioPoly" id="radioPoly" checked onclick="selectRadioPoly('+index+');">' + '<span style="color:'+ color +'">Display Speed Chart</span>' + '<br />';
	  }
	  else {
		  sidebar += '<input type="radio" name="radioPoly" id="radioPoly" onclick="selectRadioPoly('+index+');">' + '<span style="color:'+ color +'">Display Speed Chart</span>' + '<br />';
	  }
	  <%--
	  sidebar += '<br>';
	  sidebar += '&nbsp; '  + 'poly num:' + index + '<br />';
      sidebar += '<hr>';
      --%>    
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
	     
		 if (MULTIPLE_RETRIEVE == false) {
		     stopRotation(); 
		 }
		 
		 var idleTime=0;
	     if (lasttimeWithData != null) {
            idleTime = new Date().getTime() - lasttimeWithData.getTime();
			
			//.format("mm/dd/yy h:MM:ss")
			document.getElementById("xtimer").innerHTML = "Total Runtime: " + totalRuntime + "<br> Last With Data: " + formatDateFromTime(lasttimeWithData.getTime()) + "<br>Idle Time: "+idleTime +" (s)";
			
			if (idleTime > IDLE_TIME_ALLOWED_IN_SEC*1000 ) {
               if (totalRuntime > NUM_AUTO_REFERSH) { 
                  totalRuntime = 0;
	              document.getElementById('autoRefersh').style.visibility='visible';
                  stopRotation(); 
               } 
            }	       
	 	 }
		 else {
		    document.getElementById("xtimer").innerHTML = "totalRuntime: " + totalRuntime ;
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
	    //refersh 10s
      	timer = setInterval(nextCycle, 10000);
    }
	
	var logMsg = '';
	function myLog(msg) {
	   myLog(msg,false);
	}
	function myLog(msg, aggregation) {
	   if (DEBUG) {
	     if (aggregation) {
		    logMsg = msg + '<br>' + logMsg;
		 }
		 else {
		    logMsg = msg;
		 }
	     document.getElementById("thelog").innerHTML = logMsg;
	   }
	}
	function clearLog() {
	  myLog("",false); 
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
   
   function formatDateFromTime(theTimeInMillsec) {
	    var theDateTime = new Date(theTimeInMillsec);
	    var yyyy = theDateTime.getFullYear()+"";
		var yy = yyyy.substring(2);
		
        return theDateTime.getMonth() +"/" + theDateTime.getDate()+"/" + yy + " " + theDateTime.getHours() + ":" + theDateTime.getMinutes();
   }		
   
   function meterToFeet(meterPerSec) {
       return meterPerSec*3.2808399;
   }
   function meterToMPH(meterPerSec) {
       return meterPerSec*2.23693629;
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
 
   var options = null;
   function plotCurrentChart(ii, index, dispName, color, time, speed, point) {       
      if (chartData == null) {
          chartData = new google.visualization.DataTable();
          chartData.addColumn('string', 'Sample');
	      chartData.addColumn('number', 'Speed');
          document.getElementById('chart_div').style.display = 'block'; 
       }
           
       charsRepo[ii] = point;
       
       //chartData.addRow(['', meterToMPH(speed)]);
	   if (ii%10 == 0) {
          chartData.addRow([ii+1+'', meterToMPH(speed)]);
	   }
	   else {
	     chartData.addRow(['', meterToMPH(speed)]);
	   }
	 
	   if (options == null) {
	      options = {
             width: 740,
             height: 200,
             legend: 'none',
             title:  'Display Name: ' + dispName,
             titleY: 'Speed (mph)',
             titleX: 'Time',
             colors: [color,color],
             focusBorderColor: '#FFFFCC'
          };
	   }
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
   