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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
