package com.tmq.t3h.quicktask.service;

import android.content.Intent;
import android.view.View;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;

public class BtnClose extends BtnComponent{

	@Override
	protected int setBackgroundComponent() {
		if (CommonVL.getDataSharePreferences(this).getModeHand() == CommonVL.MODE_HAND_LEFT)
			return R.drawable.btn_close_left;
		else return R.drawable.btn_close;
	}

	@Override
	public void onClick(View v) {
		// Stop all Button Service  
		Intent intent = new Intent();
		intent.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intent.putExtra(CommonVL.PHONE_STATE, phoneState);
		
		intent.setClass(this, BtnOpen.class);
		startService(intent);
		
		intent.setClass(this, MenuInCall.class);
		stopService(intent);
		
		// Stop by Itself
		stopSelf();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneState = intent.getStringExtra(CommonVL.PHONE_STATE);
		return super.onStartCommand(intent, flags, startId);
	}
}
