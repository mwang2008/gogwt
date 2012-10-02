package com.gogwt.soap.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.gogwt.soap.service.javafirst.HelloWorld;

/**
 * http://cxf.apache.org/docs/a-simple-jax-ws-service.html
 * @author michael.wang
 *
 */
public class MyJavaFirstClientService {

	//http://localhost/webservice/hello_world?wsdl
	private static final String HELLO_WORLD_SERVER_URL = "http://localhost:8080/gservice/ws/hello_world";
	
	private void testJavaFirstHello() throws Throwable {
	   System.out.println("==== testJavaFirstHello ");
	   JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	   factory.getInInterceptors().add(new LoggingInInterceptor());
	   factory.getOutInterceptors().add(new LoggingOutInterceptor());
	   factory.setServiceClass(HelloWorld.class);
	   factory.setAddress(HELLO_WORLD_SERVER_URL);
	   HelloWorld client = (HelloWorld) factory.create();

	   String reply = client.sayHi("HI");
	   System.out.println("Server said: " + reply);
	    
	}
	
	//http://www.webservicex.net/usweather.asmx?WSDL
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MyJavaFirstClientService().testJavaFirstHello();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
