package com.tmq.t3h.quicktask.record;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;

public class ListRecordAdapter extends BaseAdapter{
	private ArrayList<AudioItem> listAudio = new ArrayList<AudioItem>();
	private LayoutInflater lf;
	private int positionItemChose = -1;
	private Context mContext;

	public ListRecordAdapter(ArrayList<AudioItem> list, Context c) {
		listAudio = list;
		mContext = c;
		lf = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		return listAudio.size();
	}

	@Override
	public AudioItem getItem(int position) {
		return listAudio.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
//		view = lf.inflate(R.layout.record_item_in_list, parent, false);
		view = lf.inflate(R.layout.record_item_in_list, null);
		
		CommonVL.startAnimComeInBottom(view, mContext);
		
		TextView title = (TextView)		view.findViewById(R.id.txtRecordNameFile);
		
		title.setText(listAudio.get(position).getTitle());
		
		return view;
	}
	
//-----------------------------------------------------------------------------------------------
	public String convertDuration(long duration){
		int minutes = (int)(duration/60);
		int seconds = (int)(duration%60);
		return	(minutes>9 ? minutes: "0"+minutes) + " : " + 
				(seconds>9 ? seconds: "0"+seconds);
	}
	public int getPositionItemChose() {
		return positionItemChose;
	}
	public void setPositionItemChose(int positionItemChose) {
		this.positionItemChose = positionItemChose;
	}
	
	
}
