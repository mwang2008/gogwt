package com.gogwt.apps.tracking.data;

import com.gogwt.apps.tracking.data.GPXPoint;

interface ICollectionListener {
	void handleLocationUpdated();
	void updateLocation(inout GPXPoint  point);
}