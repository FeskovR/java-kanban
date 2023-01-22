import history.HistoryManager;
import history.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddTaskToHistoryAndGetHistoryWithTasks() {
        Task task = new Task("Title", "Description");
        Task task2 = new Task("Title2", "Description2");
        task.setId(1);
        task2.setId(2);
        manager.add(task);
        manager.add(task2);
        List<Task> checkList = new ArrayList<>();
        checkList.add(task);
        checkList.add(task2);
        assertEquals(checkList, manager.getHistory());
    }

    @Test
    public void testRemoveTaskFromHistory() {
        Task task = new Task("Title", "Description");
        Task task2 = new Task("Title2", "Description2");
        task.setId(1);
        task2.setId(2);
        manager.add(task);
        manager.add(task2);
        manager.remove(1);
        List<Task> checkList = new ArrayList<>();
        checkList.add(task2);
        assertEquals(checkList, manager.getHistory());
    }

    @Test
    public void testGetHistoryWhenHistoryIsEmpty() {
        assertEquals(new ArrayList<>(), manager.getHistory());
    }

    @Test
    public void testAddEqualTaskAndRemoveDouble() {
        Task task = new Task("Title", "Description");
        Task task2 = new Task("Title2", "Description2");
        task.setId(1);
        task2.setId(2);
        manager.add(task);
        manager.add(task);
        manager.add(task2);
        manager.add(task);
        manager.add(task);
        List<Task> checkList = new ArrayList<>();
        checkList.add(task2);
        checkList.add(task);
        assertEquals(checkList, manager.getHistory());
    }
}