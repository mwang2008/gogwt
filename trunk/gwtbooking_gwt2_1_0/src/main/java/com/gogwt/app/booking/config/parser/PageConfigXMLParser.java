package com.gogwt.app.booking.config.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.gogwt.app.booking.config.interceptor.bean.ConfigPage;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.exceptions.clientserver.AppRuntimeException;

public class PageConfigXMLParser {
	private static Map<String, List<PopulatorItem>> populatorMap;
	
	private PageConfigXMLParser() {
		populatorMap = new HashMap<String, List<PopulatorItem>>();
	}

	
	/**
	 * Get Controller Map of <ControllerName, Map<Page, List<ConfigPage>>
	 * @param controllerViewMap  the map defined in gwtbooking-servlet.xml
	 * @param controllName the controller name
	 * @return  map of <ControllerName, Map<Page, List<ConfigPage>>
	 * @throws Exception
	 */
	public static Map<String, List<ConfigPage>> getPopulatorsForControllerMap (
			final Map<String, String> controllerViewMap) throws Exception {
		
		//controllerName, page element list 
		final Map<String, List<ConfigPage>> ctrlNamePageMap = parserGwtConfig(controllerViewMap);
		
		//flat above, to get controllerName, whole list of populator
		for (Map.Entry<String, List<ConfigPage>> entry : ctrlNamePageMap.entrySet()) {
			
		}
		
		return null;
	}
	
    /**
     * Parse controllerViewMap  the map defined in gwtbooking-servlet.xml 
     * name, class and populator list for each page component.
     * 
     * @param controllerViewMap
     * @return Map of <controllerName, List<ConfigPage>>
     * @throws Exception
     */
	public static Map<String, List<ConfigPage>> parserGwtConfig(final Map<String, String> controllerViewMap) throws Exception {
	 	
		String controllerName, xmlFilePath;

		final Map<String, List<ConfigPage>> controllNameViewList = new HashMap<String, List<ConfigPage>>();

		if (controllerViewMap != null) {
			for (Iterator<Entry<String, String>> item = controllerViewMap.entrySet().iterator(); item.hasNext();) {

				Entry<String, String> theEntry = item.next();
				controllerName = theEntry.getKey().trim();
				xmlFilePath = theEntry.getValue(); // conf/gwt/ReservationProcessConfig.xml

				List<ConfigPage> configList = null;
				 
				configList = parseProcessConfig(xmlFilePath);
 
				controllNameViewList.put(controllerName, configList);
			}
		}

		return controllNameViewList;
	}
	
	/**
	 * 
	 * <p> Parse view config </p>
	 * 
	 * @param xmlFilePath
	 * @return
	 * @throws Exception
	 */
	private static ArrayList<ConfigPage> parseProcessConfig(final String xmlFilePath) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(xmlFilePath);

		SAXBuilder saxBuilder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		ArrayList<ConfigPage> config = null;

		try {
			Document doc = saxBuilder.build(inStream);
			config = parseConfig(doc);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}

		return config;

	}

	/**
	 * 
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<ConfigPage> parseViewConfig(final InputStream inStream) {
		SAXBuilder saxBuilder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		ArrayList<ConfigPage> config = null;

		try {
			Document doc = saxBuilder.build(inStream);
			config = parseConfig(doc);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}

		return config;
	}

	/**
	 * 
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param jdomDocument
	 * @return
	 * @throws JDOMException
	 */
	private static ArrayList<ConfigPage> parseConfig(final Document jdomDocument) throws JDOMException {
		ArrayList<ConfigPage> configViewList = new ArrayList<ConfigPage>();

		ConfigPage configView = null;

		String key, entry;

		// for each view
		List<Element> views = XPath.selectNodes(jdomDocument, "/application/pages//page");
		for (Iterator<Element> iter = views.iterator(); iter.hasNext();) {
			configView = new ConfigPage();

			Element view = (Element) iter.next();

			configView.setPageName(view.getAttributeValue("name"));
			configView.setPageClass(view.getAttributeValue("class"));

			List<Element> properties = XPath.selectNodes(view, "properties//property//map//entry");
			if (properties != null) {
				for (Iterator<Element> propertiesIterator = properties.iterator(); propertiesIterator.hasNext();) {
					Element theProperty = propertiesIterator.next();
					key = theProperty.getAttributeValue("key");
					entry = theProperty.getText();

					configView.addPopulator(key, entry);
				}
			}

			configViewList.add(configView);
		}

		return configViewList;

	}


	public static Map<String, List<PopulatorItem>> getPopulatorMap() {
		return populatorMap;
	}


	public static void setPopulatorMap(Map<String, List<PopulatorItem>> populatorMap) {
		PageConfigXMLParser.populatorMap = populatorMap;
	}

	
 
}
