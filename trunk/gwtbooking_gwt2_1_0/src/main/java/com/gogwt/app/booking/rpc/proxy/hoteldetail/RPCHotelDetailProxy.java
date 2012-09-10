package com.gogwt.app.booking.rpc.proxy.hoteldetail;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.rpc.interfaces.hotel.HotelDetailProcessServiceAsync;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class RPCHotelDetailProxy {
    private static RPCHotelDetailProxy instance = new RPCHotelDetailProxy();
	
	private RPCHotelDetailProxy() {
	}

	public static RPCHotelDetailProxy getInstance() {
		return instance;
	}
	
	public void getHotelDetail(final int hotelId, final UserContextBean userContext, 
		       final CommandBean command,
		final RPCProxyInterface<HotelBean> callback) {
	
		HotelDetailProcessServiceAsync service = HotelDetailProcessServiceAsync.Util.getHotelDetailAsync();
	
	    service.getHotelDetail(hotelId, userContext, new AsyncCallback<HotelBean>() {

		   //@Override
		   public void onFailure(Throwable caught) {
			  callback.handleRPCError(caught, command);				
		   }

		   //@Override
		   public void onSuccess(HotelBean result) {
			  callback.handleRPCSuccess(result, command);				
		   }		 
	    });
    }
}
