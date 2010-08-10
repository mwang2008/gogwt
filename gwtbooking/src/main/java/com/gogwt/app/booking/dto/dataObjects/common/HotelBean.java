package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;

public class HotelBean extends BaseBean {
	private int id;
	private String name;
	private String description;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private double lat;
	private double lng;
	private int numberOfRooms;
	private int numberOfSuites;
	private int numberOfFloors;
	private String checkInTime;
	private String checkOutTime;

	private String hasTennisCourt;
	private String hasPetsAllowed;
	private String hasIndoorPool;
	private String hasOutdoorPool;
	private String hasKitchen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	
 	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfSuites() {
		return numberOfSuites;
	}

	public void setNumberOfSuites(int numberOfSuites) {
		this.numberOfSuites = numberOfSuites;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getHasTennisCourt() {
		return hasTennisCourt;
	}

	public void setHasTennisCourt(String hasTennisCourt) {
		this.hasTennisCourt = hasTennisCourt;
	}

	public String getHasPetsAllowed() {
		return hasPetsAllowed;
	}

	public void setHasPetsAllowed(String hasPetsAllowed) {
		this.hasPetsAllowed = hasPetsAllowed;
	}

	public String getHasIndoorPool() {
		return hasIndoorPool;
	}

	public void setHasIndoorPool(String hasIndoorPool) {
		this.hasIndoorPool = hasIndoorPool;
	}

	public String getHasOutdoorPool() {
		return hasOutdoorPool;
	}

	public void setHasOutdoorPool(String hasOutdoorPool) {
		this.hasOutdoorPool = hasOutdoorPool;
	}

	public String getHasKitchen() {
		return hasKitchen;
	}

	public void setHasKitchen(String hasKitchen) {
		this.hasKitchen = hasKitchen;
	}

	
}
