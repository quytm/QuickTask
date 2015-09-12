package com.tmq.t3h.quicktask.recall;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tmq.t3h.quicktask.CommonVL;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.mainui.MainUI;

public class RemineRecallLater extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent mIntent = new Intent(context,MainUI.class);
		mIntent.putExtra(CommonVL.TAB_ID, 2);
		Notification notification = new Notification(R.drawable.ico_recall,"Remine to recall!", System.currentTimeMillis());
		mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context,2, mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, "Recall", "You have a call!", pendingNotificationIntent);
		notiMgr.notify(0, notification);
	}

}
