package com.gogwt.apps.tracking.services.domain;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.c2md.AuthenticationUtil;
import com.gogwt.apps.tracking.c2md.C2DMResponse;
import com.gogwt.apps.tracking.c2md.MessageUtil;
import com.gogwt.apps.tracking.c2md.SecureStorage;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.CouldNotFindException;
import com.gogwt.apps.tracking.formbean.C2DMMessageBean;
import com.gogwt.apps.tracking.formbean.C2DMRegisterBean;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.gogwt.apps.tracking.utils.ToStringUtils;

public class C2DMBusinessDomainService extends BaseBusinessDomainService {
	private static Logger logger = Logger.getLogger(C2DMBusinessDomainService.class);
	
	private static String token;
	
	public String registerC2DM(final C2DMRegisterBean registerBean) {
		logger.debug(ToStringUtils.toString(registerBean));
		try {
			if (registerBean.isRegister()) {
		       getCustomerDAO().registerC2DM(registerBean);
			}
			else {
			   getCustomerDAO().unregisterC2DM(registerBean);
			}
		    return "success";
		}
		catch (AppRemoteException e) {
			e.printStackTrace();
			return "failure";	
		}
		
	}
	
 
	
	public String sendC2DMMessage(C2DMMessageBean messageBean) {
		try {
			C2DMRegisterBean registerBean = getCustomerDAO().getC2DMRegisterByPhonenumber(messageBean.getTo());
			
			//found valid target registrationid
			String registrationid = registerBean.getRegistrationid();
			 
			try {
				if (token == null) {
					token = AuthenticationUtil.getToken(SecureStorage.USER, SecureStorage.PASSWORD);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "could_not_auth";
			}
			
			try {
				return sendMessage(token, registrationid, messageBean.getDisplayName(), messageBean.getMsg());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
			return "failure";
		}
		catch (CouldNotFindException e) {
			return "could_not_find_target";
		}
		catch (AppRemoteException e) {
			e.printStackTrace();
			return "could_not_find_target";
		}
		
	}

	public List<C2DMRegisterBean> getRegisterListByGroupId(final String groupId) throws AppRemoteException {
		return getCustomerDAO().getRegisterListByGroupId(groupId);
	}
	
	public String sendNotification() {
		return "todo";
	}
	
	private String sendMessage(String tokenIn, final String registrationid, final String fromPhone, final String msg) throws IOException {
	  		 
		C2DMResponse response = MessageUtil.sendMessage(tokenIn, registrationid, fromPhone, msg);
		if (response.getResponseCode() != C2DMResponse.SC_OK) {
			token = null;
			return "failure";
		}
		
		if (StringUtils.isSet(response.getUpdatedAuthToken())) {
			token = response.getUpdatedAuthToken();
		}
		
		return 	"success"; 
	}
	
	
}
