package taskmanagers;
import history.InMemoryHistoryManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTasksManager("src/files/history.csv");
    }
    public static TaskManager getDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file); // тут Idea ругается "Cannot access taskmanagers.FileBackedTasksManager"
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
