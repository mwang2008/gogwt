package com.gogwt.apps.tracking.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * read [0:1] 0 -- new,  1 -- read 
 * type [1:2] 1 -- receive(inbox), 2 -- send  

 * @author michael.wang
 *
 */
public class GSmsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String address;
	public Date date;
	public String read;
	public String type;
	public String body;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	 
	public String toString() {
		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GLocation.class.getSimpleName());
		sbuf.append("[");
		sbuf.append("address="+address);
		sbuf.append(", date="+date);
		 
		sbuf.append(", read="+read);
		sbuf.append(", type="+type);
		sbuf.append(", body="+body);
 		sbuf.append("]");
		
		return sbuf.toString();
	}
}

