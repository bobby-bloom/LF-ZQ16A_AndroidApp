package com.kotbros.android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kotbros.android_components.PlusMinusSelector;
import com.kotbros.core.BMI;

import java.util.function.Consumer;

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
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            height = Integer.parseInt(getString(R.string.bmi_default_height));
            weight = Integer.parseInt(getString(R.string.bmi_default_weight));
            age    = Integer.parseInt(getString(R.string.bmi_default_age));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        maleBtn    = view.findViewById(R.id.male_selection_frame);
        femaleBtn  = view.findViewById(R.id.female_selection_frame);
        heightView = view.findViewById(R.id.bmi_selection_height);

        maleBtn.setOnClickListener(v -> updateGender(true));
        femaleBtn.setOnClickListener(v -> updateGender(false));

        updateGender(isMale);

        initHeightSlider(view);
        initWeightSelector(view);
        initAgeSelector(view);

        view.findViewById(R.id.bmi_btn_calculate).setOnClickListener(v -> handleCalculateBtnClick());

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isMale", isMale);
        outState.putInt("height", height);
        outState.putInt("weight", weight);
        outState.putInt("age", age);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            isMale = savedInstanceState.getBoolean("isMale");
            height = savedInstanceState.getInt("height");
            weight = savedInstanceState.getInt("weight");
            age = savedInstanceState.getInt("age");

            updateGender(isMale);
            heightView.setText(String.valueOf(height));
            heightView.setText(String.valueOf(height));
        }
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

        heightView.setText(String.valueOf(height));
    }

    private void initWeightSelector(View view) {
        ImageView plusIcon  = view.findViewById(R.id.bmi_weight_increment);
        ImageView minusIcon = view.findViewById(R.id.bmi_weight_decrement);

        TextView textView = view.findViewById(R.id.bmi_selection_weight);
        textView.setText(String.valueOf(weight));

        Consumer<Integer> callback = (v) -> {
            weight = v;
            textView.setText(String.valueOf(v));
        };

        PlusMinusSelector selector = new PlusMinusSelector(weight, 0, MAX_WEIGHT, plusIcon, minusIcon, callback);
        selector.initialize();
    }

    private void initAgeSelector(View view) {
        ImageView plusIcon  = view.findViewById(R.id.bmi_age_increment);
        ImageView minusIcon = view.findViewById(R.id.bmi_age_decrement);

        TextView textView   = view.findViewById(R.id.bmi_selection_age);
        textView.setText(String.valueOf(age));

        Consumer<Integer> callback = (v) -> {
            age = v;
            textView.setText(String.valueOf(v));
        };

        PlusMinusSelector selector = new PlusMinusSelector(age, MIN_AGE, MAX_AGE, plusIcon, minusIcon, callback);
        selector.initialize();
    }

    private void handleCalculateBtnClick() {
        double bmi = BMI.calculate(weight, height);
        String bmi_str = String.valueOf(bmi);
        String category = getString(BMI.category(bmi, age));
        String gender = getString(isMale ? R.string.bmi_male : R.string.bmi_female);

        Fragment resultFragment = BMIResultFragment.newInstance(bmi_str, category, gender);
        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.flFragment, resultFragment)
            .addToBackStack(null)
            .commit();
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