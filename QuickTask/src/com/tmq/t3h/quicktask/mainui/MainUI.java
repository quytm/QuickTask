package com.tmq.t3h.quicktask.mainui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.optionmenu.AdapterForMenuHelp;
import com.tmq.t3h.quicktask.optionmenu.SettingActivity;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class MainUI extends Activity {
	private static final String TAG_NOTE = "tag_note";
	private static final String TAG_RECALL = "tag_recall";
	private static final String TAG_RECORD = "tag_record";
	private static final String TAG = "MainUI";
	
	private ActionBar actionBar;
	private int tabID;
	private Tab tab1, tab2, tab3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_task_activity_main);

		DataContactSharedPref data = new DataContactSharedPref(this);
		data.settingDefault();
		
		setTab();
	}
	
	private void setTab(){
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		tab1 = actionBar.newTab();
		tab1.setIcon(getResources().getDrawable(R.drawable.ico_note_blue));
		TabListener<NoteFragment> t1 = new TabListener<NoteFragment>(this, TAG_NOTE, NoteFragment.class);
		tab1.setTabListener(t1);
		actionBar.addTab(tab1);
		
		tab2 = actionBar.newTab();
		tab2.setIcon(getResources().getDrawable(R.drawable.ico_recall_gray));
		TabListener<RecallFragment> t2 = new TabListener<RecallFragment>(this, TAG_RECALL, RecallFragment.class);
		tab2.setTabListener(t2);
		actionBar.addTab(tab2);
		
		tab3 = actionBar.newTab();
		tab3.setIcon(getResources().getDrawable(R.drawable.ico_record_gray));
		TabListener<RecordFragment> t3 = new TabListener<RecordFragment>(this, TAG_RECORD, RecordFragment.class);
		tab3.setTabListener(t3);
		actionBar.addTab(tab3);
	}
	
	@Override
	protected void onStart() {
		Intent intent = new Intent();
		tabID = intent.getIntExtra(CommonVL.TAB_ID, -1);
		Toast.makeText(this, "tab_id = " + tabID, Toast.LENGTH_SHORT).show();
		switch (tabID) {
		case 2:
			actionBar.selectTab(tab2);
			break;

		default:
			break;
		}
		super.onStart();
	}
	
	
	//--------------------------- Class: TabListener -----------------------------------
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
	

	
	//------------------ Menu ----------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_menu_demo:
			Intent intent = new Intent(this, BtnOpen.class);
			startService(intent);
			break;
		case R.id.action_menu_settings:
			Intent intentSetting = new Intent(this, SettingActivity.class);
			startActivity(intentSetting);
			break;
		case R.id.action_menu_help:
			showMenuHelpDialog();
			break;
		case R.id.action_menu_about:
			showMenuAboutDialog();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private Dialog dialog;
	private void showMenuAboutDialog(){
		dialog = new Dialog(this, R.style.LargeDialog);
		dialog.setTitle(R.string.action_menu_about);
		
		dialog.setContentView(R.layout.menu_about);
		
		dialog.show();
		
	}
	
	private void showMenuHelpDialog(){
		dialog = new Dialog(this, R.style.LargeDialog);
		dialog.setTitle(R.string.action_menu_help);
		
		LayoutInflater lf = dialog.getLayoutInflater();
		View view = lf.inflate(R.layout.help_main_ui, null);
		ViewPager vpr = (ViewPager) view.findViewById(R.id.helpViewPager);
		vpr.setAdapter(new AdapterForMenuHelp(this));
		
		dialog.setContentView(view);
		
		dialog.show();
		
	}
	
	
}
