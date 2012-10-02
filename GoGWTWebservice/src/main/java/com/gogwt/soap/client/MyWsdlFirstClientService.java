package com.gogwt.soap.client;

import java.math.BigInteger;

import net.webservicex.GetWeatherReport;
import net.webservicex.USWeatherSoap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;

import trade.schema.gogwt.com.Status;
import trade.schema.gogwt.com.Trade;
import tradeservice.schema.gogwt.com.TradeService;

import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;
import com.gogwt.utils.JAXBConverter;

public class MyWsdlFirstClientService {
	private static final Log log = LogFactory.getLog(MyWsdlFirstClientService.class);
	
	//http://localhost/webservice/ws-example?wsdl
	private static final String TRADE_SERVER_URL = "http://localhost:8080/gservice/ws/trade";
	
	//http://localhost/webservice/referral?wsdl
	private static final String LOCAL_REFERRAL_SERVER_URL = "http://localhost/webservice/referral";
	
	//https://uat.healthesystems.com/axis2/services/PartnerHESReferralWSv4.1?wsdl
	private static final String HES_REFERRAL_SERVER_URL = "https://uat.healthesystems.com/axis2/services/PartnerHESReferralWSv4.1";
	
	/**
	 * Test trade service
	 * wsdl is hosted locally.
	 * @throws Throwable
	 */
	private void testTradeService() throws Throwable {
        System.out.println(" ==== testTradeService ====");
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		factory.setServiceClass(TradeService.class);
		factory.setAddress(TRADE_SERVER_URL);
			
		TradeService client = (TradeService) factory.create();

		Trade t = new Trade();
		t.setSecurity("ASX");
		t.setQuantity(BigInteger.valueOf(10));

		Status s = client.book(t);
		System.out.println(s.getMessage());
	}
	
	/**
	 * http://localhost/webservice/referral?wsdl
	 * PartnerHESReferralWSv41PortTypeServiceImp will be called
	 */
	/*
	private void testLocalReferral() throws Throwable {
		System.out.println(" ==== start testReferral ====");
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		factory.setServiceClass(PartnerHESReferralWSv41PortType.class);
		
		factory.setAddress(LOCAL_REFERRAL_SERVER_URL);
		PartnerHESReferralWSv41PortType client = (PartnerHESReferralWSv41PortType)factory.create();
		
		Referral myReferral = new Referral();
		myReferral.setHesReferralID(1234566);
		ReferralAck referralAck = client.processReferral(myReferral);
		
		System.out.println(" response referralAck =" + referralAck.getHesReferralID());
		System.out.println(" ==== end testReferral ====");
	}
	*/
	/*
	private void testHESReferral() throws Throwable {
       System.out.println(" ==== start testHESReferral ====");
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		//factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		factory.setServiceClass(PartnerHESReferralWSv41PortType.class);
		
		factory.setAddress(HES_REFERRAL_SERVER_URL);
		PartnerHESReferralWSv41PortType client = (PartnerHESReferralWSv41PortType)factory.create();
		
		String resrouce = "xml/23589_out.xml";
		String xml = getReferralXMLFromResource(resrouce);
		System.out.println(" xml = \n" + xml);
		
		ProcessReferral myProcessReferral = (ProcessReferral)JAXBConverter.unmarshall(xml, ProcessReferral.class);
		 
		Referral myReferral = myProcessReferral.getR();
		
 		ReferralAck referralAck = client.processReferral(myReferral);
 		
		ProcessReferralResponse response = new ProcessReferralResponse();
		response.setReturn(referralAck);
		
		String out = JAXBConverter.marshall(response);
		System.out.println("===== \n" + out);
		
		System.out.println(" response referralAck =" + referralAck.getHesReferralID());
		System.out.println(" ==== end testHESReferral ====");
		 
	}
	*/

