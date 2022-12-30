package taskmanagers;
import history.InMemoryHistoryManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        File file = new File("src/files", "history.csv");
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
