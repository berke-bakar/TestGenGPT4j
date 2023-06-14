public class TaskManagerTest {
    @Test
    void givenTaskManager_whenCreateTask_thenTaskisCreated() {
        // given
        TaskManager taskManager = new TaskManager();

        // when
        Task task = taskManager.createTask("1", "Test task");

        // then
        assertNotNull(task);
        assertEquals("1", task.getTaskId());
        assertEquals("Test task", task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    void givenTaskManager_whenCreateTaskWithNullId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.createTask(null, "Test task"));
    }

    @Test
    void givenTaskManager_whenCreateTaskWithNullDescription_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.createTask("1", null));
    }

    @Test
    void givenTaskManager_whenCreateTaskWithExistingId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();
        taskManager.createTask("1", "Test task");

        // then
        assertThrows(IllegalStateException.class,
                () -> taskManager.createTask("1", "Test task"));
    }

    @Test
    void givenTaskManager_whenGetTask_thenTaskIsReturned() {
        // given
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "Test task");

        // when
        Task returnedTask = taskManager.getTask("1");

        // then
        assertNotNull(returnedTask);
        assertEquals(task, returnedTask);
    }

    @Test
    void givenTaskManager_whenGetTaskWithNullId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.getTask(null));
    }

    @Test
    void givenTaskManager_whenGetTaskWithNonExistingId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalStateException.class,
                () -> taskManager.getTask("1"));
    }

    @Test
    void givenTaskManager_whenUpdateTaskStatus_thenStatusIsUpdated() {
        // given
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "Test task");

        // when
        taskManager.updateTaskStatus("1", Task.Status.IN_PROGRESS);

        // then
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void givenTaskManager_whenUpdateTaskStatusWithNullId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.updateTaskStatus(null, Task.Status.IN_PROGRESS));
    }

    @Test
    void givenTaskManager_whenUpdateTaskStatusWithNullStatus_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "Test task");

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.updateTaskStatus("1", null));
    }

    @Test
    void givenTaskManager_whenUpdateTaskStatusWithNonExistingId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalStateException.class,
                () -> taskManager.updateTaskStatus("1", Task.Status.IN_PROGRESS));
    }

    @Test
    void givenTaskManager_whenDeleteTask_thenTaskIsDeleted() {
        // given
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "Test task");

        // when
        taskManager.deleteTask("1");

        // then
        assertFalse(taskManager.tasks.containsValue(task));
    }

    @Test
    void givenTaskManager_whenDeleteTaskWithNullId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalArgumentException.class,
                () -> taskManager.deleteTask(null));
    }

    @Test
    void givenTaskManager_whenDeleteTaskWithNonExistingId_thenExceptionIsThrown() {
        // given
        TaskManager taskManager = new TaskManager();

        // then
        assertThrows(IllegalStateException.class,
                () -> taskManager.deleteTask("1"));
    }
}