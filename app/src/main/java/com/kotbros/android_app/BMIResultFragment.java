package com.kotbros.android_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BMIResultFragment extends Fragment {

    public BMIResultFragment() {}

    public static BMIResultFragment newInstance(String bmi, String category, String gender) {
        BMIResultFragment fragment = new BMIResultFragment();

        Bundle args = new Bundle();
        args.putString("bmi", bmi);
        args.putString("category", category);
        args.putString("gender", gender);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi_result, container, false);

        TextView bmiView = view.findViewById(R.id.bmi_result_bmi_view);
        TextView categoryView = view.findViewById(R.id.bmi_result_category_view);
        TextView genderView = view.findViewById(R.id.bmi_result_gender_view);

        bmiView.setText(getArguments().getString("bmi", ""));
        categoryView.setText(getArguments().getString("category", ""));
        genderView.setText(getArguments().getString("gender", ""));

        return view;
    }
}