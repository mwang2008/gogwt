package com.gogwt.app.booking.businessService.geocode.google;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResultBean;
import com.gogwt.app.booking.utils.StringUtils;

public final class GoogleGeocodeKMLParser {
	private static Logger logger = Logger
			.getLogger(GoogleGeocodeKMLParser.class);

	/**
	 * Parse geocode KML got from Google geocode service
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static GeocodeResponseBean parserKML(final InputStream inStream) throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();

		final Document doc = db.parse(inStream);
		doc.getDocumentElement().normalize();

		
		final GeocodeResponseBean geocodeResponseBean = new GeocodeResponseBean();
		
		int statusCode = getStatusCode(doc);
		logger.info("statusCode=" + statusCode);
 		
		geocodeResponseBean.setCode(statusCode);
		
		ArrayList<GeocodeResultBean> geocodeResultList = new ArrayList<GeocodeResultBean>();
		
		GeocodeResultBean geocodeResultBean;
		if (statusCode == 200) {
			NodeList placemarkNodeList = doc.getElementsByTagName("Placemark");
			for (int s = 0; s < placemarkNodeList.getLength(); s++) {
				

				Node placemarkNode = placemarkNodeList.item(s);
				if (placemarkNode.getNodeType() == Node.ELEMENT_NODE) {
					Element placemarkElement = (Element) placemarkNode;
					
					geocodeResultBean = new GeocodeResultBean();
					
					geocodeResultBean.setFullAddress(getNodeValueByTagName(placemarkElement, "address"));
					geocodeResultBean.setCountryCode(getNodeValueByTagName(placemarkElement, "CountryNameCode"));
					geocodeResultBean.setCountryName(getNodeValueByTagName(placemarkElement, "CountryNameCode"));
					geocodeResultBean.setStateId(getNodeValueByTagName(placemarkElement, "AdministrativeAreaName"));
					geocodeResultBean.setCounty(getNodeValueByTagName(placemarkElement, "SubAdministrativeAreaName"));
					geocodeResultBean.setCity(getNodeValueByTagName(placemarkElement, "LocalityName"));

					String coordinates = getNodeValueByTagName(placemarkElement, "coordinates");
					fillLatLng(geocodeResultBean, coordinates);

					geocodeResultBean.setAddress(getNodeValueByTagName(placemarkElement, "ThoroughfareName"));
					geocodeResultBean.setZip(getNodeValueByTagName(placemarkElement, "PostalCodeNumber"));
					
					geocodeResultList.add(geocodeResultBean);
				}								
			}
		}
		
		geocodeResponseBean.setGeocodeDataList(geocodeResultList);

		return geocodeResponseBean;
	}

	private static void fillLatLng(GeocodeResultBean geocodeResultBean,
			String coordinates) {
		try {
			if (StringUtils.isSet(coordinates)) {
				String items[] = coordinates.split(",");
				String lngStr = items[0];
				String latStr = items[1];
				geocodeResultBean.setLat(Double.parseDouble(latStr));
				geocodeResultBean.setLng(Double.parseDouble(lngStr));
			}
		} catch (Throwable e) {
			//do nothing
		}
	}

	private static int getStatusCode(final Document doc) {
		NodeList nodeLst = doc.getElementsByTagName("code");
		Element firstElement = (Element) nodeLst.item(0);
		NodeList firstNodeList = firstElement.getChildNodes();
		String status = ((Node) firstNodeList.item(0)).getNodeValue();

		return Integer.parseInt(status);
	}

	/**
	 * Get single node value, Return null, if no tag is found
	 * 
	 * @param placemarkElement
	 * @param tagName
	 * @return
	 */
	private static String getNodeValueByTagName(final Element theElement,
			final String tagName) {

		NodeList elementList = theElement.getElementsByTagName(tagName);

		if (elementList == null || elementList.getLength() == 0) {
			return null;
		}

		Element firstElement = (Element) elementList.item(0);
		NodeList firstNodeList = firstElement.getChildNodes();
		String nodeValue = ((Node) firstNodeList.item(0)).getNodeValue();

		return nodeValue;
	}
}

