package com.breathe.breathe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sourendra on 9/17/2017.
 */

public class NotificationService extends Service {
    int MID=1;
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
        /*Log.e("onStartCommand", "OK");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent data
        Bundle bundle = intent.getExtras();
        String duration = bundle.getString("duration");
        Log.e("duration=====>", "=="+duration);
        Intent notificationIntent = new Intent(this, Home.class);
        notificationIntent.putExtra("duration", duration);
        notificationIntent.putExtra("nmId", MID);
        Toast.makeText(getApplicationContext(), "duration="+duration+":::nmId="+MID, Toast.LENGTH_SHORT).show();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,  123, new Intent(), 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        SpannableString title = new SpannableString("Breathe.now");
//        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.icon_red_dot)
                .setContentTitle("Breathe. now")
                .setContentText("Take time to breathe.").setSound(alarmSound)
                .setAutoCancel(false)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .addAction(2, "IGNORE", resultPendingIntent)
                .addAction(R.drawable.icon_red_dot,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;*/
        return START_STICKY;
    }

    public void showNotification(String duration, Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, Home.class);
        notificationIntent.putExtra("duration", duration);
        notificationIntent.putExtra("nmId", MID);
//        Toast.makeText(context, "duration="+duration+":::nmId="+MID, Toast.LENGTH_SHORT).show();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,  123, new Intent(), 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        SpannableString title = new SpannableString("Breathe.now");
//        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_red_dot)
                .setContentTitle("Breathe. now")
                .setContentText("Take time to breathe.").setSound(alarmSound)
                .setAutoCancel(false)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .addAction(2, "IGNORE", resultPendingIntent)
                .addAction(R.drawable.icon_red_dot,"BREATHE", pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
//        notificationManager.cancel(MID);
        MID++;
    }

//    @SuppressWarnings("static-access")
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
