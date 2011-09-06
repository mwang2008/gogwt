/*
 * i_header_script.js
 * 
 */

var mouse_is_inside = false;
var jq = jQuery.noConflict();

jq(document).ready(function() {
   // Method to show Login window
   openLoginForm();
	
   // Method to close PCR Login window
   closePCRForm();

   // Method to close the Login window when click
   hideLoginWindow();

}); // ready

/* Method to show Log In window */
function openLoginForm() {
      jq('#idSignin').click(function(e) {             
            showLoginWindow(e);
            return false;
      });
}

/*
 * Method to close the Login window when click outside it
 */
function hideLoginWindow() {      
      jq('#pcrMainForm').hover(function() {
            mouse_is_inside = true;
      }, function() {
            mouse_is_inside = false;
      });

      jq(document).click(function() {
            if (!mouse_is_inside) {
                  jq('#pcrMainForm').hide();
                  jq("#pcrMainForm div[id*='Content']").hide();
			jq("#pcrJoinNowPopup").hide();
            }
      });
      
      // By pressing Esc Key PCR Window will be closed.
      jq(document).keypress(function(e) {
            if (e.keyCode == 27) {
                  jq('#pcrMainForm').hide();
                  jq("#pcrMainForm div[id*='Content']").hide();
				  
				  jq('.destToolLayer_profile').each(function(i,elem){
					jq(elem).addClass('hide');
				  });
				  jq("#pcrJoinNowPopup").hide();
            }
      });
}

/* Reusable Method to call for Sign in Popup*/
function showLoginWindow(e) {
   initLoginForm();
   //alert("left" + e.pageX);
   var xpos = e.pageX - 90;
   var ypos = e.pageY; // + 15;   
   jq('#pcrMainForm').css( { "left":xpos, "top":ypos  } ).show();
}

/* Method to initiate the Login Form */
function initLoginForm() {
      
      jq('#pcrSignInFormContent').show();
      //jq('#errPCRSignInServerValidation').html("");
      //jq('#errPCRSignInServerValidation').addClass("hide");
      //jq("*[name='accountId']").removeClass("pcrReqdError");
      //jq("*[name='accountPin']").removeClass("pcrReqdError");
      //jq("*[name='accountPin']").val("");
}

/* Method to open PCR Login window */
function closePCRForm() {
      jq('div.closeBtn').click(function() {
            
		jq('#pcrSignInFormContent').hide();

            
            jq("#pcrMainForm div[id*='Content']").hide();
            
            return false;
      });
      
      
}