package com.testtask;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFlavorType();
        startNotification();

    }

    private void showFlavorType() {
        ((TextView) findViewById(R.id.text)).setText(BuildConfig.FLAVOR);
    }

    private void startNotification() {
        if (!isNotificationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), NotificationService.class);
            getApplicationContext().startService(intent);
        }
    }

    private boolean isNotificationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
