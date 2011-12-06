package com.gogwt.apps.tracking;

import android.app.Activity;
import android.app.ProgressDialog;

public class AbstractAsyncActivity extends Activity implements AsyncActivity {
	
	protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();
	protected int SOCKET_TIMEOUT = 1000;  // default to 1 seconds
	private ProgressDialog progressDialog;
	
	private boolean destroyed = false;
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public MainApplication getApplicationContext() {
		return (MainApplication) super.getApplicationContext();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroyed = true;
	}
		
	
	//***************************************
    // Public methods
    //***************************************
	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}
	public void showLoadingProgressDialog(String msg) {
		this.showProgressDialog(msg);
	}
	
	public void showProgressDialog(CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}
		
		progressDialog.setMessage(message);
		progressDialog.show();
	}
		
	public void dismissProgressDialog() {
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}

}
