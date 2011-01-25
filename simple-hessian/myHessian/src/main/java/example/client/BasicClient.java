package example.client;

import com.caucho.hessian.client.HessianProxyFactory;

import example.dto.MyInputRequest;
import example.dto.MyOutputResponse;
import example.interfaces.Basic;

public class BasicClient {
	public static void main(String []args)
    throws Exception
  {
	String url = "http://localhost/hessian_webapp/hello";
	
	HessianProxyFactory factory = new HessianProxyFactory();
    Basic basic = (Basic) factory.create(Basic.class, url);

    //test first API hello
    System.out.println("Hello from server: " + basic.hello());
    
    //test second API hiClient
    MyInputRequest input = new MyInputRequest();
    input.setFirstName("Michael");
    input.setLastName("W");
    MyOutputResponse response = basic.hiClient(input);
    
    System.out.println("hiClient from server: " + response.toString());
  }

}
