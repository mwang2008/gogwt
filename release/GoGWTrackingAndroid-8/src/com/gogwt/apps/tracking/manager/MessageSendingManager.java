package com.gogwt.apps.tracking.manager;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SmsManager;

import com.gogwt.apps.tracking.c2dm.C2DMMessageSender;
import com.gogwt.apps.tracking.c2dm.C2DMUtils;
import com.gogwt.apps.tracking.utils.GwtLog;

public class MessageSendingManager implements SendMessageIF {
	private SmsManager smsManager;
	//private Context context;
	private String destinationPhone;
	//private String sourcePhone;
	private String text;
	private PendingIntent sentIntent;
	//private PendingIntent deliveryIntent;
	
	public void sendTextMessage(SmsManager smsManager, Context context, String phone, String scAddress, String body, PendingIntent pIntent, PendingIntent deliveryIntent) {
		this.smsManager = smsManager;
	 	this.destinationPhone = phone;
	 	this.text = body;
		this.sentIntent = pIntent;
		 
		if (C2DMUtils.hasRegistrationId(context)) {
			//send message to app server bridge in turn Google C2DM server
			sendMessageToServer(context, phone, body);
		}
		else {
			smsManager.sendTextMessage(phone, null, body, pIntent, null);	
		}
	}
	
	private void sendMessageToServer(Context context, String toPhone, String msg) {
		new C2DMMessageSender().sendMessageToServer(this, context, toPhone, msg);
	}

	/**
	 * If fail to send with Google C2DM, use convention SMS to send message.
	 * responseCode  
	 */
	@Override
	public void handleC2dmResponse(String resposeCode) {
	   GwtLog.d("MessageSendingManager", "resposeCode="+resposeCode);
	   
	   smsManager.sendTextMessage(destinationPhone, null, text, sentIntent, null);
 	}
	
	
}
 