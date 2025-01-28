package com.kotbros.android_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kotbros.core.BMI;

public class BMIFragment extends Fragment {

    AutoCompleteTextView txt_height;
    AutoCompleteTextView txt_weight;

    TextView txt_result_bmi;
    TextView txt_result_cat;

    public BMIFragment() {
    }

    public static BMIFragment newInstance() {
        return new BMIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        txt_height = view.findViewById(R.id.txt_height);
        txt_weight = view.findViewById(R.id.txt_weight);
        txt_result_bmi = view.findViewById(R.id.txt_result_bmi);
        txt_result_cat = view.findViewById(R.id.txt_result_cat);
        Button btn_more_info = view.findViewById(R.id.btn_more_info);

        initTextField(txt_height);
        initTextField(txt_weight);

        btn_more_info.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.wikipedia_bmi_link)));
            startActivity(browserIntent);
        });

        return view;
    }

    private void initTextField(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateBmi();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @SuppressLint("StringFormatMatches")
    private void calculateBmi() {
        if (isValidInput(txt_height) && isValidInput(txt_weight)) {
            double bmi = BMI.calculate(getTextAsDouble(txt_height), getTextAsDouble(txt_weight));
            txt_result_bmi.setText(getString(R.string.bmi_result, bmi));
            txt_result_cat.setText(getString((BMI.category(bmi))));
        } else {
            txt_result_bmi.setText("");
            txt_result_cat.setText("");
        }
    }

    private boolean isValidInput(EditText editText) {
        return getTextAsDouble(editText) > 0;
    }

    private double getTextAsDouble(EditText editText) {
        String input = editText.getText().toString().replace(',', '.');
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}