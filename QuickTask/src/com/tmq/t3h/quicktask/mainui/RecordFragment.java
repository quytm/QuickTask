package com.tmq.t3h.quicktask.mainui;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.record.AudioItem;
import com.tmq.t3h.quicktask.record.ListRecordAdapter;
import com.tmq.t3h.quicktask.record.MediaManager;
import com.tmq.t3h.quicktask.record.MediaManager.onUpdateDuration;

public class RecordFragment extends Fragment implements OnItemClickListener, 
														OnClickListener, 
														onUpdateDuration{
	
	private static final String TAG = "RecordFragment";
	private View mRootView;
	private ListView listRecord;
	private ListRecordAdapter adapter;
	private MediaManager mediaMgr;
	private Context mContext;
	
	private Dialog dialog;
	private int posViewIsClick = -1;
	private View currentView = null;
	private ToggleButton btnPlayPause;
	
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
		mediaMgr.onUpdateDuration(this);
		
		adapter = new ListRecordAdapter(mediaMgr.getListAudio(), mContext);
		
		listRecord = (ListView) mRootView.findViewById(R.id.listRecordFile);
		listRecord.setAdapter(adapter);
		listRecord.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

//		mediaMgr.loadAudioItem(adapter.getItem(position).getData());
//		mediaMgr.play();
		getAudioFromAdapter(position);
		showDialogWhenClickItem(view);
		posViewIsClick = position;
		
	}
	
	private void showDialogWhenClickItem(View view){
		dialog.setContentView(R.layout.record_dialog_when_click_item);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mediaMgr.release();
			}
		});
		
		// InitViews
		TextView txtNameDia	= (TextView)	dialog.findViewById(R.id.txtRecordNameFileDialog);
		TextView txtName	= (TextView)	view.findViewById(R.id.txtRecordNameFile);
		
		sbrDuration			= (SeekBar)		dialog.findViewById(R.id.sbrRecordDurationDialog);
		sbrDuration.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mediaMgr.getPlayState()!=MediaManager.RELEASE)
					mediaMgr.seekTo(seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton btnDelete	= (ImageButton) dialog.findViewById(R.id.btnRecordDeleteDialog);
		ImageButton btnCancel	= (ImageButton) dialog.findViewById(R.id.btnRecordCancelDialog);
		
		btnPlayPause = (ToggleButton) dialog.findViewById(R.id.togBtnRecordPlayPauseDialog);
		
		// Set for Views
		txtNameDia.setText(txtName.getText());
		btnCancel.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		// Set click for button
		btnPlayPause.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {		// isPlaying
					mediaMgr.play();
					Toast.makeText(mContext, "play", Toast.LENGTH_SHORT).show();
				}else{
					mediaMgr.pause();
					Toast.makeText(mContext, "pause", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
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
	
	// ----------------------------- Play Audio -------------------------------------------
	private static final int UPDATE_DURATION = 1111;
	private SeekBar sbrDuration;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case UPDATE_DURATION:
					sbrDuration.setProgress(msg.arg1);
					if (msg.arg1==100){	// percent is 100%  -> Play next Audio ???
						sbrDuration.setProgress(0);
						mediaMgr.release();
					}
					break;
	
				default:
					break;
			}
		};
	};
	
	@Override
	public void onUpdate(String currentTime, int percent) {
		// Is override, it receiver Duration and send Message to Handler in order to process
		Message msg = new Message();
		msg.what = UPDATE_DURATION;
		msg.obj = currentTime;
		msg.arg1 = percent;
		mHandler.sendMessage(msg);	// Send message
	}
	
	private void getAudioFromAdapter(int position){
		// Define position of Item in Adapter -> Change background of Item: Is chose
		adapter.setPositionItemChose(position);
		AudioItem audio = adapter.getItem(position);
		// Play audio
		mediaMgr.loadAudioItem(audio.getData());
		mediaMgr.play();
	}
	
}
