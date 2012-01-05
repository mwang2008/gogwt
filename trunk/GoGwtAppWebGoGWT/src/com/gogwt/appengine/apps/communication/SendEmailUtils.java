package com.gogwt.appengine.apps.communication;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Email function. 
 * test: http://gogwtmail.appspot.com/notifyemail
 * @author Michael.Wang
 *
 */
public class SendEmailUtils {
	public static boolean sendemail(final String from, final List<String> to,
			final String subject, final String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			for (String eachTo : to) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						eachTo));
			}
			msg.setSubject(subject);
			msg.setText(body);
			Transport.send(msg);

			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean gogwtNotificationEmail(final List<String> to,
			final String subject, final String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		//String from = "no.reply.gogwt@gmail.com";
		//String from = "allhotelmotel@gmail.com";
		String from = "contact.gogwt@gmail.com";
		String fromText = "GoGWT";
		try {
			String htmlBody = body;
			
			Multipart mp = new MimeMultipart();

	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(htmlBody, "text/html");
	        mp.addBodyPart(htmlPart);

			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from, fromText));
			//msg.setFrom(new InternetAddress(from));
			for (String eachTo : to) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(eachTo));
			}
			
			msg.setSubject(subject);
			//msg.setText(body);
			msg.setContent(mp);
			
			Transport.send(msg);

			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	public static boolean gogwtNotificationEmail(final List<String> to,
			final String subject, final String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		//String from = "no.reply.gogwt@gmail.com";
		//String from = "allhotelmotel@gmail.com";
		String from = "contact.gogwt@gmail.com";
		String fromText = "GoGWT";
		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from, fromText));
			//msg.setFrom(new InternetAddress(from));
			for (String eachTo : to) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(eachTo));
			}
			
			msg.setSubject(subject);
			msg.setText(body);
			Transport.send(msg);

			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
	
}
