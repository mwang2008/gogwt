package com.gogwt.app.booking.dto.dataObjects.response;


import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.framework.arch.dto.BaseBean;

public class ReserveResponseBean extends BaseBean {
	private int reserveNum;
    private HotelBean selectHotel;
    private GuestInfoFormBean guestInfo;
    
	public int getReserveNum() {
		return reserveNum;
	}

	public void setReserveNum(int reserveNum) {
		this.reserveNum = reserveNum;
	}

	public HotelBean getSelectHotel() {
		return selectHotel;
	}

	public void setSelectHotel(HotelBean selectHotel) {
		this.selectHotel = selectHotel;
	}

	public GuestInfoFormBean getGuestInfo() {
		return guestInfo;
	}

	public void setGuestInfo(GuestInfoFormBean guestInfo) {
		this.guestInfo = guestInfo;
	}

	
}
