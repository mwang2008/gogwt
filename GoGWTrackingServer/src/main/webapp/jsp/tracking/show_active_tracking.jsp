<%--
   http://localhost/tracking/en-us/addlocation
   
--%>
<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
  
<html>
 <head>   
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Active Tracks XXX3333 </title>       
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
   
   var NUM_AUTO_REFERSH = 3;
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
              
             var hasTrackingChanged = hasTrackChanged(data); 
             
	         if (hasTrackingChanged) {
 	            clearSideBar();
	            //clearMap();
	            clearPolyline();
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
             
           //alert("=*** length="+locs.length + ",index="+index +", dispName="+dispName + " ,polyLocNum="+polyLocNum +",totalRuntime="+totalRuntime);
           for (var i=polyLocNum; i<locs.length; i++) {
                 //alert(" --- ddd i="+i + ", lat="+locs[i].latitude/1.0e6 + ",lng=" + locs[i].longitude/1.0e6);
                 point = new g.LatLng(locs[i].latitude/1.0e6, locs[i].longitude/1.0e6);
                 bounds.extend(point);                
                 
                 gpolys[index].getPath().push(point);                  
                 hasNewLoc = true;
                 
				 if (changeChart == true) {
				     replotNewChart(data, i, currentIndex, dispName, color);
					 changeChart = false;
				 }
				 
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
            
            //replotChart(currentIndex);
            
         }); 
                  
        
         //square.png
         google.maps.event.addListener(poly,'mousemove', function(event) {      	      	    				
      	    if (event) {
      	        point = event.latLng;
      	    }
 	            
	        if (mousemarker == null) {
		       mousemarker = new google.maps.Marker({
	 	             position: point,
		             map: map,
		             icon: lineIcon
		       });
	        } else {
	           mousemarker.setPosition(point);
            }            
           
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
     
    function clearPolyline(map) {
        if (gpolys && gpolys.length>0) {                    
                for (var i=0; i < gpolys.length; i++) {
                  gpolys[i].setMap(null);
          	    }                     
                gpolys.length = 0;
         }
    }
    
    function clearMap(map) {  
         if (gpolys && gpolys.length>0) {                    
            for (var i=0; i < gpolys.length; i++) {
              gpolys[i].setMap(null);
      	    }                     
            gpolys.length = 0;
         }
         
         /*
         if (gmarkers && gmarkers.length>0) {
             for (var i=0; i < gmarkers.length; i++) {
      	        gmarkers[i].setMap(null);
      	     }                     
             gmarkers.length = 0;
         }
         */
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
   
   <%--
   /**
    * show/hide polyline
    */
   --%>
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
   
   function selectRadioPoly(poly_num) {    
      alert(" selectRadioPoly togglePoly poly_num="+poly_num + ", gpolys.length=" + gpolys.length);
	  changeChart = true;
	  currentIndex = poly_num;
   }    
   
   function changePlot(newIndex) {
      alert("xxx  newIndex=" + newIndex);
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

   var chardata = [];
   var chartset = [];
   var charsRepo = [];
   var matchMousemarker;
   var numOfRow = 0;
   var chartLine = null;
 
   /**
    *  setValue(row, col, value)
    */
   function plotChart(ii, index, dispName, color, time, speed, point) {
      if (data == null) {
         data = new google.visualization.DataTable();
		 data.addColumn('string', '');
		
         //data.addColumn('string', 'Year');
	     //data.addColumn('number', 'Sales', );
		
	     //data.addColumn('number', 'Expenses');
      }
      
	  /*
	  if (typeof data.getColumnLabel(index) == 'undefined') {
	     data.addColumn('number', dispName, index);
	  }
	  alert("label="+ data.getColumnLabel(index));
	  */
	 
	  if (data.getColumnLabel(index) != dispName) {
	     alert("label="+ data.getColumnLabel(index) + ", dispName="+dispName);
	     data.addColumn('number', dispName, index);
		 //data.insertColumn(index, 'string', dispName);
	  }
	 
	  
	  
      //alert("r1="+numOfRow + ",index="+index + ",ii="+ii);
      //data.addRows(4);
      data.addRow();     
      data.setValue(ii, 0, '2004'+ii);
      //data.setValue(ii, 1, 1000+ii*100);
      data.setValue(ii, index+1, 1000+ii*100+index*300);
      
      //data.setValue(0, 2, 400);
      //data.setValue(1, 2, 460);
      //data.setValue(2, 2, 580);
      //data.setValue(3, 2, 540);      
            
      if (chartLine == null) {
          chartLine = new google.visualization.LineChart(document.getElementById('chart_div'));
      }
      chartLine.draw(data, {width: 400, height: 240, title: 'Company Performance'});
   }
    
   function plotChartXXXX(ii, index, dispName, color, time, speed, point) {
        /*   
        var data = new google.visualization.DataTable();
        
        data.addColumn('string', 'Year');
        data.addColumn('number', 'Sales');
        data.addColumn('number', 'Expenses');
         */
       if (data == null) {
          data = new google.visualization.DataTable();
          data.addColumn('string', 'Sample');
	      data.addColumn('number', 'Elevation');
	  
          //document.getElementById('chart_div').style.display = 'block'; 
       }
       
        //alert("r1="+numOfRow + ",index="+index + ",ii="+ii);
        //data.addRows(4);
        data.addRows(10);
        //data.setValue(index, ii+1, 10+ii);
        //data.setValue(0, ii+1, 10+ii);
        //data.setValue(0, ii+1, 10+ii);
        //data.setValue(0, ii+1, 10+ii);
        
        data.setValue(0, 0, '2004');
	data.setValue(0, 1, 1000);
        data.setValue(0, 2, 400);
        
        data.setValue(1, 0, '2005');
	data.setValue(1, 1, 1170);
        data.setValue(1, 2, 460);
        
        
        if (chart == null) {
	    chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	}
        
        chart.draw(data, {width: 400, height: 240, title: 'Company Performance'});
        
         /*
	        data.addRow();  // Add an empty row
		data.addRow(['Hermione', new Date(1999,0,1)]); // Add a row with a string and a date value.
		
		// Add a row with two cells, the second with a formatted value.
		data.addRow(['Hermione', {v: new Date(1999,0,1),
		                          f: 'January First, Nineteen ninety-nine'}]);
		
		data.addRow(['Col1Val', null, 'Col3Val']); // Second column is undefined.
	        data.addRow(['Col1Val', , 'Col3Val']);     // Same as previous.
        */
        /*
         alert("r1="+numOfRow + ",index="+index + ",ii="+ii);
        data.setValue(0, 0, '2004');
        data.setValue(0, 1, 1000);
        data.setValue(0, 2, 400);
      
        numOfRow++;
      alert("r2="+numOfRow+ ",index="+index + ",ii="+ii);
        data.setValue(1, 0, '2005');
        data.setValue(1, 1, 1170);
        data.setValue(1, 2, 460);
        
        numOfRow++;
        
        data.addRows(numOfRow);
        */
       
        
         /*       
               data.setValue(2, 0, '2006');
               data.setValue(2, 1, 860);
               data.setValue(2, 2, 580);
               
               data.setValue(3, 0, '2007');
               data.setValue(3, 1, 1030);
               data.setValue(3, 2, 540);
 */      
            
               

   }
   
    function drawChart222() {
           var data = new google.visualization.DataTable();
           data.addColumn('string', 'Year');
           data.addColumn('number', 'Sales');
           data.addColumn('number', 'Expenses');
           data.addRows(4);
           data.setValue(0, 0, '2004');
           data.setValue(0, 1, 1000);
           data.setValue(0, 2, 400);
           data.setValue(1, 0, '2005');
           data.setValue(1, 1, 1170);
           data.setValue(1, 2, 460);
           data.setValue(2, 0, '2006');
           data.setValue(2, 1, 860);
           data.setValue(2, 2, 580);
           data.setValue(3, 0, '2007');
           data.setValue(3, 1, 1030);
           data.setValue(3, 2, 540);
   
           var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
           chart.draw(data, {width: 400, height: 240, title: 'Company Performance'});
         }


   function plotChartXXX(ii, index, dispName, color, time, speed, point) {
       
       if (typeof chardata[index] == 'undefined') {
        
          chardata[index] = new google.visualization.DataTable();
          chardata[index].addColumn('string', 'Sample');
	      chardata[index].addColumn('number', 'Elevation');
	  
          document.getElementById('chart_div').style.display = 'block'; 
       }
       
     
       charsRepo[ii] = point;
       
       chardata[index].addRow(['', speed]);
     
       chartset[index].draw(chardata[index], {
         width: 512,
         height: 200,
         legend: 'none',
         title:  'Display Name: ' + dispName,
         titleY: 'Speed (mph)',
         titleX: 'Time',
         colors: [color, color],
         focusBorderColor: '#FFFFCC'          
       });   	  
       
       google.visualization.events.addListener(chardata[index], 'onmouseover', function(e) {
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
   
    
   var chartData = null;
   var charsRepo = [];
   var matchMousemarker;
   function plotCurrentChart(ii, index, dispName, color, time, speed, point) {       
       alert(" plotCurrentChart ii=" + ii + ", color="+color);   
	   
       if (chartData == null) {
	      alert(" inside chartData == null");
          chartData = new google.visualization.DataTable();
          chartData.addColumn('string', 'Sample');
	      chartData.addColumn('number', 'Elevation');
          document.getElementById('chart_div').style.display = 'block'; 
       }
       
     
       charsRepo[ii] = point;
       
       chartData.addRow(['', speed]);
     
	   var options = {
         width: 512,
         height: 200,
         legend: 'none',
         title:  'Display Name: ' + dispName,
         titleY: 'Speed (mph)',
         titleX: 'Time',
         colors: [color,color],
         focusBorderColor: '#FFFFCC'
         //focusBorderColor: color
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
  
   <%-- 
   /**
    *  replotNewChart
	*
	*/
   --%>
   function replotNewChart(mydata, ii, currentIndex, dispName, color) {
       var done = false;
	   
	   alert(" replotNewChart currentIndex=" + currentIndex + ", color="+color + ", ii=" + ii);
		
       jq.each(mydata.dispLocations, function(index, dispItem) {
	      alert(" index = " + index + ", currentIndex=" + currentIndex);
		  
		  if (index == currentIndex) {
		      var locs = dispItem.locs;
			  var locPoint;

			  chartData = null;
	          if (chartData== null) {
                 alert(" char chartData is null");
              }
	          alert(" set chartData to be null insideer index = " + index + ", currentIndex=" + currentIndex);
			  
	          for (var i=0; i<ii; i++) {
			     alert(" i=" + i + ", dispName XXX = " + dispItem.dispName);	
	             if (done == false) {
	                done = true;
	             }
	             locPoint = new google.maps.LatLng(locs[i].latitude/1.0e6, locs[i].longitude/1.0e6);
				 alert(" here 1");
		         bounds.extend(locPoint); 
				 alert(" here 2");
	             plotCurrentChart(i, index, dispName, color, locs[i].time, locs[i].speed, locPoint);
                 alert(" here 3");	  
	          }
		  }
	  });
	
	  return done;
   }
  
   function replotChart(theIndex) {
          if (data == null) {
             data = new google.visualization.DataTable();
             data.addColumn('string', 'Sample');
   	  data.addColumn('number', 'Elevation');
   	  
             document.getElementById('chart_div').style.display = 'block'; 
          }
          
        
          charsRepo[theIndex] = point;
          
          data.addRow(['', speed]);
        
          chart.draw(data, {
            width: 512,
            height: 200,
            legend: 'none',
            title:  'Display Name: ' + dispName,
            titleY: 'Speed (mph)',
            titleX: 'Time',
            colors: [color,color],
            focusBorderColor: '#FFFFCC'
            //focusBorderColor: color
          });   	  
          
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
            <div id="side_bar" style="height: 450px; overflow:auto"></div>
          </td>
          <td valign="top" width="760" align="left">
             <div id="container">  	   
	       <div id="map_canvas" style="width:700px; height:350px"></div>
             </div> 
             <div id="container">  
	        <div id="chart_div" style="width:512px; height:200px" onmouseout="clearMatchMousemarker()"></div>
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
 