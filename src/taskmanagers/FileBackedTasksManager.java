package taskmanagers;

import tasks.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private final String historyFile;

    public FileBackedTasksManager(String path) {
        historyFile = path;
    }

    private void save() {
        try(Writer writer = new FileWriter(historyFile)) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task: tasks.values()) {
                writer.write(task.toString() + "\n");
            }
            for (Task subtask: subtasks.values()) {
                writer.write(subtask.toString() + "\n");
            }
            for (Task epic: epics.values()) {
                writer.write(epic.toString() + "\n");
            }
            writer.write("\n");
            for (Task task: history.getHistory()) {
                writer.write(task.getId() + ",");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
    }

    @Override
    public void addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }
}
