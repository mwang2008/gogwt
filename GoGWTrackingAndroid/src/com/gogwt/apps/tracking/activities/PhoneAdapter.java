package com.gogwt.apps.tracking.activities;

import java.util.List;

import android.content.Context;
import android.provider.Contacts.Phones;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TwoLineListItem;

import com.gogwt.apps.tracking.provider.QuickContactSearcher;

public class PhoneAdapter extends  BaseAdapter implements AdapterView.OnItemClickListener {
	private final List<QuickContactSearcher.MyContact> mContacts;
	private final LayoutInflater mInflater;
	private final Context context;
	private final PhoneContactIF theCaller;
	private boolean isContact;
	
	public PhoneAdapter(boolean isContact, List<QuickContactSearcher.MyContact> contacts, Context context, PhoneContactIF theCaller) {
		this.isContact = isContact;
		mContacts = contacts;
		mInflater = (LayoutInflater) context		
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.theCaller = theCaller;
	}
	
	@Override
	public int getCount() {
		return mContacts.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TwoLineListItem view = (convertView != null) ? (TwoLineListItem) convertView
				: createView(parent);

		bindView(view, mContacts.get(position));
		return view;
	}

	private TwoLineListItem createView(ViewGroup parent) {
		TwoLineListItem item = (TwoLineListItem) mInflater.inflate(
				android.R.layout.simple_list_item_2, parent, false);

		// item.getText2().setSingleLine();
		// item.setHorizontallyScrolling(true);
		// item.getText2().setEllipsize(TextUtils.TruncateAt.END);
		return item;
	}

	private void bindView(TwoLineListItem view,
			QuickContactSearcher.MyContact contact) {
		 
		if (isContact) {
			view.getText1().setText(contact.name);
			view.getText2().setText(contact.number);
		}
		else {
		   view.getText1().setText(
					Phones.getDisplayLabel(context,	contact.type, "label"));
		   view.getText2().setText(contact.number);
		}
	}

	/**
	 * When user click the list item
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		theCaller.handlePhoneList(mContacts.get(position), isContact);
	}
}
