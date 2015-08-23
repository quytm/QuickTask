package com.tmq.t3h.quicktask.contact;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnComponent;

public class BtnContact extends BtnComponent{
	private boolean contactBoxIsShow = false;

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
		Intent intent = new Intent();
		intent.setClass(this, ContactBox.class);
		if (!contactBoxIsShow){
			startService(intent);
			contactBoxIsShow = true;
		}else{
			stopService(intent);
			contactBoxIsShow = false;
		}
	}

	@Override
	public void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(this, ContactBox.class);
		stopService(intent);
		super.onDestroy();
	}

}
