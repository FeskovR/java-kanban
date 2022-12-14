package tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int epicID;

    public Subtask(String title, String desc, TaskStatus status) {
        super(title, desc, status);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "epicID=" + epicID +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Subtask subtask = (Subtask) obj;
        return epicID == subtask.epicID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID);
    }
}
