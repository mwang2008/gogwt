package com.gogwt.apps.tracking.data;

import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.ICollectionListener;

interface IRemoteInterface {

    Location getLastLocation();
    GPXPoint getGPXPoint();
    
    void addListener(ICollectionListener listener);
    void removeListener(ICollectionListener listener);
}
