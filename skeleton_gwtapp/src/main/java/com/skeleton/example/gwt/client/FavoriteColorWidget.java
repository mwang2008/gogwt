package com.skeleton.example.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FavoriteColorWidget extends Composite {
	 
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

    public FavoriteColorWidget() {
         initWidget(uiBinder.createAndBindUi(this));

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



}
