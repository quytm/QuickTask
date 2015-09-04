package com.tmq.t3h.quicktask.mainui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainUI extends Activity {
	private static final String TAG = "MainUI";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_task_activity_main);
		
		Intent intent = new Intent(this, BtnOpen.class);
		startService(intent);
	
		ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
//		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_layout));
//		actionBar.set
		
		Tab tab = actionBar.newTab();
		tab.setIcon(getResources().getDrawable(R.drawable.ico_note));
		TabListener<NoteFragment> t1 = new TabListener<NoteFragment>(this, "Note", NoteFragment.class);
		tab.setTabListener(t1);
		tab.setText("Note");
		actionBar.addTab(tab);
		
		tab = actionBar.newTab();
		tab.setIcon(getResources().getDrawable(R.drawable.ico_recall));
		TabListener<RecallFragment> t2 = new TabListener<RecallFragment>(this, "Recall", RecallFragment.class);
		tab.setTabListener(t2);
		tab.setText("Recall");
		actionBar.addTab(tab);
		
	}

	private class TabListener<T extends android.app.Fragment> implements ActionBar.TabListener {

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
			if(mFragment == null)
			{
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				ft.attach(mFragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(mFragment != null)
			{
				ft.detach(mFragment);
			}
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
