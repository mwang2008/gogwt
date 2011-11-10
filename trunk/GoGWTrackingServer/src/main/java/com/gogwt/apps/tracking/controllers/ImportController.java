package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.TRACK_DATA_LIST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.formbean.UploadFormBean;
import com.gogwt.apps.tracking.utils.DateUtils;
import com.gogwt.apps.tracking.utils.StringUtils;

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

			ArrayList<TrackingMobileData> trackDataList = processUploadedFile(inputStream);
			session.setAttribute(TRACK_DATA_LIST, trackDataList);
		}

		String successView = getSuccessView();
		successView += "?from=import";
		return new ModelAndView(new RedirectView(successView));
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

		return badFormat;
	}

}
