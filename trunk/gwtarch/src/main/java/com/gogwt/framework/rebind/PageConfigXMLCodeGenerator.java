package com.gogwt.framework.rebind;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.google.gwt.user.rebind.SourceWriter;


/**
 * <code><B>PageConfigXMLCodeGenerator<code><B>
 * <p/>
 * PageConfigAccessorInterfaceGenerator defers reading xml and
 * generating code to this class
 * <p/>
 *  
 */

public class PageConfigXMLCodeGenerator {
	 /**
	   * <p>
	   * Generate the implementation code for the various methods needed
	   * for the PageConfigAccessor interface 
	   * </p>
	   * @param sw
	   * @param xmlFilePath
	   * @throws Exception
	   */
	  public static void createPageConfigAccessorMethods(SourceWriter sw, String xmlFilePath) throws Exception {
	    //1. construct Document based on xml file
	    final Document doc = readXml(xmlFilePath);
	    
	    //2. process views
	    generateLazyCreateOrGetPageConfigMethod(sw, readXml(xmlFilePath));
	    
	  }
	  
	  /**
	   * <p>
	   * Provide a jdom document based on the xml file path provided
	   * </p>
	   * @param xmlFilePath
	   * @return
	   * @throws Exception
	   */
	  private static Document readXml(String xmlFilePath) throws Exception {
	    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	    InputStream inStream = classloader.getResourceAsStream(xmlFilePath);
	    
	    SAXBuilder saxBuilder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
	   
	    return saxBuilder.build(inStream);
	    
	  }	  	  
	  
	  /**
	   * <p>
	   * Generates code to add properties defined in the xml file into a map object
	   * </p>
	   * @param sw
	   * @param parentElement
	   * @param destination
	   * @throws Exception
	   */
	  private static void generateSinglePropertyCode(SourceWriter sw, Element parentElement, String destination) throws Exception {
	    String propertyName = "\"" + parentElement.getAttributeValue( "name" ).toLowerCase() + "\"";
	    List<Element> entries = XPath.selectNodes(parentElement, "map//entry");
	    if (entries == null) 
	      return;
	    for (Iterator<Element> propertiesIterator = entries.iterator(); propertiesIterator.hasNext(); ) {
	      Element property = (Element) propertiesIterator.next();
	      sw.println( "addPropertyValue("+propertyName+",\""+property.getAttributeValue( "key" )+"\", \""+property.getText()+"\", "+destination+");" );
	    }
	        
	  }

	  /**
	   * Generate forward attribute
	   * @param sw
	   * @param parentElement
	   * @param destination
	   * @throws Exception
	   */
	  private static void generateForward(SourceWriter sw, Parent parentElement, String destination) throws Exception {
	      List<Element> forwardList = XPath.selectNodes(parentElement, "forward");
	      if (forwardList != null && !forwardList.isEmpty()) {
	    	  for (Element forward : forwardList) {
	 	         String name = "\"" + forward.getAttributeValue("name") + "\"";
		         String token = "\"" + forward.getAttributeValue("token") + "\"";
 		         sw.println( "addForwardValue("+ name +", " +token+ ", "+destination+");" );
 	    	  }	         
	      } 
	  }
	  
	  /**
	   * <p>
	   * Generate code related to properties provided in the xml file
	   * </p>
	   * @param sw
	   * @param parentElement
	   * @param destination
	   * @throws Exception
	   */
	  private static void generateProperties(SourceWriter sw, Parent parentElement, String destination) throws Exception {
	    List<Element> properties = XPath.selectNodes(parentElement, "properties//property");
	    if (properties == null) 
	      return;
	    for (Iterator<Element> propertiesIterator = properties.iterator(); propertiesIterator.hasNext(); ) {
	      generateSinglePropertyCode(sw, propertiesIterator.next(), destination);
	    }
	  }
	  
	  
	  /**
	   * <p>
	   * Generate the lazyCreateOrGetPageConfig(String) method imlpementation
	   * </p>
	   * @param sw
	   * @param jdomDocument
	   * @throws Exception
	   */
	  private static void generateLazyCreateOrGetPageConfigMethod(SourceWriter sw, Document jdomDocument) throws Exception {
	    List<String> tokens = new ArrayList<String>();
	    
	    // get child nodes, and display their id attribute values
	    sw.println( "public PageConfig lazyCreateOrGetPageConfig( String token ) {");
	    //sw.println( AbstractPage.class.getSimpleName() + " pageInstance = null;");
	    sw.println( "PageConfig pageConfigInstance = pageConfigInstances.get(token);");
	    sw.println( "if ( pageConfigInstance != null ) ");
	    sw.println( " return pageConfigInstance; ");
	    sw.println( "pageConfigInstance = new PageConfig();");

	    // for each view
	    List<Element> views = XPath.selectNodes(jdomDocument, "/application/pages//page");
	    String nameValue =""; 
	    String classValue="";
	    boolean isDefault;
	      
	    sw.println( "Map<String,Map<String,String>> properties = new HashMap<String,Map<String,String>>();" );
	    sw.println( "Map<String,String> forward = new HashMap<String,String>();" );
	    
	    //skip the first one
	    sw.println( "if (1 != 1) ;" );
	    
	    for (Iterator<Element> iter = views.iterator(); iter.hasNext(); ) {
	      Element view = (Element) iter.next();
	      nameValue = "\""+view.getAttributeValue( "name" ).toLowerCase()+"\"";
	      classValue = view.getAttributeValue( "class" );

	      isDefault = Boolean.parseBoolean( view.getAttributeValue( "isDefault" ) );
	       
	      sw.println( "else if (token.equals("+nameValue+")) {" );
	      sw.println( "pageConfigInstance = new PageConfig("+nameValue+", new "+classValue+"());" );
	       
	      // generate properties
	      generateProperties(sw,view, "properties");
	      sw.println( "pageConfigInstance.setProperties(properties);" );
	      
	      if (isDefault) 
	        tokens.add(0, view.getAttributeValue( "name" ).toLowerCase() );
	      else 
	        tokens.add(view.getAttributeValue( "name" ).toLowerCase() );
	      
	      // generate forward
	      generateForward(sw, view, "forward");
	      sw.println( "pageConfigInstance.setForward(forward);" );
	
	      sw.println( "}" );
	    }
	    sw.println( "pageConfigInstances.put(token,pageConfigInstance);");
	    sw.println( "return pageConfigInstance; ");
	    sw.println( "}" );
	    
	    
	    generateStaticPageTokensArray(sw, jdomDocument, tokens);
	  }
	  
	  /**
	   * <p>
	   * Initializes the pageTokens static variable in a static block
	   * </p>
	   * @param sw
	   * @param jdomDocument
	   * @param tokens
	   * @throws Exception
	   */
	  private static void generateStaticPageTokensArray(SourceWriter sw, Document jdomDocument, List<String> tokens) throws Exception {
	    // print tokens
	    String viewTokenArrVals = "";
	    for (Iterator<String> i=tokens.iterator(); i.hasNext(); ) {
	      viewTokenArrVals += "\"" + i.next() + "\"";
	      if (i.hasNext())
	        viewTokenArrVals += ",";
	    } 
	    sw.println( "{");
	    sw.println( " pageTokens = new String[]{"+ viewTokenArrVals + "};");
	    sw.println( "}");
	  }
}
