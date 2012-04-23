package com.gogwt.apps.email;

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

import org.apache.log4j.Logger;

/**
 * 
 * Utilize Google App Email server smtp.gmail.com to send email. 
 * Required account: contact@gogwt.com/freedog123
 * 
 * 
 * @author michael.wang
 *
 */
public class SendEmailWithGmailHost {
	private static Logger logger = Logger.getLogger(SendEmailWithGmailHost.class);
	
	private static SendEmailWithGmailHost instance;
	
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
		//mailSession.setDebug(false);
		 
		//if (logger.isDebugEnabled()) {
		//   mailSession.setDebug(false);
		//}
		
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

	public static SendEmailWithGmailHost getInstance() {
		if (instance == null) {
			instance = new SendEmailWithGmailHost();
		}
		return instance;
	}
}
