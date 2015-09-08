package com.tmq.t3h.quicktask.note;

import java.util.Calendar;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;
import com.tmq.t3h.quicktask.service.MenuInCall;

public class NoteBox extends LayoutInWindowMgr implements OnClickListener, OnTouchListener{
	private static final String TAG = "NoteBox";
	private EditText edtNoteContent;
	private Button btnSaveNote;
	
	private float 	xDown, yDown,		// Luu vi tri cua ngon tay khi bat dau cham vao man hinh
					xPre, yPre;			// Luu vi tri cua Layout khi bat dau cham vao man hinh
	
	@Override
	protected int setIdLayout() {
		return R.layout.note_box;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.CENTER_VERTICAL;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_TOUCH_MODAL |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.y = 100;
	}

	@Override
	protected void initViewsInLayout() {
		edtNoteContent = (EditText) mView.findViewById(R.id.edtNoteContent);
		btnSaveNote = (Button) mView.findViewById(R.id.btnSaveNote);
		btnSaveNote.setOnClickListener(this);
		
		Button btnCloseNote = (Button) mView.findViewById(R.id.btnCloseNote);
		btnCloseNote.setOnClickListener(this);
		
		mView.setOnTouchListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// if Click then NoteContent will be save in SharePreferent
		switch (v.getId()) {
		case R.id.btnCloseNote:
			break;
		case R.id.btnSaveNote:
			if (!saveNote()) return;	// If note is not saved, function will be exit, then NoteBox is not destroyed
			break;
		}
		Intent intent = new Intent(this, MenuInCall.class);
		intent.putExtra(CommonVL.NOTI_STATE_BOX, CommonVL.BOX_NOT_SHOWED);
		startService(intent);
		stopSelf();	// Hide NoteBox (Destroy)
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			xPre = mParams.x;
			yPre = mParams.y;
			break;
		case MotionEvent.ACTION_MOVE:
			float xMove = event.getRawX() - xDown;
			float yMove = event.getRawY() - yDown;
			mParams.x = (int)(xPre + xMove);
			mParams.y = (int)(yPre + yMove);
		}
		updateView();
		return true;
	}
	
	private boolean saveNote(){
		String content = edtNoteContent.getText().toString();
		Log.i(TAG, content);
		if (content.length()==0){
			Toast.makeText(this, "Textbox is empty", Toast.LENGTH_SHORT).show();
			return false;		// Note is not saved 
		}
		
		
		Calendar calendar = Calendar.getInstance();
		int minutes = calendar.get(Calendar.MINUTE);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		String time = (hours>9?hours:"0"+hours) + " : " + (minutes>9?minutes:"0"+minutes);
		DataContactSharedPref saveNote = new DataContactSharedPref(this);
		saveNote.putData(phoneDisplayName, phoneNumber, content, time, -1);
		Toast.makeText(this, "Note is saved: " + content, Toast.LENGTH_SHORT).show();
		return true;			// Note is saved
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneDisplayName = intent.getStringExtra(CommonVL.PHONE_DISPLAY_NAME);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		removeLayoutInScreen();
		super.onDestroy();
	}
}
