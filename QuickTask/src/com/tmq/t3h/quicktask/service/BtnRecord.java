package com.tmq.t3h.quicktask.service;

import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;

public class BtnRecord extends BtnComponent{

	@Override
	protected int setPosition() {
		return 400;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.ico_record;
	}
	
	@Override
	public void onClick(View v) {
		Toast.makeText(this, "Record", Toast.LENGTH_SHORT).show();
	}

}
