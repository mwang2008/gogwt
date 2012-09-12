package com.gogwt.app.booking.businessService.geocode.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.geocode.GeocodeService;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResponseBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class GoogleGeocodeService implements GeocodeService {     
    private static final String GOOGLE_KEY = "ABQIAAAAmdQW6A4k5xzDjjGKjkBoGxSpsivlna89iOWRa_hFT1LNgvW_6hSu0P4mGO5Amrxbesv6A4dCyHhueQ";
    
    private static Logger logger = Logger.getLogger(GoogleGeocodeService.class);
    
    /**
     * Geocode with Google geocode service.
     */

	public GeocodeResponseBean geocodeIt(String location) throws Exception {
		location = StringUtils.replace(location, " ", "+");
		StringBuffer geoStr = new StringBuffer("http://maps.google.com/maps/geo?");
		geoStr.append("q=" + location);
		geoStr.append("&output=xml");
		geoStr.append("&key=" + GOOGLE_KEY);
		
		logger.info(geoStr.toString());
		
		InputStream inputStream = null;
		try {

			URL url = new URL(geoStr.toString());

			// Get an input stream for reading
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
 
			//ArrayList<HotelBean> hotelList = null;
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = urlConnection.getInputStream();

				//printDebugResult(inputStream);               
				GeocodeResponseBean geocodeResponseBean = GoogleGeocodeKMLParser.parserKML(inputStream);
 				
				return geocodeResponseBean;
			}
		}
		finally {
			//close inputStream
			if (inputStream != null) {
			  inputStream.close();
			}
		}
		return null; 
		
	}

	
	private void printDebugResult(InputStream response) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(response));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		}
		
		 
	}
}
