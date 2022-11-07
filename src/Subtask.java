public class Subtask extends Task {
    private int epicID;

    public Subtask(String title, String desc, String status) {
        super(title, desc, status);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicID=" + epicID +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}
