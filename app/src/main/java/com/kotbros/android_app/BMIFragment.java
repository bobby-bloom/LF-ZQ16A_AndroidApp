package com.kotbros.android_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kotbros.android_components.PlusMinusSelector;

public class BMIFragment extends Fragment {

    public static final int MAX_HEIGHT = 300;
    public static final int MAX_WEIGHT = 450;
    public static final int MAX_AGE = 200;
    public static final int MIN_AGE = 2;

    private boolean isMale = true;
    private int height;
    private int weight;
    private int age;

    private RelativeLayout maleBtn;
    private RelativeLayout femaleBtn;
    private TextView heightView;


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

        maleBtn       = view.findViewById(R.id.male_selection_frame);
        femaleBtn     = view.findViewById(R.id.female_selection_frame);
        heightView = view.findViewById(R.id.bmi_selection_height);

        maleBtn.setOnClickListener(v -> updateGender(true));
        femaleBtn.setOnClickListener(v -> updateGender(false));

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
        ImageView plusIcon  = view.findViewById(R.id.bmi_weight_increment);
        ImageView minusIcon = view.findViewById(R.id.bmi_weight_decrement);
        TextView textView   = view.findViewById(R.id.bmi_selection_weight);

        PlusMinusSelector selector = new PlusMinusSelector(weight, 0, MAX_WEIGHT, textView, plusIcon, minusIcon);
        selector.initialize(view);
    }

    private void initAgeSelector(View view) {
        ImageView plusIcon  = view.findViewById(R.id.bmi_age_increment);
        ImageView minusIcon = view.findViewById(R.id.bmi_age_decrement);
        TextView textView   = view.findViewById(R.id.bmi_selection_age);

        PlusMinusSelector selector = new PlusMinusSelector(age, MIN_AGE, MAX_AGE, textView, plusIcon, minusIcon);
        selector.initialize(view);
    }

    private void updateGender(boolean isMale) {
        this.isMale = isMale;

        maleBtn.setSelected(isMale);
        femaleBtn.setSelected(!isMale);
    }

    private void updateHeight(int height) {
        if (height < 0 || height > MAX_HEIGHT) {
            return;
        }
        this.height = height;
        heightView.setText(String.valueOf(height));
    }

}