package com.gogwt.apps.tracking.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;

import com.gogwt.apps.tracking.services.GPSLocationFinder;
import com.gogwt.apps.tracking.services.GPSLocationIF;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * 
 * @author michael.wang
 *  
 */
public class DrawMarkerOverlay extends ItemizedOverlay<OverlayItem> implements GPSLocationIF<Location> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	protected double lat, lng;
	
	public DrawMarkerOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
 	}
 	
	public DrawMarkerOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	/**
	 *  
	 *  Google: 
	 *  direction: http://maps.google.com/maps?saddr="+lat+","+lng
	 *  map: geo:" + lat +"," + lng
	 *  navigation: google.navigation:q="+lat+","+lng
	 */
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog dialog = new AlertDialog.Builder(mContext).create();
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  
	  dialog.setButton(AlertDialog.BUTTON1, "Direction", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		    	getCurrentLocation();
            } 
	  });
		
	  dialog.setButton(AlertDialog.BUTTON2, "Navigation", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	    	  try {	    		 
	    	     String uri = "google.navigation:q="+lat+","+lng;
	    	     Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	    	     mContext.startActivity(i);
	    	  }
	    	  catch (Throwable e) {
	    		  showInfo("Info", "Google navigation may not be installed or have problem to start"); 
	    	  }	    	 
          } 
      });
	  
	  dialog.show();
	  return true;
	}
	
	private void showInfo(String title, String msg) {
		AlertDialog dialog = new AlertDialog.Builder(mContext).create();
		dialog.setTitle(title);
		dialog.setMessage(msg);
		
		 dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
			    	//close dialog, do nothing
	            } 
		  });
		 dialog.show();
	}

	private void getCurrentLocation() {
		try {
			
		   //todo: not sure to invoke the GPS or not, so disable this function now.
		   //may turn it on later
		   //new GPSLocationFinder(mContext, this).getLocation(true, false);		   
		   try {	    		    
		       String uri = "http://maps.google.com/maps?saddr="+lat+","+lng;  		    	  
		       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));	    			  		    	  
		       mContext.startActivity(intent);
		   } catch (Throwable et) {
		       showInfo("Info", "Google map may not be installed or have problem to start"); 
		   }
		}
		catch (Throwable e) {
		   	try {	    		    
		    	  String uri = "http://maps.google.com/maps?saddr="+lat+","+lng;  		    	  
		    	  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));	    			  		    	  
		    	  mContext.startActivity(intent);
		    } catch (Throwable et) {
		       showInfo("Info", "Google map may not be installed or have problem to start"); 
		    }		
		}
	}
	
	/**
	 * Callback of GPSLocationFinder
	 */
	@Override
	public void findGeocode(Location location) {
    	try {
    		String uri="http://maps.google.com/maps?saddr=";
    		if (location != null) {
    		  double dlat = location.getLatitude();
    		  double dlng = location.getLongitude();
    		  
	    	   uri += lat+","+lng+"&daddr="+dlat + "," + dlng;
    		}
    		else {
    		   uri += lat+","+lng;
    		}
	    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));	    			  		    	  
	    	mContext.startActivity(intent);
	    } catch (Throwable e) {
	       showInfo("Info", "Google map may not be installed or have problem to start"); 	        
	    }
		
	}
}
