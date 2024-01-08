package com.example.bookingappteam17.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.bookingappteam17.fragments.accommodation.AccommodationFilterFragment;

public class CircleTouchListener implements View.OnTouchListener {

    private boolean isMinThumb;
    private AccommodationFilterFragment accommodationFilterFragment;

    public CircleTouchListener(AccommodationFilterFragment accommodationFilterFragment, boolean isMinThumb) {
        this.accommodationFilterFragment = accommodationFilterFragment;
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
            float value = accommodationFilterFragment.calculateValueFromPositionMin(x);
            accommodationFilterFragment.setMinValue(value);
            // Update the position of minThumb based on the calculated value
            accommodationFilterFragment.updateMinThumbPosition(x);
        } else {
            float value = accommodationFilterFragment.calculateValueFromPositionMax(x);
            accommodationFilterFragment.setMaxValue(value);
            // Update the position of maxThumb based on the calculated value
            accommodationFilterFragment.updateMaxThumbPosition(x);
        }

        accommodationFilterFragment.updateTextViews();
    }

}

