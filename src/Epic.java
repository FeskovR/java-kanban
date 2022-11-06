import java.util.ArrayList;

public class Epic extends Task{
    protected ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String title, String desc, String status) {
        super(title, desc, status);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtaskId;
    }

    public void addSubtasksId(int subtaskId) {
        this.subtaskId.add(subtaskId);
    }
}
