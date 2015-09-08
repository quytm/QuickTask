package com.tmq.t3h.quicktask.note;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.TouchToDeleteItem;

public class ListNoteAdapter extends BaseAdapter{
	private static final String TAG = "ListNoteAdapter";
	private ArrayList<DataContactSharedPref.DataItem> listNote;
	private LayoutInflater lf;
	private Context mContext;
	
	private TouchToDeleteItem touch;

	public ListNoteAdapter(Context context) {
		lf = LayoutInflater.from(context);
		mContext = context;
		
		listNote = new ArrayList<DataContactSharedPref.DataItem>();
		
		touch = new TouchToDeleteItem(context);
		
		getAllNoteInSharePreferences(context);
	}
	
	private void getAllNoteInSharePreferences(Context context){
		DataContactSharedPref dataContact = new DataContactSharedPref(context);
		int size = dataContact.sizeOfDataContact();
		DataContactSharedPref.DataItem item;
		for (int i=1; i<=size; i++){
			item = dataContact.getData(i);
			if (!item.note.equals("null")){
				listNote.add(item);
			}
		}
	}
	
	
	@Override
	public int getCount() {
		return listNote.size();
	}

	@Override
	public DataContactSharedPref.DataItem getItem(int position) {
		return listNote.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setItem(String contactName, String noteContent, int position){
		DataContactSharedPref.DataItem item = listNote.get(position);
		item.name = contactName;
		item.note = noteContent;
		listNote.set(position, item);
	}
	
	public void removeItem(int position){
		if (position<listNote.size()) listNote.remove(position);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view==null){
			view = lf.inflate(R.layout.note_item_in_list, null);
		}
		CommonVL.startAnimComeInBottom(view, mContext);
		TextView txtNote = (TextView) view.findViewById(R.id.txtNoteContent);
		TextView txtContact = (TextView) view.findViewById(R.id.txtNoteItemContact);
		TextView txtTime = (TextView) view.findViewById(R.id.txtNoteItemTime);
		
		DataContactSharedPref.DataItem item = listNote.get(position);
		txtNote.setText(item.note);
		if (item.name.equals(CommonVL.CONTACT_IS_NOT_IN_DEVICE))	txtContact.setText(item.number);
		else	txtContact.setText(item.name);
		txtTime.setText(item.recallTime);
		
		// Set touch for item in order to startAnimation delete item
//		view.setOnTouchListener(touch);
		
		return view;
	}

}
