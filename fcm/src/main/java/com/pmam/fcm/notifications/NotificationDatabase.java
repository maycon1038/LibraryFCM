package com.pmam.fcm.notifications;

import android.app.NotificationManager;
import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;


public final class NotificationDatabase {

	public static BigTextStyleReminderAppData getBigTextStyleData(String title, String content, String summary, String ChannelId, String nameChannel) {
		return BigTextStyleReminderAppData.getInstance(title, content, summary, ChannelId, nameChannel);
	}


	public static BigPictureStyleSocialAppData getBigPictureStyleData(Bitmap imgBig, String title, String content, String summary, String ChannelId, String nameChannel) {
		return BigPictureStyleSocialAppData.getInstance(imgBig, title, content, summary, ChannelId, nameChannel);
	}


	public static ProgressBarAppData getProgressBar(String title, String content, String summary, String ChannelId, String nameChannel) {
		return ProgressBarAppData.getInstance(title, content, summary, ChannelId, nameChannel);
	}

	public static class ProgressBarAppData extends MockNotificationData {

		private static ProgressBarAppData sInstance = null;

		// Unique data for this Notification.Style:
		private Bitmap mBigImage;
		private String mBigContentTitle;
		private String mSummaryText;

		private ProgressBarAppData(String title, String content, String summary, String channelId, String nameChannel) {
			// Standard Notification values:
			// Title/Content for API <16 (4.0 and below) devices.


			// Standard Notification values:
			// Title for API <16 (4.0 and below) devices.
			mContentTitle = title;
			// Content for API <24 (4.0 and below) devices.
			mContentText = content;
			mPriority = NotificationCompat.PRIORITY_DEFAULT;

			// BigText Style Notification values:
			mBigContentTitle = title;
			mSummaryText = summary;

			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_reminder_" + channelId;
			// The user-visible name of the channel.
			mChannelName = nameChannel;
			// The user-visible description of the channel.
			mChannelDescription = "Notificações do " + nameChannel;
			mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
			mChannelEnableVibrate = false;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_social_1";
			// The user-visible name of the channel.
			// The user-visible description of the channel.
			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
			mChannelEnableVibrate = false;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
		}

		public static ProgressBarAppData getInstance(String title, String content, String summary, String channelId, String nameChannel) {

			return getSync(title, content, summary, channelId, nameChannel);
		}

		private static synchronized ProgressBarAppData getSync(String title, String content, String summary, String channelId, String nameChannel) {

			return new ProgressBarAppData(title, content, summary, channelId, nameChannel);

		}


		@Override
		public String toString() {
			return getContentTitle() + " - " + getContentText();
		}


	}


	public static class BigPictureStyleSocialAppData extends MockNotificationData {

		private static BigPictureStyleSocialAppData sInstance = null;

		// Unique data for this Notification.Style:
		private Bitmap mBigImage;
		private String mBigContentTitle;
		private String mSummaryText;


		private BigPictureStyleSocialAppData(Bitmap imgBig, String title, String content, String summary, String channelId, String nameChannel) {
			// Standard Notification values:
			// Title/Content for API <16 (4.0 and below) devices.


			// Standard Notification values:
			// Title for API <16 (4.0 and below) devices.
			mContentTitle = title;
			// Content for API <24 (4.0 and below) devices.
			mContentText = content;
			mPriority = NotificationCompat.PRIORITY_DEFAULT;

			// BigText Style Notification values:
			mBigContentTitle = title;
			mSummaryText = summary;

//			// Notification channel values (for devices targeting 26 and above):
//			mChannelId = "channel_reminder_" + channelId;
//			// The user-visible name of the channel.
//			mChannelName = nameChannel;
//			// The user-visible description of the channel.
//			mChannelDescription = "Notificações do " + nameChannel;
//			mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
//			mChannelEnableVibrate = false;
//			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;

			// Style notification values:
			mBigImage = imgBig;
			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_social_1";
			// The user-visible name of the channel.
			// The user-visible description of the channel.
			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
			mChannelEnableVibrate = true;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
		}

