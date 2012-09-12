package com.gogwt.app.booking.pipeline.reservation;

import static com.gogwt.app.booking.pipeline.PipelineConstants.RESERVATION;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.dto.dataObjects.common.EmailConfig;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.pipeline.PipelineProcessor;
import com.gogwt.app.booking.utils.EmailUtils;

public class SendCreatedEmailProcessor implements PipelineProcessor{
	private static Logger logger = Logger.getLogger(SendCreatedEmailProcessor.class);
	
	private CommonDAO commonDAO;
	
	public void execute(Map<String, Object> params) {
		
		logger.debug("execute");		
		ReserveResponseBean reservation = (ReserveResponseBean)params.get(RESERVATION);
		
		logger.info(reservation.getReserveNum());
		
		GuestInfoFormBean guestInfo = reservation.getGuestInfo();
		logger.info(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		logger.info(guestInfo.getEmail());
		
		HotelBean hotel = reservation.getSelectHotel();
		logger.info(hotel.getName());		
 		
		String subject = "Reservation is created Successfully ";
		StringBuilder body = new StringBuilder();
		body.append(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		
		body.append("\n Your reservation is created successfully");
		body.append("\n The confirmation number is: " + reservation.getReserveNum() );
		
		EmailConfig emailConfig =  commonDAO.getEmailConfig();
		
		EmailUtils emailServer = EmailUtils.getInstance(emailConfig);
		emailServer.sendEmail(guestInfo.getEmail(), subject, body.toString());
		
		
	}

	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	
}
