package com.tmq.t3h.quicktask;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.tmq.t3h.quicktask.service.BtnOpen;


public class MyCallReceiver extends BroadcastReceiver {
	
	private static final String TAG = "MyCallReceiver";
	Intent intentSend = new Intent();
	private static String preState = TelephonyManager.EXTRA_STATE_IDLE;
	private static String currentState = TelephonyManager.EXTRA_STATE_IDLE;
	private static Date timeStartCall;
	private static boolean isIncoming;
	private static String phoneNumber = null;
	private static String phoneDisplayName = null;

	@Override
	public void onReceive(Context context, Intent intent) {
//		intentSend.setClass(context, MyService.class);
		intentSend.setClass(context, BtnOpen.class);
		
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			onNewOutGoingCall(context, intent);
		}
		else if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
			String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				onRinging(context, intent);
			}
			else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
				onOffhook(context);
			}
			else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				onIdle(context);
			}
		}
	}
	
	private void onNewOutGoingCall(Context context, Intent intent){
		currentState = Intent.ACTION_NEW_OUTGOING_CALL;
		phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		
		confirmContactInAddressBook(context);
		
		intentSend.putExtra(CommonVL.PHONE_DISPLAY_NAME, phoneDisplayName);
		intentSend.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intentSend.putExtra(CommonVL.PHONE_STATE, currentState);
		context.startService(intentSend);
	}
	
	private void onRinging(Context context, Intent intent){
		currentState = TelephonyManager.EXTRA_STATE_RINGING;
		phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		
		confirmContactInAddressBook(context);
		
		intentSend.putExtra(CommonVL.PHONE_DISPLAY_NAME, phoneDisplayName);
		intentSend.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intentSend.putExtra(CommonVL.PHONE_STATE, currentState);
		context.startService(intentSend);
	}
	
	private void onOffhook(Context context){
		currentState = TelephonyManager.EXTRA_STATE_OFFHOOK;
		intentSend.putExtra(CommonVL.PHONE_STATE, currentState);
	}
	
	private void onIdle(Context context){
		currentState = TelephonyManager.EXTRA_STATE_IDLE;
		intentSend.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intentSend.putExtra(CommonVL.PHONE_STATE, currentState);
		CommonVL.stopAllService(context);
		context.startService(intentSend);
	}

	private void confirmContactInAddressBook(Context context){
		if (phoneNumber==null) {
			Toast.makeText(context, "phoneNumber is null", Toast.LENGTH_SHORT).show();
			return;
		}
		String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + 
							" = '" + phoneNumber + "'";
		Cursor contact = context.getContentResolver()
				.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, null, Data.DISPLAY_NAME);
		contact.moveToFirst();
		
		if (contact.getCount()==0) {
			Toast.makeText(context, "Phone is not in Contact", Toast.LENGTH_SHORT).show();
			phoneDisplayName = CommonVL.CONTACT_IS_NOT_IN_DEVICE;
			return;
		}
		phoneDisplayName = contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//		Toast.makeText(context, "Name phone is " + phoneDisplayName, Toast.LENGTH_SHORT).show();
	}
}
