package com.gogwt.apps.tracking.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gogwt.apps.tracking.utils.GwtLog;

public class DummyService extends Service {
	private static final String TAG = DummyService.class.getSimpleName();
	
	@Override
	public void onCreate() {
		super.onCreate();
		GwtLog.d(TAG, "==== onCreate");		 
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
