import org.example.TaskManager;
import org.example.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private TaskManager taskManager = new TaskManager();

    // Test to check if createTask() throws IllegalArgumentException when taskId or description is null
    @Test
    public void testCreateTaskIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask(null, null);
        });
    }

    // Test to check if createTask() throws IllegalStateException when taskId already exists
    @Test
    public void testCreateTaskIllegalStateException() {
        String taskId = "task1";
        String description = "description";
        taskManager.createTask(taskId, description);

        assertThrows(IllegalStateException.class, () -> {
            taskManager.createTask(taskId, description);
        });
    }

    // Test to check if createTask() returns the expected task object
    @Test
    public void testCreateTask() {
        String taskId = "task1";
        String description = "description";
        Task expectedTask = new Task(taskId, description);
        Task actualTask = taskManager.createTask(taskId, description);

        assertEquals(expectedTask.getTaskId(), actualTask.getTaskId());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }

    // Test to check if getTask() throws IllegalArgumentException when taskId is null
    @Test
    public void testGetTaskIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTask(null);
        });
    }

    // Test to check if getTask() throws IllegalStateException when taskId does not exist
    @Test
    public void testGetTaskIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> {
            taskManager.getTask("task1");
        });
    }

    // Test to check if getTask() returns the expected task object
    @Test
    public void testGetTask() {
        String taskId = "task1";
        String description = "description";
        Task expectedTask = new Task(taskId, description);
        taskManager.createTask(taskId, description);
        Task actualTask = taskManager.getTask(taskId);

        assertEquals(expectedTask.getTaskId(), actualTask.getTaskId());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }

    // Test to check if updateTaskStatus() throws IllegalArgumentException when taskId or status is null
    @Test
    public void testUpdateTaskStatusIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateTaskStatus(null, null);
        });
    }

    // Test to check if updateTaskStatus() updates the expected task object with the expected status
    @Test
    public void testUpdateTaskStatus() {
        String taskId = "task1";
        String description = "description";
        Task.Status expectedStatus = Task.Status.IN_PROGRESS;
        taskManager.createTask(taskId, description);
        taskManager.updateTaskStatus(taskId, expectedStatus);
        Task actualTask = taskManager.getTask(taskId);

        assertEquals(expectedStatus, actualTask.getStatus());
    }

    // Test to check if deleteTask() throws IllegalArgumentException when taskId is null
    @Test
    public void testDeleteTaskIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    // Test to check if deleteTask() throws IllegalStateException when taskId does not exist
    @Test
    public void testDeleteTaskIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> {
            taskManager.deleteTask("task1");
        });
    }

    // Test to check if deleteTask() successfully deletes the expected task
    @Test
    public void testDeleteTask() {
        String taskId = "task1";
        String description = "description";
        taskManager.createTask(taskId, description);
        taskManager.deleteTask(taskId);

        assertThrows(IllegalStateException.class, () -> {
            taskManager.getTask(taskId);
        });
    }


}