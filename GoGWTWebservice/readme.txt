1. download wsdl to main/wsdl directory
http://www.webservicex.net/usweather.asmx?WSDL
http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL

2. modify pom.xml

3. modify appContext.xml

4. mvn clean install

5. mycopy.bat

6. start tomcat or mvn jetty:run

7. http://hq-dt048:8080/gservice/



http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL

MyWsdlFirstClientService
public WeatherReturn retrieveWeather(String zip) throws Throwable {

INFO: Outbound Message
---------------------------
Encoding: UTF-8
Headers: {SOAPAction=["http://ws.cdyne.com/WeatherWS/GetCityWeatherByZIP"], Accept=[*]}
Messages: 
Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><GetCityWeatherByZIP xmlns="http://ws.cdyne.com/WeatherWS/"><ZIP>30097</ZIP></GetCityWeatherByZIP></soap:Body></soap:Envelope>
-
INFO: Inbound Message
----------------------------
Encoding: UTF-8
Headers: {content-type=[text/xml; charset=utf-8], X-AspNet-Version=[2.0.50727], connection=[keep-alive], Date=[Mon, 01 Oct 2012 19:36:45 GMT], Content-Length=[762], Expires=[-1], X-Cache=[MISS from webfilter.cypresscare.com], X-Powered-By=[ASP.NET], Server=[Microsoft-IIS/7.5], Pragma=[no-cache], Cache-Control=[no-cache]}
Messages: 
Message:

Payload: <?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body><GetCityWeatherByZIPResponse xmlns="http://ws.cdyne.com/WeatherWS/"><GetCityWeatherByZIPResult><Success>true</Success><ResponseText>City Found</ResponseText><State>GA</State><City>Duluth</City><WeatherStationCity>Lawrenceville</WeatherStationCity><WeatherID>32</WeatherID><Description>Light Rain</Description><Temperature>72</Temperature><RelativeHumidity>94</RelativeHumidity><Wind>SE8</Wind><Pressure>29.70F</Pressure><Visibility /><WindChill /><Remarks /></GetCityWeatherByZIPResult></GetCityWeatherByZIPResponse></soap:Body></soap:Envelope>


+------------------------------------------------------------------------
| create REST webservice to be called from GWT booking app.
+------------------------------------------------------------------------ 
Use Spring MVC REST package
1. REST Webservice
Test with Chrome plugin:
http://localhost:8080/gservice/en/resttest
and set Headers: Accept: application/html

2. WS CFX Webservice
View webservice http://localhost:8080/gservice/ws
WSDL: http://localhost:8080/gservice/ws/hello_world?wsdl

3. Spring MVC annatation
http://localhost:8080/gservice/en/hello

