package com.breathe.breathe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by Souren on 03/01/2018.
 */

public class CustomNumberPicker extends NumberPicker {

        public CustomNumberPicker(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void addView(View child) {
            super.addView(child);
            updateView(child);
        }

        @Override
        public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, index, params);
            updateView(child);
        }

        @Override
        public void addView(View child, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, params);
            updateView(child);
        }

        private void updateView(View view) {
            if(view instanceof EditText){
                ((EditText) view).setTextSize(20);
                ((EditText) view).setTextColor(Color.parseColor("#FFFFFF"));
            }

        }

}
