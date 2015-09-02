package com.tmq.t3h.quicktask;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.WindowManager;

import com.tmq.t3h.quicktask.service.BtnClose;
import com.tmq.t3h.quicktask.service.BtnOpen;

public class CommonVL {

	// Phone Call
	public static final String PHONE_NUMBER = "quicktask.phone_number";
	public static final String PHONE_STATE = "quicktask.phone_state";
	public static final String PHONE_DISPLAY_NAME = "quicktack.phone_display_name";
	public static final String CONTACT_IS_NOT_IN_DEVICE = "contact_is_not_in_device";
	
	
	// SharedPreferences
	public static final int NUMBER_DATA_CONTACT_MAX = 999;
	public static final String NOTE_RECALL_SHAREPREFERENCES = "data_note_recall_sharepreferences";
	public static final String DATA_CONTACT_SHAREPREFERENCES = "data_contact_recall_sharepreferences";
	
	public static final String NUMBER_DATA_CONTACT 	= "data_contact";
	public static final String CONTACT_NAME_ 		= "contact_name";
	public static final String CONTACT_PHONE_NUMBER_= "contact_phone_number";
	public static final String CONTACT_NOTE_ 		= "contact_note";
	public static final String CONTACT_TIME_START_ 	= "contact_time_start";
	public static final String CONTACT_RECALL_ID_ 	= "contact_recall_id";
	
	// State of box
	public static final int BOX_NOT_SHOWED 		= 0;
	public static final int MESSAGE_BOX_SHOWED 	= 1;
	public static final int RECALL_BOX_SHOWED 	= 2;
	public static final int NOTE_BOX_SHOWED 	= 3;
	public static final int CONTACT_BOX_SHOWED 	= 4;
	public static final int RECORD_BOX_SHOWED 	= 5;
	public static final String NOTI_STATE_BOX	= "notification_state_of_box";
	
	
	
	
	
	
	
	public static void stopAllService(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, BtnOpen.class);
		context.stopService(intent);
		
		intent.setClass(context, BtnClose.class);
		context.stopService(intent);
		
//		intent.setClass(context, BtnMessage.class);
//		context.stopService(intent);
//		
//		intent.setClass(context, BtnRecall.class);
//		context.stopService(intent);
//
//		intent.setClass(context, BtnNote.class);
//		context.stopService(intent);
		
//		intent.setClass(context, BtnContact.class);
//		context.stopService(intent);
		
//		intent.setClass(context, BtnRecord.class);
//		context.stopService(intent);
	}
	
	public static int getWidthScreen(Context context){
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		return display.getWidth();
	}
	
	public static int getHeightScreen(Context context){
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		return display.getHeight();
	}

}
