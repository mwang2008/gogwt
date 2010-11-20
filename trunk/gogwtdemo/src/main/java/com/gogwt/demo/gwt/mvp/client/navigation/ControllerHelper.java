package com.gogwt.demo.gwt.mvp.client.navigation;

import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window.Location;

public class ControllerHelper {
	public static String constructSEOURL() {
		//adding 
		String currentToken = History.getToken();		 
		if (!StringUtils.isSet(currentToken)) {
			currentToken = "";
		}  
		StringBuilder sbud = new StringBuilder();
		sbud.append("<hr> SEO <br>");
		
	 	String href = Location.getHref();
	 
		//remove #token
		int tokenPos = href.indexOf("#");
		if (tokenPos != -1) {
			href = href.substring(0, tokenPos);
		}
		
		String url = StringUtils.addURLParam(href, "_escaped_fragment_", currentToken);
		sbud.append("<a href=\"" + url + "\" target=\"_blank\">" + " See how Google search engine crawler views your page" + "</a>");
		sbud.append("<br>");
		sbud.append(" View page source to see meta description, meta keywords etc");
		
		return sbud.toString();
	}
}
