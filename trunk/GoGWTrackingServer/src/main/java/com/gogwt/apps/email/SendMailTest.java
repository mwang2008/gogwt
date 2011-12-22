package com.gogwt.apps.email;

public class SendMailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String from = "contact.gogwt@gmail.com";
		String to = "allhotelmotel@gmail.com";
		String subject = "Test";
		String message = "A test message";
		
		SendMail sendMail = new SendMail(from, to, subject, message);
		sendMail.send();

	}

}
