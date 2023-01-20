package tasks;

import constants.TaskStatus;
import constants.TasksTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String title;
    protected String desc;
    protected TaskStatus status;
    protected int id;
    protected int duration;
    protected LocalDateTime startTime;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Task(String title, String desc, TaskStatus status, String startTime, int duration) {
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.duration = duration;
        this.startTime = setStartTime(startTime);
    }

    public Task(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    protected LocalDateTime setStartTime(String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH.mm");
        return LocalDateTime.parse(start, formatter);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    protected DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yy HH.mm");
    }

    @Override
    public String toString() {
        return id + "," + TasksTypes.TASK.toString() + "," + title + "," + status + "," + desc + "," +
                startTime.format(formatter()) + "," + duration + "," + getEndTime().format(formatter()) + ",";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(desc, task.desc) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, desc, status, id);
    }
}
