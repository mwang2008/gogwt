package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view;

import com.gogwt.app.booking.gwt.mvpreservation.client.i18n.TagsReservationResources;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class HomeLayoutViewImpl extends BaseWidget {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("HomeLayoutView.ui.xml")
	interface HomeLayoutViewUiBinder extends UiBinder<Widget, HomeLayoutViewImpl> {}
	private static HomeLayoutViewUiBinder uiBinder = GWT.create(HomeLayoutViewUiBinder.class);

	
	public HomeLayoutViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void prepareEntryLayout() {
		 
	}

}
