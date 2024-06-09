package com.pmam.libraryfcm;

import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;
import static com.pmam.fcm.notifications.DismissNotificationBroadCastReceiver.ARG_ACTION_DISMISS;
import static com.pmam.fcm.notifications.handlers.BigTextIntentService.ACTION_DISMISS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.pmam.fcm.notifications.GlobalNotificationBuilder;
import com.pmam.fcm.notifications.DismissNotificationBroadCastReceiver;
import com.pmam.fcm.notifications.NotificationDatabase;
import com.pmam.fcm.notifications.NotificationUtil;
import com.pmam.fcm.notifications.handlers.BigTextIntentService;
import com.pmam.libraryfcm.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private static final String TAG = "FirstFragment";
    private Context ctx;
    private WorkManager mWorkManager;

    private static final String NOTIFICATION_CHANNEL_ID = "101";
    private static final String NOTIFICATION_CHANNEL = "teste";

    private NotificationManagerCompat mNotificationManagerCompat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        ctx = getContext();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("Notificação de Teste");
            }
        });


        binding.btnBigText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///
                    /*O estilo de texto grande é suportado a partir da API de nível 16.
                    O tamanho máximo do texto é de 256 caracteres.
                    A cor de fundo é opcional. Se não for definida, a cor de fundo padrão será usada.*/
                    BigText("BigText", "O tamanho máximo do texto é de 256 caracteres");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void BigText(String title, String msm) {
        Log.d(TAG, "A tarefa de curta duração é feita.");
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        Intent notifyIntent = new Intent(getContext(), MainActivity2.class);
        NotificationDatabase.BigTextStyleReminderAppData bigTextStyleReminderAppData = NotificationDatabase.getBigTextStyleData(title, msm, sdf.format(new Date()), NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL);
        generateBigTextStyleNotification(notifyIntent, bigTextStyleReminderAppData);

    }

    private void generateBigTextStyleNotification(Intent notifyIntent, NotificationDatabase.BigTextStyleReminderAppData bigTextStyleReminderAppData) {
         mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, bigTextStyleReminderAppData);

        // 2. Build the BIG_TEXT_STYLE.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(bigTextStyleReminderAppData.getBigText())
                .setBigContentTitle(bigTextStyleReminderAppData.getBigContentTitle())
                .setSummaryText(bigTextStyleReminderAppData.getSummaryText());

         // Defina a atividade para iniciar em uma tarefa nova e vazia.
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Crie o TaskStackBuilder e adicione a intenção, que aumenta a pilha de retorno.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addNextIntentWithParentStack(notifyIntent);

//Obtenha o PendingIntent que contém toda a backstack.
        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //ação ao clicar no botao visualizar
/*        PendingIntent snoozePendingIntent = PendingIntent.getActivity(ctx, 0, notifyIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action snoozeAction = new NotificationCompat.Action.Builder(R.drawable.ic_eye, "Visualizar", snoozePendingIntent).build();*/



        //executar uma tarefa ao receber a notificação
   /*     OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(BigTextIntentService.class)
                .setInputData(new Data.Builder().putInt("action", NOTIFICATION_ID).build())
                .build();
        WorkManager.getInstance(ctx).enqueue(workRequest);*/


        // Dismiss Action - 1 opacao.
      /*  Intent dismissIntent = new Intent(ctx, DismissNotificationBroadCastReceiver.class);
        dismissIntent.putExtra(ARG_ACTION_DISMISS, NOTIFICATION_ID);
        dismissIntent.setAction(ACTION_DISMISS);
           //ação ao clicar no botao fechar
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(ctx, 0, dismissIntent, PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_cancel_24, "Fechar", dismissPendingIntent).build();*/


        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);

        @SuppressLint("WrongConstant") Notification notification = notificationCompatBuilder
                .setStyle(bigTextStyle)
                .setContentTitle(bigTextStyleReminderAppData.getContentTitle())
                .setContentText(bigTextStyleReminderAppData.getContentText())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(notifyPendingIntent)
                .setColor(ContextCompat.getColor(ctx, com.msm.themes.R.color.primaryColorAmber))
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setPriority(bigTextStyleReminderAppData.getPriority())
                .setVisibility(bigTextStyleReminderAppData.getChannelLockscreenVisibility())
               // .addAction(snoozeAction)
                .setAutoCancel(true)
              //  .addAction(dismissAction)
                .build();

        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }


    private void generateBigImageStyleNotification(Intent notifyIntent, NotificationDatabase.BigPictureStyleSocialAppData bigPictureStyleReminderAppData) {
        mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, bigPictureStyleReminderAppData);

        // 1. Build the BIG_PICTURE_STYLE
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(bigPictureStyleReminderAppData.getmBigImage())
                .setBigContentTitle(bigPictureStyleReminderAppData.getBigContentTitle())
                .setSummaryText(bigPictureStyleReminderAppData.getSummaryText());

        // 2. Set up main Intent for notification

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //acção ao clicar na notificação
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(ctx, 0, notifyIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        //ação ao clicar no botao visualizar
        PendingIntent snoozePendingIntent = PendingIntent.getActivity(ctx, 0, notifyIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action snoozeAction = new NotificationCompat.Action.Builder(R.drawable.ic_eye, "Visualizar", snoozePendingIntent).build();

        // Dismiss Action - 1 opacao.
        Intent dismissIntent = new Intent(ctx, BigTextIntentService.class);
        dismissIntent.setAction(ACTION_DISMISS);

        PendingIntent dismissPendingIntent = PendingIntent.getService(ctx, 0, dismissIntent,PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_cancel_24, "Fechar", dismissPendingIntent).build();

        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);

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

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(requireContext(), channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{100, 200, 300, 400})
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal de Teste",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}