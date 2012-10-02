package com.gogwt.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.helpers.IOUtils;

public class JAXBConverter {

	/**
	 * convert 
	 * @param ref
	 * @return
	 */
	//public static String marshall(ProcessReferralResponse ref) {
	public static String marshall(Object ref) {
		StringWriter sw = new StringWriter();
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ref.getClass());
			
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			marshaller.marshal(ref, sw);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sw.toString();
	}
	
	public static Object unmarshall(String xml, Class<?> clazz) {
		Object obj = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			//JAXBContext jaxbContext = JAXBContext.newInstance(ProcessReferral.class);
			Unmarshaller  unmarshaller  = jaxbContext.createUnmarshaller();
			
			obj = unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return obj;
	}
	
    public static String getReferralXMLFromResource(String location) {
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
		String xml = null;
		try {
			xml = IOUtils.toString(in);
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml;
		
	}
}
