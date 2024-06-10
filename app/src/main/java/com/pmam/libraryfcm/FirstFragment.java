package com.pmam.libraryfcm;

import static com.msm.themes.util.themePreferencia.getProvider;
import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;
import static com.pmam.fcm.notifications.NotificationDatabase.getInboxStyleData;
import static com.pmam.fcm.notifications.handlers.BigTextIntentService.ACTION_DISMISS;
import static com.pmam.fcm.notifications.handlers.BigTextIntentService.ARG_ACTION_DISMISS;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import com.google.firebase.Timestamp;
import com.pmam.fcm.notifications.DismissNotificationBroadCastReceiver;
import com.pmam.fcm.notifications.GlobalNotificationBuilder;
import com.pmam.fcm.notifications.NotificationDatabase;
import com.pmam.fcm.notifications.NotificationUtil;
import com.pmam.libraryfcm.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private static final String TAG = "FirstFragment";
    private Context ctx;
    private WorkManager mWorkManager;

    public static java.util.ArrayList<mEvento> listEventos;

    private static final String NOTIFICATION_CHANNEL_ID = "101";
    private static final String NOTIFICATION_CHANNEL = "teste";

    private NotificationManagerCompat mNotificationManagerCompat;
    private Bitmap bitmapImg;
    // Você pode fazer a atribuição dentro de onAttach ou onCreate, ou seja, antes que a atividade seja exibida
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // There are no request codes
            Intent data = result.getData();
            bitmapImg = ImagePicker.getImageFromResult(ctx, result.getResultCode(), data);
            BigImagem("Imagem", "Notificação de Imagem", bitmapImg);

        }
    });
    private final ActivityResultLauncher<String> mPermissionCameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if (result) {
            Intent intent = ImagePicker.getPickImageIntent(ctx, getProvider(ctx));
            someActivityResultLauncher.launch(intent);
        } else {
            Toast.makeText(ctx, "Permissão negada!", Toast.LENGTH_SHORT).show();
            //tg.LogD("onActivityResult: PERMISSION DENIED");
        }
    });


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

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///
                    /*O estilo de texto grande é suportado a partir da API de nível 16.
                    O tamanho máximo do texto é de 256 caracteres.
                    A cor de fundo é opcional. Se não for definida, a cor de fundo padrão será usada.*/
                selectImage();
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  redeSocial();
            }
        });

    }

    private void redeSocial() {

        Log.d(TAG, "A tarefa de curta duração é feita.");
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        Intent notifyIntent = new Intent(getContext(), MainActivity2.class);
        mEvento me = new mEvento();
        me.setDateTimeEvento(new Timestamp(new Date()));
        me.setIdDoc("idDoc" + (int) System.currentTimeMillis());
        me.setNomeEvento("Evento de Teste: " + (int) System.currentTimeMillis());
        //	myIntent.putExtra("idDocEvento", me.getIdDoc());

     if(listEventos == null){
         listEventos = new java.util.ArrayList<>();
         listEventos.add(me);
     }else {
         listEventos.add(me);
     }
        ArrayList<String> ListTitle = new ArrayList<>();
        ArrayList<String> ListContentText = new ArrayList<>();

        for (mEvento evento : listEventos){
            String titulo = "Novo Evento disponível";
            ListTitle.add(titulo);
            ListContentText.add(evento.getNomeEvento() + " - " + sdf.format(evento.getDateTimeEvento().toDate()));
        }


        NotificationDatabase.InboxStyleAppData inboxStyle = getInboxStyleData("SEG - Evento", ListTitle, ListContentText, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL);
        generateInboxStyleNotification(notifyIntent, inboxStyle);
    }

    private void generateSocialStyleNotification(Intent notifyIntent, NotificationDatabase.MessagingStyleCommsAppData ntdata) {

        mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, ntdata);



        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(ctx, notificationCompatBuilder, ntdata.getContentTitle(), ntdata.getContentText(), notifyIntent);
        notificationCompatBuilder.setSmallIcon(R.mipmap.ic_launcher);

        for (NotificationCompat.MessagingStyle.Message m: ntdata.getMessages()) {
            assert m.getPerson() != null;
            notificationCompatBuilder.setStyle(new NotificationCompat.MessagingStyle(m.getPerson())
                    .addMessage(m));
        }

        Notification notification =  notificationCompatBuilder.build();
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            mNotificationManagerCompat.notify(113, notification);
        }
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

    public void selectImage() {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = ImagePicker.getPickImageIntent(ctx,  getProvider(ctx));
            someActivityResultLauncher.launch(intent);

        } else {
            mPermissionCameraPermission.launch(Manifest.permission.CAMERA);
        }
    }


    private void BigImagem(String title, String msm, Bitmap img) {
        Log.d(TAG, "A tarefa de curta duração é feita.");
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        Intent notifyIntent = new Intent(getContext(), MainActivity2.class);
        NotificationDatabase.BigPictureStyleSocialAppData bigTextStyleReminderAppData = NotificationDatabase.getBigPictureStyleData(img, title, msm, sdf.format(new Date()), NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL);
        generateBigImageStyleNotification(notifyIntent, bigTextStyleReminderAppData);

    }

    private void generateBigTextStyleNotification(Intent notifyIntent, NotificationDatabase.BigTextStyleReminderAppData ntdata) {
         mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, ntdata);


        // 2. Build the BIG_TEXT_STYLE.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(ntdata.getBigText())
                .setBigContentTitle(ntdata.getBigContentTitle())
                .setSummaryText(ntdata.getSummaryText());

        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(ctx, notificationCompatBuilder, ntdata.getBigContentTitle(), ntdata.getContentText(), notifyIntent);
        Notification notification = notificationCompatBuilder
                .setStyle(bigTextStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            mNotificationManagerCompat.notify(110, notification);
        }
    }


    private void generateBigImageStyleNotification(Intent notifyIntent, NotificationDatabase.BigPictureStyleSocialAppData ntdata) {
        mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, ntdata);

        // 1. Build the BIG_PICTURE_STYLE
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(ntdata.getmBigImage())
                .setBigContentTitle(ntdata.getBigContentTitle())
                .setSummaryText(ntdata.getSummaryText());

         NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);
         GlobalNotificationBuilder.setNotificationCompatBuilderInstance(ctx, notificationCompatBuilder, ntdata.getBigContentTitle(), ntdata.getContentText(), notifyIntent);
        Notification notification = notificationCompatBuilder
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            mNotificationManagerCompat.notify(111, notification);
        }

    }


    private void generateInboxStyleNotification(Intent notifyIntent, NotificationDatabase.InboxStyleAppData ntdata) {
        mNotificationManagerCompat = NotificationManagerCompat.from(ctx);
        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId = NotificationUtil.createNotificationChannel(ctx, ntdata);
        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(ctx, notificationChannelId);
        // 2. Build the INBOX_STYLE.
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .setBigContentTitle(ntdata.getBigContentTitle())
                .setSummaryText(ntdata.getSummaryText());

        // Add each summary line of the new emails, you can add up to 5.
        for (String summary : ntdata.getIndividualEmailSummary()) {
            inboxStyle.addLine(summary);
        }

        for (String name : ntdata.getParticipants()) {
            final Person person = new Person.Builder().setUri(name).build();
            notificationCompatBuilder.addPerson(person);
        }

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(ctx, notificationCompatBuilder, ntdata.getBigContentTitle(), ntdata.getContentText(), notifyIntent);
        Notification notification = notificationCompatBuilder
                .setStyle(inboxStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            mNotificationManagerCompat.notify(111, notification);
        }
    }

    private void sendNotification(String messageBody) {
        Intent notifyIntent = new Intent(getContext(), MainActivity2.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //ação ao clicar no botao visualizar
        PendingIntent snoozePendingIntent = PendingIntent.getActivity(ctx, 0, notifyIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action snoozeAction = new NotificationCompat.Action.Builder(R.drawable.ic_eye, "Visualizar", snoozePendingIntent).build();


        // Dismiss Action - 1 opacao.
        Intent dismissIntent = new Intent(ctx, DismissNotificationBroadCastReceiver.class);
        dismissIntent.setAction(ACTION_DISMISS);
        dismissIntent.putExtra(ARG_ACTION_DISMISS, NOTIFICATION_ID);
        //ação ao clicar no botao fechar
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(ctx, 0, dismissIntent, PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_cancel_24, "Fechar", dismissPendingIntent).build();

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(requireContext(), channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .addAction(dismissAction)
                         .addAction(snoozeAction)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{100, 200, 300, 400})
                        .setContentIntent(snoozePendingIntent);

        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal de Teste",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

}