package com.gogwt.apps.tracking.services.communication;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.AppConstants;
import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.formbean.ContactUsFormBean;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;

public class NotificationEmail {
	private static Logger logger = Logger
			.getLogger(ProfileBusinessDomainService.class);

	// private static final String emailURL =
	// "http://gogwtengine.appspot.com/notifyemail";
	private static final String emailURL = "http://gogwtmail.appspot.com/notifyemail";
	private String EMAIL_TEMPLATE = "/enrollemail_template.html";
	private String FORGOT_PASSWORD_EMAIL_TEMPLATE = "/forgot_password_template.html";
	private String CUSTOMER_COMMENTS_EMAIL_TEMPLATE = "/customer_comments_template.html";

	public void sendEnrollEmail(final CustomerProfile profile, final String unencrptedPassword) {
		logger.debug("=sendEnrollEmail");

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(emailURL);
		try {
			NameValuePair[] data = {
					new NameValuePair("to", profile.getEmail()),
					new NameValuePair("subject",
							"Welcome to choose gogwt mobile app"),
					new NameValuePair("body", constructBody(profile, unencrptedPassword)) };
			method.setRequestBody(data);

			int statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}
	
	public void sendForgotPasswordEmail(final CustomerProfile profile, String newPassword) {
		logger.debug("=sendForgotPasswordEmail");
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(emailURL);
		try {
			NameValuePair[] data = {
					new NameValuePair("to", profile.getEmail()),
					new NameValuePair("subject",
							"Your password has been reset"),
					new NameValuePair("body", constructForgotPasswordBody(profile, newPassword)) };
			method.setRequestBody(data);

			int statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
	}

	public void sendCustomerComments(ContactUsFormBean formBean) {
		logger.debug("sendCustomerComments");
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(emailURL);
		try {
			NameValuePair[] data = {
					new NameValuePair("to", AppConstants.MAIN_EMAIL_LIST),
					new NameValuePair("subject", formBean.getSubject()),
					new NameValuePair("body", constructCustomerCommentsBody(formBean)) };
			method.setRequestBody(data);

			int statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}
	
	private String constructCustomerCommentsBody(final ContactUsFormBean formBean) {
		InputStream in = this.getClass().getResourceAsStream(CUSTOMER_COMMENTS_EMAIL_TEMPLATE);
		
		try {
			String body = IOUtils.toString(in, "UTF-8");
			body = body.replaceAll("\\$\\{name\\}", formBean.getName());
			body = body.replaceAll("\\$\\{email\\}", formBean.getEmail());
			body = body.replaceAll("\\$\\{phone\\}", formBean.getPhone());
			
			body = body.replaceAll("\\$\\{subject\\}", formBean.getSubject());
			body = body.replaceAll("\\$\\{body\\}", formBean.getComment());
			return body;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
	}
	
	private String constructForgotPasswordBody(final CustomerProfile profile, String newPassword) {
		InputStream in = this.getClass().getResourceAsStream(FORGOT_PASSWORD_EMAIL_TEMPLATE);
		try {
			String body = IOUtils.toString(in, "UTF-8");
			body = body.replaceAll("\\$\\{firstname\\}", profile.getFirstName());
			body = body.replaceAll("\\$\\{lastname\\}", profile.getLastName());
			body = body.replaceAll("\\$\\{password\\}", newPassword);
			
			body = body.replaceAll("\\$\\{userName\\}", profile.getUserName());
			body = body.replaceAll("\\$\\{groupId\\}", profile.getGroupId());
			return body;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private String constructBody(final CustomerProfile profile, final String unencrptedPassword) {
		InputStream in = this.getClass().getResourceAsStream(EMAIL_TEMPLATE);
		try {
			String body = IOUtils.toString(in, "UTF-8");
			body = body.replaceAll("\\$\\{firstname\\}", profile.getFirstName());

			body = body.replaceAll("\\$\\{lastname\\}", profile.getLastName());
			body = body.replaceAll("\\$\\{userName\\}", profile.getUserName());
			body = body.replaceAll("\\$\\{groupId\\}", profile.getGroupId());
			body = body.replaceAll("\\$\\{password\\}", unencrptedPassword);
			body = body.replaceAll("\\$\\{email\\}", profile.getEmail());

			return body;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
