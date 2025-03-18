import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import service.TaskManager;
import java.time.LocalDate;


public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        System.setProperty("task.file", "test_tasks.csv");  // Switch to test file
        taskManager = new TaskManager();
        taskManager.clearTasks();
    }

    @Test
    public void testAddTask() {
        taskManager.addTask("Task 1", "Description 1", LocalDate.now());
        assertEquals(1, taskManager.getTaskCount());
    }

    @Test
    public void testMarkTaskAsCompleted() {
        taskManager.addTask("Task 1", "Description 1", LocalDate.now());
        taskManager.markTaskAsCompleted(0);
        assertTrue(taskManager.getTask(0).isCompleted());
    }

    @Test
    public void testDeleteTask() {
        taskManager.addTask("Task 1", "Description 1", LocalDate.now());
        taskManager.deleteTask(0);
        assertEquals(0, taskManager.getTaskCount());
    }
}