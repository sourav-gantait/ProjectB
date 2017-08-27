package com.breathe.breathe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_TIME_LENGTH = 3000;

    TextView tvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tvAppName = (TextView)findViewById(R.id.splashScreen_tvAppName);
        SpannableString appName = new SpannableString("breathe.");
        appName.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAppName.setText(appName);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, Home.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIME_LENGTH);
    }
}
