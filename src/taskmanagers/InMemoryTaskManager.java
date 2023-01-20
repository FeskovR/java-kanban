package taskmanagers;

import history.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import constants.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager{
    protected int id = 1;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    public HistoryManager history = Managers.getDefaultHistory();

    @Override
    public void addNewTask(Task task) {
        if (task.getId() == 0)
            task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        if (subtask.getId() == 0)
            subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void addNewEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(getNextId());
        }
        epics.put(epic.getId(), epic);
        for (Integer subtaskId : epic.getSubtasksId()) {
            subtasks.get(subtaskId).setEpicID(epic.getId());
        }
        setEpicStatus(epic);
        setEpicStartTime(epic);
        setEpicDuration(epic);
        setEpicEndTime(epic);
    }

    protected void setEpicStatus(Epic epic) {
        int counter = 0;
        int min = 0;
        int max = 0;
        for (Integer subtaskId : epic.getSubtasksId()) {
            min++;
            max += 3;
            if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.NEW)){
                counter++;
            } else if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.DONE)) {
                counter += 3;
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
        }
        if (counter == min) {
            epic.setStatus(TaskStatus.NEW);
        } else if (counter == max) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    protected void setEpicStartTime(Epic epic) {
        List<Subtask> subtasksByEpic = getSubtasksByEpic(epic);
        LocalDateTime startTime = null;
        for (Subtask subtask : subtasksByEpic) {
            if (startTime == null) {
                startTime = subtask.getStartTime();
            }else if (subtask.getStartTime().isBefore(startTime)){
                startTime = subtask.getStartTime();
            }
        }
        epic.setStartTime(startTime);
    }

    protected void setEpicDuration(Epic epic) {
        List<Subtask> subtasksByEpic = getSubtasksByEpic(epic);
        int duration = 0;
        for (Subtask subtask : subtasksByEpic) {
            duration += subtask.getDuration();
        }
        epic.setDuration(duration);
    }

    protected void setEpicEndTime(Epic epic) {
        List<Subtask> subtasksByEpic = getSubtasksByEpic(epic);
        LocalDateTime endTime = null;
        for (Subtask subtask : subtasksByEpic) {
            if (endTime == null) {
                endTime = subtask.getEndTime();
            } else if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }
        epic.setEndTime(endTime);
    }

    protected int getNextId(){
        return id++;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.getOrDefault(id, null);
        if (task != null)
            addTaskToHistory(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask task = subtasks.getOrDefault(id, null);
        if (task != null)
            addTaskToHistory(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic task = epics.getOrDefault(id, null);
        if (task != null)
            addTaskToHistory(task);
        return task;
    }

    @Override
    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
    }

    @Override
    public void updateEpic(Epic newEpic) {
        int epicId = newEpic.getId();
        epics.remove(epicId);
        addNewEpic(newEpic);
    }

    @Override
    public void deleteTask(int id) {
        history.remove(id);
        tasks.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        history.remove(id);
        subtasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epicToDel = epics.get(id);
        for (Integer subtaskId : epicToDel.getSubtasksId()) {
            history.remove(subtaskId);
        }
        history.remove(id);
        epics.remove(id);
    }

    @Override
    public List<Subtask> getSubtasksByEpic(Epic epic) {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtasksId()) {
            subtaskList.add(subtasks.get(subtaskId));
        }
        return subtaskList;
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    protected void addTaskToHistory(Task task) {
        if (task != null) {
            history.add(task);
        }
    }
}
