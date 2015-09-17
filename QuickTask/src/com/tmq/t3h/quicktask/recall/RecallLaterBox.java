package com.tmq.t3h.quicktask.recall;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
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
	private ImageButton btnRecallLater;
	private Calendar calendar;
	
	@Override
	protected int setIdLayout() {
//		Log.i(TAG, "setLayout");
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
		txtAmountOfHour.setBackgroundResource(android.R.color.white);
		txtAmountOfMinute.setBackgroundResource(android.R.color.white);
		sbrHour = (SeekBar) mView.findViewById(R.id.sbrRecallHour);
		sbrMinute = (SeekBar) mView.findViewById(R.id.sbrRecallMinute);
		btnRecallLater = (ImageButton) mView.findViewById(R.id.btnRecallLater);
		
		sbrHour.setOnSeekBarChangeListener(this);
		sbrMinute.setOnSeekBarChangeListener(this);
		
		btnRecallLater.setOnClickListener(this);
		
		calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE) + 20;
		if (minutes>59){
			minutes %= 60;		hours ++;
		}
		sbrHour.setProgress(hours);
		sbrMinute.setProgress(minutes);
	}
	
	@Override
	public void onClick(View v) {
		setTimeToRemine();
		
		Intent intent = new Intent(this, MenuInCall.class);
		intent.putExtra(CommonVL.NOTI_STATE_BOX, CommonVL.BOX_NOT_SHOWED);
		startService(intent);
		stopSelf();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHour:
			txtAmountOfHour.setText((progress>9 ? "" : "0") + progress);
			break;
		case R.id.sbrRecallMinute:
			txtAmountOfMinute.setText((progress>9 ? "" : "0") + progress);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHour:	
			txtAmountOfHour.setBackgroundResource(android.R.drawable.editbox_background);
			break;
		case R.id.sbrRecallMinute:
			txtAmountOfMinute.setBackgroundResource(android.R.drawable.editbox_background);
			break;
	}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHour:	
			txtAmountOfHour.setBackgroundResource(android.R.color.white);
			break;
		case R.id.sbrRecallMinute:
			txtAmountOfMinute.setBackgroundResource(android.R.color.white);
			break;
	}
	}

	
	@Override
	public void onDestroy() {
		removeLayoutInScreen();
		super.onDestroy();
	}
	
	
	private void setTimeToRemine(){
		int minutesRemine = sbrMinute.getProgress();
		int hoursRemine = sbrHour.getProgress();

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
		String time = (h>9?h:"0"+h) + " : " + (min>9?min:"0"+min);
		int id = (int)System.currentTimeMillis();
		DataContactSharedPref saveTimeRecall = new DataContactSharedPref(this);
		Toast.makeText(this, "save: " + time, Toast.LENGTH_SHORT).show();
		saveTimeRecall.putData(phoneDisplayName, phoneNumber, "null", time, id);
		return id;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneDisplayName = intent.getStringExtra(CommonVL.PHONE_DISPLAY_NAME);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
}