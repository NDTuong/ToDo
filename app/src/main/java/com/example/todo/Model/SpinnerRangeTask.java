package com.example.todo.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SpinnerRangeTask {
    String today;
    String tomorrow;
    List<String> currentWeek = new ArrayList<>();
    SimpleDateFormat DEADLINE_FORMAT = new SimpleDateFormat("dd/M/yyyy");

    public SpinnerRangeTask() {
        Calendar today = Calendar.getInstance();
        this.today = DEADLINE_FORMAT.format(today.getTime());

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH,1);
        this.tomorrow = DEADLINE_FORMAT.format(tomorrow.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        for (int i = 0; i < 7; i++){
            calendar.set(Calendar.DAY_OF_WEEK, i);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = day + "/" +  month + "/"  + year;
//            Log.d("TaskActivity", "isContains: " + date);
            this.currentWeek.add(date);
//            Log.d("TaskActivity", "isContains: " + year + "/" +  month + "/"  + day);
        }
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

    public List<String> getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(List<String> currentWeek) {
        this.currentWeek = currentWeek;
    }
}
