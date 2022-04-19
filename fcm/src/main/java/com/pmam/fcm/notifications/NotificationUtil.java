package com.pmam.fcm.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationUtil {

	public static String createNotificationChannel(Context context, NotificationDatabase.MockNotificationData mockNotificationData) {

		// NotificationChannels are required for Notifications on O (API 26) and above.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			// The id of the channel.
			String channelId = mockNotificationData.getChannelId();

			// The user-visible name of the channel.
			CharSequence channelName = mockNotificationData.getChannelName();
			// The user-visible description of the channel.
			String channelDescription = mockNotificationData.getChannelDescription();
			boolean channelEnableVibrate = mockNotificationData.isChannelEnableVibrate();
			int channelLockscreenVisibility = mockNotificationData.getChannelLockscreenVisibility();

			// Initializes NotificationChannel.
			NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
			notificationChannel.setDescription(channelDescription);
			notificationChannel.enableVibration(channelEnableVibrate);
			notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

			// Adds NotificationChannel to system. Attempting to create an existing notification
			// channel with its original values performs no operation, so it's safe to perform the
			// below sequence.
			NotificationManager notificationManager =
					(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.createNotificationChannel(notificationChannel);

			return channelId;
		} else {
			// Returns null for pre-O (26) devices.
			return null;
		}
	}


	public static String createNotificationChannelProgress(Context context, NotificationDatabase.MockNotificationData mockNotificationData) {

		// NotificationChannels are required for Notifications on O (API 26) and above.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			// The id of the channel.
			String channelId = mockNotificationData.getChannelId();

			// The user-visible name of the channel.
			CharSequence channelName = mockNotificationData.getChannelName();
			// The user-visible description of the channel.
			String channelDescription = mockNotificationData.getChannelDescription();

			int channelLockscreenVisibility = mockNotificationData.getChannelLockscreenVisibility();
			//	int priority = NotificationCompat.PRIORITY_MIN;
			int importance = NotificationManager.IMPORTANCE_LOW;
			// Initializes NotificationChannel.
			NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
			notificationChannel.setDescription(channelDescription);
			notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

			// Adds NotificationChannel to system. Attempting to create an existing notification
			// channel with its original values performs no operation, so it's safe to perform the
			// below sequence.
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.createNotificationChannel(notificationChannel);

			return channelId;
		} else {
			// Returns null for pre-O (26) devices.
			return null;
		}
	}

}
