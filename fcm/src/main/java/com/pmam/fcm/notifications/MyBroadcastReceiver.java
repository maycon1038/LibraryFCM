package com.pmam.fcm.notifications;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
	public static final String ARG_ACTION_DISMISS = "ACTION_DISMISS";
    @Override
    public void onReceive(Context context, Intent intent) {
		StringBuilder sb = new StringBuilder();
		sb.append("Action: " + intent.getAction() + "\n");
		sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n");
		String log = sb.toString();
		//Log.d(TAG, log);
		NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(intent.getIntExtra(ARG_ACTION_DISMISS, -1));


	}
}
