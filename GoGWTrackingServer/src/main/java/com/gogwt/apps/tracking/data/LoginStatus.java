package com.gogwt.apps.tracking.data;

public enum LoginStatus {
   ANAYMOUS ("anamous"),
   IMPLICIT ("implicit"),
   EXPLICIT ("explicit");
   
   private String code;
   
   private LoginStatus(String code) {
	   this.code = code;
   }
   
   public String getCode() {
	   return this.code;
   }
}
