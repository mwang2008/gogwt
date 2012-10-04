package com.gogwt.rest.service.service;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;
import com.gogwt.apps.admin.formbean.WeatherForm;
import com.gogwt.apps.admin.formbean.WeatherResponse;

@Component
public class RestDomainService {
	 
	private String url;

	public void getWeather() {
		System.out.println(" WeatherDomainService url=" + url);
	}

	/**
	 * The step to call WS 
	 * 1. download WSDL to src\main\wsdl\weather.wsdl
	 * 2. defined in pom.xml
	 * 3. mvn clean install to generate java files based on wsdl.
	 * 4. reference wsdl:binding section to find method call 
	 * 
	 * @todo: put JaxWsProxyFactoryBean into Spring config: http://cxf.apache.org/docs/jax-ws-configuration.html
	 * @param weatherForm
	 * @return
	 */
	public WeatherResponse getWeather(WeatherForm weatherForm) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		//http://www.webservicex.net/usweather.asmx?WSDL
		String weatherWSDL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx";
	    factory.setServiceClass(WeatherSoap.class);
		
		factory.setAddress(weatherWSDL);
		WeatherSoap client = (WeatherSoap)factory.create();

		WeatherReturn report = client.getCityWeatherByZIP(weatherForm.getZip());
	    
		WeatherResponse response = new WeatherResponse();
		BeanUtils.copyProperties(report, response);
		
		return response;
		
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}
}
