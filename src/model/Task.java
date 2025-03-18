package model;

import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    // Constructor
    public Task(String title, String description, LocalDate date) {
        this.title = title;
        this.description = description;
        this.dueDate = date;
        this.isCompleted = false;
    }

    // Method to mark a task as completed
    public void markAsCompleted(){
        this.isCompleted = true;
    }

    // Override toString() to control how tasks are displayed
    @Override
    public String toString() {
        return (isCompleted ? "[X]" : "[ ]") + " " + title + " - " + dueDate + " - " + description;
    }

}