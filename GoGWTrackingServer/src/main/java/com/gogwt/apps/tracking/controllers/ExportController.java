package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import static com.gogwt.apps.tracking.AppConstants.ERROR_MSG;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.DateUtils;
import com.gogwt.apps.tracking.utils.StringUtils;

public class ExportController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(ExportController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String groupId = request.getParameter("groupId");
		String displayName = request.getParameter("displayName");
		String startTime = request.getParameter("startTime");

		if (!StringUtils.isSet(groupId) || !StringUtils.isSet(displayName)
				|| !StringUtils.isSet(startTime)) {
			// redirect to error page
			session.setAttribute(ERROR_MSG,
					"Missing parameters: groupId, displayName or startTime");
			return new ModelAndView(new RedirectView("error"));
		}

		long startTiemLong = Long.parseLong(startTime);
		CustomerProfile customerProfile = (CustomerProfile) session
				.getAttribute(CUSTOMER_PROFILE);

		logger.debug("groupId=" + groupId + " ,displayName=" + displayName
				+ ", startTime=" + startTime);
		
		final RestBusinessDomainService service = LookupBusinessService.getRestBusinessDomainService();
		
		List<TrackingMobileData> trackingDataList = service.getTrack(customerProfile.getUserName(), groupId, displayName, startTiemLong);
		List<TrackingSmsData> trackingSmsList = service.findAllSms(groupId,	displayName, startTiemLong);

		String filename = groupId + "_" + displayName + "_" + startTime	+ ".csv";
		// change white space to under score
		filename = filename.replace(" ", "_");

		response.setContentType("application/download");
		filename = "filename=" + filename;

		response.setHeader("Content-Disposition", filename);
		PrintWriter out = null;

		try {
			out = response.getWriter();
			// Write header
			out.println("#mobitype, groupId, phoneNumber, displayName, latitude, longitude, altitude, provider, accuracy, bearing, distance, speed, time, startTime, totalDistance, createDate");

			TrackingMobileData trackingData;
			
			//export location, type=loc
			if (trackingDataList != null) {
				for (int i = 0; i < trackingDataList.size(); i++) {
					trackingData = trackingDataList.get(i);
					out.print("loc");
					out.print(',');
					out.print(trackingData.getGroupId());
					out.print(',');
					out.print(trackingData.getPhoneNumber());
					out.print(',');
					out.print(trackingData.getDisplayName());
					out.print(',');
					out.print(trackingData.getLatitude());
					out.print(',');
					out.print(trackingData.getLongitude());
					out.print(',');
					out.print(trackingData.getAltitude());
					out.print(',');
					out.print(trackingData.getProvider());
					out.print(',');
					out.print(trackingData.getAccuracy());
					out.print(',');
					out.print(trackingData.getBearing());
					out.print(',');
					out.print(trackingData.getDistance());
					out.print(',');
					out.print(trackingData.getSpeed());
					out.print(',');
					out.print(trackingData.getTime());
					out.print(',');
					out.print(trackingData.getStartTime());
					out.print(',');
					out.print(trackingData.getTotalDistance());
					out.print(',');
					out.print(DateUtils.toString(trackingData.getCreateDate()));
					out.print('\n');
				}
			}
			
			if (trackingSmsList != null) {
				out.println("#mobitype, groupId, displayName, address, date, read, type, body, startTime, createDate");

				TrackingSmsData trackingSms;
			
				for (int i = 0; i < trackingSmsList.size(); i++) {
					trackingSms = trackingSmsList.get(i);
					
					out.print("sms");
					out.print(',');
					out.print(trackingSms.getGroupId());
					out.print(',');				 
					out.print(trackingSms.getDisplayName());
					out.print(',');
					out.print(trackingSms.getAddress());
					out.print(',');
					//out.print(DateUtils.toString(trackingSms.getDate()));
					out.print(trackingSms.getDate());
					out.print(',');
					out.print(trackingSms.getRead());
					out.print(',');
					out.print(trackingSms.getType());
					out.print(',');
					out.print(trackingSms.getBody());
					out.print(',');
					out.print(trackingSms.getStartTime());
					out.print(',');
					out.print(DateUtils.toString(trackingSms.getCreateDate()));
					out.print('\n');
	 					
				}
			}
			out.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}

		String successView = getSuccessView();
		return new ModelAndView(successView);

	}

}
