package com.pmam.libraryfcm;

import static com.msm.themes.util.ActivityIsVisible.isActivityVisible;
import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pmam.fcm.notifications.GlobalNotificationBuilder;
import com.pmam.fcm.notifications.NotificationDatabase;
import com.pmam.fcm.notifications.NotificationUtil;
import com.pmam.fcm.notifications.handlers.BigTextIntentService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

	private NotificationManagerCompat mNotificationManagerCompat;
	private static final String TAG = "MyFirebaseMsgService";
	private static final String NOTIFICATION_CHANNEL_TESTE_ID = "101";
	private static final String NOTIFICATION_CHANNEL_TESTE = "teste";
	private int currentChannel;
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
			Log.d(TAG, "Carga útil de dados da mensagem: " + remoteMessage.getData());

			if (/* Verifique se os dados precisam ser processados por um trabalho de longa duração */ true) {
				// Para tarefas de longa duração (10 segundos ou mais), use o WorkManager.
				scheduleJob();
			} else {
				// Lidar com a mensagem em 10 segundos
				handleNow(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
			}

		}

		// Check if message contains a notification payload.
		if (remoteMessage.getNotification() != null) {
			Log.d(TAG, "Corpo da notificação da mensagem: " + remoteMessage.getNotification().getBody());
		}

		// Além disso, se você pretende gerar suas próprias notificações como resultado de uma mensagem FCM recebida,
		// aqui é onde isso deve ser iniciado. Veja o método sendNotification abaixo.
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
		Log.d(TAG, "Token atualizado: " + token);

		// Se você quiser enviar mensagens para essa instância do aplicativo
		// ou gerenciar as assinaturas desse aplicativo no lado do servidor,
		// envie o token de registro do FCM para o servidor do aplicativo.
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
	 * Lidar com o tempo alocado para BroadcastReceivers.
	 */
	private void handleNow(String title, String msm) {
		Log.d(TAG, "A tarefa de curta duração é feita.");
		String myFormat = "dd/MM/yyyy"; //In which you need put here
		final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
		Intent notifyIntent = new Intent(this, MainActivity.class);
		NotificationDatabase.BigTextStyleReminderAppData bigTextStyleReminderAppData = NotificationDatabase.getBigTextStyleData(title, msm, sdf.format(new Date()), NOTIFICATION_CHANNEL_TESTE_ID, NOTIFICATION_CHANNEL_TESTE);
		generateBigTextStyleNotification(notifyIntent, bigTextStyleReminderAppData);

	}

	/**
	 * Persistir token para servidores de terceiros.
	 *
	 * Modifique este método para associar o token de registro do FCM do usuário a qualquer
	 * conta do lado do servidor mantida pelo seu aplicativo.
	 *
	 * @param token The new token.
	 */
	private void sendRegistrationToServer(String token) {
		// TODO: Implement this method to send token to your app server.
		Log.d(TAG, token);
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

	private void generateBigTextStyleNotification(Intent notifyIntent, NotificationDatabase.BigTextStyleReminderAppData bigTextStyleReminderAppData) {
		mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
		// 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
		String notificationChannelId = NotificationUtil.createNotificationChannel(this, bigTextStyleReminderAppData);

		// 2. Build the BIG_TEXT_STYLE.
		NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
				.bigText(bigTextStyleReminderAppData.getBigText())
				.setBigContentTitle(bigTextStyleReminderAppData.getBigContentTitle())
				.setSummaryText(bigTextStyleReminderAppData.getSummaryText());

		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		//acção ao clicar na notificação
		PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//ação ao clicar no botao visualizar
		PendingIntent snoozePendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
		NotificationCompat.Action snoozeAction = new NotificationCompat.Action.Builder(R.drawable.ic_eye, "Visualizar", snoozePendingIntent).build();

		// Dismiss Action - 1 opacao.
		Intent dismissIntent = new Intent(this, BigTextIntentService.class);
		dismissIntent.setAction(BigTextIntentService.ACTION_DISMISS);

		PendingIntent dismissPendingIntent = PendingIntent.getService(this, 0, dismissIntent, 0);


		NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_cancel_24, "Fechar", dismissPendingIntent).build();

		NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext(), notificationChannelId);

		GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);

		@SuppressLint("WrongConstant") Notification notification = notificationCompatBuilder
				.setStyle(bigTextStyle)
				.setContentTitle(bigTextStyleReminderAppData.getContentTitle())
				.setContentText(bigTextStyleReminderAppData.getContentText())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
				.setContentIntent(notifyPendingIntent)
				.setDefaults(NotificationCompat.DEFAULT_ALL)
				.setColor(ContextCompat.getColor(getApplicationContext(), com.msm.themes.R.color.primaryColorAmber))
				.setCategory(Notification.CATEGORY_MESSAGE)
				.setPriority(bigTextStyleReminderAppData.getPriority())
				.setVisibility(bigTextStyleReminderAppData.getChannelLockscreenVisibility())
				.addAction(snoozeAction)
				.addAction(dismissAction)
				.build();

		mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);
	}


	private void generateBigImageStyleNotification(Intent notifyIntent, NotificationDatabase.BigPictureStyleSocialAppData bigPictureStyleReminderAppData) {
		mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
		// 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
		String notificationChannelId = NotificationUtil.createNotificationChannel(this, bigPictureStyleReminderAppData);

		// 1. Build the BIG_PICTURE_STYLE
		NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
				.bigPicture(bigPictureStyleReminderAppData.getmBigImage())
				.setBigContentTitle(bigPictureStyleReminderAppData.getBigContentTitle())
				.setSummaryText(bigPictureStyleReminderAppData.getSummaryText());

		// 2. Set up main Intent for notification

		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		//acção ao clicar na notificação
		PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//ação ao clicar no botao visualizar
		PendingIntent snoozePendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
		NotificationCompat.Action snoozeAction = new NotificationCompat.Action.Builder(R.drawable.ic_eye, "Visualizar", snoozePendingIntent).build();

		// Dismiss Action - 1 opacao.
		Intent dismissIntent = new Intent(this, BigTextIntentService.class);
		dismissIntent.setAction(BigTextIntentService.ACTION_DISMISS);

		PendingIntent dismissPendingIntent = PendingIntent.getService(this, 0, dismissIntent, 0);


		NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_cancel_24, "Fechar", dismissPendingIntent).build();

		NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(getApplicationContext(), notificationChannelId);

		GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);

		@SuppressLint("WrongConstant") Notification notification = notificationCompatBuilder
				.setStyle(bigPictureStyle)
				.setContentTitle(bigPictureStyleReminderAppData.getContentTitle())
				.setContentText(bigPictureStyleReminderAppData.getContentText())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
				.setContentIntent(notifyPendingIntent)
				.setColor(getResources().getColor(com.msm.themes.R.color.primaryColorAmber))
				.addAction(dismissAction)
				.addAction(snoozeAction)
				.setCategory(Notification.CATEGORY_SOCIAL)
				.setPriority(bigPictureStyleReminderAppData.getPriority())
				.setVisibility(bigPictureStyleReminderAppData.getChannelLockscreenVisibility()).build();


		mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);
	}

	private String[] getOpmIds(String opm_ids){
		String opmsIds = opm_ids;
		opmsIds = opm_ids.replace("[", "");
		opmsIds = opmsIds.replace("]", "");
		return opmsIds.split(",");
	}
}
