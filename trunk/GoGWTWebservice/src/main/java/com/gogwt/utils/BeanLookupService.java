package com.gogwt.utils;

 
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;


public class BeanLookupService extends ApplicationObjectSupport {

	private static Logger logger = Logger.getLogger(BeanLookupService.class);

	private static ApplicationContext mApplicationContext;

	/**
	 * @see org.springframework.context.support.ApplicationObjectSupport#initApplicationContext()
	 */
	protected void initApplicationContext() throws BeansException {
		mApplicationContext = getApplicationContext();
	}

	/**
	 * <p>
	 * Returns the bean specified in the beanName parameter. The bean must be
	 * configured in the application context xml file. If the bean is not found,
	 * null is returned.
	 * </p>
	 * 
	 * @param beanName
	 *            - the name of the bean find in the application context.
	 * @throws NoSuchBeanDefinitionException
	 *             - when the beanName is not found in the application context.
	 * @return the bean specified in the beanName parameter or null if the bean
	 *         is not found.
	 */
	public static Object getBean(final String beanName)
			throws NoSuchBeanDefinitionException {

		if (mApplicationContext == null) {
			logger.info("application context is null");
			return null;
		}

		return mApplicationContext.getBean(beanName);
	}

	/**
	 * 
	 * <p>
	 * Get Application context
	 * </p>
	 * 
	 * @return application context
	 */
	public static ApplicationContext getAppContext() {
		return mApplicationContext;
	}

}
