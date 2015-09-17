package com.tmq.t3h.quicktask.recall;

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

public class ListRecallAdapter extends BaseAdapter{
	private static final String TAG = "ListRecallAdapter";
	private ArrayList<DataContactSharedPref.DataItem> listRecall;
	private LayoutInflater lf;
	
	private Context mContext;

	public ListRecallAdapter(Context context) {
		lf = LayoutInflater.from(context);
		mContext = context;
		listRecall = new ArrayList<DataContactSharedPref.DataItem>();
		getAllNoteInSharePreferences(context);
	}
	
	private void getAllNoteInSharePreferences(Context context){
		DataContactSharedPref dataContact = new DataContactSharedPref(context);
		int size = dataContact.sizeOfDataContact();
		DataContactSharedPref.DataItem item;
		for (int i=size; i>=0; i--){
			item = dataContact.getData(i);
			if (item.note.equals("null") && !item.recallTime.equals("null")){
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
	
	public void removeItem(int position) {
		listRecall.remove(position);
	}
	

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view==null){
			view = lf.inflate(R.layout.recall_item_in_list, null);
		}
		
		CommonVL.startAnimComeInBottom(view, mContext);
		
		TextView txtRecallTime = (TextView) view.findViewById(R.id.txtRecallTime);
		TextView txtRecallPhoneName = (TextView) view.findViewById(R.id.txtRecallPhoneName);
		TextView txtRecallPhoneNumber = (TextView) view.findViewById(R.id.txtRecallPhoneNumber);
		
		String name = listRecall.get(position).name; 
		if (name.equals(CommonVL.CONTACT_IS_NOT_IN_DEVICE)){
			txtRecallPhoneName.setText(listRecall.get(position).number);
			txtRecallPhoneNumber.setText("");
		}else {
			txtRecallPhoneName.setText(listRecall.get(position).name);
			txtRecallPhoneNumber.setText(listRecall.get(position).number);
		}
		txtRecallTime.setText(listRecall.get(position).recallTime);
		
		return view;
	}

}
