package tasks;

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
}
