package com.gogwt.apps.tracking.activities;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

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
	private Path mLastPath;
	private final BlockingQueue<GPXPoint> mPendingPoints;

	public DrawPolylineOverlay() {
		mGpxPointList = new ArrayList<GPXPoint>(1024);

		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(3);

		mPendingPoints = new ArrayBlockingQueue<GPXPoint>(10000, true);
	}

	public void addNewGPXPoint(GPXPoint newGPXPoint) {
		mNewGPXPoint = newGPXPoint;
		mPendingPoints.offer(mNewGPXPoint);
		// mGpxPointList.add(newGPXPoint);

	}

	private GeoPoint mLastReferencePoint;
	private Rect mLastViewRect;

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		/*
		 * if (mGpxPointList == null || mGpxPointList.isEmpty()) { return; }
		 * 
		 * if (mGpxPointList.size() == 1) { // only one point return; }
		 */

		Path path = null;
		Projection projection = getMapProjection(mapView);
		Rect viewRect = getMapViewRect(mapView);
		synchronized (mGpxPointList) {
			final GeoPoint referencePoint = projection.fromPixels(0, 0);
			int newPoints = mPendingPoints.drainTo(mGpxPointList);

			boolean newProjection = !viewRect.equals(mLastViewRect) || !referencePoint.equals(mLastReferencePoint);

			if (newPoints == 0 && mLastPath != null && !newProjection) {
				path = mLastPath;
			} else {
				int numPoints = mGpxPointList.size();

				if (numPoints < 2) {
					path = null;
				} else if (mLastPath != null && !newProjection) {
					// using existing path
					path = mLastPath;
					path = new Path();
					path.incReserve(numPoints);
					//updatePath(projection, viewRect, path, numPoints- newPoints);
					updatePath(projection, viewRect, path, 0);
				} else {
					// new path
					path = new Path();
					path.incReserve(numPoints);
					updatePath(projection, viewRect, path, 0);
				}
				mLastPath = path;
			}
			mLastReferencePoint = referencePoint;
			mLastViewRect = viewRect;
		}

		if (path != null) {
			canvas.drawPath(path, mPaint);
		}
 
	}

	private void updatePath(Projection projection, Rect viewRect, Path path,
			int startLocationIdx) {

		// Whether to start a new segment on new valid and visible point.
		// boolean newSegment = startLocationIdx > 0 ?
		// !points.get(startLocationIdx - 1).valid : true;
		// boolean lastVisible = !newSegment;
		
		boolean newSegment = true;
		/*
		if (startLocationIdx > 0) {
			newSegment = false;
		}
		*/
		
		boolean lastVisible = false;
		final Point pt = new Point();
		
		// Loop over track points.
		for (int i = startLocationIdx; i < mGpxPointList.size(); ++i) {
			GPXPoint loc = mGpxPointList.get(i);

			final GeoPoint geoPoint = new GeoPoint(loc.latitude, loc.longitude);

			// Check if the location is visible.
			boolean visible = viewRect.contains(geoPoint.getLongitudeE6(), geoPoint.getLatitudeE6());

			if (!visible && !lastVisible) {
				newSegment = true;
			}
			
			lastVisible = visible;

			// Either move to beginning of a new segment or continue the old
			// one.
			projection.toPixels(geoPoint, pt);
			if (newSegment) {
				path.moveTo(pt.x, pt.y);
				newSegment = false;
			} else {
				path.lineTo(pt.x, pt.y);
			}
		}
	}

	// Visible for testing.
	Rect getMapViewRect(MapView mapView) {
		int w = mapView.getLongitudeSpan();
		int h = mapView.getLatitudeSpan();
		int cx = mapView.getMapCenter().getLongitudeE6();
		int cy = mapView.getMapCenter().getLatitudeE6();
		return new Rect(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2);
	}

	public void draw_good(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		synchronized (mGpxPointList) {

		}
		if (mGpxPointList == null || mGpxPointList.isEmpty()) {
			return;
		}

		if (mGpxPointList.size() == 1) {
			// only one point
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

		mLastPath = path;
	}

	Projection getMapProjection(MapView mapView) {
		return mapView.getProjection();
	}

}
