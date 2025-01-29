package com.kotbros.android_app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BMIFragment extends Fragment {

    public static final int MAX_HEIGHT = 350;
    public static final int MAX_WEIGHT = 1000;
    public static final int MAX_AGE = 420;

    private boolean isMale = true;
    private int height;
    private int weight;
    private int age;

    private RelativeLayout btn_male;
    private RelativeLayout btn_female;

    private TextView txtView_height;
    private TextView txtView_weight;
    private TextView txtView_age;


    public BMIFragment() {}

    public static BMIFragment newInstance() {
        return new BMIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        height = Integer.parseInt(getString(R.string.bmi_default_height));
        weight = Integer.parseInt(getString(R.string.bmi_default_weight));
        age    = Integer.parseInt(getString(R.string.bmi_default_age));

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        btn_male       = view.findViewById(R.id.male_selection_frame);
        btn_female     = view.findViewById(R.id.female_selection_frame);
        txtView_height = view.findViewById(R.id.bmi_selection_height);
        txtView_weight = view.findViewById(R.id.bmi_selection_weight);
        txtView_age    = view.findViewById(R.id.bmi_selection_age);

        btn_male.setOnClickListener(v -> updateGender(true));
        btn_female.setOnClickListener(v -> updateGender(false));

        updateGender(isMale);

        initHeightSlider(view);
        initWeightSelector(view);
        initAgeSelector(view);

        return view;
    }

    private void initHeightSlider(View view) {
        SeekBar height_slider = view.findViewById(R.id.bmi_height_range_slider);

        height_slider.setMax(MAX_HEIGHT);
        height_slider.setProgress(height, true);

        height_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateHeight(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void initWeightSelector(View view) {
        ImageView removeIcon = view.findViewById(R.id.bmi_weight_decrement);
        ImageView addIcon    = view.findViewById(R.id.bmi_weight_increment);

        ArrayList<Integer> argList = new ArrayList<>();
        argList.add(weight);

        initSelectorBehaviour(removeIcon, addIcon, this::updateWeight, argList);
    }

    private void initAgeSelector(View view) {
        ImageView removeIcon = view.findViewById(R.id.bmi_age_decrement);
        ImageView addIcon    = view.findViewById(R.id.bmi_age_increment);

        ArrayList<Integer> argList = new ArrayList<>();
        argList.add(age);

        initSelectorBehaviour(removeIcon, addIcon, this::updateAge, argList);
    }

    private void initSelectorBehaviour(ImageView removeIcon, ImageView addIcon, Consumer<Integer> updateFn, ArrayList<Integer> currValue) {
        Handler handler = new Handler();

        Runnable decrementRunnable = new Runnable() {
            @Override
            public void run() {
                int newValue = currValue.get(0) - 1;
                currValue.set(0, newValue);
                updateFn.accept(newValue);
                handler.postDelayed(this, 100);
            }
        };
        Runnable incrementRunnable = new Runnable() {
            @Override
            public void run() {
                int newValue = currValue.get(0) + 1;
                currValue.set(0, newValue);
                updateFn.accept(newValue);
                handler.postDelayed(this, 100);
            }
        };

        setUpSelectorIconListener(handler, removeIcon, decrementRunnable);
        setUpSelectorIconListener(handler, addIcon,    incrementRunnable);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpSelectorIconListener(Handler handler, ImageView icon, Runnable runnable) {
        icon.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.post(runnable);
                    handler.postDelayed(() -> handler.post(runnable), 500);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler.removeCallbacksAndMessages(null);
                    return true;
            }
            return false;
        });
    }

    private void updateGender(boolean isMale) {
        this.isMale = isMale;

        btn_male.setSelected(isMale);
        btn_female.setSelected(!isMale);
    }

    private void updateHeight(int height) {
        if (height < 0 || height > MAX_HEIGHT) {
            return;
        }
        this.height = height;
        txtView_height.setText(String.valueOf(height));
    }

    private void updateWeight(int weight) {
        if (weight < 0 || weight > MAX_WEIGHT) {
            return;
        }
        this.weight = weight;
        txtView_weight.setText(String.valueOf(weight));
    }

    private void updateAge(int age) {
        if (age < 0 || age > MAX_AGE) {
            return;
        }
        this.age = age;
        txtView_age.setText(String.valueOf(age));
    }
}