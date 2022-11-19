import java.util.ArrayList;

public interface TaskManager {
    void addNewTask(Task task);
    void addNewSubtask(Subtask subtask);
    void addNewEpic(Epic epic);
    ArrayList<Task> getAllTasks();
    ArrayList<Subtask> getAllSubtasks();
    ArrayList<Epic> getAllEpics();
    void removeAllTasks();
    void removeAllSubtasks();
    void removeAllEpics();
    Task getTask(int id);
    Subtask getSubtask(int id);
    Epic getEpic(int id);
    void updateTask(Task newTask);
    void updateSubtask(Subtask newSubtask);
    void updateEpic(Epic newEpic);
    void deleteTask(int id);
    void deleteSubtask(int id);
    void deleteEpic(int id);
    ArrayList<Task> getHistory();
    ArrayList<Subtask> getSubtasksByEpic(Epic epic);
}
