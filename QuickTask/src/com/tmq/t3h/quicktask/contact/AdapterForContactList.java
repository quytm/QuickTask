package com.tmq.t3h.quicktask.contact;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmq.t3h.quicktask.ContactItem;
import com.tmq.t3h.quicktask.R;

public class AdapterForContactList extends BaseAdapter{
	private ArrayList<ContactItem> listContact;
	private LayoutInflater lf;
	private Context mContext;

	public AdapterForContactList(Context context) {
		lf = LayoutInflater.from(context);
		listContact = new ArrayList<ContactItem>();
		mContext = context;
		getAllContactInDevice();
	}
	
	private void getAllContactInDevice(){
		String indexName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
		String indexNumber = ContactsContract.CommonDataKinds.Phone.NUMBER;
		Cursor contact = mContext.getContentResolver()
			.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, Data.DISPLAY_NAME);
		String name = "", number = "";
		
		while (contact.moveToNext()){
			name = contact.getString(contact.getColumnIndex(indexName));
			number = contact.getString(contact.getColumnIndex(indexNumber));
			listContact.add(new ContactItem(name, number));
		}
	}
	
	@Override
	public int getCount() {
		return listContact.size();
	}

	@Override
	public ContactItem getItem(int position) {
		return listContact.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view==null){
			view = lf.inflate(R.layout.contact_item, null);
		}
		TextView txtName = (TextView) view.findViewById(R.id.txtContactItemName);
		TextView txtNumber = (TextView) view.findViewById(R.id.txtContactItemPhonenumber);
		
		ContactItem contact = listContact.get(position);
		txtName.setText(contact.getName());
		txtNumber.setText(contact.getPhoneNumber());
		
		return view;
	}

}
