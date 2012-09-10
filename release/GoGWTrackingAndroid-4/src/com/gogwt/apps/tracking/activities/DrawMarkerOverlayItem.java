package com.gogwt.apps.tracking.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;

/**
 * 
 * @author michael.wang
 * @deprecated
 */
public class DrawMarkerOverlayItem {
	public static OverlayItem buildMarkerOverlyItem(final GeoPoint point, Drawable marker) {		
		OverlayItem overlayitem = new OverlayItem(point, "", "");
		overlayitem.setMarker(marker);
		
		return overlayitem;
	}
	
}
