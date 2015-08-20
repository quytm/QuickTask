package com.tmq.t3h.quicktask.note;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmq.t3h.quicktask.CommonVL;
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
		SharedPreferences sharePref = 
				context.getSharedPreferences(CommonVL.NOTE_SHAREPREFERENCES, context.MODE_PRIVATE);
		int number = sharePref.getInt(CommonVL.NUMBER_NOTE, 0);
		String keyNote_ = CommonVL.NOTE_;
		for (int i=1; i<=number; i++){
			listNote.add(sharePref.getString(keyNote_+i, "null"));
			Log.i(TAG, "Note_" + i + ": " + listNote.get(i-1));
		}
		Log.i(TAG, "Finish getNote");
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
		Log.i(TAG, "getView.............");
		if (view==null){
			view = lf.inflate(R.layout.note_item_in_list, null);
		}
		TextView txtNote = (TextView) view.findViewById(R.id.txtNoteContent);
		txtNote.setText(listNote.get(position));
		Log.i(TAG, "finish getView.............");
		return view;
	}

}
