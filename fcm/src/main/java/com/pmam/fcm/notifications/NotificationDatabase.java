package com.pmam.fcm.notifications;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

import com.pmam.fcm.R;

import java.util.ArrayList;


public final class NotificationDatabase {

	public static BigTextStyleReminderAppData getBigTextStyleData(String title, String content, String summary, String ChannelId, String nameChannel) {
		return BigTextStyleReminderAppData.getInstance(title, content, summary, ChannelId, nameChannel);
	}

	public static InboxStyleEmailAppData getInboxStyleData(String mContentTitle, ArrayList<String> mParticipants, ArrayList<String> listMsm, String mChannelId, String mChannelName) {
		return InboxStyleEmailAppData.getInstance(mContentTitle, mParticipants, listMsm,   mChannelId,   mChannelName);
	}
	private static synchronized InboxStyleEmailAppData getSync(String mContentTitle, ArrayList<String> mParticipants, ArrayList<String> listMsm, String mChannelId, String mChannelName) {

		return new InboxStyleEmailAppData(mContentTitle, mParticipants, listMsm,   mChannelId,   mChannelName);

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
			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
			mChannelEnableVibrate = true;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_social_1";
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
//			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
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
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
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

	public static class InboxStyleEmailAppData extends MockNotificationData {

		private static InboxStyleEmailAppData sInstance = null;

		// Unique data for this Notification.Style:
		private int mNumberOfNewEmails;
		private String mBigContentTitle;
		private String mSummaryText;
		private ArrayList<String> mIndividualEmailSummary;

		private String mAppName = "FCM";

		private ArrayList<String> mParticipants;

		private InboxStyleEmailAppData(String mContentTitle,ArrayList<String> mParticipants, ArrayList<String> listMsm, String mChannelId, String mChannelName) {
			mContentText = (listMsm.size() > 1) ? listMsm.get(0) + ", + " + (listMsm.size() - 1) : listMsm.get(0);
			mNumberOfNewEmails = listMsm.size();
			mPriority = NotificationCompat.PRIORITY_DEFAULT;
			mBigContentTitle = "(" + mContentTitle + "): " + mContentText;
			mSummaryText = mContentTitle;
			this.mParticipants = mParticipants;
			this.mIndividualEmailSummary = listMsm;
			this.mChannelId = mChannelId;
			this.mChannelName = mChannelName;
			mChannelDescription = "Notificações do " + mChannelName;
			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
			mChannelEnableVibrate = true;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
		}

		public static InboxStyleEmailAppData getInstance(String mContentTitle,ArrayList<String> mParticipants, ArrayList<String> listMsm, String mChannelId, String mChannelName) {

			return getSync(mContentTitle, mParticipants, listMsm,   mChannelId,   mChannelName);

		}

		private static synchronized InboxStyleEmailAppData getSync(String mContentTitle, ArrayList<String> mParticipants, ArrayList<String> listMsm, String mChannelId, String mChannelName) {

			return new InboxStyleEmailAppData(mContentTitle, mParticipants, listMsm,   mChannelId,   mChannelName);

		}

		public int getNumberOfNewEmails() {
			return mNumberOfNewEmails;
		}

		public String getBigContentTitle() {
			return mBigContentTitle;
		}

		public String getSummaryText() {
			return mSummaryText;
		}

		public ArrayList<String> getIndividualEmailSummary() {
			return mIndividualEmailSummary;
		}

		public ArrayList<String> getParticipants() {
			return mParticipants;
		}

		@Override
		public String toString() {
			return getContentTitle() + " " + getContentText();
		}
	}


	public static MessagingStyleCommsAppData getMessagingStyleData(Context context) {
		return MessagingStyleCommsAppData.getInstance(context);
	}


	public static class MessagingStyleCommsAppData extends MockNotificationData {

		private static MessagingStyleCommsAppData sInstance = null;

		// Unique data for this Notification.Style:
		private ArrayList<NotificationCompat.MessagingStyle.Message> mMessages;
		// String of all mMessages.
		private String mFullConversation;
		// Name preferred when replying to chat.
		private Person mMe;
		private ArrayList<Person> mParticipants;

		private String mAppName = "FCM";
		private CharSequence[] mReplyChoicesBasedOnLastMessages;

		private MessagingStyleCommsAppData(Context context) {
			// Standard notification values:
			// Content for API <24 (M and below) devices.
			// Note: I am actually hardcoding these Strings based on info below. You would be
			// pulling these values from the same source in your database. I leave this up here, so
			// you can see the standard parts of a Notification first.
			mContentTitle = "mContentTitle, Wendy";
			mContentText = "mContentText! :)";
			mPriority = NotificationCompat.PRIORITY_HIGH;
			mContentTitle = "mContentTitle, Wendy";
			mAppName = getAppName(context);

			// Create the users for the conversation.
			// Name preferred when replying to chat.
			mMe = new Person.Builder()
					.setName("Meu Usuario")
					.setKey("1234567890")
					.setUri("tel:1234567890")
					.setIcon(IconCompat.createWithResource(context, R.drawable.me_macdonald))
					.build();

			Person participant1 = new Person.Builder()
					.setName("Usuario 2")
					.setKey("9876543210")
					.setUri("tel:9876543210")
					.setIcon(IconCompat.createWithResource(context, R.drawable.famous_fryer))
					.build();

			Person participant2 = new Person.Builder()
					.setName("Usuario 3")
					.setKey("2233221122")
					.setUri("tel:2233221122")
					.setIcon(IconCompat.createWithResource(context, R.drawable.wendy_wonda))
					.build();

			// If the phone is in "Do not disturb mode, the user will still be notified if
			// the user(s) is starred as a favorite.
			// Note: You don't need to add yourself, aka 'me', as a participant.
			mParticipants = new ArrayList<>();
			mParticipants.add(participant1);
			mParticipants.add(participant2);

			mMessages = new ArrayList<>();

			// For each message, you need the timestamp. In this case, we are using arbitrary longs
			// representing time in milliseconds.
			mMessages.add(
					// When you are setting an image for a message, text does not display.
					new NotificationCompat.MessagingStyle.Message("", 1528490641998l, participant1)
							.setData("image/png", resourceToUri(context, R.drawable.earth)));

			mMessages.add(
					new NotificationCompat.MessagingStyle.Message(
							"Visitando a lua novamente? :P", 1528490643998l, mMe));

			mMessages.add(
					new NotificationCompat.MessagingStyle.Message("Ei, eu vejo minha casa!", 1528490645998l, participant2));

			// String version of the mMessages above.
			mFullConversation =
					"Famous: [Picture of Moon]\n\n"
							+ "Me: Visitando a lua novamente? :P\n\n"
							+ "Wendy: Ei, eu vejo minha casa! :)\n\n";

			// Responses based on the last messages of the conversation. You would use
			// Machine Learning to get these (https://developers.google.com/ml-kit/).
			mReplyChoicesBasedOnLastMessages =
					new CharSequence[]{"Eu também!", "Como está o tempo?", "Você tem uma boa visão."};

			// Notification channel values (for devices targeting 26 and above):
			mChannelId = "channel_messaging_1";
			// The user-visible name of the channel.
			mChannelName = "Mensagens";
			// The user-visible description of the channel.
			mChannelDescription = "Notificações de mensagens do " + mAppName;
			mChannelImportance = NotificationManager.IMPORTANCE_MAX;
			mChannelEnableVibrate = true;
			mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
		}

		public static MessagingStyleCommsAppData getInstance(Context context) {
			if (sInstance == null) {
				sInstance = getSync(context);
			}
			return sInstance;
		}

		private static synchronized MessagingStyleCommsAppData getSync(Context context) {
			if (sInstance == null) {
				sInstance = new MessagingStyleCommsAppData(context);
			}

			return sInstance;
		}

		public ArrayList<NotificationCompat.MessagingStyle.Message> getMessages() {
			return mMessages;
		}

		public String getFullConversation() {
			return mFullConversation;
		}

		public Person getMe() {
			return mMe;
		}

		public int getNumberOfNewMessages() {
			return mMessages.size();
		}

		public ArrayList<Person> getParticipants() {
			return mParticipants;
		}

		public CharSequence[] getReplyChoicesBasedOnLastMessage() {
			return mReplyChoicesBasedOnLastMessages;
		}

		@Override
		public String toString() {
			return getFullConversation();
		}

		public boolean isGroupConversation() {
			return mParticipants.size() > 1;
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
			mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
			mChannelEnableVibrate = true;
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


	public static Uri resourceToUri(Context context, int resId) {
		return Uri.parse(
				ContentResolver.SCHEME_ANDROID_RESOURCE
						+ "://"
						+ context.getResources().getResourcePackageName(resId)
						+ "/"
						+ context.getResources().getResourceTypeName(resId)
						+ "/"
						+ context.getResources().getResourceEntryName(resId));
	}

	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
			return (String) packageManager.getApplicationLabel(applicationInfo);
		} catch (PackageManager.NameNotFoundException e) {
			// Handle exception if package name is not found
			return "FCM";
		}
	}
}
