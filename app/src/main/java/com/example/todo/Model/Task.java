package com.example.todo.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Task {
    String taskName;
    String deadLine;
    String notify;
    boolean isImportant;
    String note;
    String taskID;

    boolean isComplete;
    String timeComplete;
    String idListTask;
    boolean isSelected;
    SimpleDateFormat DEADLINE_FORMAT = new SimpleDateFormat("dd/M/yyyy");

    public Task() {
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getIdListTask() {
        return idListTask;
    }

    public void setIdListTask(String idListTask) {
        this.idListTask = idListTask;
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
    public Date getDate() throws ParseException {
        Date d = DEADLINE_FORMAT.parse(this.deadLine);
        return d;
    }

    @Override
    public String toString() {
        return "taskName=" + taskName + "//" +
                "deadLine=" + deadLine + "//" +
                "notify=" + notify + "//" +
                "isComplete=" + isComplete + "//" +
                "isImportant=" + isImportant + "//" +
                "timeComplete=" + timeComplete + "//" +
                "taskID=" + taskID + "//" +
                "note=" + note;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return isImportant == task.isImportant && isComplete == task.isComplete && isSelected == task.isSelected && Objects.equals(taskName, task.taskName) && Objects.equals(deadLine, task.deadLine) && Objects.equals(notify, task.notify) && Objects.equals(note, task.note) && Objects.equals(taskID, task.taskID) && Objects.equals(timeComplete, task.timeComplete) && Objects.equals(idListTask, task.idListTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, deadLine, notify, isImportant, note, taskID, isComplete, timeComplete, idListTask, isSelected);
    }
}
