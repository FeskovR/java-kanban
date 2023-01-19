package taskmanagers;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    @Override
    FileBackedTasksManager createManager() {
        return new FileBackedTasksManager("src/files/history.csv");
    }
}