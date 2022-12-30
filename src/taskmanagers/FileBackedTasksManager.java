package taskmanagers;

import constants.TaskStatus;
import exceptions.ManagerSaveException;
import history.HistoryManager;
import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{
    final private String historyFile;
    final String path = "src/files/";

    public FileBackedTasksManager(String file) {
        historyFile = file;
    }

    private void save() throws ManagerSaveException{
        try{
            Files.createDirectory(Paths.get(path));
        } catch (Exception e) {
            e.getStackTrace();
        }
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
            writer.write(historyToString(history));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            throw new ManagerSaveException("Ошибка записи!");
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Task taskFromString(String taskString) {
        String[] fields = taskString.split(",");
        Task task;
        switch (fields[1]) {
            case "TASK":
                task = new Task(fields[2], fields[4], TaskStatus.valueOf(fields[3]));
                task.setId(Integer.parseInt(fields[0]));
                break;
            case "SUBTASK":
                task = new Subtask(fields[2], fields[4], TaskStatus.valueOf(fields[3]));
                task.setId(Integer.parseInt(fields[0]));
                break;
            case "EPIC":
                task = new Epic(fields[2], fields[4]);
                task.setId(Integer.parseInt(fields[0]));
                break;
            default:
                task = null;
        }
        return task;
    }

    public static String historyToString(HistoryManager manager){
        List<Task> history = manager.getHistory();
        String historyString = "";

        for (int i = 0; i < history.size(); i++) {
            if (i == history.size() - 1) {
                historyString += history.get(i).getId();
            } else {
                historyString += history.get(i).getId() + ",";
            }
        }
        return historyString;
    }

    static List<Integer> historyFromString(String value) {
        List <Integer> historyList = new ArrayList<>();
        String[] taskIds = value.split(",");
        for (String taskId: taskIds) {
            historyList.add(Integer.parseInt(taskId));
        }
        return historyList;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file.getAbsolutePath());
        try(Reader reader = new FileReader(file.getAbsolutePath())) {
            BufferedReader br = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();

            while (br.ready()) {
                builder.append(br.readLine());
                builder.append("\n");
            }

            String fileString = builder.toString();
            String[] blocks = fileString.split("\n\n");
            String[] tasksStrings = blocks[0].split("\n");
//            String[] historyStrings = blocks[1].split(",");
            List <Integer> historyIds = historyFromString(blocks[1].trim());

            for (int i = 1; i < tasksStrings.length; i++) {
                String type = tasksStrings[i].split(",")[1];
                switch (type) {
                    case "TASK":
                        fileBackedTasksManager.addNewTask(taskFromString(tasksStrings[i]));
                        break;
                    case "SUBTASK":
                        fileBackedTasksManager.addNewSubtask((Subtask) taskFromString(tasksStrings[i]));
                        break;
                    case "EPIC":
                        fileBackedTasksManager.addNewEpic((Epic) taskFromString(tasksStrings[i]));
                        break;
                    default:
                        System.out.println("Что-то пошло не так...и задача из файла не создалась!!!");
                }
            }

            for (Integer id : historyIds) {
                fileBackedTasksManager.getTask(id);
                fileBackedTasksManager.getSubtask(id);
                fileBackedTasksManager.getEpic(id);
            }
        } catch (IOException e) {
            return fileBackedTasksManager;
        }

        return fileBackedTasksManager;
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
