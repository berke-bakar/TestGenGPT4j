import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void testCreateTask_success() {
        Task task = taskManager.createTask("task-1", "Description");
        assertNotNull(task);
        assertEquals("task-1", task.getTaskId());
        assertEquals("Description", task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    void testCreateTask_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(null, null));
    }

    @Test
    void testCreateTask_alreadyExists() {
        taskManager.createTask("task-1", "Description");
        assertThrows(IllegalStateException.class, () -> taskManager.createTask("task-1", "Description"));
    }

    @Test
    void testGetTask_success() {
        Task task = taskManager.createTask("task-1", "Description");
        Task result = taskManager.getTask("task-1");
        assertEquals(task, result);
    }

    @Test
    void testGetTask_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(null));
    }

    @Test
    void testGetTask_doesNotExist() {
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("task-1"));
    }

    @Test
    void testUpdateTaskStatus_success() {
        Task task = taskManager.createTask("task-1", "Description");
        taskManager.updateTaskStatus("task-1", Task.Status.IN_PROGRESS);
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void testUpdateTaskStatus_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(null, null));
    }

    @Test
    void testDeleteTask_success() {
        taskManager.createTask("task-1", "Description");
        taskManager.deleteTask("task-1");
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("task-1"));
    }

    @Test
    void testDeleteTask_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(null));
    }

    @Test
    void testDeleteTask_doesNotExist() {
        assertThrows(IllegalStateException.class, () -> taskManager.deleteTask("task-1"));
    }

    // More tests for high-quality coverage (if needed)
}