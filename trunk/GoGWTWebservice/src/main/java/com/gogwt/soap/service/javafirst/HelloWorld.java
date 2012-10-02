package com.gogwt.soap.service.javafirst;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
	String sayHi(String text);
}
