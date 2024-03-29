package com.gogwt.app.booking.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.pipeline.PipelineThreadExecutor;

import static com.gogwt.app.booking.pipeline.PipelineConstants.*;
 
/**
 * <code><B>ReservationAspectJ<code><B>
 * <p/>
 * An aspect to inject to CreateReservationProcess. 
 * </p>
 * <p>
 * New thread will be spawned to start pipeline to associated actions such as sending email, etc.
 * 
 */
@Aspect
public class ReservationAspectJ {
	private static Logger logger = Logger.getLogger(ReservationAspectJ.class);

	/**
	 * <p>
	 * Injected into confirmReservation. And prepare for async pipeline call with
	 * thread pool.
	 * </p>
	 * 
	 * @param joinPoint
	 *            The join point that gives access to the execution context
	 * @param guestInfo
	 *            the form bean of guest info
	 * @param selectHotel
	 *            the hotel user wants to reserve           
	 * @param userContext
	 *            current user context
	 * @return the confirmed reservation object
	 */
	@Around(value = "execution(* com.gogwt.app.booking.businessService.domainService.ReservationBusinessService.confirmReservation(..)) && args(guestInfo, selectHotel, userContext)")
	public ReserveResponseBean newReservationPipelineProcess(
			final ProceedingJoinPoint joinPoint,			 
			final GuestInfoFormBean guestInfo, final HotelBean selectHotel,
			UserContextBean userContext) throws Throwable {

		logger.debug("==== before point cut ====");
		
		 // 1. join point
		ReserveResponseBean reservation = (ReserveResponseBean)joinPoint.proceed();
 		    
 		if (reservation == null) {
			return reservation;
		}
 
 		//2. prepare pipeline
 		PipelineThreadExecutor pipeLineThread = new PipelineThreadExecutor();
 		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RESERVATION, reservation);
		params.put(CONTEXT, userContext);
		
		// 2.1 add pipeline processor defined in pipeline.xml
		params.put(PIPELINE_PATH, RESERVATION_PIPELINE_NAME);
 		
 		pipeLineThread.execute(params);
   
		logger.debug("==== after point cut ====");

		return reservation;
	}
}
