package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayAmenitiesGWTHelper;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView.Presenter;
import com.gogwt.framework.arch.utils.FormatUtils;
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

public class ResultDetailItemSubView extends AbstractWidget {
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
		EnvMappingElem  env = GWTExtClientUtils.getMappingElem();
		StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append("<a href=\"" + env.getPrefix() + "/mvphoteldetail/" + hotelBean.getId() +"\"");
		nameBuilder.append(">");
		nameBuilder.append(hotelBean.getName());
		nameBuilder.append("</a>");
		
		name.setInnerHTML((index+1) + ", " + nameBuilder.toString());
		address.setInnerText(DisplayHelper.fullAddress(hotelBean));
		amenities.setInnerText(DisplayAmenitiesGWTHelper.fillAmenities(hotelBean, tags));
		distance.setInnerText(FormatUtils.formatDistance(hotelBean.getDistance()) + " " + tags.Label_miles());
		btnSelect.setText(tags.button_Alt_Select());
		
		//Note: as button title is used for SEO, so set attribute
		//btnSelect.setTitle(index+"");
		btnSelect.getElement().setAttribute("index", index+"");
	}
	

	
}
