package com.example.todo.Model;

import java.util.List;

public class Task {
    java.lang.String taskName;
    List<Task> subTask;
    boolean isComplete;
    java.lang.String startTime;
    java.lang.String endTime;
    java.lang.String process;
    java.lang.String startDay;
    java.lang.String endDay;
    java.lang.String location;
    java.lang.String timeComplete;
    java.lang.String dayComplete;
    boolean isImportant;
    boolean isShareTask;
    String string;

    public String getDayOfWeek() {
        return string;
    }

    public void setDayOfWeek(String string) {
        this.string = string;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isShareTask() {
        return isShareTask;
    }

    public void setShareTask(boolean shareTask) {
        isShareTask = shareTask;
    }

    public java.lang.String getGroupTaskName() {
        return groupTaskName;
    }

    public void setGroupTaskName(java.lang.String groupTaskName) {
        this.groupTaskName = groupTaskName;
    }

    public java.lang.String getTaskID() {
        return taskID;
    }

    public void setTaskID(java.lang.String taskID) {
        this.taskID = taskID;
    }

    java.lang.String groupTaskName;
    java.lang.String taskID;

    public java.lang.String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(java.lang.String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public java.lang.String getDayComplete() {
        return dayComplete;
    }

    public void setDayComplete(java.lang.String dayComplete) {
        this.dayComplete = dayComplete;
    }

    public java.lang.String getLocation() {
        return location;
    }

    public void setLocation(java.lang.String location) {
        this.location = location;
    }

    public java.lang.String getProcess() {
        return process;
    }

    public void setProcess(java.lang.String process) {
        this.process = process;
    }

    public Task() {
    }

    public Task(java.lang.String taskName, List<Task> subTask, boolean isComplete, java.lang.String startTime, java.lang.String endTime, java.lang.String process, java.lang.String startDay, java.lang.String endDay) {
        this.taskName = taskName;
        this.subTask = subTask;
        this.isComplete = isComplete;
        this.startTime = startTime;
        this.endTime = endTime;
        this.process = process;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public java.lang.String getStartDay() {
        return startDay;
    }

    public void setStartDay(java.lang.String startDay) {
        this.startDay = startDay;
    }

    public java.lang.String getEndDay() {
        return endDay;
    }

    public void setEndDay(java.lang.String endDay) {
        this.endDay = endDay;
    }

    public java.lang.String getTaskName() {
        return taskName;
    }

    public void setTaskName(java.lang.String taskName) {
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

    public java.lang.String getStartTime() {
        return startTime;
    }

    public void setStartTime(java.lang.String startTime) {
        this.startTime = startTime;
    }

    public java.lang.String getEndTime() {
        return endTime;
    }

    public void setEndTime(java.lang.String endTime) {
        this.endTime = endTime;
    }
}
