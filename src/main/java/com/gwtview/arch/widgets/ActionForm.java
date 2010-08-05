package com.gwtview.arch.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.gwtview.arch.navigation.ViewConfigAccessor;

public abstract class ActionForm extends Composite {
    private String currentToken;
    
    /* refreence of ViewConfi*/
    private ViewConfigAccessor viewAccessor;

	public String getCurrentToken() {
		return currentToken;
	}

	public void setCurrentToken(String currentToken) {
		this.currentToken = currentToken;
	}

	public ViewConfigAccessor getViewAccessor() {
		return viewAccessor;
	}

	public void setViewAccessor(ViewConfigAccessor viewAccessor) {
		this.viewAccessor = viewAccessor;
	}
    
    
}
