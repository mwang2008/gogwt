package com.gogwt.app.booking.config.interceptor;

import static com.gogwt.app.booking.BookingConstants.ENV;
import static com.gogwt.app.booking.BookingConstants.ENV_JSON;
import static com.gogwt.app.booking.BookingConstants.FORWARD_SLASH;
import static com.gogwt.app.booking.BookingConstants.SUPPORTED_LANG_REGIONS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogwt.app.booking.config.interceptor.bean.ConfigPage;
import com.gogwt.app.booking.config.parser.PageConfigXMLParser;
import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.utils.ToStringUtils;
import com.gogwt.framework.arch.utils.StringUtils;

/**
 * URLHandlerInterceptor is executed before controller
 * 
 * @author WangM
 * 
 */
public class URLHandlerInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(URLHandlerInterceptor.class);
	
	private Map<String, String> supportedLangRegion;
	//private Map<String, List<ConfigPage>> gwtPageMap;
	private Map<String, Map<String, List<PopulatorItem>>> ctrlKeyPopulators;
	
	/* defined in -servlet.xml URLHandlerInterceptor section */
	private Map<String, String> controllerGWTConfigMap;

	
	public URLHandlerInterceptor(Map<String, String> controllerGWTConfigMapArg) {
		super();
		controllerGWTConfigMap = controllerGWTConfigMapArg;
		ctrlKeyPopulators = new HashMap<String, Map<String, List<PopulatorItem>>>();
	}

	
	/**
	 * Executed every request before handler.
	 * 
	 * @param httpServletRequest
	 *            The servlet request we are processing
	 * @param httpServletResponse
	 *            The servlet response we are creating
	 * @return boolean TRUE = continue processing the request FALE = response
	 *         has already been completed
	 */
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {

		try {

			//basic element
			final UrlMappingElem urlMappingElem = fillRequestAttributes(request, response, handler);

			//populator
			processPopulator(request, urlMappingElem.getControllerName(), urlMappingElem);
			
			// save urlMappingElem to request
			request.setAttribute(ENV, urlMappingElem);
			request.setAttribute(SUPPORTED_LANG_REGIONS,
					getSupportedLangRegion());

			// convert to JSON and save to request with name envJson
			JSONObject jsonEnv = JSONObject.fromObject(urlMappingElem);
			request.setAttribute(ENV_JSON, jsonEnv);

			return true;
		} catch (Exception e) {
			// todo: has error, redirect to default page
			e.printStackTrace();
		}
		return false;
	}

	/*************************************************************************
	 * PRIVATE METHOD
	 ************************************************************************/
 
	/**
	 * 
	 */
	private void processPopulator(final HttpServletRequest request, final String controllerName, UrlMappingElem urlMappingElem) throws Exception {
		if (!StringUtils.isSet(controllerName)) {
			return;
		}
	   	
		UserContextBean userContext = contructUserContext(urlMappingElem);
		String key = controllerName + "_" + userContext.getLanguageId().toLowerCase() + "_" + userContext.getCountryId().toLowerCase();
 		
		logger.debug("key="+key);
		
		if (!ctrlKeyPopulators.containsKey(key)){
			final Map<String, String> controllerViewMap = controllerGWTConfigMap; //getControllerGWTConfigMap();	
			final Map<String, List<ConfigPage>> gwtPageMap =  PageConfigXMLParser.parserGwtConfig(controllerViewMap);				 
			final Map<String, List<PopulatorItem>> retrivePopulatorsForGivenController = PopulatorProcessor.retrivePopulatorsForGivenController(userContext, gwtPageMap, controllerName);
			ctrlKeyPopulators.put(key, retrivePopulatorsForGivenController);
		}
		 
		final Map<String, List<PopulatorItem>> populatorsMap = ctrlKeyPopulators.get(key);
		PopulatorProcessor.gwtSerialized(request, key, populatorsMap);
	}
	

	private UserContextBean contructUserContext(final UrlMappingElem urlMappingElem) {
		UserContextBean userContext = new UserContextBean();
		userContext.setCountryId(urlMappingElem.getCountryId());
		userContext.setLanguageId(urlMappingElem.getLanguageId());
		
		return userContext;
	}
	
	private UrlMappingElem fillRequestAttributes(
			final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {
		UrlMappingElem urlMappingElem = new UrlMappingElem();

		// fill language/country
		fillLanguageRegion(request, urlMappingElem);

		// fill contollerName and hotelId
		fillControllerName(request, urlMappingElem, handler);

		// find hotelId if hotelDetail page.
		fillHotelId(request, urlMappingElem);

		 
		return urlMappingElem;

	}

	/**
	 * fillHotelId, for pattern, /en-us/mvphoteldetail/holtelId
	 * 
	 * @param request
	 * @param urlMappingElem
	 * @throws Exception
	 */
	private void fillHotelId(final HttpServletRequest request,UrlMappingElem urlMappingElem) throws Exception {
		
		final String uri = urlMappingElem.getUri();
		final StringTokenizer tokenizer = new StringTokenizer(uri, FORWARD_SLASH);

		while (tokenizer.hasMoreTokens()) {
			String tokenStr = tokenizer.nextToken();
			if (StringUtils
					.equals(urlMappingElem.getControllerName(), tokenStr)) {
				// next token most likely hotelId
				if (tokenizer.hasMoreTokens()) {
					urlMappingElem.setHotelId(tokenizer.nextToken());
				}
				break;
			}
		}
	}

	/**
	 * Set controller name ControllerName is defined in booking-servlet.xml
	 * 
	 * @param request
	 * @param urlMappingElem
	 * @param handler
	 * @throws Exception
	 */
	private void fillControllerName(final HttpServletRequest request,
			UrlMappingElem urlMappingElem, final Object handler)
			throws Exception {
		if (handler instanceof BaseAbstractController) {
			BaseAbstractController executingController = (BaseAbstractController) handler;
			String controllerName = executingController.getControllerName();
			urlMappingElem.setControllerName(controllerName);
		}
	}

	private void fillLanguageRegion(final HttpServletRequest request,
			UrlMappingElem urlMappingElem) throws Exception {
		String uri = request.getRequestURI();
		urlMappingElem.setUri(uri);
		urlMappingElem.setContextPath(request.getContextPath());
		urlMappingElem.setPrefix(request.getContextPath()
				+ request.getServletPath());
		String domainName = request.getRemoteHost();
		boolean isSecure = request.isSecure();

		try {
			String servletPath = request.getServletPath(); // /en-US
			servletPath = servletPath.substring(1);

			final StringTokenizer tokenizer = new StringTokenizer(servletPath,
					FORWARD_SLASH);

			// 1. get language-Region
			String langRegion = tokenizer.nextToken();
			String[] langRegionArray = langRegion.split("-");
			urlMappingElem.setLanguageId(langRegionArray[0]);
			urlMappingElem.setCountryId(langRegionArray[1]);

		} catch (final NoSuchElementException ex) {
			logger.info("Could not set url mapping elements from uri " + uri);
			throw new Exception("Could not set url mapping elements from uri "
					+ uri);
		}
	}

	public Map<String, String> getSupportedLangRegion() {
		return supportedLangRegion;
	}

	public void setSupportedLangRegion(Map<String, String> supportedLangRegion) {
		this.supportedLangRegion = supportedLangRegion;
	}
	/* 
	public Map<String, String> getControllerGWTConfigMap() {
		return controllerGWTConfigMap;
	}

	public void setControllerGWTConfigMap(Map<String, String> controllerGWTConfigMap) {
		this.controllerGWTConfigMap = controllerGWTConfigMap;
	}*/

	
 
}
