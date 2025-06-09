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

    //метод для поиска по названию, автору, жанру, году, смешанный
    public List<Book> searchBooks(String title, String author, String genre, Integer year) {
        return books.stream()
                .filter(book -> title == null || book.title().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author == null || book.author().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> genre == null || book.genre().toLowerCase().contains(genre.toLowerCase()))
                .filter(book -> year == null || book.year() == year)
                .collect(Collectors.toList());
    }
}