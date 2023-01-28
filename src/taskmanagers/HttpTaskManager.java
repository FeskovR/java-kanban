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
        load();
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

    protected void load() {
        try {
            String tasksJson = client.load("TASK");
            if (tasksJson != null) {
                JsonElement taskElement = JsonParser.parseString(tasksJson);
                if (taskElement.isJsonArray()) {
                    JsonArray array = taskElement.getAsJsonArray();
                    for (JsonElement taskJson : array) {
                        if (taskJson.isJsonObject()) {
                            Task task = gson.fromJson(taskJson, Task.class);
                            tasks.put(task.getId()                                        , task);
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
                            subtasks.put(task.getId(), task);
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
                            epics.put(task.getId(), task);
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
                            history.add(task);
                        }
                    }
                }
            }

        } catch (ManagerSaveException e) {
            System.out.println("Ошибка загрузки состояния");
        }
    }
}
