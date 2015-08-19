package com.tmq.t3h.quicktask.service;

import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;

public class BtnContact extends BtnComponent{

	@Override
	protected int setPosition() {
		return 540;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.ico_contact;
	}
	
	@Override
	public void onClick(View v) {
		Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
	}


}
