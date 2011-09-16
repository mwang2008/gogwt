package com.gogwt.apps.tracking.activities;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.gogwt.apps.tracking.data.GPXPoint;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

class DrawPolylineOverlay extends Overlay {
	
	GPXPoint mNewGPXPoint;
	GPXPoint mLastGPXPoint;
	
	private ArrayList<GPXPoint> mGpxPointList = null;
    private Paint mPaint;
    private Projection mProjection;
    
	public DrawPolylineOverlay() {
		mGpxPointList = new ArrayList<GPXPoint>();
		
		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(3);
 	 
	}

	public void addNewGPXPoint(GPXPoint newGPXPoint) {
		mNewGPXPoint = newGPXPoint;
		
		mGpxPointList.add(newGPXPoint);
		
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		if (mGpxPointList == null || mGpxPointList.isEmpty()) {
			return;
		}

		if (mGpxPointList.size() == 1) {
			//only one point
			return;
		}
	 	
 		Path path = new Path();
		Point currentPnt = new Point();
		Point lastPnt = new Point();

		GeoPoint currentGeoPoint = null;
		GeoPoint theLastGeoPoint = null;
		
		if (mProjection == null) {
		   mProjection = getMapProjection(mapView);
		}
		
		for (GPXPoint gpxPoint : mGpxPointList) {
			currentGeoPoint = new GeoPoint(gpxPoint.latitude,
					gpxPoint.longitude);
			if (theLastGeoPoint != null) {
				mProjection.toPixels(currentGeoPoint, currentPnt);
				mProjection.toPixels(theLastGeoPoint, lastPnt);

				path.moveTo(lastPnt.x, lastPnt.y);
				path.lineTo(currentPnt.x, currentPnt.y);
			}
			theLastGeoPoint = currentGeoPoint;
		}

		mLastGPXPoint = mNewGPXPoint;
		
		// todo: revisit it later: only draw new line
		/*
		 * mProjection.toPixels(newPoint, currentPnt);
		 * mProjection.toPixels(lastPoint, lastPnt);
		 * 
		 * path.moveTo(lastPnt.x, lastPnt.y); path.lineTo(currentPnt.x,
		 * currentPnt.y);
		 * 
		 * lastPoint = newPoint;
		 */

		canvas.drawPath(path, mPaint);
	}

	 
	Projection getMapProjection(MapView mapView) {
		return mapView.getProjection();
	}

}
