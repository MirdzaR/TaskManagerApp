package service;

import model.Task;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private final String FILE_PATH = "tasks.csv";

    // Constructor
    public TaskManager() {
        loadTasks();
    }

    //  Adds a task to the list and saves to file
    public void addTask(String title, String description, LocalDate dueDate) {
        tasks.add(new Task(title, description, dueDate));
        saveTasks();
    }

    // Lists all tasks
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    // Marks a task as complete and saves the list
    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
            saveTasks();
        }
    }

    // Deletes a task and saves the list
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        }
    }

    // Check if the input string is not empty or just whitespace
    public String validateNonEmptyString(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return input.trim();
    }

    public int getTaskCount() {
       return tasks.size();
    }

    // Loads tasks from a CSV file
    private void loadTasks() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) return;
            Files.lines(Paths.get(FILE_PATH))
                    .map(line -> {
                        String[] parts = line.split(" - ");
                        return new Task(parts[0], parts[2], LocalDate.parse(parts[1]));
                    })
                    .forEach(tasks::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves all tasks to a CSV file
    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.println(task.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
