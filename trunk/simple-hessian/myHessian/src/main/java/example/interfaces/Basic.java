package example.interfaces;

import example.dto.MyInputRequest;
import example.dto.MyOutputResponse;

public interface Basic {
	public String hello();	
	public MyOutputResponse hiClient(MyInputRequest input);
}
