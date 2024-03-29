package com.gogwt.framework.rebind;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.gogwt.framework.arch.navigation.PageConfig;
import com.gogwt.framework.arch.navigation.PageConfigAccessor;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class PageConfigAccessorInterfaceGenerator extends Generator {
	private final static String ROOT_CONF_PATH = "conf/gwt/";

	/** Simple name of class to be generated */
	private transient String generatedImplClassName = null;

	/** Package name of class to be generated */
	private transient String packageName = null;

	private transient JClassType pageAccessorInterfaceClassType = null;
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		try {
			final TypeOracle typeOracle = context.getTypeOracle();
			// get classType and save instance variables
			pageAccessorInterfaceClassType = typeOracle.getType(typeName);
			packageName = pageAccessorInterfaceClassType.getPackage().getName();
			generatedImplClassName = pageAccessorInterfaceClassType.getSimpleSourceName()+ "Impl";

			// Generate class source code
			generateClass(logger, context);

		} catch (Exception e) {
			// record to logger that Map generation threw an exception
			logger.log(TreeLogger.ERROR,"Code Generation Error: PageConfigAccessorInterfaceGenerator", e);
		}

		// return the fully qualifed name of the class generated
		return packageName + "." + generatedImplClassName;

	}
	
	private void generateClass(final TreeLogger logger,
			final GeneratorContext context) {

		// get print writer that receives the source code
		PrintWriter printWriter = null;
		printWriter = context.tryCreate(logger, packageName,
				generatedImplClassName);

		// print writer if null, source code has ALREADY been generated, return
		if (printWriter == null) {
			return;
		}

		// init composer, set class properties, create source writer
		ClassSourceFileComposerFactory composer = null;
		composer = new ClassSourceFileComposerFactory(packageName,
				generatedImplClassName);

		// provide imports for the generated Impl class
		composer.addImport(Composite.class.getCanonicalName());
		composer.addImport(List.class.getCanonicalName());
		composer.addImport(Map.class.getCanonicalName());
		composer.addImport(HashMap.class.getCanonicalName());
		composer.addImport(AbstractPageConfigAccessor.class.getCanonicalName());
		composer.addImport(AbstractPage.class.getCanonicalName());
		composer.addImport(PageConfig.class.getCanonicalName());

		composer.setSuperclass(AbstractPageConfigAccessor.class.getSimpleName());
		composer.addImplementedInterface(PageConfigAccessor.class.getCanonicalName());

		// source code generator
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);

		// generator constructor source code
		generateConstructor(sourceWriter);

		// generator method source code
	    generateViewConfigMethodsByXML(logger, sourceWriter );
	    

		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");

		// commit generated class
		context.commit(logger, printWriter);

	}
	
	/************************************************************************
	 * PRIVATE METHOD
	 ************************************************************************/
	  /**
	   * <p>
	   * Generate source code for the default constructor. 
	   * Create default constructor, call super()
	   * Instantiates views and viewConfigInstances
	   * </p>
	   * @param sourceWriter
	   *          Source writer to output source code
	   */
	  private void generateConstructor( final SourceWriter sourceWriter )
	  {

	    // start constructor source generation
	    sourceWriter.println( "public " + generatedImplClassName + "() { " );
	    sourceWriter.indent();
	    sourceWriter.println( "super();" );
	    sourceWriter.println( "pageConfigInstances = new HashMap<String, PageConfig>();" );

	    // end constructor source generation
	    sourceWriter.outdent();
	    sourceWriter.println( "}" );

	  } 
	/**
	   * <p>
	   * Generates code for lazyCreateOrGetPageConfig(String token) 
	   * and getPageTokens() implementations using an xml file
	   * </p>
	   * @param writer
	   */
	  private void generateViewConfigMethodsByXML(final TreeLogger logger, final SourceWriter writer) {
	    try {
	      final String path = ROOT_CONF_PATH + this.pageAccessorInterfaceClassType.getSimpleSourceName() + ".xml";
	      
	      logger.log( TreeLogger.INFO, path );
	      
	      PageConfigXMLCodeGenerator.createPageConfigAccessorMethods( writer, path );
	    } catch (Exception e) {
	      logger.log( TreeLogger.ERROR, "An error occured in xml parser usage");
	    }
	  }
}
