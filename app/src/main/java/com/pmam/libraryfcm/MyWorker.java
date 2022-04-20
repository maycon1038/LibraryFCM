package com.pmam.libraryfcm;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

	private static final String TAG = "MyWorker";

	public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
		super(appContext, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Log.d(TAG, "Executando tarefa de longa duração no trabalho agendado");
		// TODO(developer): add long running task here.
		return Result.success();
	}
}
