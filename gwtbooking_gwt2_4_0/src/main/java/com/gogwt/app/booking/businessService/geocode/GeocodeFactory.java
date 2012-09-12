package com.gogwt.app.booking.businessService.geocode;

import com.gogwt.app.booking.businessService.geocode.google.GoogleGeocodeService;

public class GeocodeFactory {
	
    public static GeocodeService getGeocodeFactory() {
    	//currently, always return GoogleGeocode
    	return new GoogleGeocodeService();
    	
    }
}
