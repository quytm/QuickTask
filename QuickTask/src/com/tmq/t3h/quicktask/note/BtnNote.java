package com.tmq.t3h.quicktask.note;

import android.content.Intent;
import android.view.View;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnComponent;

public class BtnNote extends BtnComponent{

	@Override
	protected int setPosition() {
		return 680;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.ico_note;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, NoteBox.class);
		startService(intent);
	}

	@Override
	public void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(this, NoteBox.class);
//		stopService(intent);
		super.onDestroy();
	}
}
