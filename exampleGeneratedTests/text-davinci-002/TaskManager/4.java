import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class TaskManagerTest {
    private final Map<String, Task> tasks = new HashMap<>();

    // test 1: to test the createTask method with valid inputs
    @Test
    public void testCreateTask_validInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        assertTrue(task.getTaskId().equals("1"));
        assertTrue(task.getDescription().equals("task 1"));
        assertTrue(task.getStatus().equals(Task.Status.TODO));
    }

    // test 2: to test the createTask method with invalid inputs
    @Test
    public void testCreateTask_invalidInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(null, "task 1");
        assertFalse(task.getTaskId().equals("1"));
        assertTrue(task.getDescription().equals("task 1"));
        assertTrue(task.getStatus().equals(Task.Status.TODO));
    }

    // test 3: to test the getTask method with valid inputs
    @Test
    public void testGetTask_validInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        assertTrue(taskManager.getTask("1").getTaskId().equals("1"));
        assertTrue(taskManager.getTask("1").getDescription().equals("task 1"));
        assertTrue(taskManager.getTask("1").getStatus().equals(Task.Status.TODO));
    }

    // test 4: to test the getTask method with invalid inputs
    @Test
    public void testGetTask_invalidInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        assertFalse(taskManager.getTask(null).getTaskId().equals("1"));
        assertTrue(taskManager.getTask(null).getDescription().equals("task 1"));
        assertTrue(taskManager.getTask(null).getStatus().equals(Task.Status.TODO));
    }

    // test 5: to test the updateTaskStatus method with valid inputs
    @Test
    public void testUpdateTaskStatus_validInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        taskManager.updateTaskStatus("1", Task.Status.IN_PROGRESS);
        assertTrue(taskManager.getTask("1").getStatus().equals(Task.Status.IN_PROGRESS));
    }

    // test 6: to test the updateTaskStatus method with invalid inputs
    @Test
    public void testUpdateTaskStatus_invalidInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        taskManager.updateTaskStatus(null, Task.Status.IN_PROGRESS);
        assertTrue(taskManager.getTask("1").getStatus().equals(Task.Status.TODO));
    }

    // test 7: to test the deleteTask method with valid inputs
    @Test
    public void testDeleteTask_validInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        taskManager.deleteTask("1");
        assertFalse(taskManager.tasks.containsKey("1"));
    }

    // test 8: to test the deleteTask method with invalid inputs
    @Test
    public void testDeleteTask_invalidInputs() {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask("1", "task 1");
        taskManager.tasks.put("1", task);
        taskManager.deleteTask(null);
        assertTrue(taskManager.tasks.containsKey("1"));
    }
}