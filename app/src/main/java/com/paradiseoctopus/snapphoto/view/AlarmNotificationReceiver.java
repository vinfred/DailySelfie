package com.paradiseoctopus.snapphoto.view;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.paradiseoctopus.snapphoto.R;

public class AlarmNotificationReceiver extends BroadcastReceiver {
	// Notification ID to allow for future updates
	private static final int MY_NOTIFICATION_ID = 1;

	// Notification Text Elements
	private final CharSequence tickerText = "Time for another selfie!";
	private final CharSequence contentTitle = "Daile Selfie";
	private final CharSequence contentText = "Time for another selfie!";

	// Notification Action Elements
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;
	
	@Override
	@SuppressLint ("NewApi")
	@SuppressWarnings("deprecation")
	public void onReceive(Context context, Intent intent) {
		// The Intent to be used when the user clicks on the Notification View
		mNotificationIntent = new Intent(context, MainActivity.class);
		mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// The PendingIntent that wraps the underlying Intent
		mContentIntent = PendingIntent.getActivity(context, 0,	mNotificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		// Build the Notification
		Notification.Builder notificationBuilder = new Notification.Builder(context)
				.setTicker(tickerText)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true).setContentTitle(contentTitle)
				.setContentText(contentText).setContentIntent(mContentIntent)
				.setSound(sound).setSmallIcon(R.drawable.ic_action_camera);
				

		//set color available only on api 21
		if (Build.VERSION.SDK_INT >= 21) {
			notificationBuilder.setColor(context.getResources().getColor(R.color.notification_blue));
	    }
		
		// Get the NotificationManager
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// Pass the Notification to the NotificationManager:
		//to deal with the deprication
		if (Build.VERSION.SDK_INT >= 16) {
			mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
	    }
		else {
			mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.getNotification());
		}
	}
}