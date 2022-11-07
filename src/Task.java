public class Task {
    protected String title;
    protected String desc;
    protected String status;
    protected int id;

    public Task(String title, String desc, String status) {
        this.title = title;
        this.desc = desc;
        this.status = status;
    }

    public Task(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}
