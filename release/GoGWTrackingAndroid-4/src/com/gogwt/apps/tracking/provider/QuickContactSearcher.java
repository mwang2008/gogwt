package com.gogwt.apps.tracking.provider;

import java.util.ArrayList;
import java.util.List;

import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;

public class QuickContactSearcher {
	
	public static class MyContact {
		public final String name;
		public final String number;
		public final int type;
		
	 
		
	    public MyContact(String word, String definition) {
			this.name = word;
			this.number = definition;
			this.type = 0;
		}
	    
	    public MyContact(String word, String definition, int type) {
			this.name = word;
			this.number = definition;
			this.type = type;
		}

	}

	private static final QuickContactSearcher sInstance = new QuickContactSearcher();

	public static QuickContactSearcher getInstance() {
		return sInstance;
	}

	public List<MyContact> searchContactsByPartialName(Context context, String query) {
		List<MyContact> myContacts = new ArrayList<MyContact>();
		
		String[] proj = new String[]
			                  		 {People._ID, People.TYPE, People.NAME, People.NUMBER, People.LABEL};
		Cursor cursor;
		if (StringUtils.isSet(query)) {
			query = query.trim();
			query = query.replaceAll(" ", "%");
		    String search = People.NAME + " like \'%" + query +"%\'";
		    GwtLog.d("QuickContactSearcher", "== search="+search);
		    cursor = context.getContentResolver().query(People.CONTENT_URI, proj,
				search, null, "name asc");
	    }
		else {
			cursor = context.getContentResolver().query(People.CONTENT_URI, proj,
					null, null, "name asc");
		}
	    
		
		if (cursor == null) {
			return null;
		}
		
		int nameCol = cursor.getColumnIndex(People.NAME);
		int numberCol = cursor.getColumnIndex(People.NUMBER);
		
		cursor.moveToFirst();
	 
		if (cursor.getCount() > 0) {
			do {				
				MyContact contact = new MyContact(cursor.getString(nameCol), cursor.getString(numberCol));				 
				myContacts.add(contact) ;
			} while (cursor.moveToNext());
		}
		return myContacts;
	}
	
	 
	
	public List<MyContact> searchContactsByPartialName(Activity activity, String query) {
		List<MyContact> myContacts = new ArrayList<MyContact>();
		
		String[] proj = new String[]
			                  		 {People._ID, People.TYPE, People.NAME, People.NUMBER, People.LABEL};
	    String search = People.NAME + " like \'%" + query +"%\'";
		Cursor cursor = activity.getContentResolver().query(People.CONTENT_URI, proj,
				search, null, "name asc");
		activity.startManagingCursor(cursor);
		
		if (cursor == null) {
			return null;
		}
		
		int nameCol = cursor.getColumnIndex(People.NAME);
		int numberCol = cursor.getColumnIndex(People.NUMBER);
		
		cursor.moveToFirst();
	 
		if (cursor.getCount() > 0) {
			do {				
				MyContact contact = new MyContact(cursor.getString(nameCol), cursor.getString(numberCol));				 
				myContacts.add(contact) ;
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return myContacts;
	}
	
	public List<MyContact> searchPhonesByPartialName(Activity activity, String name) {
		List<MyContact> myContacts = new ArrayList<MyContact>();
		
		String search = Phones.NAME + "= \'" + name + "\'";
		Cursor cursor = activity.getContentResolver().query(Phones.CONTENT_URI, null,
				search, null, null);
		activity.startManagingCursor(cursor);
		
		if (cursor == null) {
			return null;
		}
		
		int nameCol = cursor.getColumnIndex(Phones.NAME);
		int idCol = cursor.getColumnIndex(Phones._ID); 
		int numCol = cursor.getColumnIndex(Phones.NUMBER);
		int typeCol = cursor.getColumnIndex(Phones.TYPE);  //0 HOME, 1 Mobile
		int labelCol = cursor.getColumnIndex(Phones.LABEL);
		 
		String number;
		int type;
		CharSequence labelStr = "label";
		CharSequence displayLabel;
		
		cursor.moveToFirst();
		 
		if (cursor.getCount() > 0) {
			do {
				number = cursor.getString(numCol);		
				type = cursor.getInt(typeCol);
				
				//displayLabel = Phones.getDisplayLabel(activity.getApplicationContext(), type, labelStr);
			
				MyContact contact = new MyContact(name, number, type);				 
				myContacts.add(contact) ;
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return myContacts;
	}

}
