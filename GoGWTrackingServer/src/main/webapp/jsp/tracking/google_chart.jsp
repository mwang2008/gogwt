<%--
  http://localhost/tracking/jsp/tracking/google_chart.jsp
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
 Copyright 2010 Google Inc. 
 Licensed under the Apache License, Version 2.0: 
 http://www.apache.org/licenses/LICENSE-2.0 
 -->
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Google Maps JavaScript API v3 Example: Elevation</title>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
  var map = null;
  var chart = null;
  
  var geocoderService = null;
  var elevationService = null;
  var directionsService = null;
  
  var mousemarker = null;
  var markers = [];
  var polyline = null;
  var elevations = null;
  
  var SAMPLES = 256;

  var examples = [{
    // Lombard St
    latlngs: [
      [37.801000, -122.426499],
      [37.802051, -122.419418],
      [37.802729, -122.413989]
    ],
    mapType: google.maps.MapTypeId.ROADMAP,
    travelMode: 'driving'
  },{
    // Mount Everest
    latlngs: [
      [27.973323, 86.908035],
      [28.020614, 86.960906]
    ],
    mapType: google.maps.MapTypeId.TERRAIN,
    travelMode: 'direct'
  },{
    // Challenger Deep
    latlngs: [
      [9.764009, 143.076632],
      [11.932658, 142.373507]
    ],
    mapType: google.maps.MapTypeId.SATELLITE,
    travelMode: 'direct'
  },{
    // Death Valley
    latlngs: [
      [37.040952, -117.300314],
      [35.896862, -116.654868],
      [35.972751, -116.270689]
    ],
    mapType: google.maps.MapTypeId.TERRAIN,
    travelMode: 'driving'
  },{
    // Grand Canyon
    latlngs: [
      [36.012196, -112.100348],
      [36.221866, -112.098975],
    ],
    mapType: google.maps.MapTypeId.TERRAIN,
    travelMode: 'direct'
  },{
    // Uluru
    latlngs: [
      [-25.34696, 131.022323],
      [-25.34060, 131.045927]
    ],
    mapType: google.maps.MapTypeId.SATELLITE,
    travelMode: 'direct'
  }];

  // Load the Visualization API and the piechart package.
  google.load("visualization", "1", {packages: ["columnchart"]});
  
  // Set a callback to run when the Google Visualization API is loaded.
  google.setOnLoadCallback(initialize);
  
  function initialize() {
    var myLatlng = new google.maps.LatLng(15, 0);
    var myOptions = {
      zoom: 1,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.TERRAIN
    }

    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
    
    geocoderService = new google.maps.Geocoder();
    elevationService = new google.maps.ElevationService();
    directionsService = new google.maps.DirectionsService();
    
    google.maps.event.addListener(map, 'click', function(event) {
      addMarker(event.latLng, true);
    });
    
    google.visualization.events.addListener(chart, 'onmouseover', function(e) {
      if (mousemarker == null) {
        mousemarker = new google.maps.Marker({
          position: elevations[e.row].location,
          map: map,
          icon: "http://maps.google.com/mapfiles/ms/icons/green-dot.png"
        });
      } else {
        mousemarker.setPosition(elevations[e.row].location);
      }
    });

    loadExample(0);
  }
  
  // Takes an array of ElevationResult objects, draws the path on the map
  // and plots the elevation profile on a GViz ColumnChart
  function plotElevation(results) {
    elevations = results;
    
    var path = [];
    for (var i = 0; i < results.length; i++) {
      path.push(elevations[i].location);
    }
    
    if (polyline) {
      polyline.setMap(null);
    }
    
    polyline = new google.maps.Polyline({
      path: path,
      strokeColor: "#000000",
      map: map});
    
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Sample');
    data.addColumn('number', 'Elevation');
    for (var i = 0; i < results.length; i++) {
      data.addRow(['', elevations[i].elevation]);
    }

    document.getElementById('chart_div').style.display = 'block';
    chart.draw(data, {
      width: 512,
      height: 200,
      legend: 'none',
      titleY: 'Elevation (m)',
      focusBorderColor: '#00ff00'
    });
  }
  
  // Remove the green rollover marker when the mouse leaves the chart
  function clearMouseMarker() {
    if (mousemarker != null) {
      mousemarker.setMap(null);
      mousemarker = null;
    }
  }
  
  // Geocode an address and add a marker for the result
  function addAddress() {
    var address = document.getElementById('address').value;
    geocoderService.geocode({ 'address': address }, function(results, status) {
      document.getElementById('address').value = "";
      if (status == google.maps.GeocoderStatus.OK) {
        var latlng = results[0].geometry.location;
        addMarker(latlng, true);
        if (markers.length > 1) {
          var bounds = new google.maps.LatLngBounds();
          for (var i in markers) {
            bounds.extend(markers[i].getPosition());
          }
          map.fitBounds(bounds);
        } else {
          map.fitBounds(results[0].geometry.viewport);
        }
      } else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
        alert("Address not found");
      } else {
        alert("Address lookup failed");
      }
    })
  }
  
  // Add a marker and trigger recalculation of the path and elevation
  function addMarker(latlng, doQuery) {
    if (markers.length < 10) {
      
      var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: true
      })
      
      google.maps.event.addListener(marker, 'dragend', function(e) {
        updateElevation();
      });
      
      markers.push(marker);
      
      if (doQuery) {
        updateElevation();
      }
      
      if (markers.length == 10) {
        document.getElementById('address').disabled = true;
      }
    } else {
      alert("No more than 10 points can be added");
    }
  }
  
  // Trigger the elevation query for point to point
  // or submit a directions request for the path between points
  function updateElevation() {
    if (markers.length > 1) {
      var travelMode = document.getElementById("mode").value;
      if (travelMode != 'direct') {
        calcRoute(travelMode);
      } else {
        var latlngs = [];
        for (var i in markers) {
          latlngs.push(markers[i].getPosition())
        }
        elevationService.getElevationAlongPath({
          path: latlngs,
          samples: SAMPLES
        }, plotElevation);
      }
    }
  }
  
  // Submit a directions request for the path between points and an
  // elevation request for the path once returned
  function calcRoute(travelMode) {
    var origin = markers[0].getPosition();
    var destination = markers[markers.length - 1].getPosition();
    
    var waypoints = [];
    for (var i = 1; i < markers.length - 1; i++) {
      waypoints.push({
        location: markers[i].getPosition(),
        stopover: true
      });
    }
    
    var request = {
      origin: origin,
      destination: destination,
      waypoints: waypoints
    };
   
    switch (travelMode) {
      case "bicycling":
        request.travelMode = google.maps.DirectionsTravelMode.BICYCLING;
        break;
      case "driving":
        request.travelMode = google.maps.DirectionsTravelMode.DRIVING;
        break;
      case "walking":
        request.travelMode = google.maps.DirectionsTravelMode.WALKING;
        break;
    }
    
    directionsService.route(request, function(response, status) {
      if (status == google.maps.DirectionsStatus.OK) {
        elevationService.getElevationAlongPath({
          path: response.routes[0].overview_path,
          samples: SAMPLES
        }, plotElevation);
      } else if (status == google.maps.DirectionsStatus.ZERO_RESULTS) {
        alert("Could not find a route between these points");
      } else {
        alert("Directions request failed");
      }
    });
  }

  // Trigger a geocode request when the Return key is
  // pressed in the address field
  function addressKeyHandler(e) {
    var keycode;
    if (window.event) {
      keycode = window.event.keyCode;
    } else if (e) {
      keycode = e.which;
    } else {
      return true;
    }
    
    if (keycode == 13) {
       addAddress();
       return false;
    } else {
       return true;
    }
  }
  
  function loadExample(n) {
    reset();
    map.setMapTypeId(examples[n].mapType);
    document.getElementById('mode').value = examples[n].travelMode;
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < examples[n].latlngs.length; i++) {
      var latlng = new google.maps.LatLng(
        examples[n].latlngs[i][0],
        examples[n].latlngs[i][1]
      );
      addMarker(latlng, false);
      bounds.extend(latlng);
    }
    map.fitBounds(bounds);
    updateElevation();
  }
  
  // Clear all overlays, reset the array of points, and hide the chart
  function reset() {
    if (polyline) {
      polyline.setMap(null);
    }
    
    for (var i in markers) {
      markers[i].setMap(null);
    }
    
    markers = [];
    
    document.getElementById('chart_div').style.display = 'none';
  }

