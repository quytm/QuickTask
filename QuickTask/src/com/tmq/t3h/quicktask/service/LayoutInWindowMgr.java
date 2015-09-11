package com.tmq.t3h.quicktask.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.tmq.t3h.quicktask.MyView;

public abstract class LayoutInWindowMgr extends Service{
	private LayoutInflater layoutInf;
	protected MyView mViewContainer;
	private WindowManager mWindow;
	protected View mView;
	protected LayoutParams mParams;
	
	protected String phoneNumber = "000 000 000";
	protected String phoneState = "Idle Idle Idle";
	protected String phoneDisplayName = "No name";

	@Override
	public void onCreate() {
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
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void updateView(){
		mWindow.updateViewLayout(mView, mParams);
	}
	
	protected void removeLayoutInScreen(){
		if (mViewContainer!=null){
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mViewContainer);
			mViewContainer=null;
		}
	}
	
//------------------------------------------------
	protected abstract int setIdLayout();
	protected abstract void setForLayoutParams();
	protected abstract void initViewsInLayout();
}
