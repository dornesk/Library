package service;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (book.title().isBlank() || book.author().isBlank()) {
            throw new IllegalArgumentException("Title and author cannot be blank.");
        }
        if (books.stream().anyMatch( //проверка на дубликаты
                b -> b.title().equalsIgnoreCase(book.title()) &&
                        b.author().equalsIgnoreCase(book.author()))) {
            throw new IllegalArgumentException("Book with the same title and author already exists.");
        }

        books.add(book);
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.title().equals(title));
    }

    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.title().trim().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books); //защитное копирование
    }
}