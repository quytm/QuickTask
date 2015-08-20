package com.tmq.t3h.quicktask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent();
		
//		intent.setClass(this, MyService.class);
//		startService(intent);
		
		intent.setClass(this, BtnOpen.class);
		startService(intent);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
