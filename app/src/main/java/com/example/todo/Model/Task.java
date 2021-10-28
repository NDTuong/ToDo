package com.example.todo.Model;

import java.util.Date;
import java.util.List;

public class Task {
    String taskName;
    List<Task> subTask;
    boolean isComplete;
    String startTime;
    String endTime;
    String process;
    String startDay;
    String endDay;
    String location;
    String timeComplete;
    String dayComplete;

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public String getDayComplete() {
        return dayComplete;
    }

    public void setDayComplete(String dayComplete) {
        this.dayComplete = dayComplete;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Task() {
    }

    public Task(String taskName, List<Task> subTask, boolean isComplete, String startTime, String endTime, String process, String startDay, String endDay) {
        this.taskName = taskName;
        this.subTask = subTask;
        this.isComplete = isComplete;
        this.startTime = startTime;
        this.endTime = endTime;
        this.process = process;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<Task> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<Task> subTask) {
        this.subTask = subTask;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
