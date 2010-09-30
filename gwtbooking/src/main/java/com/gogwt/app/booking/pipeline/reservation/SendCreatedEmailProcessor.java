package com.gogwt.app.booking.pipeline.reservation;

import static com.gogwt.app.booking.pipeline.PipelineConstants.RESERVATION;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.pipeline.PipelineProcessor;

public class SendCreatedEmailProcessor implements PipelineProcessor{
	private static Logger logger = Logger.getLogger(SendCreatedEmailProcessor.class);
	
	public void execute(Map<String, Object> params) {
		
		logger.debug("execute");		
		ReserveResponseBean reservation = (ReserveResponseBean)params.get(RESERVATION);
		
		logger.info(reservation.getReserveNum());
		
		GuestInfoFormBean guestInfo = reservation.getGuestInfo();
		logger.info(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		logger.info(guestInfo.getEmail());
		
		HotelBean hotel = reservation.getSelectHotel();
		logger.info(hotel.getName());		
 		
		//TODO: Provide email function in the below
		
	}

}
