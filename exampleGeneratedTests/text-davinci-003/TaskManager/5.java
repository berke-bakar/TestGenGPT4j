import org.example.TaskManager;
import org.example.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    //Test for createTask()
    @Test
    public void testCreateTask_givenValidTaskIdDescription_shouldCreateTaskSuccessfully() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";

        //when
        taskManager.createTask(taskId, description);

        //then
        assertTrue(taskManager.getTask(taskId) != null);
    }

    @Test
    public void testCreateTask_givenNullTaskId_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();
        String description = "Create unit tests for TaskManager";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(null, description));

        //then
        assertEquals("Task ID and description must not be null", exception.getMessage());
    }

    @Test
    public void testCreateTask_givenNullDescription_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.createTask(taskId, null));

        //then
        assertEquals("Task ID and description must not be null", exception.getMessage());
    }

    @Test
    public void testCreateTask_givenExistingTaskId_shouldThrowsIllegalStateException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";
        taskManager.createTask(taskId, description);

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> taskManager.createTask(taskId, description));

        //then
        assertEquals("Task already exists", exception.getMessage());
    }

    //Test for getTask()
    @Test
    public void testGetTask_givenValidTaskId_shouldGetTaskSuccessfully() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";
        taskManager.createTask(taskId, description);

        //when
        Task task = taskManager.getTask(taskId);

        //then
        assertEquals(taskId, task.getTaskId());
    }

    @Test
    public void testGetTask_givenNullTaskId_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.getTask(null));

        //then
        assertEquals("Task ID must not be null", exception.getMessage());
    }

    @Test
    public void testGetTask_givenNonExistingTaskId_shouldThrowsIllegalStateException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> taskManager.getTask(taskId));

        //then
        assertEquals("Task does not exist", exception.getMessage());
    }

    //Test for updateTaskStatus()
    @Test
    public void testUpdateTaskStatus_givenValidTaskIdStatus_shouldUpdateTaskSuccessfully() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";
        taskManager.createTask(taskId, description);
        Task.Status status = Task.Status.IN_PROGRESS;

        //when
        taskManager.updateTaskStatus(taskId, status);

        //then
        Task task = taskManager.getTask(taskId);
        assertEquals(status, task.getStatus());
    }

    @Test
    public void testUpdateTaskStatus_givenNullTaskId_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();
        Task.Status status = Task.Status.IN_PROGRESS;

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(null, status));

        //then
        assertEquals("Task ID and status must not be null", exception.getMessage());
    }

    @Test
    public void testUpdateTaskStatus_givenNullStatus_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";
        taskManager.createTask(taskId, description);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskStatus(taskId, null));

        //then
        assertEquals("Task ID and status must not be null", exception.getMessage());
    }

    @Test
    public void testUpdateTaskStatus_givenNonExistingTaskId_shouldThrowsIllegalStateException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        Task.Status status = Task.Status.IN_PROGRESS;

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> taskManager.updateTaskStatus(taskId, status));

        //then
        assertEquals("Task does not exist", exception.getMessage());
    }

    //Test for deleteTask()
    @Test
    public void testDeleteTask_givenValidTaskId_shouldDeleteTaskSuccessfully() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";
        String description = "Create unit tests for TaskManager";
        taskManager.createTask(taskId, description);

        //when
        taskManager.deleteTask(taskId);

        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> taskManager.getTask(taskId));
        assertEquals("Task does not exist", exception.getMessage());
    }

    @Test
    public void testDeleteTask_givenNullTaskId_shouldThrowsIllegalArgumentException() {
        //given
        TaskManager taskManager = new TaskManager();

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.deleteTask(null));

        //then
        assertEquals("Task ID must not be null", exception.getMessage());
    }

    @Test
    public void testDeleteTask_givenNonExistingTaskId_shouldThrowsIllegalStateException() {
        //given
        TaskManager taskManager = new TaskManager();
        String taskId = "T001";

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> taskManager.deleteTask(taskId));

        //then
        assertEquals("Task does not exist", exception.getMessage());
    }
}