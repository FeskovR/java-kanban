package taskmanagers;

import constants.TaskStatus;
import history.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager> {
    T manager;

    abstract T createManager();

    @BeforeEach
    public void beforeEach(){
        manager = createManager();
    }

    @Test
    public void doesSubtasksHaveEpicAndEpicHaveSubtasksId() {
        Epic epic = new Epic("Epic1 title", "Epic1 desc");
        Subtask subtask = new Subtask("Subtask title", "Subtask desc", TaskStatus.NEW);
        manager.addNewSubtask(subtask);
        epic.addSubtasksId(subtask.getId());
        manager.addNewEpic(epic);
        assertEquals(2, manager.getSubtask(1).getEpicID());
        assertEquals(1, manager.getEpic(2).getSubtasksId().get(0));
    }

    @Test
    public void shouldEpicStatusDoneWhenSubtasksDone() {
        Epic epic = new Epic("Epic title", "Epic desc");
        Subtask subtask1 = new Subtask("Subtask1 title", "Subtask1 desc", TaskStatus.DONE);
        Subtask subtask2 = new Subtask("Subtask2 title", "Subtask2 desc", TaskStatus.DONE);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        epic.addSubtasksId(subtask1.getId());
        epic.addSubtasksId(subtask2.getId());
        manager.addNewEpic(epic);
        assertEquals(TaskStatus.DONE, manager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void testAddNewTaskAndGetTaskId1() {
        Task task = new Task("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    public void testAddNewSubtaskAndGetSubtaskId1() {
        Subtask task = new Subtask("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewSubtask(task);
        assertEquals(task, manager.getSubtask(1));
    }

    @Test
    public void testAddNewEpicAndGetEpicId1() {
        Epic task = new Epic("Task title", "Task desc");
        manager.addNewEpic(task);
        assertEquals(task, manager.getEpic(1));
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task("Task1 title", "Task1 desc", TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task2 title", "Task2 desc", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        assertArrayEquals(tasks.toArray(), manager.getAllTasks().toArray());
    }

    @Test
    public void testGetAllSubtasks() {
        Subtask task1 = new Subtask("Task1 title", "Task1 desc", TaskStatus.IN_PROGRESS);
        Subtask task2 = new Subtask("Task2 title", "Task2 desc", TaskStatus.IN_PROGRESS);
        manager.addNewSubtask(task1);
        manager.addNewSubtask(task2);
        List<Subtask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        assertArrayEquals(tasks.toArray(), manager.getAllSubtasks().toArray());
    }

    @Test
    public void testGetAllEpics() {
        Epic task1 = new Epic("Task1 title", "Task1 desc");
        Epic task2 = new Epic("Task2 title", "Task2 desc");
        manager.addNewEpic(task1);
        manager.addNewEpic(task2);
        List<Epic> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        assertArrayEquals(tasks.toArray(), manager.getAllEpics().toArray());
    }

    @Test
    public void testRemoveAllTasks() {
        Task task1 = new Task("Task1 title", "Task1 desc", TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task2 title", "Task2 desc", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.removeAllTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void testRemoveAllSubtasks() {
        Subtask task1 = new Subtask("Task1 title", "Task1 desc", TaskStatus.IN_PROGRESS);
        Subtask task2 = new Subtask("Task2 title", "Task2 desc", TaskStatus.IN_PROGRESS);
        manager.addNewSubtask(task1);
        manager.addNewSubtask(task2);
        manager.removeAllSubtasks();
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void testRemoveAllEpics() {
        Epic task1 = new Epic("Task1 title", "Task1 desc");
        Epic task2 = new Epic("Task2 title", "Task2 desc");
        manager.addNewEpic(task1);
        manager.addNewEpic(task2);
        manager.removeAllEpics();
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void shouldReturnTaskIfIdIsWrong() {
        Task task = new Task("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task);
        assertNull(manager.getTask(2));
    }

    @Test
    public void shouldReturnTaskById() {
        Task task = new Task("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    public void shouldReturnSubtaskIfIdIsWrong() {
        Subtask task = new Subtask("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewSubtask(task);
        assertNull(manager.getSubtask(2));
    }

    @Test
    public void shouldReturnSubtaskById() {
        Subtask task = new Subtask("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        manager.addNewSubtask(task);
        assertEquals(task, manager.getSubtask(1));
    }

    @Test
    public void shouldReturnEpicIfIdIsWrong() {
        Epic task = new Epic("Task title", "Task desc");
        manager.addNewEpic(task);
        assertNull(manager.getEpic(2));
    }

    @Test
    public void shouldReturnEpicById() {
        Epic task = new Epic("Task title", "Task desc");
        manager.addNewEpic(task);
        assertEquals(task, manager.getEpic(1));
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        Task newTask = new Task("Task newTitle", "Task newDesc", TaskStatus.DONE);
        manager.addNewTask(task);
        newTask.setId(task.getId());
        manager.updateTask(newTask);
        assertEquals(newTask, manager.getTask(task.getId()));
    }

    @Test
    public void testUpdateSubtask() {
        Subtask task = new Subtask("Task title", "Task desc", TaskStatus.IN_PROGRESS);
        Subtask newTask = new Subtask("new Subtask title", "new SubTask desc", TaskStatus.DONE);
        manager.addNewSubtask(task);
        newTask.setId(task.getId());
        manager.updateSubtask(newTask);
        assertEquals(newTask, manager.getSubtask(task.getId()));
    }

    @Test
    public void testUpdateEpic() {
        Epic task = new Epic("Task title", "Task desc");
        Epic newTask = new Epic("New Epic title", "New Epic desc");
        manager.addNewEpic(task);
        newTask.setId(task.getId());
        manager.updateEpic(newTask);
        assertEquals(newTask, manager.getEpic(task.getId()));
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task("Task title", "Task desc", TaskStatus.NEW);
        manager.addNewTask(task);
        manager.deleteTask(task.getId());
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void testDeleteSubtask() {
        Subtask task = new Subtask("Task title", "Task desc", TaskStatus.NEW);
        manager.addNewSubtask(task);
        manager.deleteSubtask(task.getId());
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void testDeleteEpic() {
        Epic epic = new Epic("Epic title", "Epic desc");
        manager.addNewEpic(epic);
        manager.deleteEpic(epic.getId());
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void shouldReturnListOfSubtaskByEpicId() {
        Epic epic = new Epic("Title", "Description");
        Subtask subtask = new Subtask("Title", "Description", TaskStatus.DONE);
        Subtask subtask2 = new Subtask("Title", "Description", TaskStatus.NEW);
        manager.addNewSubtask(subtask);
        manager.addNewSubtask(subtask2);
        epic.addSubtasksId(subtask.getId());
        epic.addSubtasksId(subtask2.getId());
        manager.addNewEpic(epic);

        List<Subtask> checkList = new ArrayList<>();
        checkList.add(subtask);
        checkList.add(subtask2);

        assertArrayEquals(checkList.toArray(), manager.getSubtasksByEpic(epic).toArray());
    }

    @Test
    public void shouldReturnHistory() {
        InMemoryHistoryManager historyCheck = Managers.getDefaultHistory();
        assertEquals(historyCheck.getHistory(), manager.getHistory());
    }
}