package com.gogwt.app.booking.businessService.domainService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.EmailConfig;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;
import com.gogwt.app.booking.dto.dataObjects.request.ContactFormBean;
import com.gogwt.app.booking.utils.EmailUtils;

public class CommonBusinessService {
	private CommonDAO commonDAO;

	/**
	 * Search for hotels
	 * @param searchFormBean
	 */
	public List<StateBean> getStateList(final UserContextBean userContext) {
		return getCommonDAO().getStateList(userContext);
	}

	public List<KeywordBean> getKeywordList(String keyword, final int numberResult) {
		return getCommonDAO().getKeywordList(keyword, numberResult);
	}

	
	public HotelBean getHotelDetail(final int propertyId, final UserContextBean userContext) {
		return getCommonDAO().getHotelDetail(propertyId, userContext);
	}
	
	public void sendContact(final ContactFormBean contactFormBean) {
        EmailConfig emailConfig =  commonDAO.getEmailConfig();
		
		EmailUtils emailServer = EmailUtils.getInstance(emailConfig);
		List<String> tos = new ArrayList<String>();
		tos.add("contact@gogwt.com");
		tos.add("mwang_2008@yahoo.com");
		String subject = contactFormBean.getSubject();
		if (StringUtils.isEmpty(subject)) {
			subject = "No subject";
		}
		
		StringBuilder bodyBuilder = new StringBuilder();
		bodyBuilder.append("===== Send from Contact Form " + new Date() + " ======");
		bodyBuilder.append("from: " + contactFormBean.getLastName() + ", " + contactFormBean.getFirstName());
		bodyBuilder.append("email: " + contactFormBean.getEmail());
		bodyBuilder.append("phone: " + contactFormBean.getPhone());
		bodyBuilder.append("--- content ---");
		bodyBuilder.append(contactFormBean.getComment());
		
		emailServer.sendEmail(tos, "contact@gogwt.com", subject, bodyBuilder.toString());
	}
	
	private CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	
	
}
