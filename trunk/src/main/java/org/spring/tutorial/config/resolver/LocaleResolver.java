package org.spring.tutorial.config.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.spring.tutorial.config.urlmapping.URLMappingElement;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

 
/**
 * <code><B>LocaleResolver</B></code>
 * <p>
 * The locale resolver class. This class will resolve the locale by using
 * the language and country ids passed as request attributes. If the request
 * attributes are not found, the default locale will be used.
 * @author WangM
 *
 */
public class LocaleResolver extends AbstractLocaleResolver {

	/**
	 * Resolves the locale. This method assumes the language and country ids
	 * have been set into the request as attributes.
	 * 
	 * @see org.springframework.web.servlet.LocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)
	 */
	public Locale resolveLocale(final HttpServletRequest request) {

		Locale locale = null;

		URLMappingElement appMappingElem = (URLMappingElement) request
				.getAttribute(org.spring.tutorial.AppConstants.ENV_NAME);

		if (appMappingElem != null) {
			final String language = appMappingElem.getLanguageId();
			final String country = appMappingElem.getCountryId();

			if (language != null && country != null) {
				locale = new Locale(language, country.toUpperCase(request
						.getLocale()));
			}
		}

		if (locale == null) {
			locale = determineDefaultLocale(request);
		}
		LocaleContextHolder.setLocale(locale);
		return locale;
	}

	/**
	 * Determine the default locale for the given request, Called if no locale
	 * has been resolved.
	 * <p>
	 * The default implementation returns the specified default locale, if any,
	 * else look back to the request's accept-header locale.
	 * 
	 * @param request
	 *            the request to resolve the locale for
	 * @return the default locale (never <code>null</code>)
	 * @see #setDefaultLocale
	 * @see javax.servlet.http.HttpServletRequest#getLocale()
	 */
	protected Locale determineDefaultLocale(final HttpServletRequest request) {

		Locale defaultLocale = getDefaultLocale();
		if (defaultLocale == null) {
			defaultLocale = request.getLocale();
		}
		return defaultLocale;
	}

	public void setLocale(final HttpServletRequest request,
			final HttpServletResponse response, final Locale locale) {
		LocaleContextHolder.setLocale(locale);
	}

}
