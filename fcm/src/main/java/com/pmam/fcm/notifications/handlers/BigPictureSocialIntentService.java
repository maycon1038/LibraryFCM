/*
Copyright 2016 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.pmam.fcm.notifications.handlers;

import static com.msm.themes.util.themePreferencia.getProvider;
import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;


/**
 * Lida de forma assíncrona com a atualização de postagens de aplicativos sociais (e notificação ativa) com comentários de
 * do utilizador. Notificação para uso de aplicativos sociais BigPictureStyle.
 */
public class BigPictureSocialIntentService extends IntentService {


	/*public static final String ACTION_COMMENT = "br.com.pmam.copmam.notifications.handlers.action.COMMENT";
	public static final String EXTRA_COMMENT = "br.com.pmam.copmam.notifications.handlers.extra.COMMENT";*/
	private static final String TAG = "BigPictureService";
	/*public static final String ACTION_DISMISS = "br.com.pmam.copmam.notifications.handlers.action.DISMISS";
	public static final String ARG_ACTION_DISMISS = "ACTION_DISMISS";*/
	public static String ACTION_DISMISS;

	public BigPictureSocialIntentService() {
		super("BigPictureSocialIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		String provider = getProvider(this);
		StringBuilder st = new StringBuilder();
		st.append(provider);
		st.append(".notifications.handlers.action.DISMISS");
		ACTION_DISMISS = st.toString();
		if (intent != null) {
			String action = intent.getAction();
			if (ACTION_DISMISS.equals(action)) {
				handleActionDismiss();
			}
		}
		Log.d(TAG, "ACTION_DISMISS(): " + ACTION_DISMISS);
		Log.d(TAG, "provider(): " + provider);
	}

	private void handleActionDismiss() {
		NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
		notificationManagerCompat.cancel(NOTIFICATION_ID);
	}

}
