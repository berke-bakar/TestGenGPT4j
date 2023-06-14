import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("Task Manager Test")
class TaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    @DisplayName("Create Task Test")
    void testCreateTask() {
        Task task = taskManager.createTask("1", "Description");
        Assertions.assertEquals("1", task.getTaskId());
        Assertions.assertEquals("Description", task.getDescription());
        Assertions.assertEquals(Task.Status.TODO, task.getStatus());

        Map<String, Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(1, tasks.size());
    }

    @Test
    @DisplayName("Get Task Test")
    void testGetTask() {
        Task task = taskManager.createTask("1", "Description");
        Task retrievedTask = taskManager.getTask("1");
        Assertions.assertEquals(task, retrievedTask);
    }

    @Test
    @DisplayName("Update Task Status Test")
    void testUpdateTaskStatus() {
        Task task = taskManager.createTask("1", "Description");
        taskManager.updateTaskStatus("1", Task.Status.IN_PROGRESS);
        Assertions.assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    @DisplayName("Delete Task Test")
    void testDeleteTask() {
        Task task = taskManager.createTask("1", "Description");
        taskManager.deleteTask("1");
        Map<String, Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(0, tasks.size());
    }

    @Test
    @DisplayName("Create Task with Null Task ID Test")
    void testCreateTaskWithNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask(null, "Description");
        });
    }

    @Test
    @DisplayName("Create Task with Null Description Test")
    void testCreateTaskWithNullDescription() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask("1", null);
        });
    }

    @Test
    @DisplayName("Create Existing Task Test")
    void testCreateExistingTask() {
        Task task = taskManager.createTask("1", "Description");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            taskManager.createTask("1", "New Description");
        });
    }

    @Test
    @DisplayName("Get Task with Null Task ID Test")
    void testGetTaskWithNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTask(null);
        });
    }

    @Test
    @DisplayName("Get Non-Existing Task Test")
    void testGetNonExistingTask() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            taskManager.getTask("1");
        });
    }

    @Test
    @DisplayName("Update Task Status with Null Task ID Test")
    void testUpdateTaskStatusWithNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateTaskStatus(null, Task.Status.TODO);
        });
    }

    @Test
    @DisplayName("Update Task Status with Null Status Test")
    void testUpdateTaskStatusWithNullStatus() {
        Task task = taskManager.createTask("1", "Description");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateTaskStatus("1", null);
        });
    }

    @Test
    @DisplayName("Delete Task with Null Task ID Test")
    void testDeleteTaskWithNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    @Test
    @DisplayName("Delete Non-Existing Task Test")
    void testDeleteNonExistingTask() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            taskManager.deleteTask("1");
        });
    }
}