</script>
<style>
body {
  font-family: sans-serif
}

#map_canvas {
  margin: 5px 0px 5px 0px;
}
</style>
</head>

<body>
  <div style="width: 512px; text-align: center">Add points by clicking on the map or entering an address</div>
  <div id="map_canvas" style="border: 1px solid black; width:512px; height:400px"></div>
  <table style="width:512px;">
  <tr>
    <td>Address: <input type="text" id="address" size="15" onkeypress="return addressKeyHandler(event)"/></td>
    <td style="text-align: center">
      Mode of travel:
      <select id="mode" onchange="updateElevation()">

        <option value="direct">Direct</option>
        <option value="driving">Driving</option>
        <option value="bicycling">Bicycling</option>
        <option value="walking">Walking</option>
      </select>
    </td>
    <td style="text-align: right">

      <input type="button" value="Clear points" onclick="reset()"/>
    </td>
  </tr>
  </table>
  <table style="width:512px; font-size: small;">
    <tr>
      <td style="text-align: center"><a href="#" onclick="loadExample(1); return false">Mount Everest</a></td>
      <td style="text-align: center"><a href="#" onclick="loadExample(2); return false">Challenger Deep</a></td>

      <td style="text-align: center"><a href="#" onclick="loadExample(3); return false">Death Valley</a></td>
      <td style="text-align: center"><a href="#" onclick="loadExample(4); return false">Grand Canyon</a></td>
      <td style="text-align: center"><a href="#" onclick="loadExample(5); return false">Uluru</a></td>
    </tr>
  </table>
  <div id="chart_div" style="width:512px; height:200px" onmouseout="clearMouseMarker()"></div>
</body>
</html>

