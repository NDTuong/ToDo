package com.example.todo.Model;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Task {
    String taskName;
    Calendar deadLine;
    Calendar notify;
    List<String> subTask;

    boolean isComplete;
    boolean isImportant;
    boolean isShareTask;

    String process;;
    String timeComplete;
    String note;
    Map<String, String> listShare;

    public Task() {
    }

    public Task(String taskName, Calendar deadLine) {
        this.taskName = taskName;
        this.deadLine = deadLine;
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Calendar getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Calendar deadLine) {
        this.deadLine = deadLine;
    }

    public Calendar getNotify() {
        return notify;
    }

    public void setNotify(Calendar notify) {
        this.notify = notify;
    }

    public List<String> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<String> subTask) {
        this.subTask = subTask;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map<String, String> getListShare() {
        return listShare;
    }

    public void setListShare(Map<String, String> listShare) {
        this.listShare = listShare;
    }
}
