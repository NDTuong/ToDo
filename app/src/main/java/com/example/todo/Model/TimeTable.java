package com.example.todo.Model;

import java.util.Objects;

public class TimeTable {
    String subject;
    String location;
    DayOfWeek day;
    String duration;
    String key;

    public TimeTable() {
    }

    public TimeTable(String subject, String location, DayOfWeek day, String duration, String key) {
        this.subject = subject;
        this.location = location;
        this.day = day;
        this.duration = duration;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return subject.equals(timeTable.subject) && location.equals(timeTable.location) && day == timeTable.day && duration.equals(timeTable.duration) && key.equals(timeTable.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, location, day, duration, key);
    }
}
