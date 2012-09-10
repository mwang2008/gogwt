package com.gogwt.apps.tracking.processor;

import android.app.PendingIntent;
import android.os.AsyncTask;
import android.telephony.SmsManager;

/**
 * Used to send sms
 * Example:
 * <pre>
   PendingIntent pIntent = PendingIntent.getActivity(context, 
    			(int)System.currentTimeMillis(), new Intent(context, SmsReceiver.class), 0);
   SendSmsManager.getInstance().sendTextMessage(number, null, body, pIntent);
   </pre>
 * @author Michael.Wang
 *
 */
public class SendSmsManager {
	private SmsManager smsManager;
	 
	private static SendSmsManager sendSmsManager;
	
	
	public SendSmsManager() {
		smsManager = SmsManager.getDefault();
		
	}

	/**
	 * Default send sms with different thread
	 * @param callerNum
	 * @param senderNum
	 * @param body
	 * @param sentIntent
	 */
    public void sendTextMessage(String callerNum, String senderNum, String body, PendingIntent sentIntent) {
    	sendTextMessage(callerNum, senderNum, body, sentIntent, false);   	
    }
    
    public void sendTextMessage(String callerNum, String senderNum, String body, PendingIntent sentIntent, boolean newThread) {
    	if (newThread == false) {    		
    		smsManager.sendTextMessage(callerNum, null, body, sentIntent, null); 
    		return;
    	}
    	
    	new SendSmsTask().execute(callerNum, senderNum, body, sentIntent);
       	 
    	
     	//smsManager.sendTextMessage(callerNum, null, body, sentIntent, null);   	
    }
    
	public static SendSmsManager getInstance() {
		if (sendSmsManager == null) {			
			sendSmsManager = new SendSmsManager();
		}
		
		return sendSmsManager;
	}
	
	private class SendSmsTask extends AsyncTask<Object, Void, Boolean> {
		 
		
		@Override
		protected Boolean doInBackground(Object... params) {
			String callerNum = (String)params[0];
			String senderNum = (String)params[1];
			String body = (String)params[2];
			PendingIntent sentIntent = (PendingIntent)params[3];
			
			smsManager.sendTextMessage(callerNum, null, body, sentIntent, null);  
			
			return true;
		}
		
	}
}
