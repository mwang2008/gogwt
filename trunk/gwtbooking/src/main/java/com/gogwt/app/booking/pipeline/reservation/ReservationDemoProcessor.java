package com.gogwt.app.booking.pipeline.reservation;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.pipeline.PipelineProcessor;

public class ReservationDemoProcessor implements PipelineProcessor {
	private static Logger logger = Logger.getLogger(ReservationDemoProcessor.class);
	
	public void execute(Map<String, Object> params) {
		logger.debug("execute");
		
	}

}
