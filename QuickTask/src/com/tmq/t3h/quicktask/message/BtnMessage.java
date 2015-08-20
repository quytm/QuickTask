package com.tmq.t3h.quicktask.message;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnComponent;

public class BtnMessage extends BtnComponent{
	private static final String TAG = "BtnMessage";
	private boolean messageBoxIsShowed = false;

	@Override
	protected int setPosition() {
		return 960;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.ico_message;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, MessageBox.class);
		if (!messageBoxIsShowed){
			intent.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
			startService(intent);
			Log.i(TAG, "Start Service Message Box");
			messageBoxIsShowed = true;
		}
		else{
			stopService(intent);
			Log.i(TAG, "Stop Service Message Box");
			messageBoxIsShowed = false;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneState = intent.getStringExtra(CommonVL.PHONE_STATE);
		if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || 
						phoneState.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			lockBtnComponent();
		}
		else {
			unlockBtnComponent();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(this, MessageBox.class);
		stopService(intent);
		super.onDestroy();
	}

}
