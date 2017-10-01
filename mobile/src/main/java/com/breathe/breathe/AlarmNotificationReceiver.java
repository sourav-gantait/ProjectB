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
import android.support.v7.app.NotificationCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sourendra on 9/5/2017.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    int MID=0;
    private static final int NOTIFICATION = 3456;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        /*final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_dot);
        builder.setContentTitle("Time is up");
        builder.setContentText("SLIMS");
        builder.setVibrate(new long[] { 0, 200, 100, 200 });
        final Notification notification = builder.build();

        mNM.notify(NOTIFICATION, notification);*/
        /*Calendar now = GregorianCalendar.getInstance();
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
            mNotificationManager.notify(1, mBuilder.build());*/

        long when = System.currentTimeMillis();
        /*NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent data
        int duration = intent.getIntExtra("duration", 0);
        Intent notificationIntent = new Intent(context, Meditation.class);
        notificationIntent.putExtra("duration", duration);
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
                .addAction(2, "IGNORE", resultPendingIntent)
                .addAction(1,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;*/
        }

    public void clearNotification(int id){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

}
