package com.kotbros.core;

import com.kotbros.android_app.R;

public class BMI {

    public static double calculate(double height, double weight) {
        return Math.round(weight / Math.pow(height, 2) * 10d) / 10d;
    }

    public static int category(double bmi) {
        if (bmi < 15) {
            return R.string.bmi_cat_1;
        }
        if (bmi < 16) {
            return R.string.bmi_cat_2;
        }
        if (bmi < 18.5) {
            return R.string.bmi_cat_3;
        }
        if (bmi < 25) {
            return R.string.bmi_cat_4;
        }
        if (bmi < 30) {
            return R.string.bmi_cat_5;
        }
        if (bmi < 35) {
            return R.string.bmi_cat_6;
        }
        if (bmi < 40) {
            return R.string.bmi_cat_7;
        }
        if (bmi < 45) {
            return R.string.bmi_cat_8;
        }
        if (bmi < 50) {
            return R.string.bmi_cat_9;
        }
        if (bmi < 60) {
            return R.string.bmi_cat_10;
        }
        return R.string.bmi_cat_11;
    }
}
