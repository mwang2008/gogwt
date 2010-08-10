package com.gogwt.app.booking.businessService.dataaccess;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeocodeBean;
import com.gogwt.app.booking.dto.dataObjects.request.ReservationBean;

public interface HotelSearchDAO {
   public List<HotelBean> searchHotel(final GeocodeBean hotelSearchRequestBean);
   public int confirmReservation(final ReservationBean reservation);
}
