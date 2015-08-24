package com.tmq.t3h.quicktask.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.tmq.t3h.quicktask.MyView;
import com.tmq.t3h.quicktask.R;

public abstract class BtnComponent extends Service implements OnClickListener{
	private LayoutInflater layoutInf;
	private MyView mViewContainer;
	private WindowManager mWindow;
	private View mView;
	private LayoutParams mParams;
	
	protected Button btnComponent;
	protected boolean lockButton = false;
	
	protected String phoneNumber = "000 000 000";
	protected String phoneState = "Idle Idle Idle";
	
	@Override
	public void onCreate() {
		layoutInf = LayoutInflater.from(this);
		mViewContainer = new MyView(this);
		mView = layoutInf.inflate(R.layout.btn_component, mViewContainer);
		mWindow = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		// Get Params
		mParams = new LayoutParams();
		mParams.gravity = Gravity.END | Gravity.TOP;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height = LayoutParams.WRAP_CONTENT;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_FOCUSABLE |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.type = LayoutParams.TYPE_PHONE;
		mParams.y = setPosition();
		mParams.format = PixelFormat.TRANSPARENT;
		
		// Get View
		btnComponent = (Button) mView.findViewById(R.id.btnComponent);
		btnComponent.setOnClickListener(this);
		btnComponent.setBackgroundResource(setBackgroundComponent());
		
		Animation myAni = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_bottom);
		btnComponent.startAnimation(myAni);
		
		mWindow.addView(mView, mParams);
		
	}
	//-----------------------------
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mViewContainer!=null){
			Animation myAni = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_bottom);
			btnComponent.clearAnimation();
			btnComponent.startAnimation(myAni);
			
			btnComponent.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mViewContainer);
					mViewContainer=null;
				}
			}, myAni.getDuration());
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	protected void lockBtnComponent(){
		btnComponent.setAlpha(0.3f);
		btnComponent.setClickable(false);
	}
	
	protected void unlockBtnComponent(){
		btnComponent.setAlpha(1.0f);
		btnComponent.setClickable(true);
	}

	//-------------------------------------------------
	protected abstract int setPosition();	// Set position for LayoutParams
	protected abstract int setBackgroundComponent();	// Set Background for Button in Component
}
