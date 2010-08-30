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

import com.gogwt.framework.arch.widgets.AbstractView;
import com.google.gwt.user.rebind.SourceWriter;


/**
 * <code><B>ViewConfigXMLCodeGenerator<code><B>
 * <p/>
 * ViewConfigAccessorInterfaceGenerator defers reading xml and
 * generating code to this class
 * <p/>
 * @deprecated
 */
@SuppressWarnings("unchecked")
public class ViewConfigXMLCodeGenerator
{

  /**
   * <p>
   * Generate the implementation code for the various methods needed
   * for the ViewConfigAccessor interface 
   * </p>
   * @param sw
   * @param xmlFilePath
   * @throws Exception
   */
  public static void createViewConfigAccessorMethods(SourceWriter sw, String xmlFilePath) throws Exception {
    //1. construct Document based on xml file
    final Document doc = readXml(xmlFilePath);
    
    //2. process views
    generateLazyCreateOrGetViewConfigMethod(sw, readXml(xmlFilePath));
    
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
   * Initializes the viewTokens static variable in a static block
   * </p>
   * @param sw
   * @param jdomDocument
   * @param tokens
   * @throws Exception
   */
  private static void generateStaticViewTokensArray(SourceWriter sw, Document jdomDocument, List<String> tokens) throws Exception {
    // print tokens
    String viewTokenArrVals = "";
    for (Iterator<String> i=tokens.iterator(); i.hasNext(); ) {
      viewTokenArrVals += "\"" + i.next() + "\"";
      if (i.hasNext())
        viewTokenArrVals += ",";
    } 
    sw.println( "{");
    sw.println( " viewTokens = new String[]{"+ viewTokenArrVals + "};");
    sw.println( "}");
  }
  
  @SuppressWarnings("unused")
  private static void generatePropertyImports(SourceWriter sw, Parent parentElement) throws Exception {
    List<Element> propertyImports = XPath.selectNodes(parentElement, "/application/imports//import");
    if (propertyImports == null) 
      return;
    for (Iterator<Element> i=propertyImports.iterator();i.hasNext();) {
      generateProperties(sw,readXml(i.next().getAttributeValue( "path" )), "applicationProperties");
    }
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
   * Generate the lazyCreateOrGetViewConfig(String) method imlpementation
   * </p>
   * @param sw
   * @param jdomDocument
   * @throws Exception
   */
  private static void generateLazyCreateOrGetViewConfigMethod(SourceWriter sw, Document jdomDocument) throws Exception {
    List<String> tokens = new ArrayList<String>();
    
    // get child nodes, and display their id attribute values
    sw.println( "public ViewConfig lazyCreateOrGetViewConfig( String token ) {");
    sw.println( AbstractView.class.getSimpleName() + " viewInstance = null;");
    sw.println( "ViewConfig viewConfigInstance = viewConfigInstances.get(token);");
    sw.println( "if ( viewConfigInstance != null ) ");
    sw.println( " return viewConfigInstance; ");
    sw.println( "viewConfigInstance = new ViewConfig();");

    // for each view
    List<Element> views = XPath.selectNodes(jdomDocument, "/application/views//view");
    String nameValue =""; 
    String classValue="";
    boolean isDefault;
    boolean seoCrawlable;
    
    sw.println( "Map<String,Map<String,String>> properties = new HashMap<String,Map<String,String>>();" );
    //todo: what's that
    sw.println( "if (1 != 1) ;" );
    
    for (Iterator<Element> iter = views.iterator(); iter.hasNext(); ) {
      Element view = (Element) iter.next();
      nameValue = "\""+view.getAttributeValue( "name" ).toLowerCase()+"\"";
      classValue = view.getAttributeValue( "class" );

      isDefault = Boolean.parseBoolean( view.getAttributeValue( "isDefault" ) );
      //seoCrawlable = Boolean.parseBoolean( view.getAttributeValue( "seoCrawlable" ) );
      
      sw.println( "else if (token.equals("+nameValue+")) {" );
      sw.println( "viewConfigInstance = new ViewConfig("+nameValue+", new "+classValue+"());" );
      //sw.println( "viewConfigInstance.setSeoCrwalable(seoCrawlable);" );
      
      // generate properties
      generateProperties(sw,view, "properties");
      sw.println( "viewConfigInstance.setProperties(properties);" );
      
      if (isDefault) 
        tokens.add(0, view.getAttributeValue( "name" ).toLowerCase() );
      else 
        tokens.add(view.getAttributeValue( "name" ).toLowerCase() );
      sw.println( "}" );
    }
    sw.println( "viewConfigInstances.put(token,viewConfigInstance);");
    sw.println( "return viewConfigInstance; ");
    sw.println( "}" );
    
    
    generateStaticViewTokensArray(sw, jdomDocument, tokens);
  }
}
