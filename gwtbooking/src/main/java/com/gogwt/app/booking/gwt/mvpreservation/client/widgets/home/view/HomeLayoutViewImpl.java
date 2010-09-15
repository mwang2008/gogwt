package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view;

import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter.DestinationSuggestOracle;
import com.gogwt.framework.arch.widgets.AbstractRequestWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class HomeLayoutViewImpl extends AbstractRequestWidget implements HomeLayoutView<SearchFormBean> {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("HomeLayoutView.ui.xml")
	interface HomeLayoutViewUiBinder extends UiBinder<Widget, HomeLayoutViewImpl> {}
	private static HomeLayoutViewUiBinder uiBinder = GWT.create(HomeLayoutViewUiBinder.class);

	private Presenter<SearchFormBean> presenter;
	
 	@UiField Label destinationLabel;
 	@UiField (provided = true) SuggestBox destination;
	@UiField Label radiusLabel;
	@UiField ListBox radius;
	@UiField Button btnSearch;
	
	 
	
	
	public HomeLayoutViewImpl() {
		
        DestinationSuggestOracle oracle = new DestinationSuggestOracle();	 
		
        TextBox destinationText = new TextBox();
		//destinationText.setText("Please enter city or full address");
		destinationText.setVisibleLength(30);
		destinationText.setMaxLength(75);
		
		destination = new SuggestBox(oracle, destinationText);
         
		initWidget(uiBinder.createAndBindUi(this));
		
		destinationLabel.setText(tags.Label_Destination());
		radiusLabel.setText(tags.Label_Radius());
		
		 //list:
        String[] nameList = {"10", "15", "20", "25"};
        for (String name : nameList) { 
        	radius.addItem(name); 
        }
        btnSearch.setText(tags.button_Alt_FindHotel());
 	}

	public void prepareEntryLayout() {
		//destinationLabel = new Label(tags.Label_Destination());
	}


/*	@UiHandler("destination")
	void suggestionDestinationKeyDown(KeyDownEvent event) {
		destination.setText("");
	}
*/	
	@UiHandler("btnSearch")
	void doSearch(ClickEvent event) {
 	   if (presenter != null) {		   
		   presenter.doSearch();
	   }	  
	}
	public void setPresenter(
			com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutView.Presenter<SearchFormBean> presenter) {
		this.presenter = presenter;
		
	}

	public Widget asWidget() {
		return this;
	}

	public SearchFormBean toValue() {
		SearchFormBean formBean = new SearchFormBean();
		formBean.setLocation(destination.getText());		
		int radiusValue = Integer.parseInt(radius.getValue(radius.getSelectedIndex()));
		formBean.setRadius(radiusValue);
		
		return formBean;
	}

	public void fromValue(SearchFormBean t) {
		// TODO Auto-generated method stub
		
	}   



}
