package example.service;

import example.dto.MyInputRequest;
import example.dto.MyOutputResponse;
import example.interfaces.Basic;

public class BasicService implements Basic {
	private String _greeting = "My Hello world";

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public String hello() {
		return _greeting;
	}

	public MyOutputResponse hiClient(MyInputRequest input) {
		System.out.println(" from client: " + input.toString()); 
		
		MyOutputResponse response = new MyOutputResponse();
		response.setMsg("HI there from " + input.getFirstName());
		return response;
	}
}
