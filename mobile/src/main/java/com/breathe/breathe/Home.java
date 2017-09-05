package com.breathe.breathe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.math.BigDecimal;

public class Home extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPreference";
    SharedPreferences sharedpreferences;

    TextView tvAppName;
    RelativeLayout rlFifteenSecs;
    RelativeLayout rlThirtySecs;
    RelativeLayout rlSixtySecs;
    LinearLayout llTimeDuration;

    TextView tvTimeSpent;
    TextView tvTimeTotal;

    Button btnSetMyself;
    Button btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        rlFifteenSecs = (RelativeLayout) findViewById(R.id.rlFifteenSecs);
        rlThirtySecs = (RelativeLayout) findViewById(R.id.rlThirtySecs);
        rlSixtySecs = (RelativeLayout) findViewById(R.id.rlSixtySecs);

        tvAppName = (TextView) findViewById(R.id.home_tvAppName);
        btnSetMyself = (Button)findViewById(R.id.home_btnSetMyself);
        btnRandom = (Button)findViewById(R.id.home_btnRandom);

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

        btnSetMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, RemindMeAdd.class));
            }
        });

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
