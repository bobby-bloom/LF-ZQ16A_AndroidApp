package com.kotbros.android_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class BMIFragment extends Fragment {

    public static final int MAX_HEIGHT = 350;
    public static final int MAX_WEIGHT = 1000;
    public static final int MAX_AGE = 420;

    private boolean isMale = true;
    private int height;
    private int weight;
    private int age;

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

        txtView_height = view.findViewById(R.id.bmi_selection_height);
        txtView_weight = view.findViewById(R.id.bmi_selection_weight);
        txtView_age    = view.findViewById(R.id.bmi_selection_age);

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

        removeIcon.setOnClickListener(v -> updateWeight(weight-1));
        addIcon.setOnClickListener(v -> updateWeight(weight+1));
    }

    private void initAgeSelector(View view) {
        ImageView removeIcon = view.findViewById(R.id.bmi_age_decrement);
        ImageView addIcon    = view.findViewById(R.id.bmi_age_increment);

        removeIcon.setOnClickListener(v -> updateAge(weight-1));
        addIcon.setOnClickListener(v -> updateAge(weight+1));
    }

    private void updateHeight(int height) {
        if (height > MAX_HEIGHT) {
            return;
        }
        this.height = height;
        txtView_height.setText(String.valueOf(height));
    }

    private void updateWeight(int weight) {
        if (weight > MAX_WEIGHT) {
            return;
        }
        this.weight = weight;
        txtView_weight.setText(String.valueOf(weight));
    }

    private void updateAge(int age) {
        if (age > MAX_AGE) {
            return;
        }
        this.age = age;
        txtView_age.setText(String.valueOf(age));
    }
}