package com.tmq.t3h.quicktask.service;

import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;

public class BtnOpen extends BtnComponent implements OnLongClickListener{

	private static final String TAG = "BtnOpen";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Press and Hold to Exit", Toast.LENGTH_SHORT).show();
		btnComponent.setOnLongClickListener(this);
	}


	@Override
	protected int setPosition() {
		return 1200;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.btn_open;
	}

	@Override
	public void onClick(View v) {
//		Toast.makeText(this, "Open", Toast.LENGTH_SHORT).show();
		// Start Button Service
		Intent intent = new Intent();
		intent.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intent.putExtra(CommonVL.PHONE_STATE, phoneState);
		intent.putExtra(CommonVL.PHONE_DISPLAY_NAME, phoneDisplayName);
		
		intent.setClass(this, BtnClose.class);
		startService(intent);
		
		intent.setClass(this, MenuInCall.class);
		startService(intent);
		
		// Stop by Itself
		stopSelf();
	}
	
	@Override
	public boolean onLongClick(View v) {
		stopSelf();
		return true;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneState = intent.getStringExtra(CommonVL.PHONE_STATE);
		phoneDisplayName = intent.getStringExtra(CommonVL.PHONE_DISPLAY_NAME);
		if (phoneNumber==null) phoneNumber = "000 000 000";
		if (phoneState==null) phoneState = "Idle Idle Idle";
		return super.onStartCommand(intent, flags, startId);
	}
	
}
