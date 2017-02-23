package com.maxpevnitskiy.smssender;

import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by m on 22.02.2017.
 */

public class SMSJobService extends JobService {
    public static final String PHONE_NUMBER_KEY = "phone-number-key";
    private static final int NOTIFICATION_ID = 17;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("SMSJobService", "running");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = sharedPreferences.getInt(MainActivity.COUNT_TAG, 0);
        if (count > 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            count--;
            editor.putInt(MainActivity.COUNT_TAG, count);
            editor.commit();
//            Toast.makeText(this, "Service", Toast.LENGTH_LONG).show();
            Bundle phone = job.getExtras();
            SMSSender.sendSMS(this, phone.getString(PHONE_NUMBER_KEY));

            if (count == 0) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.done));

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
