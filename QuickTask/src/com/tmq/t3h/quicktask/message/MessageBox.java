package com.tmq.t3h.quicktask.message;

import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;

public class MessageBox extends LayoutInWindowMgr implements OnItemClickListener{
	
	private static final String TAG = "MessageBox";
	
	private EditText edtWriteMessage;
	private Button btnSendMessage;
	private ListView listMessage;
	private AdapterMessage adapter;

	@Override
	public void onCreate() {
		adapter = new AdapterMessage(this);
		super.onCreate();
	}
	
	@Override
	protected int setIdLayout() {
		return R.layout.message_list;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_TOUCH_MODAL |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
	}

	@Override
	protected void initViewsInLayout() {
		listMessage = (ListView) mView.findViewById(R.id.listMessage);
		listMessage.setAdapter(adapter);
		listMessage.setOnItemClickListener(this);
		
		edtWriteMessage = (EditText) mView.findViewById(R.id.edtWriteMessage);
		btnSendMessage = (Button) mView.findViewById(R.id.btnSendMessage);
		btnSendMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = edtWriteMessage.getText().toString();
				sendMessage(content);
			}
		});
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
