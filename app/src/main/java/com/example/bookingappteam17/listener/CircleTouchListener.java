package com.example.bookingappteam17.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.bookingappteam17.fragments.FilterFragment;

public class CircleTouchListener implements View.OnTouchListener {

    private boolean isMinThumb;
    private FilterFragment filterFragment;

    public CircleTouchListener(FilterFragment filterFragment, boolean isMinThumb) {
        this.filterFragment = filterFragment;
        this.isMinThumb = isMinThumb;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true; // Return true to indicate interest in subsequent events
            case MotionEvent.ACTION_MOVE:
                Log.d("My position", String.valueOf(view.getX()));
                Log.d("Move position", String.valueOf(motionEvent.getX()));
                handleThumbMovement(motionEvent.getX());
                return true;
            default:
                return false;
        }
    }

    private void handleThumbMovement(float x) {

        if (isMinThumb) {
            float value = filterFragment.calculateValueFromPositionMin(x);
            filterFragment.setMinValue(value);
            // Update the position of minThumb based on the calculated value
            filterFragment.updateMinThumbPosition(x);
        } else {
            float value = filterFragment.calculateValueFromPositionMax(x);
            filterFragment.setMaxValue(value);
            // Update the position of maxThumb based on the calculated value
            filterFragment.updateMaxThumbPosition(x);
        }

        filterFragment.updateTextViews();
    }

}

