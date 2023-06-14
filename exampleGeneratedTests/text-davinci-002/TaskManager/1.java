import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskManagerTest {

    TaskManager manager;

    @BeforeEach
    void setup() {
        manager = new TaskManager();
    }

    @Test
    void createTask_WithValidTaskIdAndDescription_ShouldCreateTask() {
        // Arrange
        String taskId = "1";
        String description = "Test task";

        // Act
        Task task = manager.createTask(taskId, description);

        // Assert
        assertEquals(taskId, task.getTaskId());
        assertEquals(description, task.getDescription());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    void createTask_WithNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        String description = "Test task";

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.createTask(taskId, description));
    }

    @Test
    void createTask_WithNullDescription_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = "1";
        String description = null;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.createTask(taskId, description));
    }

    @Test
    void createTask_WithDuplicateTaskId_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = "1";
        String description = "Test task";
        manager.createTask(taskId, description);

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.createTask(taskId, description));
    }

    @Test
    void getTask_WithExistingTaskId_ShouldReturnTask() {
        // Arrange
        String taskId = "1";
        String description = "Test task";
        Task expectedTask = manager.createTask(taskId, description);

        // Act
        Task actualTask = manager.getTask(taskId);

        // Assert
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void getTask_WithNonExistingTaskId_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = "1";

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.getTask(taskId));
    }

    @Test
    void getTask_WithNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.getTask(taskId));
    }

    @Test
    void updateTaskStatus_WithNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        Task.Status status = Task.Status.IN_PROGRESS;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_WithNullStatus_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = "1";
        Task.Status status = null;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_WithNonExistingTaskId_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = "1";
        Task.Status status = Task.Status.IN_PROGRESS;

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_WithExistingTaskId_ShouldUpdateStatus() {
        // Arrange
        String taskId = "1";
        String description = "Test task";
        Task.Status expectedStatus = Task.Status.IN_PROGRESS;
        Task task = manager.createTask(taskId, description);

        // Act
        manager.updateTaskStatus(taskId, expectedStatus);

        // Assert
        assertEquals(expectedStatus, task.getStatus());
    }

    @Test
    void deleteTask_WithNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.deleteTask(taskId));
    }

    @Test
    void deleteTask_WithNonExistingTaskId_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = "1";

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.deleteTask(taskId));
    }

    @Test
    void deleteTask_WithExistingTaskId_ShouldDeleteTask() {
        // Arrange
        String taskId = "1";
        String description = "Test task";
        manager.createTask(taskId, description);

        // Act
        manager.deleteTask(taskId);

        // Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.getTask(taskId));
    }

}