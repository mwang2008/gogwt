package com.gogwt.apps.tracking.c2md;

import java.io.IOException;

import com.gogwt.apps.tracking.utils.ToStringUtils;

public class SendMessageToDeviceTest {
	public static final String REGISTRATION_ID = 
		"APA91bEVoZuCYN7xmNdBodHtOIM7o-5yX96geQDAhSWzqVbRceEmA2Hj8LSF53Ltueh3cpvLmmqrZ3yh7nQ33dmi1bnLAo5MHV4h3FOZ6xj9zHDJFbTygQh-LxkjLPZY4lvpzBhr5ctUmChf7C3krgL9ykMkJEXFOg";

	public static void main(String[] args) throws IOException {
		// "Message to your device." is the message we will send to the Android app
		String token = AuthenticationUtil.getToken(SecureStorage.USER,
				SecureStorage.PASSWORD);
		
		C2DMResponse responseCode = MessageUtil.sendMessage(token, REGISTRATION_ID, "1234567890","Message to your device.");
		System.out.println(ToStringUtils.toString(responseCode));
	}
}
