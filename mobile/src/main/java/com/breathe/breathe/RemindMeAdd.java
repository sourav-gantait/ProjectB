package com.breathe.breathe;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import static com.breathe.breathe.Home.MyPREFERENCES;

public class RemindMeAdd extends Activity {
    private final String DATA_TAG = "data_reminder";
    SharedPreferences sharedpreferences;
    NumberPicker npSeconds;
    NumberPicker npHours;
    NumberPicker npMinutes;
    NumberPicker npAMorPM;

    LinearLayout llDayMon;
    LinearLayout llDayTue;
    LinearLayout llDayWed;
    LinearLayout llDayThu;
    LinearLayout llDayFri;
    LinearLayout llDaySat;
    LinearLayout llDaySun;

    Button btnSave;
    Button btnCancel;

    boolean isSelectedMon = false;
    boolean isSelectedTue = false;
    boolean isSelectedWed = false;
    boolean isSelectedThu = false;
    boolean isSelectedFri = false;
    boolean isSelectedSat = false;
    boolean isSelectedSun = false;

    String id = "";
    String editId = "";
    int position = 0;
    String seconds = "10 seconds";
    String hour = "1";
    String mins = "00";
    String timePeriod = "AM";

    final String[] hours = new String[12];
    final String[] minutes = new String[60];
    final String[] amOrPm = new String[]{"AM", "PM"};
    final String[] durations = {"10 seconds", "15 seconds", "20 seconds", "30 seconds", "40 seconds", "50 seconds", "60 seconds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me_add);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        if (getIntent().getStringExtra("id") != null) {
            editId = bundle.getString("id");
        }
        Log.d("editid-add-->", "======="+editId);
        boolean isRandom = sharedpreferences.getBoolean("isRandom", false);
        if (isRandom) {
            startActivity(new Intent(RemindMeAdd.this, ReminderList.class));
            finish();
        }
        initLayout();

        llDayMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedMon) {
                    llDayMon.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedMon = false;
                } else {
                    llDayMon.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedMon = true;
                }
            }
        });

        llDayTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedTue) {
                    llDayTue.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedTue = false;
                } else {
                    llDayTue.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedTue = true;
                }
            }
        });

        llDayWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedWed) {
                    llDayWed.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedWed = false;
                } else {
                    llDayWed.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedWed = true;
                }
            }
        });

        llDayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedThu) {
                    llDayThu.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedThu = false;
                } else {
                    llDayThu.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedThu = true;
                }
            }
        });

        llDayFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedFri) {
                    llDayFri.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedFri = false;
                } else {
                    llDayFri.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedFri = true;
                }
            }
        });

        llDaySat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedSat) {
                    llDaySat.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedSat = false;
                } else {
                    llDaySat.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedSat = true;
                }
            }
        });

        llDaySun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectedSun) {
                    llDaySun.setBackgroundResource(R.drawable.circle_day_inactive);
                    isSelectedSun = false;
                } else {
                    llDaySun.setBackgroundResource(R.drawable.circle_day_active);
                    isSelectedSun = true;
                }
            }
        });
    }

    private void initLayout() {
        TextView tvRemindMe = (TextView) findViewById(R.id.remindMe_tvHeader);
        npSeconds = (NumberPicker) findViewById(R.id.remindMe_npSeconds);
        npHours = (NumberPicker) findViewById(R.id.remindMe_npHours);
        npMinutes = (NumberPicker) findViewById(R.id.remindMe_npMinutes);
        npAMorPM = (NumberPicker) findViewById(R.id.remindMe_npAMorPM);

        llDayMon = (LinearLayout) findViewById(R.id.remindMe_llDayMon);
        llDayTue = (LinearLayout) findViewById(R.id.remindMe_llDayTue);
        llDayWed = (LinearLayout) findViewById(R.id.remindMe_llDayWed);
        llDayThu = (LinearLayout) findViewById(R.id.remindMe_llDayThu);
        llDayFri = (LinearLayout) findViewById(R.id.remindMe_llDayFri);
        llDaySat = (LinearLayout) findViewById(R.id.remindMe_llDaySat);
        llDaySun = (LinearLayout) findViewById(R.id.remindMe_llDaySun);

        btnSave = (Button) findViewById(R.id.remindMe_btnSave);
        btnCancel = (Button) findViewById(R.id.remindMe_btnCancel);

        SharedPreferences storedSF = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final Gson gson = new Gson();
        String storedJsonData = storedSF.getString(DATA_TAG, "");
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        ArrayList<HashMap<String, String>> alReminder = gson.fromJson(storedJsonData, type);

        final ArrayList<HashMap<String, String>> dataSet = new ArrayList<>();

        final Set<Integer> ids = new HashSet<>();

        if (!storedJsonData.equals("")) {
            if (alReminder.size() == 3 && (id.equals("") || id == null)) {
                startActivity(new Intent(RemindMeAdd.this, ReminderList.class));
                finish();
            } else {
                for (int i = 0; i < alReminder.size(); i++) {
                    HashMap<String, String> hmap = alReminder.get(i);
                    dataSet.add(hmap);
                    ids.add(Integer.parseInt(alReminder.get(i).get("id")));
                    if ((editId.equals("") || editId == null) && editId.equals(alReminder.get(i).get("id"))) {
                        Log.d("editId_in_Add--->", "======="+editId);
                        position = i;
                    }
                }
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editId.equals("") || editId == null) {
                    int randomNumber = (int) Math.random() * 1;
                    createRandom(ids, randomNumber);
                } else {
                    id = editId;
                }
                addToReminderList(gson, dataSet, id, position);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SpannableString remindMe = new SpannableString("Remind me.");
        remindMe.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 9, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRemindMe.setText(remindMe);

        setNumberPickerTextColor(npSeconds);
        npSeconds.setMinValue(0);
        npSeconds.setMaxValue(durations.length - 1);
        npSeconds.setDisplayedValues(durations);
        npSeconds.setWrapSelectorWheel(true);
        npSeconds.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        for (int i = 0; i < 12; i++) {
            hours[i] = String.valueOf(i + 1);
        }
        setNumberPickerTextColor(npHours);
        npHours.setMinValue(0);
        npHours.setMaxValue(hours.length - 1);
        npHours.setDisplayedValues(hours);
        npHours.setWrapSelectorWheel(true);
//        npHours.setValue(11);
        npHours.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", (i));
        }
        setNumberPickerTextColor(npMinutes);
        npMinutes.setMinValue(0);
        npMinutes.setMaxValue(minutes.length - 1);
        npMinutes.setDisplayedValues(minutes);
        npMinutes.setWrapSelectorWheel(true);
//        npMinutes.setValue(11);
        npMinutes.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        setNumberPickerTextColor(npAMorPM);
        npAMorPM.setMinValue(0);
        npAMorPM.setMaxValue(amOrPm.length - 1);
        npAMorPM.setDisplayedValues(amOrPm);
        npAMorPM.setWrapSelectorWheel(true);
        npAMorPM.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        npSeconds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                seconds = durations[newVal];
            }
        });
        npHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                hour = hours[newVal];
            }
        });
        npMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                mins = minutes[newVal];
            }
        });
        npAMorPM.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                timePeriod = amOrPm[newVal];
            }
        });
        if (null != alReminder)
            getExistValues(alReminder);
    }

    private void getExistValues(ArrayList<HashMap<String, String>> alRemonder) {
        for (int i = 0; i < alRemonder.size(); i++) {
            HashMap<String, String> map = alRemonder.get(i);
            String id = map.get("id");
            Log.d("idexist--->", "======"+id);
            if (id.equals(editId)) {
                String days = map.get("days");
                String duration = map.get("duration");
                String hour = map.get("hour");
                String minutes = map.get("minutes");
                String timeperiod = map.get("timeperiod");
                position = i;
                Log.d("position--->", "======="+String.valueOf(position));
                setExistValuesToLayout(days, duration, hour, minutes, timeperiod);
            }
        }
    }

    private void setExistValuesToLayout(String days, String duration, String hour, String minute, String hourFormat) {
        Log.d("existValues---->", duration + "---:---" + hour + "---:---" + minute + "--:--" + hourFormat);
        String[] weekDays = days.split(",");
        if (weekDays[0].equals("1")) {
            llDayMon.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedMon = true;
        }
        if (weekDays[1].equals("1")) {
            llDayTue.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedTue = true;
        }
        if (weekDays[2].equals("1")) {
            llDayWed.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedWed = true;
        }
        if (weekDays[3].equals("1")) {
            llDayThu.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedThu = true;
        }
        if (weekDays[4].equals("1")) {
            llDayFri.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedFri = true;
        }
        if (weekDays[5].equals("1")) {
            llDaySat.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedSat = true;
        }
        if (weekDays[6].equals("1")) {
            llDaySun.setBackgroundResource(R.drawable.circle_day_active);
            isSelectedSun = true;
        }
        int durationSetValue = Arrays.asList(durations).indexOf(duration);
        npSeconds.setValue(durationSetValue);
        seconds = durations[durationSetValue];
        /*for (int i = 0; i<durations.length; i++){
            if (duration.equals(durations[i])){
                npSeconds.setValue(i);
            }
        }*/
        int hourValue = Arrays.asList(hours).indexOf(hour);
        npHours.setValue(hourValue);
        this.hour = hours[hourValue];
        /*for (int i = 0; i < hours.length; i++) {
            if (hour.equals(hours[i])) {
                npHours.setValue(i);
            }
        }*/
        int minValue = Arrays.asList(minutes).indexOf(minute);
        npMinutes.setValue(minValue);
        this.mins = minutes[minValue];
        /*for (int j = 0; j < minutes.length; j++) {
            if (minute.equals(minutes[j])) {
                npMinutes.setValue(j);
            }
        }*/
        int timePeriodValue = Arrays.asList(amOrPm).indexOf(hourFormat);
        npAMorPM.setValue(timePeriodValue);
        this.timePeriod = amOrPm[timePeriodValue];
        /*for (int k = 0; k < amOrPm.length; k++) {
            if (timePeriod.equals(amOrPm[k])) {
                npAMorPM.setValue(k);
            }
        }*/
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
                    pf.set(numberPicker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(Color.WHITE);
                    ((EditText) child).setTextColor(Color.WHITE);
//                    ((EditText) child).setTextSize(20.0f);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                }
            }
        }
        return false;
    }

    private void addToReminderList(Gson gson, ArrayList<HashMap<String, String>> alReminder, String id, int position) {
        Log.d("position--->", "======="+String.valueOf(position));
        Log.d("id--->", "======="+id);
        String dayMon = "0";
        String dayTue = "0";
        String dayWed = "0";
        String dayThu = "0";
        String dayFri = "0";
        String daySat = "0";
        String daySun = "0";
        if (isSelectedMon) {
            dayMon = "1";
        }
        if (isSelectedTue) {
            dayTue = "1";
        }
        if (isSelectedWed) {
            dayWed = "1";
        }
        if (isSelectedThu) {
            dayThu = "1";
        }
        if (isSelectedFri) {
            dayFri = "1";
        }
        if (isSelectedSat) {
            daySat = "1";
        }
        if (isSelectedSun) {
            daySun = "1";
        }
        if (dayMon.equals("0") && dayTue.equals("0") && dayWed.equals("0") && dayThu.equals("0") && dayFri.equals("0") && daySat.equals("0") && daySun.equals("0")) {
            Toast.makeText(RemindMeAdd.this, "Select at least one day of th week!", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("days", dayMon + "," + dayTue + "," + dayWed + "," + dayThu + "," + dayFri + "," + daySat + "," + daySun);
            hashMap.put("duration", seconds);
            hashMap.put("hour", hour);
            hashMap.put("minutes", mins);
            hashMap.put("timeperiod", timePeriod);
            hashMap.put("active", "yes");
            if (!editId.equals("") && editId != null) {
                alReminder.set(position, hashMap);
            } else {
                alReminder.add(hashMap);
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String json = gson.toJson(alReminder);
            editor.putString(DATA_TAG, json);
            editor.commit();
            /*for(int i = 0; i<alReminder.size(); i++){
                setReminderNotification(alReminder.get(i));
            }*/
            ReminderList.reminderList.finish();
            Intent intent = new Intent(RemindMeAdd.this, ReminderList.class);
            startActivity(intent);
            finish();
        }
    }

    public void createRandom(Set<Integer> ids, int randomNumber) {
        if (!ids.contains(randomNumber)) {
            id = String.valueOf(randomNumber);
        } else {
            randomNumber++;
            createRandom(ids, randomNumber);
        }
    }
/*
    private void setReminderNotification(HashMap<String, String> hashMap){

        String id = hashMap.get("id");
        String days = hashMap.get("days");
        String duration = hashMap.get("duration");
        String hour = hashMap.get("hour");
        String minutes = hashMap.get("minutes");
        String timeperiod = hashMap.get("timeperiod");

        String[] daysArray = days.split(",");
        Log.e("daysArray==>", daysArray.toString());
        if (daysArray[0].equals("1")){
            setNotification(Calendar.MONDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[1].equals("1")){
            setNotification(Calendar.TUESDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[2].equals("1")){
            setNotification(Calendar.WEDNESDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[3].equals("1")){
            setNotification(Calendar.THURSDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[4].equals("1")){
            setNotification(Calendar.FRIDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[5].equals("1")){
            setNotification(Calendar.SATURDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
        if (daysArray[6].equals("1")){
            setNotification(Calendar.SUNDAY, Integer.parseInt(minutes), Integer.parseInt(hour), timeperiod, duration);
        }
    }

    private void setNotification(int weekDay, int minutes, int hour, String timeperiod, String duration){
        Intent myIntent = new Intent(getApplicationContext() , AlarmNotificationReceiver.class);
        myIntent.putExtra("duration", duration);
        Log.e("duration==>", "=="+duration);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 123, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, weekDay);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.HOUR, hour);
        if (timeperiod.equals("AM")) {
            calendar.set(Calendar.AM_PM, Calendar.AM);
            Log.e("timeperiod=AM=>", "OK");
        }else {
            calendar.set(Calendar.AM_PM, Calendar.PM);
            Log.e("timeperiod=PM=>", "OK");
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        *//*Log.e("week==>", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
        Log.e("week==>", String.valueOf(week));*//*

        Log.e("hour==>", String.valueOf(hour));

        Log.e("minutes==>", String.valueOf(minutes));

        Log.e("notification==>", String.valueOf(calendar.getTimeInMillis()));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7 , pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }*/
}
