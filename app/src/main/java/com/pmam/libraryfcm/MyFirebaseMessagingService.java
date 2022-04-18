package com.pmam.libraryfcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

	private static final String TAG = "MyFirebaseMsgService";

	/**
	 * Called when message is received.
	 *
	 * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
	 */
	// [START receive_message]
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {

		// TODO(developer): Handle FCM messages here.
		// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
		Log.d(TAG, "From: " + remoteMessage.getFrom());

		// Check if message contains a data payload.
		if (remoteMessage.getData().size() > 0) {
			Log.d(TAG, "Message data payload: " + remoteMessage.getData());

			if (/* Verifique se os dados precisam ser processados por um trabalho de longa duração */ true) {
				// Para tarefas de longa duração (10 segundos ou mais), use o WorkManager.
				scheduleJob();
			} else {
				// Lidar com a mensagem em 10 segundos
				handleNow();
			}

		}

		// Check if message contains a notification payload.
		if (remoteMessage.getNotification() != null) {
			Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
		}

		// Also if you intend on generating your own notifications as a result of a received FCM
		// message, here is where that should be initiated. See sendNotification method below.
	}
	// [END receive_message]


	// [START on_new_token]
	/**
	 * There are two scenarios when onNewToken is called:
	 * 1) When a new token is generated on initial app startup
	 * 2) Whenever an existing token is changed
	 * Under #2, there are three scenarios when the existing token is changed:
	 * A) App is restored to a new device
	 * B) User uninstalls/reinstalls the app
	 * C) User clears app data
	 */
	@Override
	public void onNewToken(String token) {
		Log.d(TAG, "Refreshed token: " + token);

		// If you want to send messages to this application instance or
		// manage this apps subscriptions on the server side, send the
		// FCM registration token to your app server.
		sendRegistrationToServer(token);
	}
	// [END on_new_token]

	/**
	 * Agende o trabalho assíncrono usando o WorkManager.
	 */
	private void scheduleJob() {
		// [START dispatch_job]
		OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
				.build();
		WorkManager.getInstance(this).beginWith(work).enqueue();
		// [END dispatch_job]
	}

	/**
	 * Handle time allotted to BroadcastReceivers.
	 */
	private void handleNow() {
		Log.d(TAG, "A tarefa de curta duração é feita.");
	}

	/**
	 * Persist token to third-party servers.
	 *
	 * Modify this method to associate the user's FCM registration token with any
	 * server-side account maintained by your application.
	 *
	 * @param token The new token.
	 */
	private void sendRegistrationToServer(String token) {
		// TODO: Implement this method to send token to your app server.
	}

	/**
	 * Crie e mostre uma notificação simples contendo a mensagem FCM recebida.
	 *
	 * @param messageBody FCM message body received.
	 */
	private void sendNotification(String messageBody) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
				PendingIntent.FLAG_ONE_SHOT);

		String channelId = getString(R.string.default_notification_channel_id);
		Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(this, channelId)
						.setSmallIcon(R.drawable.ic_launcher_foreground)
						.setContentTitle(getString(R.string.fcm_message))
						.setContentText(messageBody)
						.setAutoCancel(true)
						.setSound(defaultSoundUri)
						.setContentIntent(pendingIntent);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// Since android Oreo notification channel is needed.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(channelId,
					"Channel human readable title",
					NotificationManager.IMPORTANCE_DEFAULT);
			notificationManager.createNotificationChannel(channel);
		}

		notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
	}
}
