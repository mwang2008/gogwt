package com.gogwt.apps.admin.formbean;

import java.io.Serializable;


public class WeatherResponse implements Serializable {
	protected boolean success;
	protected String responseText;
	protected String state;
	protected String city;
	protected String weatherStationCity;
	protected short weatherID;
	protected String description;
	protected String temperature;
	protected String relativeHumidity;
	protected String wind;
	protected String pressure;
	protected String visibility;
	protected String windChill;
	protected String remarks;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeatherStationCity() {
		return weatherStationCity;
	}

	public void setWeatherStationCity(String weatherStationCity) {
		this.weatherStationCity = weatherStationCity;
	}

	public short getWeatherID() {
		return weatherID;
	}

	public void setWeatherID(short weatherID) {
		this.weatherID = weatherID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getWindChill() {
		return windChill;
	}

	public void setWindChill(String windChill) {
		this.windChill = windChill;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
