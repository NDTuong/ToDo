package com.example.todo.Model;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Task {
    String taskName;
    String deadLine;
    String notify;
    List<String> subTask;

    boolean isComplete;
    boolean isImportant;
    boolean isShareTask;

    String process;;
    String timeComplete;
    String note;
    Map<String, List<String>> listShare;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    String taskID;

    public Task() {
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

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
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

    public Map<String, List<String>> getListShare() {
        return listShare;
    }

    public void setListShare(Map<String, List<String>> listShare) {
        this.listShare = listShare;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", deadLine=" + deadLine +
                ", notify=" + notify +
                ", subTask=" + subTask +
                ", isComplete=" + isComplete +
                ", isImportant=" + isImportant +
                ", isShareTask=" + isShareTask +
                ", process='" + process + '\'' +
                ", timeComplete='" + timeComplete + '\'' +
                ", note='" + note + '\'' +
                ", listShare=" + listShare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskName.equals(task.taskName) && Objects.equals(deadLine, task.deadLine) && Objects.equals(notify, task.notify) && Objects.equals(subTask, task.subTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, deadLine, notify, subTask);
    }
}
