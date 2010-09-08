package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.i18n.TagsReservationResources;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchResultViewImpl extends AbstractWidget implements
		SearchResultView<SearchFormBean>, RPCProxyInterface<BaseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("SearchResultView.ui.xml")
	interface SearchResultViewUiBinder extends
			UiBinder<Widget, SearchResultViewImpl> {
	}

	private static SearchResultViewUiBinder uiBinder = GWT
			.create(SearchResultViewUiBinder.class);

	private Presenter<SearchFormBean> presenter;

	@UiField Label destinationLabel;
	@UiField TabLayoutPanel tabPanel;
	
	private Image mapviewOn, mapviewOff, listviewOn, listviewOff;
	private final int MAP_TAB = 0;
	private final int LIST_TAB = 1;

	public SearchResultViewImpl() {
		 
		initWidget(uiBinder.createAndBindUi(this));
		init();
		tabPanel.selectTab(1);
		
		//tabPanel.addSelectionHandler(handler);
	}


	@UiHandler("tabPanel")
	void selectTab(SelectionEvent<Integer>event) {
	   Window.alert("btnSelectionDestinationClicked event=" + event.getSelectedItem());	 
	   
	   return;
	      
	}
	public void setPresenter(
			com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView.Presenter<SearchFormBean> presenter) {
		this.presenter = presenter;

	}

	public Widget asWidget() {
		return this;
	}

	public void process() {
		final ReservationContainerBean currentContainer = GWTSession
				.getCurrentReservationContainer();

		/*----------------------------------------------------------+
		 * Session backup in case user click refersh button. 
		 *----------------------------------------------------------*/
		if (currentContainer != null
				&& currentContainer.getHotelSearchRequest() != null
				&& currentContainer.getHotelSearchResponse() != null) {
			processDisplayHotelItems(currentContainer.getHotelSearchResponse());
			return;
		}
		
		// could not find, call session backup.
		RPCReservationProxy.getInstance()
					.getReservationContainerBeanFromSession(
							ProcessStatusEnum.SEARCH_RESULT, new CommandBean(),
							this);
		

	}
	
	
	public void handleRPCSuccess(BaseBean result, CommandBean command) {
		ReservationContainerBean reservationContainer = (ReservationContainerBean)result;
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getHotelSearchRequest() != null 
	      && reservationContainer.getHotelSearchResponse() != null) {
	      GWTSession.setCurrentReservationContainer( reservationContainer );
	      
	      processDisplayHotelItems( reservationContainer.getHotelSearchResponse() );
	      
	    } else {
			// invoke event bus for target page
			//eventBus.fireEvent(new HotelSearchEvent());	
	    	GWTExtClientUtils.redirect( VIEW_HOME );
	    } 
		
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		  // could not find in backend server session, redirect back to
	    //home page.
		GWTExtClientUtils.redirect( VIEW_HOME );
 	}
	
	/**
	 * 
	 * @param hotelSearchResponse
	 */
	private void processDisplayHotelItems(
			final HotelSearchResponseBean hotelSearchResponse) {
		
		//destinationLabel.setText("=== processDisplayHotelItems ===");
	
		// 1. map/list view
	/*	tabPanel.add(new FlowPanel(), tags.searchresult_tabpanel_map_view());
		tabPanel.add(new FlowPanel(), tags.searchresult_tabpanel_list_view());
		tabPanel.getTabBar().setTabHTML(MAP_TAB, mapviewOn.toString());
		tabPanel.getTabBar().setTabHTML(LIST_TAB, listviewOff.toString());
		
		tabPanel.selectTab(MAP_TAB);*/


 	}
	
	
	private void init() {	 
		EnvMappingElem envMappingElem = GWTExtClientUtils.getMappingElem();

		mapviewOn = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/mapviewOn.gif");
		mapviewOn.setTitle("Map View");

		mapviewOff = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/mapviewOff.gif");
		mapviewOff.setTitle("Map View");

		listviewOn = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/listviewOn.gif");
		listviewOn.setTitle("List View");

		listviewOff = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/listviewOff.gif");
		listviewOn.setTitle("List View");
	}
}
