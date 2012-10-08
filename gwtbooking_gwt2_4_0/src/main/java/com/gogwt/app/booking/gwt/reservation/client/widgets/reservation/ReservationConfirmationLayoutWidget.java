package com.gogwt.app.booking.gwt.reservation.client.widgets.reservation;

import com.gogwt.app.booking.dto.dataObjects.common.WeatherResponse;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WeatherResponseJsonConverter;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
 
public class ReservationConfirmationLayoutWidget extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private Panel layoutPanel = new FlowPanel();

	public ReservationConfirmationLayoutWidget() {
		super();
		initWidget(layoutPanel);
	}
	
	/**
	 * Fill the layout
	 */
	public void prepareEntryLayout(final ReserveResponseBean reserveResponse) {
		//layoutPanel.add(new Label("Reservation is success"));
		
		
		final FlexTable mainTable = WidgetStyleUtils.createFlexTable();
		mainTable.setCellPadding( 0 );
		mainTable.setCellSpacing( 0 );
		mainTable.setStyleName( "guestInformationTable" );
		
		//add white space
		int row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
		
	 	//reservation number
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<b>" + tags.reservation_resnumber() + "</b>"));
		mainTable.setText( row, 1,  DisplayHelper.dispReservationNum(reserveResponse.getReserveNum()));
		
		//add white space
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);

	 	//CAP: guest info
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(tags.caption_guest_info(), "text12blue"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//name
	 	String name = reserveResponse.getGuestInfo().getFirstName() + "  " + reserveResponse.getGuestInfo().getLastName();
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML(name));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//guest address
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(DisplayHelper.fullAddress(reserveResponse.getGuestInfo())));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
 	 	
		//add white space
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//CAP: hotel
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(tags.caption_hotel_info(), "text12blue"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//hotel address
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(DisplayHelper.fullAddress(reserveResponse.getSelectHotel())));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
  	 
		//add white space
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);

	 	//add weather link
	 	Anchor weatherAnchor = new Anchor("  Weather  ");
	 	
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  weatherAnchor);
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//create dialog
	 	//final DialogBox dialogBox = createDialog();
	 	
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
		
		/**
		 * Call /gservice/en/restweather with input 
		 */
	 	weatherAnchor.addClickHandler(new ClickHandler() {
	 		public void onClick(ClickEvent event) {
	 			//POST
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
	 	});
		layoutPanel.add(mainTable);
		
		 
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
	
	private DialogBox createDialog() {
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
		
		return dialogBox;
	}
}
