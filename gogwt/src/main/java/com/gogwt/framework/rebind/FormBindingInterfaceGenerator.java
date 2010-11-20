package com.gogwt.framework.rebind;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gogwt.framework.arch.utils.GWTParamValueUtils;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.FormBindingManager;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;


/**
 * <code><B>FormBindingInterfaceGenerator<code><B>
 * <p>
 * Generate an implementation for <code>FormBindingManager</code>
 * in order to set/get value from widgets and formBean
 * 
 * 
 * Requirements for proper code generation:
 *  
 *  example of invocation of generating code:
 *  public class HomeViewImpl extends AbstractFormComposite<FormBean> implements HomeView<FormBean>
 *  
 *  
 *  Example of generated code:
 *  <pre>
    import com.gogwt.framework.arch.utils.GWTParamValueUtils;

    public class HomeViewImplFormBinding implements com.gogwt.framework.arch.widgets.FormBindingManager<com.gogwt.demo.gwt.mvp.client.common.FormBean> {
       public com.gogwt.demo.gwt.mvp.client.common.FormBean toValue(final com.gogwt.framework.arch.widgets.FormBindingManager view) {
          com.gogwt.demo.gwt.mvp.client.common.FormBean formBean = new com.gogwt.demo.gwt.mvp.client.common.FormBean();
          return  formBean;   
       }
    public void fromValue(final com.gogwt.demo.gwt.mvp.client.common.FormBean formBean, com.gogwt.framework.arch.widgets.FormBindingManager view) {
       com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeViewImpl viewImpl = (com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeViewImpl) view; 
       GWTParamValueUtils.setValue(viewImpl.detail, formBean.getDetail());
    }    
 *  </pre>
 * 
 * Throws <code>UnableToCompleteException</code> if for any reason
 * it cannot provide a substitute class
 * 
 * </p>
 * @author contact.gogwt@gmail.com
 *
 */
public class FormBindingInterfaceGenerator extends Generator {

	/** Simple name of class to be generated */
	private transient String generatedImplClassName = null;

	/** Package name of class to be generated */
	private transient String packageName = null;

	private transient JClassType instanceClassType = null;

	private transient String superClassName = null;
	private transient String genericClassName = null;
	private static Map<String, String> supportedWidgetTypesWithSetValueMethodMap = new HashMap<String, String>();
	private static Map<String, String> supportedWidgetTypesWithGetValueMethodMap = new HashMap<String, String>();
	
	private static List<String> supportedTypeList = Arrays.asList(new String[] {
			String.class.getCanonicalName(), List.class.getCanonicalName() });

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		long startTime = System.currentTimeMillis();

		final TypeOracle typeOracle = context.getTypeOracle();

		try {
			populateSupportedValueTypeMap(logger);

			// get classType and save instance variables
			instanceClassType = typeOracle.getType(typeName);
			packageName = instanceClassType.getPackage().getName();
			generatedImplClassName = instanceClassType.getSimpleSourceName()
					+ "FormBinding";

			JClassType superClass = instanceClassType.getSuperclass();

			superClassName = superClass.getQualifiedSourceName();

			JParameterizedType jParamType = (JParameterizedType) superClass;

			if (jParamType != null) {
				JClassType[] argsTypes = jParamType.getTypeArgs();
				if (argsTypes != null && argsTypes.length > 0) {
					genericClassName = argsTypes[0].getQualifiedSourceName();
				}
			}

			generateClass(logger, context);

		} catch (Exception e) {
			e.printStackTrace();

			// record to logger that Map generation threw an exception
			logger.log(TreeLogger.ERROR,
					"Code Generation Error: FormBindingInterfaceGenerator", e);
		}

		long duration = System.currentTimeMillis() - startTime;
		String fileName = packageName + "." + generatedImplClassName;
		if (duration > 0) {
			logger.log(TreeLogger.INFO, "Generate " + fileName + ", using "
					+ duration + "ms");
		}

