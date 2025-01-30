package com.kotbros.core;

import com.kotbros.android_app.R;

public class BMI {

    public static double calculate(double weight, double height) {
        double bmi = weight / Math.pow(height / 100, 2);
        return Math.round(bmi * 10.0) / 10.0;
    }

    public static int category(double bmi, int age) {
        if (age < 17) {
            return categorizeChildren(bmi);
        }
        return categorizeAdult(bmi);
    }

    private static int categorizeChildren(double bmi) {
        if (bmi < 15) {
            return R.string.bmi_category_underweight;
        }
        if (bmi < 22) {
            return R.string.bmi_category_normal;
        }
        return R.string.bmi_category_overweight;
    }

    private static int categorizeAdult(double bmi) {
        if (bmi < 15) {
            return R.string.bmi_category_v_serv_underweight;
        }
        if (bmi < 16) {
            return R.string.bmi_category_serv_underweight;
        }
        if (bmi < 18.5) {
            return R.string.bmi_category_underweight;
        }
        if (bmi < 25) {
            return R.string.bmi_category_normal;
        }
        if (bmi < 30) {
            return R.string.bmi_category_overweight;
        }
        if (bmi < 35) {
            return R.string.bmi_category_obese1;
        }
        if (bmi < 40) {
            return R.string.bmi_category_obese2;
        }
        if (bmi < 45) {
            return R.string.bmi_category_obese3;
        }
        if (bmi < 50) {
            return R.string.bmi_category_obese4;
        }
        if (bmi < 60) {
            return R.string.bmi_category_obese5;
        }
        return R.string.bmi_category_obese6;
    }
}
