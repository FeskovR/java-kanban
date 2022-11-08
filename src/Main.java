public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Task 1", "Description task 1", "IN_PROGRESS");
        Task task2 = new Task("Task 2", "Description task 2", "DONE");
        Subtask subtask1 = new Subtask("Subtask 1", "Description subtask 1", "DONE");
        Subtask subtask2 = new Subtask("Subtask 2", "Description subtask 2", "NEW");
        Subtask subtask3 = new Subtask("Subtask 3", "Description subtask 3", "IN_PROGRESS");
        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        Epic epic2 = new Epic("Epic 2", "Description epic 2");

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        epic1.addSubtasksId(subtask1.getId());
        epic1.addSubtasksId(subtask2.getId());
        manager.addNewEpic(epic1);

        manager.addNewSubtask(subtask3);
        epic2.addSubtasksId(subtask3.getId());
        manager.addNewEpic(epic2);

        manager.addNewTask(task1);
        manager.addNewTask(task2);

//        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());



        epic1.getSubtasksId().stream().map(manager::getById).forEach(e -> e.status = "DONE");
        manager.updateEpic(epic1);

        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());
    }
}
