package com.tmq.t3h.quicktask.mainui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.recall.ListRecallAdapter;

public class RecallFragment extends Fragment{
	private View mRootView;
	private ListRecallAdapter adapter;
	private Context mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.recall_main_ui, null);
		mContext = container.getContext();
		
		initViews();
		return mRootView;
	}
	
	private void initViews(){
		adapter = new ListRecallAdapter(mContext);
		
		ListView listRecall = (ListView) mRootView.findViewById(R.id.listRecallLater);
		listRecall.setAdapter(adapter);
		
	}

}
