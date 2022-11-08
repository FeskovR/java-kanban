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

    public void setStatus(String status) {
        //не понял для чего нужен сеттер для поля статус, если статус присваивается в конструкторе
        this.status = status;
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
