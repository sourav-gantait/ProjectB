package com.breathe.breathe;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import static com.breathe.breathe.Home.MyPREFERENCES;

public class ReminderList extends AppCompatActivity {
    private final String DATA_TAG = "data_reminder";
    private final String DATA_REQUEST_CODE = "data_request_code";
    TextView tvNoOfReminders;
    TextView tvNoReminders;
    LinearLayout llList;
    LinearLayout llHome;
    LinearLayout llRandom;
    LinearLayout llAdd;
    LinearLayout llRandomActive;
    ArrayList<HashMap<String, String>> alReminder = new ArrayList<>();
    Set<String> setRequestCodes;
    int requestCode = 1;
    SharedPreferences sharedpreferences;
    Gson gson = new Gson();
    boolean isRandom = false;
    public static Activity reminderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        reminderList = this;

        SharedPreferences storedSF = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String storedJsonData = storedSF.getString(DATA_TAG, null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        alReminder = gson.fromJson(storedJsonData, type);
        isRandom = storedSF.getBoolean("isRandom", false);
        setRequestCodes = storedSF.getStringSet(DATA_REQUEST_CODE, new HashSet<String>());

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        TextView tvRemindMe = (TextView) findViewById(R.id.reminderList_tvHeader);
        tvNoOfReminders = (TextView) findViewById(R.id.reminderList_tvNoOfReminders);
        tvNoReminders = (TextView) findViewById(R.id.reminderList_tvNoReminders);
        llList = (LinearLayout) findViewById(R.id.reminderList_llList);
        llHome = (LinearLayout) findViewById(R.id.reminderList_llHome);
        llRandom = (LinearLayout) findViewById(R.id.reminderList_llRandom);
        llAdd = (LinearLayout) findViewById(R.id.reminderList_llAdd);
        llRandomActive = (LinearLayout) findViewById(R.id.reminderList_llRandomActive);

        SpannableString remindMe = new SpannableString("Remind me.");
        remindMe.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 9, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRemindMe.setText(remindMe);

        if (isRandom) {
            llRandomActive.setVisibility(View.VISIBLE);
            llRandom.setBackgroundResource(R.drawable.bg_btn_random);

            llAdd.setEnabled(false);
            llAdd.setBackgroundResource(R.drawable.btn_border_disable);
            ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(getResources().getColor(R.color.colorBtnGrey));
            llList.setVisibility(View.GONE);

            tvNoReminders.setVisibility(View.VISIBLE);
            tvNoReminders.setText("You have set your reminders to random.\nYou will get a reminder between 8:00AM - 9:00PM once a day for 15 Seconds.");
//            for (int i = 0; i<alRequestCodes.size(); i++){
//                String requestCode = alRequestCodes.get(i);
//                int reqCode = Integer.parseInt(requestCode);
//                //Cancel all previous alarmmanager//
//                Intent myIntent = new Intent(getApplicationContext() , AlarmNotificationReceiver.class);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                PendingIntent pendingIntent = PendingIntent.getActivity(ReminderList.this, reqCode, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//                alarmManager.cancel(pendingIntent);
//            }
        } else {
            llRandomActive.setVisibility(View.GONE);
            llRandom.setBackgroundResource(R.drawable.btn_border);

            llAdd.setEnabled(true);
            llAdd.setBackgroundResource(R.drawable.btn_border);
            ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(getResources().getColor(R.color.colorWhite));
            llList.setVisibility(View.VISIBLE);

            tvNoReminders.setVisibility(View.VISIBLE);
            tvNoReminders.setText("You have no reminders.");
//            for (int i =0; i < alReminder.size(); i++){
//                setReminderNotification(alReminder.get(i), requestCode);
//            }
        }

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderList.this, Home.class));
                finish();
            }
        });

        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderList.this, RemindMeAdd.class));
            }
        });

        llRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRandom) {
                    llRandomActive.setVisibility(View.GONE);
                    llRandom.setBackgroundResource(R.drawable.btn_border);

                    llAdd.setEnabled(true);
                    llAdd.setBackgroundResource(R.drawable.btn_border);
                    ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(getResources().getColor(R.color.colorWhite));
                    if (llList.getChildCount() > 0) {
                        llList.setVisibility(View.VISIBLE);
                        tvNoReminders.setVisibility(View.GONE);
                    } else {
                        tvNoReminders.setVisibility(View.VISIBLE);
                        tvNoReminders.setText("You have no reminders.");
                    }
                    for (int i = 0; i < alReminder.size(); i++) {
                        setReminderNotification(alReminder.get(i), requestCode);
                        requestCode++;
                    }
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("isRandom", false);
                    editor.commit();
                    isRandom = false;
                    setRandomReminder(isRandom);
                } else {
                    llRandomActive.setVisibility(View.VISIBLE);
                    llRandom.setBackgroundResource(R.drawable.bg_btn_random);

                    llAdd.setEnabled(false);
                    llAdd.setBackgroundResource(R.drawable.btn_border_disable);
                    ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(getResources().getColor(R.color.colorGrey));
                    llList.setVisibility(View.GONE);

                    tvNoReminders.setVisibility(View.VISIBLE);
                    tvNoReminders.setText("You have set your reminders to random.\nYou will get a reminder between 8:00AM - 9:00PM once a day for 15 Seconds.");
                    for (String requestCode : setRequestCodes) {
//                        String requestCode = alRequestCodes.;
                        int reqCode = Integer.parseInt(requestCode);
                        //Cancel all previous alarmmanager//
                        Intent myIntent = new Intent(getApplicationContext(), AlarmNotificationReceiver.class);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getActivity(ReminderList.this, reqCode, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.cancel(pendingIntent);
                    }
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("isRandom", true);
                    editor.commit();
                    isRandom = true;
                    setRandomReminder(isRandom);
                }

            }
        });


        if (null != alReminder && alReminder.size() > 0) {
            /*//Cancel all previous alarmmanager//
            Intent myIntent = new Intent(getApplicationContext() , AlarmNotificationReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(ReminderList.this, 123, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);*/
            tvNoReminders.setVisibility(View.GONE);
            setReminders();

            for (int i = 0; i < alReminder.size(); i++) {
                HashMap<String, String> hashMap = alReminder.get(i);
                String id = hashMap.get("id");
                String days = hashMap.get("days");
                String duration = hashMap.get("duration");
                String hour = hashMap.get("hour");
                String minutes = hashMap.get("minutes");
                String timeperiod = hashMap.get("timeperiod");
                String active = hashMap.get("active");
                addReminder(id, days, duration, hour, minutes, timeperiod, active);
//                setReminderNotification(alReminder.get(i));
            }
        }
        if (null != alReminder && alReminder.size() == 3) {
            llAdd.setEnabled(false);
            llAdd.setBackgroundResource(R.drawable.btn_border_disable);
            ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(getResources().getColor(R.color.colorGrey));
        }

    }

    private void addReminder(final String id, String days, String duration, String hour, String minutes, String timeperiod, String active) {
        String daysOfWeek[] = days.split(",");
        View rowReminder = getLayoutInflater().inflate(R.layout.row_reminder, null, false);
        TextView tvTime = (TextView) rowReminder.findViewById(R.id.rowReminder_tvTime);
        TextView tvDuration = (TextView) rowReminder.findViewById(R.id.rowReminder_tvDuration);
        LinearLayout llDayMon = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDayMon);
        LinearLayout llDayTue = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDayTue);
        LinearLayout llDayWed = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDayWed);
        LinearLayout llDayThu = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDayThu);
        LinearLayout llDayFri = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDayFri);
        LinearLayout llDaySat = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDaySat);
        LinearLayout llDaySun = (LinearLayout) rowReminder.findViewById(R.id.rowReminder_llDaySun);
        SwitchButton sbActive = (SwitchButton) rowReminder.findViewById(R.id.rowReminder_sbActive);
        RelativeLayout rlEdit = (RelativeLayout) rowReminder.findViewById(R.id.rowReminder_rlEdit);
        RelativeLayout rlDelete = (RelativeLayout) rowReminder.findViewById(R.id.rowReminder_rlDelete);

        duration = duration.substring(0, 6);
        tvTime.setText(hour + ":" + minutes + " " + timeperiod);
        tvDuration.setText(duration);
        if (daysOfWeek[0].equals("1")) {
            llDayMon.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[1].equals("1")) {
            llDayTue.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[2].equals("1")) {
            llDayWed.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[3].equals("1")) {
            llDayThu.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[4].equals("1")) {
            llDayFri.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[5].equals("1")) {
            llDaySat.setBackgroundResource(R.drawable.circle_day_active);
        }
        if (daysOfWeek[6].equals("1")) {
            llDaySun.setBackgroundResource(R.drawable.circle_day_active);
        }
        llList.addView(rowReminder);
        final int position = llList.getChildCount() - 1;
        if (active.equals("yes")) {
            sbActive.setChecked(true);
        } else {
            sbActive.setChecked(false);
        }
        rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReminder(position, id);
            }
        });
        rlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editReminder(position, id);
            }
        });
        sbActive.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                setActive(id, isChecked);
            }
        });
    }

    private void setActive(String id, boolean isActive){
        for (int i = 0; i < llList.getChildCount(); i++) {
            HashMap<String, String> hashMap = alReminder.get(i);
            String storedId = hashMap.get("id");
            String days = hashMap.get("days");
            String duration = hashMap.get("duration");
            String hour = hashMap.get("hour");
            String minutes = hashMap.get("minutes");
            String timeperiod = hashMap.get("timeperiod");
            if (storedId.equals(id)) {
                if (isActive){
                    hashMap.put("active", "yes");
                }else {
                    hashMap.put("active", "no");
                }
                hashMap.put("id", storedId);
                hashMap.put("days", days);
                hashMap.put("duration", duration);
                hashMap.put("hour", hour);
                hashMap.put("minutes", minutes);
                hashMap.put("timeperiod", timeperiod);
                alReminder.set(i, hashMap);
            }
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String json = gson.toJson(alReminder);
        editor.putString(DATA_TAG, json);
        editor.commit();

        setReminders();
    }

    private void deleteReminder(int position, String id) {
        for (int i = 0; i < llList.getChildCount(); i++) {
            HashMap<String, String> hashMap = alReminder.get(i);
            String storedId = hashMap.get("id");
            if (storedId.equals(id)) {
                alReminder.remove(i);
                llList.removeViewAt(i);
            }
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String json = gson.toJson(alReminder);
        editor.putString(DATA_TAG, json);
        editor.commit();

        setReminders();

        if (alReminder.size() < 3) {
            llAdd.setEnabled(true);
            llAdd.setBackgroundResource(R.drawable.btn_border);
            ((TextView) findViewById(R.id.reminderList_tvAdd)).setTextColor(Color.WHITE);
        }
        if (alReminder.size() == 0) {
            tvNoReminders.setVisibility(View.VISIBLE);
            tvNoReminders.setText("You have no reminders.");
        } else {
            tvNoReminders.setVisibility(View.GONE);
        }
    }

    private void editReminder(int position, String id) {
        Intent intent = new Intent(ReminderList.this, RemindMeAdd.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setReminders() {
        if (null != setRequestCodes) {
            for (String requestCode : setRequestCodes) {
                int reqCode = Integer.parseInt(requestCode);
                //Cancel all previous alarmmanager//
                Intent myIntent = new Intent(ReminderList.this, AlarmNotificationReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(ReminderList.this, reqCode, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingIntent);
            }
            setRequestCodes.clear();
            requestCode = 1;
        }
        for (int i = 0; i < alReminder.size(); i++) {
            setReminderNotification(alReminder.get(i), requestCode);
            requestCode++;
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet(DATA_REQUEST_CODE, setRequestCodes);
        editor.commit();
    }

    private void setReminderNotification(HashMap<String, String> hashMap, int requestCode) {
//        int i = 1;
        Log.e("requestCode==>", String.valueOf(requestCode));
        String reqCode = String.valueOf(requestCode);
        setRequestCodes.add(reqCode);
        String id = hashMap.get("id");
        String days = hashMap.get("days");
        String duration = hashMap.get("duration");
        String hour = hashMap.get("hour");
        String minutes = hashMap.get("minutes");
        String timeperiod = hashMap.get("timeperiod");
        String active = hashMap.get("active");

        String[] daysArray = days.split(",");
        if (active.equals("yes")) {
            if (daysArray[0].equals("1")) {
                setNotification(Calendar.MONDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[1].equals("1")) {
                setNotification(Calendar.TUESDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[2].equals("1")) {
                setNotification(Calendar.WEDNESDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[3].equals("1")) {
                setNotification(Calendar.THURSDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[4].equals("1")) {
                setNotification(Calendar.FRIDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[5].equals("1")) {
                setNotification(Calendar.SATURDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
            if (daysArray[6].equals("1")) {
                setNotification(Calendar.SUNDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration, requestCode);
            }
        }
    }

    private void setNotification(int weekDay, int minutes, int hour, String timeperiod, String duration, int requestCode) {
        Intent myIntent = new Intent(ReminderList.this, AlarmNotificationReceiver.class);
        myIntent.putExtra("duration", duration);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderList.this, requestCode, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, weekDay);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minutes);
        if (timeperiod.equals("AM")) {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        } else {
            calendar.set(Calendar.AM_PM, Calendar.PM);
        }
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60*1000, AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    private void setRandomReminder(boolean isRandom){
        if (isRandom){
            Intent myIntent = new Intent(ReminderList.this, RandomReminderReceiver.class);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderList.this, 1001, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }else {
            Intent myIntent = new Intent(ReminderList.this, RandomReminderReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(ReminderList.this, 1001, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }
}
