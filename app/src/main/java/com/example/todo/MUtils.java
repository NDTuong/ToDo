package com.example.todo;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Model.DayOfWeek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MUtils extends AppCompatActivity {
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }

    // Hàm chuyển từ kiểu calendar sang dạng string
    // flags = 0 - sử dụng format 1
    // flags = 1 - sử dụng format 2
    public static String convertCalendar2String(Calendar calendar, int flags){
        SimpleDateFormat format1 = new SimpleDateFormat("d/MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm - d/MM/yyyy");
        switch (flags){
            case 0:
                String s1 = format1.format(calendar.getTime());
                return s1;
            default:
                String s2 = format2.format(calendar.getTime());
                return s2;
        }

    }


}
