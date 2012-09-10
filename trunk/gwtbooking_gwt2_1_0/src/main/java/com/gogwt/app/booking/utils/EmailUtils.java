package com.gogwt.app.booking.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
public class EmailUtils {
	private static EmailUtils instance;
	
	public void sendEmail(final String to, 
			final String subject, final String body) {
		List<String> tos = new ArrayList<String>();
		tos.add(to);
		sendEmail(tos, "contact@gogwt.com", subject, body);

	}
	
	public void sendEmail(final String to, final String from,
			final String subject, final String body) {
		List<String> tos = new ArrayList<String>();
		tos.add(to);
		sendEmail(tos, from, subject, body);

	}

	public void sendEmail(final List<String> tos, final String from,
			final String subject, final String body) {
		Session mailSession = createSmtpSession();
	 
		
		try {
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject(subject);
			message.setFrom(new InternetAddress(from));
			message.setContent(body, "text/html");
	
			for (String to : tos) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}

			transport.connect();
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (MessagingException e) {
			System.err.println("Cannot Send email");
			e.printStackTrace();
		}

	}

	private Session createSmtpSession() {
		final Properties props = new Properties();
		props.setProperty("mail.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.port", "" + 587);
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		//props.setProperty("mail.debug", "false");

		return Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("contact@gogwt.com",
								"freedog123");
					}
				});
	}

	public static EmailUtils getInstance() {
		if (instance == null) {
			instance = new EmailUtils();
		}
		return instance;
	}
}
