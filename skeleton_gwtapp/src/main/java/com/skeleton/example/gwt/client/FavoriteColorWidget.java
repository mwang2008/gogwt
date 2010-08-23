package com.skeleton.example.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FavoriteColorWidget extends Composite {
	private static TagsResources tags = TagsResources.Util.getInstance();
	
    private static FavoriteColorWidgetUiBinder uiBinder = GWT
              .create(FavoriteColorWidgetUiBinder.class);

    interface FavoriteColorWidgetUiBinder extends
              UiBinder<Widget, FavoriteColorWidget> {
    }

    @UiField Label greeting;
    
    @UiField CheckBox red;
    @UiField CheckBox white;
    @UiField CheckBox blue;
    @UiField Button button;
    @UiField ListBox listBox;
    @UiField TextBox address;
    
    @UiField Label addressLabel;
    
    @UiField Label firstName;
    //@UiField Label lastName;
    @UiField Button btnSearch;
    
    public FavoriteColorWidget() {
         initWidget(uiBinder.createAndBindUi(this));

         addressLabel.setText(tags.Label_address());
         
         //list:
         String[] nameList = {"michael", "steve", "bill"};
         for (String name : nameList) { 
             listBox.addItem(name); 
         }

         firstName.setText(tags.label_First_Name());
         //lastName.setText(tags.label_Last_Name());
         
         // add a greeting
         greeting.setText("Hello Jeff!!");

         final ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
         checkboxes.add(red);
         checkboxes.add(white);
         checkboxes.add(blue);

        // add a button handler to show the color when clicked
         button.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                   String t = "";
                   for(CheckBox box : checkboxes) {
                        // if the box was checked
                        if (box.getValue()) {
                             t += box.getFormValue() + ", ";
                        }
                   }
                   Window.alert("Your favorite color/colors are: "+ t);
              }
         });

    }

    @UiHandler("btnSearch")
    void handleClick(ClickEvent e) {
    	//String address = address.get
    	String whiteStr= white.getFormValue();
    	String whiteText = white.getText();
    	Boolean whiteBoolean = white.getValue();
    	String addressStr = address.getText();
    	
      Window.alert("Hello, AJAX: whiteStr="+whiteStr + ", addressStr="+addressStr + ", whiteText="+whiteText + ",whiteBoolean="+whiteBoolean);
    }


}
