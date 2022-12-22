package taskmanagers;
import history.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getDefault() {
//        return new InMemoryTaskManager();
        return new FileBackedTasksManager("src/files/history.csv");
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
