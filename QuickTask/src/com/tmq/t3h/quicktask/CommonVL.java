package com.tmq.t3h.quicktask;

import android.content.Context;
import android.content.Intent;

import com.tmq.t3h.quicktask.contact.BtnContact;
import com.tmq.t3h.quicktask.message.BtnMessage;
import com.tmq.t3h.quicktask.note.BtnNote;
import com.tmq.t3h.quicktask.recall.BtnRecall;
import com.tmq.t3h.quicktask.service.BtnClose;
import com.tmq.t3h.quicktask.service.BtnOpen;
import com.tmq.t3h.quicktask.service.BtnRecord;

public class CommonVL {

	public static final String PHONE_NUMBER = "quicktask.phone_number";
	public static final String PHONE_STATE = "quicktask.phone_state";
	public static final String NOTE_SHAREPREFERENCES = "data_note_sharepreferences";
	public static final String NUMBER_NOTE = "number_note";
	public static final String NOTE_ = "note_";
	
	
	public static void stopAllService(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, BtnOpen.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnClose.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnMessage.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnRecall.class);
		context.stopService(intent);

		intent.setClass(context, BtnNote.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnContact.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnRecord.class);
		context.stopService(intent);
	}

}
