package com.tmq.t3h.quicktask.message;

import java.util.ArrayList;

import com.tmq.t3h.quicktask.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterMessage extends BaseAdapter{
	private static final String TAG = "AdapterMessage";
	private ArrayList<String> listMsg = new ArrayList<String>();
	private LayoutInflater lf;

	public AdapterMessage(Context context) {
		lf = LayoutInflater.from(context);
		loadMessage();
	}
	
	private void loadMessage(){
		Log.i(TAG, "Start load DataMessage");
		listMsg.add("Tôi đang bận! Xin gọi lại sau");
		listMsg.add("Tôi đang trong lớp học");
		listMsg.add("Tôi đang làm việc");
		listMsg.add("Tôi đang họp");
		listMsg.add("Tôi đang đi trên đường");
	}
	
	@Override
	public int getCount() {
		return listMsg.size();
	}

	@Override
	public String getItem(int position) {
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null){
			view = lf.inflate(R.layout.message_item, null);
		}
		TextView msg = (TextView) view.findViewById(R.id.txtContentMsg);
		msg.setText(listMsg.get(position));
		return view;
	}

}
