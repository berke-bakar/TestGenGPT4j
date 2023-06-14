import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void setup() {
        taskManager = new TaskManager();
    }

    @Test
    public void createTask_withValidTaskIdAndDescription_shouldCreateTask() {
        // Arrange
        String taskId = "task1";
        String description = "Description of task1";

        // Act
        Task task = taskManager.createTask(taskId, description);

        // Assert
        assertTrue(taskManager.tasks.containsKey(taskId));
        assertEquals(task, taskManager.tasks.get(taskId));
        assertEquals(taskId, task.getTaskId());
        assertEquals(description, task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    public void createTask_withNullTaskId_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        String description = "Description of task1";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.createTask(taskId, description));

        // Assert
        assertTrue(exception.getMessage().contains("Task ID"));
    }

    @Test
    public void createTask_withNullDescription_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = "task1";
        String description = null;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.createTask(taskId, description));

        // Assert
        assertTrue(exception.getMessage().contains("description"));
    }

    @Test
    public void createTask_withExistingTaskId_shouldThrowIllegalStateException() {
        // Arrange
        String taskId = "task1";
        String description = "Description of task1";
        taskManager.createTask(taskId, description);

        // Act
        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> taskManager.createTask(taskId, description));

        // Assert
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    public void getTask_withNonExistingTaskId_shouldThrowIllegalStateException() {
        // Arrange
        String taskId = "task1";

        // Act
        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> taskManager.getTask(taskId));

        // Assert
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    public void getTask_withNullTaskId_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.getTask(taskId));

        // Assert
        assertTrue(exception.getMessage().contains("Task ID"));
    }

    @Test
    public void getTask_withExistingTaskId_shouldReturnExistingTask() {
        // Arrange
        String taskId = "task1";
        String description = "Description of task1";
        taskManager.createTask(taskId, description);

        // Act
        Task task = taskManager.getTask(taskId);

        // Assert
        assertEquals(task, taskManager.tasks.get(taskId));
        assertEquals(taskId, task.getTaskId());
        assertEquals(description, task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    public void updateTaskStatus_withExistingTaskIdAndValidStatus_shouldUpdateTaskStatus() {
        // Arrange
        String taskId = "task1";
        String description = "Description of task1";
        taskManager.createTask(taskId, description);
        Task.Status newStatus = Task.Status.DONE;

        // Act
        taskManager.updateTaskStatus(taskId, newStatus);

        // Assert
        Task task = taskManager.getTask(taskId);
        assertEquals(task, taskManager.tasks.get(taskId));
        assertEquals(taskId, task.getTaskId());
        assertEquals(description, task.getDescription());
        assertEquals(newStatus, task.getStatus());
    }

    @Test
    public void updateTaskStatus_withNonExistingTaskId_shouldThrowIllegalStateException() {
        // Arrange
        String taskId = "task1";
        Task.Status newStatus = Task.Status.DONE;

        // Act
        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> taskManager.updateTaskStatus(taskId, newStatus));

        // Assert
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    public void updateTaskStatus_withNullTaskId_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        Task.Status newStatus = Task.Status.DONE;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.updateTaskStatus(taskId, newStatus));

        // Assert
        assertTrue(exception.getMessage().contains("Task ID"));
    }

    @Test
    public void updateTaskStatus_withNullStatus_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = "task1";
        Task.Status newStatus = null;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.updateTaskStatus(taskId, newStatus));

        // Assert
        assertTrue(exception.getMessage().contains("status"));
    }

    @Test
    public void deleteTask_withExistingTaskId_shouldDeleteTask() {
        // Arrange
        String taskId = "task1";
        String description = "Description of task1";
        taskManager.createTask(taskId, description);

        // Act
        taskManager.deleteTask(taskId);

        // Assert
        assertThrows(IllegalStateException.class, () -> taskManager.getTask(taskId));
    }

    @Test
    public void deleteTask_withNullTaskId_shouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> taskManager.deleteTask(taskId));

        // Assert
        assertTrue(exception.getMessage().contains("Task ID"));
    }

    @Test
    public void deleteTask_withNonExistingTaskId_shouldThrowIllegalStateException() {
        // Arrange
        String taskId = "task1";

        // Act
        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> taskManager.deleteTask(taskId));

        // Assert
        assertTrue(exception.getMessage().contains("does not exist"));
    }
}