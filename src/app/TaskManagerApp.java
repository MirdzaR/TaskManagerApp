package app;

import service.TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManagerApp {
    // Check if the input string is not empty or just whitespace
    private static String validateNonEmptyString(Scanner scanner, String input, String fieldName) {
        while (input == null || input.trim().isEmpty()) {
            System.out.println(fieldName + " cannot be empty.");
            System.out.print("Please input " + fieldName + ": ");
            input = scanner.nextLine();
        }
        return input.trim();
    }

    // Check if input value for date field corresponds to date format and date is equal or bigger than date today
    private static String validateDueDate(Scanner scanner, String input) {
        while (true) {
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Due Date cannot be empty.");
            } else {
                LocalDate dueDate = parseDueDate(input);
                if (dueDate == null) {
                    System.out.println("Due Date is in incorrect form. Please use YYYY-MM-DD.");
                } else if (dueDate.isBefore(LocalDate.now())) {
                    System.out.println("Due Date cannot be in the past.");
                } else {
                    return input.trim(); // Valid date, return it!
                }
            }
            System.out.print("Please input Due Date (YYYY-MM-DD): ");
            input = scanner.nextLine();
        }
    }

    // Parse and validate date
    private static LocalDate parseDueDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Validate task index when a'ccessing tasks
    private static int validateTaskIndex(Scanner scanner, int size) {
        int showSize = size > 0 ? size - 1 : 0;
        String verbaliseIndexMessage = showSize == 0 ? "0: " : ("between 0 and " + showSize + ": ");
        int input = -1;

        while (true) {
            System.out.print("Please provide index " + verbaliseIndexMessage);
            try {
                input = scanner.nextInt();
                scanner.nextLine();  // Consume newline character left by nextInt()

                if (input >= 0 && input < size) {
                    return input;  // Valid index, return it!
                } else {
                    System.out.println("Provided index is not valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect index. Enter a number.");
                scanner.nextLine();  // Clear invalid input
            }
        }
    }

    private static int validateChoiceInt(Scanner scanner){
        int input = -1;

        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();  // Consume newline character left by nextInt()
                return input;  // Valid index, return it!
            } catch (InputMismatchException e) {
                System.out.println("Input value must be an integer");
                System.out.print("Choose an option: ");
                scanner.nextLine();  // Clear invalid input
            }
        }
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask Manager:");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = validateChoiceInt(scanner);

            switch (choice) {
                case 1:
                    System.out.print("Title: ");
                    String title = validateNonEmptyString(scanner, scanner.nextLine(), "Title");
                    System.out.print("Description: ");
                    String description = validateNonEmptyString(scanner, scanner.nextLine(),"Description");
                    System.out.print("Due Date (YYYY-MM-DD): ");
                    LocalDate dueDate = LocalDate.parse(validateDueDate(scanner, scanner.nextLine()));
                    taskManager.addTask(title, description, dueDate);
                    System.out.println("Task added!");
                    break;
                case 2:
                    taskManager.listTasks();
                    break;
                case 3:
                    if (taskManager.getTaskCount() == 0) {
                        System.out.println("The task list is empty");
                    } else {
                        System.out.print("Task index to mark as complete: ");
                        int completeIndex = validateTaskIndex(scanner, taskManager.getTaskCount());
                        taskManager.markTaskAsCompleted(completeIndex);
                        System.out.println("Task marked as complete.");
                    }
                    break;
                case 4:
                    if (taskManager.getTaskCount() == 0) {
                        System.out.println("The task list is empty");
                    } else {
                        System.out.print("Task index to delete: ");
                        int deleteIndex = validateTaskIndex(scanner, taskManager.getTaskCount());
                        taskManager.deleteTask(deleteIndex);
                        System.out.println("Task deleted.");
                    }
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}