@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeAll
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void createTask_validInputs_success() {
        Task task = taskManager.createTask("task-1", "description");
        assertNotNull(task);
        assertEquals("task-1", task.getTaskId());
        assertEquals("description", task.getDescription());
    }

    @Test
    void createTask_nullInputs_throwException() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(null, "description"));
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("task-1", null));
    }

    @Test
    void createTask_existingTask_throwException() {
        taskManager.createTask("task-1", "description");
        assertThrows(IllegalStateException.class, () -> taskManager.createTask("task-1", "description2"));
    }

    @Test
    void getTask_validTaskId_success() {
        Task task = taskManager.createTask("task-1", "description");
        Task fetchedTask = taskManager.getTask("task-1");
        assertNotNull(fetchedTask);
        assertEquals(task.getTaskId(), fetchedTask.getTaskId());
        assertEquals(task.getDescription(), fetchedTask.getDescription());
        assertEquals(task.getStatus(), fetchedTask.getStatus());
    }

    @Test
    void getTask_nullTaskId_throwException() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(null));
    }

    @Test
    void getTask_nonExistingTaskId_throwException() {
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("task-1"));
    }

    @Test
    void updateTaskStatus_validInputs_success() {
        Task task = taskManager.createTask("task-1", "description");
        taskManager.updateTaskStatus("task-1", Task.Status.IN_PROGRESS);
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void updateTaskStatus_nullInputs_throwException() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(null, Task.Status.IN_PROGRESS));
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus("task-1", null));
    }

    @Test
    void deleteTask_validTaskId_success() {
        taskManager.createTask("task-1", "description");
        assertTrue(taskManager.getTasks().containsKey("task-1"));
        taskManager.deleteTask("task-1");
        assertFalse(taskManager.getTasks().containsKey("task-1"));
    }

    @Test
    void deleteTask_nullTaskId_throwException() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(null));
    }

    @Test
    void deleteTask_nonExistingTaskId_throwException() {
        assertThrows(IllegalStateException.class, () -> taskManager.deleteTask("task-1"));
    }

    @Test
    void getTasks_emptyTasksMap_returnEmptyMap() {
        Map<String, Task> tasks = taskManager.getTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void getTasks_nonEmptyTasksMap_returnNonEmptyMap() {
        taskManager.createTask("task-1", "description");
        Map<String, Task> tasks = taskManager.getTasks();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertTrue(tasks.containsKey("task-1"));
    }
}