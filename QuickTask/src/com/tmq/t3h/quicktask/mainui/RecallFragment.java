package com.tmq.t3h.quicktask.mainui;

import java.util.Calendar;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.recall.ListRecallAdapter;

public class RecallFragment extends Fragment implements OnItemClickListener, 
														OnClickListener,
														OnSeekBarChangeListener{
	private static final String TAG = "RecallFragment";
	private View mRootView;
	private ListRecallAdapter adapter;
	private Context mContext;
	
	private AdapterView<?> adapterView = null;
	private Dialog dialog;
	private SeekBar sbrHour, sbrMinutes;
	private TextView txtHour, txtMinutes;
	private int posViewIsClick = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.recall_main_ui, null);
		mContext = container.getContext();
		dialog = new Dialog(mContext, R.style.PauseDialog);
		initViews();
		return mRootView;
	}
	
	private void initViews(){
		adapter = new ListRecallAdapter(mContext);
		
		ListView listRecall = (ListView) mRootView.findViewById(R.id.listRecallLater);
		listRecall.setAdapter(adapter);
		listRecall.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		showDialogWhenClickItem(view);
		
		adapterView = parent;
		posViewIsClick = position;
	}

	
	// ------------------------------ Dialog -------------------------------------------
	private void showDialogWhenClickItem(View view){
		dialog.setContentView(R.layout.recall_dialog_when_click_item);
		dialog.setTitle("Remine:");
		
		// InitViews
		sbrHour 		= (SeekBar) 	dialog.findViewById(R.id.sbrRecallHourDialog);
		sbrMinutes		= (SeekBar) 	dialog.findViewById(R.id.sbrRecallMinuteDialog);
		
		txtHour			= (TextView)	dialog.findViewById(R.id.txtRecallAmuontOfHourDialog);
		txtMinutes		= (TextView)	dialog.findViewById(R.id.txtRecallAmuontOfMinuteDialog);
		
		ImageButton btnDelete	= (ImageButton)		dialog.findViewById(R.id.btnRecallDialogDeleteItem);
		ImageButton btnCancel	= (ImageButton)		dialog.findViewById(R.id.btnRecallDialogCancel);
		ImageButton btnRemine	= (ImageButton)		dialog.findViewById(R.id.btnRecallDialogRemineRecall);
		ImageButton btnCallNow	= (ImageButton)		dialog.findViewById(R.id.btnRecallDialogCallNow);
		
		// Set for seekbar
		sbrHour.setOnSeekBarChangeListener(this);
		sbrMinutes.setOnSeekBarChangeListener(this);
		TextView txtTime = (TextView) view.findViewById(R.id.txtRecallTime);
		String time = txtTime.getText().toString();
		int pos = time.indexOf(":");
		int hours = Integer.parseInt(time.substring(0, pos-1));
		int minutes = Integer.parseInt(time.substring(pos+2, time.length()));
		sbrHour.setProgress(hours);
		sbrMinutes.setProgress(minutes);
		
		// Set click for button
		btnDelete.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnRemine.setOnClickListener(this);
		btnCallNow.setOnClickListener(this);
		
		dialog.show();
	}
	
	@Override
	public void onClick(View v) {
		DataContactSharedPref sharedPref = new DataContactSharedPref(mContext);
		
//		View view = adapterView.getChildAt(posViewIsClick);
//		TextView txtContent = (TextView) view.findViewById(R.id.);
		switch (v.getId()) {
		case R.id.btnRecallDialogDeleteItem:
			Toast.makeText(mContext, "Recall is deleted", Toast.LENGTH_SHORT).show();
			adapterView.removeViews(posViewIsClick, 1);
			sharedPref.removeData(adapter.getItem(posViewIsClick).position);
			adapter.removeItem(posViewIsClick);
			break;
		case R.id.btnRecallDialogCancel:
			Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnRecallDialogRemineRecall:
			Toast.makeText(mContext, "Time to recall is changed", Toast.LENGTH_SHORT).show();
			int hoursRemine = sbrHour.getProgress();
			int minutesRemine = sbrMinutes.getProgress();
			
//			txtContent.setText(edtContent.getText().toString());
//			adapter.setItem(edtContact.getText().toString(), edtContent.getText().toString(), posViewIsClick);
//			sharedPref.setNoteDataAt(adapter.getItem(posViewIsClick).position, edtContent.getText().toString());
			
//			Calendar calendar = Calendar.getInstance();
//			
//			calendar.set(Calendar.HOUR_OF_DAY, hoursRemine);
//			calendar.set(Calendar.MINUTE, minutesRemine);
//			calendar.set(Calendar.SECOND, 0);
			break;
		case R.id.btnRecallDialogCallNow:
			Toast.makeText(mContext, "Call now", Toast.LENGTH_SHORT).show();
			String number = adapter.getItem(posViewIsClick).number;
			Toast.makeText(mContext, number, Toast.LENGTH_SHORT).show();
	        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)); 
	        startActivity(callIntent);
			break;
		}
		
		dialog.dismiss();
	}
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHourDialog:
			txtHour.setText((progress>9 ? "" : "0") + progress);
			break;
		case R.id.sbrRecallMinuteDialog:
			txtMinutes.setText((progress>9 ? "" : "0") + progress);
			break;
		}
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