		public static BigPictureStyleSocialAppData getInstance(Bitmap imgBig, String title, String content, String summary, String channelId, String nameChannel) {

			return getSync(imgBig, title, content, summary, channelId, nameChannel);
		}

		private static synchronized BigPictureStyleSocialAppData getSync(Bitmap imgBig, String title, String content, String summary, String channelId, String nameChannel) {

			return new BigPictureStyleSocialAppData(imgBig, title, content, summary, channelId, nameChannel);

		}


		public String getBigContentTitle() {
			return mBigContentTitle;
		}

		public String getSummaryText() {
			return mSummaryText;
		}

		@Override
		public String toString() {
			return getContentTitle() + " - " + getContentText();
		}

		public Bitmap getmBigImage() {
			return mBigImage;
		}
	}


	/**
	 * Represents data needed for BigTextStyle Notification.
	 */
	public static class BigTextStyleReminderAppData extends MockNotificationData {

		private static BigTextStyleReminderAppData sInstance = null;

		// Unique data for this Notification.Style:
		private String mBigContentTitle;
		private String mBigText;
		private String mSummaryText;
		private String mAppName = " SISPMAM";

		private BigTextStyleReminderAppData(String title, String content, String summary, String ChannelId, String nameChannel) {

			// Standard Notification values:
			// Title for API <16 (4.0 and below) devices.
			mContentTitle = title;
			// Content for API <24 (4.0 and below) devices.
			mContentText = content;
			mPriority = NotificationCompat.PRIORITY_DEFAULT;

			// BigText Style Notification values:
			mBigContentTitle = title;
			mBigText = content;
			mSummaryText = summary;

			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_reminder_" + ChannelId;
			// The user-visible name of the channel.
			mChannelName = nameChannel;
			// The user-visible description of the channel.
			mChannelDescription = "Notificações do " + nameChannel;
			mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
			mChannelEnableVibrate = false;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
		}

		private BigTextStyleReminderAppData() {
		}

		public static BigTextStyleReminderAppData getInstance(String title, String content, String summary, String ChannelId, String nameChannel) {

			return getSync(title, content, summary, ChannelId, nameChannel);

		}

		public static BigTextStyleReminderAppData getInstance() {
			if (sInstance == null) {
				sInstance = getSync();
			}

			return sInstance;
		}

		private static synchronized BigTextStyleReminderAppData getSync(String title, String content, String summary, String ChannelId, String nameChannel) {

			return new BigTextStyleReminderAppData(title, content, summary, ChannelId, nameChannel);

		}

		private static synchronized BigTextStyleReminderAppData getSync() {
			if (sInstance == null) {
				sInstance = new BigTextStyleReminderAppData();
			}

			return sInstance;
		}

		public String getBigContentTitle() {
			return mBigContentTitle;
		}

		public String getBigText() {
			return mBigText;
		}

		public String getSummaryText() {
			return mSummaryText;
		}

		@Override
		public String toString() {
			return getBigContentTitle() + getBigText();
		}
	}


	/**
	 * Represents standard data needed for a Notification.
	 */
	public abstract static class MockNotificationData {

		// Standard notification values:
		protected String mContentTitle;
		protected String mContentText;
		protected int mPriority;

		// Notification channel values (O and above):
		protected String mChannelId;
		protected CharSequence mChannelName;
		protected String mChannelDescription;
		protected int mChannelImportance;
		protected boolean mChannelEnableVibrate;
		protected int mChannelLockscreenVisibility;

		// Notification Standard notification get methods:
		public String getContentTitle() {
			return mContentTitle;
		}

		public String getContentText() {
			return mContentText;
		}

		public int getPriority() {
			return mPriority;
		}

		// Channel values (O and above) get methods:
		public String getChannelId() {
			return mChannelId;
		}

		public CharSequence getChannelName() {
			return mChannelName;
		}

		public String getChannelDescription() {
			return mChannelDescription;
		}

		public int getChannelImportance() {
			return mChannelImportance;
		}

		public boolean isChannelEnableVibrate() {
			return mChannelEnableVibrate;
		}

		public int getChannelLockscreenVisibility() {
			return mChannelLockscreenVisibility;
		}
	}
}
