import java.util.HashMap;

public class Manager {
    protected int id = 1;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Task> subtasks = new HashMap<>();
    protected HashMap<Integer, Task> epics = new HashMap<>();

    public void addNewTask(Task task){
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    public void addNewSubtask(Subtask subtask){
        subtask.setId(id++);
        subtasks.put(subtask.getId(), subtask);
    }

    public void addNewTask(Epic epic){
        epic.setId(id++);
        epics.put(epic.getId(), epic);
    }
}
