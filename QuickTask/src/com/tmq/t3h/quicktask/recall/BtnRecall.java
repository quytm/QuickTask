package com.tmq.t3h.quicktask.recall;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnComponent;

public class BtnRecall extends BtnComponent{
	boolean recallLaterBoxIsShow = false;

	@Override
	protected int setPosition() {
		return 820;
	}

	@Override
	protected int setBackgroundComponent() {
		return R.drawable.ico_recall;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, RecallLaterBox.class);
		startService(intent);
		if (!recallLaterBoxIsShow){
			startService(intent);
			recallLaterBoxIsShow = true;
		}else{
			stopService(intent);
			recallLaterBoxIsShow = false;
		}
	}
	
	@Override
	public void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(this, RecallLaterBox.class);
		stopService(intent);
		super.onDestroy();
	}

}
