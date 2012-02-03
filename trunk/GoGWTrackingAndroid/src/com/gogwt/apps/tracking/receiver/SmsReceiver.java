package com.gogwt.apps.tracking.receiver;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.gogwt.apps.tracking.GoGWTConstants;
import com.gogwt.apps.tracking.activities.AdminInvokeActivity;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.processor.SmsProcessor;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.StringUtils;

import static com.gogwt.apps.tracking.GoGWTConstants.*;

public class SmsReceiver extends BroadcastReceiver {
	protected static final String TAG = SmsReceiver.class.getSimpleName();

	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
			    
		if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED)) {
			//this stops notifications to others
			this.abortBroadcast();
			
			Bundle extras = intent.getExtras();
			Object[] pdus = (Object[]) extras.get("pdus");
			
			GSmsData smsData = null;
			ArrayList<GSmsData> smsDataList = new ArrayList<GSmsData>();
			final SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				String from = messages[i].getOriginatingAddress();
				String body = messages[i].getMessageBody();
				smsData = new GSmsData();
				smsData.setAddress(from);
				smsData.setBody(body);
				
				smsDataList.add(smsData);				
			}

			if (messages.length > -1) {
				GwtLog.i(TAG,
						"^^^^^^^=== SmsReceiver Message recieved: from="
								+ messages[0].getOriginatingAddress()
								+ ", body=" + messages[0].getMessageBody());
			}
			
			boolean fromGoGWT = false;
			if (smsDataList != null && !smsDataList.isEmpty()) {
				for (GSmsData theGSmsData : smsDataList) {				 
					if (isCommandFromSMS(theGSmsData)) {
						fromGoGWT = true;
						GwtLog.d(TAG, "isCommandFromSMS");
						new SmsProcessor().handleMsg(theGSmsData, context, false);
					}
					else if (isCommandFromAdmin(theGSmsData)) {
						fromGoGWT = true;
						GwtLog.d(TAG, "isCommandFromAdmin");
						new SmsProcessor().handleMsg(theGSmsData, context, true);
					}
					else if (isReturnedSMSFromAdmin(theGSmsData)) {
						fromGoGWT = true;
						GwtLog.d(TAG, "isReturnedSMSFromAdmin");
						Intent adminInvokeIntent = new Intent(AdminInvokeActivity.ACTION_NAME); 
						adminInvokeIntent.putExtra(AdminInvokeActivity.ACTION_NAME+"_SMS_DATA", theGSmsData);
						context.sendBroadcast(adminInvokeIntent);
					}
				}
			}
			
			if(fromGoGWT){
	             //and no alert notification and sms not in inbox
				GwtLog.d(TAG, "^^^^^^^=== GoGWT filter");
				
	        }
	        else{	        	
	            //continue the normal process of sms and will get alert and reaches inbox
	            this.clearAbortBroadcast();
	        }
		}

	}
	
	/**
	 * The SMS message is the response from the command such as *location# *starttracking# etc.
	 * Check whether the returned sms is originally started by AdminInvokeActivity
	 *  
	 * @param smsData
	 * @return
	 */
	private boolean isReturnedSMSFromAdmin(final GSmsData smsData) {
		if (smsData == null || !StringUtils.isSet(smsData.body)) {
			return false;
		}
		
		String body = smsData.body.trim();
		if (body.startsWith(ADMIN)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * The command sent from group owner with Admin function
	 * @param smsData
	 * @return
	 */
	private boolean isCommandFromAdmin(final GSmsData smsData) {
		if (smsData == null || !StringUtils.isSet(smsData.body)) {
			return false;
		}
		String body = smsData.body.trim();
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACK)) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.LOCATION)) {
		    return true;	
		}
		
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.STOP_TRACK)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * The command sent from group owner with SMS function.
	 * @param smsData
	 * @return
	 */
	private boolean isCommandFromSMS(final GSmsData smsData) {
		
		if (smsData == null || !StringUtils.isSet(smsData.body)) {
			return false;
		}
		
		String body = smsData.body.trim();
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACK1) || 
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACK2) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACK3) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACK4)	) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACKING1) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACKING2) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACKING3) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.START_TRACKING4)	) {
			return true;
		}
	 
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.LOCATION1) || 
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.LOCATION2)) {
			return true;
		}
		
		if (StringUtils.equalsIgnoreCase(body, GoGWTConstants.STOP_TRACK1) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.STOP_TRACK2) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.STOP_TRACK3) ||
			StringUtils.equalsIgnoreCase(body, GoGWTConstants.STOP_TRACK4) ) {
			return true;
		}
		
		
		
		return false;
	}

}
