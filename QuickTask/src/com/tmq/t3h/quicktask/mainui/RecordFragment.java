package com.tmq.t3h.quicktask.mainui;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.record.ListRecordAdapter;
import com.tmq.t3h.quicktask.record.MediaManager;

public class RecordFragment extends Fragment implements OnItemClickListener, OnClickListener{
	
	private static final String TAG = "RecordFragment";
	private View mRootView;
	private ListView listRecord;
	private ListRecordAdapter adapter;
	private MediaManager mediaMgr;
	private Context mContext;
	
	private Dialog dialog;
	private int posViewIsClick = -1;
	private View currentView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.record_main_ui, null);
		mContext = container.getContext();
		dialog = new Dialog(mContext, R.style.PauseDialog);
		
		initViews();
		
		return mRootView;
	}
	
	private void initViews(){
		mediaMgr = new MediaManager(mContext);
		mediaMgr.readAllAudio();
		adapter = new ListRecordAdapter(mediaMgr.getListAudio(), mContext);
		
		listRecord = (ListView) mRootView.findViewById(R.id.listRecordFile);
		listRecord.setAdapter(adapter);
		listRecord.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

//		indexToFileMgr();
		
		showDialogWhenClickItem(view);
		posViewIsClick = position;
		
	}
	
	private void showDialogWhenClickItem(View view){
		dialog.setContentView(R.layout.record_dialog_when_click_item);
		
		// InitViews
		TextView txtNameDia	= (TextView)	dialog.findViewById(R.id.txtRecordNameFileDialog);
		TextView txtName	= (TextView)	view.findViewById(R.id.txtRecordNameFile);
		
		ImageButton btnDelete	= (ImageButton) dialog.findViewById(R.id.btnRecordDeleteDialog);
		ImageButton btnCancel	= (ImageButton) dialog.findViewById(R.id.btnRecordCancelDialog);
		
		// Set for Views
		txtNameDia.setText(txtName.getText());
		btnCancel.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
//		
//		// Set click for button
//		btnDelete.setOnClickListener(this);
//		btnCancel.setOnClickListener(this);
//		btnCopy.setOnClickListener(this);
//		btnSave.setOnClickListener(this);
		
		dialog.show();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRecordDeleteDialog:
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
			alertDialogBuilder.setMessage("Delete file Record?\n" +
											adapter.getItem(posViewIsClick).getDisplayName());
			
			alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String path = adapter.getItem(posViewIsClick).getData();
					File file = new File(path);
		        	if (file.delete()){
		        		mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
		 				Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
		 				Log.i(TAG, "Deleted...");
		 				adapter.removeItem(posViewIsClick);
		 				listRecord.setAdapter(adapter);
		 				dialog.dismiss();
		        	 }
		         }
		      });
		      
		      alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		            
		         }
		      });
		      
		      AlertDialog alertDialog = alertDialogBuilder.create();
		      alertDialog.show();
			
			break;
			default: dialog.dismiss();
		}
		
	}
	
	private void indexToFileMgr(){
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