	/**
	 * The input of service is raw xml payload
	 * raw xml map to object USWeatherSoap
	 * 
	 * Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><GetWeatherReport xmlns="http://www.webserviceX.NET"><ZipCode>30097</ZipCode></GetWeatherReport></soap:Body></soap:Envelope>
	 * Payload: <?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body><GetWeatherReportResponse xmlns="http://www.webserviceX.NET"><GetWeatherReportResult>&lt;Weather&gt;&lt;City&gt;&lt;/City&gt;&lt;State&gt;&lt;/State&gt;&lt;County&gt;&lt;/County&gt;&lt;Fahrenheit&gt;&lt;/Fahrenheit&gt;&lt;Celsius&gt;&lt;/Celsius&gt;&lt;Condition&gt;&lt;/Condition&gt;&lt;Humidity&gt;&lt;/Humidity&gt;&lt;Wind&gt;&lt;/Wind&gt;&lt;Sunrise&gt;&lt;/Sunrise&gt;&lt;Sunset&gt;&lt;/Sunset&gt;&lt;DailyReport&gt;&lt;Day&gt;&lt;/Day&gt;&lt;HighFahrenheit&gt;&lt;/HighFahrenheit&gt;&lt;HighCelsius&gt;&lt;/HighCelsius&gt;&lt;LowFahrenheit&gt;&lt;/LowFahrenheit&gt;&lt;LowCelsius&gt;&lt;/LowCelsius&gt;&lt;/DailyReport&gt;&lt;DailyReport&gt;&lt;Day&gt;&lt;/Day&gt;&lt;HighFahrenheit&gt;&lt;/HighFahrenheit&gt;&lt;HighCelsius&gt;&lt;/HighCelsius&gt;&lt;LowFahrenheit&gt;&lt;/LowFahrenheit&gt;&lt;LowCelsius&gt;&lt;/LowCelsius&gt;&lt;/DailyReport&gt;&lt;DailyReport&gt;&lt;Day&gt;&lt;/Day&gt;&lt;HighFahrenheit&gt;&lt;/HighFahrenheit&gt;&lt;HighCelsius&gt;&lt;/HighCelsius&gt;&lt;LowFahrenheit&gt;&lt;/LowFahrenheit&gt;&lt;LowCelsius&gt;&lt;/LowCelsius&gt;&lt;/DailyReport&gt;&lt;DailyReport&gt;&lt;Day&gt;&lt;/Day&gt;&lt;HighFahrenheit&gt;&lt;/HighFahrenheit&gt;&lt;HighCelsius&gt;&lt;/HighCelsius&gt;&lt;LowFahrenheit&gt;&lt;/LowFahrenheit&gt;&lt;LowCelsius&gt;&lt;/LowCelsius&gt;&lt;/DailyReport&gt;&lt;DailyReport&gt;&lt;Day&gt;&lt;/Day&gt;&lt;HighFahrenheit&gt;&lt;/HighFahrenheit&gt;&lt;HighCelsius&gt;&lt;/HighCelsius&gt;&lt;LowFahrenheit&gt;&lt;/LowFahrenheit&gt;&lt;LowCelsius&gt;&lt;/LowCelsius&gt;&lt;/DailyReport&gt;&lt;/Weather&gt;</GetWeatherReportResult></GetWeatherReportResponse></soap:Body></soap:Envelope>
	 */
	public void soapXMLUSWeather(String zip) throws Throwable {
		 System.out.println(" ==== start testUSWeather ====");
			
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
		//http://www.webservicex.net/usweather.asmx?WSDL
		String weatherWSDL = "http://www.webservicex.net/usweather.asmx";
	    factory.setServiceClass(USWeatherSoap.class);
			
		factory.setAddress(weatherWSDL);
		//USWeatherSoap client = (USWeatherSoap)factory.create();

		String resrouce = "xml/usweather_body.tmpl";
		resrouce = resrouce.trim();
		
		String xml = JAXBConverter.getReferralXMLFromResource(resrouce);
		System.out.println(" xml = \n" + xml);
		
		xml = xml.replace("$xipCode", zip);
		
		GetWeatherReport clientIn = (GetWeatherReport)JAXBConverter.unmarshall(xml, GetWeatherReport.class);
		
		USWeatherSoap client = (USWeatherSoap)factory.create();
	    String report = client.getWeatherReport(clientIn.getZipCode());
			
		System.out.println(report);
		 		
			
		System.out.println(" == end testUSWeather");
	}

	public void soapObjectUSWeather(String zip) throws Throwable {
		 System.out.println(" ==== start soapObjectUSWeather ====");
			
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
		//http://www.webservicex.net/usweather.asmx?WSDL
		String weatherWSDL = "http://www.webservicex.net/usweather.asmx";
	    factory.setServiceClass(USWeatherSoap.class);
			
		factory.setAddress(weatherWSDL);
		USWeatherSoap client = (USWeatherSoap)factory.create();

		String report = client.getWeatherReport(zip);
		
		System.out.println(report);
			
			
		System.out.println(" == end soapObjectUSWeather");
	}

	/*
	 * http://www.webservicex.net/WS/WSDetails.aspx?WSID=53&CATID=12
	 */
	private void testUSWeather(String zip) throws Throwable {
		 System.out.println(" ==== start testUSWeather ====");
			
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		//http://www.webservicex.net/usweather.asmx?WSDL
		String weatherWSDL = "http://www.webservicex.net/usweather.asmx";
	    factory.setServiceClass(USWeatherSoap.class);
		
		factory.setAddress(weatherWSDL);
		USWeatherSoap client = (USWeatherSoap)factory.create();

		String report = client.getWeatherReport(zip);
		
		System.out.println(report);
		
		String resrouce = "xml/usweather.tmpl";
		
		
		System.out.println(" == end testUSWeather");
	}
	
	/**
	 * http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL
	 * @param zip
	 * @throws Throwable
	 */
	public WeatherReturn retrieveWeather(String zip) throws Throwable {
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		//http://www.webservicex.net/usweather.asmx?WSDL
		String weatherWSDL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx";
	    factory.setServiceClass(WeatherSoap.class);
		
		factory.setAddress(weatherWSDL);
		WeatherSoap client = (WeatherSoap)factory.create();

		WeatherReturn report = client.getCityWeatherByZIP(zip);
		
		System.out.println(report);
		
		return report;
	}
	
	private void progress() throws Throwable {
		System.out.println(" ==== progress ====");
		
		String zip = "30097";		
		 
 		retrieveWeather(zip);
		testTradeService();
	}
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MyWsdlFirstClientService().progress();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

	}

	class MyLoggingInInterceptor extends LoggingInInterceptor {
		 public void handleMessage(Message message) throws Fault {
		     System.out.println("MyLoggingInInterceptor");
		     Object xml = message.get("PAYLOAD");
		     System.out.println(xml);
		     
		     
		     Object headers = message.get(Message.PROTOCOL_HEADERS);
		     
		    /* try {
		    	InputStream is = message.getContent(InputStream.class);
				String payload = IOUtils.toString(is);
				System.out.println(" payload =" + payload);
				 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		     
		     
		 }
	}
}
