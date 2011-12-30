package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GFakeLocation;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.services.domain.ActiveSharedLocation;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.DateUtils;
import com.gogwt.apps.tracking.utils.StringUtils;

public class AddLocationController extends BaseAbstractController {
	private static Logger logger = Logger
			.getLogger(AddLocationController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final GFakeLocation requestForm = new GFakeLocation();

		HttpSession session = request.getSession();
		CustomerProfile customerProfile = (CustomerProfile) session
				.getAttribute(CUSTOMER_PROFILE);

		String groupId = "";
		if (customerProfile != null) {
			groupId = customerProfile.getGroupId();
		}

		requestForm.setGroupId(groupId);

		return requestForm;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("AddLocationController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}

	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		logger.debug("AddLocationController - In onSubmit()");

		final GFakeLocation formBean = (GFakeLocation) command;

		String action = request.getParameter("action");
		if (StringUtils.equalsIgnoreCase("Add", action)) {
			// call login
			try {
				System.out.println(" === formBean === " + formBean.toString());
				LocationRequest locationRequest = new LocationRequest();

				Profile profile = new Profile();
				profile.setGroupId(formBean.getGroupId());
				profile.setDisplayName(request.getParameter("displayName"));
				profile.setServerUsername("mwang");
				profile.setServerLastName("michael");
				profile.setServerLastName("wang");
				profile.setPhoneNumber("12344333");

				locationRequest.setProfile(profile);

				List<GLocation> locations = new ArrayList<GLocation>();
				locations.add(formBean);

				locationRequest.setLocations(locations);

				final RestBusinessDomainService service = LookupBusinessService
						.getRestBusinessDomainService();
				LocationResponse locationResponse = service
						.processLocation(locationRequest);

				logger.debug(" ==== response: " + locationResponse.toString());
			} catch (Throwable te) {
				te.printStackTrace();
				logger.debug("Unknown error code");
			}
		} else if (StringUtils.equalsIgnoreCase("Remove The Track", action)) {
			ActiveSharedLocation.removeClientFromMap(formBean.getGroupId(),
					request.getParameter("displayName"));
		} else if (StringUtils.equalsIgnoreCase("Select File", action)) {
			try {
				processFileLoad(formBean, request, response, errors);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.debug("Unknown error code");
			}
		}

		// final String targetURL = getSuccessURL(request);
		// String successView = getSuccessView();
		final ModelMap modelMap = new ModelMap();
		return super.showForm(request, response, errors, modelMap);

	}

	/**
	 * 
	 * @param formBean
	 * @param errors
	 */
	private ModelAndView processFileLoad(final GFakeLocation formBean, final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors) throws Exception {
		System.out.println(" ====  processFileLoad ");
		String fileName = "";
		
		MultipartFile multipartFile = formBean.getFileData();
		
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

			ArrayList<TrackingMobileData> trackDataList = processUploadedFile(inputStream);
			if (trackDataList == null || trackDataList.isEmpty()) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.file.could.not.find.track");
				 
				return super.showForm(request, response, errors, modelMap);
			}
			
			//so far, all data is good. convert TrackingMobileData to LocationRequest
			LocationRequest locationRequest = new LocationRequest();
			Profile profile = new Profile();
			profile.setGroupId(formBean.getGroupId());
			profile.setDisplayName(request.getParameter("displayName"));
			profile.setServerUsername("mwang");
			profile.setServerLastName("michael");
			profile.setServerLastName("wang");
			profile.setPhoneNumber("12344333");

			locationRequest.setProfile(profile);
		
			int start = 0;
			int size = 1;
			
			final RestBusinessDomainService service = LookupBusinessService.getRestBusinessDomainService();
			
			List<GLocation> locations = null;
			while (true) {
				locations = new ArrayList<GLocation>();
			    start = convertToGlocation(start,size,locations, trackDataList );
			    if (start == -1) {
				   break;
			    }
			    locationRequest.setLocations(locations);   
			    
			    //add location
			   	LocationResponse locationResponse = service.processLocation(locationRequest);
			   	
			   	Thread.sleep(1000);
			}
		}
		
		return null;
	}

	private int convertToGlocation(int start, int size, List<GLocation> locations, ArrayList<TrackingMobileData> trackDataList) {
		if (start+size>trackDataList.size()) {
			return -1;
		}
		GLocation gLocation = null;
		TrackingMobileData track;
		for (int i=start; i<start+size; i++) {
			track = trackDataList.get(i);
			gLocation = new GLocation();
			
			gLocation.setGroupId(track.getGroupId());
			gLocation.setLatitude(track.getLatitude());
			gLocation.setLongitude(track.getLongitude());	
			gLocation.setAltitude(track.getAltitude());
			gLocation.setProvider(track.getProvider());
			gLocation.setAccuracy(track.getAccuracy());
			gLocation.setBearing(track.getBearing());
			gLocation.setDistance(track.getDistance());
			gLocation.setSpeed(track.getSpeed());
			gLocation.setTime(track.getTime());
			gLocation.setStartTime(track.getStartTime());
			gLocation.setAddress(track.getAddress());
			gLocation.setTotalDistance(track.getTotalDistance());
			
			locations.add(gLocation);	
		}
		
		return start+size;
	}
	private ArrayList<TrackingMobileData> processUploadedFile(InputStream is)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		ArrayList<TrackingMobileData> dataList = new ArrayList<TrackingMobileData>();

		String line = null;
		TrackingMobileData trackData;
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

			trackData = convertToTrackingMobileData(line);

			if (trackData != null) {
				dataList.add(trackData);
			}
		}

		return dataList;
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
	private TrackingMobileData convertToTrackingMobileData(final String line) {
		TrackingMobileData trackData = new TrackingMobileData();

		try {
			String[] rawData = line.split(",");

			trackData.setGroupId(rawData[0]);
			trackData.setPhoneNumber(rawData[1]);
			trackData.setDisplayName(rawData[2]);
			trackData.setLatitude(Integer.parseInt(rawData[3]));
			trackData.setLongitude(Integer.parseInt(rawData[4]));
			trackData.setAltitude(Double.parseDouble(rawData[5]));
			trackData.setProvider(rawData[6]);
			trackData.setAccuracy(Double.parseDouble(rawData[7]));
			trackData.setBearing(Double.parseDouble(rawData[8]));
			trackData.setDistance(Double.parseDouble(rawData[9]));
			trackData.setSpeed(Double.parseDouble(rawData[10]));
			trackData.setTime(Long.parseLong(rawData[11]));
			trackData.setStartTime(Long.parseLong(rawData[12]));
			trackData.setTotalDistance(Double.parseDouble(rawData[13]));
			Date theDate = DateUtils.toDate(rawData[14]);
			trackData.setCreateDate(theDate);

			return trackData;

		} catch (Exception e) {
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
