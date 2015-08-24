package com.tmq.t3h.quicktask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.MyView;
import com.tmq.t3h.quicktask.R;

public class MyService extends Service implements OnClickListener, OnTouchListener{
	private static final String TAG = "MyService...";
	private LayoutInflater layoutInf;
	private MyView mViewContainer;
	private WindowManager mWindow;
	private View mView;
	private LayoutParams mParams;
	
	private float 	xStart, yStart,
					deltaX, deltaY,
					prePositionX, prePositionY;
	
	private Button btnSend, btnClose;
	private EditText edtMessage;
	private TextView txtContent;
	
	@Override
	public void onCreate() {
		layoutInf = LayoutInflater.from(this);
		mViewContainer = new MyView(this);
		mView = layoutInf.inflate(R.layout.my_view, mViewContainer);
		mWindow = (WindowManager) getSystemService(WINDOW_SERVICE);
		// Set Params for View
		mParams = new LayoutParams();
		// Center
		mParams.gravity = Gravity.CENTER;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height= LayoutParams.WRAP_CONTENT;
		mParams.flags = 
						LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
//						LayoutParams.FLAG_NOT_FOCUSABLE|
						LayoutParams.FLAG_NOT_TOUCH_MODAL |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		
		mParams.type = LayoutParams.TYPE_PHONE;
		
		getViewContainer();
		
		mView.setOnTouchListener(this);
		mWindow.addView(mView, mParams);
		
		Log.i(TAG, "Create.................");
	}
	
	private void getViewContainer(){
		btnSend = (Button) mView.findViewById(R.id.btnSend);
		btnClose= (Button) mView.findViewById(R.id.btnClose1);
		edtMessage = (EditText) mView.findViewById(R.id.edtMessage);
		txtContent = (TextView) mView.findViewById(R.id.txtContent);
		btnSend.setOnClickListener(this);
		btnClose.setOnClickListener(this);
	}
	
	public void updateView(){
		mWindow.updateViewLayout(mView, mParams);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnSend){
			String content = ""+txtContent.getText().toString();
			if (edtMessage.getText().toString().length()==0) return;
			content += (edtMessage.getText().toString() + "\n");
			txtContent.setText(content);
			edtMessage.setText("");
		}
		else if (v.getId()==R.id.btnClose1){
			Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
			final int heightScreen = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay().getHeight();
			Log.i(TAG, "height = " + heightScreen);
			Log.i(TAG, "y = " + mParams.y);
			while (mParams.y<heightScreen){
				mParams.y += 1;
				Log.i(TAG, "y = " + mParams.y);
				updateView();
			}
			stopSelf();
		}
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG,"Destroy...............");
		super.onDestroy();
		if (mViewContainer!=null){
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mViewContainer);
			mViewContainer=null;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			Toast.makeText(this, "ACTION DOWN", Toast.LENGTH_SHORT).show();
			xStart = event.getRawX();
			yStart = event.getRawY();
			prePositionX = mParams.x;
			prePositionY = mParams.y;
			Log.i(TAG, "Down........................");
			break;
		case MotionEvent.ACTION_MOVE:
//			Toast.makeText(this, "ACTION MOVE", Toast.LENGTH_SHORT).show();
			deltaX = event.getRawX() - xStart;
			deltaY = event.getRawY() - yStart;
			mParams.x = (int) (prePositionX + deltaX);
			mParams.y = (int) (prePositionY + deltaY);
//			Log.i(TAG, "" + event.getX() + "......." + event.getY());
			updateView();
			break;
		case MotionEvent.ACTION_UP:
			Toast.makeText(this, "ACTION UP", Toast.LENGTH_SHORT).show();
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Toast.makeText(this, "ACTION OUTSIDE", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
		return true;
	}
	
}
