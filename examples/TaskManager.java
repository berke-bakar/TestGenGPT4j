package org.example;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private final Map<String, Task> tasks = new HashMap<>();

    public Task createTask(String taskId, String description) {
        if (taskId == null || description == null) {
            throw new IllegalArgumentException("Task ID and description must not be null");
        }

        if (tasks.containsKey(taskId)) {
            throw new IllegalStateException("Task already exists");
        }

        Task task = new Task(taskId, description);
        tasks.put(taskId, task);
        return task;
    }

    public Task getTask(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID must not be null");
        }

        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalStateException("Task does not exist");
        }

        return task;
    }

    public void updateTaskStatus(String taskId, Task.Status status) {
        if (taskId == null || status == null) {
            throw new IllegalArgumentException("Task ID and status must not be null");
        }

        Task task = getTask(taskId);
        task.setStatus(status);
    }

    public void deleteTask(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID must not be null");
        }

        if (!tasks.containsKey(taskId)) {
            throw new IllegalStateException("Task does not exist");
        }

        tasks.remove(taskId);
    }
}

class Task {
    private final String taskId;
    private final String description;
    private Status status;

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE
    }

    public Task(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
        this.status = Status.TODO;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

