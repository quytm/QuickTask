package com.tmq.t3h.quicktask.message;

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

public class MessageBox extends Service implements OnItemClickListener{
	private static final String TAG = "MessageBox";
	private LayoutInflater layoutInf;
	private MyView mViewContainer;
	private WindowManager mWindow;
	private View mView;
	private LayoutParams mParams;
	
	private ListView listMessage;
	private AdapterMessage adapter;
	
	private String phoneNumber;

	@Override
	public void onCreate() {
		Log.i(TAG, "Oncreate...");
		layoutInf = LayoutInflater.from(this);
		mViewContainer = new MyView(this);
		mView = layoutInf.inflate(R.layout.message_list, mViewContainer);
		mWindow = (WindowManager) getSystemService(WINDOW_SERVICE);
		adapter = new AdapterMessage(this);
		
		// Get Params
		mParams = new LayoutParams();
		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height = LayoutParams.WRAP_CONTENT;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_FOCUSABLE |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.type = LayoutParams.TYPE_PHONE;
		mParams.format = PixelFormat.TRANSPARENT;
//		mParams.y = ;
		
		Log.i(TAG, "Get view");
		// Get View
		listMessage = (ListView) mView.findViewById(R.id.listMessage);
		listMessage.setAdapter(adapter);
		listMessage.setOnItemClickListener(this);
		
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
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		sendMessage(adapter.getItem(position));
	}
	
	private void sendMessage(String message){
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
		Log.i(TAG, "Complete:\tPhone: " + phoneNumber + "\tMsg: " + message);
		Toast.makeText(this, "Send SMS to " + phoneNumber + " complete!", Toast.LENGTH_SHORT).show();
	}

	
}
