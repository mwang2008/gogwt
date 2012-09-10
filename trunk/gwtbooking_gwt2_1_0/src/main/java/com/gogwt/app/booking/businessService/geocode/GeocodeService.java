package com.gogwt.app.booking.businessService.geocode;

import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResponseBean;

public interface GeocodeService {
    public GeocodeResponseBean geocodeIt(final String location) throws Exception;
}
