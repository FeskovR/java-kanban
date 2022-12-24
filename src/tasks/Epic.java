package tasks;

import constants.TaskStatus;
import constants.TasksTypes;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String title, String desc) {
        super(title, desc);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void addSubtasksId(int subtaskId) {
        this.subtasksId.add(subtaskId);
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + TasksTypes.EPIC.toString() + "," + title + "," + status + "," + desc + ",";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksId, epic.subtasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksId);
    }
}
