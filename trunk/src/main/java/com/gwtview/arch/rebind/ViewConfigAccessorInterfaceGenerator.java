package com.gwtview.arch.rebind;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtview.arch.navigation.AbstractViewConfigAccessor;
import com.gwtview.arch.navigation.ViewConfig;
import com.gwtview.arch.navigation.ViewConfigAccessor;
import com.gwtview.arch.widgets.AbstractView;

/**
 * 
 * @author WangM
 * 
 */
public class ViewConfigAccessorInterfaceGenerator extends Generator {

	private final static String ROOT_CONF_PATH = "conf/gwt/";

	/** Simple name of class to be generated */
	private transient String generatedImplClassName = null;

	/** Package name of class to be generated */
	private transient String packageName = null;

	private transient JClassType viewAccessorInterfaceClassType = null;

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		try {
			final TypeOracle typeOracle = context.getTypeOracle();
			// get classType and save instance variables
			viewAccessorInterfaceClassType = typeOracle.getType(typeName);
			packageName = viewAccessorInterfaceClassType.getPackage().getName();
			generatedImplClassName = viewAccessorInterfaceClassType
					.getSimpleSourceName()
					+ "Impl";

			// Generate class source code
			generateClass(logger, context);

		} catch (Exception e) {
			// record to logger that Map generation threw an exception
			logger
					.log(
							TreeLogger.ERROR,
							"Code Generation Error: ViewAccessorInterfaceProxyGenerator",
							e);
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
		composer.addImport(AbstractViewConfigAccessor.class.getCanonicalName());
		composer.addImport(AbstractView.class.getCanonicalName());
		composer.addImport(ViewConfig.class.getCanonicalName());

		composer
				.setSuperclass(AbstractViewConfigAccessor.class.getSimpleName());
		composer.addImplementedInterface(ViewConfigAccessor.class
				.getCanonicalName());

		// source code generator
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);

		// generator constructor source code
		generateConstructor(sourceWriter);

		generateMethod_ToString(sourceWriter);
		// generator method source code
		// generateViewConfigMethodsByXML(logger, sourceWriter );

		final ConfigXMLHolder configVeiewHolder = retrieveViewConfigXML(logger);
		if (configVeiewHolder != null) {
			generateMethod(sourceWriter, configVeiewHolder);
		}

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
	 * Generates code for lazyCreateOrGetViewConfig(String token) and
	 * getViewTokens()
	 */
	private void generateMethod(final SourceWriter sourceWriter,
			final ConfigXMLHolder configVeiewHolder) {
		generate_lazyCreateOrGetViewConfig(sourceWriter, configVeiewHolder);
	}

	private void generate_lazyCreateOrGetViewConfig(
			final SourceWriter sourceWriter,
			final ConfigXMLHolder configVeiewHolder) {
		List<String> tokens = new ArrayList<String>();
		String viewTokenArrVals = "";

		// get child nodes, and display their id attribute values
		sourceWriter
				.println("public ViewConfig lazyCreateOrGetViewConfig( String token ) {");
		sourceWriter.println("  " + AbstractView.class.getSimpleName()
				+ " viewInstance = null;");
		sourceWriter
				.println("  ViewConfig viewConfigInstance = viewConfigInstances.get(token);");
		sourceWriter.println("  if ( viewConfigInstance != null ) ");
		sourceWriter.println("     return viewConfigInstance; ");
		sourceWriter.println("   viewConfigInstance = new ViewConfig();");

		// for each view
		String nameValue = "";
		String classValue = "";

		sourceWriter
				.println("  Map<String,Map<String,String>> properties = new HashMap<String,Map<String,String>>();");

		// skip the first one
		sourceWriter.println("  if (1 != 1) ;");

		int i = 0;
		final Map views = configVeiewHolder.getViewMap();
		for (Iterator<Entry<String, ConfigXMLView>> item = views.entrySet()
				.iterator(); item.hasNext();) {
			
			Entry<String, ConfigXMLView> theEntry = item.next();
			nameValue = theEntry.getKey();
			classValue = theEntry.getValue().getViewClass(); //.getValue();
			
			sourceWriter.println("  else if (token.equals(\"" + nameValue
					+ "\")) {");
			sourceWriter.println("    viewConfigInstance = new ViewConfig(\""
					+ nameValue + "\", new " + classValue + "());");

			sourceWriter.println("  }");

			i++;
			viewTokenArrVals += "\"" + nameValue + "\"";
			if (i != views.size())
				viewTokenArrVals += ",";
		}

		sourceWriter
				.println("  viewConfigInstances.put(token,viewConfigInstance);");
		sourceWriter.println("  return viewConfigInstance; ");
		sourceWriter.println("}");

		sourceWriter.println("static {");
		sourceWriter.println(" viewTokens = new String[]{" + viewTokenArrVals
				+ "};");
		sourceWriter.println("}");

	}

