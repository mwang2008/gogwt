package com.gogwt.apps.tracking.services.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ActiveColorAssignmentManager {
	private static Map<String, String> displayNameColorMap = new HashMap<String, String>();

	public static String getColor(String displayName) {
		if (displayNameColorMap.containsKey(displayName)) {
			return displayNameColorMap.get(displayName);
		}

		//get next color which is not used.
		String nextColor = getNextColor(displayName);
		displayNameColorMap.put(displayName, nextColor);
		
		return nextColor;
	}

	public static void removeColor(String displayName) {
		if (displayNameColorMap.containsKey(displayName)) {
			displayNameColorMap.remove(displayName);
		}
	}
	
	/**
	 * Return the first color which is not in the list
	 * @param displayName
	 * @return
	 */
	private static String getNextColor(String displayName) {
		for (String color : getColors()) {
			if (!displayNameColorMap.containsValue(color)) {
				return color;
			}
		}
		
		return "#000000"; //black
	}
	
	public static ArrayList<String> getColors() {
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("#FF0000"); // red
		colors.add("#00FF00"); // green }
		colors.add("#0000FF"); // blue }
		colors.add("#FFFF00"); // yellow }
		colors.add("#00FFFF"); // red }
		colors.add("#FF00FF"); // purple }
		
		return colors;
	}
}
