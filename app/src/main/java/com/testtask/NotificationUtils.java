package com.testtask;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

    private static final String FCM_DEFAULT_CHANNEL = "fcm_default_channel";

    public static Notification createNotification(@NonNull Context context, @NonNull String time) {

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(context, time);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(FCM_DEFAULT_CHANNEL,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        return notificationBuilder.build();
    }

    @NonNull
    private static NotificationCompat.Builder getNotificationBuilder(@NonNull Context context,
                                                                     @NonNull String time) {
        return new NotificationCompat.Builder(context, FCM_DEFAULT_CHANNEL)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content, time))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(null)
                .setAutoCancel(false)
                .setOngoing(true);
    }
}
