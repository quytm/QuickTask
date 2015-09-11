package com.tmq.t3h.quicktask.mainui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainUI extends Activity {
	private static final String TAG_NOTE = "tag_note";
	private static final String TAG_RECALL = "tag_recall";
	private static final String TAG_RECORD = "tag_record";
	
	private static final String TAG = "MainUI";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_task_activity_main);
		
		ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab = actionBar.newTab();
		tab.setIcon(getResources().getDrawable(R.drawable.ico_note_blue));
		TabListener<NoteFragment> t1 = new TabListener<NoteFragment>(this, TAG_NOTE, NoteFragment.class);
		tab.setTabListener(t1);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab();
		tab.setIcon(getResources().getDrawable(R.drawable.ico_recall_gray));
		TabListener<RecallFragment> t2 = new TabListener<RecallFragment>(this, TAG_RECALL, RecallFragment.class);
		tab.setTabListener(t2);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab();
		tab.setIcon(getResources().getDrawable(R.drawable.ico_record_gray));
		TabListener<RecordFragment> t3 = new TabListener<RecordFragment>(this, TAG_RECORD, RecordFragment.class);
		tab.setTabListener(t3);
		actionBar.addTab(tab);
		
	}

	private class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private android.app.Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		
		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(mFragment == null){
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				ft.attach(mFragment);
			}
			if (mTag.equals(TAG_NOTE)){		tab.setIcon(R.drawable.ico_note_blue);		return;}
			if (mTag.equals(TAG_RECALL)){	tab.setIcon(R.drawable.ico_recall_blue);	return;}
			if (mTag.equals(TAG_RECORD)){	tab.setIcon(R.drawable.ico_record_blue);	return;}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(mFragment != null){
				ft.detach(mFragment);
			}
			
			if (mTag.equals(TAG_NOTE)){		tab.setIcon(R.drawable.ico_note_gray);		return;}
			if (mTag.equals(TAG_RECALL)){	tab.setIcon(R.drawable.ico_recall_gray);	return;}
			if (mTag.equals(TAG_RECORD)){	tab.setIcon(R.drawable.ico_record_gray);	return;}
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_demo:
			Toast.makeText(this, "Demo", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, BtnOpen.class);
			startService(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
