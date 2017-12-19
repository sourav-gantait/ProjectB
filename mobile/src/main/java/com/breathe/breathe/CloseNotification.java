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

       /* ArrayList<HashMap<String, String>>  rawData = new ArrayList<>();
        [{"Sourav Gantait":"32", "Sourav Gantait":"Garia", "Sourav Gantait":"Cerner", "Sourav Gantait":"dfgfg","Saikat Gantait":"32"}]
        "Sourav Gantait":["", "", ""];
        "Saikat Gantait":["32"];
        HashMap<String, List<String>> map = new HashMap<>();

        for (HashMap<String, String> map1 : rawData){
            for (Map.Entry mapValue : map1.entrySet()){
                String key = (String) mapValue.getKey();
                String value = (String) mapValue.getValue();
                if (!map.containsKey(key)){
                    List<String> tempList = new ArrayList<>();
                    tempList.add(value);
                    map.put(key, tempList);
                }else {
                    map.get(key).add(value);
                }
            }
        }*/
    }


}
