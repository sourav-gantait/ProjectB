package com.breathe.breathe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;

import static com.breathe.breathe.Home.MyPREFERENCES;

public class ReminderList extends AppCompatActivity {
    private final String DATA_TAG = "data_reminder";
    TextView tvNoOfReminders;
    TextView tvNoReminders;
    LinearLayout llList;
    Button btnHome;
    Button btnAdd;
    ArrayList<HashMap<String, String>> alReminder;
    SharedPreferences sharedpreferences;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        TextView tvRemindMe = (TextView) findViewById(R.id.reminderList_tvHeader);
        tvNoOfReminders = (TextView) findViewById(R.id.reminderList_tvNoOfReminders);
        tvNoReminders = (TextView) findViewById(R.id.reminderList_tvNoReminderss);
        llList = (LinearLayout) findViewById(R.id.reminderList_llList);
        btnHome = (Button)findViewById(R.id.reminderList_btnHome);
        btnAdd = (Button)findViewById(R.id.reminderList_btnAdd);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderList.this, Home.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderList.this, RemindMeAdd.class));
            }
        });

        SpannableString remindMe = new SpannableString("Remind me.");
        remindMe.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 9, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRemindMe.setText(remindMe);

        SharedPreferences storedSF = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String storedJsonData = storedSF.getString(DATA_TAG, null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        alReminder = gson.fromJson(storedJsonData, type);
        if (alReminder.size() > 0) {
            for (int i = 0; i < alReminder.size(); i++) {
                HashMap<String, String> hashMap = alReminder.get(i);
                String id = hashMap.get("id");
                String days = hashMap.get("days");
                String duration = hashMap.get("duration");
                String hour = hashMap.get("hour");
                String minutes = hashMap.get("minutes");
                String timeperiod = hashMap.get("timeperiod");
                addReminder(id, days, duration, hour, minutes, timeperiod);
            }
        }
        if (alReminder.size() == 3){
            btnAdd.setEnabled(false);
            btnAdd.setBackgroundResource(R.drawable.btn_border_disable);
            btnAdd.setTextColor(getResources().getColor(R.color.colorGrey));
        }

    }

    private void addReminder(final String id, String days, String duration, String hour, String minutes, String timeperiod) {
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
        RelativeLayout rlEdit = (RelativeLayout) rowReminder.findViewById(R.id.rowReminder_rlEdit);
        RelativeLayout rlDelete = (RelativeLayout) rowReminder.findViewById(R.id.rowReminder_rlDelete);

        duration = duration.substring(0, 6);
        tvTime.setText(hour+":"+minutes+" "+timeperiod);
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
        final int position = llList.getChildCount()-1;
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
    }

    private void deleteReminder(int position, String id){
        for (int i = 0; i< llList.getChildCount(); i++){
            HashMap<String, String> hashMap = alReminder.get(i);
            String storedId = hashMap.get("id");
            if (storedId.equals(id)){
                alReminder.remove(i);
                llList.removeViewAt(i);
            }
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String json = gson.toJson(alReminder);
        editor.putString(DATA_TAG, json);
        editor.commit();

        if (alReminder.size() < 3){
            btnAdd.setEnabled(true);
            btnAdd.setBackgroundResource(R.drawable.btn_border);
            btnAdd.setTextColor(Color.WHITE);
        }
        if (alReminder.size() == 0){
            tvNoReminders.setVisibility(View.VISIBLE);
        }else {
            tvNoReminders.setVisibility(View.GONE);
        }
    }
    private void editReminder(int position, String id){
        /*for (int i = 0; i< llList.getChildCount(); i++){
            HashMap<String, String> hashMap = alReminder.get(i);
            String storedId = hashMap.get("id");
            if (storedId.equals(id)){
                alReminder.remove(i);
                llList.removeViewAt(i);
            }
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String json = gson.toJson(alReminder);
        Log.e("alReminder =>", json);
        editor.putString(DATA_TAG, json);
        editor.commit();*/
        Intent intent = new Intent(ReminderList.this, RemindMeAdd.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
