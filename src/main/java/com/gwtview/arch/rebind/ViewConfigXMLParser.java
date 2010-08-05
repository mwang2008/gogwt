package com.gwtview.arch.rebind;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public final class ViewConfigXMLParser {
	/**
	 * 
	 * <p>
	 * Parse view config
	 * </p>
	 * 
	 * @param xmlFilePath
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws Exception
	 */
	public static ConfigXMLHolder parseProcessConfig(
			final String xmlFilePath) throws JDOMException, IOException   {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(xmlFilePath);

		SAXBuilder saxBuilder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		Document doc = saxBuilder.build(inStream);

		return parseConfig(doc);

	}

	/**
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static ConfigXMLHolder parseProcessConfig(
			final InputStream inStream) throws Exception {
		 
		SAXBuilder saxBuilder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		Document doc = saxBuilder.build(inStream);

		return parseConfig(doc);

	}
	   /**
	    * 
	    * <p>
	    * 
	    * </p>
	    * @param jdomDocument
	    * @return
	    * @throws JDOMException
	    */
	   private static ConfigXMLHolder parseConfig(final Document jdomDocument ) throws JDOMException {
	     ConfigXMLHolder configXmlHolder = new ConfigXMLHolder();
	     	 
	     String name;
	     
	     String key, entry;
	     
	     //1. for forms
	     ConfigXMLForm configForm = null;
	     HashMap<String, ConfigXMLForm> formMap = new HashMap<String, ConfigXMLForm>();
	     
	     List<Element> forms = XPath.selectNodes(jdomDocument, "/application/forms//form" );
	     for( Iterator<Element> iter = forms.iterator(); iter.hasNext(); ) {
	    	 configForm = new ConfigXMLForm();
	    	 Element view = (Element)iter.next();
	    	 name = view.getAttributeValue( "name" );
	    	 configForm.setFormName( name );
	    	 configForm.setFormClass(view.getAttributeValue( "type" ));
		     
	    	 formMap.put(name, configForm);
	     }
	     configXmlHolder.setFormMap(formMap);
	     
	     //2. value section
	     ConfigXMLValue configValue = null;
	     HashMap<String, ConfigXMLValue> valueMap = new HashMap<String, ConfigXMLValue>();
	     
	     List<Element> values = XPath.selectNodes(jdomDocument, "/application/values//value" );
	     for( Iterator<Element> iter = values.iterator(); iter.hasNext(); ) {
	    	 configValue = new ConfigXMLValue();
	    	 Element view = (Element)iter.next();
	    	 
	    	 name = view.getAttributeValue( "name" );
	    	 configValue.setValueName( name );
	    	 configValue.setValueClass(view.getAttributeValue( "type" ));
		     
	    	 valueMap.put(name, configValue);
	     }
	     configXmlHolder.setValueMap(valueMap);
	     
	     //3. for each view
	     ConfigXMLView configView = null;
	     HashMap<String, ConfigXMLView> viewMap = new LinkedHashMap<String, ConfigXMLView>();
	     
	     List<Element> views = XPath.selectNodes(jdomDocument, "/application/views//view" );
	     for( Iterator<Element> iter = views.iterator(); iter.hasNext(); ) {
	       configView = new ConfigXMLView();
	       
	       Element view = (Element)iter.next();
	       
	       name = view.getAttributeValue( "name" );
	       configView.setViewName( name );
	       configView.setViewClass( view.getAttributeValue( "type" ));
	       configView.setForm( view.getAttributeValue( "form" ) );
	       configView.setValue( view.getAttributeValue( "value" ));
	       configView.setViewClass(view.getAttributeValue( "class" ));
	       
/*	       List<Element> properties = XPath.selectNodes(view, "properties//property//map//entry");  
	       if (properties != null) {
	         for (Iterator<Element> propertiesIterator = properties.iterator(); propertiesIterator.hasNext(); ) {
	           Element theProperty = propertiesIterator.next();
	           key = theProperty.getAttributeValue( "key" );
	           entry = theProperty.getText();
	           
	           configView.addPopulator(key, entry);
	         }
	       }
*/	       
	       viewMap.put(name, configView);	       
	     }
	     
	     configXmlHolder.setViewMap(viewMap);
	     
	     return configXmlHolder;
	     
	   }
	  
	   
}

