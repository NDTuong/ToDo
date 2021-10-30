package com.example.todo.Model;

import java.util.Objects;

public class TimeTable {
    String subject;
    String location;
    DayOfWeek day;
    String duration;

    public TimeTable() {
    }

    public TimeTable(String subject, String location, DayOfWeek day, String duration) {
        this.subject = subject;
        this.location = location;
        this.day = day;
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
        return Objects.equals(subject, timeTable.subject) && Objects.equals(location, timeTable.location) && day == timeTable.day && Objects.equals(duration, timeTable.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, location, day, duration);
    }
}
