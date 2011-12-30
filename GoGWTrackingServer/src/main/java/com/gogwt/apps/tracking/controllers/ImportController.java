package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.TRACK_DATA_WRAPPER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.data.TrackingDataWrapper;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.formbean.UploadFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.DateUtils;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * 
 * Handle import function
 * @author michael.wang
 *
 */
public class ImportController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(ImportController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final UploadFormBean uploadForm = new UploadFormBean();

		return uploadForm;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("ImportController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}

	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {

		HttpSession session = request.getSession();

		final UploadFormBean formBean = (UploadFormBean) command;

		MultipartFile multipartFile = formBean.getFileData();

		String fileName = "";
		if (multipartFile != null) {
			fileName = multipartFile.getOriginalFilename();
			String contentType = multipartFile.getContentType();
			if (isBadContentType(contentType)) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.invalid.contentType");
				return super.showForm(request, response, errors, modelMap);
			}

			long fileSize = multipartFile.getSize();
			if (fileSize > 2000000) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.file.over.size");
				return super.showForm(request, response, errors, modelMap);
			}

			InputStream inputStream = multipartFile.getInputStream();

			TrackingDataWrapper trackingDataWrapper = processUploadedFile(inputStream);
			ArrayList<TrackingMobileData> locList = trackingDataWrapper.getLocList();
			 
			if (locList == null || locList.isEmpty()) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.file.could.not.find.track");
				session.removeAttribute(TRACK_DATA_WRAPPER);
				return super.showForm(request, response, errors, modelMap);
			}
			
			//get start and end address
			final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
			String startAddress = service.findAddress(locList.get(0));
			String endAddress = service.findAddress(locList.get(locList.size()-1));
			
			//save to session, the AJAX will retrieve this value
			session.setAttribute(TRACK_DATA_WRAPPER, trackingDataWrapper);
			
			String successView = getSuccessView();			
			successView += "?from=import&startAddress="+URLEncoder.encode(startAddress) + "&endAddress="+URLEncoder.encode(endAddress);			
			 	
			return new ModelAndView(new RedirectView(successView));
		}

		final ModelMap modelMap = new ModelMap();
		errors.reject("error.file.not.found");		
		return super.showForm(request, response, errors, modelMap);
	
	}

	private TrackingDataWrapper processUploadedFile(InputStream is)
			throws IOException {
	 	
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		ArrayList<TrackingMobileData> locList = new ArrayList<TrackingMobileData>();
		ArrayList<TrackingSmsData> smsList = new ArrayList<TrackingSmsData>();
		
		String line = null;
		TrackingMobileData trackData;
		TrackingSmsData smsData;
		
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("#")) {
				continue;
			}

			if (!StringUtils.isSet(line)) {
				continue;
			}

			if (line.contains("groupId")) {
				continue;
			}

			logger.debug(" ----- " + line);
			String[] rawData = line.split(",");
			String stype = rawData[0];
			
			if (StringUtils.equalsIgnoreCase(stype, "loc")) {
				trackData = convertToTrackingMobileData(rawData);
				if (trackData != null) {
					locList.add(trackData);
				}
			}
			else if (StringUtils.equalsIgnoreCase(stype, "sms")) {
				smsData = convertToTrackingSmsData(rawData);
				if (smsData != null) {
					smsList.add(smsData);
				}
			}
  		}
		
		TrackingDataWrapper trackingDataWrapper = new TrackingDataWrapper();
		trackingDataWrapper.setLocList(locList);
		trackingDataWrapper.setSmsList(smsList);
		
 		return trackingDataWrapper;
	}

 
	
	private TrackingSmsData convertToTrackingSmsData(String [] rawData) {
		TrackingSmsData smsData = new TrackingSmsData();
		
		try {
			smsData.setGroupId(rawData[1]);		 
			smsData.setDisplayName(rawData[2]);
			smsData.setAddress(rawData[3]);
			
			Date theDate = DateUtils.toDate(rawData[4]);
			smsData.setDate(theDate);
			smsData.setRead(Integer.parseInt(rawData[5]));
			smsData.setType(Integer.parseInt(rawData[6]));
			smsData.setBody(rawData[7]);
			smsData.setStartTime(Long.parseLong(rawData[8]));
			Date theCreateDate = DateUtils.toDate(rawData[9]);
			smsData.setCreateDate(theCreateDate);
			
			return smsData;
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 groupId, phoneNumber, displayName, latitude, longitude, altitude,
	 * provider, accuracy, bearing, distance, speed, time, startTime,
	 * totalDistance, createDate g5,12344333,show
	 * 5,31060000,-81060000,50.0,gps,9.0,9.0,9.0,50.0,1,1,138.0,2011-10-31
	 * 16:35:02.0 g5,12344333,show
	 * 5,31560000,-81060000,50.0,gps,9.0,9.0,9.0,50.0,2,1,140.0,2011-10-31
	 * 16:35:22.0
	 * 
	 * @param line
	 * @return
	 */
	private TrackingMobileData convertToTrackingMobileData(String[] rawData) {
		TrackingMobileData trackData = new TrackingMobileData();

		try {
			//String[] rawData = line.split(",");

			trackData.setGroupId(rawData[1]);
			trackData.setPhoneNumber(rawData[2]);
			trackData.setDisplayName(rawData[3]);
			trackData.setLatitude(Integer.parseInt(rawData[4]));
			trackData.setLongitude(Integer.parseInt(rawData[5]));
			trackData.setAltitude(Double.parseDouble(rawData[6]));
			trackData.setProvider(rawData[7]);
			trackData.setAccuracy(Double.parseDouble(rawData[8]));
			trackData.setBearing(Double.parseDouble(rawData[9]));
			trackData.setDistance(Double.parseDouble(rawData[10]));
			trackData.setSpeed(Double.parseDouble(rawData[11]));
			trackData.setTime(Long.parseLong(rawData[12]));
			trackData.setStartTime(Long.parseLong(rawData[13]));
			trackData.setTotalDistance(Double.parseDouble(rawData[14]));
			Date theDate = DateUtils.toDate(rawData[15]);
			trackData.setCreateDate(theDate);
			
			return trackData;
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;

	}

	private boolean isBadContentType(String contentType) {
		boolean badFormat = true;
		if (contentType == null) {
			return true;
		}

		if (contentType.indexOf("text") != -1) {
			return false;
		}

		if (contentType.indexOf("application/octet-stream") != -1) {
			return false;
		}

		if (contentType.indexOf("application/download") != -1) {
			return false;
		}

		if (contentType.indexOf("application/vnd.ms-excel") != -1) {
			return false;
		}
		return badFormat;
	}

}
