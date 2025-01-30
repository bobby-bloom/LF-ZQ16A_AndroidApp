package com.kotbros.android_components;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class PlusMinusSelector {

    private static final int DELAY_TOUCH = 200;

    private final Handler handler = new Handler();

    private final AtomicInteger value;
    private final int minValue;
    private final int maxValue;

    private final ImageView plusIcon;
    private final ImageView minusIcon;

    private final Consumer<Integer> callback;

    public PlusMinusSelector(int initialValue, int minValue, int maxValue, ImageView plusIcon, ImageView minusIcon, Consumer<Integer> callback) {
        this.value = new AtomicInteger(initialValue);
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.plusIcon = plusIcon;
        this.minusIcon = minusIcon;

        this.callback = callback;
    }

    public void initialize() {
        setUpTouchListener(minusIcon, () -> updateValue(value.decrementAndGet()));
        setUpTouchListener(plusIcon, () -> updateValue(value.incrementAndGet()));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpTouchListener(ImageView icon, Runnable updateAction) {
        icon.setOnTouchListener(new View.OnTouchListener() {
            private final Runnable runnable = createRunnable(updateAction);

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handleOnTouch(event, runnable);
            }
        });
    }

    private boolean handleOnTouch(MotionEvent event, Runnable runnable) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.post(runnable);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacksAndMessages(null);
                return true;
        }
        return false;
    }

    private Runnable createRunnable(Runnable updateAction) {
        return new Runnable() {
            @Override
            public void run() {
                updateAction.run();
                handler.postDelayed(this, DELAY_TOUCH);
            }
        };
    }

    private void updateValue(int newValue) {
        if (newValue < minValue || newValue > maxValue) {
            return;
        }
        this.value.set(newValue);
        callback.accept(newValue);
    }
}
