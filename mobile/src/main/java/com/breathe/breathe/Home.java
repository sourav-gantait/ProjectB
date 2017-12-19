package com.breathe.breathe;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    public static Activity home;
//    Button btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home = this;
//        enableReceiver();
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
        List<Calendar> allAlarmDays = new ArrayList<>();
        for (int j = 0; j < alReminder.size(); j++) {
            String[] daysArray = alReminder.get(j).get("days").split(",");

            String hour = alReminder.get(j).get("hour");
            String minutes = alReminder.get(j).get("minutes");
            String timeperiod = alReminder.get(j).get("timeperiod");

            for (int i = 0; i < 8; i++) {
                Calendar tempDay = Calendar.getInstance();
                tempDay.setFirstDayOfWeek(Calendar.MONDAY);
                tempDay.add(Calendar.DATE, i);
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

                int tempI = getDayOfWeek(dayFormat.format(tempDay.getTime()));

                if (daysArray[tempI].equals("1")) {
                    tempDay.set(Calendar.HOUR, Integer.parseInt(hour));
                    tempDay.set(Calendar.MINUTE, Integer.parseInt(minutes));
                    tempDay.set(Calendar.SECOND, 0);
                    if (timeperiod.equals("AM")) {
                        tempDay.set(Calendar.AM_PM, Calendar.AM);
                    } else {
                        tempDay.set(Calendar.AM_PM, Calendar.PM);
                    }
                    if (tempDay.after(Calendar.getInstance())) {
                        allAlarmDays.add(tempDay);
                    }
                }
            }
        }
        Collections.sort(allAlarmDays, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar calendar, Calendar t1) {
                return calendar.get(Calendar.AM_PM) - t1.get(Calendar.AM_PM);
            }
        });
        Collections.sort(allAlarmDays, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar calendar, Calendar t1) {
                return calendar.get(Calendar.MINUTE) - t1.get(Calendar.MINUTE);
            }
        });
        Collections.sort(allAlarmDays, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar calendar, Calendar t1) {
                return calendar.get(Calendar.HOUR) - t1.get(Calendar.HOUR);
            }
        });
        Collections.sort(allAlarmDays, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar calendar, Calendar t1) {
                return calendar.get(Calendar.DATE) - t1.get(Calendar.DATE);
            }
        });
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                int hour = allAlarmDays.get(0).get(Calendar.HOUR);
                int minutes = allAlarmDays.get(0).get(Calendar.MINUTE);
                int AMorPM = allAlarmDays.get(0).get(Calendar.AM_PM);
                String fullDayTime = dayFormat.format(allAlarmDays.get(0).getTime()) + "_" + String.valueOf(hour) + "_" + String.valueOf(minutes) + "_" + String.valueOf(AMorPM);
                String hourFormat = "";
                if (AMorPM == Calendar.AM){
                    hourFormat = "AM";
                }else {
                    hourFormat = "PM";
                }
                if (dayFormat.format(allAlarmDays.get(0).getTime()).equals(dayFormat.format(Calendar.getInstance().getTime()))){
                    tvReminder.setText("Today @ " + String.format("%02d",hour) + ":" + String.format("%02d",minutes) + " " + hourFormat);
                }else {
                    tvReminder.setText(dayFormat.format(allAlarmDays.get(0).getTime())+ " @ " + String.format("%02d",hour) + ":" + String.format("%02d",minutes) + " " + hourFormat);
                }
            }

    public int getDayOfWeek(String day) {
        switch (day) {
            case "Monday":
                return 0;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            case "Saturday":
                return 5;
            case "Sunday":
                return 6;
            default:
                return 0;
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
    }
}
