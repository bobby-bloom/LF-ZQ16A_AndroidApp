package com.kotbros.android_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class BMIFragment extends Fragment {

    public static final int MAX_HEIGHT = 350;

    private int height;
    private TextView txtView_height;

    private boolean isMale = true;

    public BMIFragment() {}

    public static BMIFragment newInstance() {
        return new BMIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        height = Integer.parseInt(getString(R.string.bmi_default_height));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        txtView_height = view.findViewById(R.id.bmi_selection_height);
        initHeightSlider(view);

        return view;
    }

    private void initHeightSlider(View view) {
        SeekBar height_slider = view.findViewById(R.id.bmi_height_range_slider);

        System.err.printf("height: %d%n", height);

        height_slider.setMax(MAX_HEIGHT);
        height_slider.setProgress(height, true);
        height_slider.animate();

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

    private void updateHeight(int height) {
        this.height = height;
        txtView_height.setText(String.valueOf(height));
    }
}