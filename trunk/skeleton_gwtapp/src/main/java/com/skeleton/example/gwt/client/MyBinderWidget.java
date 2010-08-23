package com.skeleton.example.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyBinderWidget extends Composite {
	private static TagsResources tags = TagsResources.Util.getInstance();
	
    private static MyBinderWidgetUiBinder uiBinder = GWT
              .create(MyBinderWidgetUiBinder.class);

    interface MyBinderWidgetUiBinder extends UiBinder<Widget, MyBinderWidget> { }

    @UiField VerticalPanel myPanelContent;
    @UiField Label welcomeLabel;
    @UiField SpanElement nameSpan;

    public MyBinderWidget() {
         initWidget(uiBinder.createAndBindUi(this));

         final String welcomeStr = tags.label_welcome();
         welcomeLabel.setText(welcomeStr);
         setName(welcomeStr);
         
         HTML html1 = new HTML();
         html1.setHTML("<a href='http://www.google.com'>Click me!</a>");
         myPanelContent.add(html1);
         HTML html2 = new HTML();
         html2.setHTML("This is my sample <b>content</b>!");
         myPanelContent.add(html2);

    }
    
    public void setName(String name) { nameSpan.setInnerText(name); }

}
