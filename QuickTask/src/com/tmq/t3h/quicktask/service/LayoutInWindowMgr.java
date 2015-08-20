package com.tmq.t3h.quicktask.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.MyView;
import com.tmq.t3h.quicktask.R;

public abstract class LayoutInWindowMgr extends Service{
	private static final String TAG = "LayoutInWindowMgr";
	private LayoutInflater layoutInf;
	private MyView mViewContainer;
	private WindowManager mWindow;
	protected View mView;
	protected LayoutParams mParams;
	
	protected String phoneNumber;

	@Override
	public void onCreate() {
		Log.i(TAG, "Oncreate...");
		layoutInf = LayoutInflater.from(this);
		mViewContainer = new MyView(this);
		mView = layoutInf.inflate(setIdLayout(), mViewContainer);
		mWindow = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		// Get Params
		mParams = new LayoutParams();
//		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height = LayoutParams.WRAP_CONTENT;
//		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
//						LayoutParams.FLAG_NOT_FOCUSABLE |
//						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		setForLayoutParams();
		mParams.type = LayoutParams.TYPE_PHONE;
		mParams.format = PixelFormat.TRANSPARENT;
//		mParams.y = ;
		
		initViewsInLayout();
		
		mWindow.addView(mView, mParams);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mViewContainer!=null){
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mViewContainer);
			mViewContainer=null;
		}
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void updateView(){
		mWindow.updateViewLayout(mView, mParams);
	}
	
//------------------------------------------------
	protected abstract int setIdLayout();
	protected abstract void setForLayoutParams();
	protected abstract void initViewsInLayout();
}