/*
 * 
 * <?xml version="1.0" encoding="UTF-8"?> <kml
 * xmlns="http://earth.google.com/kml/2.0"><Response> <name>rome</name> <Status>
 * <code>200</code> <request>geocode</request> </Status> <Placemark id="p1">
 * <address>Rome, Italy</address> <AddressDetails Accuracy="4"
 * xmlns="urn:oasis:names:tc:ciq:xsdschema:xAL:2.0"> <Country>
 * <CountryNameCode>IT</CountryNameCode> <CountryName>Italia</CountryName>
 * <AdministrativeArea> <AdministrativeAreaName>Lazio</AdministrativeAreaName>
 * <SubAdministrativeArea>
 * <SubAdministrativeAreaName>RM</SubAdministrativeAreaName> <Locality>
 * <LocalityName>Roma</LocalityName> </Locality> </SubAdministrativeArea>
 * </AdministrativeArea> </Country> </AddressDetails> <ExtendedData> <LatLonBox
 * north="42.1606919" south="41.6291333" east="12.9945619" west="11.9700867"/>
 * </ExtendedData>
 * <Point><coordinates>12.4823243,41.8954656,0</coordinates></Point>
 * </Placemark> </Response></kml>
 * 
 * 
 * <?xml version="1.0" encoding="UTF-8"?> <kml
 * xmlns="http://earth.google.com/kml/2.0"><Response> <name>atlanta</name>
 * <Status> <code>200</code> <request>geocode</request> </Status> <Placemark
 * id="p1"> <address>Atlanta, GA, USA</address> <AddressDetails Accuracy="4"
 * xmlns="urn:oasis:names:tc:ciq:xsdschema:xAL:2.0">
 * <Country><CountryNameCode>US</CountryNameCode> <CountryName>USA</CountryName>
 * <AdministrativeArea> <AdministrativeAreaName>GA</AdministrativeAreaName>
 * <SubAdministrativeArea>
 * <SubAdministrativeAreaName>Fulton</SubAdministrativeAreaName> <Locality>
 * <LocalityName>Atlanta</LocalityName> </Locality> </SubAdministrativeArea>
 * </AdministrativeArea> </Country> </AddressDetails> <ExtendedData> <LatLonBox
 * north="33.8231844" south="33.6747422" east="-84.2599230" west="-84.5160418"/>
 * </ExtendedData>
 * <Point><coordinates>-84.3879824,33.7489954,0</coordinates></Point>
 * </Placemark> </Response></kml>
 * 
 * 
 * <?xml version="1.0" encoding="UTF-8"?> <kml
 * xmlns="http://earth.google.com/kml/2.0"><Response> <name>3 ravinia drive,
 * atlanta,ga 30346</name> <Status> <code>200</code> <request>geocode</request>
 * </Status> <Placemark id="p1"> <address>3 Ravinia Dr, Atlanta, GA 30346,
 * USA</address> <AddressDetails Accuracy="8"
 * xmlns="urn:oasis:names:tc:ciq:xsdschema:xAL:2.0"> <Country>
 * <CountryNameCode>US</CountryNameCode> <CountryName>USA</CountryName>
 * <AdministrativeArea> <AdministrativeAreaName>GA</AdministrativeAreaName>
 * <SubAdministrativeArea>
 * <SubAdministrativeAreaName>DeKalb</SubAdministrativeAreaName> <Locality>
 * <LocalityName>Atlanta</LocalityName> <Thoroughfare> <ThoroughfareName>3
 * Ravinia Dr</ThoroughfareName> </Thoroughfare> <PostalCode>
 * <PostalCodeNumber>30346</PostalCodeNumber> </PostalCode> </Locality>
 * </SubAdministrativeArea> </AdministrativeArea> </Country> </AddressDetails>
 * <ExtendedData> <LatLonBox north="33.9251986" south="33.9189034"
 * east="-84.3344314" west="-84.3407266"/> </ExtendedData>
 * <Point><coordinates>-84.3352340,33.9209280,0</coordinates></Point>
 * </Placemark> </Response></kml>
 */
