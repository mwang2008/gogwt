package com.gogwt.apps.tracking;

public final class GwtConfig {
   public static int QA = 1;
   public static int PROD = 0;
   
   public static int env = QA;  //QA  PROD
   public static boolean DEBUG = true; ///true; //false
   
   public static String getHost() {
   	if (env == GwtConfig.QA) {
   		return "http://10.0.101.244/";    //ipconfig /all  - IPv4
   	}
   	
   	if (env == GwtConfig.PROD) {
   		//return "http://74.244.61.229/";  //gogwt.com
   		return "http://www.gogwt.com/";
   	}
   	
   	return "http://10.0.122.7/";
   }
   
   public static int getMapViewId() {
	   if (env == QA) {
		   return R.id.mapview;
	   }
	   /*
	   if (env == PROD) {
		   return R.id.mapview_prod;
	   }
	   */
	   return R.id.mapview;
   }
}
