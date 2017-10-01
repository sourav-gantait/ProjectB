package com.breathe.breathe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

/**
 * Created by Sourendra on 9/17/2017.
 */

public class NotificationService extends Service {
    private NotificationManager mManager;
    int MID=0;
    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand", "OK");
        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent data
        int duration = intent.getIntExtra("duration", 0);
        Intent notificationIntent = new Intent(getApplicationContext(), Meditation.class);
        notificationIntent.putExtra("duration", duration);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),  123, new Intent(), 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        SpannableString title = new SpannableString("Breathe.now");
        title.setSpan(new ForegroundColorSpan(getApplicationContext().getResources().getColor(R.color.colorRedDot)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.drawable.ic_dot)
                .setContentTitle(title)
                .setContentText("Take time to breathe.").setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .addAction(2, "IGNORE", resultPendingIntent)
                .addAction(1,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;
        return START_STICKY;
    }

    @SuppressWarnings("static-access")
    /*@Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);
        Notification notification = new Notification(R.drawable.ic_dot,"This is a test message!", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(this.getApplicationContext(), "Daily Notification Demo", "This is a test message!", pendingNotificationIntent);
        mManager.notify(0, notification);
    }*/
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
