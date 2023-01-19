package tasks;

import constants.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanagers.Managers;
import taskmanagers.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Epic title", "Epic desc");
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldEpicStatusNewWhenSubtasksMissed() {
        taskManager.addNewEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.NEW, status);
    }

    @Test
    public void shouldEpicStatusNewWhenSubtasksAllNew() {
        Subtask subtask1 = new Subtask("Subtask1 title", "Subtask1 desk", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Subtask2 title", "Subtask2 desk", TaskStatus.NEW);
        Subtask subtask3 = new Subtask("Subtask3 title", "Subtask3 desk", TaskStatus.NEW);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        epic.addSubtasksId(subtask1.getId());
        epic.addSubtasksId(subtask2.getId());
        epic.addSubtasksId(subtask3.getId());
        taskManager.addNewEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.NEW, status);
    }

    @Test
    public void shouldEpicStatusDoneWhenSubtasksAllDone() {
        Subtask subtask1 = new Subtask("Subtask1 title", "Subtask1 desk", TaskStatus.DONE);
        Subtask subtask2 = new Subtask("Subtask2 title", "Subtask2 desk", TaskStatus.DONE);
        Subtask subtask3 = new Subtask("Subtask3 title", "Subtask3 desk", TaskStatus.DONE);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        epic.addSubtasksId(subtask1.getId());
        epic.addSubtasksId(subtask2.getId());
        epic.addSubtasksId(subtask3.getId());
        taskManager.addNewEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.DONE, status);
    }

    @Test
    public void shouldEpicStatusInProgressWhenSubtasksNewAndDone() {
        Subtask subtask1 = new Subtask("Subtask1 title", "Subtask1 desk", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Subtask2 title", "Subtask2 desk", TaskStatus.NEW);
        Subtask subtask3 = new Subtask("Subtask3 title", "Subtask3 desk", TaskStatus.DONE);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        epic.addSubtasksId(subtask1.getId());
        epic.addSubtasksId(subtask2.getId());
        epic.addSubtasksId(subtask3.getId());
        taskManager.addNewEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status);
    }

    @Test
    public void shouldEpicStatusInProgressWhenSubtasksAllInProgress() {
        Subtask subtask1 = new Subtask("Subtask1 title", "Subtask1 desk", TaskStatus.IN_PROGRESS);
        Subtask subtask2 = new Subtask("Subtask2 title", "Subtask2 desk", TaskStatus.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Subtask3 title", "Subtask3 desk", TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        epic.addSubtasksId(subtask1.getId());
        epic.addSubtasksId(subtask2.getId());
        epic.addSubtasksId(subtask3.getId());
        taskManager.addNewEpic(epic);
        TaskStatus status = epic.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status);
    }
}