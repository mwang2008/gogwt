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
	

	public transient String address;
	
	public transient Date date;
	public transient int read;
	public int type;
	public String body;
	public long startTime;
	
	
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
	 
	
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String toString() {
		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GSmsData.class.getSimpleName());
		sbuf.append("[");
		sbuf.append("address="+address);
		sbuf.append(", date="+date);
		 
		sbuf.append(", read="+read);
		sbuf.append(", type="+type);
		sbuf.append(", body="+body);
		sbuf.append(", startTime="+startTime);
		sbuf.append("]");
		
		return sbuf.toString();
	}
	
	public String getContent() {
		return toString();
	}
}

