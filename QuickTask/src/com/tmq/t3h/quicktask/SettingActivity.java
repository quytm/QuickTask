package com.tmq.t3h.quicktask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends Activity{
	
	private ToggleButton toggleButton;
	private DataContactSharedPref data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_setting);
		
		initViews();
	}
	
	private void initViews(){
		data = new DataContactSharedPref(this);
		
		// Toggle button
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		if (data.getModeHand() == CommonVL.MODE_HAND_LEFT) toggleButton.setChecked(false);
		else toggleButton.setChecked(true);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					data.setModUseHand(CommonVL.MODE_HAND_RIGHT);
					Toast.makeText(buttonView.getContext(), "Right hand", Toast.LENGTH_SHORT).show();
				}
				else{
					data.setModUseHand(CommonVL.MODE_HAND_LEFT);
					Toast.makeText(buttonView.getContext(), "Left hand", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
