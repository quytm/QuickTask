package com.tmq.t3h.quicktask.service;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.message.BtnMessage;

public class BtnClose extends BtnComponent{

	@Override
	protected int setPosition() {
		return 1200;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.btn_close;
	}

	@Override
	public void onClick(View v) {
//		Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
		// Stop all Button Service  
		Intent intent = new Intent();
		
		intent.setClass(this, BtnOpen.class);
		intent.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intent.putExtra(CommonVL.PHONE_STATE, phoneState);
		startService(intent);
		
		intent.setClass(this, BtnMessage.class);
		stopService(intent);
		
		intent.setClass(this, BtnRecall.class);
		stopService(intent);

		intent.setClass(this, BtnNote.class);
		stopService(intent);
		
		intent.setClass(this, BtnContact.class);
		stopService(intent);
		
		intent.setClass(this, BtnRecord.class);
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
