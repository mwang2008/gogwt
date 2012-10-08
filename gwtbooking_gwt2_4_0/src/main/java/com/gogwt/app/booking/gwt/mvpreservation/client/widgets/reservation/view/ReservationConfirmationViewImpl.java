package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.WeatherResponse;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WeatherResponseJsonConverter;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReservationConfirmationViewImpl extends AbstractWidget implements ReservationConfirmationView <ReserveResponseBean>  {

	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();

    @UiTemplate("ReservationConfirmationView.ui.xml")
    interface ReservationConfirmationViewUiBinder extends UiBinder<Widget, ReservationConfirmationViewImpl> {}

    private static ReservationConfirmationViewUiBinder uiBinder = GWT.create(ReservationConfirmationViewUiBinder.class);

    private Presenter presenter;
    
    @UiField Label reservationNumber; 
    @UiField Label reservationNumberLabel;
    @UiField Label guestName;
    @UiField Label guestAddress;
    @UiField Label hotelName;
    @UiField Label hotelAddress;
    
    private ReserveResponseBean reserveResponse;
    
    public ReservationConfirmationViewImpl() {
 		initWidget(uiBinder.createAndBindUi(this)); 	
  	}
    
	 
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	public Widget asWidget() {
		return this;
	}
 	
	/**
	 * Display reservation result
	 */
	public void processDisplay(ReserveResponseBean reserveResponse) {
		this.reserveResponse = reserveResponse;
		
		reservationNumberLabel.setText(tags.reservation_resnumber());
		
		reservationNumber.setText(DisplayHelper.dispReservationNum(reserveResponse.getReserveNum()));
		
		final GuestInfoFormBean guestInfo = reserveResponse.getGuestInfo();
		
		guestName.setText(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		guestAddress.setText(DisplayHelper.fullAddress(guestInfo));
		
		final HotelBean reservedHotel = reserveResponse.getSelectHotel();
		
		hotelName.setText(reservedHotel.getName());
		hotelAddress.setText(DisplayHelper.fullAddress(reservedHotel));
		
	}
	
	
	@UiHandler("weatherAnchor")
    void onWeatherAnchorClick(ClickEvent event) {
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		//dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
	 		}
		});
		
		String url = "/gservice/en/restweather";
		final RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		final String requestJSON = getJSONInput(reserveResponse.getSelectHotel().getZipCode());
		
		builder.setHeader("Content-Type", "application/json"); 
	    try {
			 builder.sendRequest(requestJSON, new RequestCallback() {

				/**
				 * Call back
				 */
				public void onResponseReceived(Request request, Response response) {
					String text = response.getText();
					WeatherResponse weatherResponse = WeatherResponseJsonConverter.convertJSON(text);
					
					dialogBox.setText("Weather ");
					dialogBox.setWidth("300px");
					dialogBox.setHeight("200px");
					 
					serverResponseLabel.setHTML(weatherResultInHtml(weatherResponse));
					dialogBox.center();
					closeButton.setFocus(true);
				}
				 
				public void onError(Request arg0, Throwable arg1) {
					serverResponseLabel.setHTML("Error to get weather: " + arg1.getMessage());
					dialogBox.center();
				}

			 });
		 }
	     catch (RequestException e) {
		 
  	     }
	}

	private String getJSONInput(String zip) {
		return "{\"zip\":\""+ zip + "\", \"temperatureType\":\"F\"}";
	}
	
	private String weatherResultInHtml(WeatherResponse weatherResponse) {
		StringBuilder weathreHtml = new StringBuilder();
		weathreHtml.append("<table width=\"100%\">");
		if (weatherResponse.isSuccess()) {
			weathreHtml.append("<tr>");
			weathreHtml.append("<td>");				
			weathreHtml.append("City");
			weathreHtml.append("</td>");
			weathreHtml.append("<td>");
			weathreHtml.append(weatherResponse.getCity());
			weathreHtml.append("</td>");
			weathreHtml.append("</tr>");
			
			weathreHtml.append("<tr>");
			weathreHtml.append("<td>");				
			weathreHtml.append("State");
			weathreHtml.append("</td>");
			weathreHtml.append("<td>");
			weathreHtml.append(weatherResponse.getState());
			weathreHtml.append("</td>");
			weathreHtml.append("</tr>");

			weathreHtml.append("<tr>");
			weathreHtml.append("<td>");				
			weathreHtml.append("Zip");
			weathreHtml.append("</td>");
			weathreHtml.append("<td>");
			weathreHtml.append(weatherResponse.getZip());
			weathreHtml.append("</td>");
			weathreHtml.append("</tr>");
			
			
			weathreHtml.append("<tr>");
			weathreHtml.append("<td>");				
			weathreHtml.append("Temperature");
			weathreHtml.append("</td>");
			weathreHtml.append("<td>");
			weathreHtml.append(weatherResponse.getTemperature() + " "  + weatherResponse.getTemperatureType());
			weathreHtml.append("</td>");
			weathreHtml.append("</tr>");
			
			
			
		}
		else {
			weathreHtml.append("<tr>");
			weathreHtml.append("<td>");				
			weathreHtml.append("Error");
			weathreHtml.append("</td>");
			weathreHtml.append("<td>");
			weathreHtml.append(weatherResponse.getResponseText());
			weathreHtml.append("</td>");
			weathreHtml.append("</tr>");
		}
		
		return weathreHtml.toString();
	}

}
