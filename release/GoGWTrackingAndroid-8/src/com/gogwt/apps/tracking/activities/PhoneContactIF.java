package com.gogwt.apps.tracking.activities;

import com.gogwt.apps.tracking.provider.QuickContactSearcher;

/**
 * 
 * @author Michael.Wang
 *
 */
public interface PhoneContactIF {
	public void handlePhoneList(QuickContactSearcher.MyContact item, boolean isContact);
}
