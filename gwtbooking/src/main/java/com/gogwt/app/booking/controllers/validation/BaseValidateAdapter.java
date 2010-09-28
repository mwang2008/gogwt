package com.gogwt.app.booking.controllers.validation;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gogwt.app.booking.utils.BeanLookupService;

public abstract class BaseValidateAdapter implements Validator {

	//private String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+[A-Za-z0-9-]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	private String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	 /**
	   * <p>
	   * Validate required Field
	   * </p>
	   * @param errors
	   *          errors object
	   * @param fieldName
	   *          input field name
	   * @param fieldValue
	   *          input field value
	   */
	  protected void validateRequiredField( final Errors errors, final String fieldName,
	    final String fieldValue, final String labelName )
	  {
	    final String labelValue = getLabelValue( labelName );
	    String[] args = new String[1];
	    args[0] = labelValue;
	    ValidationUtils.rejectIfEmptyOrWhitespace(
	      errors, fieldName, "input.field.required", args, labelValue + " is required." );
	  }
	  
	  /**
	   * <p>
	   * Get Label Value based on lable resouce bundle name and current locale
	   * </p>
	   * @param inputFieldAttribute
	   *          the input field attribute
	   * @return the lable value
	   */
	  protected String getLabelValue( final String inputFieldLabelBundleName )
	  {

	    final Locale currentLocale = LocaleContextHolder.getLocale();

	    String labelValue = inputFieldLabelBundleName;
	    try {
	      labelValue = BeanLookupService.getAppContext().getMessage(
	        inputFieldLabelBundleName, null, currentLocale );
	    } catch( Throwable e ) {
	      // do not stop because of any exception
	      // use bundle name as label value
	    }

	    return labelValue;
	  }

	  /**
		 * validate email address should have @ in it
		 * 
		 * @param errors
		 * @param fieldValue
		 */
		protected boolean isValidEmailFormat(final String fieldValue) {
	 		if (fieldValue != null && !(fieldValue.matches(EMAIL_REGEX))) {
				return false;
			}
			return true;
		} 
}
