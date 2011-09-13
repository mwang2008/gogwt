package com.gogwt.apps.tracking;

public final class GwtConfig {
   public static int QA = 1;
   public static int PROD = 0;
   
   public static int env = QA;  //qa prod
   public static boolean DEBUG = true; //false
   
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
