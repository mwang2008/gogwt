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

import org.apache.log4j.Logger;

import com.gogwt.app.booking.dto.dataObjects.common.EmailConfig;
import com.gogwt.app.booking.pipeline.reservation.SendCreatedEmailProcessor;

 
public class EmailUtils {
	private static EmailUtils instance;
	private Session mailSession;
	private static EmailConfig emailConfig;
	
	private static Logger logger = Logger.getLogger(EmailUtils.class);
	
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
		if (mailSession == null) {
		    mailSession = createSmtpSession();
		}
	 
		
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
						return new PasswordAuthentication(emailConfig.getUsername(),
								emailConfig.getPassword());
					}
				});
	}

	public static EmailUtils getInstance(EmailConfig emailConfig) {
		if (instance == null) {
			
			instance = new EmailUtils();
			EmailUtils.emailConfig = emailConfig;
			logger.info("-- email config usernmae=" + emailConfig.getUsername() + ", password="+emailConfig.getPassword());
		}
		return instance;
	}
}
