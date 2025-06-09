package ui;

import model.Book;
import service.Library;

import java.util.List;

public class ConsoleUI {
    private final Library library;
    private final InputReader reader;

    public ConsoleUI(Library library, InputReader reader) {
        this.library = library;
        this.reader = reader;
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
                         Write 'avdfind' for Advanced Search
                        """);

                String choice = reader.readLine().toLowerCase();

                switch (choice) {
                    case "add" -> handleAdd();
                    case "remove" -> handleRemove();
                    case "find" -> handleSearch();
                    case "avdfind" -> handleAdvancedSearch();
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
            String input = reader.readLine();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again");
        }
    }

    private void handleRemove() {
        System.out.println("Enter title: ");
        String titleToRemove = reader.readLine();
        boolean removingResult = library.removeBook(titleToRemove);

        if (removingResult) {
            System.out.println("Book removed succesfully.");
        } else {
            System.out.println("No books found.");
        }
    }

    private void handleSearch() {
        System.out.println("Enter title to search: ");
        String searchTitle = reader.readLine();
        List<Book> results = library.searchByTitle(searchTitle);

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleAdvancedSearch() {
        System.out.println("Advanced Search. Leave blank to skip the field.");

        System.out.println("Title: ");
        String title = emptyToNull(reader.readLine());

        System.out.println("Author: ");
        String author = emptyToNull(reader.readLine());

        System.out.println("Genre: ");
        String genre = emptyToNull(reader.readLine());

        System.out.println("Year: ");
        String yearInput = reader.readLine();
        Integer year = null;
        //проверяем поле год на отсутствующее значение и некорректный формат
        try {
            if (!yearInput.isBlank()) {
                year = Integer.parseInt(yearInput);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format, skipping year filter.");
        }

        //выводим результат поиска
        List<Book> results = library.searchBooks(title, author, genre, year);
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    //метод для handleAdvancedSearch - заменяет пустое поле на null
    private String emptyToNull(String input) {
        return input.isBlank() ? null : input;//для возврата null при пустом поле
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
