package com.tmq.t3h.quicktask.note;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;

public class ListNoteAdapter extends BaseAdapter{
	private static final String TAG = "ListNoteAdapter";
	private ArrayList<String> listNote = new ArrayList<String>();
	private LayoutInflater lf;

	public ListNoteAdapter(Context context) {
		lf = LayoutInflater.from(context);
		
		getAllNoteInSharePreferences(context);
	}
	
	private void getAllNoteInSharePreferences(Context context){
		DataContactSharedPref dataContact = new DataContactSharedPref(context);
		int size = dataContact.sizeOfDataContact();
		DataContactSharedPref.DataItem item;
		for (int i=1; i<=size; i++){
			item = dataContact.getData(i);
			if (!item.note.equals("null")){
				listNote.add(item.note);
			}
		}
	}
	
	
	@Override
	public int getCount() {
		return listNote.size();
	}

	@Override
	public String getItem(int position) {
		return listNote.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view==null){
			view = lf.inflate(R.layout.note_item_in_list, null);
		}
		TextView txtNote = (TextView) view.findViewById(R.id.txtNoteContent);
		txtNote.setText(listNote.get(position));
		return view;
	}

}
