package com.breathe.breathe;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
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
//    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
//        StaticWakeLock.lockOn(context);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm Manager");
        wl.acquire(60000);
        final String duration = intent.getStringExtra("duration");
        final long time = intent.getLongExtra("time", 0);
        final int requestCode = intent.getIntExtra("requestCode", 0);
//        NotificationService notificationService = new NotificationService();
//            notificationService.showNotification(duration, context);
//            showNotification(duration, context);
        Log.d("alarmNotificReceiver", String.valueOf(time));
        Log.d("systemTime---->", String.valueOf(System.currentTimeMillis()));
        if ((time+60000) > System.currentTimeMillis()) {
//            context.startService(new Intent(context, NotificationService.class));
//            NotificationService notificationService = new NotificationService();
            showNotification(duration, context);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setNoticationForRepeat(context, duration, time, requestCode);
//                }
//            }, 60000);
        }
//        setNoticationForRepeat(context, duration, time, requestCode);
    }

    private void setNoticationForRepeat(Context context, String duration, long time, int requestCode) {
        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        myIntent.putExtra("duration", duration);
        myIntent.putExtra("time", time);
        myIntent.putExtra("requestCode", requestCode);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        }else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
//        }
    }


    public void showNotification(String duration, Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, Home.class);
        notificationIntent.putExtra("duration", duration);
        notificationIntent.putExtra("nmId", MID);
//        Toast.makeText(context, "duration="+duration+":::nmId="+MID, Toast.LENGTH_SHORT).show();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent cancelIntent = new Intent(context, CloseNotification.class);
        cancelIntent.putExtra("duration", duration);
        cancelIntent.putExtra("nmId", MID);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,  123, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        SpannableString title = new SpannableString("Breathe.now");
//        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notification_red)
                .setContentTitle("Breathe. now")
                .setContentText("Take time to breathe.").setSound(alarmSound)
                .setAutoCancel(false)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .addAction(R.drawable.notification_red, "IGNORE", resultPendingIntent)
                .addAction(R.drawable.notification_red,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;
    }


}
