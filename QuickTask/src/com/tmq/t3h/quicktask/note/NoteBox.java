package com.tmq.t3h.quicktask.note;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;

public class NoteBox extends LayoutInWindowMgr implements OnClickListener, OnTouchListener{
	private EditText edtNoteContent;
	private Button btnSaveNote;
	
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
			stopSelf();
			break;
		case R.id.btnSaveNote:
			String content = edtNoteContent.getText().toString();
			Toast.makeText(this, "SAVE: " + content, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	float 	xDown, yDown,
			xPre, yPre;
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
}