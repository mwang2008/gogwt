package com.gogwt.app.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlUnit {

	private void googlePageNavigation() throws Throwable {
		String url;
		for (int i = 3; i < 4; i++) {
			if (i == 0) {
				url = "http://www.google.com/search?q=atlanta+hotel&hl=en";
			} else {
				url = "http://www.google.com/search?q=atlanta+hotel&hl=en&start="
						+ i * 10 + "&sa=N";
			}

			String html = retrivePage(url);
			
			//System.out.println(" ---- url=" + url);
			
			if (html.indexOf("holiadyinn") != -1) {
				System.out.println(" ---- found holidayinn: " + i);				
			}

			if (html.indexOf("hiexpress") != -1) {
				System.out.println(" ---- found hiexpress: " + i);				
				String filePathName = "d:\\test\\google_atlanta" + i + ".html";
				saveToFile(filePathName, html);
			}
			

			

			
			// <?xml version="1.0" encoding="UTF-8"?>
			

			// }

			
		}

	}
	private void test() throws Throwable {
		System.out.println(" --- test ----");
		
		//String theURL = "http://www.google.com/url?sa=t&source=web&cd=40&ved=0CHcQFjAJOB4&url=http%3A%2F%2Fwww.hiexpress.com%2Fhotels%2Fus%2Fen%2Fhd%2Funited-states%2Fgeorgia%2Fatlanta-hotels&rct=j&q=atlanta%20hotel&ei=9L9vTY-NMIOtgQeEga03&usg=AFQjCNFcN4EZbzQidWxoyFQkqZnGTQXUIg";
		//String html = retrivePage(theURL);
		//String filePathName = "d:\\test\\google_atlanta_final.html";
		//saveToFile(filePathName, html);

		//googlePageNavigation();
		
		//currently, it is on page 5, will execute 500 times to see the result. March 03, 2011
		String atlbhURL = "http://www.google.com/url?sa=t&source=web&cd=49&ved=0CEsQFjAIOCg&url=http%3A%2F%2Fwww.ichotelsgroup.com%2Fh%2Fd%2Fic%2F1%2Fen%2Fhd%2FATLBH&rct=j&q=atlanta%20hotel&ei=2spvTcDsMZOFtgfrpOTPBQ&usg=AFQjCNFUw1c22XbXZUeCsOfuGVFsO_95tQ&cad=rja";
		String atlbhURLhtml = retrivePage(atlbhURL);
		String filePathNameatlbh = "d:\\test\\google_atlanta_atlbh.html";
		saveToFile(filePathNameatlbh, atlbhURLhtml);
			
		
	}

	/**
	 *  <?xml version="1.0" encoding="UTF-8"?>
	 * String filePathName = "d:\\test\\google_atlanta" + i + ".html";
	 * @param filePathName
	 * @param str
	 * @throws Throwable
	 */
	private void saveToFile(String filePathName, String str) throws Throwable {
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(filePathName)));
		out.write(str);
		out.close();
	}
	
	private String retrivePage(String url) throws Throwable {
		long start = System.currentTimeMillis();
		String html = "";
		WebClient webClient = null;
		HtmlPage htmlPage = null;
		// String
		// paramSeoUrl="http://www.google.com/webhp?sourceid=navclient-ff#hl=en&sugexp=gsihc&xhr=t&q=atlanta+hotel&cp=2&qe=YXQ&qesig=l2UxdIHFoE1rrqLS_IqWDw&pkc=AFgZ2tn_6L3gBTkDdEYHd6wyORMq2BIcgMmQRgOO7KGAsQL08F_AhR2vRjF2z_vXUtDnfz53KQrSo9--NQDRN8vYTRdR5b4Aeg&pf=p&sclient=psy&site=webhp&source=hp&aq=0p&aqi=&aql=f&oq=at&pbx=1&bav=on.2,or.&fp=89e96c95c947f3f0";
		// String paramSeoUrl="http://www.google.com";
		// working one
		String paramSeoUrl = url; // "http://www.google.com/search?hl=en&q=atlanta+hotel";
		// file:///search?q=atlanta+hotel&hl=en&prmd=ivnscbm&ei=nWluTeuePIe-tgeT0-2FDw&start=10&sa=N
		// http://www.google.com/search?q=atlanta+hotel&hl=en&prmd=ivnscbm&ei=IGduTeDqNM2tgQfQ7dBU&start=20&sa=N
		// http://www.google.com/search?q=atlanta+hotel&hl=en&start=100&sa=N
		try {
			webClient = new WebClient(BrowserVersion.FIREFOX_3);
			webClient.setThrowExceptionOnFailingStatusCode(false); // since some
																	// pages are
																	// giving
																	// 503
			webClient.setThrowExceptionOnScriptError(false); // since htmlunit
																// detect some
																// errors in the
																// javascripts
			webClient
					.setAjaxController(new NicelyResynchronizingAjaxController()); // converts
																					// async
																					// calls
																					// to
																					// synchronous
			webClient.setCssEnabled(false);
			webClient.getCookieManager().setCookiesEnabled(true);

			webClient.setTimeout(6000);

			webClient.addRequestHeader("seoRequest", "true");

			htmlPage = (HtmlPage) webClient.getPage(paramSeoUrl);

			// wait FEW seconds in order to make sure all javascript threads
			// complete execution

			webClient.waitForBackgroundJavaScript(5000);

			webClient.waitForBackgroundJavaScriptStartingBefore(4000);

			List<HtmlAnchor> anchorList = htmlPage.getAnchors();
			for (HtmlAnchor myAnchor : anchorList) {
				String anchorText = myAnchor.asText();
				String href = myAnchor.getHrefAttribute();
				
				System.out.println(" href=" + href + ", anchorText=" + anchorText);
				
				//if (href.indexOf("holidayinn.com") != -1 || href.indexOf("www.hiexpress.com/hotels/us/en/hd/united-states/georgia-hotels") != -1) {
				if (href.indexOf("www.hiexpress.com/hotels/us/en/hd/united-states/georgia/atlanta-hotels") != -1) {
				    System.out.println(" ==== found holdyainn or hiexpress and ready to click");
				    
					HtmlPage anchorPage = (HtmlPage)myAnchor.click();
					String anchorPageInStr = anchorPage.asXml();
					
					//return clk(this.href,'','','','40','','0CHkQFjAJOB4')
					String mouseDown = myAnchor.getOnMouseDownAttribute(); 
					
					mouseDown = mouseDown.replace("this.href", href);
					System.out.println(" mouseDown="+mouseDown);
					
					String mouseOver = myAnchor.getOnMouseOverAttribute();
					System.out.println(" mouseOver="+mouseOver);
					
					//has error running following code.
					//ScriptResult scriptResult = 
					ScriptResult scriptResult = htmlPage.executeJavaScript( mouseDown );
//http://www.google.com/url?sa=t&source=web&cd=47&ved=0CHkQFjAJOB4&url=http%3A%2F%2Fwww.hiexpress.com%2Fhotels%2Fus%2Fen%2Fhd%2Funited-states%2Fgeorgia%2Fatlanta-hotels&rct=j&q=atlanta%20hotels&ei=9bxuTeb5DoL_8AaLhK2YDw&usg=AFQjCNFcN4EZbzQidWxoyFQkqZnGTQXUIg&cad=rja

					String filePathName = "d:\\test\\google_atlanta_script.html"; 
					Page scriptPage = scriptResult.getNewPage();
					String scriptResultStr = scriptResult.toString();
					
					saveToFile(filePathName, scriptResultStr);
					//System.out.println("\n\n" + anchorPageInStr);

					System.out.println(" === finish ");
				}
			}
			
			html = htmlPage.asXml();

		} catch (Exception e) {
			System.out
					.println("Exception occured when processing page with HTMLUnit for the URL= "
							+ paramSeoUrl);
		} finally {
			if (htmlPage != null) {
				htmlPage.cleanUp();
			}
			if (webClient != null) {
				webClient.closeAllWindows();
			}
			htmlPage = null;
			webClient = null;
		}
		long end = System.currentTimeMillis();
		System.out.println("\n\n" + paramSeoUrl + ": took " + (end - start)
				+ " milliseconds");

		return html;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new TestHtmlUnit().test();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
