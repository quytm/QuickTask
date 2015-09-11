package com.tmq.t3h.quicktask.mainui;

import java.io.File;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.record.ListRecordAdapter;
import com.tmq.t3h.quicktask.record.MediaManager;

public class RecordFragment extends Fragment implements OnItemClickListener{
	
	private static final String TAG = "RecordFragment";
	private View mRootView;
	private ListRecordAdapter adapter;
	private MediaManager mediaMgr;
	private Context mContext;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.record_main_ui, null);
		mContext = container.getContext();
		
		initViews();
		
		return mRootView;
	}
	
	private void initViews(){
		mediaMgr = new MediaManager(mContext);
		mediaMgr.readAllAudio();
		adapter = new ListRecordAdapter(mediaMgr.getListAudio(), mContext);
		
		ListView listRecord = (ListView) mRootView.findViewById(R.id.listRecordFile);
		listRecord.setAdapter(adapter);
		listRecord.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView txt = (TextView) view.findViewById(R.id.txtRecordNameFile);
//		File file = new File(CommonVL.PATH_RECORD_FILE + txt.getText() + ".amr");
		File file = new File(CommonVL.PATH_RECORD_FILE);
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setData(Uri.fromFile(file));
//		intent.setType("file/*");
//		startActivity(Intent.createChooser(intent, "View Default File Manager"));
		
		intent.setType("application/file");
		startActivity(Intent.createChooser(intent, "View Default File Manager"));
		
	}
	
}
