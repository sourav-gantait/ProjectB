package com.breathe.breathe;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloseNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_close_notification);

        Bundle bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("nmId") != null) {
            int nmId = bundle.getInt("nmId", 0);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(nmId);
        }
        finish();

    }


}
