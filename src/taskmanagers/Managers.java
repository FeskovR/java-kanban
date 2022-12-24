package taskmanagers;
import history.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager("history.csv");
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
