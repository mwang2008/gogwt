package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView.Presenter;
import com.gogwt.framework.arch.utils.GWTFormatUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class ResultDetailItemSubView<T> extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("I_ResultDetailItemSubView.ui.xml")
	interface ResultDetailItemSubViewUiBinder extends
			UiBinder<Widget, ResultDetailItemSubView> {
	}

	private static ResultDetailItemSubViewUiBinder uiBinder = GWT
			.create(ResultDetailItemSubViewUiBinder.class);

	@UiField Element name;
	@UiField Element address;
	@UiField Element amenities;
	@UiField Element distance;
	@UiField Button btnSelect;
	
	private int itemIndex;
	private HotelBean itemHotelBean;
	private Presenter<HotelBean> presenter;
	
 	public ResultDetailItemSubView(int index, HotelBean hotelBean, Presenter<HotelBean> presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		displayHotelItem(index, hotelBean);
	}
	
	/**
	 * Note: as button title is used for SEO, so set attribute
	 * @param event
	 */
	@UiHandler("btnSelect")
	void doSelect(ClickEvent event) {	   
	   String indexStr = btnSelect.getElement().getAttribute("index");
	   int index = Integer.parseInt(indexStr);
	   final ReservationContainerBean currentContainer = GWTSession
		.getCurrentReservationContainer();
	   
	   HotelBean selectHotel = currentContainer.getHotelSearchResponse().getHotelList().get(index);
	   
	   //set selected hotel to GWTSession
	   currentContainer.setSelectedHotel(selectHotel);
	   currentContainer.setSelectHotelItem(index);
	   
	   if (presenter != null) {		   
		   presenter.doSelect(index, selectHotel);
	   }	 
	}
	
	
	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	public HotelBean getItemHotelBean() {
		return itemHotelBean;
	}

	public void setItemHotelBean(HotelBean itemHotelBean) {
		this.itemHotelBean = itemHotelBean;
	}

	
	public Presenter<HotelBean> getPresenter() {
		return presenter;
	}

	public void setPresenter(Presenter<HotelBean> presenter) {
		this.presenter = presenter;
	}

	private void displayHotelItem(int index, HotelBean hotelBean) {
		name.setInnerText(hotelBean.getName());
		address.setInnerText(DisplayHelper.fullAddress(hotelBean));
		amenities.setInnerText(DisplayHelper.fillAmenities(hotelBean, tags));
		distance.setInnerText(GWTFormatUtils.formatDistance(hotelBean.getDistance()) + " miles");
		btnSelect.setText("Select");
		
		//Note: as button title is used for SEO, so set attribute
		//btnSelect.setTitle(index+"");
		btnSelect.getElement().setAttribute("index", index+"");
	}
	

	
}
