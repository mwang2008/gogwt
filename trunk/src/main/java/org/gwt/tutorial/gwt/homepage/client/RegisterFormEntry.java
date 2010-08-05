package org.gwt.tutorial.gwt.homepage.client;

import org.gwt.tutorial.dto.dataObjects.common.CommandBean;
import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;
import org.gwt.tutorial.gwt.homepage.client.i18n.TagsResources;
import org.gwt.tutorial.proxy.rpc.CustomerProxy;
import org.gwt.tutorial.proxy.rpc.RPCProxyInterfaces;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtview.arch.widgets.ActionForm;

public class RegisterFormEntry extends ActionForm implements ClickListener, RPCProxyInterfaces<EnrollResponse> {
	private final static TagsResources tags = TagsResources.Util.getInstance();
	
	private TextBox firstname; // = new TextBox();
	private TextBox lastname; // =; new TextBox();
	private Button submitBtn; // = new Button("Submit");
	private Button myBtn;
	 

	
	public RegisterFormEntry() {
		super();
		initForm();
	}

	/**
	 * init form entry
	 */
	public void initForm() {
		firstname = new TextBox();
		lastname = new TextBox();	
		submitBtn = new Button("Submit");
		submitBtn.addClickListener(this);
		
		myBtn = new Button("waiting click");
		myBtn.addClickListener(this);
	}

	//////////////////////////////////////////////////////
	// control section
	//
	/**
	 * 
	 */
	public void onClick(Widget widget) {
	
	  System.out.println("== onClick");
	  if (widget == submitBtn) {
		  Window.alert("======= process submit");
	      processSubmit();
	      return;
	  }
	  
	  if (widget == myBtn) {
		  Window.alert("======== myBtn");
		  myBtn.setText("clicked");  
		  return;
	  }
		
	}
	
	/**
	 * processs button action of submit
	 */
	public void processSubmit() {
		GuestInfoDTO guestInfo = new GuestInfoDTO();
		guestInfo.setFirstname(firstname.getText());
		guestInfo.setLastname(lastname.getText());
		
		//rpc call
		Window.alert("======== before calling RPC");
		CustomerProxy.getInstance().enroll(guestInfo, new CommandBean(), this);
		
	}
	
	/**
	 * RPC call back, success
	 */
	public void handleRPCSuccess(EnrollResponse result, CommandBean command) {
		String ret = result.getStatus();
		System.out.println("ret="+ret);
		
		History.newItem( "confirm" );
	}

	
	/**
	 * RPC call back, error
	 */
	public void handleRPCError(Throwable caught, CommandBean command) {
		// TODO Auto-generated method stub
		Window.alert("======== handleRPCError caught:" + caught.getCause());
	}


	
	////////////////////////////////////////////////
	// mutator & accessor
	//
	public TextBox getFirstname() {
		return firstname;
	}
 
	public TextBox getLastname() {
		return lastname;
	}
 

	public Button getSubmitBtn() {
		return submitBtn;
	}

	public void setFirstname(TextBox firstname) {
		this.firstname = firstname;
	}

	public void setLastname(TextBox lastname) {
		this.lastname = lastname;
	}

	public void setSubmitBtn(Button submitBtn) {
		this.submitBtn = submitBtn;
	}

	public Button getMyBtn() {
		return myBtn;
	}

	public void setMyBtn(Button myBtn) {
		this.myBtn = myBtn;
	}

	 
}
