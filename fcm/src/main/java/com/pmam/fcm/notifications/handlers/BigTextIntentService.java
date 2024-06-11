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


import static com.pmam.fcm.notifications.GlobalNotificationBuilder.NOTIFICATION_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BigTextIntentService extends Worker {
	private static final String TAG = BigTextIntentService.class.getSimpleName();

	/*public static final String ACTION_DISMISS = "br.com.pmam.copmam.notifications.handlers.action.DISMISS";*/
	public static   String ARG_ACTION_DISMISS = "ARG_ACTION_DISMISS";
	public static   String ACTION_DISMISS = "ACTION_DISMISS";
	Context ctx;

	public BigTextIntentService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
		ctx = context;

	}

	private void handleActionDismiss() {
		NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
		notificationManagerCompat.cancel(NOTIFICATION_ID);
	}


	@NonNull
	@Override
	public Result doWork() {
		// Get the provider
		handleActionDismiss();
		Log.d(TAG, "ACTION_DISMISS(): " + NOTIFICATION_ID);
		//Log.d(TAG, "provider(): " + provider);
		// Indicate success
		return Result.success();
	}
}
