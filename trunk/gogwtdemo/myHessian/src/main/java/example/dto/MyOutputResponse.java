package example.dto;

import java.io.Serializable;

public class MyOutputResponse implements Serializable {
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("MyOutputResponse[");
		sbuilder.append(" msg=" + msg);
		 
		sbuilder.append("]");
		
		return sbuilder.toString();
	}	
}
