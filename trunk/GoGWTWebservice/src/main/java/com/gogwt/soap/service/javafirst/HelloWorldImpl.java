package com.gogwt.soap.service.javafirst;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 
 * http://localhost/gwebservice/hello_world?wsdl
 * http://localhost:8080/gservice/ws/hello_world?wsdl
 * @author michael.wang
 *
 */
@WebService(endpointInterface = "com.gogwt.soap.service.javafirst.HelloWorld", serviceName = "HelloWorld")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class HelloWorldImpl implements HelloWorld {

	public String sayHi(String text) {
		System.out.println("sayHi called");
	    return "Hello " + text;
	}

}
