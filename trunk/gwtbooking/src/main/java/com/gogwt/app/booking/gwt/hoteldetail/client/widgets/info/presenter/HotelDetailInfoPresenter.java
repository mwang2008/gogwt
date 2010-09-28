package com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.presenter;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsHotelDetailResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.widget.Presenter;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoView;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.hoteldetail.RPCHotelDetailProxy;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.ui.HasWidgets;

public class HotelDetailInfoPresenter implements Presenter, HotelDetailInfoView.Presenter<HotelBean>, RPCProxyInterface<HotelBean> {
	TagsHotelDetailResources tags = TagsHotelDetailResources.Util.getInstance();
	
    private final HotelDetailInfoView<HotelBean> view; 
	
	public HotelDetailInfoPresenter(HotelDetailInfoView<HotelBean> view) {
		this.view = view;
		this.view.setPresenter(this);				 		
	}
	
	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());		
	}

	public void retrieveHotelDetail() {
		//1. check hotel Id is set or not
		EnvMappingElem  envMappingElem = GWTExtClientUtils.getMappingElem();
		if (!StringUtils.isSet(envMappingElem.getHotelId())) {
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Please specify hotelId");
			view.dispErrorMsg(errorList);
			return;
		}
		
		//2. verify hotelId
		int hotelId = 0;
		try {
		   hotelId = Integer.parseInt(envMappingElem.getHotelId());
		} catch (NumberFormatException e) {
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Wrong hotelId, please specify correct hotelId");
			view.dispErrorMsg(errorList);
			return;
		}
		
		//3. call RPC to get HotelBean
		CommandBean passComment = new CommandBean();
		passComment.addArg("hotelId", hotelId);
		RPCHotelDetailProxy.getInstance().getHotelDetail(hotelId, GWTExtClientUtils.getUserContext(), passComment, this);
	}

	public void handleRPCSuccess(HotelBean hotelBean, CommandBean command) {
		if (hotelBean == null) {
			int hotelId = (Integer)command.getArg("hotelId");
			
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Could not find hotel which matches the hotelId of " + hotelId);
			view.dispErrorMsg(errorList);
			return;
		}
		view.displayHotelDetail(hotelBean);		
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		//view.displayHotelDetailError(caught);
		Log.info("error when retrieving hotel with id");
		
		int hotelId = (Integer)command.getArg("hotelId");
		
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add("Could not find hotel which matches the hotelId of " + hotelId);
		view.dispErrorMsg(errorList);
	}
}


