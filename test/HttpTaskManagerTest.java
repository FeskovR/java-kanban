import constants.TaskStatus;
import org.junit.jupiter.api.*;
import server.KVServer;
import taskmanagers.HttpTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;

    @Override
    HttpTaskManager createManager() throws IOException {
        return new HttpTaskManager("http://localhost");
    }

    @BeforeEach
    public void beforeEach() {
        try{
            kvServer = new KVServer();
            kvServer.start();
            manager = createManager();
        } catch (IOException e) {
            System.out.println("Error at starting kvserver");
        }
    }

    @AfterEach
    public void stopKvServer() {
        kvServer.stop();
    }

    @Test
    public void testLoadingStateFromKVServerToOtherManager() {
        Task task = new Task("Title", "Desc", TaskStatus.NEW, "30.01.23 09.00", 45);
        Subtask subtask = new Subtask("Subtask title", "Subtask description", TaskStatus.IN_PROGRESS, "31.01.23 10.00", 15);
        Epic epic = new Epic("Epic title", "Epic description");

        manager.addNewTask(task);
        manager.addNewSubtask(subtask);
        epic.addSubtasksId(subtask.getId());
        manager.addNewEpic(epic);

        manager.getSubtask(subtask.getId());
        manager.getTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();

        assertEquals(task, manager2.getTask(task.getId()));
        assertEquals(subtask, manager2.getSubtask(subtask.getId()));
        assertEquals(epic, manager2.getEpic(epic.getId()));
    }

    @Test
    public void testLoadingHistoryFromKVServerToOtherManager() {
        Task task = new Task("Title", "Desc", TaskStatus.NEW, "30.01.23 09.00", 45);
        Subtask subtask = new Subtask("Subtask title", "Subtask description", TaskStatus.IN_PROGRESS, "31.01.23 10.00", 15);
        Epic epic = new Epic("Epic title", "Epic description");

        manager.addNewTask(task);
        manager.addNewSubtask(subtask);
        epic.addSubtasksId(subtask.getId());
        manager.addNewEpic(epic);

        manager.getSubtask(subtask.getId());
        manager.getTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();
        assertArrayEquals(manager.getHistory().toArray(), manager2.getHistory().toArray());
    }

    @Test
    public void getPrioritizedTaskTest() {
        Task task = new Task("Title", "Desc", TaskStatus.NEW, "30.01.23 09.00", 45);
        Task task1 = new Task("Title", "Desc", TaskStatus.NEW, "02.02.23 11.00", 15);
        Task task2 = new Task("Title", "Desc", TaskStatus.NEW, "15.01.23 23.00", 60);
        Subtask subtask = new Subtask("Subtask title", "Subtask description", TaskStatus.IN_PROGRESS, "31.01.23 10.00", 15);
        Epic epic = new Epic("Epic title", "Epic description");

        manager.addNewTask(task);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewSubtask(subtask);
        epic.addSubtasksId(subtask.getId());
        manager.addNewEpic(epic);

        manager.getSubtask(subtask.getId());
        manager.getTask(task.getId());

        HttpTaskManager manager2 = HttpTaskManager.load();

        assertArrayEquals(manager.getPrioritizedTasks().toArray(), manager2.getPrioritizedTasks().toArray());
    }
}
