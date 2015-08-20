package com.tmq.t3h.quicktask.mainui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainUI extends Activity {
	private static final String TAG = "MainUI";
	private NoteFragment noteFragment = new NoteFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_task_activity_main);
		
		Intent intent = new Intent();
		
//		intent.setClass(this, MyService.class);
//		startService(intent);
		
		intent.setClass(this, BtnOpen.class);
//		startService(intent);
		
		addAllFragments();
//		showNoteFragment();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void addAllFragments(){
//		getFragmentManager().beginTransaction().add(noteFragment, "note_fragment").commit();
		
		getFragmentManager().beginTransaction().add(android.R.id.content, noteFragment, "note_fragment").commit();
		Log.i(TAG, "finish commit fragment");
	}
	
	private void showNoteFragment(){
		Log.i(TAG, "commit Fragment");
		getFragmentManager().beginTransaction().replace(android.R.id.content, noteFragment).commit();
	}

}
