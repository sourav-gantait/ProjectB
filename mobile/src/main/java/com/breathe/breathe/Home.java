package com.breathe.breathe;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPreference";
    private final String DATA_TAG = "data_reminder";
    SharedPreferences sharedpreferences;

    TextView tvAppName;
    RelativeLayout rlFifteenSecs;
    RelativeLayout rlThirtySecs;
    RelativeLayout rlSixtySecs;
    LinearLayout llTimeDuration;

    TextView tvTimeSpent;
    TextView tvTimeTotal;

    LinearLayout llReminder;
    TextView tvReminder;
    Button btnRemindMe;
//    Button btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ArrayList<HashMap<String, String>> alReminder = getStoredReminders();

        Bundle bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("duration") != null) {
            String durationTime = bundle.getString("duration");
            int nmId = bundle.getInt("nmId", 0);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(nmId);
            int duration = Integer.parseInt(durationTime.split(" ")[0]);
            Intent intent = new Intent(Home.this, Meditation.class);
            intent.putExtra("duration", duration);
            startActivityForResult(intent, 1);

        }

        rlFifteenSecs = (RelativeLayout) findViewById(R.id.rlFifteenSecs);
        rlThirtySecs = (RelativeLayout) findViewById(R.id.rlThirtySecs);
        rlSixtySecs = (RelativeLayout) findViewById(R.id.rlSixtySecs);

        tvAppName = (TextView) findViewById(R.id.home_tvAppName);
        llReminder = (LinearLayout) findViewById(R.id.home_llReminder);
        tvReminder = (TextView) findViewById(R.id.home_tvReminderTime);
        btnRemindMe = (Button) findViewById(R.id.home_btnRemindMe);
//        btnRandom = (Button) findViewById(R.id.home_btnRandom);

        SpannableString appName = new SpannableString("breathe.");
        appName.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAppName.setText(appName);

        tvTimeSpent = (TextView) findViewById(R.id.home_tvTimeSpentDuration);
        tvTimeTotal = (TextView) findViewById(R.id.home_tvTimeTotalDuration);
        llTimeDuration = (LinearLayout) findViewById(R.id.home_llTimes);
        tvTimeSpent.setText("00:00");
        tvTimeTotal.setText("00:00");
        llTimeDuration.setVisibility(View.GONE);

        rlFifteenSecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMedition(15);
            }
        });
        rlThirtySecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMedition(30);
            }
        });
        rlSixtySecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMedition(60);
            }
        });

        btnRemindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, ReminderList.class));
            }
        });

        if (null != alReminder && alReminder.size() > 0) {
            showNextReminder(alReminder);
            btnRemindMe.setText("Edit Reminder");
            llReminder.setVisibility(View.VISIBLE);
        } else {
            btnRemindMe.setText("Remind Me");
            llReminder.setVisibility(View.GONE);
        }

    }

    private ArrayList<HashMap<String, String>> getStoredReminders() {
        SharedPreferences storedSF = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final Gson gson = new Gson();
        String storedJsonData = storedSF.getString(DATA_TAG, "");
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(storedJsonData, type);
    }

    private void showNextReminder(ArrayList<HashMap<String, String>> alReminder) {
        Calendar calendar = Calendar.getInstance();
        boolean isFound = false;

        Collections.sort(alReminder, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> map, HashMap<String, String> t1) {
                return map.get("timeperiod").compareTo(t1.get("timeperiod"));
            }
        });
        Collections.sort(alReminder, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> map, HashMap<String, String> t1) {
                return map.get("minutes").compareTo(t1.get("minutes"));
            }
        });
        Collections.sort(alReminder, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> map, HashMap<String, String> t1) {
//                Log.d("map--->", map.get("hour"));
//                Log.d("t1--->", map.get("hour"));
                return map.get("hour").compareTo(t1.get("hour"));
            }
        });
        /*for (int i = 0; i<alReminder.size(); i++){
            HashMap<String, String> hashMap = alReminder.get(i);
            String id = hashMap.get("id");
            String days = hashMap.get("days");
            String duration = hashMap.get("duration");
            String hour = hashMap.get("hour");
            String minutes = hashMap.get("minutes");
            String timeperiod = hashMap.get("timeperiod");
            Log.d("days--->", days);
            Log.d("time--->", hour+":"+minutes+timeperiod);
        }*/
        HashMap<String, String> nextTime = new HashMap<>();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i< 7; i++){
            int today1 = (today+i) % 7;
        }


        for (int i = 0; i < alReminder.size(); i++) {
            if (!isFound) {
                HashMap<String, String> hashMap = alReminder.get(i);
                String id = hashMap.get("id");
                String days = hashMap.get("days");
                String duration = hashMap.get("duration");
                String hour = hashMap.get("hour");
                String minutes = hashMap.get("minutes");
                String timeperiod = hashMap.get("timeperiod");
                String active = hashMap.get("active");
                String[] daysArray = days.split(",");
                if (active.equals("yes")) {
                    if (daysArray[0].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Monday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[1].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Tuesday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[2].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Wednesday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[3].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Thursday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[4].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Friday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[5].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Saturday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                    if (daysArray[6].equals("1") && !isFound) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            if (calendar.get(Calendar.HOUR)< Integer.parseInt(hour)){
                                if (calendar.get(Calendar.MINUTE) < Integer.parseInt(minutes)){
                                    tvReminder.setText("Today @ " + hour + ":" + minutes + " "+timeperiod);
                                    isFound = true;
                                }
                            }
                        } else {
                            tvReminder.setText("Sunday @ " + hour + ":" + minutes + " "+timeperiod);
                            isFound = true;
                        }
                    }
                }
            }
        }

    }

    private void gotoMedition(int duration) {
        Intent intent = new Intent(Home.this, Meditation.class);
        intent.putExtra("duration", duration);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                long result = data.getLongExtra("result", 0);
                SharedPreferences storedSF = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                Long storedDuratoin = storedSF.getLong("total_duration", 0);
                storedDuratoin += result;
                int[] splittedTimes = splitToLongTimes(BigDecimal.valueOf(storedDuratoin));
                String splittedHours = "";
                String splittedMins = "";
                String splittedSecs = "";
                for (int i = 0; i < splittedTimes.length; i++) {
                    splittedHours = String.format("%02d", splittedTimes[0]);
                    splittedMins = String.format("%02d", splittedTimes[1]);
                    splittedSecs = String.format("%02d", splittedTimes[2]);
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putLong("total_duration", storedDuratoin);
                editor.commit();
                llTimeDuration.setVisibility(View.VISIBLE);
                tvTimeSpent.setText("00:" + String.format("%02d", result));
                if (!splittedHours.equals("00") && splittedHours != null) {
                    tvTimeTotal.setText(splittedHours + ":" + splittedMins + ":" + splittedSecs);
                } else {
                    tvTimeTotal.setText(splittedMins + ":" + splittedSecs);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    public static int[] splitToLongTimes(BigDecimal biggy) {
        long longVal = biggy.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;
        /*long longVal = biggy.longValue();
        *//*int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;*//*
        int mins = (int)longVal / 60;
        int remainder = (int)longVal - mins * 60;
        int secs = remainder;*/

        /*int[] ints = {mins , secs};
        return ints;*/
    }
}
