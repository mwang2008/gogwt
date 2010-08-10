package com.gogwt.app.booking.config.resolver;

import static com.gogwt.app.booking.BookingConstants.ENV;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;

public class BookLocaleResolver extends AbstractLocaleResolver {
	private static Logger logger = Logger.getLogger(BookLocaleResolver.class);

	/**
	 * Resolves the locale. This method assumes the language and country ids
	 * have been set into the request as attributes.
	 * 
	 * @see com.ihg.dec.apps.dotcom.config.DotcomHandlerInterceptor
	 * @see org.springframework.web.servlet.LocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)
	 */
	public Locale resolveLocale(final HttpServletRequest request) {

		Locale locale = null;

		final UrlMappingElem mappingElem = (UrlMappingElem) request
				.getAttribute(ENV);
		if (mappingElem != null) {
			final String language = mappingElem.getLanguageId();
			final String country = mappingElem.getCountryId();

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

		if (logger.isDebugEnabled()) {
			logger.debug("IHGLocaleResolver.determineDefaultLocale");
		}

		Locale defaultLocale = getDefaultLocale();
		if (defaultLocale == null) {
			defaultLocale = request.getLocale();
		}
		return defaultLocale;
	}

	/**
	 * @see org.springframework.web.servlet.LocaleResolver#setLocale(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.util.Locale)
	 */
	public void setLocale(final HttpServletRequest request,
			final HttpServletResponse response, final Locale locale) {
		LocaleContextHolder.setLocale(locale);
	}

}
