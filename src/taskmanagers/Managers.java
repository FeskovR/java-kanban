package taskmanagers;
import history.InMemoryHistoryManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault(){
        return new HttpTaskManager("http://localhost");
    }
    public static TaskManager getDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
