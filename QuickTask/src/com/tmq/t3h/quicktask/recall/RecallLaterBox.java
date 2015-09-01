package com.tmq.t3h.quicktask.recall;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;
import com.tmq.t3h.quicktask.service.MenuInCall;

public class RecallLaterBox extends LayoutInWindowMgr implements
																OnClickListener, 
																OnSeekBarChangeListener{
	private static final String TAG = "RecallLaterBox";
	private TextView txtAmountOfHour, txtAmountOfMinute;
	private SeekBar sbrHour, sbrMinute;
	private Button btnRecallLater;
	
	private SharedPreferences recallSharedPref;

	@Override
	protected int setIdLayout() {
		Log.i(TAG, "setLayout");
		return R.layout.recall_later_box;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_FOCUSABLE |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.y = 200;
	}

	@Override
	protected void initViewsInLayout() {
		txtAmountOfHour = (TextView) mView.findViewById(R.id.txtRecallAmuontOfHour);
		txtAmountOfMinute = (TextView) mView.findViewById(R.id.txtRecallAmuontOfMinute);
		sbrHour = (SeekBar) mView.findViewById(R.id.sbrRecallHour);
		sbrMinute = (SeekBar) mView.findViewById(R.id.sbrRecallMinute);
		btnRecallLater = (Button) mView.findViewById(R.id.btnRecallLater);
		
		sbrHour.setOnSeekBarChangeListener(this);
		sbrMinute.setOnSeekBarChangeListener(this);
		
		btnRecallLater.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
//		Toast.makeText(this, 
//				"Recall later: " + txtAmountOfHour.getText() + "h " + txtAmountOfMinute.getText() + "m.", 
//				Toast.LENGTH_SHORT).show();
		setTimeToRemine();
		Intent intent = new Intent(this, MenuInCall.class);
		intent.putExtra(CommonVL.NOTI_STATE_BOX, true);
		startService(intent);
		stopSelf();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHour:
			txtAmountOfHour.setText(""+progress);
			break;
		case R.id.sbrRecallMinute:
			txtAmountOfMinute.setText(""+progress);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	
	@Override
	public void onDestroy() {
		removeLayoutInScreen();
		super.onDestroy();
	}
	
	
	private void setTimeToRemine(){
		Calendar calendar = Calendar.getInstance();
		int minutes = calendar.get(Calendar.MINUTE);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		
		int minutesRemine = ( minutes + sbrMinute.getProgress() ) % 60;
		int hoursRemine = ( hours + (minutes + sbrMinute.getProgress()) / 60 ) % 24;
		Toast.makeText(this, hoursRemine + "h : " + minutesRemine + "min", Toast.LENGTH_SHORT).show();

		calendar.set(Calendar.HOUR_OF_DAY, hoursRemine);
		calendar.set(Calendar.MINUTE, minutesRemine);
		calendar.set(Calendar.SECOND, 0);
		
		int id = saveToSharedPref(hoursRemine, minutesRemine);
		
		Intent myIntent = new Intent(this, RemineRecallLater.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, myIntent,0);

		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}
	
	private int saveToSharedPref(int h, int min){
		String time = h + "h : " + min + "min";
		int id = (int)System.currentTimeMillis();
		DataContactSharedPref saveTimeRecall = new DataContactSharedPref(this);
		saveTimeRecall.putData("null", "null", "null", time, id);
		return id;
	}
	
}