package com.gogwt.apps.tracking.c2dm;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;

import com.gogwt.apps.tracking.GwtConfig;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.manager.SendMessageIF;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;


/**
 * Responsible for sending message to app server, app server will redirec to Google C2DM server. 
 * The Google C2DM server will route to specific device.
 * 
 * @author Michael.Wang
 *
 */
public class C2DMMessageSender {
	protected static int SOCKET_TIMEOUT = 5000;
	
   //private static C2DMMessageSender instance;
   private SendMessageIF sendMessage;
   
   /*
   private C2DMMessageSender() {};
   
   public static C2DMMessageSender getInstance() {
	   if (instance == null) {
		   instance = new C2DMMessageSender(); 
	   }
	   return instance;
   }
   */
   
   public void sendMessageToServer(SendMessageIF sendMessage, Context context, String toPhone, String msg) {
	   this.sendMessage = sendMessage;  
	   Profile profile = SessionManager.getProfile(context);
	   sendMessageToServer(profile.getGroupId(), profile.getDisplayName(), profile.getPhoneNumber(), toPhone, msg);	    
   }
   
   /**
    * Process the result from app server, call back MessageSendingManager.c2dmResponse
    * @param returnCode
    */
   public void processResult(String returnCode) {
	   sendMessage.handleC2dmResponse(returnCode);   
   }
   
   /**
    * Send message to app server
    * @param from
    * @param to
    * @param msg
    */
   public void sendMessageToServer(String groupId, String displayName, String fromPhone, String toPhone, String msg) {
	   C2DMMessageBean messageBean = new C2DMMessageBean();
	   messageBean.setGroupId(groupId);
	   messageBean.setDisplayName(displayName);
	   messageBean.setFrom(fromPhone);
	   messageBean.setTo(toPhone);
	   messageBean.setMsg(msg);
	   
	   new SendMessageToServerTask().execute(messageBean);
   }
   
   private class SendMessageToServerTask extends AsyncTask<C2DMMessageBean, Void, String> {

		@Override
		protected String doInBackground(C2DMMessageBean... registerBeanArray) {
			C2DMMessageBean messageBean = registerBeanArray[0];
			try {
				return executeSendMessage(messageBean);
			} catch (Throwable e) {
				// call one more time.
				try {
					return executeSendMessage(messageBean);
				} catch (Throwable et) {
					GwtLog.d("C2DM", "Registion error with tracking/en-us/c2dmreg");
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				   processResult(result);
				}
				catch (Throwable e) {
					GwtLog.d("C2DM", "result="+result); 
				}
		}
		
		private String executeSendMessage(C2DMMessageBean messageBean) throws Throwable {
			// stop with different thread

			final String url = GwtConfig.getHost()+"tracking/en-us/c2dmmsg";

			HttpHeaders requestHeaders = new HttpHeaders();

			MediaType mediaType = MediaType.APPLICATION_JSON;
			requestHeaders.setContentType(mediaType);

			HttpEntity<C2DMMessageBean> requestEntity = new HttpEntity<C2DMMessageBean>(messageBean, requestHeaders);

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(SOCKET_TIMEOUT);

			RestTemplate restTemplate = new RestTemplate(requestFactory);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

			return response.getBody();
		}
	}

}
