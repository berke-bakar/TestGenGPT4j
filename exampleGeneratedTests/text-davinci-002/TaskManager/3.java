import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    public void setUp() {
        manager = new TaskManager();
    }

    @Test
    public void testCreateTask() {
        String taskId = "1";
        String description = "Test task";
        Task task = manager.createTask(taskId, description);
        assertEquals(taskId, task.getTaskId());
        assertEquals(description, task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    public void testCreateTaskWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> manager.createTask(null, "description"));
    }

    @Test
    public void testCreateTaskWithNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> manager.createTask("1", null));
    }

    @Test
    public void testCreateDuplicateTask() {
        String taskId = "1";
        String description = "Test task";
        manager.createTask(taskId, description);
        assertThrows(IllegalStateException.class, () -> manager.createTask(taskId, description));
    }

    @Test
    public void testGetTask() {
        String taskId = "1";
        String description = "Test task";
        Task task = manager.createTask(taskId, description);
        Task task2 = manager.getTask(taskId);
        assertEquals(task, task2);
    }

    @Test
    public void testGetTaskWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> manager.getTask(null));
    }

    @Test
    public void testGetNonExistentTask() {
        assertThrows(IllegalStateException.class, () -> manager.getTask("1"));
    }

    @Test
    public void testUpdateTaskStatus() {
        String taskId = "1";
        String description = "Test task";
        Task task = manager.createTask(taskId, description);
        manager.updateTaskStatus(taskId, Task.Status.IN_PROGRESS);
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testUpdateTaskStatusWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> manager.updateTaskStatus(null, Task.Status.IN_PROGRESS));
    }

    @Test
    public void testUpdateTaskStatusWithNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> manager.updateTaskStatus("1", null));
    }

    @Test
    public void testUpdateTaskStatusForNonExistentTask() {
        assertThrows(IllegalStateException.class, () -> manager.updateTaskStatus("1", Task.Status.IN_PROGRESS));
    }

    @Test
    public void testDeleteTask() {
        String taskId = "1";
        String description = "Test task";
        Task task = manager.createTask(taskId, description);
        manager.deleteTask(taskId);
        assertThrows(IllegalStateException.class, () -> manager.getTask(taskId));
    }

    @Test
    public void testDeleteTaskWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> manager.deleteTask(null));
    }

    @Test
    public void testDeleteNonExistentTask() {
        assertThrows(IllegalStateException.class, () -> manager.deleteTask("1"));
    }
}