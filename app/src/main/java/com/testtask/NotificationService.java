package com.testtask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationService extends Service {

    private static final long EXECUTION_TIME_IN_SEC = 300;

    private static final int NOTIFICATION_ID = 1;

    private Disposable timerSubscribe;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timerSubscribe = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(EXECUTION_TIME_IN_SEC)
                .map(sec -> EXECUTION_TIME_IN_SEC - sec - 1)
                .subscribeOn(Schedulers.io())
                .subscribe(this::showNotification, (throwable) -> {}, this::onComplete);
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(long timeInSec) {
        long minutes = TimeUnit.SECONDS.toMinutes(timeInSec);
        long seconds = timeInSec - TimeUnit.MINUTES.toSeconds(minutes);
        String time = getString(R.string.time_format, minutes, seconds);

        startForeground(NOTIFICATION_ID,
                NotificationUtils.createNotification(getApplicationContext(), time));
    }

    private void onComplete() {
        if (!timerSubscribe.isDisposed()) {
            timerSubscribe.dispose();
        }
    }
}
