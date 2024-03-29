package com.pmam.libraryfcm;

import static com.msm.themes.util.themePreferencia.setProvider;
import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pmam.libraryfcm.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
	private static final String TAG = "MainActivity";



    // Você pode fazer a atribuição dentro de onAttach ou onCreate, ou seja, antes que a atividade seja exibida
    private final ActivityResultLauncher<String> mPermissionPotNotification = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if (result) {
          /*  Intent intent = ImagePicker.getPickImageIntent(ctx, getProvider(ctx));
            someActivityResultLauncher.launch(intent);*/
        } else {
            Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
            //tg.LogD("onActivityResult: PERMISSION DENIED");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
		setProvider(this, "com.pmam.libraryfcm");

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(NOTIFICATION_ID);
		FirebaseMessaging.getInstance().getToken()
				.addOnCompleteListener(new OnCompleteListener<String>() {
					@Override
					public void onComplete(@NonNull Task<String> task) {
						if (!task.isSuccessful()) {
							Log.w(TAG, "Falha ao buscar o token de registro do FCM", task.getException());
							return;
						}

						// Get new FCM registration token
						String token = task.getResult();

						// Log and toast
						String msg = getString(R.string.msg_token_fmt, token);
						Log.d(TAG, msg);
						Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				});
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// Create channel to show notifications.
			String channelId  = getString(R.string.default_notification_channel_id);
			String channelName = getString(R.string.default_notification_channel_name);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
		}*/

		if (getIntent().getExtras() != null) {
			for (String key : getIntent().getExtras().keySet()) {
				Object value = getIntent().getExtras().get(key);
				Log.d(TAG, "Key: " + key + " Value: " + value);
			}
		}



		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

     /*   binding.fab.setOnClickListener(view ->
                //Snackbar.make(view, "Substitua por sua própria ação", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show());*/

        binding.fab.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                //  Intent intent = ImagePicker.getPickImageIntent(this,  getProvider(ctx));
                if (Build.VERSION.SDK_INT >= 33) {
                    mPermissionPotNotification.launch(Manifest.permission.POST_NOTIFICATIONS);
                }else {
                    Snackbar.make(v, "API < 33", Snackbar.LENGTH_SHORT)
                            .setAction("oK", null).show();
                }
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
