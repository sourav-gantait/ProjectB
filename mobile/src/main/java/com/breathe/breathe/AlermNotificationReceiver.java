package com.breathe.breathe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sourendra on 9/5/2017.
 */

public class AlermNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar now = GregorianCalendar.getInstance();
        Log.e("NotificationReceiver==>", String.valueOf(now.getTime()));
        String duration = intent.getStringExtra("duration");
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_dot)
                        .setContentTitle("Breathe")
                        .setContentText("Time to breathe.");
        Intent resultIntent = new Intent(context, Meditation.class);
        resultIntent.putExtra("duration", duration);
        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(Home.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }

    }
}
