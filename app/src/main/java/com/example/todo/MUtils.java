package com.example.todo;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Model.DayOfWeek;

import java.util.ArrayList;

public class MUtils extends AppCompatActivity {
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }

}
