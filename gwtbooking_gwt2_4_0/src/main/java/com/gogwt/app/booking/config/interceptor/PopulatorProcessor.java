package com.gogwt.app.booking.config.interceptor;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.POPULATOR_SERIALIZED_ITEMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.config.interceptor.bean.ConfigPage;
import com.gogwt.app.booking.config.interceptor.bean.PopulatorEntry;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.populator.Populator;
import com.gogwt.app.booking.rpc.interfaces.common.PopulatorRPCService;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;

public class PopulatorProcessor {
	private static Logger logger = Logger.getLogger(PopulatorProcessor.class);

	public static void gwtSerialized(final HttpServletRequest request,
			final String key, final Map<String, List<PopulatorItem>> populatorsMap) {

		// call RPC
		try {
			String serializedString = RPC.encodeResponseForSuccess(
					PopulatorRPCService.class.getDeclaredMethod("getPopulatorInServerSide"),populatorsMap);
			String jsSerializedString = serializedString.replace("'", "\\'");
			
			request.setAttribute(POPULATOR_SERIALIZED_ITEMS, jsSerializedString);

		} catch (SerializationException e) {
			logger.error("could not serialized reservation populator", e);
		} catch (SecurityException e) {
			logger.error("could not serialized reservation populator", e);
		} catch (NoSuchMethodException e) {
			logger.error("could not serialized reservation populator", e);
		}

	}

	/**
	 * Get populator map for given controller name.
	 * 
	 * @param userContext
	 *            UserContext
	 * @param gwtConifgMap
	 * @param controllName
	 * @return Map populator list for given controllerName
	 */
	public static Map<String, List<PopulatorItem>> retrivePopulatorsForGivenController(
			final UserContextBean userContext,
			Map<String, List<ConfigPage>> gwtConifgMap,
			final String controllName) {
		if (!StringUtils.isSet(controllName)) {
			return null;
		}

		List<ConfigPage> configViewList = gwtConifgMap.get(controllName);
		return retrievePopulator(configViewList, userContext);
	}

	/**
	 * retrievePopulator
	 * <p>
	 * Retrieve populator based on
	 * </p>
	 * 
	 * @param moduleName
	 */
	private static Map<String, List<PopulatorItem>> retrievePopulator(
			List<ConfigPage> configViewList, final UserContextBean userContext) {

		if (configViewList == null || configViewList.isEmpty()) {
			return null;
		}

		// 2. get uniquePopulatorList
		final List<PopulatorEntry> uniquePopulatorList = generateUniquePopulatorList(configViewList);

		// 3. retrieve populator
		final Map<String, List<PopulatorItem>> populatorItems = generatePopulatorItem(
				userContext, uniquePopulatorList);

		return populatorItems;
	}

	private static Map<String, List<PopulatorItem>> generatePopulatorItem(
			final UserContextBean userContext,
			final List<PopulatorEntry> populatorList) {
		final Map<String, List<PopulatorItem>> populatorItems = new HashMap<String, List<PopulatorItem>>();

		String className, key;
		Populator populator;
		for (PopulatorEntry populatorEntry : populatorList) {
			className = populatorEntry.getEntry();
			key = populatorEntry.getKey();

			logger.debug(" populator className=" + className);
			try {
				populator = (Populator) Class.forName(className).newInstance();
				populatorItems.put(key, populator.getPopulator(userContext));

			} catch (ClassNotFoundException e) {
				populatorItems.put(key, null);
				logger.error("error with populator: " + key, e);
			} catch (InstantiationException e) {
				populatorItems.put(key, null);
				logger.error("error with populator: " + key, e);
			} catch (IllegalAccessException e) {
				populatorItems.put(key, null);
				logger.error("error with populator: " + key, e);
			}
		}
		return populatorItems;
	}

	private static List<PopulatorEntry> generateUniquePopulatorList(
			final List<ConfigPage> configViewList) {
		final List<PopulatorEntry> uniquePopulatorList = new ArrayList<PopulatorEntry>();

		for (ConfigPage configView : configViewList) {
			if (configView.getPopulatorList() != null) {
				for (PopulatorEntry populatorEntry : configView
						.getPopulatorList()) {
					if (!hasKeyAlready(populatorEntry.getKey(),
							uniquePopulatorList)) {
						uniquePopulatorList.add(populatorEntry);
					}
				}
			}
		}
		return uniquePopulatorList;
	}

	/**
	 * 
	 * <p>
	 * hasKeyAlready
	 * </p>
	 * 
	 * @param key
	 * @param uniquePopulatorList
	 * @return
	 */
	private static boolean hasKeyAlready(final String key,
			List<PopulatorEntry> uniquePopulatorList) {
		if (uniquePopulatorList == null || uniquePopulatorList.isEmpty()) {
			return false;
		}

		for (PopulatorEntry existPopulatorEntry : uniquePopulatorList) {
			if (StringUtils.equals(key, existPopulatorEntry.getKey())) {
				return true;
			}
		}
		return false;
	}
}
