_soap from production database mdata 

SoapUI does not work for: 23589_to_hes_soap_db.xml 
SoapUI does work for: 23589_to_hes_soap_realtime.xml

by change <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
with <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
it is working fine.



