import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.example.Task;
import org.example.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void setUp(TestInfo testInfo, TestReporter testReporter) {
        taskManager = new TaskManager();
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
    }

    // Decision coverage
    @Test
    void createTask_GivenNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        String description = "testDescription";

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(taskId, description));
    }

    @Test
    void createTask_GivenNullDescription_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(taskId, description));
    }

    @Test
    void createTask_ GivenValidInput_ShouldCreateTask() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = "testDescription";

        // Act + Assert
        assertDoesNotThrow(() -> {
            taskManager.createTask(taskId, description);
            Task task = taskManager.getTask(taskId);
            assertEquals(task.getTaskId(), taskId);
            assertEquals(task.getDescription(), description);
        });
    }

    @Test
    void createTask_GivenDuplicateTaskId_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = "testDescription";
        taskManager.createTask(taskId, description);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> taskManager.createTask(taskId, description));
    }

    // Statement coverage
    @Test
    void updateTaskStatus_GivenNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;
        Task.Status status = Task.Status.IN_PROGRESS;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_GivenNullStatus_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        Task.Status status = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_GivenNonExistentTask_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        Task.Status status = Task.Status.IN_PROGRESS;

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> taskManager.updateTaskStatus(taskId, status));
    }

    @Test
    void updateTaskStatus_GivenExistentTask_ShouldUpdateTaskStatus() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = "testDescription";
        taskManager.createTask(taskId, description);
        Task.Status status = Task.Status.IN_PROGRESS;

        // Act + Assert
        assertDoesNotThrow(() -> {
            taskManager.updateTaskStatus(taskId, status);
            Task task = taskManager.getTask(taskId);
            assertEquals(task.getStatus(), status);
        });
    }

    // Control flow coverage
    @Test
    void deleteTask_GivenNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(taskId));
    }

    @Test
    void deleteTask_GivenNonExistentTask_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> taskManager.deleteTask(taskId));
    }

    @Test
    void deleteTask_GivenExistentTask_ShouldDeleteTask() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = "testDescription";
        taskManager.createTask(taskId, description);

        // Act + Assert
        assertDoesNotThrow(() -> {
            taskManager.deleteTask(taskId);
            assertThrows(IllegalStateException.class, () -> taskManager.getTask(taskId));
        });
    }

    // Data flow coverage
    @Test
    void getTask_GivenNullTaskId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String taskId = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(taskId));
    }

    @Test
    void getTask_GivenNonExistentTask_ShouldThrowIllegalStateException() {
        // Arrange
        String taskId = UUID.randomUUID().toString();

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> taskManager.getTask(taskId));
    }

    @Test
    void getTask_GivenExistentTask_ShouldReturnTask() {
        // Arrange
        String taskId = UUID.randomUUID().toString();
        String description = "testDescription";
        taskManager.createTask(taskId, description);

        // Act + Assert
        assertDoesNotThrow(() -> {
            Task task = taskManager.getTask(taskId);
            assertEquals(task.getTaskId(), taskId);
            assertEquals(task.getDescription(), description);
        });
    }
}