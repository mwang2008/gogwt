package com.gogwt.soap.service.wsdlfirst;

import trade.schema.gogwt.com.Comment;
import trade.schema.gogwt.com.Status;
import trade.schema.gogwt.com.Trade;
import tradeservice.schema.gogwt.com.TradeService;


/**
 * Create webservice for tradeservice based on generated files of trade.wsdl 
 * http://localhost/gwebservice/trade?wsdl
 * 
 * @author michael.wang
 *
 */
@javax.jws.WebService(
        serviceName = "TradeService",
        portName = "TradeService",
        targetNamespace = "http://com.gogwt.schema.tradeservice",
        //wsdlLocation = "file:/C:/views/mycfx/ws-example/src/main/wsdl/trade.wsdl",
        endpointInterface = "tradeservice.schema.gogwt.com.TradeService")        
public class TradeServiceImpl implements TradeService {

	public Status book(Trade trade) {
		System.out.print ("Booking security ");
		System.out.print (trade.getSecurity());
		System.out.print (", quantity ");
		System.out.print (trade.getQuantity());
		System.out.println();
		if (trade.getComments() != null) {
			System.out.println ("Comments:");
			for (Comment c : trade.getComments()) {
				System.out.print (c.getAuthor());
				System.out.print (": ");
				System.out.print (c.getMessage());
				System.out.println();
			}
		}
		Status s = new Status();
		s.setId("12345");
		s.setMessage("ok");
		return s;
	}

}