<%--
  http://localhost/gwtbooking/jquery_test.jsp
--%>

<!DOCTYPE html>
<html>
<head>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
   
	
  <script>
  var jq = jQuery.noConflict();
  
  jq(document).ready(function() {
       
    	jq('#autocomplete').autocomplete({
     	    
		   source: function(request, response) {
		     // alert(" here 1");
		      
			   jq.getJSON('http://localhost/gwtbooking/en-us/getkeywords', {key: request.term}, function(myreturn) {
			     // alert(" herere 3" + myreturn.data);
			   	 
			   	response(jq.map(myreturn.data, function(destination) {
					   return {
						   label: destination.keyword,
						   value: destination.searchkey,						    
						   lat: destination.lat,
						   lng: destination.lng
					   };
				   })); //</response
				    
			}); //</getJSON
		  }
		 
		,minLength: 3
		,select: function(event, ui) {
		   //alert(" here 2");
		   jq('#lat').val(ui.item.lat);
			//('#latitude').val(ui.item.latitude);
			//('#longitude').val(ui.item.longitude);
			// call default submit action , geocode are set
			//callDefaultFormSubmit = true;
		}
	 });
	  
     
  });
  </script>
</head>
<body style="font-size:62.5%;">
<input id="lat" />  
<input id="autocomplete" />

</body>
</html>
