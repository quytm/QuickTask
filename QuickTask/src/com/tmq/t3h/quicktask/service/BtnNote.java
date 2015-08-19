package com.tmq.t3h.quicktask.service;

import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;

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
		Toast.makeText(this, "Note", Toast.LENGTH_SHORT).show();
	}

}
