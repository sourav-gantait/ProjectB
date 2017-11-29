package com.breathe.breathe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sourendra on 9/5/2017.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    int MID = 1;
    private static final int NOTIFICATION = 3456;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
//        StaticWakeLock.lockOn(context);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm Manager");
        wl.acquire();
        String duration = intent.getStringExtra("duration");
        NotificationService notificationService = new NotificationService();
        notificationService.showNotification(duration, context);
    }

}
