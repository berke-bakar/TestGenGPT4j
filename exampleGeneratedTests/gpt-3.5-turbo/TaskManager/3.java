package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskManager Unit Test")
class TaskManagerTest {
    private TaskManager taskManager;
    private Task task;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        task = taskManager.createTask("1", "Test Task");
    }

    @Test
    @DisplayName("Create Task")
    void createTask() {
        Task createdTask = taskManager.createTask("2", "Another Test Task");

        assertNotNull(createdTask);
        assertEquals("2", createdTask.getTaskId());
        assertEquals("Another Test Task", createdTask.getDescription());
        assertEquals(Task.Status.TODO, createdTask.getStatus());
    }

    @Test
    @DisplayName("Create Task with Null Arguments")
    void createTaskWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(null, null));
    }

    @Test
    @DisplayName("Create Task that Already Exists")
    void createTaskThatAlreadyExists() {
        assertThrows(IllegalStateException.class, () -> taskManager.createTask("1", "Duplicate Test Task"));
    }

    @Test
    @DisplayName("Get Task")
    void getTask() {
        Task retrievedTask = taskManager.getTask("1");

        assertNotNull(retrievedTask);
        assertEquals(task.getTaskId(), retrievedTask.getTaskId());
        assertEquals(task.getDescription(), retrievedTask.getDescription());
        assertEquals(task.getStatus(), retrievedTask.getStatus());
    }

    @Test
    @DisplayName("Get Task with Null Argument")
    void getTaskWithNullArgument() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(null));
    }

    @Test
    @DisplayName("Get Nonexistent Task")
    void getNonexistentTask() {
        assertThrows(IllegalStateException.class, () -> taskManager.getTask("2"));
    }

    @Test
    @DisplayName("Update Task Status")
    void updateTaskStatus() {
        taskManager.updateTaskStatus("1", Task.Status.IN_PROGRESS);

        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    @DisplayName("Update Task Status with Null Arguments")
    void updateTaskStatusWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(null, null));
    }

    @Test
    @DisplayName("Delete Task")
    void deleteTask() {
        taskManager.deleteTask("1");

        assertThrows(IllegalStateException.class, () -> taskManager.getTask("1"));
    }

    @Test
    @DisplayName("Delete Task with Null Argument")
    void deleteTaskWithNullArgument() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(null));
    }

    @Test
    @DisplayName("Delete Nonexistent Task")
    void deleteNonexistentTask() {
        assertThrows(IllegalStateException.class, () -> taskManager.deleteTask("2"));
    }
}