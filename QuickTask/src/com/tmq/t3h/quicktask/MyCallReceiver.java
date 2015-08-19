package com.tmq.t3h.quicktask;
import java.util.Date;

import com.tmq.t3h.quicktask.service.BtnOpen;
import com.tmq.t3h.quicktask.service.MyService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class MyCallReceiver extends BroadcastReceiver {
	
	private static final String TAG = "MyCallReceiver";
	Intent intentSend = new Intent();
	private static String preState = TelephonyManager.EXTRA_STATE_IDLE;
	private static String currentState = TelephonyManager.EXTRA_STATE_IDLE;
	private static Date timeStartCall;
	private static boolean isIncoming;
	private static String phoneNumber = null;

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
		intentSend.putExtra(CommonVL.PHONE_NUMBER, phoneNumber);
		intentSend.putExtra(CommonVL.PHONE_STATE, currentState);
		context.startService(intentSend);
	}
	
	private void onRinging(Context context, Intent intent){
		currentState = TelephonyManager.EXTRA_STATE_RINGING;
		phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		Intent intentSend = new Intent();
//        intentSend.setClass(context, MyService.class);
//		if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
//				TelephonyManager.EXTRA_STATE_RINGING)) {
//             
//            // get the phone number 
////            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
////            Toast.makeText(context, "Call from:" +incomingNumber, Toast.LENGTH_LONG).show();
//            
//            context.startService(intentSend);
//        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
//        		TelephonyManager.EXTRA_STATE_IDLE) 
//        		|| intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
//        		TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//            // This code will execute when the call is disconnected
//            Toast.makeText(context, "Detected call hangup event", Toast.LENGTH_LONG).show();
//            context.stopService(intentSend);
//        }
//	}

}
