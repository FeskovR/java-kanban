import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    protected int id = 1;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();

    public void addNewTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    public void addNewSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void addNewEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(getNextId());
        }
        epics.put(epic.getId(), epic);
        for (Integer subtaskId : epic.getSubtasksId()) {
            subtasks.get(subtaskId).setEpicID(epic.getId());
        }
        setEpicStatus(epic);
    }

    private void setEpicStatus(Epic epic) {
        int counter = 0;
        int min = 0;
        int max = 0;
        for (Integer subtaskId : epic.getSubtasksId()) {
            min++;
            max += 3;
            if (subtasks.get(subtaskId).getStatus().equals("NEW")){
                counter++;
            } else if (subtasks.get(subtaskId).getStatus().equals("DONE")) {
                counter += 3;
            } else {
                epic.setStatus("IN_PROGRESS");
                return;
            }
        }
        if (counter == min) {
            epic.setStatus("NEW");
        } else if (counter == max) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }

    private int getNextId(){
        return id++;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public Task getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else return epics.getOrDefault(id, null);
    }

    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void updateSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
    }

    public void updateEpic(Epic newEpic) {
        int epicId = newEpic.getId();
        epics.remove(epicId);
        addNewEpic(newEpic);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtasksId()) {
            subtaskList.add(subtasks.get(subtaskId));
        }
        return subtaskList;
    }
}
