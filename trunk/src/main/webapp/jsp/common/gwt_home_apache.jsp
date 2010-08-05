<%--
  home.jsp
  Extract from GWT: com.ihg.dec.apps.hi.gwt.common.HomeEntry
--%>
<%@ include file="i_global.jspf"%>
<html>
<head>
  <meta name="gwt:property" content="locale=${env.languageId}_${env.countryId}" />

  <title>Wrapper HTML for HomeEntry</title>

  <!--                                           -->
  <!-- Use normal html, such as style            -->
  <!--                                           -->
  <style>
    body,td,a,div,.p{font-family:arial,sans-serif}
    div,td{color:#000000}
    a:link,.w,.w a:link{color:#0000cc}
    a:visited{color:#551a8b}
    a:active{color:#ff0000}
  </style>	 
</head>


<body>
   <!-- OPTIONAL: include this if you want history support -->
   <%--
   <iframe src="${env.contextPath}/jsp/empty_iframe.html" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
		
   <script type="text/javascript" language="javascript" src="${env.contextPath}/org.gwt.tutorial.gwt.homepage.HomeEntry/org.gwt.tutorial.gwt.homepage.HomeEntry.nocache.js"></script>
   --%>		
   	<%-- 
      <iframe src="http://wangm1-wxp.www.holidayinn.com/web-spring/jsp/empty_iframe.html" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
		
   <script type="text/javascript" language="javascript" src="http://wangm1-wxp.www.holidayinn.com/web-spring/org.gwt.tutorial.gwt.homepage.HomeEntry/org.gwt.tutorial.gwt.homepage.HomeEntry.nocache.js"></script>
    --%>
    <b>GWT from cross domain</b><br>
      <iframe src="http://wangm-wxp.www.holidayinn.com:8080/myspring/jsp/empty_iframe.html" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
		
   <script type="text/javascript" language="javascript" src="http://wangm-wxp.www.holidayinn.com:8080/myspring/org.gwt.tutorial.gwt.homepage.HomeEntry/org.gwt.tutorial.gwt.homepage.HomeEntry.nocache.js"></script>
          
            
   <hr> 
   <b> =================== include HI ===================</b> 
   <hr>
    
    <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="http://wangm-wxp.www.holidayinn.com:8080/hotels/css/nosecure/123/FF/all/hi/us/en/global_reservation.css"/>

    <script type="text/javascript" >
	var envJson = '{"brand":"HolidayInn","brandId":"hi","checkInDate":"","checkInMonth":"","checkInMonthYear":"","checkInYear":"","checkOutDate":"","checkOutMonth":"","checkOutMonthYear":"","checkOutYear":"","clientInfo":{"AOL":false,"FF":true,"IE":false,"NN":false,"OP":false,"WTV":false,"browserCode":"FF","browserMajorVersion":0,"browserMinorVersion":0,"browserName":"Firefox","browserVersion":"0.0","comment":"","commentType":"","linux":false,"mac":false,"n4":false,"n6":false,"platformCode":"XP","platformName":"Microsoft Windows XP","win":true},"contextPath":"/hotels","controllerData":[],"controllerName":"reservation","cookieDomain":".holidayinn.com","countryId":"us","currentFullUrl":"http://wangm-wxp.www.holidayinn.com:8080/hotels/us/en/reservation","gwtToken":"","hotelCode":"","imagePrefix":"http://content.dev.ichotelsgroup.com","languageId":"en","locale":{"ISO3Country":"USA","ISO3Language":"eng","country":"US","displayCountry":"United States","displayLanguage":"English","displayName":"English (United States)","displayVariant":"","language":"en","variant":""},"longDateFormat":"E d MMM yyyy","mediumDateFormat":"MMM-dd-yyyy","regionId":"1","section":"","secure":"false","sortBy":"","subSection":"","timeFormat":"hh:mm aa","uri":"/hotels/us/en/reservation","uriPrefix":"/hotels/us/en","url":"/hotels/us/en/reservation","urlContext":{"EXSecureDomain":"https://local.secure.hiexpress.com","EXUnSecureDomain":"http://local.www.hiexpress.com","HISecureDomain":"https://local.secure.holidayinn.com","HIUnSecureDomain":"http://local.www.holidayinn.com","secureDomain":"https://local.secure.holidayinn.com","secureGenesisDomain":"https://local.secure.ichotelsgroup.com","unSecureDomain":"http://local.www.holidayinn.com","unSecureGenesisDomain":"http://local.www.ichotelsgroup.com"},"urlReferrer":"","versionNumber":"123"}';
	var pcrSerializProfile = '//OK[0,0,0,0,0,2,0,1,["com.ihg.dec.apps.hi.dto.dataObjects.pcr.PCRProfileBean/880196606","anonymous"],0,5]';
	var serializedPopulator = '';
	if (window.getPopulators) {
	 serializedPopulator =  getPopulators();
	}
	
  var serverMessages = {
		  formId  : '',
		  message : '',
		  success : ''
  };
  
  
  var menuXml = '?<?xml version="1.0" encoding="UTF-8" standalone="no"?><menus>-<reservation>-<menuitem><name>Find and Book</name><url>/hotels/us/en/reservation/findandbook</url><description>Find and Book</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Reward Nights</name><url>/hotels/us/en/reservation/rewardnights</url><description>Reward Nights</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Manage your Stay</name><url>/hotels/us/en/reservation#ManageYourStay</url><description>Manage your Stay</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Meetings and Groups</name><url>/hotels/us/en/global/2/support/meetings</url><description>Meetings and Groups</description><domain>holidayinn</domain><flag>1</flag></menuitem></reservation>-<offers>-<menuitem><name>Best Flexible Rate</name><url>/hotels/us/en/global/1/offers/bestflex</url><description>Best Flexible Rate Offer</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Sign up for email offers</name><url>http://www.priorityclubpromotion.com/brandOptIn/hi</url><description>Sign up for offers in your inbox</description><domain>third party</domain><flag>1</flag></menuitem></offers>-<brandexperience>-<menuitem><name>The New Holiday Inn</name><url>/hotels/us/en/global/3/exp/exp_home</url><description>About the new Holiday Inn</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Holiday Inn Brands</name><url>/hotels/us/en/global/2/exp/hi_brands</url><description>About our brands</description><domain>holidayinn</domain><flag>1</flag></menuitem>-<menuitem><name>Find a Refreshed Hotel</name><url>http://relaunch.holidayinn.com/?lang=en&amp;section=2</url><description>Find a Refreshed Holiday Inn Hotel</description><domain>third party</domain><flag>1</flag></menuitem></brandexperience></menus>';
</script>


    <script type="text/javascript" language="javascript"
	src="http://wangm-wxp.www.holidayinn.com:8080/hotels/com.ihg.dec.apps.hi.gwt.reservationentry.ReservationProcessEntry/com.ihg.dec.apps.hi.gwt.reservationentry.ReservationProcessEntry.nocache.js"></script>

   <hr>
     <fmt:message key='label.welcome'/> 
   <hr>
   <div id="wrapperContent" style="position: relative"></div>
   <div id="gwt_section" style="position: relative"></div>
</body>
</html>
