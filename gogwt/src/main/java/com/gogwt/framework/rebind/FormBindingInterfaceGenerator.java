package com.gogwt.framework.rebind;

import java.io.PrintWriter;

import com.gogwt.framework.arch.utils.GWTValueUtils;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class FormBindingInterfaceGenerator extends Generator {

	/** Simple name of class to be generated */
	private transient String generatedImplClassName = null;

	/** Package name of class to be generated */
	private transient String packageName = null;

	private transient JClassType instanceClassType = null;

	private transient String superClassName = null;
	private transient String genericClassName = null;

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		long startTime = System.currentTimeMillis();

		final TypeOracle typeOracle = context.getTypeOracle();

		try {
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
			
			System.out.println(" ** superClassName= " + superClassName);
			System.out.println(" ** genericClassName " + genericClassName);
 
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
		composer.addImport(Composite.class.getCanonicalName());
		composer.addImport( GWTValueUtils.class.getCanonicalName() );
		
		 // implements interface
	    composer.addImplementedInterface( FormBindingManager.class.getCanonicalName() + "<" + genericClassName + ">" );
	     
		// source code generator
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);

/*		JClassType fieldType;
		JField[] instanceFields = instanceClassType.getFields();
		for (int i = 0; i < instanceFields.length; i++) {
			fieldType = (JClassType) instanceFields[i].getType();

			String qualifiedPackage = getPackageForType(fieldType);
			// System.out.println(" ---- qualifiedPackage="+qualifiedPackage);

			UiField uiField = instanceFields[i].getAnnotation(UiField.class);
			if (uiField != null) {
	 			System.out.println(" ---- getQualifiedSourceName="
						+ fieldType.getQualifiedSourceName()
						+ " , instanceFields[i].getNam="
						+ instanceFields[i].getName());
	 
			}

		}
*/
		// generator constructor source code
		// generateConstructor(sourceWriter);

		// generator method source code
		generateMethods(logger, sourceWriter );

		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");

		// commit generated class
		context.commit(logger, printWriter);
	}

	private void generateMethods(final TreeLogger logger,
			final SourceWriter writer) throws Exception {
		
		   //public T toValue();
		   writer.println( "public " + genericClassName + "   toValue() {");
		   writer.println( "   "  + genericClassName + " formBean = new " + genericClassName + "();");
		   writer.println( "   return  formBean;   ");
		   writer.println( "}");
		   
		   //public void fromValue(T value);
		   writer.println( "public void fromValue(" + genericClassName + " formBean) {");
		   
		   JClassType fieldType;
		   JField[] instanceFields = instanceClassType.getFields();
		   for (int i = 0; i < instanceFields.length; i++) {
				fieldType = (JClassType) instanceFields[i].getType();

				UiField uiField = instanceFields[i].getAnnotation(UiField.class);
				if (uiField != null) {
					writer.println( "//  formBean.get" + StringUtils.firstLetterCapital(instanceFields[i].getName()) + "();");
					//writer.println("\n");   //newline
					
		 			System.out.println(" ---- getQualifiedSourceName="
							+ fieldType.getQualifiedSourceName()
							+ " , instanceFields[i].getNam="
							+ instanceFields[i].getName());
		 
				}

			}
		   
		   writer.println( "}");
		   

				      
				 
	}
	private static String getPackageForType(JType type) {
		return type.getQualifiedSourceName().replace(
				type.getSimpleSourceName(), "");
	}

}
