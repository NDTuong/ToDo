package com.example.todo.Model;

import java.util.Date;

public class TimeTable {
    String subject;
    String location;
    DayOfWeek dow;
    String duration;

    public TimeTable() {
    }

    public TimeTable(String subject, String location, DayOfWeek dow, String duration) {
        this.subject = subject;
        this.location = location;
        this.dow = dow;
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DayOfWeek getDow() {
        return dow;
    }

    public void setDow(DayOfWeek dow) {
        this.dow = dow;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
