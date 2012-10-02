package com.gogwt.apps.admin.formbean;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class WeatherForm implements Serializable {
	
	@NotEmpty
    @Size(min = 1, max = 5)
	private String zip;
    private String temperatureType;
    
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTemperatureType() {
		return temperatureType;
	}

	public void setTemperatureType(String temperatureType) {
		this.temperatureType = temperatureType;
	}

	
}
