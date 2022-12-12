import taskmanagers.Managers;
import taskmanagers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Task1", "Desc task1", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Desc task2", TaskStatus.IN_PROGRESS);
        Subtask subtask1 = new Subtask("Subtask1", "Desc subtask1", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("subtask2", "Desc subtask2", TaskStatus.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Subtask3", "Desc subtask3", TaskStatus.DONE);
        Subtask subtask4 = new Subtask("subtask4", "Desc subtask4", TaskStatus.DONE);
        Epic epic1 = new Epic("Epic1", "Desc epic1");
        Epic epic2 = new Epic("Epic2", "Desc epic2");

        inMemoryTaskManager.addNewTask(task1);
        inMemoryTaskManager.addNewTask(task2);
        inMemoryTaskManager.addNewSubtask(subtask1);
        inMemoryTaskManager.addNewSubtask(subtask2);
        inMemoryTaskManager.addNewSubtask(subtask3);
        inMemoryTaskManager.addNewSubtask(subtask4);

        epic1.addSubtasksId(subtask1.getId());
        epic1.addSubtasksId(subtask2.getId());
        epic2.addSubtasksId(subtask3.getId());
        epic2.addSubtasksId(subtask4.getId());

        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);

        inMemoryTaskManager.getTask(1);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(2);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(3);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(4);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(5);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(6);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(7);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(8);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(8);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(8);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(8);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(8);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubtask(3);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteTask(2);
        System.out.println("After delete");
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteEpic(7);
        System.out.println("After delete EPIC #7");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println(inMemoryTaskManager.getHistory().size());
    }
}
