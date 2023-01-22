import taskmanagers.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }
}