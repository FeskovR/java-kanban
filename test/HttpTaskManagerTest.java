import constants.TaskStatus;
import org.junit.jupiter.api.*;
import server.KVServer;
import taskmanagers.HttpTaskManager;
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
        manager.addNewTask(task);
        HttpTaskManager manager2 = HttpTaskManager.load();
        assertEquals(task, manager2.getTask(task.getId()));
    }
}
