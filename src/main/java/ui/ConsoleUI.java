package ui;

import model.Book;
import service.Library;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Library library;
    private final Scanner scanner;

    public ConsoleUI(Library library, Scanner scanner) {
        this.library = library;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;

        while (running) {
            try {
                System.out.println("""
                        \n
                         Write 'add' for add Book
                         Write 'remove' for Remove Book
                         Write 'find' for Search Book
                         Write 'list' for List All Books
                         Write 'exit' for Exit
                        """);

                String choice = scanner.nextLine().trim().toLowerCase();

                switch (choice) {
                    case "add" -> handleAdd();
                    case "remove" -> handleRemove();
                    case "find" -> handleSearch();
                    case "list" -> handleList();
                    case "exit" -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleAdd() {
        String title = promptNotBlank("Enter title: ");
        String author = promptNotBlank("Enter author: ");
        String genre = promptNotBlank("Enter genre: ");
        String yearStr = promptNotBlank("Enter year: ");

        try {
            int year = Integer.parseInt(yearStr);
            library.addBook(new Book(title, author, genre, year));
            System.out.println("Book added.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private String promptNotBlank(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again");
        }
    }

    private void handleRemove() {
        System.out.println("Enter title: ");
        String titleToRemove = scanner.nextLine().trim().toLowerCase();
        boolean removingResult = library.removeBook(titleToRemove);

        if (removingResult) {
            System.out.println("Book removed succesfully.");
        } else {
            System.out.println("No books found.");
        }
    }

    private void handleSearch() {
        System.out.println("Enter title to search: ");
        String searchTitle = scanner.nextLine().trim().toLowerCase();
        List<Book> results = library.searchByTitle(searchTitle);

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleList() {
        List<Book> books = library.listBooks();

        if (books.isEmpty()) {
            System.out.println("Library is empty.");
        } else {
            books.forEach(System.out::println);
        }
    }
}
