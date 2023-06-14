import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        taskManager.createTask("task1", "Task 1 description");
        taskManager.createTask("task2", "Task 2 description");
    }

    @Test
    void testCreateTask() {
        Task task = taskManager.createTask("task3", "Task 3 description");
        assertNotNull(task);
        assertEquals("task3", task.getTaskId());
        assertEquals("Task 3 description", task.getDescription());
    }

    @Test
    void testCreateTaskWithNullValues() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(null, "Task description"));
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("task4", null));
    }

    @Test
    void testCreateTaskAlreadyExists() {
        assertThrows(IllegalStateException.class, () -> taskManager.createTask("task1", "New description"));
    }

    @Test
    void testGetTask() {
        Task task = taskManager.getTask("task1");
        assertNotNull(task);
        assertEquals("task1", task.getTaskId());
        assertEquals("Task 1 description", task.getDescription());
    }

    @Test
    void testGetTaskWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(null));
    }

    @Test
    void testGetTaskDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("task3"));
    }

    @Test
    void testUpdateTaskStatus() {
        taskManager.updateTaskStatus("task1", Task.Status.IN_PROGRESS);
        Task task = taskManager.getTask("task1");
        assertNotNull(task);
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void testUpdateTaskStatusWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(null, Task.Status.DONE));
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus("task1", null));
    }

    @Test
    void testDeleteTask() {
        taskManager.deleteTask("task1");
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("task1"));
        assertNotNull(taskManager.getTask("task2"));
    }

    @Test
    void testDeleteTaskWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(null));
    }

    @Test
    void testDeleteTaskDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> taskManager.deleteTask("task3"));
    }
}