package com.gogwt.apps.tracking.data;

 
public class Status {
 
	private int code;
	 
	private String msg;

	public Status() {}
	
	public Status(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Status.class.getSimpleName() + "=[");
		sbuf.append("code=" + code);
		sbuf.append(",msg=" + msg);
	 
		sbuf.append("]");

		return sbuf.toString();

	}
	
}
