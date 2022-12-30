package taskmanagers;
import history.InMemoryHistoryManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        File file = new File("src/files", "history.csv");
        return FileBackedTasksManager.loadFromFile(file); // тут Idea ругается "Cannot access taskmanagers.FileBackedTasksManager"
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
