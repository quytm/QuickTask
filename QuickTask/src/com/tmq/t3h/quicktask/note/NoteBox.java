package com.tmq.t3h.quicktask.note;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;

public class NoteBox extends LayoutInWindowMgr implements OnClickListener, OnTouchListener{
	private static final String TAG = "NoteBox";
	private EditText edtNoteContent;
	private Button btnSaveNote;
	
	private float 	xDown, yDown,		// Luu vi tri cua ngon tay khi bat dau cham vao man hinh
					xPre, yPre;			// Luu vi tri cua Layout khi bat dau cham vao man hinh
	
	private SharedPreferences noteSharePref;
	
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
			stopSelf();
			break;
		case R.id.btnSaveNote:
			saveNote();
			break;
		}
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
	
	private void saveNote(){
		String content = edtNoteContent.getText().toString();
		Log.i(TAG, content);
		if (content.length()==0){
			Toast.makeText(this, "Textbox is empty", Toast.LENGTH_SHORT).show();
			return;
		}
		
 		noteSharePref = getSharedPreferences(CommonVL.NOTE_SHAREPREFERENCES, Context.MODE_PRIVATE);
		
		int numberNote = noteSharePref.getInt(CommonVL.NUMBER_NOTE, 0) + 1;
		String keyNote = CommonVL.NOTE_ + numberNote;
		
		SharedPreferences.Editor editor = noteSharePref.edit();
		editor.putString(keyNote, content);
		editor.remove(CommonVL.NUMBER_NOTE);
		editor.putInt(CommonVL.NUMBER_NOTE, numberNote);
		
		editor.commit();
		
		Toast.makeText(this, "Save note_" + numberNote + ": " + noteSharePref.getString(keyNote, "null"), Toast.LENGTH_SHORT).show();
		
	}
}
