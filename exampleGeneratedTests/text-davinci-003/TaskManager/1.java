import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {

  //Decision Coverage
  @Test
  public void createTask_WithNullId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String nullId = null;
    assertThrows(IllegalArgumentException.class, () -> manager.createTask(nullId, "test"));
  }

  @Test
  public void createTask_WithNullDescription_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String nullDescription = null;
    assertThrows(IllegalArgumentException.class, () -> manager.createTask("taskId", nullDescription));
  }

  @Test
  public void createTask_WithExistingTaskId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    manager.createTask(taskId, "test");
    assertThrows(IllegalStateException.class, () -> manager.createTask(taskId, "test"));
  }

  @Test
  public void createTask_WithValidInput_ShouldCreateTask() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    String description = "test";
    Task task = manager.createTask(taskId, description);
    assertEquals(task.getTaskId(), taskId);
    assertEquals(task.getDescription(), description);
    assertEquals(task.getStatus(), Task.Status.TODO);
  }

  //Statement Coverage
  @Test
  public void getTask_WithNullId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String nullId = null;
    assertThrows(IllegalArgumentException.class, () -> manager.getTask(nullId));
  }

  @Test
  public void getTask_WithNonExistingTaskId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    assertThrows(IllegalStateException.class, () -> manager.getTask(taskId));
  }

  @Test
  public void getTask_WithExistingTaskId_ShouldReturnTaskObject() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    String description = "test";
    Task task = manager.createTask(taskId, description);
    Task retrievedTask = manager.getTask(taskId);
    assertEquals(task, retrievedTask);
  }

  //Control Flow Coverage
  @Test
  public void updateTaskStatus_WithNullId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String nullId = null;
    assertThrows(IllegalArgumentException.class, () -> manager.updateTaskStatus(nullId, Task.Status.IN_PROGRESS));
  }

  @Test
  public void updateTaskStatus_WithNullStatus_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    Task.Status nullStatus = null;
    assertThrows(IllegalArgumentException.class, () -> manager.updateTaskStatus("taskId", nullStatus));
  }

  @Test
  public void updateTaskStatus_WithExistingTaskId_ShouldSetTaskStatus() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    String description = "test";
    manager.createTask(taskId, description);
    manager.updateTaskStatus(taskId, Task.Status.IN_PROGRESS);
    Task task = manager.getTask(taskId);
    assertEquals(task.getStatus(), Task.Status.IN_PROGRESS);
  }

  //Data Flow Coverage
  @Test
  public void deleteTask_WithNullId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String nullId = null;
    assertThrows(IllegalArgumentException.class, () -> manager.deleteTask(nullId));
  }

  @Test
  public void deleteTask_WithNonExistingTaskId_ShouldThrowException() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    assertThrows(IllegalStateException.class, () -> manager.deleteTask(taskId));
  }

  @Test
  public void deleteTask_WithExistingTaskId_ShouldRemoveTaskFromMap() {
    TaskManager manager = new TaskManager();
    String taskId = "taskId";
    String description = "test";
    manager.createTask(taskId, description);
    manager.deleteTask(taskId);
    assertThrows(IllegalStateException.class, () -> manager.getTask(taskId));
  }
}