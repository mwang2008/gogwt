package com.gogwt.apps.tracking.services.domain;

import java.util.Comparator;

import com.gogwt.apps.tracking.data.TrackingMobileData;

public class TrackingMobileDataComparator implements Comparator <TrackingMobileData>{
	@Override
	public int compare(TrackingMobileData c1, TrackingMobileData c2) {
		TrackingMobileData o1 = (TrackingMobileData) c1;
		TrackingMobileData o2 = (TrackingMobileData) c2;

		if (o1.getStartTime() - o2.getStartTime() == 0) {
			if (o1.getDisplayName().equals(o2.getDisplayName())) {
				return (int) (o1.getTime() - o2.getTime());
			} else {
				return o1.getDisplayName().compareTo(
						o2.getDisplayName());
			}
		} else {
			return (int) -(o1.getStartTime() - o2.getStartTime());
		}
	}			 

}
