package com.gwtview.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet {
	 
	//private static final String GOOGLE_HTTPS_PROXY_PATH = "/h/proxy";
	private String proxyPath="";
	private String proxyPort;
	
	public void init() throws ServletException {
        super.init();
        proxyPath = getInitParameter("proxyPath");
        
        String proxyPort = getInitParameter("proxyPort");
        
        
	}
	/**
	 * Process re
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		String requestUrl = request.getQueryString();
        requestUrl = requestUrl.substring(requestUrl.indexOf( "http" ), requestUrl.length());
 
	  	
		String user_agent = request.getHeader("User-Agent");
		URL url = new URL(requestUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", user_agent);

		InputStream mapScript = null; 
 
		try {
			String contentType = con.getContentType();
			if (isTextContentType(contentType)) {
 				//logger.debug(" content is text");
				
				mapScript = con.getInputStream();
				String resultScript = convertInputStreamToString(mapScript);
				mapScript.close();

				// replace http:
				if (resultScript.indexOf(proxyPath) == -1) {
					resultScript = resultScript.replaceAll("http:",
							proxyPath + "?http:");
				}

				PrintWriter out = response.getWriter();				 
				out.println(resultScript);
				out.flush();
			} else {
   			    //logger.debug("content is binary");
 				response.sendRedirect(requestUrl);
			}

		} catch (Throwable e) {
			e.printStackTrace();
 			//logger.debug("has error for proxy " + e.getMessage());
		}
	}

	 
	/**
	 * is text
	 * @param contentType
	 * @return
	 */
	public boolean isTextContentType(String contentType) {
		return (contentType.indexOf("text") != -1);
	}

	/**
	 * Convert inputstream to string
	 * @param theInputStream
	 * @return
	 * @throws IOException
	 */
	public String convertInputStreamToString(InputStream theInputStream)
			throws IOException {
		BufferedInputStream bf = new BufferedInputStream(theInputStream);
		int ch = 0;

		StringBuffer buf = new StringBuffer();
		while ((ch = bf.read()) > -1) {
			buf.append((char) ch);
		}
		return buf.toString();
	}

	/**
	 * Convert inputstream to byte
	 * @param theInputStream
	 * @return
	 * @throws IOException
	 */
	public byte[] convertInputStreamToBytes(InputStream theInputStream)
			throws IOException {
		byte[] theBytes = new byte[theInputStream.available()];
		theInputStream.read(theBytes);

		return theBytes;
	}
}

