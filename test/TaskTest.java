import org.junit.jupiter.api.Test;

import model.Task;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Learn Java", "Complete Task Manager", LocalDate.of(2025, 3, 20));
        assertNotNull(task);
        assertEquals("Learn Java", task.getTitle());
        assertEquals("Complete Task Manager", task.getDescription());
        assertEquals(LocalDate.of(2025, 3, 20), task.getDueDate());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testMarkAsCompleted() {
        Task task = new Task("Learn Java", "Complete Task Manager", LocalDate.of(2025, 3, 20));
        task.markAsCompleted();
        assertTrue(task.isCompleted());
    }

    @Test
    public void testToString() {
        Task task = new Task("Learn Java", "Complete Task Manager", LocalDate.of(2025, 3, 20));
        String expected = "[ ] Learn Java - 2025-03-20 - Complete Task Manager";
        assertEquals(expected, task.toString());
    }
}