		// return the fully qualifed name of the class generated
		return fileName;
	}

	private void generateClass(final TreeLogger logger,
			final GeneratorContext context) throws Exception {

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
 		composer.addImport(GWTParamValueUtils.class.getCanonicalName());

		// implements interface
		composer.addImplementedInterface(FormBindingManager.class
				.getCanonicalName() + "<" + genericClassName + ">");

		// source code generator
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);

		// generator method source code
		generateMethods(logger, sourceWriter);

		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");

		// commit generated class
		context.commit(logger, printWriter);
	}

	/**
	 * 
	 * public com.gogwt.demo.gwt.mvp.client.common.FormBean toValue(final com.gogwt.framework.arch.widgets.FormBindingManager view)
	 * 
	 * @param logger
	 * @param writer
	 * @throws Exception
	 */
	private void generateToValueMethod(final TreeLogger logger,
			final SourceWriter writer) throws Exception {
		writer.println("public " + genericClassName + " toValue(final " + FormBindingManager.class.getCanonicalName() + " view) {");
		writer.println("   " + genericClassName + " formBean = new "
				+ genericClassName + "();");
		writer.println( "   String userInput = null;");

		JClassType fieldType;
		JField[] instanceFields = instanceClassType.getFields();
		
		boolean hasViewInstance = false;
		
		for (int i = 0; i < instanceFields.length; i++) {
			fieldType = (JClassType) instanceFields[i].getType();

			//todo: revisit
			//UiField uiField = instanceFields[i].getAnnotation(UiField.class);
			//if (uiField != null) {
 				String widgetClassType = fieldType.getQualifiedSourceName();
				if ( supportedWidgetTypesWithGetValueMethodMap.containsKey( widgetClassType ) ) {
					if (!hasViewInstance) { 
					   writer.println( "   " + this.instanceClassType.getQualifiedSourceName() + " viewImpl = ("
						      + this.instanceClassType.getQualifiedSourceName() + ") view; " );
					   hasViewInstance = true;
					}
					
					writer.println( "   userInput = GWTParamValueUtils.getValue(viewImpl." + instanceFields[i].getName() + ");");
					writer.println( "   formBean.set" +StringUtils.firstLetterToUpper(instanceFields[i].getName()) + "(userInput);");
				}				 
			//}
		}

		writer.println("   return  formBean;   ");
		writer.println("}");
	}

	private void generateFromValueMethod(final TreeLogger logger,
			final SourceWriter writer) throws Exception {

		writer.println("public void fromValue(final " + genericClassName
				+ " formBean, " + FormBindingManager.class.getCanonicalName() + " view) {");
		writer.println( "   String userInput = null;");
		
		boolean hasViewInstance = false;
		JClassType fieldType;
		JField[] instanceFields = instanceClassType.getFields();
		for (int i = 0; i < instanceFields.length; i++) {
			fieldType = (JClassType) instanceFields[i].getType();
			
			//UiField uiField = instanceFields[i].getAnnotation(UiField.class);
			//if (uiField != null) {
				String widgetClassType = fieldType.getQualifiedSourceName();
				if ( supportedWidgetTypesWithSetValueMethodMap.containsKey( widgetClassType ) ) {
					if (!hasViewInstance) { 
					 writer.println( "   " + this.instanceClassType.getQualifiedSourceName() + " viewImpl = ("
						      + this.instanceClassType.getQualifiedSourceName() + ") view; " );
					 hasViewInstance = true;
					}
 					writer.println( "   GWTParamValueUtils.setValue(viewImpl." +  instanceFields[i].getName() + ", formBean.get" + StringUtils.firstLetterToUpper(instanceFields[i].getName()) + "());");
				}
			//}
		}
		writer.println("}");
	}

	private void generateMethods(final TreeLogger logger,
			final SourceWriter writer) throws Exception {

		// public T toValue();
		generateToValueMethod(logger, writer);

		// public void fromValue(T value);
		generateFromValueMethod(logger, writer);

	}
 
	/**
	 * <p>
	 * Get map of classes that can be used in the parameters for
	 * GWTParamValueUtils.setValue(a0,a1) method
	 * </p>
	 * 
	 * @throws Exception
	 */
	private static void populateSupportedValueTypeMap(final TreeLogger logger)
			throws Exception {
		Method[] methods = GWTParamValueUtils.class.getMethods();
		for (final Method method : methods) {
			if (method.getName().equals("setValue")) {
				Class<?>[] types = method.getParameterTypes();
				if (types.length == 2) {
					if (supportedTypeList.contains(types[1].getCanonicalName()))
						supportedWidgetTypesWithSetValueMethodMap.put(
								types[0].getCanonicalName(),
								types[1].getCanonicalName());
					else {
						logger.log(
								TreeLogger.ERROR,
								"Error: 2nd parameter for GWTParamValueUtils.setValue() must be one of the following classes"
										+ supportedTypeList.toString()
										+ " (NOT "
										+ types[1].getCanonicalName() + ")");
						return;
					}
				} else {
					logger.log(TreeLogger.ERROR,
							"Error: GWTParamValueUtils.setValue() must contain exactly 2 parameters");
					return;
				}
			}
			
			//supportedWidgetTypesWithGetValueMethodMap
			if (method.getName().equals("getValue")) {
				Class<?>[] types = method.getParameterTypes();
			    if (types.length == 1) {			    	
			    	supportedWidgetTypesWithGetValueMethodMap.put(
								types[0].getCanonicalName(),
								types[0].getCanonicalName());			    				     
			    }
			    else {
			    	logger.log(TreeLogger.ERROR,
					"Error: GWTParamValueUtils.getValue() must contain exactly 1 parameters");
			    	return;
			    }
			}
		}
		
		
	}

}
