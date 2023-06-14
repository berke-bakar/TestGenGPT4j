import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.example.Task;
import org.example.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Task Manager Tests")
public class TaskManagerTesting {
    private TaskManager manager;
    private Map<String, Task> tasks;
    private static final String taskId = "testId";
    private static final String description = "testDescription";
    private static final Task.Status status = Task.Status.TODO;

    //Priority 1
    @Test
    @DisplayName("Create Task with valid data")
    public void testCreateTaskWithValidData(TestInfo testInfo) {
        Task task = manager.createTask(taskId, description);
        assertEquals(taskId, task.getTaskId(), "Check taskId");
        assertEquals(description, task.getDescription(), "Check description");
        assertEquals(status, task.getStatus(), "Check status");
    }

     //Priority 2
    @ParameterizedTest(name = "Create Task with {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void testCreateTaskWithInvalidData(String invalidData, TestInfo testInfo) {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.createTask(invalidData, invalidData);
        });
    }

    //Priority 3
    @Test
    @DisplayName("Create Task with already existing taskId")
    public void testCreateTaskWithExistingTaskId() {
        manager.createTask(taskId, description);
        assertThrows(IllegalStateException.class, () -> {
            manager.createTask(taskId, description);
        });
    }

    //Priority 4
    @ParameterizedTest(name = "Get Task with {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void testGetTaskWithInvalidId(String invalidId, TestInfo testInfo) {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.getTask(invalidId);
        });
    }

    //Priority 5
    @Test
    @DisplayName("Get Task with non-existing taskId")
    public void testGetTaskWithNonExistingTaskId() {
        assertThrows(IllegalStateException.class, () -> {
            manager.getTask(taskId);
        });
    }

    //Priority 6
    @Test
    @DisplayName("Get Task with existing taskId")
    public void testGetTaskWithExistingTaskId() {
        manager.createTask(taskId, description);
        Task task = manager.getTask(taskId);
        assertEquals(taskId, task.getTaskId(), "Check task Id");
        assertEquals(description, task.getDescription(), "Check description");
        assertEquals(status, task.getStatus(), "Check status");
    }

    //Priority 7
    @ParameterizedTest(name = "Update Task with {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void testUpdateTaskWithInvalidData(String invalidData, TestInfo testInfo) {
        manager.createTask(taskId, description);
        assertThrows(IllegalArgumentException.class, () -> {
            manager.updateTaskStatus(invalidData, null);
        });
    }

    //Priority 8
    @Test
    @DisplayName("Update Task with non-existing taskId")
    public void testUpdateTaskWithNonExistingTaskId() {
        assertThrows(IllegalStateException.class, () -> {
            manager.updateTaskStatus(taskId, Task.Status.IN_PROGRESS);
        });
    }

    //Priority 9
    @Test
    @DisplayName("Update Task with existing taskId")
    public void testUpdateTaskWithExistingTaskId() {
        manager.createTask(taskId, description);
        Task task = manager.getTask(taskId);
        manager.updateTaskStatus(taskId, Task.Status.IN_PROGRESS);
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus(), "Check new status");
    }

      //Priority 10
    @ParameterizedTest(name = "Delete Task with {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    public void testDeleteTaskWithInvalidTaskId(String invalidId, TestInfo testInfo) {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.deleteTask(invalidId);
        });
    }

    //Priority 11
    @Test
    @DisplayName("Delete Task with non-existing taskId")
    public void testDeleteTaskWithNonExistingTaskId() {
        assertThrows(IllegalStateException.class, () -> {
            manager.deleteTask(taskId);
        });
    }

    //Priority 12
    @Test
    @DisplayName("Delete Task with existing taskId")
    public void testDeleteTaskWithExistingTaskId() {
        manager.createTask(taskId, description);
        manager.deleteTask(taskId);
        assertThrows(IllegalStateException.class, () -> {
            manager.getTask(taskId);
        });
    }

    @BeforeEach
    public void initialize() {
        manager = new TaskManager();
        tasks = manager.getTasks();
    }
}