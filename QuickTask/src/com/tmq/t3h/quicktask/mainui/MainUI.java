package com.tmq.t3h.quicktask.mainui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainUI extends FragmentActivity {
	private static final String TAG = "MainUI";
	
	private ViewPager viewPager;
	private AdapterForFragment adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_task_activity_main);
		
		Intent intent = new Intent();
		
//		intent.setClass(this, MyService.class);
//		startService(intent);
		
		intent.setClass(this, BtnOpen.class);
		startService(intent);
		
		initViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void initViews(){
		adapter = new AdapterForFragment(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.viewPageMain);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
	}

}
