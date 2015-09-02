package com.tmq.t3h.quicktask.recall;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;

public class ListRecallAdapter extends BaseAdapter{
	private static final String TAG = "ListRecallAdapter";
	private ArrayList<DataContactSharedPref.DataItem> listRecall;
	private LayoutInflater lf;

	public ListRecallAdapter(Context context) {
		lf = LayoutInflater.from(context);
		listRecall = new ArrayList<DataContactSharedPref.DataItem>();
		getAllNoteInSharePreferences(context);
	}
	
	private void getAllNoteInSharePreferences(Context context){
		DataContactSharedPref dataContact = new DataContactSharedPref(context);
		int size = dataContact.sizeOfDataContact();
		DataContactSharedPref.DataItem item;
		for (int i=1; i<=size; i++){
			item = dataContact.getData(i);
			if (!item.recallTime.equals("null")){
				listRecall.add(item);
			}
		}
	}
	
	
	@Override
	public int getCount() {
		return listRecall.size();
	}

	@Override
	public DataContactSharedPref.DataItem getItem(int position) {
		return listRecall.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view==null){
			view = lf.inflate(R.layout.recall_item_in_list, null);
		}
		TextView txtRecallTime = (TextView) view.findViewById(R.id.txtRecallTime);
		TextView txtRecallPhoneName = (TextView) view.findViewById(R.id.txtRecallPhoneName);
		TextView txtRecallPhoneNumber = (TextView) view.findViewById(R.id.txtRecallPhoneNumber);
		
		txtRecallTime.setText(listRecall.get(position).recallTime);
		txtRecallPhoneName.setText(listRecall.get(position).name);
		txtRecallPhoneNumber.setText(listRecall.get(position).number);
		
		return view;
	}

}
