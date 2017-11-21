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
    int MID=1;
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
//        Toast.makeText(context, "duration : "+duration, Toast.LENGTH_LONG).show();
//        Intent serviceIntent = new Intent(context, NotificationService.class);
//        serviceIntent.putExtra("duration", duration);
//        context.startService(serviceIntent);
        NotificationService notificationService = new NotificationService();
        notificationService.showNotification(duration, context);

        /*long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent data

        Intent notificationIntent = new Intent(context, Home.class);
        notificationIntent.putExtra("duration", Integer.parseInt(duration));
        notificationIntent.putExtra("nmId", MID);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,  123, new Intent(), 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        SpannableString title = new SpannableString("Breathe.now");
        title.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorRedDot)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_dot)
                .setContentTitle(title)
                .setContentText("Take time to breathe.").setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .addAction(2, "IGNORE", resultPendingIntent)
                .addAction(1,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;*/

//        StaticWakeLock.lockOff(context);
        }
/*
    public void clearNotification(int id){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }*/

}
