import taskmanagers.Managers;
import taskmanagers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import constants.TaskStatus;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Task1", "Desc task1", TaskStatus.NEW, "21.01.23 12.00", 30);
        Task task2 = new Task("Task2", "Desc task2", TaskStatus.IN_PROGRESS, "22.01.23 12.00", 30);
        Subtask subtask1 = new Subtask("Subtask1", "Desc subtask1", TaskStatus.NEW, "23.01.23 12.00", 30);
        Subtask subtask2 = new Subtask("Subtask2", "Desc subtask2", TaskStatus.IN_PROGRESS, "24.01.23 12.00", 30);
        Subtask subtask3 = new Subtask("Subtask3", "Desc subtask3", TaskStatus.DONE, "19.01.23 12.00", 30);
        Subtask subtask4 = new Subtask("Subtask4", "Desc subtask4", TaskStatus.DONE, "18.01.23 12.00", 30);
        Epic epic1 = new Epic("Epic1", "Desc epic1");
        Epic epic2 = new Epic("Epic2", "Desc epic2");

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        taskManager.addNewSubtask(subtask4);

        epic1.addSubtasksId(subtask1.getId());
        epic1.addSubtasksId(subtask2.getId());
        epic2.addSubtasksId(subtask3.getId());
        epic2.addSubtasksId(subtask4.getId());

        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);

        taskManager.getTask(task1.getId()); //1
        taskManager.getTask(task2.getId()); //2
        taskManager.getSubtask(subtask1.getId()); //3
        taskManager.getSubtask(subtask2.getId()); //4
        taskManager.getSubtask(subtask3.getId()); //5
        taskManager.getSubtask(subtask4.getId()); //6
        taskManager.getEpic(epic1.getId()); //7
        taskManager.getEpic(epic2.getId()); //8
        taskManager.getEpic(epic2.getId()); //8
        taskManager.getSubtask(subtask1.getId()); //3
        taskManager.deleteTask(task2.getId()); // 2
        taskManager.deleteEpic(epic1.getId()); //7
        Task task3 = new Task("Task3", "Desc task3", TaskStatus.NEW, "25.01.23 12.00", 30);
        taskManager.addNewTask(task3);
        taskManager.getTask(task3.getId());
        Task task4 = new Task("Task4", "Desc task4", TaskStatus.DONE, "26.01.23 12.00", 30);
        taskManager.addNewTask(task4);
        taskManager.getTask(task4.getId());
        System.out.println(taskManager.getHistory()); //1 5 6 8 9

        TaskManager taskManager2 = Managers.getDefault(new File("src/files", "history.csv"));
        System.out.println(taskManager2.getHistory());
        System.out.println(taskManager.getAllTasks().equals(taskManager2.getAllTasks()));
        System.out.println(taskManager.getAllSubtasks().equals(taskManager2.getAllSubtasks()));
        System.out.println(taskManager.getAllEpics().equals(taskManager2.getAllEpics()));
    }
}
