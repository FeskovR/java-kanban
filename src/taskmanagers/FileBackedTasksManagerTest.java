package taskmanagers;

import constants.TaskStatus;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;

import java.io.File;
import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    @Override
    FileBackedTasksManager createManager() {
        return new FileBackedTasksManager("src/files/history.csv");
    }

    @Test
    public void testSaveToFileAndLoadFromFileWithoutHistory() {
        Task task = new Task("Title", "Description", TaskStatus.DONE, "25.01.23 12.00", 30);
        manager.addNewTask(task);
        manager.save();
        File file = new File("src/files", "history.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(task, fileBackedTasksManager.getTask(task.getId()));
    }

    @Test
    public void testSaveToFileAndLoadFromFileWithHistory() {
        Task task = new Task("Title", "Description", TaskStatus.NEW, "25.01.23 12.00", 30);
        manager.addNewTask(task);
        manager.getTask(task.getId());
        manager.save();
        File file = new File("src/files", "history.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(task, fileBackedTasksManager.getTask(task.getId()));
    }

    @Test
    public void testSaveToFileAndLoadFromFileWithoutTasks() {
        manager.save();
        File file = new File("src/files", "history.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(0, fileBackedTasksManager.getAllTasks().size());
        assertEquals(0, fileBackedTasksManager.getAllSubtasks().size());
        assertEquals(0, fileBackedTasksManager.getAllEpics().size());
    }

    @Test
    public void testLoadToFileEpicWithoutSubtasks() {
        Epic epic = new Epic("Title", "Description");
        manager.addNewEpic(epic);
        manager.save();
        File file = new File("src/files", "history.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(epic, fileBackedTasksManager.getEpic(epic.getId()));
    }
}