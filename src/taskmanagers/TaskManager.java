package taskmanagers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.util.Set;

public interface TaskManager {
    void addNewTask(Task task);
    void addNewSubtask(Subtask subtask);
    void addNewEpic(Epic epic);

    List<Task> getAllTasks();
    List<Subtask> getAllSubtasks();
    List<Epic> getAllEpics();
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
    List<Task> getHistory();
    List<Subtask> getSubtasksByEpic(Epic epic);
    Set<Task> getPrioritizedTasks();
}
