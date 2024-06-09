package com.pmam.libraryfcm;

import static com.msm.themes.util.themePreferencia.setProvider;
import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
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

import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
	private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUESTS = 113;

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

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
		setProvider(this, "com.pmam.libraryfcm");

	/*	NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(NOTIFICATION_ID);*/
		FirebaseMessaging.getInstance().getToken()
				.addOnCompleteListener(task -> {
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
                });


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


        getRuntimePermissions();


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

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            //tg.LogD(new Throwable(), permission);
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }


    }

    private String[] getRequiredPermissions() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private static boolean isPermissionGranted(Context context, String permission) {

        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", "Permissão garantida: " + permission);
            return true;
        }
        Log.d("MainActivity", "Permissão não concedida: " + permission);
        return false;
    }
}
