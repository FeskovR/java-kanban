package taskmanagers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import exceptions.ManagerSaveException;
import httpClient.KVTaskClient;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class HttpTaskManager extends FileBackedTasksManager{
    KVTaskClient client;
    Gson gson = new Gson();

    public HttpTaskManager(String url){
        client = new KVTaskClient(url);
    }

    @Override
    public void save() throws ManagerSaveException {
        try {
            client.put("TASK", gson.toJson(tasks.values()));
            client.put("SUBTASK", gson.toJson(subtasks.values()));
            client.put("EPIC", gson.toJson(epics.values()));
            client.put("HISTORY", gson.toJson(getHistory()));
        } catch (ManagerSaveException e) {
            System.out.println("Ошибка записи");
        }
    }

    public static HttpTaskManager load() {
        HttpTaskManager manager = new HttpTaskManager("http://localhost");
        KVTaskClient client = manager.client;
        Gson gson = new Gson();
        try {
            String tasksJson = client.load("TASK");
            if (tasksJson != null) {
                JsonElement taskElement = JsonParser.parseString(tasksJson);
                if (taskElement.isJsonArray()) {
                    JsonArray array = taskElement.getAsJsonArray();
                    for (JsonElement taskJson : array) {
                        if (taskJson.isJsonObject()) {
                            Task task = gson.fromJson(taskJson, Task.class);
                            manager.addNewTask(task);
                        }
                    }
                }
            }


            String subtasksJson = client.load("SUBTASK");
            if (subtasksJson != null) {
                JsonElement subtaskElement = JsonParser.parseString(subtasksJson);
                if (subtaskElement.isJsonArray()) {
                    JsonArray array = subtaskElement.getAsJsonArray();
                    for (JsonElement taskJson : array) {
                        if (taskJson.isJsonObject()) {
                            Subtask task = gson.fromJson(taskJson, Subtask.class);
                            manager.addNewSubtask(task);
                        }
                    }
                }
            }


            String epicsJson = client.load("EPIC");
            if (epicsJson != null) {
                JsonElement epicElement = JsonParser.parseString(epicsJson);
                if (epicElement.isJsonArray()) {
                    JsonArray array = epicElement.getAsJsonArray();
                    for (JsonElement taskJson : array) {
                        if (taskJson.isJsonObject()) {
                            Epic task = gson.fromJson(taskJson, Epic.class);
                            manager.addNewEpic(task);
                        }
                    }
                }
            }


            String historyJson = client.load("HISTORY");
            if (historyJson != null) {
                JsonElement historyElement = JsonParser.parseString(historyJson);
                if (historyElement.isJsonArray()) {
                    JsonArray array = historyElement.getAsJsonArray();
                    for (JsonElement taskJson : array) {
                        if (taskJson.isJsonObject()) {
                            Task task = gson.fromJson(taskJson, Epic.class);
                            manager.addTaskToHistory(task);
                        }
                    }
                }
            }

        } catch (ManagerSaveException e) {
            System.out.println("Ошибка загрузки состояния");
        }
        return manager;
    }
}
