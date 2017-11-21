package com.breathe.breathe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.breathe.breathe.R.color.colorRedDot;


public class Meditation extends AppCompatActivity {
    RelativeLayout rlActivityMeditation;
    RelativeLayout rlMeditationActive;
    //    TableLayout tl_time;
    private boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private boolean isCanceled = false;

    private long timeRemaining = 0;
    private long totalTimeSpent = 0;

    String durationTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        rlActivityMeditation = (RelativeLayout)findViewById(R.id.activity_meditation);
        rlMeditationActive = (RelativeLayout)findViewById(R.id.meditation_active);
        final TextView tvMeditationInstruction = (TextView)findViewById(R.id.meditation_tvInstruction);

        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                tvMeditationInstruction.setVisibility(View.GONE);
            }
        }, 3000);

        Bundle bundle = getIntent().getExtras();
        final int durationTime = bundle.getInt("duration", 0);
        Log.e("durationTime==>", "hgjh==="+durationTime);
//        if (durationTime == 15 || durationTime == 30 || durationTime == 60) {
        if (durationTime != 0) {
            startTimer(durationTime);
        }else {
            finish();
        }
//        }

        rlActivityMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPaused = true;
                showMeditationPause(totalTimeSpent, durationTime);
            }
        });
    }

    public void startTimer(final int duration) {
        CountDownTimer timer;
        long millisInFuture = 1000;
        long countDownInterval = 1000; //1 second
        millisInFuture = duration * 1000;
        //Initialize a new CountDownTimer instance
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //do something in every tick
                if (isPaused || isCanceled) {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                } else {
                    //Display the remaining seconds to app interface
                    //1 second = 1000 milliseconds
                    totalTimeSpent = (duration - millisUntilFinished / 1000) + 1;
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                //Do something when count down finished
                Intent intent = new Intent();
                intent.putExtra("result", totalTimeSpent);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }.start();
    }

    public void resumeTimer(final long totalTime) {
        long millisInFuture = timeRemaining;
        long countDownInterval = 1000;
        new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //Do something in every tick
                if (isPaused || isCanceled) {
                    //If user requested to pause or cancel the count down timer
                    cancel();
                } else {
                    long resumeTime = totalTime - millisUntilFinished / 1000 + 1;
                    totalTimeSpent = resumeTime;
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                //Do something when count down finished
                Intent intent = new Intent();
                intent.putExtra("result", totalTimeSpent);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }.start();
    }

    private void showMeditationPause(final long time, final long totalTime){
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        final View meditationPauseView = inflater.inflate(R.layout.meditation_pause, null);
        TextView tvPause = (TextView)meditationPauseView.findViewById(R.id.meditatiionPause_tvPause);
        TextView tvTimeSpentDuration = (TextView)meditationPauseView.findViewById(R.id.meditatiionPause_tvTimeSpentDuration);
        TextView tvTimeRemainingDuration = (TextView)meditationPauseView.findViewById(R.id.meditatiionPause_tvTimeRemainingDuration);
        Button btnStop = (Button)meditationPauseView.findViewById(R.id.meditatiionPause_btnStop);
        Button btnContinue = (Button)meditationPauseView.findViewById(R.id.meditatiionPause_btnContinue);

        SpannableString pause = new SpannableString("Pause.");
        pause.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedDot)), 5, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPause.setText(pause);
        rlMeditationActive.setVisibility(View.GONE);
        rlActivityMeditation.addView(meditationPauseView);

        long timeRemaining = totalTime - time;
        tvTimeSpentDuration.setText("00:"+String.format("%02d", time));
        tvTimeRemainingDuration.setText("00:"+String.format("%02d", timeRemaining));

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meditationPauseView.setVisibility(View.GONE);
                rlMeditationActive.setVisibility(View.VISIBLE);
                isPaused = false;
                resumeTimer(totalTime);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("result", totalTimeSpent);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
