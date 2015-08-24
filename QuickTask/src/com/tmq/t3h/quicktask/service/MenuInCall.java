package com.tmq.t3h.quicktask.service;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.contact.ContactBox;
import com.tmq.t3h.quicktask.message.MessageBox;
import com.tmq.t3h.quicktask.note.NoteBox;
import com.tmq.t3h.quicktask.recall.RecallLaterBox;

public class MenuInCall extends LayoutInWindowMgr implements OnClickListener{
	private static final String TAG = "MenuInCall";

	private Button 	btnMessage,
					btnRecall,
					btnNote,
					btnContact,
					btnRecord;
	
	private int boxIsShow = CommonVL.BOX_NOT_SHOWED;

	@Override
	protected int setIdLayout() {
		return R.layout.menu_in_call;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.END | Gravity.TOP;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_FOCUSABLE |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.y = CommonVL.getHeightScreen(this) - 800;
	}

	//--------------------------- Get View --------------------------------------------
	@Override
	protected void initViewsInLayout() {
		// Get from Layout
		btnMessage 	= (Button) mView.findViewById(R.id.btnMessageNew);
		btnRecall 	= (Button) mView.findViewById(R.id.btnRecallNew);
		btnNote 	= (Button) mView.findViewById(R.id.btnNoteNew);
		btnContact 	= (Button) mView.findViewById(R.id.btnContactNew);
		btnRecord 	= (Button) mView.findViewById(R.id.btnRecordNew);
		// Set Animation
		startAnimationForAllButton(R.anim.anim_slide_in_bottom);
		//Set onClick
		btnMessage.setOnClickListener(this);
		btnRecall.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnContact.setOnClickListener(this);
		btnRecord.setOnClickListener(this);
	}
	
	private void startAnimationForAllButton(int IDAnimation){
		Animation myAni = AnimationUtils.loadAnimation(this, IDAnimation);
		btnMessage.startAnimation(myAni);
		btnRecall.startAnimation(myAni);
		btnNote.startAnimation(myAni);
		btnContact.startAnimation(myAni);
		btnRecord.startAnimation(myAni);
	}
	
	//--------------------------- Click Event ------------------------------------------
	@Override
	public void onClick(View v) {
		int box = hideCurrentBox();
		if (box == numberOfBox(v.getId())){
			boxIsShow = CommonVL.BOX_NOT_SHOWED;
			return;
		}
		
		Intent intent = new Intent();
		intent.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intent.putExtra(CommonVL.PHONE_STATE, phoneState);
		
		switch (v.getId()) {
			case R.id.btnMessageNew:
				intent.setClass(this, MessageBox.class);
				boxIsShow = CommonVL.MESSAGE_BOX_SHOWED;
				break;
			case R.id.btnRecallNew:
				intent.setClass(this, RecallLaterBox.class);
				boxIsShow = CommonVL.RECALL_BOX_SHOWED;
				break;
			case R.id.btnNoteNew:
				intent.setClass(this, NoteBox.class);
				boxIsShow = CommonVL.NOTE_BOX_SHOWED;
				break;
			case R.id.btnContactNew:
				intent.setClass(this, ContactBox.class);
				boxIsShow = CommonVL.CONTACT_BOX_SHOWED;
				break;
			case R.id.btnRecordNew:
				Toast.makeText(this, "Record", Toast.LENGTH_SHORT).show();
				boxIsShow = CommonVL.RECORD_BOX_SHOWED;
				break;
		}
		startService(intent);
	}
	
	private int hideCurrentBox(){
//		Log.i(TAG, "BoxIsShow = " + boxIsShow);
		if (boxIsShow==CommonVL.BOX_NOT_SHOWED) return CommonVL.BOX_NOT_SHOWED;
		
		int valueReturn = CommonVL.BOX_NOT_SHOWED;
		Intent intent = new Intent();
		switch (boxIsShow) {
			case CommonVL.MESSAGE_BOX_SHOWED:
				valueReturn = CommonVL.MESSAGE_BOX_SHOWED;
				intent.setClass(this, MessageBox.class);
				break;
			case CommonVL.RECALL_BOX_SHOWED:
				valueReturn = CommonVL.RECALL_BOX_SHOWED;
				intent.setClass(this, RecallLaterBox.class);
				break;
			case CommonVL.NOTE_BOX_SHOWED:
				valueReturn = CommonVL.NOTE_BOX_SHOWED;
				intent.setClass(this, NoteBox.class);
				break;
			case CommonVL.CONTACT_BOX_SHOWED:
				valueReturn = CommonVL.CONTACT_BOX_SHOWED;
				intent.setClass(this, ContactBox.class);
				break;
			case CommonVL.RECORD_BOX_SHOWED:
//				valueReturn = CommonVL.RECALL_BOX_SHOWED;
	//			intent.setClass(this, .class);
				break;
		}
		stopService(intent);
		return valueReturn;
	}

	// Return number of box: number is define in CommonVL
	private int numberOfBox(int id){
		switch (id) {
			case R.id.btnMessageNew:
				return CommonVL.MESSAGE_BOX_SHOWED;
			case R.id.btnRecallNew:
				return CommonVL.RECALL_BOX_SHOWED;
			case R.id.btnNoteNew:
				return CommonVL.NOTE_BOX_SHOWED;
			case R.id.btnContactNew:
				return CommonVL.CONTACT_BOX_SHOWED;
			case R.id.btnRecordNew:
				return CommonVL.RECORD_BOX_SHOWED;
			default:
				return CommonVL.BOX_NOT_SHOWED;
		}
		
	}
	
	//----------------------------------- Other -------------------------------------------
	@Override
	public void onDestroy() {
		hideCurrentBox();
		Animation myAni = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_bottom);
		startAnimationForAllButton(R.anim.anim_slide_out_bottom);
		btnRecord.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				removeLayoutInScreen();
			}
		}, myAni.getDuration());
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		phoneNumber = intent.getStringExtra(CommonVL.PHONE_NUMBER);
		phoneState = intent.getStringExtra(CommonVL.PHONE_STATE);
		
		return super.onStartCommand(intent, flags, startId);
	}

}
