package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import constants.TaskStatus;
import taskmanagers.Managers;
import taskmanagers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpTaskServer {
    private final int PORT = 8080;

    TaskManager manager = Managers.getDefault();
    HttpServer httpServer;
    Gson gson = new Gson();

    public void serverStart() throws IOException {
        //
        Task testTask = new Task("Task title", "Task description", TaskStatus.DONE, "25.11.23 12.15", 30);
        manager.addNewTask(testTask);
        //
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.start();

        System.out.println("Сервер запущен на порт: " + PORT);
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    String getQuery = exchange.getRequestURI().getQuery();
                    if (getQuery == null) {
                        List<Task> tasks = manager.getAllTasks();
                        String tasksJson = gson.toJson(tasks);
                        writeResponse(exchange, tasksJson, 200);
                    } else {
                        int id = Integer.parseInt(getQuery.split("=")[1]);
                        Task task = manager.getTask(id);
                        if(task == null) {
                            writeResponse(exchange, "Задача с запрошенным id не найдена", 404);
                            break;
                        }
                        String taskJson = gson.toJson(task);
                        writeResponse(exchange, taskJson, 200);
                    }
                    break;
                case "POST":
                    InputStream is = exchange.getRequestBody();
                    String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    if (requestBody.isBlank()) {
                        writeResponse(exchange, "Данные не переданы", 404);
                        break;
                    }
                    Task task = gson.fromJson(requestBody, Task.class);
                    if (manager.getTask(task.getId()) == null) {
                        manager.addNewTask(task);
                        writeResponse(exchange, "Задача успешно добавлена", 200);
                    } else {
                        manager.updateTask(task);
                        writeResponse(exchange, "Задача успешно обновлена", 200);
                    }
                    break;
                case "DELETE":
                    String deleteQuery = exchange.getRequestURI().getQuery();
                    if (deleteQuery == null) {
                        manager.removeAllTasks();
                        writeResponse(exchange, "Все задачи успешно удалены", 200);
                    } else {
                        int id = Integer.parseInt(deleteQuery.split("=")[1]);
                        if (manager.getTask(id) == null) {
                            writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                            break;
                        }
                        manager.deleteTask(id);
                        writeResponse(exchange, "Задача id:" + id + " успешно удалена", 200);
                    }
                    break;
                default:
                    writeResponse(exchange, "Неизвестный метод", 404);
            }
        }
    }
}