	/**
	 * <p>
	 * Generate source code for the default constructor. Create default
	 * constructor, call super() Instantiates views and viewConfigInstances
	 * </p>
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 */
	private void generateConstructor(final SourceWriter sourceWriter) {

		// start constructor source generation
		sourceWriter.println("public " + generatedImplClassName + "() { ");
		sourceWriter.indent();
		sourceWriter.println("super();");
		sourceWriter
				.println("viewConfigInstances = new HashMap<String, ViewConfig>();");

		// end constructor source generation
		sourceWriter.outdent();
		sourceWriter.println("}");

	}

	private void generateMethod_ToString(final SourceWriter sourceWriter) {
		// start constructor source generation
		sourceWriter.println("public String toString () { ");
		sourceWriter.indent();
		sourceWriter.print("return \"hi there\";");
		// end constructor source generation
		sourceWriter.outdent();
		sourceWriter.println("}");
	}

	/**
	 * <p>
	 * Generates code for lazyCreateOrGetViewConfig(String token) and
	 * getViewTokens() implementations using an xml file
	 * </p>
	 * 
	 * @param writer
	 */
	private ConfigXMLHolder retrieveViewConfigXML(final TreeLogger logger) {
		final String path = ROOT_CONF_PATH
				+ this.viewAccessorInterfaceClassType.getSimpleSourceName()
				+ ".xml";

		try {

			logger.log(TreeLogger.INFO, "== module config file: " + path);

			final ConfigXMLHolder configVeiewHolder = ViewConfigXMLParser
					.parseProcessConfig(path);

			// debug
			// System.out.println("\n");
			final Map views = configVeiewHolder.getViewMap();
			for (Iterator<Entry<String, ConfigXMLView>> item = views.entrySet()
					.iterator(); item.hasNext();) {
				Entry<String, ConfigXMLView> theEntry = item.next();				
				logger.log(TreeLogger.INFO, "-view:" + theEntry.getValue().toString());
			}
			
			final Map forms = configVeiewHolder.getFormMap();
			if (forms != null) {
				for (Iterator<Entry<String, ConfigXMLForm>> item = forms
						.entrySet().iterator(); item.hasNext();) {
					Entry<String, ConfigXMLForm> theEntry = item.next();
					logger.log(TreeLogger.INFO, "-form:" + theEntry.getValue().toString());
				}
			}
			
			final Map values = configVeiewHolder.getValueMap();
			if (values != null) {
				for (Iterator<Entry<String, ConfigXMLValue>> item = values
						.entrySet().iterator(); item.hasNext();) {
					Entry<String, ConfigXMLValue> theEntry = item.next();
					logger.log(TreeLogger.INFO, "-value:" + theEntry.getValue().toString());
				}
			}

			return configVeiewHolder;
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR,
					"=== could not find config file under directory: " + path);
		} catch (Exception e) {
			logger
					.log(TreeLogger.ERROR,
							"An error occured in xml parser usage");
		}
		return null;
	}
}
